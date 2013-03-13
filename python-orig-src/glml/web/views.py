from django import forms
from django.contrib.auth.decorators import login_required
from django.core.urlresolvers import reverse
from django.http import HttpResponseRedirect
from django.shortcuts import get_object_or_404, render_to_response
from django.template import RequestContext

from glml.utils import add_message, allow
from glml.web.models import *

def home(request, year=None, month=None, day=None):
    from glml.utils import get_working_year
    working_year = get_working_year(request)
    if year and month and day:
        from datetime import datetime
        try:
            date = datetime(int(year), int(month), int(day))
        except ValueError:
            from django.http import Http404
            raise Http404
        test_date = get_object_or_404(TestDate, date=date)
        if test_date.year != working_year:
            return HttpResponseRedirect(reverse(home))
    else:
        test_date = None
    data = []
    for district in District.objects.filter(year__id=working_year.id).exclude(glml_id=ANSWER_STUDENT_ID[0]):
        data.append({'district': district,
                     'schools': district.get_top_schools(test_date),
                     'students': district.get_top_students(test_date)})
    return render_to_response('web/home.html',
                              {'test_date': test_date,
                               'test_dates': working_year.testdate_set.all(),
                               'data': data},
                              RequestContext(request))

def permission_denied(request):
    response = render_to_response('web/permission_denied.html',
                                  RequestContext(request))
    response.status = 403
    return response

def login(request):
    if request.user.is_authenticated():
        add_message(request, "You are already logged in.")
        return HttpResponseRedirect(reverse(home))
    from django.contrib.auth import REDIRECT_FIELD_NAME
    from glml.web.forms import LoginForm
    if request.method == 'POST':
        form = LoginForm(request.POST)
        if request.session.test_cookie_worked():
            no_cookies = False
            if form.is_valid():
                if REDIRECT_FIELD_NAME in request.session:
                    redirect_to = request.session[REDIRECT_FIELD_NAME]
                    del request.session[REDIRECT_FIELD_NAME]
                else:
                    redirect_to = ''
                if not redirect_to or '://' in redirect_to or ' ' in redirect_to:
                    redirect_to = reverse(home)
                from django.contrib.auth import login
                login(request, form.cleaned_data['user'])
                request.session.delete_test_cookie()
                from glml.utils import get_school, get_working_year, set_up
                set_up()
                request.session['school'] = get_school(request)
                request.session['working_year'] = get_working_year(request)
                return HttpResponseRedirect(redirect_to)
        else:
            no_cookies = True
    else:
        request.session[REDIRECT_FIELD_NAME] = request.REQUEST.get(REDIRECT_FIELD_NAME, '')
        form = LoginForm()
        no_cookies = False
    request.session.set_test_cookie()
    return render_to_response('web/login.html', 
                              {'form': form,
                               'no_cookies': no_cookies},
                              RequestContext(request))

@login_required
def logout(request):
    request.session.pop('school', None)
    request.session.pop('working_year', None)
    from django.contrib.auth import logout
    logout(request)
    return HttpResponseRedirect(reverse(home))

@login_required
def send_email(request):
    from glml.web.forms import SendEmailForm
    user = request.user
    if request.method == 'POST':
        form = SendEmailForm(user, request.POST)
        if form.is_valid():
            if 'recipients' in form.cleaned_data:
                users = form.cleaned_data['recipients']
            else:
                users = None
            from glml.utils import email
            email(user, form.cleaned_data['subject'], form.cleaned_data['message'], users)
            add_message(request, u'Your message was successfully sent.')
            return HttpResponseRedirect(reverse(home))
    else:
        form = SendEmailForm(user)
    return render_to_response('web/send_email.html', 
                              {'form': form},
                              RequestContext(request))
    
