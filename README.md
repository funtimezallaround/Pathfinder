# Pathfindfer
## A program which, finds the goal in a given area and generates an optimal path to it from the start location

### This project is split up in 4 parts:
"""
  A) creating an area with a start and end
  B) populating area with obstacles
  C) to find the end point, the goal
  D) to find an optimal route from the start to the end
"""

### Brief overview:
"""
  A) Firstly, the program asks the user to input the size of the area, and gets the user to confirm that they are satisfied with those values.
     The program then ask the user to input the coordinates of the start and end, and gets the user to confirm that they are satisfied with those values.
  B) Next, the user moves on to the obstacle creation menu, where the user can create, edit and delete obstacles in the area.
     At this stage the user can also ask for an illustration of the area including the obstacles. 
     When the user is satisfied they can choose to run the pathfinder. This marks the last instance of user input.
  C) The program starts searching for the end while keeping track of the path its taken. Once the end is found, the path is traced to get the coordinates of the end.
  D) Once the start adn the end are found, an optimal path is generated by using a pathfinding algorithm similar to the A* algorithm
"""

### In-depth pathfinding explanation:
"""
  After creating the area, start, end, and all the obstacles, a pathfinder, named AIden is created.
    AIden begins at the start and checks each cardinal direction for an obstacle or for a boundary edge.
    A *random* direction which is not blocked off is selected and AIden moves in that direction.
    The move is added to a list to remember the path taken and the current position of AIden is printed out
    The process repeats until the end in in one of the cardinal directions of AIden. In this case, The direction chosen is not random, but the direction the goal is in
    Once Aiden reaches the end, this stage is completed.
  Next, the path taken is traced to get the coordinates of the end.
  AIden is moved back to the start, and an algorithm similar to the A* algorithm is followed:
    Each cardinal direction is checked for an obstacle or boundary edge
    A list is created, containing the cells in the directions which are not blocked off and have not been evaluated
    The cells are then evaluated, and their parent cell set as the current cell, and the best cell is selected and then AIden repeats the process for the new location, always selelcting the best cell.
    This repeats until the end is reached and an optimal path has been generated.
  The optimal path is printed out to the user and the program ends.
"""

For those wondering why the pathfinder is named 'AIden', it is because the first 2 letters on that name is 'AI', which is what the goal of this project was for me, an attempt at creating an AI using the limited expertise that I currently have in Java.
