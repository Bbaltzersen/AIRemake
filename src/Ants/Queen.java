/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntFindPath.findDirection;
import static Ants.AntFindPath.getLength;
import static Ants.AntMethods.homeNodes;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.EulerHeristic;
import board.Graph;
import board.Node;
import java.util.List;

/**
 *
 * @author bbalt
 */
public class Queen {

//    AntMethods antM = new AntMethods();
//    AntFindPath fRoute = new AntFindPath(new EulerHeristic());
    int posX, posY;

    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        setPosX(thisLocation.getX());
        setPosY(thisLocation.getY());

        if (thisAnt.getHitPoints() < 15 && possibleActions.contains(EAction.EatFood)) {
            return EAction.EatFood;
        }
        List<Node> nodes = g.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        }

        if (thisAnt.getHealth() > 10 && possibleActions.contains(EAction.EatFood)) {
            return EAction.EatFood;
        }

        if (roundNumber < 50) {

            return startProduction(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber, starX, starY);
        } else {
            return stallGame(thisAnt, thisLocation, visibleLocations, possibleActions, g, startPos, roundNumber, starX, starY);
        }

    }

    public EAction startProduction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        // Position 1 // South West
        // <editor-fold defaultstate="collapsed">

        if (startPos == 1) {

            if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(0, 1).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

            } //Go to 0,1
            else if (g.getNode(0, 1).getFoodCount() != 0 || !g.getNode(0, 1).isDiscovered()) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(0, 1), g, possibleActions);
            } else if (g.getNode(1, 0).getFoodCount() != 0 && !g.getNode(1, 0).isDiscovered() && thisAnt.getFoodLoad() != 10) {
                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(1, 0), g, possibleActions);
            } else if (thisLocation.getX() != 0 || thisLocation.getY() != 0) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(0, 0), g, possibleActions);
            } else if (thisLocation.getX() == 0 && thisLocation.getY() == 0 && thisAnt.getFoodLoad() >= 5) {

                if (thisAnt.getDirection() != 1) {

                    return findDirection(1, 0, thisLocation, visibleLocations, thisAnt, possibleActions, false);
                } else {

                    return EAction.LayEgg;
                }
            } else {
                return EAction.Pass;
            }

        } // </editor-fold>
        // Position 2 // North East
        //<editor-fold defaultstate="collapsed">
        if (startPos == 4) {
            if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

            } else if (g.getNode(starX, starY - 1).getFoodCount() != 0 || !g.getNode(starX, starY - 1).isDiscovered()) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY - 1), g, possibleActions);
            } else if (g.getNode(starX - 1, starY).getFoodCount() != 0 && !g.getNode(1, 0).isDiscovered() && thisAnt.getFoodLoad() != 10) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY - 1), g, possibleActions);
            } else if (thisLocation.getX() != starX || thisLocation.getY() != starY) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY), g, possibleActions);
            } else if (thisLocation.getX() == starX && thisLocation.getY() == starY && thisAnt.getFoodLoad() >= 5) {

                if (thisAnt.getDirection() != 2) {

                    return findDirection(starX, starY - 1, thisLocation, visibleLocations, thisAnt, possibleActions, false);
                } else {

                    return EAction.LayEgg;
                }
            } else {
                return EAction.Pass;
            }

        } // </editor-fold>
        // Position 3 // South East
        //<editor-fold defaultstate="collapsed">
        if (startPos == 2) {
            System.out.println(thisLocation.getX() == starX && thisLocation.getY() == starY && thisAnt.getFoodLoad() >= 5);

            if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

            } else if (g.getNode(starX + 1, starY).getFoodCount() != 0 || !g.getNode(starX + 1, starY).isDiscovered()) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX + 1, starY), g, possibleActions);
            } else if (g.getNode(starX, starY - 1).getFoodCount() != 0 && !g.getNode(starX, starY - 1).isDiscovered() && thisAnt.getFoodLoad() != 10) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX - 1, starY), g, possibleActions);
            } else if (thisLocation.getX() != starX || thisLocation.getY() != starY) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY), g, possibleActions);
            } else if (thisLocation.getX() == starX && thisLocation.getY() == starY && thisAnt.getFoodLoad() >= 5) {

                if (thisAnt.getDirection() != 2) {

                    return findDirection(starX - 1, starY, thisLocation, visibleLocations, thisAnt, possibleActions, false);
                } else {

                    return EAction.LayEgg;
                }
            } else {
                return EAction.Pass;
            }
        } //</editor-fold>
        // Position 4 and else // South West
        //<editor-fold defaultstate="collapsed">
        if (startPos == 3) {

            System.out.println(thisLocation.getX() == starX && thisLocation.getY() == starY && thisAnt.getFoodLoad() >= 5);

            if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

            } else if (g.getNode(starX - 1, starY).getFoodCount() != 0 || !g.getNode(starX - 1, starY).isDiscovered()) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX - 1, starY), g, possibleActions);
            } else if (g.getNode(starX, starY + 1).getFoodCount() != 0 && !g.getNode(starX, starY + 1).isDiscovered() && thisAnt.getFoodLoad() != 10) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX + 1, starY), g, possibleActions);
            } else if (thisLocation.getX() != starX || thisLocation.getY() != starY) {

                return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY), g, possibleActions);
            } else if (thisLocation.getX() == starX && thisLocation.getY() == starY && thisAnt.getFoodLoad() >= 5) {

                if (thisAnt.getDirection() != 0) {

                    return findDirection(starX, starY + 1, thisLocation, visibleLocations, thisAnt, possibleActions, false);
                } else {

                    return EAction.LayEgg;
                }
            } else {
                return EAction.Pass;
            }
        } else {

            return EAction.Pass;
        }
        // </editor-fold>
    }

    public EAction stallGame(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
         List<Node> home = homeNodes(g, startPos, starX, starY, thisAnt);

        if (!home.isEmpty()) {
            List<Node> closestPath = null;
            int sortNumber = 100000;
            int v = thisAnt.getDirection();
            for (Node n : home) {
                List<Node> sortList = getLength(g.getNode(thisLocation.getX(), thisLocation.getY()), n, g);
                if (sortList != null && !sortList.isEmpty()) {

                    if (sortList.size() < sortNumber) {
                        closestPath = sortList;
                        sortNumber = sortList.size();
                    }
                }
            }
            int nX = 0;
            int nY = 0;
            if (closestPath != null) {
                if (closestPath.size() >= 2) { //hvorfor 2 ??
                    nX = (int) closestPath.get(1).getXPos();
                    nY = (int) closestPath.get(1).getYPos();
                }
            }

            return AntFindPath.findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);

        }
        if (home.isEmpty() && thisLocation.getX() != starX && thisLocation.getY() != starY) {
            return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY), g, possibleActions);
        } else {
            return EAction.Pass;
        }
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
