/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ants;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.Graph;
import board.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bbalt
 */
public class AntMethods {
    
//    public AntMethods()
//    {
//    }

    public int totalFoodInFront(List<ILocationInfo> visibleLocations) {
        int foodCount = 0;
        foodCount = visibleLocations.stream().map((loc) -> loc.getFoodCount()).reduce(foodCount, Integer::sum); // hvad betyder dette?
        return foodCount;
    }

     public static EAction walkAround(List<EAction> possibleActions, ILocationInfo thisLocation,Queen queen,IAntInfo thisAnt){
        if( thisAnt.getAntType() == EAntType.CARRIER ){
            List<EAction> actions = new ArrayList();
            
            if(possibleActions.contains(EAction.MoveForward))
                actions.add(EAction.MoveForward);
            
            if(possibleActions.contains(EAction.TurnLeft))
                actions.add(EAction.TurnLeft);
            
            if(possibleActions.contains(EAction.TurnRight))
                actions.add(EAction.TurnRight);
            
                if( !actions.isEmpty() ){
                Random rand = new Random();
                System.out.println("Array action.size() = " +actions.size());
                int x = rand.nextInt( actions.size() );
                System.out.println("randInt num = "+x);


                try{
                    System.out.println("CARRIER RETURNED WALKAROUND ");

                    EAction a = actions.get( x  );
                    System.out.println("returned action : "+a.name() );
                    return  a;
                }catch(Exception e){
                    System.out.println("ERROR: "+ e.toString());
                }

                return EAction.Pass;

            }
        }
        return EAction.Pass;
     }
     
     public List<Node> homeNodes(Graph g, int startPos, int starX, int starY, IAntInfo thisAnt) {
        List<Node> listOfBlockedNodes = new ArrayList();
        if(startPos==1){
            for(int x = starX; x <= starX+2; x++) {
                for(int y = starY; y <= starY+2; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==2) {
            for(int x = starX; x <= starX+2; x++) {
                for(int y = starY-2; y <= starY; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==3) {
            for(int x = starX-2; x <= starX; x++) {
                for(int y = starY; y <= starY+2; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        if(startPos==4) {
            for(int x = starX-2; x <= starX; x++) {
                for(int y = starY-2; y <= starY; y++) {
                    Node nodexy = g.getNode(x, y);
                    if(nodexy.isBlocked() && thisAnt.getAntType() == EAntType.CARRIER) {
                        listOfBlockedNodes.add(nodexy);
                    }
                    if(nodexy.getFoodCount() != 0 && thisAnt.getAntType() == EAntType.QUEEN) {
                        listOfBlockedNodes.add(nodexy);
                    }
                }
            }
        }
        return listOfBlockedNodes;
    }
}
