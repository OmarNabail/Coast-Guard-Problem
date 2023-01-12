package code;

import java.util.*;
public class Node {
    private pair state;

    public pair getParent() {
        return parent;
    }

    public void setParent(pair parent) {
        this.parent = parent;
    }

    private pair parent;
    private int pathCost;
    private int heuristicCost1;
    private int heuristicCost2;
    private Action action_taken_to_reach;

    public void setAction_taken_to_reach(Action action_taken_to_reach) {
        this.action_taken_to_reach = action_taken_to_reach;
    }

    public Action getAction_taken_to_reach() {
        return action_taken_to_reach;
    }

    public pair getState() {
        return state;
    }

    public void setState(pair state) {
        this.state = state;
    }

    public int getPathCost() {
        return pathCost;
    }
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getHeuristicCost2() {
        return heuristicCost2;
    }
    public void setHeuristicCost2(int heuristicCost2) {
        this.heuristicCost2 = heuristicCost2;
    }

    public int getHeuristicCost1() {
        return heuristicCost1;
    }
    public void setHeuristicCost1(int heuristicCost1) {
        this.heuristicCost1 = heuristicCost1;
    }


    public Node(pair state, pair parent, Action action_taken , int pathCost , int heuristicCost1, int heuristicCost2){
      this.state = state;
      this.parent = parent;
      this.action_taken_to_reach  = action_taken;
      this.pathCost = pathCost;
      this.heuristicCost1 = heuristicCost1  ;
      this.heuristicCost2 = heuristicCost2  ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return (pathCost == node.pathCost && state.equals(node.state) && heuristicCost1 == node.heuristicCost1 && heuristicCost2 == node.heuristicCost2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, pathCost, heuristicCost1 , heuristicCost2);
    }
}
