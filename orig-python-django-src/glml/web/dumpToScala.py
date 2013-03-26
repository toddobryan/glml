from glml.web.models import *

def defn(cls, lst):
    line1 = '  def load%ss(debug: Boolean = false) {\n' % cls
    line2 = '    if (debug) println("Adding %s models to the database...")\n' % cls
    line3 = '    val %ss = List(\n' % cls.lower()
    linex = '    )\n'
    liney = '    %ss.foreach(DataStore.pm.makePersistent(_))\n  }\n' % cls.lower()
    return line1 + line2 + line3 + lst + linex + liney
    
def years():
    objs = ["      new Year(%d)" % y.start for y in Year.objects.all()]
    return defn("Year", ",\n".join(objs))
    
        
