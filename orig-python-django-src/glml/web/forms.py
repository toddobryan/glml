import re
import xlrd

from django import forms
from django.forms.extras.widgets import SelectDateWidget

from glml.web.models import ANSWERS, ANSWER_STUDENT_ID, GRADES, Student, StudentID, SchoolID, TestDate, Year

class LoginForm(forms.Form):
    username = forms.CharField()
    password = forms.CharField(widget=forms.PasswordInput())
    
    def clean(self):
        username = password = ''
        if 'username' in self.cleaned_data:
            username = self.cleaned_data['username']
        if 'password' in self.cleaned_data:
            password = self.cleaned_data['password']
        from django.contrib.auth import authenticate 
        user = authenticate(username=username, password=password)
        if not user:
            raise forms.ValidationError(u'Please enter a correct username and password. Note that both fields are case-sensitive.')
        elif not user.is_active:
            raise forms.ValidationError(u'This account is inactive.')
        else:
            self.cleaned_data['user'] = user
            return self.cleaned_data

class CoachMultipleChoiceField(forms.ModelMultipleChoiceField):
    def label_from_instance(self, coach):
        label = u'%s, %s' % (coach.last_name, coach.first_name)
        school_id = SchoolID.get_current_school_id(coach)
        if school_id:
            label += u' (%s)' % school_id.school
        return label

class SendEmailForm(forms.Form):
    def __init__(self, user, *args, **kwargs):
        super(SendEmailForm, self).__init__(*args, **kwargs)
        message = u'\n\n\n--\n%s' % user.get_full_name()
        if user.is_superuser:
            from django.contrib.auth.models import User
            self.fields['recipients'] = CoachMultipleChoiceField(queryset=User.objects.filter(is_active=True).exclude(id=user.id).order_by('last_name'),
                                                                 help_text=u'Hold down "Control", or "Command" on a Mac, to select more than one.')
            message += u'\nGLML Administrator'
        school_id = SchoolID.get_current_school_id(user)
        if school_id:
            message += u'\nGLML Coach for %s' % (school_id.school.name)
        self.fields['subject'] = forms.CharField(initial=u'GLML')
        self.fields['message'] = forms.CharField(initial=message, widget=forms.Textarea)

class ChangePasswordForm(forms.Form):
    current_password = forms.CharField(widget=forms.PasswordInput())
    new_password = forms.CharField(widget=forms.PasswordInput())
    verify_password = forms.CharField(widget=forms.PasswordInput())

    def clean(self):
        error = None
        new_password = verify_password = ''
        if 'new_password' in self.cleaned_data:
            new_password = self.cleaned_data['new_password']
        if 'verify_password' in self.cleaned_data:
            verify_password = self.cleaned_data['verify_password']
        if new_password != verify_password:
            error = u'The new password does not match the verification.'
        elif len(new_password) < 6:
            error = u'The new password must be at least 6 characters long.'
        if error:
            raise forms.ValidationError(error)
        else:
            return self.cleaned_data

    def clean_current_password(self):
        current_password = self.cleaned_data['current_password']
        if not self.user.check_password(current_password):
            raise forms.ValidationError(u'The password is incorrect!')
        else:
            return current_password

class ChangeEmailForm(forms.Form):
    def __init__(self, user, *args, **kwargs):
        super(ChangeEmailForm, self).__init__(*args, **kwargs)
        self.fields['email'] = forms.EmailField(initial=user.email)
        self.fields['email'].widget.attrs.update({'size': 40})

class StudentForm(forms.ModelForm):
    def __init__(self, student_id, *args, **kwargs):
        super(StudentForm, self).__init__(*args, **kwargs)
        if student_id:
            self.fields['grade'] = forms.IntegerField(widget=forms.Select(choices=[(grade, grade) for grade in GRADES]))
            if isinstance(student_id, StudentID):
                self.fields['grade'].initial = student_id.grade 
    
    class Meta:
        model = Student

