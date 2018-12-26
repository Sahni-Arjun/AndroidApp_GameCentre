# AndroidApp_GameCentre

## How to use
The app runs on a phone with Android 8.1.

The first GameCentre file can be opened on AndroidStudio the path is:    Phase2/GameCentre



To run the code on a phone/emulator the app file has to run, the correct file is:   Phase2/GameCentre/app



To got to the code for the functionality of the app, the path is shown below: 
Phase2/GameCentre/app/src/main/java/fall2018/csc2017/GameCentre



## App Instructions

Users can choose to sign in/up upon starting the app.

Username/Password must be longer than 4 characters with no trailing or internal spaces.
Note if the app crashes upon sign in/up, the user should delete and reinstall the app without closing the emulator.

Afterwards users will encounter a 'Not a robot' checker where the user will have to solve the 2x2 sliding tile to continue.

Sometimes the board will generate solved, so the user only needs to move a tile out of place, then move it back to proceed.
In the next screen the user has the option to choose a game to play, check the scoreboard for top scores of all games, or log out.

Note to log out the user must press the back key 'built into the phone' until they reach this activity, or simply close the app.
There are three games to choose from

 * SlidingTiles
 * Hangman
 * Sudoku

Upon selecting the game to play the user now chooses which game state to play. There are three game states to choose from, if the user has no game states saved, load continue and load save buttons will not move to the next screen.

The user can select how many undos are allowed. Therefore, the user cannot use more undos than the amount selected here.
The game page is shown.

The user can choose to save the game in between the user's work.
Even if the user does not choose to save the game and exit, continue game gives the lastest state before the user left the game(autosave functionality).


#### SlidingTilesGame:
To finish the game the user has to correctly order the numbers in the row-major order by swapping a number tile with a blank tile.

An algorithm ensures every board is solvable.

There are 3 choices given for board complexity.

The score is calculated based on the number of moves taken.


#### SudokuGame
Each column, row and 3x3 box needs to contain every digit other than 0 for the board to be solved.

There are three game modes to choose from easy, medium, and hard, which determine how many numbers need to be filled in by the user.

Each boards is algorithmically generated so that the boards are always solvable.

The score is calculated based on the time it takes to complete the game and as well as the game difficulty.

At each move if the number you are trying to input is already in the row, column, or box then the program does not let you input the value.


#### HangmanGame
The user uses the keyboard to input letters as guesses.

There are three game modes to choose from easy, medium and hard, which determine how long the word given to guess is.

The word the user has to guess is randomly chosen from the list of words in words.txt.

The score is calculated based on the number of guesses it takes to complete the game as well as the game difficulty.


#### Scoreboard functionality
Once a user finishes a game, they will be brought to a "You win!" screen with their score displayed, this score will also be
saved and displayed on the Scoreboard Activity screen.

Only the top 10 scores of all users of each games will be displayed on the Scoreboard Activity Screen.

The score is calculated by a function that is unique to the game played.
