### Important set up note.
* We were having trouble pushing the assets directory onto the repository so before running the app:
    * Need to add the assets directory under \group_0635\Phase2\GameCentre\app\src\main folder which will look like: \group_0635\Phase2\GameCentre\app\src\main\assets
    * Next Need to add the text file words.txt under the assets directory which is currently under: C:\Users\Xi\AndroidStudioProjects\group_0635\Phase2\words.txt

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
* Users can choose to sign in/up upon starting the app.
    * Username/Password must be longer than 4 characters with no trailing or internal spaces.
* Note if the app crashes upon sign in/up, the user should delete and reinstall the app without closing the emulator.
* (Extra functionality) Afterwards users will encounter a 'Not a robot' checker where the user will have to solve the 2x2 sliding tile to continue.
    * Some random generated configurations will be unplayable so there is a "New Game" Button available to rescramble the screen.
    * Sometimes the board will generate solved, so the user only needs to move a tile out of place, then move it back to proceed.
* In the next screen the user has the option to choose a game to play, check the scoreboard for top scores of all games, or log out.
    * Note to log out the user must press the back key 'built into the phone' until they reach this activity, or simply close the app.
* There are three games to choose from 
    * SlidingTiles
    * Hangman
    * Sudoku
* Upon selecting the game to play the user now chooses which game state to play. There are three game states to choose from, if the user has no game states saved, load continue and load save buttons will not move to the next screen. 
    * The user can select how many undos are allowed. Therefore, the user cannot use more undos than the amount selected here.
* The game page is shown. 
    * The user can choose to save the game in between the user's work. 
    * Even if the user does not choose to save the game and exit, continue game gives the lastest state before the user left the game 

* SudokuGame: 
    * Each columns, each rows and each boxes need to have one 1 to 9s. 
    * There are three game states to choose from easy, hard, and medium. 
    * Each boards are algorithmically generated so that the boards are always solvable. 
    * The score is calculated based on the time it takes to complete the game and as well as the game difficulty
    * At each move if the number you are trying to input is already in the row, column, or box then the program does not let you input the value. 

* HangmanGame:
    * The user uses the keyboard to input letter guesses.
    * There are three game states to choose from easy, medium and hard.
    * The word the user has to guess is randomly chosen from the list of words in words.txt.
        * Easy gives you a 4 lettered word.
        * Medium gives you a 5 lettered word.
        * Hard gives you a 6 lettered word.
    * The score is calculated based on the number of guesses it takes to complete the game as well as the game difficulty.

* Scoreboard functionality:
    * Once a user finishes a game, they will be brought to a "You win!" screen with their score displayed, this score will also be saved and displayed on the Scoreboard Activity screen.
        * Only the top 10 scores of all users of each games will be displayed on the Scoreboard Activity Screen.
    * The score is calculated by a function, that takes into account the number of moves and the complexity chosen.