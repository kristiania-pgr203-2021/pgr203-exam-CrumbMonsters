# PGR203 Avansert Java eksamen

[![HttpServer](https://github.com/kristiania-pgr203-2021/pgr203-exam-eskil4152/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr203-2021/pgr203-exam-eskil4152/actions/workflows/maven.yml/badge.svg)


## Program beskrivelse
Dette er et program som skal sette opp en localhost server som skal kunne kobles til gjennom en HTTP klient. Klienten skal kunne sende handlinger til servern og få utlevert forskjellig informasjon avhengig av etterspurt informasjon samt legge til informasjon til databasen på serveren.
Prosjektet leverer et system som gjør at man kan legge til, svare på og oppdatere spørsmål samt se svar.

## Beskriv hvordan programmet skal testes:
Prosjektet kan startes på følgende måte.

1. For å komme i gang starter vi med å kjøre en maven Clean. Dette kan gjøres gjennom Maven baren til høyre, under Lifcycle og så velge clean eller i terminal med "mvn clean". Dette gjøres for å forsikre oss om at rester etter tidlgiere programkjøringer blir borte.
2. Deretter kjører vi en Maven Package. Dette kan gjøres på samme måte som steg 1, men i stedet for clean velger vi package under Lifecycle og i terminal skriver vi "mvn package".  
3. Vi bør a få en build success og fått en JAR fil vi kan eksekvere til en egen mappe. MERK: Mappen må ha en properties fil med navn "config.properties" for å fungere. Denne filen er nødvendig for å få tilgang til postgres databasene.
4. For å få tilgang til postgres databasene må config.properties dermed inneholde følgende verdier:
   - username = username(Ditt username)
     password = password(Ditt passord)
     URL = url(Din url)
5. Programmet kan nå startes og bør kunne lese verdiene fra config.properties av seg selv. 
6. Skriv så 'java -jar Java-Eksamen-1.0-SNAPSHOT.jar' i terminalen. Dette skal eksekvere JAR filen og starte serveren. 
7. For å avslutte programmet må man per nå gå i terminal og trykke control c. Kleint
## Funkjsoner som kan tests i programmet:
Vi har en del funksjoner som kan testes i programemt vårt.
Først har vi "crumbs" funksjonen vår som man kommer til direkte ved tilkobling av klient. Her skriver man inn navn som lagres i databasen. Vi forsøkte å bygge ut denne funksjonen til Cookies, men kom ikke fram til noe vi var fornøyde med. Derav har vi bare noen små crumbs.
Etter å ha oppgitt navn kommer man videre til index siden vår. 
    -her kan man gå til alle funksjonalitetene våre, men siden alle funksjonene baserer seg på at det ligger spørsmål i databasen må man først legge til spørsmål før man kan teste resten av funksjonalitetene ordentlig.
Vi trykker da dermed på "create new question" og kommer til en side der vi kan skrive inn en question title, som har som funksjon å vise hvilke spørsmål som hører sammen, og question name som er spørsmålsteksten. Trykk deretter på submit for å legge spørsmålet ditt til i databasen.
Etter å ha trykket på submit kan vi velge mellom å legge til flere spørsmål, gå tilbake til index eller vise spørsmålene vi har laget. Hvis du legger til et til vil funksjonaliteten som gjør at man kan endre på enkelte spørsmål uten at det påvirker andre blir synligere.
Vi går så til å se på spørsmålene vi har laget. Her kan vi velge hvilke spørsmål vi vil svare på gjennom en drop down meny. Vi svarer så på spørmålet og trykker submit.
Vi kan da velge mellom å legge til flere sprøsmål eller å gå tilbake til index.
Ved å gå tilbake til index kan vi nå velge å liste ut svarene ved å trykke "view answers" eller "alter questions" for å endre på spørsmålene.
Ved å trykke på view questions vil få listet ut spørsmålene  sammen med titlene de er linket med.
Ved å trykke på alter questions kan vi gå inn å endre på spørsmålene som ligger inne i databasen vår. Dette skjer på en liknende måte som når man skal oprette spørsmålet, men vi velger hvilket spørsmål vi ønsker å endre på gjennom en drop down meny som inneholder alle spørsmålene fra databasen.


## Programdesign 
???

## Korreksjoner av eksamensteksten i Wiseflow:

**DET ER EN FEIL I EKSEMPELKODEN I WISEFLOW:**

* I `addOptions.html` skulle url til `/api/tasks` vært `/api/alternativeAnswers` (eller noe sånt)

**Det er viktig å være klar over at eksempel HTML i eksamensoppgaven kun er til illustrasjon. Eksemplene er ikke tilstrekkelig for å løse alle ekstraoppgavene og kandidatene må endre HTML-en for å være tilpasset sin besvarelse**


## Sjekkliste

## Vedlegg: Sjekkliste for innlevering

* [x] Dere har lest eksamensteksten
* [ ] Dere har lastet opp en ZIP-fil med navn basert på navnet på deres Github repository
* [x] Koden er sjekket inn på github.com/pgr203-2021-repository
* [x] Dere har committed kode med begge prosjektdeltagernes GitHub konto (alternativt: README beskriver arbeidsform)

### README.md

* [ ] `README.md` inneholder en korrekt link til Github Actions
* [ ] `README.md` beskriver prosjektets funksjonalitet, hvordan man bygger det og hvordan man kjører det
* [ ] `README.md` beskriver eventuell ekstra leveranse utover minimum
* [ ] `README.md` inneholder et diagram som viser datamodellen

### Koden

* [x] `mvn package` bygger en executable jar-fil
* [x] Koden inneholder et godt sett med tester
* [ ] `java -jar target/...jar` (etter `mvn package`) lar bruker legge til og liste ut data fra databasen via webgrensesnitt
* [ ] Serveren leser HTML-filer fra JAR-filen slik at den ikke er avhengig av å kjøre i samme directory som kildekoden
* [x] Programmet leser `dataSource.url`, `dataSource.username` og `dataSource.password` fra `pgr203.properties` for å connecte til databasen
* [x] Programmet bruker Flywaydb for å sette opp databaseskjema
* [x] Server skriver nyttige loggmeldinger, inkludert informasjon om hvilken URL den kjører på ved oppstart

### Funksjonalitet

* [x] Programmet kan opprette spørsmål og lagrer disse i databasen (påkrevd for bestått)
* [x] Programmet kan vise spørsmål (påkrevd for D)
* [ ] Programmet kan legge til alternativ for spørsmål (påkrevd for D)
* [x] Programmet kan registrere svar på spørsmål (påkrevd for C)
* [x] Programmet kan endre tittel og tekst på et spørsmål (påkrevd for B)

### Ekstraspørsmål (dere må løse mange/noen av disse for å oppnå A/B)

* [ ] Før en bruker svarer på et spørsmål hadde det vært fint å la brukeren registrere navnet sitt. Klarer dere å implementere dette med cookies? Lag en form med en POST request der serveren sender tilbake Set-Cookie headeren. Browseren vil sende en Cookie header tilbake i alle requester. Bruk denne til å legge inn navnet på brukerens svar
* [ ] Når brukeren utfører en POST hadde det vært fint å sende brukeren tilbake til dit de var før. Det krever at man svarer med response code 303 See other og headeren Location
* [x] Når brukeren skriver inn en tekst på norsk må man passe på å få encoding riktig. Klarer dere å lage en <form> med action=POST og encoding=UTF-8 som fungerer med norske tegn? Klarer dere å få æøå til å fungere i tester som gjør både POST og GET?
* [ ] Å opprette og liste spørsmål hadde vært logisk og REST-fult å gjøre med GET /api/questions og POST /api/questions. Klarer dere å endre måten dere hånderer controllers på slik at en GET og en POST request kan ha samme request target?
* [ ] Vi har sett på hvordan å bruke AbstractDao for å få felles kode for retrieve og list. Kan dere bruke felles kode i AbstractDao for å unngå duplisering av inserts og updates?
* [ ] Dersom noe alvorlig galt skjer vil serveren krasje. Serveren burde i stedet logge dette og returnere en status code 500 til brukeren
* [ ] Dersom brukeren går til http://localhost:8080 får man 404. Serveren burde i stedet returnere innholdet av index.html
* [ ] Et favorittikon er et lite ikon som nettleseren viser i tab-vinduer for en webapplikasjon. Kan dere lage et favorittikon for deres server? Tips: ikonet er en binærfil og ikke en tekst og det går derfor ikke an å laste den inn i en StringBuilder
* [ ] I forelesningen har vi sett på å innføre begrepet Controllers for å organisere logikken i serveren. Unntaket fra det som håndteres med controllers er håndtering av filer på disk. Kan dere skrive om HttpServer til å bruke en FileController for å lese filer fra disk?
* [ ] Kan dere lage noen diagrammer som illustrerer hvordan programmet deres virker?
* [ ] JDBC koden fra forelesningen har en feil ved retrieve dersom id ikke finnes. Kan dere rette denne?
* [x] I forelesningen fikk vi en rar feil med CSS når vi hadde `<!DOCTYPE html>`. Grunnen til det er feil content-type. Klarer dere å fikse det slik at det fungerer å ha `<!DOCTYPE html>` på starten av alle HTML-filer?
* [ ] Klarer dere å lage en Coverage-rapport med GitHub Actions med Coveralls? (Advarsel: Foreleser har nylig opplevd feil med Coveralls så det er ikke sikkert dere får det til å virke)
* [ ] FARLIG: I løpet av kurset har HttpServer og tester fått funksjonalitet som ikke lenger er nødvendig. Klarer dere å fjerne alt som er overflødig nå uten å også fjerne kode som fortsatt har verdi? (Advarsel: Denne kan trekke ned dersom dere gjør det feil!)
