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
        
        if(startPos == 1){
             if(  !isInQueenArea( node,startPos,graph ) && node.getXPos() < 4 && node.getYPos() < 4){ 
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
    
    
}
