import os
from pubsettings import *

MEDIA_ROOT = '/srv/django/glml/src/static/'
MEDIA_URL = 'https://www.dupontmanual.org/glmlmedia/'

DATABASE_ENGINE = 'postgresql_psycopg2'
DATABASE_NAME = 'glml'
DATABASE_USER = 'glml'
DATABASE_PASSWORD = 'rEmn96B93qzXOsJgF_09'
SECRET_KEY = '586uq0hrzilEENjClD8lFFzwngQ9EJORwA0SzNKSabDZ18uOeL'

DEBUG = True
TEMPLATE_DEBUG = DEBUG
TEMPLATE_STRING_IF_INVALID = ''

