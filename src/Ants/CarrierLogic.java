package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntFindPath.findShortestPath;
import static Ants.AntMethods.walkAround;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.EulerHeristic;
import board.Graph;
import board.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maze <martinweberhansen at gmail.com>
 */
public class CarrierLogic {
    
    public static EAction generalCarrierControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations
            , List<EAction> possibleActions, Graph graph,Queen queen , int roundNumber) {
     
        //hvis der er food på nodes i graph, -tilføj til foodNodes
        List<Node> foodNodes = new ArrayList();
        for(Node node : graph.getNodes()){
            if(node.getFoodCount() > 0){
                foodNodes.add(node);
                System.out.println("node containing food "+node.getXPos()+","+node.getYPos());
            }
        }
        
        if(thisAnt.getFoodLoad() >=               15)      //thisAnt.getAntType().getMaxFoodLoad())
        {
            System.out.println("Foodload at max, returning to queen" +thisAnt.asString());
            return goToQueen(thisAnt,visibleLocations, thisLocation,  possibleActions,  graph,  roundNumber, queen );
        } 
        else if ( !foodNodes.isEmpty() ){
            System.out.println("Foodload not at max, trying to find new route to food.. : " +thisAnt.getAntType());
            return findFood(thisAnt, visibleLocations,thisLocation,  possibleActions,  graph,  roundNumber, queen );
        }
        else{
           
                    return walkAround(possibleActions,thisLocation,queen,  thisAnt);
        }
        //return EAction.Pass;
    }

    //use the A star to move to queens location..
    private  static EAction goToQueen(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph  graph, int roundNumber, Queen queen) {
       
        // implement here: if thisAnt is near the queen drop food
        
        int antLocX = thisLocation.getX();
        int antLocY = thisLocation.getY();
        int queenLocX = queen.getPosX();
        int queenLocY = queen.getPosY();
        
        if(antLocX == queenLocX || antLocX == queenLocX-1 || antLocX == queenLocX+1 && antLocY == queenLocY || antLocY == queenLocY-1 || antLocY == queenLocY+1){
            return EAction.DropFood;
        }else{
            return  NextStep(thisAnt,thisLocation, visibleLocations,    graph.getNode(  thisLocation.getX(), thisLocation.getY() ) ,  graph.getNode(queen.getPosX(),queen.getPosY()), graph);
        }
    }
    
//find the closest location where there is food and pick it up..
    private  static EAction findFood(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph graph, int roundNumber,Queen queen) {
       
        Node targetNode = null;
        
        if( thisLocation.getFoodCount() > 0 ){
            System.out.println("carrier eats food");
           return EAction.PickUpFood;
        }
        if( !visibleLocations.isEmpty() ){
            if(visibleLocations.get(0) != null){
                if(visibleLocations.get(0).getFoodCount() > 0 && !visibleLocations.get(0).isRock() && !visibleLocations.get(0).isFilled() ){
                    System.out.println("FOOD LIGGER 1 FORAN...");
                    return  NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                            , graph.getNode( (int) visibleLocations.get(0).getX() , (int) visibleLocations.get(0).getY() ) ,graph);
                } 
            }
            if( indexExists( visibleLocations,1 ) ){
                 if(visibleLocations.get(1).getFoodCount() > 0 && !visibleLocations.get(1).isRock() && !visibleLocations.get(1).isFilled() ){
                     System.out.println("FOOD LIGGER 2 FORAN..");
                return  NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                        , graph.getNode( (int) visibleLocations.get(1).getX() , (int) visibleLocations.get(1).getY() ) ,graph);
                }
            }
        }
        else {
            // her vil jeg gerne lave en algoritme som lopper igennem alle nodes som indeholder food, og vælge den der er tættest på denne carrier ant.
            List<Node> shortestPathToFood = null;//new ArrayList(); 
            List<Node>temp;// = new ArrayList();
            
            for( Node node : graph.getNodes() ){
                if( node.getFoodCount() > 0 ){
                    temp = findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ), node, graph );
                    if( temp != null ){
                        if( shortestPathToFood == null ){
                            shortestPathToFood = temp;
                        }
                        else if( temp.size() < shortestPathToFood.size() ){ //shortestPathToFood.isEmpty()
                            shortestPathToFood = temp;
                        }
                    }
                }
            }   
            try{
                targetNode = shortestPathToFood.get( shortestPathToFood.size() -1); 
            }catch(Exception nullPointer){
                System.out.println("nullPointer exception target node.."+nullPointer);
            }
            
        }
        
        if(targetNode != null){
            System.out.println("CARRIER FIND FOOD TARGET NOT NULL");
            return  NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                    , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph);
        }else{
            System.out.println("CARRIER FIND FOOD TARGET WAS NULL");
            return walkAround(possibleActions,thisLocation,queen,  thisAnt);
        }
      }
    
    
    private  static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }
    
}


