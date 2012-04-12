// ** I18N
Calendar._DN = new Array
("S�ndag",
 "Mandag",
 "Tirsdag",
 "Onsdag",
 "Torsdag",
 "Fredag",
 "L�rdag",
 "S�ndag");

Calendar._SDN_len = 2;

Calendar._MN = new Array
("Januar",
 "Februar",
 "Marts",
 "April",
 "Maj",
 "Juni",
 "Juli",
 "August",
 "September",
 "Oktober",
 "November",
 "December");

// First day of the week. "0" means display Sunday first, "1" means display
// Monday first, etc.
Calendar._FD = 1;

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "Info";

Calendar._TT["ABOUT"] =
"DHTML Dato/Tid v�lger\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" +
"Find seneste version her: http://www.dynarch.com/projects/calendar/\n" +
"Distribueret under GNU LGPL.  Se http://gnu.org/licenses/lgpl.html for detaljer." +
"\n\n" +
"Valg af dato:\n" +
"- Anvend \xab \xbb knapperne for at v�lge �r\n" +
"- Anvend " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " knapperne for at v�lge m�ned\n" +
"- Hold museknappen nede p� de n�vnte knapper for hurtigere udv�lgelse.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Valg af tid:\n" +
"- Klik p� tidsangivelsen for at g�re den st�rre\n" +
"- eller Skift-klik for at g�re den mindre\n" +
"- eller hold museknappen nede og tr�k for hurtigere udv�lgelse.";

Calendar._TT["PREV_YEAR"] = "Forrige �r (hold for menu)";
Calendar._TT["PREV_MONTH"] = "Forrige m�ned (hold for menu)";
Calendar._TT["GO_TODAY"] = "G� til idag";
Calendar._TT["NEXT_MONTH"] = "N�ste m�ned (hold for menu)";
Calendar._TT["NEXT_YEAR"] = "N�ste �r (hold for menu)";
Calendar._TT["SEL_DATE"] = "V�lg dato";
Calendar._TT["DRAG_TO_MOVE"] = "Tr�k for at flytte";
Calendar._TT["PART_TODAY"] = " (idag)";

Calendar._TT["DAY_FIRST"] = "Vis %s f�rst";

Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "Luk";
Calendar._TT["TODAY"] = "(idag)";
Calendar._TT["TIME_PART"] = "(Skift-)Klik eller tr�k for at �ndre v�rdi";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%d-%m-%Y";
Calendar._TT["TT_DATE_FORMAT"] = "%a, %e %b %Y";

Calendar._TT["WK"] = "uge";
Calendar._TT["TIME"] = "Tid:";