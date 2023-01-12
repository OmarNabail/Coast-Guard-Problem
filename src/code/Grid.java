package code;

import java.util.*;

public class Grid {
    private int maxGridSize=15;
    private int minGridSize=5;
    private int xIndex;
    private int yIndex=1;

    private HashMap<pair,String> cells=new HashMap<pair,String>();
    private int m;
    private int n;
    boolean costGuardAtShip=false;


    public boolean isCostGuardAtShip() {
        return costGuardAtShip;
    }

    public void setCostGuardAtShip(boolean costGuardAtShip) {
        this.costGuardAtShip = costGuardAtShip;
    }

    public boolean isCostGuardAtStation() {
        return costGuardAtStation;
    }

    public void setCostGuardAtStation(boolean costGuardAtStation) {
        this.costGuardAtStation = costGuardAtStation;
    }

    boolean costGuardAtStation=false;
    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Grid(int M , int N){
//    setM(minGridSize + (int)(Math.random() * ((maxGridSize - minGridSize) + 1)));
//    setN(minGridSize + (int)(Math.random() * ((maxGridSize - minGridSize) + 1)));
    setM(M);
    setN(N);

    costGuardAtShip=false;
    costGuardAtStation=false;
        for(int i=0;i<getM();i++){
            for(int j=0;j<getN();j++){
                pair location=new pair(i,j);
                this.cells.put(location,"Empty");
            }

        }
    }
    /*public void createGrid(int m,int n){

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                pair location=new pair(i,j);
                cells.put(location,"Empty");

        }

    }

    }*/

    public boolean isActionValid(int newXIndex,int newYIndex){
        if((newXIndex<0) ||(newYIndex<0)||(newXIndex>=getM())||(newYIndex>=getN())){
            return false;
        }
        else return true;

    }
    public static void addObjectOnGrid(Grid grid,pair location, String objectString)throws Exception{
       if(!(grid.cells.get(location).equals("Empty"))){
           throw new Exception("attempted to add an object in a non empty cell");

       }
       grid.cells.put(location,objectString);

    }
    public void moveObjectOnGrid(RescueBoat c, pair newLocation)throws Exception{
        /** The objective of this check is to make sure that when a coastguard move into the location of ship or a
         *  station it does not overwrite the location of the ship or the station in the next action.
         */
        switch (this.cells.get(c.getLocation())) {
            case "CoastGuard":
                this.cells.put(c.getLocation(),"Empty");
                break;
            case "ShipCoastGuard":
                this.cells.put(c.getLocation(),"Ship");
                this.costGuardAtShip=false;
                break;
            case "StationCoastGuard":
                this.cells.put(c.getLocation(),"Station");
                this.costGuardAtStation=false;
                break;
            default:
                throw new IllegalArgumentException("Invalid Name in old coastguard Location");
        }
        switch (this.cells.get(newLocation)) {
            case "Empty":
                this.cells.put(newLocation,c.getObjectString());
                c.setLocation(newLocation);
                break;
            case "Ship":
                this.cells.put(newLocation,cells.get(newLocation)+""+c.getObjectString());
                c.setLocation(newLocation);
                this.costGuardAtShip=true;
                break;
            case "Station":
                this.cells.put(newLocation,cells.get(newLocation)+""+c.getObjectString());
                c.setLocation(newLocation);
                this.costGuardAtStation=true;
                break;
            default:
                throw new IllegalArgumentException("Invalid Name in old coastguard Location");
        }
    }
    public boolean isCoastGuardAtShip(){
        return this.costGuardAtShip;
    }
    public boolean isCoastGuardAtStation(){
        return this.costGuardAtStation;
    }

    public void deleteObjectOnGrid(pair location,String objectString) throws Exception{
        if(!(this.cells.get(location).equals(objectString))){
            throw new Exception("attempted to remove wrong object on the grid");
        }
        this.cells.put(location,"Empty");
    }
    public boolean isCellOccupied(pair location){
        boolean cellOccupancy=true;
        if(this.cells.get(location).equals("Empty")){
            cellOccupancy=false;
        }
        return cellOccupancy;
    }

    public HashMap<pair, String> getCells() {

        return cells;
    }

