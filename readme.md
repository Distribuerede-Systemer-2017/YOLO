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
2. Kør følgende (Installer Homebrew): `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"` - du bliver her bedt om at skrive dit password til computeren
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
1. Hent denne installer http://mirrors.rackhosting.com/apache/tomcat/tomcat-8/v8.5.23/bin/apache-tomcat-8.5.23.exe
2. Åbn .exe filen og følg installationsguiden
3. Åben "Configure Tomcat" (kan findes ved at søge, eller ved at åbne "tomcat8w.exe" i jeres installations mappe), og hvis der står "Service Status: Started" så skal i trykke på Stop. 
4. Hvis du får en Windows Defender pop-up besked som har stoppet din server, så tryk "flere oplysninger" og "kør alligevel"
5. Du er færdig


#### 2. Opgave - Hent dette repo
Jeg har lavet noget begyndende kode i dette projekt, som I kan tage udgangspunkt i. Næste opgave er få dette projekt
til at køre på jeres lokale maskine:

1. Åbn din terminal og naviger hen til mappen hvor du ønsker dette projekt skal ligge (HINT: `cd` bruges til at skifte mappe)
2. `git clone https://github.com/Distribuerede-Systemer-2017/restfull-dis.git`
3. Åbn IntelliJ, lav et nyt projekt og placer projektet oven i dette repository
4. OBS: læg mærke til de to pop-up beskeder nederst til højre i vinduet: "Frameworks Detected" og "Non-managed pom.xml file found". I "Frameworks Detected" trykker I "configure" og trykker OK". I den næste trykker i på pilen, og trykker "Add as Maven Project".
5. Du er færdig!

#### 3. Opgave - Kør serveren med Tomcat
Vi skal nu prøve at starte serveren, og det først du måske ligger mærke til er at der ingen `main()` metode er i projektet. Du kan derfor ikke højre-klikke på nogen fil og trykker `run`. Det er fordi at projektet er konfigureret til selv at kalde `main()` metoden (oplyst i `webapp/WEB-INF/web.xml`). Vi kan derfor deploye vores app med Tomcat, og så er det Tomcat der sørger for at køre vores server.

1. I øverste højre hjørne af IntellJ (lige til venstre for den mørke play-knap) er der en pil som åbner en dropdown, klik på denne. 
2. Tryk "Edit Configurations ..."
3. Tryk "+" i øverste venstre hjørne af vinduet
4. Find "Tomcat Server" på listen
5. Vælg "Local"
6. Start med at ændre navnet på denne konfiguration fra "Unnamed" til "Run server"
7. Tryk på fanen "Deployment"
8. Tryk på "+" nederste til venstre
9. Vælg "Artifact"
10. Vælg "restfull-dis:war exploded" og klik OK
12. Sørg for at Application server er valgt til "Tomcat 8.5.xx". Hvis du er på Mac og får fejlen "Application Server not specified", så klik "Configure", klik "+" i venstre hjørne, og sæt `/usr/local/Cellar/tomcat/8.5.20/libexec` ind som Tomcat Home og tryk OK til alle vinduer er lukket.
11. Klik OK så du gemmer og lukker konfigurationsvinduet

Du bør nu se at "Play-knappen" er blevet grøn, og der ved siden af den står "Run server". Tryk på "Play-knappen" og lad Tomcat starte serveren op. Denne process kan godt tage lidt tid, og i din konsol bliver der printet en masse rød tekst - dette er ikke nødvendigvis pga. af fejl!

Hvis din browser nu åbner på `http://localhost:8080/` og der står "Jersey RESTful Web Application!" med store bogstaver, så kører din server - tillykke!

#### 4. Opgave - udforsk og udbyg din server 
Som du kan se, så returnerer din server en HTML side når du rammer `http://localhost:8080/`. Der er oprettet endpoints for `User` som både accepterer `GET http://localhost:8080/api/users` og `POST http://localhost:8080/api/users`.

Da vi ikke har tilkoblet en database endnu, så har jeg oprettet en falsk database klasse, som blot gemmer information i RAM fremfor på disk. Klasse kan dog nogle basale ting, som også kunne forventes af en rigtig database.

##### 4.1 Opgave
Når du åbner `http://localhost:8080/api/users` skal serveren returnere alle brugerene i vores system, i JSON format

##### 4.2 Opgave
Færddigør `createUser()` metoden (main/java/server/endpoints/UserEndpoint), så der kan oprettes nye brugere i systemet. Til at teste dette kan programmer som f.eks. Advanced Rest Client eller Postman bruges.

Lidt hjælp:
1. Brug Gson's `fromJson()` metode til at konvertere fra en string til object
2. Brug herefter UserTable's `addUser()` metode

##### 4.3 Opgave
Lav et nyt endpoint i `UserEndpoint`-klassen, som kan returnere én specifik bruger, hvor der søges med brugerens ID.

Jeg har givet lidt hjælp i kommentarerene i koden!
