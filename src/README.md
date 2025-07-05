# Coast Guard AI Rescue Simulation

This project is a Java-based AI simulation of coast guard missions using various search algorithms. The goal is to rescue passengers from sinking ships and retrieve black boxes before they are damaged beyond recovery.

## Problem Description

A rescue boat navigates an m x n sea grid (5 ≤ m, n ≤ 15) to perform the following:
- Rescue passengers from ships before they sink
- Retrieve black boxes from wrecked ships
- Drop rescued passengers at coast guard stations

Each time step (i.e., each action) causes:
- Ships to lose one passenger
- Wrecked ships’ black boxes to gain one damage point

The mission is complete when:
- No living passengers remain unrescued
- No black boxes remain unretrieved
- The rescue boat has no passengers on board

## How to Run

1. Compile all Java files:

   javac *.java

2. Run the main class:

   java CoastGuard

3. Example use (in CoastGuard.java):
   Input format: "GridSize; BoatCapacity; BoatStartPos; Stations; Ships position and Number of Passensgers"     
   String input = "5,5;5;0,0;2,2;3,3,10";
   String strategy = "AS1"; // Options: BF, DF, ID, UC, GR1, GR2, AS1, AS2
   boolean visualize = false;
   System.out.println(CoastGuard.solve(input, strategy, visualize));

## Project Components

- CoastGuard.java: Entry point, controls problem setup and output
- World.java: Core game logic including state transitions and action handling
- Ship.java, Station.java, RescueBoat.java: Object representations for entities in the simulation
- Strategy.java: Implements search algorithms
- Node.java: Search node representation
- Grid.java: Grid management using a HashMap
- Display.java: GUI visualization using Java Swing
- Action.java, Algorithm.java, MissionState.java: Enum classes for states and actions
- pair.java: Utility class to represent grid coordinates

## Supported Algorithms

- BF (Breadth-First Search)
- DF (Depth-First Search)
- ID (Iterative Deepening)
- UC (Uniform Cost Search)
- GR1, GR2 (Greedy Search with different heuristics)
- AS1, AS2 (A* Search with different heuristics)

## Output Format

The result is returned as a string:

Plan;Number of Saved Passengers;Number of Sunk Passengers;Number of Saved Boxes


## Notes

- Display is optional but helpful for debugging
- Ships, stations, and boats must be placed within grid bounds

