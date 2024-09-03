[open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323?new)

# BookTracker - Oversikt og Struktur
Prosjektet er konfigurert med **maven** som byggesystem. Det har i hovedssak de tre modulene core, ui og restserver. Brukergrensesnittet befinner seg i modulen ui. Prosjektet benytter javafx version 17.0.8 og Apache Maven 3.9.4.

Kodeprosjektet er plassert i mappen **[bookTracker](bookTracker)**. Denne mappen inneholder en **[readme-fil](bookTracker/README.md)** som fungerer som en mer forklarende fil om hvordan selve applikasjonen er bygget opp og fungerer. 

## Bygging og kjøring av prosjektet 
Til bygging og kjøring av prosjektet benyttes maven. For å kunne kjøre prosjektet må man først kjøre `mvn clean install` i bookTracker-mappen for å rense, bygge og installere prosjektet lokalt. Alle testene vil da bli sjekket og en kvalitetssjekking av prosjektet vil bli gjort. For å få igang restserveren må kommandoen `mvn spring-boot:run` i restserver-mappen kjøres, i en egen terminal. Etter dette kan man gå inn i ui-mappen og kjøre `mvn javafx:run` for å kjøre applikasjonen, i en annen terminal. Begge kommandoene må kjøres inne i `workspace/gr2323/bookTracker`, førstenevnte i `workspace/bookTracker/restserver` og sistnevnte i `workspace/bookTracker/ui`. For å komme seg til bookTracker kan man bruke `cd bookTracker` fra `workspace/gr2323`. Kommandoen `mvn test` brukes for å bare kjøre testene. Hvis det er ønskelig å kjøre applikasjonen uten å kjøre testene, kan `mvn clean install -DskipTests` brukes. Dette vil også spare tid.<br />
For å kjøre restserveren må man kjøre `mvn clean install -DskipTests`.<br />
Testene for JavaFx er noen ganger ustabile. Om testene feiler, har vi av erfaring, opplevd at løsningen kan være å kjøre `mvn clean install -DskipTests` forså å kjøre `mvn clean install`. Noen ganger fungerer det også kjøre applikasjonen flere ganger. En mer detaljert forklaring rundt dette er i **[challenges](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/blob/master/docs/challenges.md)**.

