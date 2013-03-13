from django import forms

from glml.web.models import ANSWER_STUDENT_ID, ANSWER_STUDENT_NAME, School, SchoolID, StudentID, Year

def add_message(request, message):
    request.user.message_set.create(message=message)

def allow(request):
    user = request.user
    school = get_school(request)
    if school:
        coaches = school.coaches()
    else:
        coaches = []
    return user.is_superuser or user in coaches 

class YearSelectorForm(forms.Form):
    selected_year = forms.ModelChoiceField(label='Selected Year',
                                           empty_label=None,
                                           queryset=Year.objects.all(),
                                           widget=forms.Select(attrs={'onchange':
                                                                      'this.form.submit()'}))

def get_working_year(request):
    if 'working_year' not in request.session.keys():
        return Year.get_current_year()
    return request.session['working_year']

class SchoolSelectorForm(forms.Form):
    selected_school = forms.ModelChoiceField(label='Selected School',
                                             required=False,
                                             queryset=School.objects.exclude(name=ANSWER_STUDENT_NAME),
                                             widget=forms.Select(attrs={'onchange':
                                                                        'this.form.submit()'}))

def get_school(request):
    user = request.user
    if not user.is_anonymous():
        if 'school' in request.session.keys():
            return request.session['school']
        school_id = SchoolID.get_current_school_id(user)
        if school_id:
            return school_id.school
    return None

def set_up():
    from datetime import date
    today = date.today()
    if today.month > 6:
        Year.objects.get_or_create(start=today.year)
    else:
        Year.objects.get_or_create(end=today.year)
    StudentID.get_or_create_answer_student_id()

def email(user, subject, message, users=None):
    from_email = '%s <%s>' % (user.get_full_name(), user.email)
    if not users:
        from django.contrib.auth.models import User
        users = User.objects.filter(is_active=True).exclude(id=user.id)
        if not user.is_superuser:
            users = users.filter(is_superuser=True)
    recipient_list = ['%s <%s>' % (user.get_full_name(),
                                   user.email) for user in users if user.email]
    from django.conf import settings
    if settings.DEBUG:
        print u'From: %s\n' % from_email
        print u'To:'
        for recipient in recipient_list:
            print u'\t%s' % recipient
        print u'\n'
        print u'Subject: %s\n\n' % subject
        print u'Message:\n%s' % message
    else:
        from django.core.mail import send_mail
        send_mail(subject, message, from_email, recipient_list)

def date_string(date):
    if date.year >= 1900:
        date = date.strftime('%B %d, %Y').replace(' 0', ' ')
    return unicode(date)

class StudentWithScore(object):
    def __init__(self, student_id, score):
        self.student_id = student_id
        self.score = score
        
    def __cmp__(self, other):
        if self.score == other.score:
            return cmp(self.student_id.student, other.student_id.student)
        else:
            return cmp(other.score, self.score)
        
class Place(object):
    def __init__(self, place, students):
        self.place = place
        self.students = students

    def length(self):
        return len(self.students)

    @staticmethod
    def make_place_list(students):
        place_list = []
        start_index = 0
        end_index = 1
        place = 1
        while place <= 10 and end_index < len(students) and students[start_index].score > 0.0:
            while end_index < len(students) and students[start_index].score == students[end_index].score:
                end_index += 1
            place_students = students[start_index:end_index]
            place_list.append(Place(place, place_students))
            place += len(place_students)
            start_index = end_index
            end_index = start_index + 1
        return place_list