from django.conf import settings as SETTINGS
from django.core.urlresolvers import reverse

from glml.utils import get_school, get_working_year, SchoolSelectorForm, YearSelectorForm
from glml.web.models import Year
from glml.web.views import *

class Link(object):
    def __init__(self, url, title):
        self.url = url
        self.title = title

def glml_context_processor(request):
    user = request.user
    context = {'STATIC_PATH': SETTINGS.MEDIA_URL,
               'url_path': request.path}
    school = get_school(request)
    context['school'] = school
    if school:
        context['school_selector_form'] = SchoolSelectorForm(initial={'selected_school':
                                                                      school.id})
    else:
        context['school_selector_form'] = None
    context['disable_school_selector_form'] = True
    context['links'] = [
                        Link(reverse(home), 'Home'),
                        Link(reverse(archive), 'Archive')
                       ]
    if user.is_anonymous():
        context['links'].append(Link(reverse(login), 'Log In'))
    else:
        if user.is_superuser:
            context['links'] += [
                                 Link(reverse(send_email), 'Email Coaches'),
                                 Link('/glml/admin/', 'Admin Panel'),
                                 Link(reverse(upload), 'Upload Spreadsheet'),
                                 Link(reverse(create_scantron), 'Create Scantron')
                                ]
            if not school:
                context['school_selector_form'] = SchoolSelectorForm()
            context['disable_school_selector_form'] = False
        else:
            context['links'].append(Link(reverse(send_email), 'Email Administrators'))
        if school:
            context['links'].append(Link(reverse(school_view), '%s' % school))
        context['links'] += [Link(reverse(settings), 'Settings'),
                             Link(reverse(logout), 'Log Out')]
    return context

def year_context_processor(request):
    current_year = Year.get_current_year()
    working_year = get_working_year(request)
    year_selector_form = YearSelectorForm(initial={'selected_year':
                                                   working_year.id})
    context = {'year_selector_form': year_selector_form,
               'current_year': current_year,
               'working_year': working_year}
    return context