## maven
byggesystemet vårt har tillegg for: 
- oppsett av java (**maven-compiler-plugin**)
- testing (**maven-surefire-plugin**)
- kjøring av javafx (**javafx-maven-plugin**)
- testdekningsgrad med jacoco [jacoco](https://github.com/jacoco/jacoco) (**jacoco-maven-plugin**)
- sjekking av kodekvalitet med [checkstyle](https://checkstyle.sourceforge.io) (**maven-checkstyle-plugin**) og [spotbugs](https://spotbugs.github.io) spotbugs (**spotbugs-maven-plugin**)
- oppsett av server med spring-boot [spring-boot](https://spring.io/) (**spring-boot-maven-plugin**)


## Organisering 
### modul-organiseringen av koden
Modulene i prosjektet inneholder fire mapper for kildekode; koden selv, tester for koden, og ressurser for både hovedkoden og testene:

- **src/main/java** befinner koden til applikasjonen seg
- **src/main/resources** ressurser for hovedkoden som er nyttig for applikasjonen, f.eks. FXML-filer, data-filer og bilde-filer.
- **src/test/java** inneholder testkoden
- **src/test/resources** ressurser som tilhører testkoden og som brukes av de. Dette kan være FXML-filer, data-filer og bilde-filer.

### core 
core-moduelen er delt inn i to packages:
- **[core](bookTracker/core/src/main/java/core/)** 
- **[json](bookTracker/core/src/main/java/json/)** 

**[core](bookTracker/core)** inneholder alle klasser for logikk til applikasjonen vår. I vårt prosjekt skal en bruker kunne registrere en profil, logge inn med denne, og videre ha mulighet til å legge til og slette bøker i en personlig bokhylle. Klassene danner til sammen logikk som håndterer dette.

**[json](bookTracker/json)** inneholder klasser som brukes til å serialisere og deserialisere java-objekter til og fra json. Til dette har vi benyttet oss av Jackson-biblioteket.

### ui
**[ui](bookTracker/ui)** inneholder én package, **[ui](bookTracker/ui/src/main/java/ui)** . Denne inneholder alle kontrollerne til brukergrensesnitett i applikasjonen vår, samt implementasjonene våre av dataaccess. <br />
Vi har i prosjektet benyttet JavaFX og FXML. Hver scene i appen vår har en tilhørende FXML-fil med en tilhørende kontroller. FXML-filene er plassert i en egen resources-mappe i ui-modulen. FXML-filene er i  **[ui-resources](bookTracker/ui/src/main/resources/ui)**. Kontrollerne som FXML-filene er knyttet opp til ligger i **[ui](bookTracker/ui/src/main/java/ui)**. Registrering og innlogging av bruker, profilside til brukere og bibliotek er en del av brukerinteraksjonen med vårt brukergrensesnitt. <br />

Klassene **[DirectDataAccess](bookTracker/ui/src/main/java/ui/DirectDataAccess.java)** og **[RemoteDataAccess](bookTracker/ui/src/main/java/ui/RemotetDataAccess.java)**  har ulike metoder for å hente data. DirectDataAccess benytter seg direkte av  persistence for å lese fra og skrive til fil. Denne klassen brukes til testing av ui og lagrer lokalt.
RemoteDataAccess har metoder som kaller på REST API-et for å lese fra og skrive til fil, og brukes mens appen og serveren kjører.

### restserver
Prosjektet er konfigurert med Spring Boot og inneholder en restserver-modul. **[restserver](bookTracker/restserver)**  inneholder én package med klasser som er ansvarlige for å håndtere de forskjellige HTTP-forespørslene til og fra serveren. I ui-modulen befinner det seg en RemoteDataAccess-klasse som inneholder metoder for å utføre HTTP-kall. Serveren kjøres på `localhost:8080`


## Testdekningsgrad 
Hver gang man kjører `mvn clean install` eller `mvn test` vil det i hver modul bygges en target mappe som inneholder en “site” mappe. I denne vil det være en fil, index.html, som viser testdekningsgraden i jacoco. I ui viser RemoteDataAccess kun 4% testdekningsgrad. Dette skyldes at restserveren ikke kjører under testene og RemoteDataAccess kobler ui med rest API-et. Vi har for ui-testene derfor valgt å opprette en DirectDataAccess som etterligner RemoteDataAccess for testene.

### jacoco
Jacoco er et verktøy som sjekker prosjektets testdekningsgrad og gir detaljert innsikt i hvilken del av koden som testes i løpet av kjøretiden. Dette kan hjelpe med å identifisere områder av koden som mangler testdekning, slik at kvaliteten på koden kan forbedres. 

For å få mest mulig ut av Jacoco i Visual Studio Code, har det i dette prosjektet blitt brukt to extensions; Live Server og Coverage Gutters. Live Server gjør at man kan åpne opp en detaljert oversikt i nettleseren, mens Code Gutters gjør at man kan se hvilke deler som er testet, direkte i koden. 

### javadoc
For å enklere klare å sette seg inn i prosjektet er det lagt til kommentarer til alle klassene og metodene. Dette vil gi en bredere forståelse over hvordan prosjektet er bydg opp og fungerer.

### spotbugs 
Spotbugs er et statisk analyse verktøy som tester koden for vanlige feil. Den identifiserer potensielle problemer, bugs og svake punkter i koden, for så å gi innsikt og tilbakemelding om hvordan kodekvaliteten og sikkerheten til softwaren kan forbedres. Spotbugs sørger for at koden er sikker, forutsigbar og lesbar. 

For å kjøre spotbugs kan man bruke kommandoene `mvn verify`, `mvn spotbugs:check` og `mvn spotbugs:gui`, der sistnevnte er kommandoen som gir  tilbakemelding. Tilbakemeldingen vil komme i et spotbugs-vindu.

### checkstyle 
Checkstyle er et verktøy som bevarer kodekvaliteten og sørger for at en bestemt kodestandard blir holdt. Hovedoppgaven til verktøyet er å sjekke overfladiske og stilmessige egenskaper til koden som tekst.  Den sørger for at koden holder seg konsistent slik at den er enklere å lese og forstå. Checkstylen i prosjektet er konfigurert med standard “Google Java Style ”. Filen **[eclipse-java-google-style.xml](bookTracker/config/checkstyle/eclipse-java-google-style.xml)** definerer reglene og retningslinjene på formatet på prosjektet  vårt ( koden vår). 

For å kjøre checkstyle kan man kjøre kommandoene `mvn verify` og  `mvn checkstyle:check`

- I prosjektet har vi fremdeles en del innrykkfeil i checkstyle, som skyldes at checkstyle ikke lar seg endre til innrykk 4 istedet for 2.

Utvidet informasjon om valg rundt implementasjonen av checkstyle og spotbugs, befinner seg i [README](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/blob/master/bookTracker/README.md) i "Kommentarer til prosjektet".

## Pakkediagram 
Pakkediagrammet viser arkitekturen til prosjektet vårt. Diagrammet illustrerer de fire modulene i prosjektet og hvordan de avhenger av hverandre, samt rammeverk som er brukt. Modulene sine packages er også illustrert.

![Pakkediagram](bookTracker/images/pakkediagram.png)

## Klassediagram for core-modulen 
Klassediagrammet er en oversikt over alle klassene i prosjektets core-modul, og sammenhengen mellom dem. Hver boks representerer én klasse med dens felter og metoder. Som vist i diagrammet har Users en én til mange relasjon med User. User har en én til én relasjon med BookShelf, og BookShelf har en én til mange relasjon med Book.

![Klassediagram](bookTracker/images/klassediagram.png)

## Sekvensdiagram for å legge til bok 
I sekvensdiagrammet illustreres prosessen der en bruker legger til en bok i bokhyllen. Når brukeren velger en bok fra startsiden, sendes en forespørsel til serveren som henter detaljene til boken. Når brukeren trykker på “add book”, hentes brukeren fra serveren, og brukerens bokhylle oppdateres til å inneholde den nye bokhyllen. Den oppdaterte brukeren sendes så til serveren igjen. Når brukeren senere ønsker å se sin personlige bokhylle ved å klikke på "shelf"-knappen, hentes den oppdaterte brukerprofilen fra serveren, og brukerens bokhylle vises med de nye endringene.

![Sekvensdiagram](bookTracker/images/sekvensdiagram.png)