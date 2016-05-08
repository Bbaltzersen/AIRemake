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
public class Queen {

    AntMethods antM = new AntMethods();
    AntFindPath fRoute = new AntFindPath(new EulerHeristic());
    int posX,posY ;
    
    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber) {
        setPosX( thisLocation.getX() );
        setPosY( thisLocation.getY() );
        
        if(possibleActions.contains(EAction.LayEgg)){
            return EAction.LayEgg;
        }
        
        
        List<Node> nodes = g.getNodes();
        for(Node n : nodes) {
             n.resetNode();
        }
        
        if (roundNumber > 20) {
            return startProduction(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber);
        } else {
            return EAction.Pass;
        }
        
    }
    
    public EAction startProduction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber) {
        // Position 1 // South West
        // <editor-fold defaultstate="collapsed">
        if (startPos == 1) {
            return EAction.Pass;
        }
        // </editor-fold>
        
        // Position 2 // North East
        // <editor-fold defaultstate="collapsed">
        if (startPos == 2) {
            return EAction.Pass;
        }
        // </editor-fold>
        
        // Position 3 // South West
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            return EAction.Pass;
        }
        // </editor-fold>
        
        // Position 4 and else
        // <editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            return EAction.Pass;
        } else {
            return EAction.Pass;
        }
        // </editor-fold>
    }
    
    public Node getNodeByXAndY(int x, int y, List<Node> lN) {
        for(Node n : lN) {
            if(x == n.getXPos() && y == n.getYPos()) return n;
        }
        return null;
    }
    
    public void setPosX(int x){
        this.posX = x;
    }
    public void setPosY(int y){
        this.posY = y;
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
}
