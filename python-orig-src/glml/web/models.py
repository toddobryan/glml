from django.db import models
from django.contrib.auth.models import User
        
ANSWER_STUDENT_ID = u'999999'
ANSWER_STUDENT_NAME = u'KEY'

class Year(models.Model):
    start = models.IntegerField(unique=True)
    end = models.IntegerField(unique=True, editable=False)
    slug = models.CharField(max_length=4, unique=True, editable=False)

    class Meta:
        ordering = ['-start']

    class Admin:
        pass

    def __unicode__(self):
        return '%s-%s' % (self.start, self.end)

    def save(self, **kwargs):
        if self.start:
            self.end = self.start + 1
        self.slug = '%s%s' % (unicode(self.start)[2:],
                              unicode(self.end)[2:])
        super(Year, self).save(**kwargs)

    def __cmp__(self, other):
        return cmp(self.start, other.start)

    @staticmethod
    def get_current_year():
        years = Year.objects.all()
        if years:
            return years[0]
        return None

class District(models.Model):
    glml_id = models.CharField(max_length=1)
    year = models.ForeignKey(Year)

    class Meta:
        unique_together = (('year', 'glml_id'), )
        ordering = ['year', 'glml_id']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s: %s' % (self.year.slug, self.glml_id)

    @staticmethod
    def get_or_create_answer_district(year=None):
        if not year:
            year = Year.get_current_year()
        return District.objects.get_or_create(glml_id=ANSWER_STUDENT_ID[0],
                                              year=year)

    def get_top_schools(self, test_date=None):
        mapping = {}
        for school_id in SchoolID.objects.filter(district=self):
            if test_date:
                total = 0.0
                for test in Test.objects.filter(student_id__school_id=school_id,
                                                test_date=test_date):
                    total += float(test.score)
                mapping[school_id] = total
            else:
                mapping[school_id] = school_id.get_cumulative_score()
        sorted_list = sorted(mapping.items(), key=lambda x: x[1], reverse=True)
        return [{'name': school_id.school.name,
                 'score': score,
                 'coaches': school_id.coaches_names()} for (school_id,
                                                            score) in sorted_list]

    def get_top_students(self, test_date=None):
        from glml.utils import Place, StudentWithScore
        final_list = []
        for grade in GRADES:
            if test_date:
                students = [StudentWithScore(test.student_id, float(test.score)) for test in Test.objects.filter(student_id__school_id__district=self,
                                                                                                                 test_date=test_date,
                                                                                                                 student_id__grade=grade)]
            else:
                students = [StudentWithScore(students_id,
                                             students_id.get_cumulative_score()) for students_id in StudentID.objects.filter(school_id__district=self,
                                                                                                                             grade=grade)]
            students.sort()
            final_list.append({'grade': grade, 'place_list': Place.make_place_list(students)})
        return final_list

class School(models.Model):
    name = models.CharField(max_length=50)

    class Meta:
        ordering = ['name']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s' % self.name

    @staticmethod
    def get_or_create_answer_school():
        return School.objects.get_or_create(name=ANSWER_STUDENT_NAME)

    def coaches(self):
        coaches = set()
        for school_id in self.schoolid_set.all():
            for coach in school_id.coaches.all():
                coaches.add(coach)
        return list(coaches)

