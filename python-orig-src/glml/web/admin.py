from django.contrib import admin

from glml.web.models import Year, District, School, SchoolID, Student, StudentID, TestDate, Test, Question

class Question_Inline(admin.StackedInline):
    model = Question

class TestOptions(admin.ModelAdmin):
    inlines = [Question_Inline]
    
class SchoolIDAdmin(admin.ModelAdmin):
    filter_horizontal = ('coaches',)

admin.site.register(School)
admin.site.register(SchoolID, SchoolIDAdmin)
admin.site.register(District)
admin.site.register(TestDate)
admin.site.register(StudentID)
admin.site.register(Student)
admin.site.register(Year)
admin.site.register(Test, TestOptions)
