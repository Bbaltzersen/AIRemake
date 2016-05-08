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
    int posX, posY;

    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        setPosX(thisLocation.getX());
        setPosY(thisLocation.getY());

        if (possibleActions.contains(EAction.LayEgg)) {
            return EAction.LayEgg;
        }

        List<Node> nodes = g.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        }

        if (roundNumber < 20) {
            System.out.println("in first loop");
            return startProduction(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber, starX, starY);
        } else {
            return EAction.Pass;
        }

    }

    public EAction startProduction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        // Position 1 // South West
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            System.out.println("in second");
            System.out.println(thisLocation.getX() + thisLocation.getY());
            if (thisAnt.getFoodLoad() <= 10 && thisLocation.getFoodCount() != 0) {
                return EAction.PickUpFood;
            } else if (g.getNode(0,3).isDiscovered() != true || g.getNode(0,3).getFoodCount() != 0 || thisAnt.getFoodLoad() <= 10 ) {
                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(),thisLocation.getY()), g.getNode(0,3), g);
            } else if (g.getNode(1,0).isDiscovered() != true || g.getNode(1,0).getFoodCount() != 0 || thisAnt.getFoodLoad() <= 10) {
                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(),thisLocation.getY()), g.getNode(1,0), g);
            } else if (thisLocation.getX() != 0 && thisLocation.getY() != 0) {
                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(),thisLocation.getY()), g.getNode(0,0), g);
            } else if(thisLocation.getX() == 0 && thisLocation.getY() == 0 && thisAnt.getFoodLoad() >= 5) {
                if(thisAnt.getDirection() != 1) {
                return fRoute.findDirection(1, 0, thisLocation, visibleLocations, thisAnt, false);
                } else {
                    return EAction.LayEgg;
                }
            } 
            else {
                System.out.println("pass in main");
                return EAction.Pass;
            }

        } // </editor-fold>
        // Position 2 // North East
        // <editor-fold defaultstate="collapsed">
        //        if (startPos == 2) {
        //            if(thisAnt.getFoodLoad() <= 10 && thisLocation.getFoodCount() != 0) {
        //                return EAction.PickUpFood;
        //            } else if(thisAnt.getFoodLoad() == 5) {
        //                if(thisLocation.getX() != starX && thisLocation.getY() != starY) {
        //                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(posX, posY), g);
        //                }
        //            }
        //        }
        // </editor-fold>
        // Position 3 // South East
        // <editor-fold defaultstate="collapsed">
        //        if (startPos == 3) {
        //            if(thisAnt.getFoodLoad() <= 10 && thisLocation.getFoodCount() != 0) {
        //                return EAction.PickUpFood;
        //            } else if(thisAnt.getFoodLoad() == 5) {
        //                if(thisLocation.getX() != posX && thisLocation.getY() != posY) {
        //                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(posX, posY), g);
        //                } {
        //                    return EAction.Pass;
        //                }
        //            }
        //        }
        // </editor-fold>
        // Position 4 and else // South West
        // <editor-fold defaultstate="collapsed">
        //        if (startPos == 4) {
        //            if(thisAnt.getFoodLoad() <= 10 && thisLocation.getFoodCount() != 0) {
        //                return EAction.PickUpFood;
        //            } else if(thisAnt.getFoodLoad() == 5) {
        //                if(thisLocation.getX() != posX && thisLocation.getY() != posY) {
        //                return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(posX, posY), g);
        //                } else {
        //                    return EAction.Pass;
        //                }
        //            }
        //        } 
        else {
            return EAction.Pass;
        }
        // </editor-fold>
    }

    public EAction stallGame(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber) {
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

        // Position 3 // South East
        // <editor-fold defaultstate="collapsed">
        if (startPos == 3) {
            return EAction.Pass;
        }
        // </editor-fold>

        // Position 4 and else // South West
        // <editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            return EAction.Pass;
        } else {
            return EAction.Pass;
        }
        // </editor-fold>
    }

    public void setPosX(int x) {
        this.posX = x;
    }

    public void setPosY(int y) {
        this.posY = y;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
}
