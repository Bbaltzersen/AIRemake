package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntMethods.gateOneLocation;
import static Ants.AntMethods.gateTwoLocation;
import static Ants.AntMethods.getGuardPositions;
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
public class WarriorLogic {
    
    public static EAction generalWarriorControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations
            , List<EAction> possibleActions, Graph graph,Queen queen , int roundNumber, int startPos){
        
        //always reset node.prev else the heap stack gets out of memory..
        List<Node> nodes = graph.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        }
        
        if(thisAnt.getHitPoints() < 20 && possibleActions.contains(EAction.EatFood)){
                return EAction.EatFood;
        }
        
        IAntInfo enemy = null;
        try{
             enemy = visibleLocations.get(0).getAnt();
        }catch(Exception e){
            
        }
        if( enemy != null){
            if( visibleLocations.get(0).getAnt().getTeamInfo() != thisAnt.getTeamInfo() && visibleLocations.get(0).getAnt().getTeamInfo() != null){
                return EAction.Attack;
            }
        }
        
        if( !getGuardPositions(startPos, graph).isEmpty() ){
            if(   graph.getNode( thisLocation.getX(),thisLocation.getY() ) == getGuardPositions( startPos, graph).get(0) ||
                   graph.getNode( thisLocation.getX(),thisLocation.getY() ) == getGuardPositions( startPos, graph).get(1) ){
                
                int gateNumber =graph.getGateNumber();
                List<Node> gateNodes = new ArrayList();
                if( gateNumber == 1 ){
                    gateNodes = gateOneLocation( graph, startPos);
                }
                if( gateNumber == 2 ){
                    gateNodes = gateTwoLocation( graph, startPos);
                }
                if( !gateNodes.isEmpty() ){
                    for(Node gate : gateNodes){
                        if(visibleLocations.get(0) == gate)
                            return EAction.Pass;
                    }
                }
            }
        }
        return guardGate( thisAnt,  thisLocation,  visibleLocations,  possibleActions,  graph, queen ,  roundNumber,  startPos);
    }
    
    private static EAction guardGate(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations
            , List<EAction> possibleActions, Graph graph,Queen queen , int roundNumber, int startPos){
        
        //if guard node is not blocked , move there and turn to gate
        //startPos 
        
         List<Node> guardPositions = getGuardPositions( startPos, graph);
         
         if( !guardPositions.isEmpty() ){
             return NextStep( thisAnt,  thisLocation,  visibleLocations,  graph.getNode(thisLocation.getX(), thisLocation.getY() ), guardPositions.get(0),  graph,  possibleActions);
         }
         return null;
        
    }
    
    private  static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }
   
}
