# BashBoys Inc

###Deloppgave 1:
Rollene i teamet fungerer fortsatt veldig bra og alle er fornøyde med rollene sine. Teamet er fortsatt veldig fornøyd med valget av prosjektmetodikk og det går for det meste helt problemfritt. Hvis vi har møtt på et lite problem har det raskt blitt fikset opp i og alle føler de får jobbet slik de føler er mest effektivt og ryddig.   

Retrospektivt har vi klart å jobbe bra som et team fra starten av uten noe særlig problemer eller hindringer i veien. Alle jobber bra og er med og bidra. Det eneste som kunne ha forbedret seg litt er å få pushet ting litt mer jevnlig slik at det ikke blir noe kluss med for eksempel bruk av array aller arraylist osv. Dette har ikke vært noe veldig stort problem men kan alltid forbedres. I tillegg kan vi bli bedre på å skrive javadoc til metoder med en gang.
 
Forbedringspunkter:
* Kan fortsatt bli bedre på javadocs.
* Mer jevnlige push på github

Fremover har vi prioritert å få spill loopen til å gå skikkelig (runder og faser). I tillegg har vi begynt å se på multiplayer delen av spillet, nå som vi har kommet langt nok i programmeringen at det er viktig å få med slik det ikke blir noe kluss helt på slutten.

###Deloppgave 2:
Det vi har prioritert høyest og blitt ferdig med siden forrige gang er å få ferdig en fungerende programmerings sekvens og at denne programmerings sekvensen beveger roboten riktig i forhold til kortene og prioriterings nummeret på kortene.

#### Brukerhistorier:
* Som spiller vil jeg kunne flytte kortene til registrene
    * Oppgave: Kan flytte kortene fra hånden til rett register 
        * Kortet fjernes fra hånden
        * Kortet legges til programsheet på riktig plass
* Som en spiller vil jeg kunne programmere roboten
    * Oppgave: Kortene på programsheet beveger roboten i rett rekkefølge
        * Kortene stemme overens med det robot gjør
        * Kortene blir gjort i rett rekkefølge
* Som en spiller vil jeg kunne få nye kort vær runde
    * Oppgave: Nye kort blir delt ut vær runde
        * Når robotene er ferdige med alt får jeg nye kort basert på robten HP
        * Jeg får korrekt antall kort
* Som en spiller vil jeg at roboten skal beveges i forhold til priority på kortet
    * Oppgave: Robotene beveger seg etter priority nummeret på kortene som spilles den runden
        * Kortene har priority nummer
        * Roboten med høyest nummer beveger seg først
* Som robot vil jeg miste liv av laser
    * Oppgave: Robot mister liv etter hver fase
        * Robot mister liv når den er “under” en laser
        * Robot mister korrekt mengde liv basert på hvor mange lasere
* Som robot vil jeg få liv av healstasjone
    * Oppgave: Robot får liv når den går på en heal stasjon
        * Robot får liv når den står på heal stasjonen
        * Roboten får korrekt mengde liv basert på hvilken type heal stasjon
* Som robot vil jeg bli skuet når jeg står ved siden av en “pusher”
    * Oppgave: Roboten blir skuet en “celle” av pusher vegger
        * Roboten reagere når veggen skuer roboten
        * Roboten beveger seg korrekt lengde basert på pusheren
* Som robot vil jeg rotere når jeg står på et “gear”
    * Oppgave: Roboten rotere når den ender en fase på “gear” celler
        * Roboten roter i korrekt retning
        * Roboten roter i korrekte mengder
* Som robot vil jeg dø når jeg faller i et hull
    * Oppgave: Roboten dør når den går over et hull eller kanten av brettet
        * Roboten mister all livet når den går/dyttet i et hull
        * Roboten kan bli dyttet i hullet og dø
* Som robot vil jeg bli flyttet hvis jeg står på et belte i slutten av en fase
    * Oppgave: Robot beveger seg korrekt hvis den er stående på et belte i slutten av en fase
        * Robot beveger seg 1 felt hvis den står på et 1er belte
        * Robot beveger seg 1 felt hvis den står på et 2er belte
                
Våre hovedkrav for MVP er at spillet har en fungerende program sekvens som deler ut korrekt antall kort og en spiller kan bevege han sin robot ved bruk av de utdelte kortene. Dette er fordi for å spille spillet må man kunne bevege roboten sin. Man skal også kunne spille et komplett spill fra “start game” til spilleren har plukket om alle flaggene og vinner. Vi valgte dette som MVP, fordi vi mener at spiller er da spillbart og kanskje litt gøy. Som er definisjonen på et spill. Det er også det punktet hvor det det meste ekstra egenskaper bare gjør spille bedre, enn at de gjør det spillbart mener vi.


## WorkBoard
![WorkBoard](https://github.com/inf112-v20/BashBoys_Inc/blob/master/Deliverables/WordBoards/WorkBoardOblig3.PNG)
