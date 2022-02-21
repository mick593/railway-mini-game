## Overview
![alt text](https://github.com/mick593/railway-mini-game/blob/main/brief_overview.pdf)



## Railway game

### About :

- The **Railway game** is a game you manage to deliver the passenger 
to a desired station. The player will have options to switch intersection or stop a train.
This game will over when all passengers are delivered or a train got crash with another train or obstacle.

- People who love strategy game but still contains some dynamic in game should try this game.

I'm making this game because this game is one of my favorite games on Switch called *Conduct TOGETHER!*.
It's also not too complex to be my very first project.
## User Stories

- As a user, I want to be able to choose which train to stop.
- As a user, I want to stop/unstop the train.
- As a user, I want to get pick up people from a train station feature.
- As a user, I want to get feature to drop off people from a train station.
- As a user, I want to be able to add more cargo behind the train.
- As a user, I want to save the current game.
- As a user, I want to reload the save.

## Phase 4: Task 2

- SwitchTile has an exception thrown when switchLine() is called while there is a train on it.
## Phase 4: Task 3

- There is some redundant of field type RailwayGame in RailwayApp, ControllerPanel, and GameDrawer classes. 
It is necessary to have them all for this design but could re-design to fix this issue.
- The UI package need to introduce new class to compute location of Object as currently introduced in GameDrawer
which is lack of single responsibility.

