from django import template

register = template.Library()

@register.filter
def wtf(mapping):
    glml_id = mapping['glml_id']
    from glml.web.models import ANSWER_STUDENT_ID, ANSWER_STUDENT_NAME, District, GRADES, SchoolID, Year 
    year = Year.get_current_year()
    try:
        district = District.objects.get(glml_id=glml_id[0], year=year)
        try:
            school = SchoolID.objects.get(glml_id=glml_id[1:3], district=district).school.name
            if school == ANSWER_STUDENT_NAME:
                assert False
        except SchoolID.DoesNotExist:
            school = u'?'
        district = district.glml_id
        if district == ANSWER_STUDENT_ID[0]:
            assert False
    except District.DoesNotExist:
        district = u'?'
    try:
        grade = dict(zip(GRADES.values(), GRADES.keys()))[int(glml_id[3])]
    except:
        grade = u'?'
    if grade == 11:
        grade = u'n %s' % grade
    else:
        grade = u' %s' % grade
    return u'%s, row %s (seems like a%sth grader at %s in district %s)' % (glml_id,
                                                                           mapping['row'],
                                                                           grade,
                                                                           school,
                                                                           district)

@register.filter
def hash(dictionary, key):
    return dictionary[key]

@register.filter
def date_string(date):
    from glml.utils import date_string
    return date_string(date)
