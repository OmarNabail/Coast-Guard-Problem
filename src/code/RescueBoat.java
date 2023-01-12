package code;

public class RescueBoat {
    private int maxCoastGuardCap=100;
    private int minCoastGuardCap=30;
    private int capacity;
    private int passengers;
    private int blackBox;
    private String objectString="CoastGuard";
    private pair location= new pair(0,0);

    public int getBlackBox() {
        return blackBox;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setBlackBox(int blackBox) {
        this.blackBox = blackBox;
    }

    public pair getLocation() {
        return location;
    }

    public void setLocation(pair location) {
        this.location.setFirstValue(location.getFirstValue());
        this.location.setSecondValue(location.getSecondValue());
    }


    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public RescueBoat(Grid grid1, int cg_capacity, int cg_x , int cg_y) throws Exception {
//        location=grid1.fillCellsRandomly ("code.CoastGuard");
//        setCapacity(minCoastGuardCap + (int)(Math.random() * ((maxCoastGuardCap - minCoastGuardCap) + 1)));
        pair temp_pair = new pair<>(0,0);
        temp_pair=grid1.fillCells("CoastGuard" , cg_x, cg_y);
        this.location.setFirstValue(temp_pair.getFirstValue());
        this.location.setSecondValue(temp_pair.getSecondValue());

        setCapacity(cg_capacity);

        setPassengers(0);
        setBlackBox(0);
        setObjectString("CoastGuard");

    }

    public void addPassengers(int numberOfPassengersAdded){

        this.passengers=this.passengers+numberOfPassengersAdded;
    }
    public void removePassengers(int numberOfPassengersRemoved){
        this.passengers=this.passengers-numberOfPassengersRemoved;
    }
    public int getNumberOfPassengers(){
        return this.passengers;
    }
    public void addBlackBox(){
        this.blackBox=this.blackBox+1;
    }
    public void removeBlackBox(int numberOfBlackBox){
        this.blackBox=this.blackBox-numberOfBlackBox;
    }
    public int getNumberOfBlackBox(){
        return this.blackBox;
    }

    public String getObjectString() {
        return objectString;
    }

    public void setObjectString(String objectString) {
        this.objectString = objectString;
    }


    public static void main(String[] args)
    {
    }

}
