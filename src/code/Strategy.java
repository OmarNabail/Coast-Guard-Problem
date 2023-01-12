package code;

import java.util.*;
public class Strategy {

    Algorithm strategyName=Algorithm.NONE;
    Node startNode;
    Node goalNode;
    Algorithm search_method;
    ArrayList<Node> explored=null;
    ArrayList<pair> list_of_goal_states =new ArrayList<>();
    Stack<Action> shortest_path=new Stack<>();



    public Algorithm getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(Algorithm strategyName) {
        this.strategyName = strategyName;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }
    public int blackBoxHeuristicFunc(Node current){
        int estimatedHealthOfBlackBox=20;
        int estimatedStepsTaken=0;
        int heuristicCost=0;
        int xDistance= goalNode.getState().getFirstValue()-current.getState().getFirstValue();
        int yDistance= goalNode.getState().getSecondValue()- current.getState().getSecondValue();
        estimatedStepsTaken=Math.abs(xDistance)+Math.abs(yDistance);
        estimatedHealthOfBlackBox=estimatedHealthOfBlackBox-estimatedStepsTaken;
        if(estimatedHealthOfBlackBox<=0){
            return 1000;
        }
        else{heuristicCost=20-estimatedHealthOfBlackBox;}
        return heuristicCost;
    }
    public int deathsHeuristicFunc(Node current){
        int estimatedDeaths=0;
        int estimatedStepsTaken=0;
        int xDistance= goalNode.getState().getFirstValue()-current.getState().getFirstValue();
        int yDistance= goalNode.getState().getSecondValue()- current.getState().getSecondValue();
        estimatedStepsTaken=Math.abs(xDistance)+Math.abs(yDistance);
        estimatedDeaths=estimatedDeaths+estimatedStepsTaken;
        return estimatedDeaths;
    }
 
 
 
 
    public Strategy(pair start_loc,ArrayList<pair> goal_loc_list,pair goal_loc,Algorithm search_method){
        this.search_method = search_method;
        this.explored = new ArrayList<Node>();
        this.list_of_goal_states = goal_loc_list;
        switch (this.search_method) {
            case BFS:
                this.startNode = new Node(start_loc,null , Action.NONE ,0, 0, 0);
                this.goalNode = new Node(goal_loc,null ,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case DFS:
                this.startNode = new Node(start_loc,null,Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case IDS:
                this.startNode = new Node(start_loc,null,Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case GREEDY:
                this.startNode = new Node(start_loc,null, Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case GREEDY2:
                this.startNode = new Node(start_loc,null, Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case ASTAR:
                this.startNode = new Node(start_loc,null,Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            case ASTAR2:
                this.startNode = new Node(start_loc,null,Action.NONE , 0, 0, 0);
                this.goalNode = new Node(goal_loc,null,Action.NONE , 0, 0, 0);
                this.strategyName = search_method;
                break;
            default:
                throw new IllegalArgumentException("Invalid algoithm");
        }
    }

    private Node getPreviousNode(pair current_parent_pair)
    {
        Node last_node = null;
        for (int i =0 ; i < explored.size() ; i++)
        {
            if (explored.get(i).getState().getFirstValue() == current_parent_pair.getFirstValue()
                &&
                    explored.get(i).getState().getSecondValue() == current_parent_pair.getSecondValue())
            {
                last_node = explored.get(i);
            }
        }
    return last_node;
    }

    private void getPathtoGoalNode(Node last_node) throws Exception {
        while (! (last_node.getState().getFirstValue() ==  this.startNode.getState().getFirstValue()) ||
                ! (last_node.getState().getSecondValue() ==  this.startNode.getState().getSecondValue()))
        {
//            System.out.println(last_node.getState().ToString());
            pair current_parent_pair = last_node.getParent();
            this.shortest_path.add(last_node.getAction_taken_to_reach());
            last_node = getPreviousNode(current_parent_pair);
            if (last_node == null)
            {
                throw new Exception("The parent of the current node did not match any node from the explored nodes");
            }
        }
//        System.out.println(this.shortest_path);
    }

    private boolean GR1_exceute(int grid_size_M,int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        PriorityQueue<Node> queueNode = new PriorityQueue<Node>(grid_size_M*grid_size_N,new Heuristic1Comparator());
        queueNode.add(this.startNode);
        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        while(!queueNode.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current=queueNode.peek();
            if (is_current_state_a_goal_state(current)){
                reached_goal_state = true;
                getPathtoGoalNode(current);

//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                while(queueNode.isEmpty())
//                    wait();
                return reached_goal_state;
            }
            else
            {
                queueNode.remove();

                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState() , Action.UP , cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
            }

        }


        return reached_goal_state;
    }


    private boolean GR2_exceute(int grid_size_M,int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        PriorityQueue<Node> queueNode = new PriorityQueue<Node>(grid_size_M*grid_size_N,new Heuristic2Comparator());
        queueNode.add(this.startNode);
        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        while(!queueNode.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current=queueNode.peek();
            if(current.getState().getFirstValue() == goalNode.getState().getFirstValue() && current.getState().getSecondValue() == goalNode.getState().getSecondValue()){
                reached_goal_state = true;
                getPathtoGoalNode(current);

//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                return reached_goal_state;
            }
            else
            {
                queueNode.remove();

                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState() , Action.UP , cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective));
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
            }

        }


        return reached_goal_state;
    }

    private boolean AS1_exceute(int grid_size_M,int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        PriorityQueue<Node> queueNode = new PriorityQueue<Node>(grid_size_M*grid_size_N,new Heuristic1Comparator());
        queueNode.add(this.startNode);
        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        while(!queueNode.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current=queueNode.peek();
            if(current.getState().getFirstValue() == goalNode.getState().getFirstValue() && current.getState().getSecondValue() == goalNode.getState().getSecondValue()){
                reached_goal_state = true;
                getPathtoGoalNode(current);

//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                return reached_goal_state;
            }
            else
            {
                queueNode.remove();

                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState() , Action.UP , cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost1(blackBoxHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
            }

        }


        return reached_goal_state;
    }

    private boolean AS2_exceute(int grid_size_M,int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        PriorityQueue<Node> queueNode = new PriorityQueue<Node>(grid_size_M*grid_size_N,new Heuristic2Comparator());
        queueNode.add(this.startNode);
        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        while(!queueNode.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current=queueNode.peek();
            if(current.getState().getFirstValue() == goalNode.getState().getFirstValue() && current.getState().getSecondValue() == goalNode.getState().getSecondValue()){
                reached_goal_state = true;
                getPathtoGoalNode(current);

//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                return reached_goal_state;
            }
            else
            {
                queueNode.remove();

                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState() , Action.UP , cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    prospective.setHeuristicCost2(deathsHeuristicFunc(prospective)+cummPathCost);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
            }

        }


        return reached_goal_state;
    }

    private boolean ids_execute(int grid_size_M , int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        Stack<Node> nodeStack= new Stack<Node>();
        nodeStack.push(this.startNode);

        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        int depth=0;
        int max_depth=naviagation_grid.getM()* naviagation_grid.getN();

        while(!nodeStack.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current= nodeStack.peek();
            if(depth<max_depth){
                depth ++;
                if (is_current_state_a_goal_state(current)){
                    reached_goal_state = true;
                    getPathtoGoalNode(current);
//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                    return reached_goal_state;
                }
                else
                {
                    nodeStack.pop();
                    //Explore Action in all directions
                    //Move Up
                    new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                    if (new_location != null ) {
                        Node prospective = new Node(new_location,current.getState(),Action.UP , cummPathCost, 0, 0);
                        nodeStack.push(prospective);
                        explored.add(prospective);
                    }
                    //Move Down
                    new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                    if (new_location != null ) {
                        Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                        nodeStack.push(prospective);
                        explored.add(prospective);
                    }
                    //Move Left
                    new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                    if (new_location != null) {
                        Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                        nodeStack.push(prospective);
                        explored.add(prospective);
                    }

                    //Move Right
                    new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                    if (new_location != null) {
                        Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                        nodeStack.push(prospective);
                        explored.add(prospective);
                    }
                }}
            else{
                throw new Exception("target isn't within this limit");
            }

        }
        return reached_goal_state;
    }


    private boolean bfs_execute(int grid_size_M , int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        Queue<Node> queueNode= new LinkedList<Node>();
        queueNode.add(this.startNode);

        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;
        while(!queueNode.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current=queueNode.peek();
            if (is_current_state_a_goal_state(current)){
                reached_goal_state = true;
                getPathtoGoalNode(current);

//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                return reached_goal_state;
            }
            else
            {
                queueNode.remove();

                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState() , Action.UP , cummPathCost, 0, 0);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    queueNode.add(prospective);
                    explored.add(prospective);
                }
            }

        }
        return reached_goal_state;
    }

    private boolean is_current_state_a_goal_state(Node current_node)
    {
        boolean result = false;
        for (int i = 0; i < list_of_goal_states.size() ; i++)
        {
        if(current_node.getState().getFirstValue() == list_of_goal_states.get(i).getFirstValue() && current_node.getState().getSecondValue() == list_of_goal_states.get(i).getSecondValue())
            {
                result = true;
                this.goalNode.setState(list_of_goal_states.get(i));
                return result;
            }
        }
            return result;
    }


    public pair getGoalLocationReached()
    {
        return this.goalNode.getState();
    }

    private boolean dfs_execute(int grid_size_M , int grid_size_N) throws Exception {
        Grid naviagation_grid = new Grid(grid_size_M, grid_size_N);
        boolean reached_goal_state = false;
        Stack<Node> nodeStack= new Stack<Node>();
        nodeStack.push(this.startNode);

        // Add node to the list of explored nodes
        this.explored.add(this.startNode);
        naviagation_grid.getCells().put(this.startNode.getState(),"Full");

        int cummPathCost = 0;

        while(!nodeStack.isEmpty()){
            pair new_location = null;
            cummPathCost ++;
            Node current= nodeStack.peek();
            if (is_current_state_a_goal_state(current)){
                reached_goal_state = true;
                getPathtoGoalNode(current);
//                System.out.println(this.shortest_path);
//                while(new_location == null)
//                {
//                    System.out.println(1);
//                }
//                System.exit(0);
                return reached_goal_state;
            }
            else
            {
                nodeStack.pop();
                //Explore Action in all directions
                //Move Up
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.UP);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.UP , cummPathCost, 0, 0);
                    nodeStack.push(prospective);
                    explored.add(prospective);
                }
                //Move Down
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.DOWN);
                if (new_location != null ) {
                    Node prospective = new Node(new_location,current.getState(),Action.DOWN, cummPathCost, 0, 0);
                    nodeStack.push(prospective);
                    explored.add(prospective);
                }
                //Move Left
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.LEFT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.LEFT ,cummPathCost, 0, 0);
                    nodeStack.push(prospective);
                    explored.add(prospective);
                }

