package code;

public class Utils {
    public double dist(pair startLocation,pair endLocation){
     double sumFirstValues= Math.pow((endLocation.getFirstValue()-startLocation.getFirstValue()),2);
     double sumSecondValues= Math.pow((endLocation.getSecondValue()- startLocation.getSecondValue()),2);
     double result=sumFirstValues+sumSecondValues;
     return Math.sqrt(result);
    }

    public void plot(Grid grid){
     int [] [] data=new int[grid.getM()][grid.getN()];
     for(int i=0;i< grid.getM();i++){
         for(int j=0;j< grid.getN();j++){
             pair current=new pair(i,j);
             String objectLayingHere=grid.getCells().get(current);
             if(objectLayingHere.equals("code.Ship")){
               data[i][j]=1;

             }
             else if(objectLayingHere.equals("code.Station")){
                 data[i][j]=2;
             }
             else if(objectLayingHere.equals("code.CoastGuard")){
                 data[i][j]=3;
             }
             else if(objectLayingHere.equals("ShipCoastGuard")){
                 data[i][j]=4;
             }
             else if(objectLayingHere.equals("StationCoastGuard")){
                 data[i][j]=5;
             }
         }
        }





    }











}