@login_required
def settings(request):
    user = request.user
    from glml.web.forms import ChangeEmailForm, ChangePasswordForm
    if request.POST:
        if 'current_password' in request.POST:
            pw_form = ChangePasswordForm(request.POST)
            pw_form.user = user
            if pw_form.is_valid():
                user.set_password(pw_form.cleaned_data['new_password'])
                user.save()
                add_message(request, u'Your password has been changed.')
                return HttpResponseRedirect(reverse(settings))
            email_form = ChangeEmailForm(user)
        if 'email' in request.POST: 
            email_form = ChangeEmailForm(user, request.POST)
            if email_form.is_valid():
                user.email = email_form.cleaned_data['email']
                user.save()
                add_message(request, u'Your email has been changed.')
                return HttpResponseRedirect(reverse(settings))
            pw_form = ChangePasswordForm()
    else:
        pw_form = ChangePasswordForm()
        email_form = ChangeEmailForm(user)
    return render_to_response('web/settings.html', 
                              {'pw_form': pw_form,
                               'email_form': email_form},
                              RequestContext(request))

@login_required
def school_view(request):
    from glml.utils import get_school, get_working_year
    school = get_school(request)
    working_year = get_working_year(request)
    if not school:
        add_message(request, u'Please select a school first.')
        return HttpResponseRedirect(reverse(home))
    try:
        school_id = school.schoolid_set.get(district__year__id=working_year.id)
    except SchoolID.DoesNotExist:
        school_id = None
    if allow(request):
        if school_id:
            student_ids = school_id.studentid_set.all()
        else:
            student_ids = None
        try:
            import_year = Year.objects.get(start=working_year.start-1)
            import_school_id = school.schoolid_set.get(district__year__id=import_year.id)
        except (Year.DoesNotExist, SchoolID.DoesNotExist):
            can_import = False
        else:
            can_import = True
        if student_ids:
            test_dates = TestDate.objects.filter(year=working_year)
            students = []
            for student_id in student_ids:
                tests = []
                for test_date in test_dates:
                    if test_date.test_set.filter(student_id=student_id):
                        tests.append(test_date.test_set.get(student_id=student_id))
                    else:
                        tests.append(None)
                students.append({'student_id': student_id, 'tests': tests})
        else:
            test_dates = None
            students = None
        return render_to_response('web/school.html', 
                                  {'school_id': school_id,
                                   'test_dates': test_dates,
                                   'students': students,
                                   'can_import': can_import},
                                  RequestContext(request))
    return HttpResponseRedirect(reverse(permission_denied))

@login_required
def student_view(request, id=None):
    from glml.utils import get_school, get_working_year
    school = get_school(request)
    working_year = get_working_year(request)
    if not school:
        add_message(request, u'Please select a school first.')
        return HttpResponseRedirect(reverse(home))
    try:
        school_id = school.schoolid_set.get(district__year__id=working_year.id)
    except SchoolID.DoesNotExist:
        return HttpResponseRedirect(reverse(school_view))
    if id:
        student_id = get_object_or_404(StudentID, id=id)
        student = student_id.student
        if not student.coached_by(request.user):
            return HttpResponseRedirect(reverse(permission_denied))
        request.session['school'] = student_id.school_id.school
        request.session['working_year'] = student_id.school_id.district.year
        if student_id.school_id.district.year != Year.get_current_year():
            student_id = None
    else:
        if working_year != Year.get_current_year():
            add_message(request, u'You may not add a student to a past season.')
            return HttpResponseRedirect(reverse(school_view))
        student_id = True
        student = None
    from glml.web.forms import StudentForm
    if request.method == 'POST':
        form = StudentForm(student_id, request.POST, instance=student)
        if form.is_valid():
            students = Student.objects.filter(first_name=form.cleaned_data['first_name'],
                                              last_name=form.cleaned_data['last_name'])
            if not students:
                student = form.save()
            else:
                student = students[0]  
            if student:
                if 'grade' in form.cleaned_data:
                    grade = form.cleaned_data['grade']
                    if not isinstance(student_id, StudentID):
                        student_id, created = StudentID.objects.get_or_create(student=student,
                                                                              school_id=school_id,
                                                                              grade=grade)
                    else:
                        student_id.grade = grade
                        student_id.save()
                return HttpResponseRedirect(reverse(school_view))
    else:
        form = StudentForm(student_id, instance=student)
    return render_to_response('web/student.html', 
                              {'student_id': student_id,
                               'student': student,
                               'form': form},
                              RequestContext(request))

