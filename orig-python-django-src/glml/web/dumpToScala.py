from xml.dom.minidom import *

from glml.web.models import *

def defn(cls, lst, setup=""):
    line1 = '  def load%ss(debug: Boolean = false) {\n' % cls
    line2 = '    if (debug) println("Adding %s models to the database...")\n' % cls
    line3 = '    val %ss = List(\n' % cls.lower()
    linex = '\n    )\n'
    liney = '    %ss.foreach(DataStore.pm.makePersistent(_))\n  }\n' % cls.lower()
    return line1 + line2 + setup + line3 + lst + linex + liney
        
def asString(s):
    return '"' + s.replace('"', '\\"') + '"'
    
def asOption(s):
    if s != u'':
        return 'Some(%s)' % asString(s)
    else:
        return 'None'
        
def asDate(d):
    return 'new LocalDate(%d, %d, %d)' % (d.year, d.month, d.day)
    
def asDateTime(dt):
    return 'new DateTime(%d, %d, %d, %d, %d, %d)' % (dt.year, dt.month, dt.day, dt.hour, dt.minute, dt.second)

def asDateTimeOption(dt):
    if dt is None:
         return 'None'
    else:
         return 'Some(%s)' % asDateTime(dt)
    
def asBoolean(b):
    if b:
        return 'true'
    else:
        return 'false'
    
def yearMap():
    # returns Scala code to create a map of years from start to the object
    return '    val yearMap: Map[Int, Year] = DataStore.pm.query[Year].executeList().map((y: Year) => y.start -> y).toMap\n'

def schoolMap():
    # creates a map from school names to school objects
    return '    val schoolMap: Map[String, School] = DataStore.pm.query[School].executeList().map((s: School) => s.name -> s).toMap\n'    

def schoolIdMap():
    # creates a map from (year.start, district id, school id) to SchoolId objects
    return '    val schoolIdMap: Map[(Int, String, String), SchoolId] = DataStore.pm.query[SchoolId].executeList().map((s: SchoolId) => ((s.district.year.start, s.district.glmlId, s.glmlId) -> s)).toMap\n'

def districtMap():
    # creates a map from (glmlId, year.start) to District objects
    return '    val districtMap: Map[(String, Int), District] = DataStore.pm.query[District].executeList().map((d: District) => ((d.glmlId, d.year.start) -> d)).toMap\n'
    
def studentMap():
    # creates a map from (last, first, middle) to Student
    return '    val studentMap: Map[(String, String, Option[String]), Student] = DataStore.pm.query[Student].executeList().map((s: Student) => ((s.lastName, s.firstName, s.middleName) -> s)).toMap\n'
    
def studentIdMap():
    # creates a map from (year.start, glmlId) to StudentId
    return '    val studentIdMap: Map[(Int, String), StudentId] = DataStore.pm.query[StudentId].executeList().map((s: StudentId) => ((s.schoolId.district.year.start, s.glmlId) -> s)).toMap\n'
    
def testDateMap():
    # creates a map from date to TestDate
    return '    val testDateMap: Map[String, TestDate] = DataStore.pm.query[TestDate].executeList().map((td: TestDate) => td.date.toString -> td).toMap\n'
    
def testMap():
    # creates a map from (date, student_id) to test
    return '    val testMap: Map[(String, String), Test] = DataStore.pm.query[Test].executeList().map((t: Test) => ((t.testDate.date.toString, t.studentId.glmlId) -> t)).toMap\n'
    
def usernamesToSetUser():
    return '''    def usernamesToSetUser(names: List[String]): Set[User] = {
      val cand = QUser.candidate
      names.map((n: String) => DataStore.pm.query[User].filter(cand.username.eq(n)).executeOption().get).toSet
    }\n'''
    
def makeData():
    doc = Document()
    root = doc.createElement('data')
    doc.appendChild(root)
    root.appendChild(years(doc))
    root.appendChild(districts(doc))
    root.appendChild(schools(doc))
    root.appendChild(students(doc))
    root.appendChild(testDates(doc))
    root.appendChild(coaches(doc))
    root.appendChild(schoolIds(doc))
    root.appendChild(studentIds(doc))
    root.appendChild(tests(doc))
    dataFile = open('../test/resources/postgres-data.xml', 'w')
    doc.writexml(dataFile, indent='', addindent='  ', newl='\n', encoding='UTF-8')
    dataFile.close()
    
def addChildWithText(doc, parent, elemName, text):
    elem = doc.createElement(elemName)
    elem.appendChild(doc.createTextNode(text))
    parent.appendChild(elem)

def years(doc):
    top = doc.createElement('years')
    for yr in Year.objects.all():
        addChildWithText(doc, top, 'year', str(yr.start))
    return top
        
def districts(doc):
    top = doc.createElement('districts')
    for d in District.objects.all():
        dElem = doc.createElement('district')
        addChildWithText(doc, dElem, 'glmlId', d.glml_id)
        addChildWithText(doc, dElem, 'year', str(d.year.start))
        top.appendChild(dElem)
    return top

def schools(doc):
    top = doc.createElement('schools')
    for s in School.objects.all():
        addChildWithText(doc, top, 'school', s.name)
    return top
    
def studentElement(doc, student):
    sElem = doc.createElement('student')
    addChildWithText(doc, sElem, 'last', student.last_name)
    addChildWithText(doc, sElem, 'first', student.first_name)
    if student.middle_name:
	addChildWithText(doc, sElem, 'middle', student.middle_name)
    if student.suffix:
	addChildWithText(doc, sElem, 'suffix', student.suffix)
    return sElem
    
