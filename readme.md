## 6. Øvelsesgang - Workshop
Vi skal i dag prøve at bygge "en rigtig" restfull webservice, som kunne være inspiration 
til jeres eksamensprojekt. Vi skal prøve at lege med frameworket Jersey, som har en masse 
indbygget funktionalitet der hjælper os hurtigt i gang med at lave en webservice.

Til at køre vores server-kode bruge vi Tomcat som Java Servlet (https://en.wikipedia.org/wiki/Java_servlet). 
Første trin er derfor at installere Tomcat på vores maskiner.

#### 1. Opgave - Installer Tomcat
Da Installationsprocessen for Windows og Mac er forskellig, skal du her kun følge den guide som passer til din platform

##### Mac
1. Åbn Terminal
2. Kør følgende (Installer Homebrew): `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
3. Når install er færdig så kør: `brew install tomcat`
4. Når install er færdig så kør: `brew list tomcat`
5. Hvis du får et output der ligner det her:
```
/usr/local/Cellar/tomcat/8.5.20/bin/catalina
/usr/local/Cellar/tomcat/8.5.20/homebrew.mxcl.tomcat.plist
/usr/local/Cellar/tomcat/8.5.20/libexec/bin/ (15 files)
/usr/local/Cellar/tomcat/8.5.20/libexec/conf/ (10 files)
/usr/local/Cellar/tomcat/8.5.20/libexec/lib/ (25 files)
/usr/local/Cellar/tomcat/8.5.20/libexec/logs/ (10 files)
/usr/local/Cellar/tomcat/8.5.20/libexec/temp/safeToDelete.tmp
/usr/local/Cellar/tomcat/8.5.20/libexec/webapps/ (630 files)
/usr/local/Cellar/tomcat/8.5.20/libexec/work/ (3 files)
/usr/local/Cellar/tomcat/8.5.20/RELEASE-NOTES
/usr/local/Cellar/tomcat/8.5.20/RUNNING.txt
```
... så er du færdig!

##### Windows
1. Hent denne installer http://mirrors.dotsrc.org/apache/tomcat/tomcat-8/v8.5.20/bin/apache-tomcat-8.5.20.exe
2. Åbn .exe filen og følg installationsguiden
3. Du er færdig


#### 2. Opgave - Hent dette repo
Jeg har lavet noget begyndende kode i dette projekt, som I kan tage udgangspunkt i. Næste opgave er få dette projekt
til at køre på jeres lokale maskine:

1. Åbn din terminal og naviger hen til mappen hvor du ønsker dette projekt skal ligge (HINT: `cd` bruges til at skifte mappe)
2. `git clone https://github.com/Distribuerede-Systemer-2017/restfull-dis.git`
3. Åbn IntelliJ, lav et nyt projekt og placer projektet oven i dette repository
4. OBS: læg mærke til at IntelliJ spørger 
