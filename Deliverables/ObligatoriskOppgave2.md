# BashBoys Inc
### Deloppgave 1:
Rollene fungerte bra i teamet og alle fikk gjort litt hvert, vi ser ikke noe behov i å oppdatere hvilke roller vi har. Alle var fornøyde med sine valgte roller fra oblig1 og rollene våre er fleksible nok til at alle skal kunne gjøre hva de vil hvis de ønsker.

Vi synes at kanban metoden fungerte veldig bra, det gjorde det lett for oss å vite hvilke oppgaver vi kunne jobbe med mellom møtene. Gruppedynamikken var top notch, eventuelle uenigheter ble løst på en fornuftig måte. Kommunikasjon på møtene har vært bra og alle har fått sagt deres meninger. Slack har også fungert bra som et kommunikasjonsmiddel mellom møtene.

I retrospekt mener at vi har fått til en god arbeidsfordeling mellom alle gruppemedlemmene. Vi er spesielt fornøyd med bruken av project board på GitHub.

Forbedringspunkter:
1.  Enda bedre JavaDoc, spesielt for @params
2.  Vi er fornøyde med project board, men vi kan bli bedre på å close issues etter de er gjort

### Deloppgave 2:  
Brukerhistorier:
* Som en Robot kan jeg flyttes
    * Oppgave: Programmere bevegelse til robot object
        * Akseptansekrav: Roboten flytter seg et felt på brettet
    * Oppgave: Roboten må oppdateres på brettet visuelt
        * Akseptansekrav: Hvis roboten flytter seg en rute skal den flytte seg en rute på skjermen

* Som en Robot kan jeg ikke bevege meg gjennom vegger
    * Oppgave: Roboten stopper hvis den treffer en vegg
        * Akseptansekrav: Roboten stopper når den treffer veggen

* Som en Robot kan jeg dytte en annen robot 
    * Oppgave: Programmer en dytte egenskap til robot
        * Akseptansekrav: Robot dytter en annen robot på brettet
    * Oppgave: Robot som blir dyttet flytter seg korrekt i henhold til roboten som dytter
        * Akseptansekrav: Robot som blir dyttet flytter seg et felt i retning til roboten som dytter

* Som bruker vil jeg kunne se kort på skjermen
    * Oppgave: Et kort vises skjermen
        * Akseptansekrav: Kortet vises på skjermen under brettet
    * Oppgave: Et kort skal kunne trykkes på
        * Akseptansekrav: Kortet reagerer når det trykkes.

Planen fremover: 

Fremover skal vi legge mer fokus på det visuelle programmeringen, slik at de andre slipper å programmere i blinde. Det vil si at vi fokusere mer enn tidligere på libGdx delen av prosjektet.

Vår hovedkrav for MVP er at spillet har en fungerende program sekvens som deler ut korrekt antall kort og en spiller kan bevege han sin robot ved bruk av de utdelte kortene. 

Vi har kommet relativt langt på den generelle programmeringsdelen, men det visuelle ligger litt bak og alt programmerings logikken blir ikke brukt ennå. Siden forrige oblig har vi implementert et fungerende bevegende robot, visuell visning av kort og litt basic spill logikk. Fremover blir det mest prioriterte kravene følgende for å prøve å oppnå et MVP.

1. Fungerende programmering sekvens
2. Dele ut korrekt antall kort 
3. Spiller kan bruke kortene til å bevege roboten

Flere krav:
* Flere spillere fra ulike maskiner over LAN
* Fungerer på alle operativsystem
* Skal gå ann å vinne spillet
* Flagg på brettet
* Fungerende RoboRally regler implementert

### UML
![UML](https://github.com/inf112-v20/BashBoys_Inc/blob/master/Deliverables/UMLs/oblig2UML.png)