def students(doc):
    top = doc.createElement('students')
    for s in Student.objects.all():
        sElem = studentElement(doc, s)
        top.appendChild(sElem)
    return top
    
def testDates(doc):
    top = doc.createElement('testDates')
    for td in TestDate.objects.all():
        tdElem = doc.createElement('testDate')
        addChildWithText(doc, tdElem, 'date', str(td.date))
        addChildWithText(doc, tdElem, 'year', str(td.year.start))
        top.appendChild(tdElem)
    return top
    
def coaches(doc):
    top = doc.createElement('coaches')
    for c in User.objects.all():
        cElem = doc.createElement('coach')
        addChildWithText(doc, cElem, 'username', c.username)
        addChildWithText(doc, cElem, 'first', c.first_name)
        addChildWithText(doc, cElem, 'last', c.last_name)
        addChildWithText(doc, cElem, 'isActive', asBoolean(c.is_active))
        addChildWithText(doc, cElem, 'isSuperUser', asBoolean(c.is_superuser))
        addChildWithText(doc, cElem, 'dateJoined', str(c.date_joined))
        if c.last_login:
            addChildWithText(doc, cElem, 'lastLogin', str(c.last_login))
        addChildWithText(doc, cElem, 'email', c.email)
        top.appendChild(cElem)
    return top
        
def schoolIds(doc):
    top = doc.createElement('schoolIds')
    for id in SchoolID.objects.all():
        idElem = doc.createElement('schoolId')
        addChildWithText(doc, idElem, 'glmlId', id.glml_id)
        addChildWithText(doc, idElem, 'school', id.school.name)
        addChildWithText(doc, idElem, 'district', id.district.glml_id)
        addChildWithText(doc, idElem, 'year', str(id.district.year.start))
        addChildWithText(doc, idElem, 'coaches', ','.join([c.username for c in id.coaches.all()]))
        top.appendChild(idElem)
    return top

def studentIds(doc):
    top = doc.createElement('studentIds')
    for id in StudentID.objects.all():
        idElem = doc.createElement('studentId')
        addChildWithText(doc, idElem, 'glmlId', id.glml_id)
        idElem.appendChild(studentElement(doc, id.student))
        schoolElem = doc.createElement('schoolId')
        addChildWithText(doc, schoolElem, 'year', str(id.school_id.district.year.start))
        addChildWithText(doc, schoolElem, 'glmlId', id.school_id.glml_id)
        addChildWithText(doc, schoolElem, 'district', id.school_id.district.glml_id)
        idElem.appendChild(schoolElem)
        addChildWithText(doc, idElem, 'grade', str(id.grade))
        top.appendChild(idElem)
    return top
    
def answers(test):
    qs_by_num = {}
    for q in test.question_set.all():
        qs_by_num[q.number] = q.answer or ' '
    answers = ''.join([qs_by_num.get(i + 1, ' ') for i in range(24)])
    return answers
    
def tests(doc):
    top = doc.createElement('tests')
    for t in Test.objects.all():
        tElem = doc.createElement('test')
        addChildWithText(doc, tElem, 'testDate', str(t.test_date.date))
        addChildWithText(doc, tElem, 'year', str(t.student_id.school_id.district.year.start))
        addChildWithText(doc, tElem, 'studentGlmlId', t.student_id.glml_id)
        addChildWithText(doc, tElem, 'score', '%.2f' % t.score)
        addChildWithText(doc, tElem, 'answers', answers(t))
        top.appendChild(tElem)
    return top

#questionTop = '''  def addQuestions(test: Test, answers: String) {
    #(1 to 24).foreach { (i: Int) =>
      #val ans = answers.substring(i - 1, i)
      #val ansToSave = if ("ABCDE".indexOf(ans) > -1) ans else ""
      #val q = new Question(i, ansToSave, test)
      #DataStore.pm.makePersistent(q)
    #}
  #}
  
  #def loadQuestions(debug: Boolean = false) {
    #if (debug) println("Adding Question models to the database...")
#'''    

#def questions():
    #top = doc.createElement('questions')
    #for t in Test.objects.all():
        #qs_by_num = {}
        #for q in t.question_set.all():
            #qs_by_num[q.number] = q.answer or ' '
        #answers = ''.join([qs_by_num.get(i + 1, ' ') for i in range(24)])
        #tests.append('    addQuestions(testMap(%s, %s), %s)' % (asString(str(t.test_date.date)), asString(t.student_id.glml_id), asString(answers)))
    #return questionTop + testMap() + '\n'.join(tests) + '\n  }'
    
#header = '''import org.joda.time.{LocalDate, DateTime}
#import scalajdo.DataStore

#import models.auth.{User, QUser}
#import models._

#object LoadRealData {
  #def load(debug: Boolean = true) {
    #loadYears(debug)
    #loadDistricts(debug)
    #loadSchools(debug)
    #loadStudents(debug)
    #loadTestDates(debug)
    #loadCoachs(debug)
    #loadSchoolIds(debug)
    #loadStudentIds(debug)
    #loadTests(debug)
    #loadQuestions(debug)
  #}
    
#'''

#def writeFile():
    #out = open('../test/LoadRealData.scala', 'w')
    #out.write(header)
    #out.write(years() + '\n')
    #out.write(districts() + '\n')
    #out.write(schools() + '\n')
    #out.write(students() + '\n')
    #out.write(testDates() + '\n')
    #out.write(coaches() + '\n')
    #out.write(schoolIds() + '\n')
    #out.write(studentIds() + '\n')
    #out.write(tests() + '\n')
    #out.write(questions() + '\n')
    #out.write('}')
    #out.close()
