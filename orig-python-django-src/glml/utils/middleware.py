import urllib

from django.http import HttpResponseRedirect

from glml.utils import SchoolSelectorForm, YearSelectorForm

class YearMiddleware:
    def process_view(self, request, view_func, view_args, view_kwargs):
        if request.method == 'POST' and 'selected_year' in request.POST:
            form = YearSelectorForm(request.POST)
            if form.is_valid():
                request.session['working_year'] = form.cleaned_data['selected_year']
                return HttpResponseRedirect(urllib.quote(request.get_full_path()))

class SchoolMiddleware:
    def process_view(self, request, view_func, view_args, view_kwargs):
        if request.user.is_superuser and request.method == 'POST' and 'selected_school' in request.POST:
            form = SchoolSelectorForm(request.POST)
            if form.is_valid():
                request.session['school'] = form.cleaned_data['selected_school']
                return HttpResponseRedirect(urllib.quote(request.get_full_path()))