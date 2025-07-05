package code;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;

public class World {

    int maxNumShipsDebuggingMode=2;
    int maxNumStationsDebuggingMode=2;
    int numberOfCoastGuards=1;
    int minNumberShips=1;
    int minNumberStations=1;
    boolean debugMode=false;
    boolean visualizeMode = false;
    int numberOfSavedPassengers=0;
    int numberOfSavedBoxes=0;
    int numberOfSunkPassengers=0;
    int numberOfSunkBoxes=0;
    int stepsTaken=0;
    int totalNumberOfShipsPassengers=0;
    int totalNumberOfShipsBoxes=0;
    int actualNumberOfShips=0;
    int actualNumberOfStations=0;
    int initalNumberOfPassengers=0;
    int initalNumberOfBoxes=0;
    Utils util=new Utils();
    MissionState missionStateObject=null;
    Algorithm algorithm_enum = Algorithm.NONE;
    private pair goalLocation=new pair(0, 0);
    private ArrayList<pair> goalLoctaionList = new ArrayList<>();
    private int num_explored_nodes = 0;

    //World Parameters
    Grid grid_object = null;
    RescueBoat rescueboat_object =null;

    ArrayList<Ship> ships_objectlist =new ArrayList<Ship>();
    ArrayList<Station> stations_objectlist =new ArrayList<Station>();
    ArrayList<Action> takenactions_list =new ArrayList<Action>();

    public int getStepsTaken() {
        return stepsTaken;
    }

    public void setStepsTaken(int stepsTaken) {
        this.stepsTaken = stepsTaken;
    }

    private void add_action_to_takenactionlist (Action action_added){
        takenactions_list.add(action_added);
        for(int j=0;j<this.actualNumberOfShips;j++) {
            ships_objectlist.get(j).cyclicFunction();
        }
    }

    public World(boolean debugMode, boolean visualizeMode, Algorithm search_algo){
        this.debugMode=debugMode;
        this.visualizeMode = visualizeMode;
        this.numberOfSavedPassengers=0;
        this.numberOfSavedBoxes=0;
        this.numberOfSunkPassengers=0;
        this.numberOfSunkBoxes=0;
        this.totalNumberOfShipsPassengers=0;
        this.totalNumberOfShipsBoxes=0;
        this.actualNumberOfShips = 0;
        this.actualNumberOfStations =0;
        this.initalNumberOfPassengers=0;
        this.initalNumberOfBoxes=0;
        this.algorithm_enum = search_algo;
        setStepsTaken(0);
        MissionState missionStateObject=MissionState.RESCUE;


    }
    private void genGrid(int M, int N)
    {
        grid_object =new Grid(M,N);
    }

    private void genRescueBoat(int cg_capacity, int cg_x, int cg_y)throws Exception
    {
        rescueboat_object =new RescueBoat(grid_object, cg_capacity, cg_x, cg_y);
    }

