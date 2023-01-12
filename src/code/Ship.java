package code;

public class Ship {

    private boolean debugMode=false;
    private int maxBlackBoxLife=20;
    private int maxShipCap=100;
    private int minShipCap=1;
    private boolean stateWreck=false;
    private int numberOfPassengers=minShipCap + (int)(Math.random() * ((maxShipCap - minShipCap) + 1));
    private int blackBoxLife=maxBlackBoxLife;
    public pair getLocation() {
        return location;
    }

    public void setLocation(pair location) {
        this.location = location;
    }
    private pair location=new pair(0,0);

    private boolean shipVanished=false;
    private int numberOfSunkPassengers=0;
    private int numberOfSunkBoxes=0;
   public void removePassengers(int numberRemoved){
       this.numberOfPassengers=this.numberOfPassengers-numberRemoved;
   }
   public int getNumberOfPassengers(){
       return this.numberOfPassengers;
   }
   public int getNumberOfSunkPassengers(){
       return this.numberOfSunkPassengers;
   }
   public void removeBlackBox(){
       this.blackBoxLife=0;
       this.shipVanished=true;
   }

    public int getBlackBoxLife() {
        return this.blackBoxLife;
    }

    public int getNumberOfSunkBoxes() {
        return this.numberOfSunkBoxes;
    }
    public boolean getShipWreck(){
       return this.stateWreck;
    }
    public boolean getShipVanished(){
       return this.shipVanished;
    }

    public void setStateWreck(boolean stateWreck) {
        this.stateWreck = stateWreck;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public boolean isShipVanished() {
        return shipVanished;
    }

    public void setShipVanished(boolean shipVanished) {
        this.shipVanished = shipVanished;
    }

    public void setNumberOfSunkPassengers(int numberOfSunkPassengers) {
        this.numberOfSunkPassengers = numberOfSunkPassengers;
    }

    public boolean isStateWreck() {
        return stateWreck;
    }

    public void setNumberOfSunkBoxes(int numberOfSunkBoxes) {
        this.numberOfSunkBoxes = numberOfSunkBoxes;
    }

    public Ship(Grid grid4, int ship_x, int ship_y, int ship_passengers)throws Exception{

         this.location=grid4.fillCells("Ship", ship_x , ship_y);
         setStateWreck(false);
//             setNumberOfPassengers(3 + (int)(Math.random() * ((20 - 3) + 1)));
         setNumberOfPassengers(ship_passengers);
         setBlackBoxLife(maxBlackBoxLife);
         setShipVanished(false);
         setNumberOfSunkPassengers(0);
         setNumberOfSunkBoxes(0);

    }

    public void setBlackBoxLife(int blackBoxLife) {
        this.blackBoxLife = blackBoxLife;
    }
    // there is a bug here, the ship keeps updating to sinking after being rescued
    public void cyclicFunction(){
       if(this.numberOfPassengers>0){
         this.numberOfPassengers=this.numberOfPassengers-1;
         this.numberOfSunkPassengers=this.numberOfSunkPassengers+1;
         if(this.numberOfPassengers==0){
             this.stateWreck=true;
         }
         else{
             this.stateWreck=false;
         }
       }
       else if(this.blackBoxLife>0){
           this.stateWreck=true;
           this.blackBoxLife=this.blackBoxLife-1;
           if(this.blackBoxLife==0){
               this.shipVanished=true;
               this.numberOfSunkBoxes=1;
           }
       }
       else{
           this.shipVanished=true;
       }
    }




public static void main(String[] args){

}


}
