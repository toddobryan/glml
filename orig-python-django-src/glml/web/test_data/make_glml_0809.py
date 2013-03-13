import os
import re

FOLDER = os.getcwd() + '/glml/web/test_data'

FILENAME = "glml_0809.py"

def make_glml_0809(folder=FOLDER, filename=FILENAME):
    f1 = open(os.path.join(folder, 'schools'))
    f1 = f1.read()
    f1 = f1.split('\n')
    f2 = open(os.path.join(folder, 'coaches'))
    f2 = f2.read()
    f2 = f2.split('\n')
    f = open(os.path.join(folder, filename), "w")
    f.write("from glml.web.models import *")
    f.write("\n")
    f.write("year2008, created = Year.objects.get_or_create(start=2008)")
    f.write("\n")
    from glml.web.models import ANSWER_STUDENT_ID
    f.write("district_key, created = District.objects.get_or_create(glml_id=\"%s\", year=year2008)" % str(ANSWER_STUDENT_ID)[0])
    f.write("\n")
    f.write("school_key, created = School.objects.get_or_create(name=\"KEY\")")
    f.write("\n")
    f.write("school_id_key, created = SchoolID.objects.get_or_create(glml_id=\"%s\", school=school_key, district=district_key)" % str(ANSWER_STUDENT_ID)[:2])
    f.write("\n")
    for line in f1:
        if line:
            if re.match(r'\d', line):
                i = 1
                f.write("district, created = District.objects.get_or_create(glml_id=\"%s\", year=year2008)" % line)
                f.write("\n")
            else:
                f.write("school, created = School.objects.get_or_create(name=\"%s\")" % line)
                f.write("\n")
                temp = str(i)
                if len(temp) == 1:
                    temp = '0%s' % temp
                f.write("%s_id, created = SchoolID.objects.get_or_create(glml_id=\"%s\", school=school, district=district)" % (line.lower().replace(' ', '_').replace('.', ''), temp))
                f.write("\n")
                i += 1
    f.write("from django.contrib.auth.models import User")
    f.write("\n")
    for line in f2:
        if line:
            if '\t' in line:
                x = line.split('\t')
                name = x[0].split(' ')
                first_name = name[0]
                last_name = name[1]
                username = (first_name[0] + last_name).lower()
                email = x[1]
                var = username.replace('-', '_')
                f.write("%s, created = User.objects.get_or_create(username=\"%s\")"  % (var, username))
                f.write("\n")
                f.write("%s.first_name=\"%s\"" % (var, first_name))
                f.write("\n")
                f.write("%s.last_name=\"%s\"" % (var, last_name))
                f.write("\n")
                f.write("%s.email=\"%s\"" % (var, email))
                f.write("\n")
                f.write("%s.is_active = True" % var)
                f.write("\n")
                f.write("%s.set_password('temp123')" % var)
                f.write("\n")
                f.write("%s.save()"  % var)
                f.write("\n")
                if school:
                    f.write("%s_id.coaches.add(%s)" % (school, var))
                    f.write("\n")
                    f.write("%s_id.save()" % school)
                    f.write("\n")
                else:
                    f.write("%s.is_superuser = True" % var)
                    f.write("\n")
                    f.write("%s.is_staff = True" % var)
                    f.write("\n")
                    f.write("%s.save()" % var)
                    f.write("\n")
            else:
                if line == "Administrators":
                    school = None
                else:
                    school = line.lower().replace(' ', '_').replace('.', '')