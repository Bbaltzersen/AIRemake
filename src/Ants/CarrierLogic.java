package Ants;

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
    AntMethods antM = new AntMethods();
    AntFindPath fRoute = new AntFindPath(new EulerHeristic());
    
    public CarrierLogic(){}
           
    public  EAction generalCarrierControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations
            , List<EAction> possibleActions, Graph graph,Queen queen , int roundNumber) {
     
        //hvis der er food på nodes i graph, -tilføj til foodNodes
        List<Node> foodNodes = new ArrayList();
        for(Node node : graph.getNodes()){
            if(node.getFoodCount() > 0){
                foodNodes.add(node);
                System.out.println("node containing food "+node.getXPos()+","+node.getYPos());
            }
        }
        
        if(thisAnt.getFoodLoad() >= thisAnt.getAntType().getMaxFoodLoad())
        {
            System.out.println("Foodload at max, returning to queen" +thisAnt.asString());
            return goToQueen(thisAnt,visibleLocations, thisLocation,  possibleActions,  graph,  roundNumber, queen );
        } 
        else if ( !foodNodes.isEmpty() ){
            System.out.println("Foodload not at max, trying to find new route to food.. : " +thisAnt.getAntType());
            return findFood(thisAnt, visibleLocations,thisLocation,  possibleActions,  graph,  roundNumber, queen );
        }
        else{
            return antM.walkAround(possibleActions,thisLocation,queen,  thisAnt);
        }
        //return EAction.Pass;
    }

    //use the A star to move to queens location..
    private  EAction goToQueen(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation, List<EAction> possibleActions, Graph  graph, int roundNumber, Queen queen) {
        int antLocX = thisLocation.getX();
        int antLocY = thisLocation.getY();
        int queenLocX = queen.getPosX();
        int queenLocY = queen.getPosY();
        
        if(antLocX == queenLocX || antLocX == queenLocX-1 || antLocX == queenLocX+1 && antLocY == queenLocY || antLocY == queenLocY-1 || antLocY == queenLocY+1){
            return EAction.DropFood;
        }else{
            return  fRoute.NextStep(thisAnt,thisLocation, visibleLocations,    graph.getNode(  thisLocation.getX(), thisLocation.getY() ) ,  graph.getNode(queen.getPosX(),queen.getPosY()), graph);
        }
    }
    
//find the closest location where there is food and pick it up..
    private  EAction findFood(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph graph, int roundNumber,Queen queen) {
       
        Node targetNode = null;
        
        if( thisLocation.getFoodCount() > 0 ){
            System.out.println("carrier eats food");
           return EAction.PickUpFood;
        }
        else {
            // her vil jeg gerne lave en algoritme som lopper igennem alle nodes som indeholder food, og vælge den der er tættest på denne carrier ant.
            List<Node> shortestPathToFood = null;//new ArrayList(); 
            List<Node>temp;// = new ArrayList();
            
            for( Node node : graph.getNodes() ){
                if( node.getFoodCount() > 0){
                    temp = fRoute.findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ), node, graph );
                    if(temp != null){
    //                    try{
    //                        System.out.println("temp Array size = "+temp.size());
    //                        System.out.println("shortest path size = "+shortestPathToFood.size());

                            if(shortestPathToFood == null){
                                shortestPathToFood = temp;
                            }
                            else if( temp.size() < shortestPathToFood.size() ){ //shortestPathToFood.isEmpty()
                                shortestPathToFood = temp;
                            }
    //                    }catch(Exception nullPointer){
    //                        System.out.println("EXCEPTION THROWN, no visible food on map: "+nullPointer);
    //                    }
                    }
                }
            }   
//            for( Node node : shortestPathToFood){
//                System.out.println(node.getXPos()+" , "+node.getYPos()+" THIS WAS SHORTEST PATH TO FOOD!!");
//            }
            try{
                targetNode = shortestPathToFood.get( shortestPathToFood.size() -1); 
            }catch(Exception nullPointer){
                System.out.println("nullPointer exception target node.."+nullPointer);
            }
            
        }
        
        if(targetNode != null){
            return  fRoute.NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                    , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph);
        }else{
            System.out.println("tried next step, but ended up walk around");
            return antM.walkAround(possibleActions,thisLocation,queen,  thisAnt);
        }
//      try{
//            return  fRoute.NextStep(thisAnt, thisLocation, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
//                    , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph);
//        }catch(Exception e){
//            System.out.println("tried next step, but ended up walk around");
//            return antM.walkAround(possibleActions,thisLocation,queen,  thisAnt);
//        }
      }
    
    
}


