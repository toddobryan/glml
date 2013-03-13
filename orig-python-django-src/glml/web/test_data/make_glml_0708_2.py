from GLML.web.models import *

f = open('glml_0708_2.py', 'w')

f.write("import datetime\n\n")
f.write("from glml.web.models import *\n\n")

f.write("year = Year.objects.get(slug=\"0708\")\n\n")

test_dates = list(set(t.date for t in Test.objects.all()))
test_dates.sort()
for test_date in test_dates:
    f.write("TestDate.objects.create(date=%s)\n" % repr(test_date))

f.write("""
def make_test(first_name, last_name, test_date, answers_string):
    test_date = TestDate.objects.get(date=test_date)
    try:
        student_id = StudentID.objects.get(student__first_name=first_name,
                                           student__last_name=last_name,
                                           school_id__district__year__id=year.id)
    except:
        print "make_test(\\"%s\\", \\"%s\\", %s, \\"%s\\")?" % (first_name,
                                                          last_name,
                                                          test_date,
                                                          answers_string)
        return None
    test = Test.objects.create(test_date=test_date,
                               student_id=student_id,
                               score=0)
    i = 0
    while i < len(answers_string):
        Question.objects.create(number=i+1,
                                answer=answers_string[i],
                                test=test)
        i += 1
    test.rescore()
""")

def _answers_string(test):
    answers_string = ''
    for question in test.question_set.all():
        if question.answer:
            answers_string += question.answer
        else:
            answers_string += '_'
    return answers_string

for test in list(Test.objects.filter(student__id=ANSWER_STUDENT_ID)) + \
            list(Test.objects.exclude(student__id=ANSWER_STUDENT_ID)):
    f.write("\nmake_test(\"%s\", \"%s\", %s, \"%s\")" % (test.student.first_name,
                                                         test.student.last_name,
                                                         repr(test.date),
                                                         _answers_string(test)))

f.close()