class UploadForm(forms.Form):
    excel_file = forms.FileField(label='Path to spreadsheet')
    date = forms.DateField(label='Date when the test was taken')

    def __init__(self, *args, **kwargs):
        super(UploadForm, self).__init__(*args, **kwargs)
        year = Year.get_current_year()
        self.years = (year.start, year.end)
        self.fields['date'].widget = SelectDateWidget(years=self.years)

    def clean_excel_file(self):
        errors = []
        try:
            file_contents = self.cleaned_data['excel_file'].read()
            book = xlrd.open_workbook(file_contents=file_contents)
            sheet = book.sheet_by_index(0)
            glml_ids = sheet.col_values(0)
            if ANSWER_STUDENT_ID not in glml_ids:
                errors.append(u'The key (%s) was not found.' % ANSWER_STUDENT_ID) 
            used_glml_ids = {}
            used_glml_ids_keys = set()
            missing_students = []
            i = 0
            while i < len(glml_ids):
                glml_id = unicode(glml_ids[i])[:6]
                if re.match(r'\d{6}', glml_id):
                    try:
                        student_id = StudentID.objects.get(glml_id=glml_id,
                                                           school_id__district__year__id=Year.get_current_year().id)
                    except StudentID.DoesNotExist:
                        missing_students.append({u'glml_id': glml_id, u'row': i+1})
                elif re.match(r'\d', glml_id):
                    errors.append(u'The student ID %s on row %s is an invalid ID.' % (glml_id, i+1))
                if glml_id:
                    if glml_id in used_glml_ids:
                        used_glml_ids[glml_id].append(unicode(i+1))
                        used_glml_ids_keys.add(glml_id)
                    else:
                        used_glml_ids[glml_id] = [unicode(i+1)]
                i += 1
            used_glml_ids_keys = list(used_glml_ids_keys)
            for used_glml_id in used_glml_ids_keys:
                rows = used_glml_ids[used_glml_id]
                rows.insert(-1, u'and')
                if len(rows) == 3:
                    rows = u' '.join(rows)
                else:
                    rows = u', '.join(rows)
                    rows = rows.replace(u'and,', u'and')
                errors.append(u'The student ID %s was found on rows %s.' % (used_glml_id, rows)) 
            if missing_students:
                if len(missing_students) == 1:
                    verb = u'  was'
                    noun = u'the student'
                else:
                    verb = u's were'
                    noun = u'them'
                errors.insert(0, u'The above student ID%s not found in the database.' % verb)
                errors.insert(1, u'Please add %(noun)s in the database or correct %(noun)s in the spreadsheet.' % {'noun': noun})
                self.missing_students = missing_students
        except xlrd.XLRDError:
            errors = [u"That's an invalid spreadsheet. Please upload the correct file."]
        if errors:
            raise forms.ValidationError(errors)
        else:
            return sheet
        
    def clean_date(self):
        date = self.cleaned_data['date']
        if date.year not in self.years:
            raise forms.ValidationError(u'You may not upload a test to a past season.')
        elif TestDate.objects.filter(date=date):
            raise forms.ValidationError(u"You've already uploaded a file for this date.")
        else:
            return self.cleaned_data['date']

class TestDateChoiceField(forms.ModelChoiceField):
    def label_from_instance(self, test_date):
        return test_date.date_string()

class ScantronForm(forms.Form):
    def __init__(self, year, *args, **kwargs):
        super(ScantronForm, self).__init__(*args, **kwargs)
        self.fields['student_id'] = forms.CharField(label=u"Student's ID")
        self.fields['test_date'] = TestDateChoiceField(label=u'Date when the test was taken',
                                                       queryset=TestDate.objects.filter(year=year))

    def clean(self):
        if not self.errors:
            try:
                test_date = self.cleaned_data['test_date']
                student_id = StudentID.objects.get(glml_id=self.cleaned_data['student_id'],
                                                   school_id__district__year__id=test_date.year.id)
                if test_date.test_set.filter(student_id=student_id):
                    raise forms.ValidationError(u'A scantron for this student on this test already exists.')
                else:
                    self.cleaned_data['student_id'] = student_id
                    return self.cleaned_data
            except StudentID.DoesNotExist:
                raise forms.ValidationError(u'The student was not found in the database.')