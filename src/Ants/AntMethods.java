/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ants;

import aiantwars.ILocationInfo;
import java.util.List;

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
        foodCount = visibleLocations.stream().map((loc) -> loc.getFoodCount()).reduce(foodCount, Integer::sum);
        return foodCount;
    }

}
