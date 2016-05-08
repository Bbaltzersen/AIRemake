/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author bbalt
 */
public class Queen {

    AntMethods antM = new AntMethods();
    AntFindPath fRoute = new AntFindPath(new EulerHeristic());

    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber) {
        if(possibleActions.contains(EAction.LayEgg)){
            return EAction.LayEgg;
        }
         if( roundNumber > 50 ){ // skal sættes ned når den er færdig..
            seekToCorner();
        }
        
        List<Node> nodes = g.getNodes();
        for(Node n : nodes) {
             n.resetNode();
        }
        
        
        // Position 1
        // <editor-fold defaultstate="collapsed">
        if (startPos == 1) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, thisLocation , visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 2
        // <editor-fold defaultstate="collapsed">
        if (startPos == 2) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, thisLocation , visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 3
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, thisLocation , visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 4
        // <editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            if (thisLocation.getFoodCount() != 0 && thisAnt.getFoodLoad() < 10) {
                return EAction.PickUpFood;
            }
            
            return fRoute.NextStep(thisAnt, thisLocation , visibleLocations, getNodeByXAndY(thisLocation.getX(), thisLocation.getY(), nodes), getNodeByXAndY(5, 5, nodes), g);
        } else {
            System.out.println("Pass in main");
            return EAction.Pass;
        }
        // </editor-fold>
    }

    ;
    
     public void setBlocked(List<ILocationInfo> visibleLocations, Graph g) {
        for (ILocationInfo loc : visibleLocations) {
            if (loc.isFilled()) {
                g.setBlocked(loc.getX(), loc.getY(), true);
            }
        }
    }

    public void setBlocked(List<ILocationInfo> visibleLocations) {
        for (ILocationInfo loc : visibleLocations) {

        }
    }
    
    public Node getNodeByXAndY(int x, int y, List<Node> lN) {
        for(Node n : lN) {
            if(x == n.getXPos() && y == n.getYPos()) return n;
        }
        return null;
    }
    private void seekToCorner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