class SchoolID(models.Model):
    glml_id = models.CharField(max_length=2)
    school = models.ForeignKey(School)
    district = models.ForeignKey(District)
    coaches = models.ManyToManyField(User)

    class Meta:
        unique_together = (('district', 'school'), ('district', 'glml_id'))
        ordering = ['district__year', 'school']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s: %s' % (self.district.year.slug, self.school)

    @staticmethod
    def get_or_create_answer_school_id(year=None):
        if not year:
            year = Year.get_current_year()
        district, created = District.get_or_create_answer_district(year)
        school, created = School.get_or_create_answer_school()
        school_ids = SchoolID.objects.filter(school=school, district=district)
        if school_ids:
            return school_ids[0], False
        return SchoolID.objects.create(glml_id=ANSWER_STUDENT_ID[:2],
                                       school=school,
                                       district=district), True

    def coaches_names(self):
        return [coach.get_full_name() for coach in self.coaches.all()]

    def coaches_str(self):
        ret = []
        for coach in self.coaches.all():
            email = coach.email
            if email:
                ret.append('%s %s - <a href="mailto:%s">%s</a>' % (coach.first_name,
                                                                   coach.last_name,
                                                                   email, email))
            else:
                ret.append('%s %s' % (coach.first_name, coach.last_name))
        return ret

    def coach_word(self):
        coach = u'Coach'
        if self.coaches.count() < 2:
            return coach 
        return u'%ses' % coach

    @staticmethod
    def get_current_school_id(user, year=None):
        if year == None:
            year = Year.get_current_year()
        school_ids = user.schoolid_set.filter(district__year__id=year.id)
        if school_ids:
            return school_ids[0]
        return None

    def get_valid_id(self, grade):
        student_ids = StudentID.objects.filter(school_id=self, grade=grade)
        used_ids = [int(student_id.glml_id[4:]) for student_id in student_ids]
        all_ids = range(1, 100)
        [all_ids.remove(id) for id in used_ids if id in all_ids]
        id = unicode(min(all_ids))
        if len(id) == 1:
            return u'0%s' % id
        return id

    def get_cumulative_score(self):
        total = 0.0
        for student_id in StudentID.objects.filter(school_id=self):
            total += student_id.get_cumulative_score()
        return total

class Student(models.Model):
    last_name = models.CharField(max_length=50)
    first_name = models.CharField(max_length=50)
    middle_name = models.CharField(max_length=50, blank=True)
    suffix = models.CharField(max_length=50, blank=True)

    class Meta:
        unique_together = (('last_name', 'first_name', 'middle_name'),)
        ordering = ['last_name', 'first_name', 'middle_name']

    class Admin:
        pass

    def __unicode__(self):
        return self.name()

    def __cmp__(self, other):
        return cmp(self.last_name, other.last_name) or \
               cmp(self.first_name, other.first_name) or \
               cmp(self.middle_name, other.middle_name)

    @staticmethod
    def get_or_create_answer_student():
        return Student.objects.get_or_create(last_name=ANSWER_STUDENT_NAME,
                                             first_name=ANSWER_STUDENT_NAME,
                                             middle_name=ANSWER_STUDENT_NAME,
                                             suffix=ANSWER_STUDENT_NAME)

    def coached_by(self, coach):
        for student_id in self.studentid_set.all():
            if coach in student_id.school_id.school.coaches():
                return True
        return coach.is_superuser

    def name(self):
        name = u'%s, %s' % (self.last_name, self.first_name)
        if self.middle_name:
            name = u'%s %s' % (name, self.middle_name)
        if self.suffix:
            name = u'%s %s' % (name, self.suffix)
        return name

GRADES = {}
for i in range(9, 13):
    GRADES[i] = i - 8

class StudentID(models.Model):
    glml_id = models.CharField(editable=False, max_length=6)
    student = models.ForeignKey(Student)
    school_id = models.ForeignKey(SchoolID)
    grade = models.IntegerField(choices=[(grade, grade) for grade in GRADES])

    class Meta:
        unique_together = (('school_id', 'student'), ('school_id', 'glml_id'))
        ordering = ['student']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s: %s (%s)' % (self.school_id.district.year.slug, self.student, self.glml_id)

    def save(self, **kwargs):
        if self.grade in GRADES.keys() and (not self.id or GRADES[self.grade] != int(self.glml_id[3])):
            self.glml_id = u'%s%s%s%s' % (self.school_id.district.glml_id,
                                          self.school_id.glml_id,
                                          GRADES[self.grade],
                                          self.school_id.get_valid_id(self.grade))
        if self.school_id.glml_id == ANSWER_STUDENT_ID[:2] and self.school_id.district.glml_id == ANSWER_STUDENT_ID[0]:
            self.glml_id = ANSWER_STUDENT_ID
        super(StudentID, self).save(**kwargs)

    @staticmethod
    def get_or_create_answer_student_id(year=None):
        if not year:
            year = Year.get_current_year()
        student, created = Student.get_or_create_answer_student()
        school_id, created = SchoolID.get_or_create_answer_school_id(year)
        return StudentID.objects.get_or_create(student=student,
                                               school_id=school_id,
                                               grade=int(ANSWER_STUDENT_ID[0]))

    def get_cumulative_score(self):
        total = 0.0
        for test in self.test_set.all():
            total += float(test.score)
        return total