@login_required
def delete_student(request, id):
    student_id = get_object_or_404(StudentID, id=id)
    student = student_id.student
    if student.coached_by(request.user):
        if student_id.school_id.district.year != Year.get_current_year():
            add_message(request, u'You may not delete a student from a past season.')
            return HttpResponseRedirect(reverse(school_view))
        if student_id.test_set.count():
            add_message(request, u'You may not delete a student who has already taken a test.')
            return HttpResponseRedirect(reverse(school_view))
        if request.method == 'POST':
            if 'yes' in request.POST:
                if not student.studentid_set.exclude(id=student_id.id):
                    student.delete()
                student_id.delete()
                return HttpResponseRedirect(reverse(school_view))
            else:
                return HttpResponseRedirect(reverse('edit_student', args=[id]))
        return render_to_response('web/delete_student.html',
                                  {'student_id': student_id},
                                  RequestContext(request))
    return HttpResponseRedirect(reverse(permission_denied))

@login_required
def import_students(request):
    if not allow(request):
        return HttpResponseRedirect(reverse(permission_denied))
    from glml.utils import get_school, get_working_year
    school = get_school(request)
    working_year = get_working_year(request)
    if not school:
        add_message(request, u'Please select a school first.')
        return HttpResponseRedirect(reverse(home))
    if working_year != Year.get_current_year():
        add_message(request, u'You may not import students into a past season.')
        return HttpResponseRedirect(reverse(school_view))
    try:
        import_year = Year.objects.get(start=working_year.start-1)
        import_school_id = school.schoolid_set.get(district__year__id=import_year.id)
    except (Year.DoesNotExist, SchoolID.DoesNotExist):
        add_message(request, u'There is no previous year to import students from.')
        return HttpResponseRedirect(reverse(school_view))
    try:
        school_id = school.schoolid_set.get(district__year__id=working_year.id)
    except SchoolID.DoesNotExist:
        return HttpResponseRedirect(reverse(school_view))
    students = [{'name': student_id.student.name(),
                 'grade': student_id.grade + 1,
                 'id': student_id.student.id} for student_id in import_school_id.studentid_set.exclude(grade=12)]
    if not students:
        add_message(request, u'There are no students from the previous year to import.')
        return HttpResponseRedirect(reverse(school_view))
    if request.method == 'POST':
        for student_id_number in request.POST:
            if request.POST[student_id_number] == 'on':
                student = get_object_or_404(Student, id=student_id_number)
                grade = int(request.POST["%s_grade" % student_id_number])
                if not StudentID.objects.filter(student=student,
                                                school_id=school_id):
                    StudentID.objects.create(student=student,
                                             grade=grade,
                                             school_id=school_id)
                else:
                    student_id = StudentID.objects.get(student=student,
                                                       school_id=school_id)
                    student_id.grade = grade
                    student_id.save()
        return HttpResponseRedirect(reverse(school_view))
    return render_to_response('web/import.html', 
                              {'students': students},
                              RequestContext(request))

@login_required
def upload(request):
    user = request.user
    if not user.is_superuser:
        return HttpResponseRedirect(reverse(permission_denied))
    from glml.web.forms import UploadForm
    if request.method == 'POST':
        form = UploadForm(request.POST, request.FILES)
        if form.is_valid():
            TestDate.import_test(form.cleaned_data)
            from glml.utils import date_string, email
            message = u'The results from the test on %s' % date_string(form.cleaned_data['date'])
            from django.conf import settings
            message += u' are now on the website (%s).' % settings.URL
            message += u'\n\n--\n%s\nGLML Administrator' % user.get_full_name()
            email(user, 'GLML', message)
            message = u'The upload was completed successfully'
            message += u' and all coaches have been notified via email.'
            add_message(request, message)
            return HttpResponseRedirect(reverse(home))
    else:
        form = UploadForm()
    from glml.utils import get_working_year
    working_year = get_working_year(request)
    if working_year == Year.get_current_year():
        test_dates = TestDate.objects.filter(year=working_year)
    else:
        test_dates = TestDate.objects.none()
    return render_to_response('web/upload.html', 
                              {'form': form,
                               'test_dates': test_dates},
                              RequestContext(request))

