/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ants;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import airemake.AntControl;
import board.EulerHeristic;
import board.Graph;
import board.Node;
import java.util.List;

/**
 *
 * @author bbalt
 */
public class CarrierExplore {
    AntFindPath fRoute = new AntFindPath(new EulerHeristic());
    public EAction exploreRandom(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        System.out.println(new AntControl().getWorldSizeX());
        List<Node> nodes = g.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        } 
        if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(0, 1).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

        } else if (thisAnt.getFoodLoad() == 10){
            return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(1,1), g);
        }
        if(possibleActions.contains(EAction.DigOut)) {
            return EAction.DigOut;
        }
        return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(new AntControl().getWorldSizeX()/2, new AntControl().getWorldSizeY()/2), g);
    }
    
    public Node getXFromPos(int starX, int starY, int sPos, Graph g) {
        if(sPos == 1) {
            return g.getNode(starX+1, starY);
        }
        if(sPos == 2) {
            return g.getNode(starX-1, starY);
        }
        if(sPos == 3) {
            return g.getNode(starX, starY+1);
        }
        if(sPos == 4){
            return g.getNode(starX, starY-1);
        }else {
           return g.getNode(starX, starY);
        }
    }
    
    public int getYFromPos(int y) {
        if(y == 1) {
            return 0;
        }
        if(y == 2) {
            return new AntControl().getWorldSizeY() - 1;
        }
        if(y == 3) {
            return 0;
        }
        if(y == 4){
            return new AntControl().getWorldSizeY() - 1;
        }else {
        return 0;
        }
    }
}