    private void genShips(int num_ships, int[] shipsloc_list) throws Exception {
        this.actualNumberOfShips = num_ships;
        if (shipsloc_list.length % 3 != 0) {
        throw new Exception("Ship locations must be in groups of 3 (x,y,passengers)");
    }
        for (int i = 0; i < shipsloc_list.length && i/3 < num_ships; i += 3) {
        ships_objectlist.add(new Ship(grid_object, 
            shipsloc_list[i], 
            shipsloc_list[i+1], 
            shipsloc_list[i+2]));
    }
        for(int i=0;i<num_ships;i++){
            this.totalNumberOfShipsPassengers += ships_objectlist.get(i).getNumberOfPassengers();
            this.totalNumberOfShipsBoxes += 1;
        }

        this.initalNumberOfPassengers = this.totalNumberOfShipsPassengers ;
        this.initalNumberOfBoxes = this.totalNumberOfShipsBoxes ;

    }
    private void genStations(int num_stations, int [] stationloc_list)throws Exception{

//        this.actualNumberOfStations = (minNumberStations + (int)(Math.random() * (((grid_object.getM()* grid_object.getN()) - minNumberStations) + 1)));
        this.actualNumberOfStations = num_stations;
        for(int i=0;i<stationloc_list.length;i+=2){
            stations_objectlist.add(new Station(grid_object, stationloc_list[i], stationloc_list[i+1]));
        }
    }
    private boolean isMissionActive(){
        if((this.totalNumberOfShipsPassengers>0)||(this.totalNumberOfShipsBoxes>0)||(rescueboat_object.getNumberOfPassengers()>0)){
            return true;
        }
        else{
            missionStateObject=MissionState.DONE;
            return false;
        }
    }
    private int getShipItertor()throws Exception{
        for(int i=0;i<ships_objectlist.size();i++){
            if(ships_objectlist.get(i).getLocation().equals(rescueboat_object.getLocation())){
                return i;
            }

        }
        throw new Exception("none of the ship locations correspond to the same location whose the coast guard is at");
    }
    private int getStationItertor()throws Exception{
        for(int i=0;i<stations_objectlist.size();i++){
            if(stations_objectlist.get(i).getLocation().equals(rescueboat_object.getLocation())){
                return i;
            }
        }
        throw new Exception("none of the station locations correspond to the same location whose the coast guard is at");
    }
    private void retrievePassanegersFromShip()throws Exception{
        //make a check to see if you really made a pick up
        int iShip=getShipItertor();
        if(!(ships_objectlist.get(iShip).getShipVanished())){
            int availableRoomOnCoastGuard= rescueboat_object.getCapacity()- rescueboat_object.getNumberOfPassengers();
            int numberOfPassanegers=ships_objectlist.get(iShip).getNumberOfPassengers();
            // Case 1: the coast guard can save all passengers on the ship
            if(availableRoomOnCoastGuard>=numberOfPassanegers){
                ships_objectlist.get(iShip).removePassengers(numberOfPassanegers);
                rescueboat_object.addPassengers(numberOfPassanegers);
                takenactions_list.add(Action.PICKUP);
                // if an action was taken, so the ship status must be updated
                for(int i=0;i<ships_objectlist.size();i++){
                    ships_objectlist.get(i).cyclicFunction();
                }
                ships_objectlist.get(iShip).removeBlackBox();
                rescueboat_object.addBlackBox();
                takenactions_list.add(Action.RETRIEVE);
                // if an action was taken, so the ship status must be updated
                for(int i=0;i<ships_objectlist.size();i++){
                    ships_objectlist.get(i).cyclicFunction();
                }
                goalLoctaionList.clear();
            }
            // Case 2: the coast guard can save only some passengers on the ship
            else if (availableRoomOnCoastGuard>0){
                ships_objectlist.get(iShip).removePassengers(availableRoomOnCoastGuard);
                rescueboat_object.addPassengers(availableRoomOnCoastGuard);
                takenactions_list.add(Action.PICKUP);
                // if an action was taken, so the ship status must be updated
                for(int i=0;i<ships_objectlist.size();i++){
                    ships_objectlist.get(i).cyclicFunction();
                }
            }
            else
            {
                // in case the coast guard passes by the ship when it is full , the retrieve
                // action is not included within the list of action in the final because
                // I did not save anything
                // Do Nothing
            }
        }
        else{

            // in case the coast guard passes by a ship that is now vanished , the retrieve
            // action is not included within the list of action in the final because
            // I did not save anything
            // Do Nothing
        }
    }

    private void dropPassangersToStation()throws Exception{
        int iStation=getStationItertor();
        int numberOfPassangersOnCoastGuard= rescueboat_object.getNumberOfPassengers();
        int numberOfBlackBoxesOnCoastGuard= rescueboat_object.getNumberOfBlackBox();
        if (numberOfPassangersOnCoastGuard > 0 ||numberOfBlackBoxesOnCoastGuard > 0 ) {
            rescueboat_object.removePassengers(numberOfPassangersOnCoastGuard);
            rescueboat_object.removeBlackBox(numberOfBlackBoxesOnCoastGuard);
            stations_objectlist.get(iStation).addPassengers(numberOfPassangersOnCoastGuard);
            stations_objectlist.get(iStation).addBlackBox(numberOfBlackBoxesOnCoastGuard);
            takenactions_list.add(Action.DROP);
            for(int j=0;j<ships_objectlist.size();j++){
                ships_objectlist.get(j).cyclicFunction();
            }
        }
        else{
            // in case the coast guard passes by the station when it is empty , the drop
            // action is not included within the list of action in the final because
            // I did not drop anything
            // Do Nothing
        }

        goalLoctaionList.clear();
    }
    private void takeRetrieveDropAction()throws Exception{

        if(grid_object.isCoastGuardAtShip()){
            retrievePassanegersFromShip();
        }
        else if(grid_object.isCoastGuardAtStation()){
            dropPassangersToStation();
            }
        else{
        }
    }