@login_required
def delete_tests(request, id):
    if not request.user.is_superuser:
        return HttpResponseRedirect(reverse(permission_denied))
    test_date = get_object_or_404(TestDate, id=id)
    from glml.utils import get_working_year
    working_year = get_working_year(request)
    if test_date.year != Year.get_current_year():
        add_message(request, u'You may not delete tests from a past season.')
        return HttpResponseRedirect(reverse(upload))
    if request.method == 'POST':
        if 'yes' in request.POST:
            test_date.delete()
            add_message(request, u'All tests from %s have been successfully deleted.' % test_date.date_string())
        return HttpResponseRedirect(reverse(upload))
    return render_to_response('web/delete_tests.html',
                              {'test_date': test_date},
                              RequestContext(request))

@login_required
def compare(request, id):
    test_date = get_object_or_404(TestDate, id=id)
    from glml.utils import get_school, get_working_year
    school = get_school(request)
    working_year = get_working_year(request)
    if working_year != test_date.year:
        return HttpResponseRedirect(reverse(school_view))
    try:
        school_id = school.schoolid_set.get(district__year__id=working_year.id)
    except SchoolID.DoesNotExist:
        return HttpResponseRedirect(reverse(school_view))
    tests = test_date.test_set.filter(student_id__school_id__id=school_id.id).order_by('student_id')
    tests = [test.compare_mapping() for test in tests]
    key = test_date.get_key().compare_mapping()
    numbers = key['questions'].keys()
    numbers.sort()
    return render_to_response('web/compare.html', 
                              {'test_date': test_date,
                               'numbers': numbers,
                               'key': key,
                               'tests': tests},
                              RequestContext(request))

@login_required
def create_scantron(request):
    user = request.user
    if not user.is_superuser:
        return HttpResponseRedirect(reverse(permission_denied))
    from glml.web.forms import ScantronForm
    from glml.utils import get_working_year
    working_year = get_working_year(request)
    current_year = Year.get_current_year()
    if working_year != current_year:
        request.session['working_year'] = current_year
        working_year = get_working_year(request)
        add_message(request, u'You may not create a Scantron for a past season.')
    if request.method == 'POST':
        form = ScantronForm(working_year, request.POST)
        if form.is_valid():
            test_date = form.cleaned_data['test_date']
            student_id = form.cleaned_data['student_id']
            test = Test.objects.create(test_date=test_date, student_id=student_id, score=0)
            i = 1
            while i < len(request.POST) - 1:
                q = Question.objects.create(number=i, test=test, answer=request.POST[unicode(i)])
                i += 1
            test.rescore()
            return HttpResponseRedirect(reverse(home))
        else:
            rows = [[(j, request.POST[unicode(j)]) for j in range(i, i + 6)] for i in range(1, 24, 6)]
    else:
        form = ScantronForm(working_year)
        rows = [[(j, '') for j in range(i, i + 6)] for i in range(1, 24, 6)]
    return render_to_response('web/create_scantron.html', 
                              {'form': form,
                               'rows': rows,
                               'choices': [''] + ANSWERS},
                              RequestContext(request))

def archive(request):
    import datetime
    import os
    import time
    from django.conf import settings
    pdfs = []
    for f in os.listdir(os.path.join(settings.MEDIA_ROOT, u'archive')):
        url = os.path.join(settings.MEDIA_URL, os.path.join(u'archive', f))
        date = datetime.date(*time.strptime(f, '%Y-%m-%d.pdf')[:3])
        pdfs.append((url, date))
    pdfs.sort(key=lambda x: x[1])
    hof = {}
    for test_date in TestDate.objects.all():
        key = test_date.get_key()
        tests = test_date.test_set.filter(score=key.score).exclude(id=key.id)
        for test in tests:
            student_id = test.student_id
            student = student_id.student.name()
            if student not in hof:
                hof[student] = [student_id.school_id.school.name, 0]
            hof[student][1] += 1
    hof = hof.items()
    hof = [[student] + school_count for student, school_count in hof]
    hof.sort(key=lambda x: (-x[-1], x[0]))
    return render_to_response('web/archive.html', 
                              {'pdfs': pdfs,
                               'hof': hof},
                              RequestContext(request))