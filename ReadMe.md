**Balancer**

**Android app**

This is my final assignment for Mobile Infrastructure and security.

The application is a simple game, where the user has to incline the phone in the directions which pops up on the screen.

Main menu contains buttons to show the instructions, scoreboard start the game itself and one inpiut for the user's name.

Instructions button shows a dialog which contains the instructions.

Scoreboard goes to another activity where all avaialable scores are downloaded via REST from server. Scores are ordered by score and contain name, score, date and coordinates of the location where the score was recorded.

Start the game goes to a screen where you can see the score on top and instructions in the center of the screen, the instructions change as soon as you correctly incline the phone. Score is calculated from the time it takes to do that, if the time is <= 0.2s you get 1000, if the time is >= 2s you get nothing.
Formula for the score calculation: f(time) = 1/(time)+1)-1/3 

**Server**

The server provides and accepts data via REST api, documented in API.md. The server loads and stores the data in a MySQL database and allows anyone to post and read data. Server allows https serving.

* **Author**
  * Name: Jan Hric 
  * Email: jan.hric@hva.nl 
  * Student nr: hricj001