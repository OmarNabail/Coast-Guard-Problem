package code;

public class Station {
    int passengers=0;

    int blackBox=0;

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }



    public void setBlackBox(int blackBox) {
        this.blackBox = blackBox;
    }
    private pair location= new pair(0,0);

    public Station(Grid grid3, int station_x, int station_y)throws Exception{
//         location=grid3.fillCellsRandomly("code.Station");
        location=grid3.fillCells("Station", station_x, station_y);
        setPassengers(0);
         setBlackBox(0);

    }

    public pair getLocation() {
        return location;
    }

    public void setLocation(pair location) {
        this.location = location;
    }
    public void addPassengers(int numberOfPassengersAdded){
        this.passengers=this.passengers+numberOfPassengersAdded;
    }
    public int getPassengers(){
        return this.passengers;
    }
    public void addBlackBox(int numberOfBlackBoxAdded){
        this.blackBox=this.blackBox+numberOfBlackBoxAdded;
    }
    public int getNumberOfBlackBox(){
        return this.blackBox;
    }

}
