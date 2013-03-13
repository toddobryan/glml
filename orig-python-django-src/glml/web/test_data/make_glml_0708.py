import os

FOLDER = os.getcwd() + '/glml/web/test_data'

FILENAME = "glml_0708.py"

def make_glml_0708(folder=FOLDER, filename=FILENAME):
    f = open(os.path.join(folder, filename), "w")
    from django.contrib.auth.models import User
    f.write("from django.contrib.auth.models import User")
    f.write("\n")
    for user in User.objects.exclude(username='tobryan1'):
        if user.first_name and user.last_name and user.email:
            username = user.username.replace('-', '_')
            f.write("%s, created = User.objects.get_or_create(first_name=\"%s\", last_name=\"%s\", email=\"%s\", username=\"%s\")" % (username, user.first_name, user.last_name, user.email, user.username))
            f.write("\n")
            f.write("%s.is_active = False" % username)
            f.write("\n")
            f.write("%s.set_password('temp123')" % username)
            f.write("\n")
            f.write("%s.save()" % username)
            f.write("\n")
    from glml.web.models import *
    f.write("from glml.web.models import *")
    f.write("\n")
    f.write("year2007, created = Year.objects.get_or_create(start=2007)")
    f.write("\n")
    for district in District.objects.exclude(id=ANSWER_STUDENT_ID):
        f.write("district%(id)s, created = District.objects.get_or_create(glml_id=\"%(id)s\", year=year2007)" % {"id": district.id})
        f.write("\n")
    f.write("district_key, created = District.objects.get_or_create(glml_id=\"%s\", year=year2007)" % str(ANSWER_STUDENT_ID)[0])
    f.write("\n")
    for school in School.objects.exclude(id=ANSWER_STUDENT_ID):
        school_id = str(school.id)
        if len(school_id) == 1:
            school_id = '0%s' % school_id
        school_district = 'district%s' % school.district.id 
        school_name = school.name.lower().replace(' ', '_').replace('.', '')
        f.write("%s, created = School.objects.get_or_create(name=\"%s\")" % (school_name, school.name))
        f.write("\n")
        f.write("%(school_name)s_id, created = SchoolID.objects.get_or_create(school=%(school_name)s, " % {"school_name": school_name})
        f.write("glml_id=\"%s\", district=%s)" % (school_id, school_district))
        f.write("\n")
        coaches = school.coaches.all()
        for coach in coaches:
            username = coach.username
            if username != 'glml':
                f.write("%s_id.coaches.add(%s)" % (school_name, username.replace('-', '_')))
                f.write("\n")
        if coaches:
            f.write("%s_id.save()" % school_name)
            f.write("\n")
    f.write("school_key, created = School.objects.get_or_create(name=\"KEY\")")
    f.write("\n")
    f.write("school_id_key, created = SchoolID.objects.get_or_create(glml_id=\"%s\", school=school_key, district=district_key)" % str(ANSWER_STUDENT_ID)[:2])
    f.write("\n")
    for student in Student.objects.exclude(id=ANSWER_STUDENT_ID):
        if not (student.last_name == 'Barringer' and student.grade == 9):
            f.write("student, created = Student.objects.get_or_create(last_name=\"%s\", first_name=\"%s\", middle_name=\"%s\", suffix=\"%s\")" % (student.last_name, student.first_name, student.middle_name, student.suffix))
            f.write("\n")
            f.write("student_id, created = StudentID.objects.get_or_create(student=student, school_id=%s_id, grade=%s)" % (student.school.name.lower().replace(' ', '_').replace('.', ''), student.grade))
            f.write("\n")
    f.write("student_key, created = Student.objects.get_or_create(last_name=\"KEY\", first_name=\"KEY\", middle_name=\"KEY\", suffix=\"KEY\")")
    f.write("\n")
    f.write("student_id_key, created = StudentID.objects.get_or_create(student=student_key, school_id=school_id_key, grade=%s)" % str(ANSWER_STUDENT_ID)[0])
    f.write("\n")
    f.write("st_francis.name = 'Saint Francis'")
    f.write("\n")
    f.write("st_francis.save()")
    f.write("\n")
    f.write("desales.name = 'DeSales'")
    f.write("\n")
    f.write("desales.save()")
    f.write("\n")
    f.write("for school_id in SchoolID.objects.all():")
    f.write("\n")
    f.write("    if not school_id.studentid_set.count():")
    f.write("\n")
    f.write("        school_id.school.delete()")
    f.write("\n")
    f.write("        school_id.delete()")
    f.write("\n")
    f.write("for user in User.objects.all():")
    f.write("\n")
    f.write("    if not user.schoolid_set.all():")
    f.write("\n")
    f.write("        user.delete()")
    f.close()