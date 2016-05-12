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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bbalt
 */
public class AntMethods {
    
    public AntMethods()
    {
    }

    public int totalFoodInFront(List<ILocationInfo> visibleLocations) {
        int foodCount = 0;
        foodCount = visibleLocations.stream().map((loc) -> loc.getFoodCount()).reduce(foodCount, Integer::sum); // hvad betyder dette?
        return foodCount;
    }

     public EAction walkAround(List<EAction> possibleActions, ILocationInfo thisLocation,Queen queen,IAntInfo thisAnt){
        if( thisAnt.getAntType() == EAntType.CARRIER ){
            List<EAction> actions = new ArrayList();
            
            if(possibleActions.contains(EAction.MoveForward))
                actions.add(EAction.MoveForward);
            if(possibleActions.contains(EAction.TurnLeft))
                actions.add(EAction.TurnLeft);
            if(possibleActions.contains(EAction.TurnRight))
                actions.add(EAction.TurnRight);
            if(possibleActions.contains(EAction.MoveForward))
                actions.add(EAction.MoveForward);
            
            Random rand = new Random();
//            System.out.println("Array action.size() = " +actions.size());
//            int x = rand.nextInt( actions.size());
//            System.out.println("randInt num = "+x);
           
            
            try{
                return  actions.get( rand.nextInt( actions.size() ) );
            }catch(Exception e){
                System.out.println("ERROR: "+ e.toString());
            }
           
            return EAction.Pass;
                
        }
        else return EAction.Pass;
    }

}