class TestDate(models.Model):
    date = models.DateField(unique=True)
    year = models.ForeignKey(Year, editable=False)

    class Meta:
        ordering = ['date']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s: %s' % (self.year.slug, self.date.strftime('%m/%d/%y'))

    def save(self, **kwargs):
        self.year = Year.get_current_year() 
        super(TestDate, self).save(**kwargs)

    def date_string(self):
        from glml.utils import date_string
        return date_string(self.date)

    @staticmethod
    def import_test(cleaned_data):
        test_date = TestDate.objects.create(date=cleaned_data['date'])
        sheet = cleaned_data['excel_file']
        i = 0
        rows = []
        import re
        while i < sheet.nrows:
            row = [cell.value for cell in sheet.row(i)][:25]
            if row and re.match(r'\d{6}', unicode(row[0])[:6]):
                rows.append(row)
            i += 1
        for row in rows:
            student_id = StudentID.objects.get(glml_id=unicode(row[0])[:6], school_id__district__year__id=test_date.year.id)
            test = Test.objects.create(test_date=test_date, student_id=student_id, score=0)
            i = 1
            while i < len(row):
                q = Question.objects.create(number=i, test=test, answer=row[i])
                i += 1
        for test in test_date.test_set.all():
            test.rescore()

    def get_key(self):
        return self.test_set.get(student_id__glml_id=ANSWER_STUDENT_ID)

    def pdf(self):
        import os
        from django.conf import settings
        filename = os.path.join(u'archive', self.date.strftime('%Y-%m-%d') + u'.pdf')
        if os.path.exists(os.path.join(settings.MEDIA_ROOT, filename)):
            return settings.MEDIA_URL + filename
        else:
            return None

class Test(models.Model):
    test_date = models.ForeignKey(TestDate)
    student_id = models.ForeignKey(StudentID)
    score = models.DecimalField(editable=False, max_digits=4, decimal_places=2)

    class Meta:
        unique_together = (('test_date', 'student_id'), )
        ordering = ['-score', '-student_id__student']

    class Admin:
        pass

    def __unicode__(self):
        return u'%s %s (%s)' % (self.test_date,
                                unicode(self.student_id)[6:],
                                self.score)

    def compare_mapping(self):
        mapping = {}
        mapping['name'] = self.student_id.student.name()
        questions = {}
        for question in self.question_set.all():
            questions[question.number] = question.answer
        mapping['questions'] = questions
        return mapping 

    def rescore(self):
        score = 0.0
        key = self.test_date.get_key()
        answer_list = dict([(q.number, q.answer) for q in key.question_set.all()])
        question_list = self.question_set.all()
        for question in question_list:
            if question.number in answer_list:
                correct_answer = answer_list[question.number]
                if question.answer.upper() == correct_answer.upper():
                    #correct
                    if question.number <= 6:
                        score += 2
                    elif question.number <= 12:
                        score += 3
                    elif question.number <= 18:
                        score += 4
                    elif question.number <= 24:
                        score += 5
                elif question.answer == "":
                    # Score is untouched.
                    pass
                else:
                    #incorrect
                    if question.number <= 6:
                        score -= .5
                    elif question.number <= 12:
                        score -= .75
                    elif question.number <= 18:
                        score -= 1.00
                    elif question.number <= 24:
                        score -= 1.25
            else:
                pass
        self.score = unicode(score)
        self.save()
        return score

from string import uppercase
ANSWERS = list(uppercase[:5])

class Question(models.Model):
    number = models.IntegerField()
    answer = models.CharField(max_length=1,
                              choices=[(answer, answer) for answer in ANSWERS])
    test = models.ForeignKey(Test)

    class Meta:
        unique_together = (('test', 'number'),)
        ordering = ('number',)

    def __unicode__(self):
        answer = self.answer
        if not answer:
            answer = u'(blank)'
        return u'%s %s %s' % (self.test, self.number, answer)

    def save(self, **kwargs):
        rescore = bool(self.id)
        self.answer = self.answer.upper()
        if self.answer not in ANSWERS:
            self.answer = ''
        super(Question, self).save(**kwargs)
        if rescore:
            self.test.rescore()