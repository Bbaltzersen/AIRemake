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
public class CarrierBuildAndGuard {
    AntFindPath antP = new AntFindPath();
    AntMethods antM = new AntMethods();
    public EAction generalCarrierBuild(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        buildArea(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber, starX, starY);
        return EAction.Pass;
    }
    
    public EAction buildArea(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        List<Node> blocksAtHome = antM.homeNodes(g, startPos, starX, starY, thisAnt);
        System.out.println(blocksAtHome.isEmpty() + ", " + thisAnt.carriesSoil());
        if(!blocksAtHome.isEmpty() && !thisAnt.carriesSoil()){
            
            return moveToAndPickUp(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber, starX, starY, blocksAtHome);
        }
        return EAction.Pass;
    }
    
    public EAction moveToAndPickUp(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY, List<Node> blocksAtHome) {
        List<Node> closestPath = null;
        int sortNumber = 100000;
        int v = thisAnt.getDirection();
        for(Node n : blocksAtHome) {
           // antP.NextStep(thisAnt, thisLocation, visibleLocations, n, n, g, possibleActions);
            List<Node> sortList = antP.getLength(g.getNode(thisLocation.getX(), thisLocation.getY()), n, g);
            if(sortList!=null && !sortList.isEmpty()) {
            if(sortList.size() < sortNumber){
                closestPath = sortList;
                sortNumber = sortList.size();
            }
            }
        }
        System.out.println("PATH TO TAKE: " + closestPath);
        int nX = 0;
        int nY = 0;
        if(closestPath != null) {
            if (closestPath.size() >= 2) { //hvorfor 2 ??
                nX = (int) closestPath.get(1).getXPos();
                nY = (int) closestPath.get(1).getYPos();
            }
        }
        
        
        return AntFindPath.findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);
    }
    
}
