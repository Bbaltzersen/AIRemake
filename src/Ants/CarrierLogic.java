package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntFindPath.findShortestPath;
import static Ants.AntMethods.IsInOuterWallArea;
import static Ants.AntMethods.determineGate;
import static Ants.AntMethods.findUndiscoveredFood;
import static Ants.AntMethods.gateOneLocation;
import static Ants.AntMethods.gateTwoLocation;
import static Ants.AntMethods.isGateOneDiscovered;
import static Ants.AntMethods.isGateTwoDiscovered;
import static Ants.AntMethods.isInQueenArea;
import static Ants.AntMethods.isInWallArea;
import static Ants.AntMethods.walkAround;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.Graph;
import board.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maze <martinweberhansen at gmail.com>
 */
public class CarrierLogic {
    
    
    public static EAction generalCarrierControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations
            , List<EAction> possibleActions, Graph graph,Queen queen , int roundNumber, int startPos, int gateNumber) {
  
        if( gateNumber < 0 ){
            if( !isGateOneDiscovered( graph, startPos )){
                for(Node node : gateOneLocation( graph, startPos) ){
                    if( !node.isDiscovered() )
                        return NextStep(thisAnt,thisLocation, visibleLocations, graph.getNode(  thisLocation.getX(), thisLocation.getY() ) ,  node  , graph, possibleActions);
                }   
            }
            if( !isGateTwoDiscovered( graph, startPos )){
                for(Node node : gateTwoLocation( graph, startPos) ){
                    if( !node.isDiscovered() )
                        return NextStep(thisAnt,thisLocation, visibleLocations, graph.getNode(  thisLocation.getX(), thisLocation.getY() ) ,  node  , graph, possibleActions);
                }   
            }
        }else{
            int gateNum = determineGate( graph, startPos);
            setGateNumber(1);
        }
        
        if(thisAnt.getHitPoints() < 20 && possibleActions.contains(EAction.EatFood)){
                return EAction.EatFood;
        }
        
        if( !visibleLocations.isEmpty() ){
            if( !thisAnt.carriesSoil() &&possibleActions.contains(EAction.DigOut) && !isInWallArea( graph.getNode( visibleLocations.get(0).getX(), visibleLocations.get(0).getY() ) ,  startPos,  graph) ){
                return EAction.DigOut;
            }
            if( visibleLocations.size() > 1 ){
                if( !thisAnt.carriesSoil() && visibleLocations.get(1).isFilled() && possibleActions.contains(EAction.MoveForward) ){
                    return EAction.MoveForward;
                }
            }
            if(  !thisAnt.carriesSoil() && visibleLocations.get(0).isFilled() && thisAnt.getActionPoints() < 5 &&   !isInWallArea( graph.getNode( visibleLocations.get(0).getX(),visibleLocations.get(0).getY() ) , startPos,  graph ) ){
                return EAction.Pass;
            }
        }    
        List<Node> nodes = graph.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        }
        List<Node> foodNodes = new ArrayList();
        for(Node node : graph.getNodes()){
            if( node.getFoodCount() > 0 &&  !isInQueenArea( node,  startPos, graph ) && !node.isBlocked() && !node.isRock() && !node.isTempBlocked()){
                foodNodes.add(node);
            }
        }
        
        if(thisAnt.getFoodLoad()  >=  5 ) //thisAnt.getAntType().getMaxFoodLoad()    
        {
            return goToQueen(thisAnt,visibleLocations, thisLocation,  possibleActions,  graph,  roundNumber, queen, startPos);
        } 
        else if ( !foodNodes.isEmpty() ){
            return findFood(thisAnt, visibleLocations,thisLocation,  possibleActions,  graph,  roundNumber, queen , startPos);
        }
        else{
            return walkAround( possibleActions, thisLocation, queen,  thisAnt );
        }
    }

      private  static EAction goToQueen(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph  graph, int roundNumber, Queen queen, int startPos) {

        if( isInQueenArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph ) && thisAnt.carriesSoil() ||
                isInWallArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph ) && thisAnt.carriesSoil() ||
                IsInOuterWallArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph ) && thisAnt.carriesSoil() ){

            return buildWall( thisAnt, visibleLocations, thisLocation, possibleActions, graph, roundNumber, queen, startPos);
        }
        
        if( isInQueenArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph ) && possibleActions.contains(EAction.DropFood) ){
             return EAction.DropFood;
        }else{
            Node target = null;
            if(startPos == 1)
               target = graph.getNode(0, 0);
            if(startPos == 2)
                target = graph.getNode( 0, graph.getWorldSizeY()-1 );
            if(startPos == 3)
                target = graph.getNode( graph.getWorldSizeX()-1 , 0 );
            if(startPos == 4)
               target =  graph.getNode(graph.getWorldSizeX()-1,graph.getWorldSizeY()-1 );
                
            return  NextStep(thisAnt,thisLocation, visibleLocations, graph.getNode(  thisLocation.getX(), thisLocation.getY() ) ,  target  , graph, possibleActions);
        }
    }
    
