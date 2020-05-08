# Manual tests
This document describes manual test procedures to confirm features that we are unable to test automatically at the moment.


### Confirm drag and drop of cards (last tested: 03.05.2020)
Given that a person has launched a game and seeing the playing board: an user shall be able to drag and drop programming card to the programming sequence.
1. Launch game
2. When in main menu: Click "Play"
3. Game should launch with a board
4. You should now be able to drag and drop programming cards to programming sequence


### Graphics shall load (last tested: 03.05.2020)
When in game, the graphics shall load correctly.
1. Launch game
2. When in main menu: Click "Play"
3. Game should launch with a board
4. There shall now not be any black "holes" on the screen.
    - All cards and tiles shall have graphics.

### Music shall play (last tested: 03.05.2020)
When in game, music shall be heard if enabled in settings before entering game.
1. Launch game
2. When in main menu:
    a. Click `Settings`
    b. If Sound is not checked -> click the checkbox to enable sound.
    c. Go back
    d. Click "Play"
3. Game should launch with a board
4. Music shall now be playing if volume on pc is on.

### Gui works on all diffrent resolutions between (1366x768 and upwards) (last tested: 05.05.2020)
When game is launchen on diffrent resolutions the game still looks nice and all gui elments are drawn correctly
1. Launch game on a pc with diffrent resolutions or manualy choose resoulution
2. Check each screen, `Settings`, `Menu`, `Join`, `Host` and `Play`
3. See that all gui elements are drawn correctly and it looks ok
