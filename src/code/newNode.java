package code;

import java.util.*;
public class newNode {
    private World state;
    private newNode parentNode;
    private String operator;
    private int depth;
    private int pathCost;
    private ArrayList<newNode> childrenList=new ArrayList<newNode>();

    public void addChildren(newNode node,String operator){

        newNode child= new newNode(getState(),node,operator,getDepth(),getPathCost());
        if(!(child==null)){
            childrenList.add(child);}

    }

    public World getState() {
        return state;
    }

    public void setState(World state) {
        this.state = state;
    }

    public newNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(newNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public newNode(World state, newNode parentNode, String operator, int depth, int pathCost){
        setState(state);
        setParentNode(parentNode);
        setOperator(operator);
        setDepth(depth);
        setPathCost(pathCost);
    }

    public ArrayList<newNode> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<newNode> childrenList) {
        this.childrenList = childrenList;
    }

    public String ToString(){
        return getState()+","+getParentNode()+","+getOperator()+","+getDepth()+","+getPathCost()+";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        newNode node = (newNode) o;
        return depth == node.depth && pathCost == node.pathCost && state.equals(node.state) && Objects.equals(parentNode, node.parentNode) && Objects.equals(operator, node.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, parentNode, operator, depth, pathCost);
    }
}
