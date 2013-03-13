from glml.web.models import *
year2008, created = Year.objects.get_or_create(start=2008)
district_key, created = District.objects.get_or_create(glml_id="9", year=year2008)
school_key, created = School.objects.get_or_create(name="KEY")
school_id_key, created = SchoolID.objects.get_or_create(glml_id="99", school=school_key, district=district_key)
district, created = District.objects.get_or_create(glml_id="1", year=year2008)
school, created = School.objects.get_or_create(name="Ballard")
ballard_id, created = SchoolID.objects.get_or_create(glml_id="01", school=school, district=district)
school, created = School.objects.get_or_create(name="duPont Manual")
dupont_manual_id, created = SchoolID.objects.get_or_create(glml_id="02", school=school, district=district)
school, created = School.objects.get_or_create(name="Kentucky Country Day")
kentucky_country_day_id, created = SchoolID.objects.get_or_create(glml_id="03", school=school, district=district)
school, created = School.objects.get_or_create(name="Louisville Collegiate")
louisville_collegiate_id, created = SchoolID.objects.get_or_create(glml_id="04", school=school, district=district)
school, created = School.objects.get_or_create(name="Oldham County")
oldham_county_id, created = SchoolID.objects.get_or_create(glml_id="05", school=school, district=district)
school, created = School.objects.get_or_create(name="Saint Xavier")
saint_xavier_id, created = SchoolID.objects.get_or_create(glml_id="06", school=school, district=district)
school, created = School.objects.get_or_create(name="Trinity")
trinity_id, created = SchoolID.objects.get_or_create(glml_id="07", school=school, district=district)
district, created = District.objects.get_or_create(glml_id="2", year=year2008)
school, created = School.objects.get_or_create(name="Assumption")
assumption_id, created = SchoolID.objects.get_or_create(glml_id="01", school=school, district=district)
school, created = School.objects.get_or_create(name="Eastern")
eastern_id, created = SchoolID.objects.get_or_create(glml_id="02", school=school, district=district)
school, created = School.objects.get_or_create(name="Male")
male_id, created = SchoolID.objects.get_or_create(glml_id="03", school=school, district=district)
school, created = School.objects.get_or_create(name="New Albany")
new_albany_id, created = SchoolID.objects.get_or_create(glml_id="04", school=school, district=district)
school, created = School.objects.get_or_create(name="Pleasure Ridge Park")
pleasure_ridge_park_id, created = SchoolID.objects.get_or_create(glml_id="05", school=school, district=district)
school, created = School.objects.get_or_create(name="Sacred Heart")
sacred_heart_id, created = SchoolID.objects.get_or_create(glml_id="06", school=school, district=district)
school, created = School.objects.get_or_create(name="Seneca")
seneca_id, created = SchoolID.objects.get_or_create(glml_id="07", school=school, district=district)
school, created = School.objects.get_or_create(name="South Oldham")
south_oldham_id, created = SchoolID.objects.get_or_create(glml_id="08", school=school, district=district)
school, created = School.objects.get_or_create(name="St. Francis")
st_francis_id, created = SchoolID.objects.get_or_create(glml_id="09", school=school, district=district)
district, created = District.objects.get_or_create(glml_id="3", year=year2008)
school, created = School.objects.get_or_create(name="Atherton")
atherton_id, created = SchoolID.objects.get_or_create(glml_id="01", school=school, district=district)
school, created = School.objects.get_or_create(name="Christian Academy")
christian_academy_id, created = SchoolID.objects.get_or_create(glml_id="02", school=school, district=district)
school, created = School.objects.get_or_create(name="DeSales")
desales_id, created = SchoolID.objects.get_or_create(glml_id="03", school=school, district=district)
school, created = School.objects.get_or_create(name="Holy Cross")
holy_cross_id, created = SchoolID.objects.get_or_create(glml_id="04", school=school, district=district)
school, created = School.objects.get_or_create(name="Jeffersontown")
jeffersontown_id, created = SchoolID.objects.get_or_create(glml_id="05", school=school, district=district)
school, created = School.objects.get_or_create(name="Providence")
providence_id, created = SchoolID.objects.get_or_create(glml_id="06", school=school, district=district)
school, created = School.objects.get_or_create(name="Waggener")
waggener_id, created = SchoolID.objects.get_or_create(glml_id="07", school=school, district=district)
school, created = School.objects.get_or_create(name="Walden")
walden_id, created = SchoolID.objects.get_or_create(glml_id="08", school=school, district=district)
district, created = District.objects.get_or_create(glml_id="4", year=year2008)
school, created = School.objects.get_or_create(name="Brown")
brown_id, created = SchoolID.objects.get_or_create(glml_id="01", school=school, district=district)
school, created = School.objects.get_or_create(name="Fairdale")
fairdale_id, created = SchoolID.objects.get_or_create(glml_id="02", school=school, district=district)
school, created = School.objects.get_or_create(name="Iroquois")
iroquois_id, created = SchoolID.objects.get_or_create(glml_id="03", school=school, district=district)
school, created = School.objects.get_or_create(name="Moore")
moore_id, created = SchoolID.objects.get_or_create(glml_id="04", school=school, district=district)
school, created = School.objects.get_or_create(name="Presentation")
presentation_id, created = SchoolID.objects.get_or_create(glml_id="05", school=school, district=district)
school, created = School.objects.get_or_create(name="Southern")
southern_id, created = SchoolID.objects.get_or_create(glml_id="06", school=school, district=district)
school, created = School.objects.get_or_create(name="Valley")
valley_id, created = SchoolID.objects.get_or_create(glml_id="07", school=school, district=district)
school, created = School.objects.get_or_create(name="Western")
western_id, created = SchoolID.objects.get_or_create(glml_id="08", school=school, district=district)
from django.contrib.auth.models import User
treinert, created = User.objects.get_or_create(username="treinert")
treinert.first_name="Tim"
treinert.last_name="Reinert"
treinert.email="Tim.Reinert@jefferson.kyschools.us"
treinert.is_active = True
treinert.set_password('temp123')
treinert.save()
treinert.is_superuser = True
treinert.is_staff = True
treinert.save()
bschuetter, created = User.objects.get_or_create(username="bschuetter")
bschuetter.first_name="Betty"
bschuetter.last_name="Schuetter"
bschuetter.email="Betty.Schuetter@jefferson.kyschools.us"
bschuetter.is_active = True
bschuetter.set_password('temp123')
bschuetter.save()
bschuetter.is_superuser = True
bschuetter.is_staff = True
bschuetter.save()
apeach, created = User.objects.get_or_create(username="apeach")
apeach.first_name="Alisa"
apeach.last_name="Peach"
apeach.email="APeach@spalding.edu"
apeach.is_active = True
apeach.set_password('temp123')
apeach.save()
apeach.is_superuser = True
apeach.is_staff = True
apeach.save()
blutmer, created = User.objects.get_or_create(username="blutmer")
blutmer.first_name="Barb"
blutmer.last_name="Lutmer"
blutmer.email="barb.lutmer@ahsrockets.org"
blutmer.is_active = True
blutmer.set_password('temp123')
blutmer.save()
assumption_id.coaches.add(blutmer)
assumption_id.save()
tkurtz, created = User.objects.get_or_create(username="tkurtz")
tkurtz.first_name="Terri"
tkurtz.last_name="Kurtz"
tkurtz.email="terri.kurtz@ahsrockets.org"
tkurtz.is_active = True
tkurtz.set_password('temp123')
tkurtz.save()
assumption_id.coaches.add(tkurtz)
assumption_id.save()
dhernandez, created = User.objects.get_or_create(username="dhernandez")
dhernandez.first_name="Dave"
dhernandez.last_name="Hernandez"
dhernandez.email="David.Hernandez@jefferson.kyschools.us"
dhernandez.is_active = True
dhernandez.set_password('temp123')
dhernandez.save()
atherton_id.coaches.add(dhernandez)
atherton_id.save()
lpoynter, created = User.objects.get_or_create(username="lpoynter")
lpoynter.first_name="Lesli"
lpoynter.last_name="Poynter"
lpoynter.email="Lesli.Poynter@jefferson.kyschools.us"
lpoynter.is_active = True
lpoynter.set_password('temp123')
lpoynter.save()
ballard_id.coaches.add(lpoynter)
ballard_id.save()
meschels, created = User.objects.get_or_create(username="meschels")
meschels.first_name="Mary"
meschels.last_name="Eschels"
meschels.email="Mary.Eschels@jefferson.kyschools.us"
meschels.is_active = True
meschels.set_password('temp123')
meschels.save()
ballard_id.coaches.add(meschels)
ballard_id.save()
jleksrisawat, created = User.objects.get_or_create(username="jleksrisawat")
jleksrisawat.first_name="Juanita"
jleksrisawat.last_name="Leksrisawat"
jleksrisawat.email="Juanita.leksrisawat@jefferson.kyschools.us"
jleksrisawat.is_active = True
jleksrisawat.set_password('temp123')
jleksrisawat.save()
brown_id.coaches.add(jleksrisawat)
brown_id.save()
fdavis, created = User.objects.get_or_create(username="fdavis")
fdavis.first_name="Fran"
fdavis.last_name="Davis"
fdavis.email="fdavis@christianacademylou.org"
fdavis.is_active = True
fdavis.set_password('temp123')
fdavis.save()
christian_academy_id.coaches.add(fdavis)
christian_academy_id.save()
jschardein, created = User.objects.get_or_create(username="jschardein")
jschardein.first_name="Jacob"
jschardein.last_name="Schardein"
jschardein.email="Jacob.schardein@desaleshs.com"
jschardein.is_active = True
jschardein.set_password('temp123')
jschardein.save()
desales_id.coaches.add(jschardein)
desales_id.save()
osoumare, created = User.objects.get_or_create(username="osoumare")
osoumare.first_name="Ousseynou"
osoumare.last_name="Soumare"
osoumare.email="ousseynou.soumare@desaleshs.com"
osoumare.is_active = True
osoumare.set_password('temp123')
osoumare.save()
desales_id.coaches.add(osoumare)
desales_id.save()
bwahl, created = User.objects.get_or_create(username="bwahl")
bwahl.first_name="Becky"
bwahl.last_name="Wahl"
bwahl.email="bwahl1@jefferson.kyschools.us"
bwahl.is_active = True
bwahl.set_password('temp123')
bwahl.save()
dupont_manual_id.coaches.add(bwahl)
dupont_manual_id.save()
jwilson, created = User.objects.get_or_create(username="jwilson")
jwilson.first_name="John"
jwilson.last_name="Wilson"
jwilson.email="awilso1@jefferson.kyschools.us"
jwilson.is_active = True
jwilson.set_password('temp123')
jwilson.save()
dupont_manual_id.coaches.add(jwilson)
dupont_manual_id.save()
mquirk, created = User.objects.get_or_create(username="mquirk")
mquirk.first_name="Martin"
mquirk.last_name="Quirk"
mquirk.email="martin.quirk@jefferson.kyschools.us"
mquirk.is_active = True
mquirk.set_password('temp123')
mquirk.save()
eastern_id.coaches.add(mquirk)
eastern_id.save()
pboyd, created = User.objects.get_or_create(username="pboyd")
pboyd.first_name="Patricia"
pboyd.last_name="Boyd"
pboyd.email="patrica.boyd@jefferson.kyschools.us"
pboyd.is_active = True
pboyd.set_password('temp123')
pboyd.save()
fairdale_id.coaches.add(pboyd)
fairdale_id.save()
sbroyles, created = User.objects.get_or_create(username="sbroyles")
sbroyles.first_name="Susan"
sbroyles.last_name="Broyles"
sbroyles.email="rsbroyles@yahoo.com"
sbroyles.is_active = True
sbroyles.set_password('temp123')
sbroyles.save()
holy_cross_id.coaches.add(sbroyles)
holy_cross_id.save()
tdunn, created = User.objects.get_or_create(username="tdunn")
tdunn.first_name="Tonda"
tdunn.last_name="Dunn"
tdunn.email="Tonda.Dunn@jefferson.kyschools.us"
tdunn.is_active = True
tdunn.set_password('temp123')
tdunn.save()
iroquois_id.coaches.add(tdunn)
iroquois_id.save()
smartin, created = User.objects.get_or_create(username="smartin")
smartin.first_name="Stan"
smartin.last_name="Martin"
smartin.email="William.Martin@jefferson.kyschools.us"
smartin.is_active = True
smartin.set_password('temp123')
smartin.save()
iroquois_id.coaches.add(smartin)
iroquois_id.save()
lwheeler, created = User.objects.get_or_create(username="lwheeler")
lwheeler.first_name="Lou"
lwheeler.last_name="Wheeler"
lwheeler.email="Lou.Wheeler@Jefferson.kyschools.us"
lwheeler.is_active = True
lwheeler.set_password('temp123')
lwheeler.save()
jeffersontown_id.coaches.add(lwheeler)
jeffersontown_id.save()
mgoldberg, created = User.objects.get_or_create(username="mgoldberg")
mgoldberg.first_name="Michael"
mgoldberg.last_name="Goldberg"
mgoldberg.email="michael.goldberg@kcd.org"
mgoldberg.is_active = True
mgoldberg.set_password('temp123')
mgoldberg.save()
kentucky_country_day_id.coaches.add(mgoldberg)
kentucky_country_day_id.save()
jwilliams, created = User.objects.get_or_create(username="jwilliams")
jwilliams.first_name="Jennifer"
jwilliams.last_name="Williams"
jwilliams.email="jennifer.williams@kcd.org"
jwilliams.is_active = True
jwilliams.set_password('temp123')
jwilliams.save()
kentucky_country_day_id.coaches.add(jwilliams)
kentucky_country_day_id.save()
sstern, created = User.objects.get_or_create(username="sstern")
sstern.first_name="Simon"
sstern.last_name="Stern"
sstern.email="sstern@loucol.com"
sstern.is_active = True
sstern.set_password('temp123')
sstern.save()
louisville_collegiate_id.coaches.add(sstern)
louisville_collegiate_id.save()
sfountain, created = User.objects.get_or_create(username="sfountain")
sfountain.first_name="Sue"
sfountain.last_name="Fountain"
sfountain.email="sue.fountain@jefferson.kyschools.us"
sfountain.is_active = True
sfountain.set_password('temp123')
sfountain.save()
male_id.coaches.add(sfountain)
male_id.save()
shunter, created = User.objects.get_or_create(username="shunter")
shunter.first_name="Seth"
shunter.last_name="Hunter"
shunter.email="Seth.Hunter@jefferson.kyschools.us"
shunter.is_active = True
shunter.set_password('temp123')
shunter.save()
male_id.coaches.add(shunter)
male_id.save()
bdillman, created = User.objects.get_or_create(username="bdillman")
bdillman.first_name="Bruce"
bdillman.last_name="Dillman"
bdillman.email="Bruce.Dillman@jefferson.kyschools.us"
bdillman.is_active = True
bdillman.set_password('temp123')
bdillman.save()
male_id.coaches.add(bdillman)
male_id.save()
pparker_huck, created = User.objects.get_or_create(username="pparker-huck")
pparker_huck.first_name="Pam"
pparker_huck.last_name="Parker-Huck"
pparker_huck.email="pam.parker@jefferson.kyschools.us"
pparker_huck.is_active = True
pparker_huck.set_password('temp123')
pparker_huck.save()
moore_id.coaches.add(pparker_huck)
moore_id.save()
gkessler, created = User.objects.get_or_create(username="gkessler")
gkessler.first_name="Gabriel"
gkessler.last_name="Kessler"
gkessler.email="Gabriel.Kessler@oldham.kyschools.us"
gkessler.is_active = True
gkessler.set_password('temp123')
gkessler.save()
oldham_county_id.coaches.add(gkessler)
oldham_county_id.save()
btuttle, created = User.objects.get_or_create(username="btuttle")
btuttle.first_name="Becky"
btuttle.last_name="Tuttle"
btuttle.email="Becky.Tuttle@jefferson.kyschools.us"
btuttle.is_active = True
btuttle.set_password('temp123')
btuttle.save()
pleasure_ridge_park_id.coaches.add(btuttle)
pleasure_ridge_park_id.save()
cburtle, created = User.objects.get_or_create(username="cburtle")
cburtle.first_name="Carol"
cburtle.last_name="Burtle"
cburtle.email="cburtle@presentationacademy.org"
cburtle.is_active = True
cburtle.set_password('temp123')
cburtle.save()
presentation_id.coaches.add(cburtle)
presentation_id.save()
dvonallmen, created = User.objects.get_or_create(username="dvonallmen")
dvonallmen.first_name="Dion"
dvonallmen.last_name="vonAllmen"
dvonallmen.email="DvonAllmen@providencehigh.net"
dvonallmen.is_active = True
dvonallmen.set_password('temp123')
dvonallmen.save()
providence_id.coaches.add(dvonallmen)
providence_id.save()
pbarnett, created = User.objects.get_or_create(username="pbarnett")
pbarnett.first_name="Pat"
pbarnett.last_name="Barnett"
pbarnett.email="PBarnett@sacredheartschools.org"
pbarnett.is_active = True
pbarnett.set_password('temp123')
pbarnett.save()
sacred_heart_id.coaches.add(pbarnett)
sacred_heart_id.save()
rgarrett, created = User.objects.get_or_create(username="rgarrett")
rgarrett.first_name="Ron"
rgarrett.last_name="Garrett"
rgarrett.email="garrett@stfrancishighschool.com"
rgarrett.is_active = True
rgarrett.set_password('temp123')
rgarrett.save()
st_francis_id.coaches.add(rgarrett)
st_francis_id.save()
rnewton, created = User.objects.get_or_create(username="rnewton")
rnewton.first_name="Ron"
rnewton.last_name="Newton"
rnewton.email="NewtonR@saintxfac.com"
rnewton.is_active = True
rnewton.set_password('temp123')
rnewton.save()
saint_xavier_id.coaches.add(rnewton)
saint_xavier_id.save()
lhorstman, created = User.objects.get_or_create(username="lhorstman")
lhorstman.first_name="Linda"
lhorstman.last_name="Horstman"
lhorstman.email="linda.horstman@jefferson.kyschools.us"
lhorstman.is_active = True
lhorstman.set_password('temp123')
lhorstman.save()
seneca_id.coaches.add(lhorstman)
seneca_id.save()
apavon, created = User.objects.get_or_create(username="apavon")
apavon.first_name="Ann"
apavon.last_name="Pavon"
apavon.email="ann.pavon@oldham.kyschools.us"
apavon.is_active = True
apavon.set_password('temp123')
apavon.save()
south_oldham_id.coaches.add(apavon)
south_oldham_id.save()
asmith, created = User.objects.get_or_create(username="asmith")
asmith.first_name="Amy"
asmith.last_name="Smith"
asmith.email="asmith7@jefferson.kyschools.us"
asmith.is_active = True
asmith.set_password('temp123')
asmith.save()
southern_id.coaches.add(asmith)
southern_id.save()
hmoody, created = User.objects.get_or_create(username="hmoody")
hmoody.first_name="Harry"
hmoody.last_name="Moody"
hmoody.email="moody@THSROCK.NET"
hmoody.is_active = True
hmoody.set_password('temp123')
hmoody.save()
trinity_id.coaches.add(hmoody)
trinity_id.save()
jcharleswort, created = User.objects.get_or_create(username="jcharleswort")
jcharleswort.first_name="Joyce"
jcharleswort.last_name="Charleswort"
jcharleswort.email="joyce.charleswort@jefferson.kyschools.us"
jcharleswort.is_active = True
jcharleswort.set_password('temp123')
jcharleswort.save()
valley_id.coaches.add(jcharleswort)
valley_id.save()
edavid, created = User.objects.get_or_create(username="edavid")
edavid.first_name="Emily"
edavid.last_name="David"
edavid.email="edavid1@jefferson.kyschools.us"
edavid.is_active = True
edavid.set_password('temp123')
edavid.save()
waggener_id.coaches.add(edavid)
waggener_id.save()
echancellor, created = User.objects.get_or_create(username="echancellor")
echancellor.first_name="Eric"
echancellor.last_name="Chancellor"
echancellor.email="echancellor@walden-school.org"
echancellor.is_active = True
echancellor.set_password('temp123')
echancellor.save()
walden_id.coaches.add(echancellor)
walden_id.save()
bbradshaw, created = User.objects.get_or_create(username="bbradshaw")
bbradshaw.first_name="Beth"
bbradshaw.last_name="Bradshaw"
bbradshaw.email="Beth.Bradshaw@jefferson.kyschools.us"
bbradshaw.is_active = True
bbradshaw.set_password('temp123')
bbradshaw.save()
western_id.coaches.add(bbradshaw)
western_id.save()
