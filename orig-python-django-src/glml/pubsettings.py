import os

DEBUG = True
TEMPLATE_DEBUG = DEBUG

ADMINS = (
    ("Todd O'Bryan", 'toddobryan@gmail.com'),
)

MANAGERS = ADMINS

#currentuser = os.environ['USER']

DATABASE_ENGINE = 'postgresql_psycopg2'           # 'postgresql', 'mysql', 'sqlite3' or 'ado_mssql'.
#DATABASE_NAME = 'glml_' + currentuser             # Or path to database file if using sqlite3.
#DATABASE_USER = currentuser             # Not used with sqlite3.
#DATABASE_PASSWORD = 'glml_' + currentuser         # Not used with sqlite3.
DATABASE_HOST = ''             # Set to empty string for localhost. Not used with sqlite3.
DATABASE_PORT = ''             # Set to empty string for default. Not used with sqlite3.

# Local time zone for this installation. All choices can be found here:
# http://www.postgresql.org/docs/8.1/static/datetime-keywords.html#DATETIME-TIMEZONE-SET-TABLE
TIME_ZONE = 'America/Kentucky/Louisville'

# Language code for this installation. All choices can be found here:
# http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes
# http://blogs.law.harvard.edu/tech/stories/storyReader$15
LANGUAGE_CODE = 'en-us'

SITE_ID = 1

# If you set this to False, Django will make some optimizations so as not
# to load the internationalization machinery.
USE_I18N = True

# URL prefix for admin media -- CSS, JavaScript and images. Make sure to use a
# trailing slash.
# Examples: "http://foo.com/media/", "/media/".
ADMIN_MEDIA_PREFIX = '/media/'

# Make this unique, and don't share it with anybody.
SECRET_KEY = '6p=5tfctvaa_*#4y2!e%uljful&7=6)v@uioq)!81n2k1nir#)'

# List of callables that know how to import templates from various sources.
TEMPLATE_LOADERS = (
    'django.template.loaders.app_directories.load_template_source',
)

MIDDLEWARE_CLASSES = (
    'django.middleware.common.CommonMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.middleware.doc.XViewMiddleware',
    'glml.utils.middleware.YearMiddleware',
    'glml.utils.middleware.SchoolMiddleware',
)

SESSION_COOKIE_AGE = 7200
SESSION_EXPIRE_AT_BROWSER_CLOSE = True

ROOT_URLCONF = 'glml.urls'
URL_PREFIX = 'glml/'

LOGIN_URL = '/%slogin/' % URL_PREFIX

# project_root_directory/web/
#MEDIA_ROOT = os.path.dirname(os.path.abspath(__file__)) + "/../static/"

# URL that handles the media served from MEDIA_ROOT.
# Example: "http://media.lawrence.com"
#MEDIA_URL = "/%sstatic/" % URL_PREFIX

TEMPLATE_DIRS = (
    # Put strings here, like "/home/html/django_templates" or "C:/www/django/templates".
    # Always use forward slashes, even on Windows.
    # Don't forget to use absolute paths, not relative paths.
)

if DEBUG:
    TEMPLATE_STRING_IF_INVALID = 'ERROR-CAUSING-CODE-FIXTHIS'

DJANGO_APPS = (
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.admin',
)

GLML_APPS = (
    'glml.web',
    'glml.utils',
)

INSTALLED_APPS = DJANGO_APPS + GLML_APPS

TEMPLATE_CONTEXT_PROCESSORS = (
    "glml.utils.context_processors.glml_context_processor",
    "glml.utils.context_processors.year_context_processor",
    "django.core.context_processors.auth",
    "django.core.context_processors.debug",
    "django.core.context_processors.i18n",
)

URL = u'http://www.dupontmanual.org/glml/'
