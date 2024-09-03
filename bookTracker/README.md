# BookTracker

Booktracker er en Goodreads-inspirert applikasjon. Applikasjonen fungerer som et medium hvor brukere kan holde oversikt over bøker de har lest eller ønsker å lese. Brukere skal for eksempel ha muligheten til å lage personlige lesemål, lage ulike mapper og søke etter bøker.

## Innholdet i applikasjon

BookTracker er en JavaFX-applikasjon som skal gi brukere muligheten til å ha en digital oversikt over bøker de har lest eller ønsker å lese. <br />
Ved første møte med applikasjonen må brukerensørge for å registrere seg som en ny bruker, altså en `User`. Brukeren må da oppgi informasjon om ønsket brukernavn, mail og passord. Serveren vil deretter lagre informasjonen slik at brukeren kan logge seg inn ved bruk av brukernavn og passord. Passordet og brukernavnet vil da tilhøre den bestemte brukeren, og kan brukes ved videre innlogging.<br />
Det første brukeren vil møte på etter innlogging er startsiden, Home Page. På startsiden kan han/hun se en oversikt over de mest populære bøkene og Pulitzer vinnerne fra 2022 og 2023. Brukeren kan i tillegg søke etter bestemte bøker og legge de til i sin bokhylle, hvis de eksisterer. Muligheten for å legge til bøkene som allerede vises på startsiden, i sin egen bokhylle, er også der. <br />
I `Shelf` kan man finne bokhyllene som inneholder de tilhørende bøkene til brukeren. Brukerinformasjonen om den spesifikke brukeren hentes og lagres i serveren.  Både bøker som allerede er lagt til i bokhyllen og bøker som legges til fortløpende vil vises i `Shelf`. I `Shelf` har man også muligheten til å fjerne bøker. For hver gang en bok legges til vil brukeren få et varsel, mens når en bok fjernes fra bokhyllen vil antall bøker endres deretter. <br />
Det finnes også en brukerprofil side, `Profile`, som innholder brukernavn og e-post til brukeren som er logget inn. Muligheten til å bytte passord er også her. Ved passordbytte vil serveren lagre det nye passordet og forkaste det gamle. 

**Innholdet i denne readme-filen:**
- Endelig brukergrensesnitt 
- Kommentarer til prosjektet

Selve oversikten over koden og kodens oppbygging, er i **[readme](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/blob/master/readme.md)**. <br />

## Endelig brukergrensesnitt 

En oversikt over de fem hovedsidene av brukergrensesnittet.

![img2](../bookTracker/images/app2.png)
![img3](../bookTracker/images/app3.png)
![img5](../bookTracker/images/app5.png)
![img9](../bookTracker/images/app9.png)
![img10](../bookTracker/images/app10.png)

## Kommentarer til prosjektet
Vi har laget flere brukerhistorier som dekker ønsket bruksområde til applikasjonen. Brukerhistoriene innholder funksjoner som legges til i applikasjonen. Disse historiene finner man i **[brukerhistorier](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/blob/master/brukerhistorier.md)** 

Fremgangen over tid og hvordan prosjektet har utviklet seg er dokumentert i release-notatene som er **[docs](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/tree/master/docs)**.

### Kvalitetssjekking 
I løpet av prosjektet har det oppstått en del problemer med checkstyle og spotbugs. For at programmet skulle fungere uten store feil så vi oss nødt til å fjerne deler av plugin implementasjonen til checkstyle og spotbugs, i den ytterste pom-filen. I Checkstyle plugin måtte vi fjerene <encoding>UTF-8</encoding> for at koden skulle kjøre uten unntak. I tilfellet med spotbugs fikk vi egentlig ikke ordentlig tilbakemelding før vi fjernet <htmlOutput>true</htmlOutput> fra plugin, og endret <xmlOutput>false</xmlOutput> til <xmlOutput>true</xmlOutput>. Etter å ha gått gjennom problemene med en Undervisningsassistent, fant vi ut at disse endringene var de beste løsningene for vårt prosjekt. Nå fungerer checkstyle og spotbugs som det skal, vi må bare bruke andre kommandoer for å få de til å kjøre. Mer om de spesifikke kommandoene som må kjøres befinner seg i **[readme](https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2323/gr2323/-/blob/master/readme.md)**.

I tillegg har vi slitt litt med å endre på innrykk-sjekkingen til checkstyle. Vi bestemte oss ganske tidlig for å ha innrykk 4 og ikke innrykk 2, som er standard i Google-checkstyle formatet. Avgjørelsen ble tatt på bakgrunn av at vscode har default innrykk 4, som alle medlemmene på gruppa er vant med. Siden vi ikke klarte å endre instillingene til checkstyle, bestemte vi oss for å følge default vscode kodeformatering.  