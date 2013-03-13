from django.conf.urls.defaults import *

from glml.web.views import *

urlpatterns = patterns('',
    (r'^$', home),
    url(r'^(?P<year>\d{4})-(?P<month>\d{1,2})-(?P<day>\d{1,2})/$', home, name='home_by_date'),
    (r'^login/$', login),
    (r'^logout/$', logout),
    (r'^send_email/$', send_email),
    (r'^settings/$', settings),
    (r'^permission_denied/$', permission_denied),
    (r'^school/$', school_view),
    (r'^student/add/$', student_view),
    url(r'^student/edit/(?P<id>\d+)/$', student_view, name='edit_student'),
    (r'^student/delete/(?P<id>\d+)/$', delete_student),
    (r'^school/import$', import_students),
    (r'^upload/$', upload),
    (r'^delete_tests/(?P<id>\d+)/$', delete_tests),
    (r'^create_scantron/$', create_scantron),
    (r'^compare/(?P<id>\d+)/$', compare),
    (r'^archive/$', archive),
)