    private void takeMotionAction() throws Exception {
       Strategy search_strategy = new Strategy(rescueboat_object.getLocation(),this.goalLoctaionList ,this.goalLocation, this.algorithm_enum);
        Stack<Action> taken_path = search_strategy.execute(grid_object.getM(), grid_object.getN());
        this.num_explored_nodes += search_strategy.getNumberofExploredNodes();
        this.goalLocation = search_strategy.getGoalLocationReached();
        while (!taken_path.isEmpty()){
                add_action_to_takenactionlist(taken_path.peek());
                taken_path.pop();
        }
//        System.out.println(takenactions_list);
        grid_object.moveObjectOnGrid(rescueboat_object,this.goalLocation);
    }



    /**
     * This function sets the location of the ship to save when there are more than one ship
     * The objective for the search algorithm is to find the path to the location of the ship
     * The selection method for the active ship could be either based on closest distance between ship and coast guard ,
     * ship with most passengers / any other criterion. For simplicity, closest distance is used.
     *
     * The ship has to not be vanished (eg has passengers or still have black boxes "wreck" ) to be a candidate for
     * active ship
     */
    private void setActiveShip(){
        double closestShipDistance=Double.MAX_VALUE ;
        int iActiveShip=0;
        for(int i=0;i<ships_objectlist.size();i++){
            double distanceToShip=util.dist(rescueboat_object.getLocation(),ships_objectlist.get(i).getLocation());
           if((!(ships_objectlist.get(i).getShipVanished()))&&(closestShipDistance>distanceToShip)){
              iActiveShip=i;
              closestShipDistance=distanceToShip;
           }
           if (!(ships_objectlist.get(i).getShipVanished()))
            {
                pair new_goal_pair = new pair<>(ships_objectlist.get(i).getLocation().getFirstValue(),
                        ships_objectlist.get(i).getLocation().getSecondValue());
                goalLoctaionList.add(new_goal_pair);
            }

        }

        goalLocation.setFirstValue(ships_objectlist.get(iActiveShip).getLocation().getFirstValue());
        goalLocation.setSecondValue(ships_objectlist.get(iActiveShip).getLocation().getSecondValue());


    }

    /**
     * This function sets the location of the station to drop when there are more than one station
     * The objective for the search algorithm is to find the path to the location of the station
     * The selection method for the active station is based on closest distance from coastguard.
     */
    private void setActiveStation(){
        double closestStationDistance=Double.MAX_VALUE ;
        int iActiveStation=0;
        for(int i=0;i<stations_objectlist.size();i++){
            double distanceToStation=util.dist(rescueboat_object.getLocation(),stations_objectlist.get(i).getLocation());
            if(closestStationDistance>distanceToStation){
                iActiveStation=i;
                closestStationDistance=distanceToStation;
            }
            pair new_goal_pair = new pair<>(stations_objectlist.get(i).getLocation().getFirstValue(),
                    stations_objectlist.get(i).getLocation().getSecondValue());
            goalLoctaionList.add(new_goal_pair);
        }
        goalLocation.setFirstValue(stations_objectlist.get(iActiveStation).getLocation().getFirstValue());
        goalLocation.setSecondValue(stations_objectlist.get(iActiveStation).getLocation().getSecondValue());
    }



    private void printSummary(){
        System.out.println("********************************");
        System.out.println("The Stations");
        for(int i=0;i<actualNumberOfStations;i++){
            System.out.println("code.Station: "+i);
            System.out.println("           Location : ("+ stations_objectlist.get(i).getLocation().getFirstValue() +" , " +  stations_objectlist.get(i).getLocation().getSecondValue() +")");
        }
        System.out.println("The ships:");
        for(int j=0;j<this.actualNumberOfShips;j++){
            System.out.println("code.Ship: "+j);
            System.out.println("        Location: ("+ ships_objectlist.get(j).getLocation().getFirstValue() + " , " + ships_objectlist.get(j).getLocation().getSecondValue() + ")");
            System.out.println("        Number of passengers: "+ ships_objectlist.get(j).getNumberOfPassengers());
            System.out.println("        Is code.Ship Sunk: "+ ships_objectlist.get(j).getShipWreck()); }
        System.out.println("code.CoastGuard");
        System.out.println("         code.CoastGuard Location: ("+ rescueboat_object.getLocation().getFirstValue() + " , " + rescueboat_object.getLocation().getSecondValue()+")");
        System.out.println("         code.CoastGuard Passengers:"+ rescueboat_object.getPassengers());
        System.out.println("         code.CoastGuard Capacity: "+ rescueboat_object.getCapacity());
        System.out.println("General Information");
        System.out.println("     total passengers: "+this.totalNumberOfShipsPassengers);
        System.out.println("     total boxes: "+this.totalNumberOfShipsBoxes);

    }

