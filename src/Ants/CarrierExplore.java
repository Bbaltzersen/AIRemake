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
        if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0 && g.getNode(thisLocation.getX(), thisLocation.getY()) != getToStartGoal(starX, starY, startPos, g) ) {
                g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

        } else if (thisAnt.getFoodLoad() == 10){
            return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), getToStartGoal(starX, starY, startPos, g), g);
        } if(g.getNode(thisLocation.getX(), thisLocation.getY()) == getToStartGoal(starX, starY, startPos, g)) {
            g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() + 1);
            return EAction.DropFood;
        }
        if(possibleActions.contains(EAction.DigOut)) {
            return EAction.DigOut;
        } else {
            return EAction.Pass;
        }
        
    }
    
    public Node getToStartGoal(int starX, int starY, int sPos, Graph g) {
        System.out.println(starY + ", "+ starX);
        if(sPos == 1) {
            System.out.println("node pos1´: " +g.getNode(starX+1, starY));
            return g.getNode(starX+1, starY);
        }
        if(sPos == 2) {
            System.out.println("node pos2 :"+ g.getNode(starX, starY-1));
            System.out.println(g.getNode(0, 8));
            System.out.println(g.getNode(8, 0));
            return g.getNode(starX, starY-1);
        }
        if(sPos == 3) {
            System.out.println("node pos3 "+g.getNode(starX, starY+1));
            return g.getNode(starX, starY+1);
        }
        if(sPos == 4){
            System.out.println("node pos3: "+g.getNode(starX, starY-1));
            return g.getNode(starX, starY-1);
        }else {
           return g.getNode(starX, starY);
        }
    }
}
