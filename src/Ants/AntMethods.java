package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntFindPath.findShortestPath;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.Graph;
import board.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bbalt
 */
public class AntMethods {
    

    public int totalFoodInFront(List<ILocationInfo> visibleLocations) {
        int foodCount = 0;
        foodCount = visibleLocations.stream().map((loc) -> loc.getFoodCount()).reduce(foodCount, Integer::sum); // hvad betyder dette?
        return foodCount;
    }

    public static EAction findUndiscoveredFood(List<EAction> possibleActions, ILocationInfo thisLocation,Queen queen,IAntInfo thisAnt, Graph graph,List<ILocationInfo> visibleLocations){
        Node targetNode = null;
        List <Node> shortestPathToUndiscovered = null;
        List <Node> tempRoute = null;
        
        List<Node> undiscoveredNodes = new ArrayList();
        for( Node node : graph.getNodes() ){
            if( node.isDiscovered() == false ){
                undiscoveredNodes.add(node);
            }
        }
        for(Node node : undiscoveredNodes ){
            tempRoute = findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ),node ,graph );
            try{
                if( shortestPathToUndiscovered == null || tempRoute.size() < shortestPathToUndiscovered.size() )
                    shortestPathToUndiscovered = tempRoute;
            }catch(NullPointerException e){
                System.out.println("exception: "+e);
            }
        }
        try{
            targetNode =shortestPathToUndiscovered.get(shortestPathToUndiscovered.size()-1 );
        }catch(Exception nullPointer){
            System.out.println("nullPointer Exception: "+nullPointer);
        }
       
        if(targetNode != null){
            return NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() ) , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph, possibleActions);
        }else{
            return walkAround( possibleActions, thisLocation, queen,  thisAnt );
        }
    }
    
    public static EAction walkAround(List<EAction> possibleActions, ILocationInfo thisLocation,Queen queen,IAntInfo thisAnt){
        if( thisAnt.getAntType() == EAntType.CARRIER ){
            List<EAction> actions = new ArrayList();
            
            if(possibleActions.contains(EAction.MoveForward)){
                actions.add(EAction.MoveForward);
                actions.add(EAction.MoveForward);
                actions.add(EAction.MoveForward);
                actions.add(EAction.MoveForward);
            }
                
            if(possibleActions.contains(EAction.TurnLeft))
                actions.add(EAction.TurnLeft);
            
            if(possibleActions.contains(EAction.TurnRight))
                actions.add(EAction.TurnRight);
            
            if( !actions.isEmpty() ){
                Random rand = new Random();
                int x = rand.nextInt( actions.size() );

                try{
                    return  actions.get( rand.nextInt( actions.size() )  );
                }catch(Exception e){
                    System.out.println("ERROR: "+ e.toString());
                }
                return EAction.Pass;
            }
        }
        return EAction.Pass;
     }
     
    public static List<Node> homeNodes(Graph g, int startPos, int starX, int starY, IAntInfo thisAnt) {
        List<Node> listOfBlockedNodes = new ArrayList();
        if(startPos==1){
            for(int x = starX; x <= starX+2; x++) {
                for(int y = starY; y <= starY+2; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==2) {
            for(int x = starX; x <= starX+2; x++) {
                for(int y = starY-2; y <= starY; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==3) {
            for(int x = starX-2; x <= starX; x++) {
                for(int y = starY; y <= starY+2; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==4) {
            for(int x = starX-2; x <= starX; x++) {
                for(int y = starY-2; y <= starY; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        return listOfBlockedNodes;
    }
     
    public static boolean isInQueenArea(Node node, int startPos, Graph graph){
         
         if(startPos == 1){
             if( node.getXPos() < 3 && node.getYPos() < 3  ){ 
                 return true;// South West
             }
         }
         if(startPos == 2){
             if( node.getXPos() < 3 && node.getYPos() > graph.getWorldSizeY()-4 ){
                 return true;// North West
             }
         }
         if(startPos == 3){
             if( node.getXPos() > graph.getWorldSizeX()-4 && node.getYPos() < 3 ){
                 return true;// South East
             }
         }
         if(startPos == 4){
             if( node.getXPos() > graph.getWorldSizeX()-4 && node.getYPos() > graph.getWorldSizeY()-4 ){
                 return true;// North east
             }
         }
         return false;
    }
     
    public static Node moveAwayFromQueenArea(int startPos, Graph graph, ILocationInfo thisLocation){
        
        if(startPos == 1){ // nede venstre
            return graph.getNode( graph.getWorldSizeX()-1, graph.getWorldSizeY()-1 );
        }
        if(startPos == 2){// oppe venstre
            return graph.getNode( graph.getWorldSizeX()-1 , 0 );
        }
        if(startPos == 3){// nede højre
            return graph.getNode( 0, graph.getWorldSizeY()-1 );
        }
        if(startPos == 4){ // oppe højre
            return graph.getNode(0, 0);
        }
        else return graph.getNode(graph.getWorldSizeX()/2, graph.getWorldSizeY()/2 );
    }
    
    public static boolean isInWallArea(Node node, int startPos, Graph graph){
        
        List<Node> gate = gateOneLocation( graph, startPos);
        for( Node n : gate ){
            if( node.getXPos() == n.getXPos() &&  node.getYPos() == n.getYPos() )
                return false;
        }
        
        if(startPos == 1){
             if(  !isInQueenArea( node,startPos,graph ) && node.getXPos() < 4 && node.getYPos() < 4  ){ 
                 return true;// South West
             }
         }
         if(startPos == 2){
             if(  !isInQueenArea( node,startPos,graph )  &&  node.getXPos() < 4  && node.getYPos() > graph.getWorldSizeY()-5){
                 return true;// North West
             }
         }
         if(startPos == 3){
             if(  !isInQueenArea( node,startPos,graph )  &&  node.getXPos() > graph.getWorldSizeX()-5 && node.getYPos() < 4 ){
                 return true;// South East
             }
         }
         if(startPos == 4){
             if(  !isInQueenArea( node,startPos,graph )  &&  node.getXPos() > graph.getWorldSizeX()-5 && node.getYPos() > graph.getWorldSizeY()-5){
                 return true;// North east
             }
         }
        return false;
    }
    
    public static boolean IsInOuterWallArea(Node node, int startPos, Graph graph){
        
        if(startPos == 1){
             if(  !isInQueenArea( node,startPos,graph ) && !isInWallArea( node,startPos,graph )  && node.getXPos() < 5 && node.getYPos() < 5){ 
                 return true;// South West
             }
         }
         if(startPos == 2){
             if(  !isInQueenArea( node,startPos,graph )  &&  !isInWallArea( node,startPos,graph )  && node.getXPos() < 5  && node.getYPos() > graph.getWorldSizeY()-6){
                 return true;// North West
             }
         }
         if(startPos == 3){
             if(  !isInQueenArea( node,startPos,graph )  &&  !isInWallArea( node,startPos,graph )  && node.getXPos() > graph.getWorldSizeX()-6 && node.getYPos() < 5 ){
                 return true;// South East
             }
         }
         if(startPos == 4){
             if(  !isInQueenArea( node,startPos,graph )  &&  !isInWallArea( node,startPos,graph )  &&  node.getXPos() > graph.getWorldSizeX()-6 && node.getYPos() > graph.getWorldSizeY()-6){
                 return true;// North east
             }
         }
        return false;
        
    }
    
    public static boolean isGateOneDiscovered(Graph graph,int startPos){
    
        Node n1 = null, n2 = null, n3 = null;
        if( startPos == 1 ){// South West
            n1  = graph.getNode(2, 1);
            n2  = graph.getNode(3, 1);
            n3  = graph.getNode(4, 1);
        }
        if(startPos == 2){// North West 
            n1  = graph.getNode(1, graph.getWorldSizeY()-3);//-3 fordi worldsize ex = 20 så er node hedder 19 -> i dette tilfælde 1,17
            n2  = graph.getNode(1, graph.getWorldSizeY()-4);
            n3  = graph.getNode(1, graph.getWorldSizeY()-5);
        }
        if(startPos == 3){// South East
            n1  = graph.getNode(graph.getWorldSizeX()-2, 2);
            n2  = graph.getNode(graph.getWorldSizeX()-2, 3);
            n3  = graph.getNode(graph.getWorldSizeX()-2, 4);
        }
         if(startPos == 4){// North east
            n1  = graph.getNode(graph.getWorldSizeX()-3, graph.getWorldSizeY()-2);
            n2  = graph.getNode(graph.getWorldSizeX()-4, graph.getWorldSizeY()-2);
            n3  = graph.getNode(graph.getWorldSizeX()-5, graph.getWorldSizeY()-2);
        }
        if(n1.isDiscovered() && n2.isDiscovered() && n3.isDiscovered())
            return true;
        else return false;
    }
      
    public static boolean isGateTwoDiscovered(Graph graph,int startPos){
        
        Node n1 = null, n2 = null, n3 = null;
        if(startPos == 1){// South West
            n1  = graph.getNode(1, 2);
            n2  = graph.getNode(1, 3);
            n3  = graph.getNode(1, 4);
        }
        if(startPos == 2){// North West 
            n1  = graph.getNode(2, graph.getWorldSizeY()-2);
            n2  = graph.getNode(3, graph.getWorldSizeY()-2);
            n3  = graph.getNode(4, graph.getWorldSizeY()-2);
        }
        if(startPos == 3){// South East
            n1  = graph.getNode(graph.getWorldSizeX()-3, 1);
            n2  = graph.getNode(graph.getWorldSizeX()-4, 1);
            n3  = graph.getNode(graph.getWorldSizeX()-5, 1);
        }
         if(startPos == 4){// North east
            n1  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-3);
            n2  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-4);
            n3  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-5);
        }
        if(n1.isDiscovered() && n2.isDiscovered() && n3.isDiscovered())
            return true;
        else return false;
    }
  
    public static List<Node> gateOneLocation(Graph graph,int startPos){
        
         Node n1 = null, n2 = null, n3 = null;
        if(startPos == 1){// South West
            n1  = graph.getNode(2, 1);
            n2  = graph.getNode(3, 1);
            n3  = graph.getNode(4, 1);
        }
        if(startPos == 2){// North West 
            n1  = graph.getNode(1, graph.getWorldSizeY()-3);//-3 fordi worldsize ex = 20 så er node hedder 19 -> i dette tilfælde 1,17
            n2  = graph.getNode(1, graph.getWorldSizeY()-4);
            n3  = graph.getNode(1, graph.getWorldSizeY()-5);
        }
        if(startPos == 3){// South East
            n1  = graph.getNode(graph.getWorldSizeX()-2, 2);
            n2  = graph.getNode(graph.getWorldSizeX()-2, 3);
            n3  = graph.getNode(graph.getWorldSizeX()-2, 4);
        }
         if(startPos == 4){// North east
            n1  = graph.getNode(graph.getWorldSizeX()-3, graph.getWorldSizeY()-2);
            n2  = graph.getNode(graph.getWorldSizeX()-4, graph.getWorldSizeY()-2);
            n3  = graph.getNode(graph.getWorldSizeX()-5, graph.getWorldSizeY()-2);
        }
         
        List res = new ArrayList();
        res.add(n1);
        res.add(n2);
        res.add(n3);
        
        return res;
    }
    
    public static List<Node> gateTwoLocation(Graph graph,int startPos){
        
         Node n1 = null, n2 = null, n3 = null;
        if(startPos == 1){// South West
            n1  = graph.getNode(1, 2);
            n2  = graph.getNode(1, 3);
            n3  = graph.getNode(1, 4);
        }
        if(startPos == 2){// North West 
            n1  = graph.getNode(2, graph.getWorldSizeY()-2);
            n2  = graph.getNode(3, graph.getWorldSizeY()-2);
            n3  = graph.getNode(4, graph.getWorldSizeY()-2);
        }
        if(startPos == 3){// South East
            n1  = graph.getNode(graph.getWorldSizeX()-3, 1);
            n2  = graph.getNode(graph.getWorldSizeX()-4, 1);
            n3  = graph.getNode(graph.getWorldSizeX()-5, 1);
        }
         if(startPos == 4){// North east
            n1  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-3);
            n2  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-4);
            n3  = graph.getNode(graph.getWorldSizeX()-2, graph.getWorldSizeY()-5);
        }
         
        List res = new ArrayList();
        res.add(n1);
        res.add(n2);
        res.add(n3);
        
        return res;
    }
    
    public static int determineGate(Graph graph,int startPos){
        
        boolean gate1free = true;
        List<Node> gate1 = gateOneLocation( graph, startPos );
        for( Node node : gate1 ){
            if(node.isDiscovered()){
                if( node.isRock() ){
                    gate1free = false;
                }
            }else{
                 gate1free = false;
            }
        }
        
        if(gate1free)
            return 1;
        
        boolean gate2free = true;
        List<Node> gate2 = gateOneLocation( graph, startPos );
        for( Node node : gate2 ){
            if(node.isDiscovered()){
                if( node.isRock() ){
                    gate2free = false;
                }
            }else{
                 gate2free = false;
            }
        }
        
        if(gate2free)
            return 1;
        
        return 0;
    }
    
}