    private void printDoneSummary(){
        System.out.println("********************************");
        System.out.println("The Stations");
        for(int i=0;i<this.actualNumberOfStations;i++){
            System.out.println("Station: "+i);
            System.out.println("           Location : ("+ stations_objectlist.get(i).getLocation().getFirstValue() +" , " +  stations_objectlist.get(i).getLocation().getSecondValue() +")");
        }
        System.out.println("The ships:");
        for(int j=0;j<this.actualNumberOfShips;j++){
            System.out.println("Ship: "+j);
            System.out.println("        Location: ("+ ships_objectlist.get(j).getLocation().getFirstValue() + " , " + ships_objectlist.get(j).getLocation().getSecondValue() + ")");
            System.out.println("        Number of passengers: "+ ships_objectlist.get(j).getNumberOfPassengers());
            System.out.println("        Is Ship Sunk: "+ ships_objectlist.get(j).getShipWreck()); }
        System.out.println("CoastGuard");
        System.out.println("         CoastGuard Location: ("+ rescueboat_object.getLocation().getFirstValue() + " , " + rescueboat_object.getLocation().getSecondValue()+")");
        System.out.println("         CoastGuard Passengers:"+ rescueboat_object.getPassengers());
        System.out.println("         CoastGuard Capacity: "+ rescueboat_object.getCapacity());
        System.out.println("General Information");
        System.out.println("     total passengers: "+this.totalNumberOfShipsPassengers);
        System.out.println("     total boxes: "+this.totalNumberOfShipsBoxes);

        System.out.println("General Information");

        System.out.println("      Initial passengers: "+this.initalNumberOfPassengers);
        System.out.println("      Initial boxes: "+this.initalNumberOfBoxes);
        System.out.println("      Remaining passengers: "+this.totalNumberOfShipsPassengers);
        System.out.println("      Remaining boxes: "+this.totalNumberOfShipsBoxes);
        System.out.println("      saved passengers: "+this.numberOfSavedPassengers);
        System.out.println("      saved boxes: "+this.numberOfSavedBoxes);
        System.out.println("      sunk passengers: "+this.numberOfSunkPassengers);
        System.out.println("      sunk boxes: "+this.numberOfSunkBoxes);
        }


    private void updateWorld(){
        //total
        this.totalNumberOfShipsPassengers =0;
        this.totalNumberOfShipsBoxes = 0;

        //sunk
        this.numberOfSunkPassengers = 0;
        this.numberOfSunkBoxes = 0;

        //saved
        this.numberOfSavedPassengers = 0 ;
        this.numberOfSavedBoxes = 0;


        for(int i=0;i<ships_objectlist.size();i++){
            this.totalNumberOfShipsPassengers +=ships_objectlist.get(i).getNumberOfPassengers();
            this.numberOfSunkPassengers += ships_objectlist.get(i).getNumberOfSunkPassengers();
            this.numberOfSunkBoxes += ships_objectlist.get(i).getNumberOfSunkBoxes();
            if(!(ships_objectlist.get(i).getShipVanished())){
                this.totalNumberOfShipsBoxes+=1;
            }
        }

        for(int i=0;i<stations_objectlist.size();i++){
            this.numberOfSavedPassengers += stations_objectlist.get(i).getPassengers();
            this.numberOfSavedBoxes += stations_objectlist.get(i).getNumberOfBlackBox();
        }
    }

