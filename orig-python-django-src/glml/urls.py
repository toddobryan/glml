from django.conf import settings
from django.conf.urls.defaults import *
from django.contrib import admin

from glml.settings import URL_PREFIX

admin.autodiscover()

urlpatterns = patterns('',
    (r'^%sadmin/(.*)' % URL_PREFIX, admin.site.root),
    (r'^%s' % URL_PREFIX, include('glml.web.urls')),
)

if settings.DEBUG:
    urlpatterns += patterns('',
        (r'^%sstatic/(.*)$' % URL_PREFIX, 'django.views.static.serve', {'document_root': settings.MEDIA_ROOT}),
    )
