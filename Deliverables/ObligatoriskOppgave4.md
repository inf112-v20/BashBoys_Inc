# BashBoys Inc
### Deloppgave 1:  
Rollene i teamet fungerer fremdeles veldig bra og alle er fornøyd med de oppgave de får tildelt. Vi føler derfor at det ikke trengs å gjøre noe endringer før prosjektet er ferdig.  

Med tanke på prosjektet som en helhet føler vi at vi har tatt gode valg og jobbet bra med det meste. Vi er spesielt fornøyd med valget av prosjektmetodikk (kanban) og måten vi har jobbet sammen som et team. 

Hvis vi skulle gjort prosjektet på ny er det noen ting vi ville ha gjort annerledes. Det første problemet vi møtte på var at vi hadde satt for lite folk til å sette seg inn i libgdx og den visuelle siden av prosjektet. Dette førte til at vi hadde kommet lengre på selve spillkoden enn det å vise elementer på skjermen så vi kunne ikke teste noe av det vi kodet. Som følge av det ble en del kode gjort om på/kastet når vi fikk til den visuelle delen. Vi ville derfor ha satt enda flere folk til å jobbe med den visuelle delen fra starten av. I tillegg ville vi også ha fokusert mer på nettverksdelen av oppgaven tidligere slik at vi hadde sluppet å skifte rundt på koden slik at den passet til multiplayer.

Gruppedynamikken og kommunikasjonen har vært bra stort sett gjennom hele prosjektet. Karantene og nedstengning var en liten utfordring i starten men det gikk ikke lenge før vi jobbet nesten like effektivt over discord som i de vanlige gruppetimene. Det eneste som var litt mer stress å få til var å diskutere små kode problemer over discord. Litt mer vrient å ta på skjermdeling og vise enn å bare lene seg over og se på skjermen til de du jobber med.

### Deloppgave 2: 

Det vi har prioritert høyest og blitt ferdig med siden forrige gang er fungerende LAN og online multiplayer. Andre prioriteringer har vært å gjør ferdige UI med en meny og settings knapp på hoved menyen.

Brukerhistorier: 
* Som spiller vil jeg kunne få opp en meny når jeg åpner programmet
    * Oppgave: Meny skjerm vises når programmet åpnes
        * Grafikk til meny skjerm
        * Knapp til å starte et spill
* Som spiller vil jeg kunne velge om jeg vil høre musikk i spillet
    * Oppgave: Knapp som skrur av eller på musikk
        * Musikk spilles i spillet hvis på
        * Musikk spilles ikke hvis av
        * Knapp til å skru av/på musikk
* Som spiller vil jeg kunne hoste et spill
    * Oppgave: En spiller kan hoste et game og andre kan bli med online eller LAN
        * Host meny knapp som tar deg til host skjerm
        * Host starter et multiplayer game som andre kan finne via IP-adresse
        * Spillere som blir med i gamet ditt vises på skjermen og følger de samme reglene
* Som spiller vil jeg kunne joine et spill
    * Oppgave: En spiller kan bli med en annen spiller som hoster et game
        * Join meny knapp som gjør det muligt å finne andre host via IP-adresse
        * Spilleren kommer med i spillet til hosten og ser samme “brett”
* Som spiller vil jeg kunne ta flagg i riktig rekkefølge
    * Oppgave: Ta et flagg hvis roboten står på det i slutten av en runde og det er riktig flagg
        * Ha flagg på brettet
        * Grafikk til flagg
        * Registrere at en robot står på et flagg
        * Vite hvilke flagg spilleren kan ta
        * Vite hvilke flagg spilleren har tatt
* Som spiller vil jeg kunne vinne et spill
    * Oppgave: Få opp en vinner skjerm hvis alle flaggene er samlet
        * Vite hvor mange flagg spilleren har tatt
        * Vite hvor mange flagg som er på brettet
        * Vite hvor mange flagg andre spillere har tatt
        * Grafikk til vinner skjerm
        
Våre hovedkrav for MVP er fortsatt at spillet har en fungerende program sekvens som deler ut korrekt antall kort og en spiller kan bevege han sin robot ved bruk av de utdelte kortene (for mer utdypning se oblig 3). 

## Kravliste
Alle de utdelte kravene skal være oppfylt i prioritert rekkefølge
1. ha et spillebrett
2. vise en brikke
3. kunne flytte en brikke
4. spille fra ulike maskiner
5. dele ut kort
6. velge kort (5 av 9)
7. flytte brikke utfra kort
8. kan ikke gå gjennom vegger
9. dele ut nye kort ved ny runde
10. vise flere (i alle fall to) brikker på samme brett
11. dele ut kort til hver robot
12. flytte flere brikker samtidig
13. flytte brikker utfra prioritet på kort
14. flagg på brettet
15. kunne registrere at en robot har vært innom et flagg
16. håndtere konflikter i bevegelse riktig
17. kunne legge igjen backup
18. restarte fra backup ved ødeleggelse
19. går du i et hull, blir du ødelagt, mister et liv og begynner fra forrige backup
20. går du av brettet, blir du ødelagt, mister et liv og begynner fra forrige backup
21. blir du skutt i fillebiter (9 i skade) blir du ødelagt, mister et liv og begynner fra forrige backup
22. vender en robot mot deg ved slutten av en fase blir du skutt og får en i skade
23. får du skade får du mindre kort i henhold til skaden du har
24. for mye skade brenner fast programkort fra runde til runde


### Deloppgave 3:
Instrukser for å bygge prosjektet ligger i ‘readme.md’ i root i prosjektet.
Vi har skrevet automatiske tester på alle funksjoner i koden. Visuelle ting som er vanskelig å teste i kode som for eksempel “drag and drop” av programmeringskort og bekreftelse på at lyd fungerer er det også laget tester for. Disse testene er beskrevet i “manualTests.md” i “src/test/java/inf112.skeleton.app/manualTests.md”.

Prosjektet er testet på Windows, Linux og OSX. Testen på Linux ble utført på en gammel maskin, men spillet fungerte greit.

I “readme.md” ligger og en liste over alle krav som er oppfylt basert på kravene som ble gitt fra kunden.

## WorkBoard
![WorkBoard](https://github.com/inf112-v20/BashBoys_Inc/blob/master/Deliverables/WordBoards/WorkBoardOblig4.PNG)

## UML
![Oblig3UML](https://github.com/inf112-v20/BashBoys_Inc/blob/master/Deliverables/UMLs/uml4.png)

