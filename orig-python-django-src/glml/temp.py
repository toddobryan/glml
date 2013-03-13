def fix(good, bads):
    """
    consumes: good, the correct Student object
              bads, the list of incorrect Student objects
    produces: nothing
    side-effect: replaces all references to the bads with the good
                 and then deletes the bads
    """
    for bad in bads:
        for bad_id in bad.studentid_set.all():
            bad_id.student = good
            bad_id.save()
        bad.delete()

"""
from glml.web.models import *

import string

LETTERS = string.letters

i = 0
done = []
for s in Student.objects.all():
    a = Student.objects.filter(last_name=s.last_name, first_name__istartswith=s.first_name[0]).exclude(id=s.id)
    b = Student.objects.filter(first_name=s.first_name, last_name__istartswith=s.last_name[0]).exclude(id=s.id)
    similar = list(set(list(a) + list(b)))
    if len(similar) > 0 and s not in done:
        similar.sort(lambda x,y: cmp(x.last_name, y.last_name))
        done.append(s)
        done += similar
        letter = LETTERS[i]
        print '#---///---#'
        print '%s = Student.objects.get(id=%s) # %s' % (letter, s.id, s)
        j = 0
        for sim in similar:
            print '%s%s = Student.objects.get(id=%s) # %s' % (letter, j, sim.id, sim)
            j += 1
        i += 1
"""
from glml.web.models import Student

#---///---#
d = Student.objects.get(id=43) # Buchanan, Matt
d0 = Student.objects.get(id=495) # Buchannan, Matt
fix(d0, [d])
#---///---#
f = Student.objects.get(id=585) # Condon, CJ
f0 = Student.objects.get(id=60) # Condon, C. J.
fix(f, [f0])
#---///---#
C = Student.objects.get(id=214) # Lee, Chan
C0 = Student.objects.get(id=615) # Lee, Chun
fix(C, [C0])
#---///---#
J = Student.objects.get(id=542) # Murphy, Joe
J0 = Student.objects.get(id=268) # Murphy, Joseph
fix(J, [J0])
#---///---#
S = Student.objects.get(id=423) # Stein, Sue
S0 = Student.objects.get(id=394) # Stein, Susan
fix(S, [S0])
#---///---#
T = Student.objects.get(id=391) # Taylor, Jon
U = Student.objects.get(id=390) # Taylor , Jonathan
U0 = Student.objects.get(id=395) # Taylor, Jonathan
fix(None, [T, U, U0])
#---///---#
X = Student.objects.get(id=364) # Vonderschmitt, Tim
X0 = Student.objects.get(id=404) # Vonderschmitt, Timothy
fix(X0, [X])