//find the closest location where there is food and pick it up..
    private  static EAction findFood(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph graph, int roundNumber,Queen queen, int startPos) {
       
        Node targetNode = null;
        
        if( thisLocation.getFoodCount() > 0  &&   !isInQueenArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph ) && possibleActions.contains(EAction.PickUpFood)){
           return EAction.PickUpFood;
        }
        if( isInQueenArea( graph.getNode( thisLocation.getX(), thisLocation.getY() ), startPos, graph )  &&  thisAnt.getFoodLoad() > 0  &&  possibleActions.contains(EAction.DropFood) ){
            return EAction.DropFood;
        }
        if( !visibleLocations.isEmpty() ){
            if(visibleLocations.get(0) != null){
                if(visibleLocations.get(0).getFoodCount() > 0 && !visibleLocations.get(0).isRock() && !visibleLocations.get(0).isFilled() && 
                        !isInQueenArea(  graph.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY()  ),  startPos,  graph) ){
                    return  NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                            , graph.getNode( (int) visibleLocations.get(0).getX() , (int) visibleLocations.get(0).getY() ) ,graph, possibleActions);
                } 
            }
            if( indexExists( visibleLocations,1 ) ){
                 if(visibleLocations.get(1).getFoodCount() > 0 && !visibleLocations.get(1).isRock() && !visibleLocations.get(1).isFilled() &&
                         !isInQueenArea(  graph.getNode(visibleLocations.get(1).getX(), visibleLocations.get(1).getY()  ),  startPos,  graph)){
                return  NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                        , graph.getNode( (int) visibleLocations.get(1).getX() , (int) visibleLocations.get(1).getY() ) ,graph, possibleActions);
                }
            }
        }
        else {
            // her vil jeg gerne lave en algoritme som lopper igennem alle nodes som indeholder food, og vælge den der er tættest på denne carrier ant.
            List<Node> shortestPathToFood = null;
            List<Node>temp;
            
            for( Node node : graph.getNodes() ){
                if( node.getFoodCount() > 0 &&  !isInQueenArea( node , startPos , graph )){
                    temp = findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ), node, graph );
                    if( temp != null ){
                        if( shortestPathToFood == null || temp.size() < shortestPathToFood.size()){
                            shortestPathToFood = temp;
                        }
//                        else if(       ){ // her tester den kun for hvor mange nodes der er, ikke hvor mange "moves"
//                            shortestPathToFood = temp;
//                        }
                    }
                }
            }   
            try{
                targetNode = shortestPathToFood.get( shortestPathToFood.size() -1); 
            }catch(Exception nullPointer){
            }
        }
        
        if(targetNode != null){
            return   NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() ) , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph, possibleActions);
        }else{
            return findUndiscoveredFood(possibleActions,thisLocation,queen,  thisAnt,graph,visibleLocations);
        }
      }
    
    private static EAction buildWall(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph  graph, int roundNumber, Queen queen, int startPos){

        if( !visibleLocations.isEmpty() ){
            if(  isInWallArea( graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ),  startPos,  graph)    && 
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isWall()                     &&
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isBlocked()                &&
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isTempBlocked()        &&
                    possibleActions.contains(EAction.DropSoil)){
                
                graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).setWall(true);
                return EAction.DropSoil;
            }
        }
        
        if( !visibleLocations.isEmpty() ){
            if(  isInWallArea( graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ),  startPos,  graph)    && 
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isWall()                     &&
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isBlocked()                &&
                                        !graph.getNode( visibleLocations.get(0).getX() , visibleLocations.get(0).getY() ).isTempBlocked()        &&
                    thisAnt.getActionPoints() < 4){
                
                return EAction.Pass;
            }
        }
        
        Node targetNode = null;
        List<Node> tempRoute =  null;
        List<Node> shortestPathToWallToBuild = null;
        List<Node> buildWall = new ArrayList();
        
        for( Node node : graph.getNodes() ){
            if( isInWallArea( node,  startPos,  graph )){
                if( (!node.isWall() && thisLocation.getY() != node.getYPos() && thisLocation.getX() != node.getXPos())   ||
                     (!node.isWall() && thisLocation.getX() != node.getXPos() && thisLocation.getY() != node.getYPos()) ) {
                        buildWall.add(node);
                }
            }
        }
       
        if( !buildWall.isEmpty() ){
            for( Node node : buildWall ){
                  tempRoute = findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ) , node , graph ); 
                  
                  if( shortestPathToWallToBuild == null && tempRoute != null ){
                          shortestPathToWallToBuild = tempRoute;
                  }
                  else if( tempRoute != null && shortestPathToWallToBuild != null ){
                        if( tempRoute.size() < shortestPathToWallToBuild.size() )
                            shortestPathToWallToBuild = tempRoute;
                  }
            } 
        }     
        try{
            targetNode = shortestPathToWallToBuild.get(shortestPathToWallToBuild.size()-1 );
        }catch(NullPointerException e){
        }
        if(targetNode != null ){
                return NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() ) , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph, possibleActions);
         }
       return walkAround( possibleActions, thisLocation, queen,  thisAnt );
    }
  
    private  static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }
    
    private static double getMinimumDist(Node start, Node goal) {
        
            double heuristicX = start.getXPos() -   goal.getXPos();
            double heuristicY = start.getYPos()  -   goal.getYPos();

            //sæt heuristic value til positiv værdi 
                if(heuristicX < 0) { heuristicX = heuristicX * -1; }
                if(heuristicY < 0) { heuristicY = heuristicY * -1; }
        
        return heuristicX + heuristicY;
    }
    
    
    
    
}

