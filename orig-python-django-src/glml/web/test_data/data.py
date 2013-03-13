def load():
    from glml.web.test_data.glml_0708 import *
    from glml.web.test_data.glml_0708_2 import *
    from glml.web.test_data.glml_0809 import *
    from django.contrib.auth.models import User
    for user in User.objects.filter(is_active=True):
        username = user.username
        email = user.email
        password = User.objects.make_random_password(6, 'abcdefghijkmnopqrstuvwxyz023456789')
        user.set_password(password)
        extra = "\nPlease log in to add your team members prior to the test on Tuesday. Each team member will be assigned his/her own ID number to use on the exam."
        if user.is_staff:
             extra = ''
        subject = "GLML Website"
        message = """The url is
https://www.dupontmanual.org/glml/

Your login information is
Username: %s
Password: %s

Please change your password.%s
Sorry for the delay in the completion of the website.""" % (username, password, extra)
        from_email = "toddobryan@gmail.com"
        try:
            user.email_user(subject, message, from_email)
            user.save()
        except:
            pass
#            print """To: %s
#From: %s
#Subject: %s
#Message:
#%s
#""" % (email, from_email, subject, message)