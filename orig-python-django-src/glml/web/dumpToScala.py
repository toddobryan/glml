from glml.web.models import *

def defn(cls, lst, setup=""):
    line1 = '  def load%ss(debug: Boolean = false) {\n' % cls
    line2 = '    if (debug) println("Adding %s models to the database...")\n' % cls
    line3 = '    val %ss = List(\n' % cls.lower()
    linex = '\n    )\n'
    liney = '    %ss.foreach(DataStore.pm.makePersistent(_))\n  }\n' % cls.lower()
    return line1 + line2 + setup + line3 + lst + linex + liney
    
def asOption(s):
    if s != u'':
        return 'Some("%s")' % s
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
    
def years():
    objs = ["      new Year(%d)" % y.start for y in Year.objects.all()]
    return defn("Year", ",\n".join(objs))

def yearMap():
    # returns Scala code to create a map of years from start to the object
    return '    val yearMap: Map[Int, Year] = DataStore.pm.query[Year].executeList().map((y: Year) => y.start -> y).toMap\n'

def schoolMap():
    # creates a map from school names to school objects
    return '    val schoolMap: Map[String, School] = DataStore.pm.query[School].executeList().map((s: School) => s.name -> s).toMap\n'    

def schoolIdMap():
    # creates a map from (year.start, district id, school id) to SchoolId objects
    return '    val schoolIdMap: Map[(Int, String, String), SchoolId] = DataStore.pm.query[SchoolId].executeList().map((s: SchoolId) => ((s.district.year.start, s.district.glmlId, s.glmlId) -> s).toMap\n'

def districtMap():
    # creates a map from (glmlId, year.start) to District objects
    return '    val districtMap: Map[(String, Int), District] = DataStore.pm.query[District].executeList().map((d: District) => ((d.glmlId, d.year.start) -> d)).toMap\n'
    
def studentMap():
    # creates a map from (last, first, middle) to Student
    return '    val studentMap: Map[(String, String, Option[String]), Student] = DataStore.pm.query[Student].executeList().map((s: Student) => ((s.last, s.first, s.middle) -> s).toMap\n'
    
def studentIdMap():
    # creates a map from (year.start, glmlId) to StudentId
    return '    val studentIdMap: Map[(Int, String), StudentId] = DataStore.pm.query[StudentId].executeList().map((s: StudentId) => ((s.schoolId.district.year.start, s.glmlId) -> s).toMap\n'
    
def usernamesToSetUser():
    return '''    def usernamesToSetUser(names: List[String]): Set[User] = {
      val cand = QUser.candidate
      names.map((n: String) => DataStore.pm.query[User].filter(cand.username.eq(n)).executeOption().get).toSet
    }\n'''

def districts():
    objs = ['      new District("%s", yearMap(%d))' % (d.glml_id, d.year.start) for d in District.objects.all()]
    return defn('District', ',\n'.join(objs), yearMap())

def schools():
    objs = ['      new School("%s")' % s.name for s in School.objects.all()]
    return defn('School', ',\n'.join(objs))
    
def students():
    objs = ['      new Student("%s", "%s", %s, %s)' % 
             (s.last_name, s.first_name, asOption(s.middle_name), asOption(s.suffix)) for s in Student.objects.all()]
    return defn('Student', ',\n'.join(objs))
    
def testDates():
    objs = ['      new TestDate(%s, yearMap(%d))' %
             (asDate(td.date), td.year.start) for td in TestDate.objects.all()]
    return defn('TestDate', ',\n'.join(objs), yearMap())
    
def coaches():
    objs = ['      new User(username="%s", first="%s", last="%s", isActive=%s, isSuperUser=%s, dateJoined=%s, lastLogin=%s, email="%s")' % 
                           (c.username, c.first_name, c.last_name, asBoolean(c.is_active), asBoolean(c.is_superuser),
                            asDateTime(c.date_joined), asDateTimeOption(c.last_login), c.email) for 
                           c in User.objects.all()]
    return defn('Coach', ',\n'.join(objs))
    
    
def schoolIds():
    objs = ['      new SchoolId(%s, schoolMap("%s"), districtMap(("%s", %d)), usernamesToSetUser(List(%s)))' %
            (id.glml_id, id.school.name, id.district.glml_id, id.district.year.start, 
            ', '.join(['"%s"' % c.username for c in id.coaches.all()])) for
            id in SchoolID.objects.all()]
    return defn('SchoolId', ',\n'.join(objs), usernamesToSetUser() + schoolMap() + districtMap())

def studentIds():
    objs = ['      new StudentId("%s", studentMap(("%s", "%s", %s)), schoolIdMap((%d, "%s", "%s")), %d)' %
            (s.glml_id, s.student.last_name, s.student.first_name, asOption(s.student.middle_name),
             s.school_id.district.year.start, s.school_id.glml_id, s.school_id.district.glml_id, s.grade) for
            s in StudentID.objects.all()]
    return defn('StudentId', ',\n'.join(objs), studentMap() + schoolIdMap())
    
#def tests():
#    objs = ['     new Test(%s, studentIdMap(%d, "%s"), %d)' %
            
    
imports = '''
import org.joda.time.{LocalDate, DateTime}
'''