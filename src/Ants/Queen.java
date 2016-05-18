package Ants;

import static Ants.AntFindPath.NextStep;
import static Ants.AntFindPath.findDirection;
import static Ants.AntFindPath.getLength;
import static Ants.AntMethods.homeNodes;
import static Ants.AntMethods.isInQueenArea;
import static Ants.AntMethods.isInWallArea;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.Graph;
import board.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bbalt
 */
public class Queen {

    int posX, posY;

    public EAction generalQueenControl(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph graph, int startPos, int roundNumber, int starX, int starY) {
        setPosX(thisLocation.getX());
        setPosY(thisLocation.getY());

        if (thisAnt.getHitPoints() < 15 && possibleActions.contains(EAction.EatFood)) {
            return EAction.EatFood;
        }
        List<Node> nodes = graph.getNodes();
        for (Node n : nodes) {
            n.resetNode();
        }

        if (thisAnt.getFoodLoad() >= 6) {
            return missionLayEggs(thisAnt, thisLocation, visibleLocations, possibleActions, graph, startPos, roundNumber, starX, starY);
        } else if (roundNumber < 30) {

            return startProduction(thisAnt, thisLocation, visibleLocations, possibleActions, graph, startPos, roundNumber, starX, starY);
        } else {
            return stallGame(thisAnt, thisLocation, visibleLocations, possibleActions, graph, startPos, roundNumber, starX, starY);
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

        if (!home.isEmpty() && thisAnt.getFoodLoad() < 10) {
            if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0) {
                g.getNode(0, 1).setFoodCount(thisLocation.getFoodCount() - 1);
                return EAction.PickUpFood;

            }
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

//            System.out.println("The way to go: "+home);
            return AntFindPath.findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);

        }
        System.out.println(home.isEmpty() && thisLocation.getX() != starX && thisLocation.getY() != starY || thisAnt.getFoodLoad() <= 10 && thisLocation.getX() != starX && thisLocation.getY() != starY);
        if (home.isEmpty() && thisLocation.getX() != starX && thisLocation.getY() != starY || thisLocation.getX() != starX && thisLocation.getY() != starY) {
            return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(starX, starY), g, possibleActions);
        } else {
            return EAction.Pass;
        }
    }

    public EAction missionLayEggs(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
        List<Node> listOfBlockedNodes = new ArrayList();
        System.out.println(faceNode(startPos, g, starX, starY, visibleLocations));
        if(faceNode(startPos, g, starX, starY, visibleLocations)) {
            return EAction.LayEgg;
        }
        
        if (startPos == 1) {
            for (int x = starX; x <= starX + 2; x++) {
                for (int y = starY + 1; y <= starY + 1; y++) {
                    Node nodexy = g.getNode(x, y);
                    listOfBlockedNodes.add(nodexy);
                }
            }
        }
        if (startPos == 2) {
            for (int x = starX; x <= starX + 2; x++) {
                for (int y = starY - 1; y <= starY - 1; y++) {
                    Node nodexy = g.getNode(x, y);
                    listOfBlockedNodes.add(nodexy);
                }
            }
        }
        if (startPos == 3) {
            for (int x = starX - 2; x <= starX; x++) {
                for (int y = starY + 1; y <= starY + 1; y++) {
                    Node nodexy = g.getNode(x, y);
                    listOfBlockedNodes.add(nodexy);
                }
            }
        }
        if (startPos == 4) {
            for (int x = starX - 2; x <= starX; x++) {
                for (int y = starY - 1; y <= starY - 1; y++) {
                    Node nodexy = g.getNode(x, y);
                    listOfBlockedNodes.add(nodexy);
                }
            }
        }
        for (Node n : listOfBlockedNodes) {
            
            if (!n.isBlocked() && !n.isTempBlocked()) {
                try {
                    System.out.println("I AM HERE AND WANTS TO FIND WAY TO LAY EGG");
                    return NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), n, g, possibleActions);
                } catch (NullPointerException e) {
                    System.out.println("Nullpoint exception caught: " + e);
                }
            }
        }
        System.out.println("I AM HERE AND WANTS TO FIND WAY TO LAY EGG BUT HAVE TO");
        return EAction.Pass;
    }

    public boolean faceNode(int startPos, Graph g, int starX, int starY, List<ILocationInfo> visibleLocations) {
        if(!visibleLocations.isEmpty()) {
        if (startPos == 1) {
            for (int x = starX; x <= starX + 2; x++) {
                for (int y = starY + 1; y <= starY + 1; y++) {
                    if (g.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY()) == g.getNode(x, y) && !g.getNode(x, y).isBlocked() && !g.getNode(x, y).isRock() && !g.getNode(x, y).isTempBlocked() ) {
                        return true;
                    }

                }
            }
        }
        if (startPos == 2) {
            for (int x = starX; x <= starX + 2; x++) {
                for (int y = starY - 1; y <= starY - 1; y++) {
                    if (g.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY()) == g.getNode(x, y) && !g.getNode(x, y).isBlocked() && !g.getNode(x, y).isRock() && !g.getNode(x, y).isTempBlocked() ) {
                        return true;
                    }
                }
            }
        }
        if (startPos == 3) {
            for (int x = starX - 2; x <= starX; x++) {
                for (int y = starY + 1; y <= starY + 1; y++) {
                    if (g.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY()) == g.getNode(x, y) && !g.getNode(x, y).isBlocked() && !g.getNode(x, y).isRock() && !g.getNode(x, y).isTempBlocked() ) {
                        return true;
                    }
                }
            }
        }
        if (startPos == 4) {
            for (int x = starX - 2; x <= starX; x++) {
                for (int y = starY - 1; y <= starY - 1; y++) {
                    if (g.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY()) == g.getNode(x, y) && !g.getNode(x, y).isBlocked() && !g.getNode(x, y).isRock() && !g.getNode(x, y).isTempBlocked() ) {
                        return true;
                    }
                }
            }
        }
        }
        return false;
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