                //Move Right
                new_location = naviagation_grid.exploreMotionAction(current.getState(), Action.RIGHT);
                if (new_location != null) {
                    Node prospective = new Node(new_location,current.getState(),Action.RIGHT ,cummPathCost, 0, 0);
                    nodeStack.push(prospective);
                    explored.add(prospective);
                }
            }

        }
        return reached_goal_state;
    }

    public int getNumberofExploredNodes()
    {
        return (explored.size());
    }

    public Stack<Action> execute( int grid_size_M , int grid_size_N) throws Exception {
        boolean execute_state = false;
        switch (search_method) {
            case BFS:
                execute_state =  this.bfs_execute(grid_size_M,grid_size_N);
                break;
            case DFS:
                execute_state =  this.dfs_execute(grid_size_M,grid_size_N);
                break;
            case IDS:
                execute_state =  this.ids_execute(grid_size_M,grid_size_N);
                break;
            case GREEDY:
                execute_state = this.GR1_exceute(grid_size_M,grid_size_N);
                break;
            case GREEDY2:
                execute_state = this.GR2_exceute(grid_size_M,grid_size_N);
                break;
            case ASTAR:
                execute_state = this.AS1_exceute(grid_size_M,grid_size_N);
                break;
            case ASTAR2:
                execute_state = this.AS2_exceute(grid_size_M,grid_size_N);
                break;
            default:
                throw new IllegalArgumentException("Invalid algoithm");
        }
    return this.shortest_path;
    }




}
class Heuristic1Comparator implements Comparator<Node>{

    // Overriding compare()method of Comparator
    // for ascending order of HeuristicCost1
    public int compare(Node n1, Node n2) {
        if (n1.getHeuristicCost1() > n2.getHeuristicCost1())
            return 1;
        else if (n1.getHeuristicCost1() < n2.getHeuristicCost1())
            return -1;
        return 0;
    }
}

class Heuristic2Comparator implements Comparator<Node>{

    // Overriding compare()method of Comparator
    // for ascending order of HeuristicCost2
    public int compare(Node n1, Node n2) {
        if (n1.getHeuristicCost2() > n2.getHeuristicCost2())
            return 1;
        else if (n1.getHeuristicCost2() < n2.getHeuristicCost2())
            return -1;
        return 0;
    }
}
