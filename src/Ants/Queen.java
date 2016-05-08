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
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5));
        }
        // </editor-fold>
        
        // Position 2
        // <editor-fold defaultstate="collapsed">
        if (startPos == 2) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5));
        }
        // </editor-fold>
        
        // Position 3
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            if (thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5));
        }
        // </editor-fold>
        
        // Position 4
        // <editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            if (thisLocation.getFoodCount() != 0 || thisAnt.getFoodLoad() == 10) {
                return EAction.PickUpFood;
            }
            for (ILocationInfo loc : visibleLocations) {
                if (loc.isFilled()) {
                    System.out.println("Soil inc !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    g.getNode(loc.getX(), loc.getY()).setBlocked(true);
                    System.out.println(g.getNode(loc.getX(), loc.getY()));
                }
                if (loc.isRock()) {
                    System.out.println("ROOOOOOOOOOOOOOOOOOOOOOOCKED");
                    g.getNode(loc.getX(), loc.getY()).setBlocked(true);
                    System.out.println(g.getNode(loc.getX(), loc.getY()));
                }
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5, 5));
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
