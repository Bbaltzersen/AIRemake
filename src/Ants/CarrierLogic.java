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
            , List<EAction> possibleActions, Graph graph, int roundNumber,Queen queen) {
        
        if(thisAnt.getFoodLoad() >= thisAnt.getAntType().getMaxFoodLoad())
        {
            System.out.println("Foodload at max, returning to queen" +thisAnt.asString());
            return goToQueen( thisLocation,  possibleActions,  graph,  roundNumber, queen );
        } 
        else {
            System.out.println("Foodload not at max, trying to find new route to food.. : " +thisAnt.asString());
            findFood(thisAnt, visibleLocations,thisLocation,  possibleActions,  graph,  roundNumber, queen );
        }
        
        
        return EAction.Pass;
    }

    //use the A star to move to queens location..
    private  EAction goToQueen( ILocationInfo thisLocation, List<EAction> possibleActions, Graph  graph, int roundNumber, Queen queen) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    //find the closest location where there is food and pick it up..
    private  EAction findFood(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, ILocationInfo thisLocation
            , List<EAction> possibleActions, Graph graph, int roundNumber,Queen queen) {
       
        Node targetNode;
        System.out.println("thisLocation.getFoodCount() "+thisLocation.getFoodCount() );
        if(thisLocation.getFoodCount() > 0)
           return EAction.PickUpFood;
        else {
            // her vil jeg gerne lave en algoritme som lopper igennem alle nodes som indeholder food, og vælge den der er tættest på denne carrier ant.
            List<Node> shortestPathToFood = new ArrayList(); 
            List<Node>temp = new ArrayList();
            
            for( Node node : graph.getNodes() ){
                if( node.getFoodCount() > 0){
                    temp = fRoute.findShortestPath( graph.getNode( thisLocation.getX(), thisLocation.getY() ), node, graph );
                }
                if( temp.size() < shortestPathToFood.size() || shortestPathToFood.isEmpty() ){
                    shortestPathToFood = temp;
                }
            }
            for( Node node : shortestPathToFood){
                System.out.println(node.getXPos()+" , "+node.getYPos()+" THIS WAS SHORTEST PATH TO FOOD!!");
            }
            targetNode = shortestPathToFood.get( shortestPathToFood.size()-1 );
        }
        
      
        return fRoute.NextStep(thisAnt, visibleLocations, graph.getNode( thisLocation.getX(), thisLocation.getY() )
                , graph.getNode( (int) targetNode.getXPos() , (int) targetNode.getYPos() ) ,graph);
    }
    

}