    public boolean preformMotionAction (RescueBoat c, Action action_taken) throws Exception{
        boolean actionPreformed=false;
        pair location=c.getLocation();
        pair newLocation=new pair(0,0);
        switch(action_taken) {
            case LEFT:
                newLocation.setFirstValue(location.getFirstValue()-1);
                newLocation.setSecondValue(location.getSecondValue());
                break;
            case RIGHT:
                newLocation.setFirstValue(location.getFirstValue()+1);
                newLocation.setSecondValue(location.getSecondValue());
                break;
            case UP:
                newLocation.setFirstValue(location.getFirstValue());
                newLocation.setSecondValue(location.getSecondValue()-1);
                break;
            case DOWN:
                newLocation.setFirstValue(location.getFirstValue());
                newLocation.setSecondValue(location.getSecondValue()+1);
                break;
            default:
                throw new Exception("attempted to perform an action in an invalid direction");
        }
        if(this.isActionValid((newLocation.getFirstValue()), newLocation.getSecondValue())){
            this.moveObjectOnGrid(c,newLocation);
            actionPreformed=true;
        }

     return actionPreformed;
    }

    /**
     input:
        String object_string : description of the object to be added to the grid
     return type:
        pair assigned_location: location of the object in the grid
     The point of this function is to check if the cell to be assigned to an object has already been assigned
     before to another object, in case it has never been assigned before the grid object is updated with
     the new object string passed to the function, otherwise it keeps generating random locations for cells until
     one of them is empty
    **/
    public pair fillCellsRandomly(String objectString)throws Exception {
        String proposedLocOccupancy = "Occupied";
        pair ProposedLocation = new pair(0, 0);

//        this.print_cells();
        while (!proposedLocOccupancy.equals("Empty"))
        {
            int firstValue = (int) (Math.random() * (this.getM()));
            int secondValue = (int) (Math.random() * (this.getN()));
            ProposedLocation.setFirstValue(firstValue);
            ProposedLocation.setSecondValue(secondValue);

            if ((this.cells.get(ProposedLocation).equals("Empty"))) {

                proposedLocOccupancy = this.cells.get(ProposedLocation);
            }
        }
//        System.out.println(ProposedLocation.getFirstValue() + "," + ProposedLocation.getSecondValue() + " , " + objectString);
        addObjectOnGrid(this,ProposedLocation,objectString);
//        for debugging
//        this.print_cells();
//        System.exit(0);
        return ProposedLocation;
    }

    public pair fillCells(String objectString, int x, int y)throws Exception {
        pair ProposedLocation = new pair(0, 0);
        ProposedLocation.setFirstValue(x);
        ProposedLocation.setSecondValue(y);
        if ((ProposedLocation.getFirstValue() >=this.m ) || (ProposedLocation.getSecondValue() >=this.n ))
        {
            throw new Exception("location of object ("+ x+ "," +y +") outside grid size (" + m+ "," +n +")");
        }
        if ((this.cells.get(ProposedLocation).equals("Empty"))) {
            addObjectOnGrid(this,ProposedLocation,objectString);
        }
        else{
            throw new Exception("attempted to add an object in a non empty cell");
        }
        return ProposedLocation;
    }



    public pair exploreMotionAction (pair start_loc , Action action_taken) throws Exception{
        pair newLocation=new pair(0,0);
        switch(action_taken) {
            case LEFT:
                newLocation.setFirstValue(start_loc.getFirstValue()-1);
                newLocation.setSecondValue(start_loc.getSecondValue());
                break;
            case RIGHT:
                newLocation.setFirstValue(start_loc.getFirstValue()+1);
                newLocation.setSecondValue(start_loc.getSecondValue());
                break;
            case UP:
                newLocation.setFirstValue(start_loc.getFirstValue());
                newLocation.setSecondValue(start_loc.getSecondValue()-1);
                break;
            case DOWN:
                newLocation.setFirstValue(start_loc.getFirstValue());
                newLocation.setSecondValue(start_loc.getSecondValue()+1);
                break;
            default:
                throw new Exception("attempted to perform an action in an invalid direction");
        }
        if(this.isActionValid((newLocation.getFirstValue()), newLocation.getSecondValue()) && this.cells.get(newLocation).equals("Empty")){
            this.cells.put(newLocation,"Full");
            return newLocation;
        }
        return null;
    }


    public void print_cells() {
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            pair loc_index = new pair(i, j);
            System.out.println("( "+i + " , " + j + " ): " + cells.get(loc_index));

        }
    }

}
}
