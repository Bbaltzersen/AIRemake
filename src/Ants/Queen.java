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
    Graph gra;
    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos) {
        System.out.println("thisX: "+ thisLocation.getX() + ", ThisY: " + thisLocation.getY());
        System.out.println("Direction: "+visibleLocations.size());
        System.out.println("Food: " + antM.totalFoodInFront(visibleLocations));
        System.out.println("Find Path: " + new AntFindPath(new EulerHeristic()).findShortestPath(g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(5,5)));
        
        if(startPos == 1) {
            if(thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(thisLocation.getX(),thisLocation.getY()+1));
        }
        if(startPos == 2) {
            if(thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(thisLocation.getX(),thisLocation.getY()+1));
        }
        if(startPos == 3) {
            if(thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(thisLocation.getX(),thisLocation.getY()+1));
        }
        if(startPos == 4) {
            if(thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            }
            return fRoute.NextStep(thisAnt, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(thisLocation.getX(),thisLocation.getY()+1));
        }
        else {
            return EAction.Pass;
        }
    };
    
     public int totalFoodInFront(List<ILocationInfo> visibleLocations) {
        int foodCount = 0;
        for(ILocationInfo loc : visibleLocations){
            foodCount += loc.getFoodCount();
        }
        return foodCount;
    }
}
