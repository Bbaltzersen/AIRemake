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
import java.util.List;

/**
 *
 * @author bbalt
 */
public class Queen {

    AntMethods antM = new AntMethods();
    AntFindPath fRoute = new AntFindPath(new EulerHeristic());

    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos) {
        // Position 1
        // <editor-fold defaultstate="collapsed">
        if (startPos == 1) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 2
        // <editor-fold defaultstate="collapsed">
        if (startPos == 2) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 3
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5),g);
        }
        // </editor-fold>
        
        // Position 4
        // <editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            System.out.println("in here");
            System.out.println(fRoute.findShortestPath(g.getNode(0, 0), g.getNode(5, 5),g));
            for (ILocationInfo loc : visibleLocations) {
                if (loc.isFilled()) {
                    g.getNode(loc.getX(), loc.getY()).setBlocked(true);
                    System.out.println(g.getNode(loc.getX(), loc.getY()));
                }
                if (loc.isRock()) {
                    g.getNode(loc.getX(), loc.getY()).setBlocked(true);
                    System.out.println(g.getNode(loc.getX(), loc.getY()));
                }
            }
            System.out.println("Foodload: " + thisAnt.getFoodLoad());
            if (thisLocation.getFoodCount() != 0 && thisAnt.getFoodLoad() < 10) {
                return EAction.PickUpFood;
            }
            
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(2, 1), g);
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
}