    private String gridToSting(){
        String grid=grid_object.getM()+","+ grid_object.getN()+";"+ rescueboat_object.getCapacity()+";"+ rescueboat_object.getLocation().getFirstValue()+","+ rescueboat_object.getLocation().getSecondValue()+";";
        String stationsString="";
        for(int i =0;i<stations_objectlist.size();i++){
            stationsString=stationsString+stations_objectlist.get(i).getLocation().getFirstValue()+","+stations_objectlist.get(i).getLocation().getSecondValue();

        }
        stationsString=stationsString+";";
        String shipsString="";
        for(int j=0;j<ships_objectlist.size();j++){
         shipsString=shipsString+ships_objectlist.get(j).getLocation().getFirstValue()+","+ships_objectlist.get(j).getLocation().getSecondValue()+","+ships_objectlist.get(j).getNumberOfPassengers();

        }
        shipsString=shipsString+";";

        grid=grid+stationsString+shipsString;

        return grid;
    }




    public void generate(int M,int N, int cg_capacity,int cg_x,int cg_y,int  num_stations, int [] stationloc_list, int num_ships, int [] shipsloc_list)throws Exception{
        genGrid(M,N);
        genRescueBoat(cg_capacity, cg_x, cg_y);
        genShips(num_ships,shipsloc_list);
        genStations(num_stations, stationloc_list);
        if(debugMode)
            printDoneSummary();
            new Display(grid_object , rescueboat_object, stations_objectlist , ships_objectlist, grid_object.isCoastGuardAtShip(), grid_object.isCoastGuardAtStation());
        if (visualizeMode)
            new Display(grid_object , rescueboat_object, stations_objectlist , ships_objectlist, grid_object.isCoastGuardAtShip(), grid_object.isCoastGuardAtStation());
    }

    public String solve() throws Exception{
        while(isMissionActive()){

            //Keep in rescue mode if there are still passengers/boxes on ship and there is still room on coast guard
            if(rescueboat_object.getPassengers()== rescueboat_object.getCapacity() || this.totalNumberOfShipsPassengers==0  || this.totalNumberOfShipsBoxes==0 ){
               missionStateObject=MissionState.DROPPING;

               setActiveStation();

           }
           else{
               missionStateObject=MissionState.RESCUE;
               setActiveShip();
           }
        if (debugMode)
        {
            //for debugging to act on the following printed information in the manual actions
            System.out.println(goalLocation.getFirstValue());
            System.out.println(goalLocation.getSecondValue());
            System.out.println(missionStateObject);
            new Display(grid_object , rescueboat_object, stations_objectlist , ships_objectlist, grid_object.isCoastGuardAtShip(), grid_object.isCoastGuardAtStation());
        }
        else
        {
            if (this.visualizeMode)
            {
                new Display(grid_object , rescueboat_object, stations_objectlist , ships_objectlist, grid_object.isCoastGuardAtShip(), grid_object.isCoastGuardAtStation());
            }
            takeMotionAction();
        }
        takeRetrieveDropAction();
        updateWorld();
//        printSummary();
       }
        if(missionStateObject==MissionState.DONE){
//            printDoneSummary();

//            System.out.println("************************");
//            System.out.println("Mission is Accomplished");
//            System.out.println("************************");
        }
        else{
            throw new Exception("mission state is neither active nor done");
        }
        //plan;deaths;retrieved;nodes
        // takenactions_list;numberOfSunkPassengers;numberOfSavedBoxes;anyDummyNumber
        return concatString();
    }


    public pair getGoalLocation() {
        return goalLocation;
    }

    public void setGoalLocation(pair goalLocation) {
        this.goalLocation = goalLocation;
    }

    public String convertListToString(ArrayList<Action> takenactions_list){
        String returnedString = "";
        for(int i=0; i< takenactions_list.size(); i++) {
            returnedString+=takenactions_list.get(i).toString().toLowerCase();
            if(i!= takenactions_list.size()-1)
                returnedString+=",";
        }
        return returnedString;
    }

    public String concatString(){
        StringJoiner joiner = new StringJoiner(";");
        joiner.add("Taken actions: " + convertListToString(takenactions_list));
        joiner.add("Number of Saved Passengers: " + String.valueOf(numberOfSavedPassengers));
        joiner.add("Number of Sunk Passengers: " + String.valueOf(numberOfSunkPassengers));
        joiner.add("Number of Saved Boxes: " + String.valueOf(numberOfSavedBoxes));

        String text = joiner.toString();
        System.out.println("Solution: " + text);
        return text;
    }
}
