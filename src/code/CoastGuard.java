package code;

public class CoastGuard {

    /**
     *
     * @param world_description : String describing the world including the grid dimensions, number and
     *                          locations of ships and stations
     * @param search_algo_str    : String stating which search method to use (eg bfs, dfs, ids)
     * @param visualize_mode   : boolean to decide to visualize or not
     * @return                 : string of the solution to the grid including the number of death, saved people
     * @throws Exception
     *
     * Description:
     * The solve function parses the information from the test cases and passes them to the world class
     * The world constructor handles the declaration of the necessary variables  and initializes them
     * The world generate function handles the creation of the grid , ships list and stations with the parameters
     * provided from the test cases
     * The world solve function handles the solving of the search problem and returns the solution to the test case
     * provided
     */
    public static String solve(String world_description, String search_algo_str, boolean visualize_mode)throws Exception{

        String[] arrofInputs = world_description.split(";");

        //Grid Dimensions
        int M = Integer.parseInt(arrofInputs[0].split(",")[0]);
        int N = Integer.parseInt(arrofInputs[0].split(",")[1]) ;

        //CoastGuard Capacity
        int coastguard_capacity = Integer.parseInt(arrofInputs[1]);

        //CoastGuard
        int cg_x = Integer.parseInt(arrofInputs[2].split(",")[0]);
        int cg_y = Integer.parseInt(arrofInputs[2].split(",")[1]);

        //Stations Locations
        String [] stationloc_list_string =arrofInputs[3].split(",");
        int num_stations = stationloc_list_string.length / 2 ;
        int[] stationloc_list = new int [stationloc_list_string.length] ;
        // Convert String locations to int locations
        for(int i=0;i<stationloc_list_string.length;i++){
            stationloc_list[i] = Integer.parseInt(stationloc_list_string[i]);
        }


        //Ships Locations
        String [] shipsloc_list_string =arrofInputs[4].split(",");
        int num_ships = shipsloc_list_string.length / 3 ;
        int[] shipsloc_list = new int [shipsloc_list_string.length] ;
        // Convert String locations to int locations
        for(int i=0;i<shipsloc_list_string.length;i++){
            shipsloc_list[i] = Integer.parseInt(shipsloc_list_string[i]);
        }


        Algorithm search_algo = Algorithm.NONE;
        switch (search_algo_str) {
            case "BF":
                search_algo = Algorithm.BFS;
                break;
            case "DF":
                search_algo = Algorithm.DFS;
                break;
            case "ID":
                search_algo = Algorithm.IDS;
                break;
            case "GR1":
                search_algo = Algorithm.GREEDY;
                break;
            case "GR2":
                search_algo = Algorithm.GREEDY2;
                break;
            case "AS1":
                search_algo = Algorithm.ASTAR;
                break;
            case "AS2":
                search_algo = Algorithm.ASTAR2;
                break;

            default:
                throw new IllegalArgumentException("Invalid algoithm: " + search_algo_str);
        }


        World world=new World(false , visualize_mode , search_algo);

        world.generate(M, N,coastguard_capacity, cg_x, cg_y, num_stations, stationloc_list, num_ships, shipsloc_list);
        return world.solve();

//        String solution = "dummy_return";
//        return solution;
    }
    public static void main(String[] args) {
    try {
    // Input format: "GridSize; BoatCapacity; BoatStartPos; Stations; Ships position and Number of Passensgers"
    String input = "5,5;5;0,0;2,2;3,3,10";          
    String strategy = "AS2"; 
        boolean visualize = false;
        CoastGuard.solve(input, strategy, visualize);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}

}
