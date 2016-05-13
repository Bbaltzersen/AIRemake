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
}
