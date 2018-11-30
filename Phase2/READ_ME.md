### Important set up note.
* We were having trouble pushing the assets directory onto the repository so before running the app:
    * Need to add the assets directory under \group_0635\Phase2\GameCentre\app\src\main folder which will look like: \group_0635\Phase2\GameCentre\app\src\main\assets
    * Need to add the text file words.txt under the assets directory which is currently under: C:\Users\Xi\AndroidStudioProjects\group_0635\Phase2\words.txt

### Instructions for using GameCenter From Phase 1

* Users can choose to sign in/up upon starting the app.
    * Username/Password must be longer than 4 characters with no trailing or internal spaces.
* Note if the app crashes upon sign in/up, the user should delete and reinstall the app without closing the emulator.
* (Extra functionality) Afterwards users will encounter a 'Not a robot' checker where the user will have to solve the 2x2 sliding tile to continue.
    * Some random generated configurations will be unplayable so there is a "New Game" Button available to rescramble the screen.
    * Sometimes the board will generate solved, so the user only needs to move a tile out of place, then move it back to proceed.
* In the next screen the user has the option to choose a game to play, check the scoreboard for top scores of all games, or log out.
    * Note to log out the user must press the back key 'built into the phone' until they reach this activity, or simply close the app.
* After choosing to play the Sliding Tiles Game, users can either:
    * Start a New Game:
        * Note: This deletes the auto save.
    * Load a Saved Game:
        * Note: This deletes the auto save.
    * Continue a game.
        * This Loads the auto save.
* Please note, we used the same implementation of Sliding Tiles as A2, so since the board is randomly generated, 50% of boards are unsolvable.
* If the User starts a new game they will be prompted to choose a complexity and the number of undos allowed for that game.
* Autosave functionality:
    * Everytime the user makes a move, that move is auto saved.
    * If the user accidentally exits the activity (By pressing back), or closes the app... The user can recover their game if they press the continue button. However if they choose to load a saved game or start a new one, the auto save is lost.
* Undo functionality:
    * The user can specify the maximum number of undos allowed per game. They can also type "unlimited" for an unlimited number of undos.
* Scoreboard functionality:
    * Once a user finishes a game, they will be brought to a "You win!" screen with their score displayed, this score will also be saved and displayed on the Scoreboard Activity screen.
        * Only the top 10 scores of all users will be displayed on the Scoreboard Activity Screen.
    * The score is calculated by a function, that takes into account the number of moves and the complexity chosen.
    
### Instructions for using GameCenter For Phase 2
