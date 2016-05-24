package Ants;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import board.Edge;
import board.Graph;
import board.IHeuristic;
import board.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author bbalt
 */
public class AntFindPath {

    private static double getMinimumDist(Node start, Node goal) {

        double heuristicX = start.getXPos() - goal.getXPos();
        double heuristicY = start.getYPos() - goal.getYPos();

        //sæt heuristic value til positiv værdi 
        if (heuristicX < 0) {
            heuristicX = heuristicX * -1;
        }
        if (heuristicY < 0) {
            heuristicY = heuristicY * -1;
        }

        return heuristicX + heuristicY;
    }

    public static List<Node> findShortestPath(Node start, Node goal, Graph graph) {
        Queue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        start.setGVal(0);

        Node curNode = start;
        while (true) {
            for (Edge edge : curNode) {
                Node other = edge.getEnd();
                if (!closedSet.contains(other) && !other.isBlocked() && !other.isRock() && !other.isTempBlocked()) {
                    double newG = edge.getWeight() + curNode.getGVal();
                    if (other.getGVal() > newG) {
                        other.setGVal(newG);
                        other.setPrev(curNode);
                    }
                    if (!openSet.contains(other)) {
                        other.setHVal(getMinimumDist(other, goal));
                        openSet.add(other);
                    }
                }
            }
            if (openSet.isEmpty()) {
                return null;
            }
            closedSet.add(curNode);
            curNode = openSet.poll();
            {
                if (curNode == goal) {
                    ArrayList<Node> res = new ArrayList<>();
                    do {
                        res.add(curNode);
                        curNode = curNode.getPrev();
                    } while (curNode != null);
                    Collections.reverse(res);
                    return res;
                }
            }
        }
    }

    public static EAction NextStep(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, Node start, Node goal, Graph graph, List<EAction> possibleActions) {

        List<Node> nodes;
        nodes = findShortestPath(start, goal, graph);
        int nX = 0;
        int nY = 0;
        if (nodes != null) {
            if (nodes.size() >= 2) { //hvorfor 2 ??
                nX = (int) nodes.get(1).getXPos();
                nY = (int) nodes.get(1).getYPos();
            }
            return findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);
        } else {
            return EAction.Pass;
        }
    }

    public static EAction findDirection(int nX, int nY, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, IAntInfo thisAnt, List<EAction> possibleActions, boolean move) {

        int v = thisAnt.getDirection();
        int vX = -1;
        int vY = -1;
        if (!visibleLocations.isEmpty()) {
            vX = visibleLocations.get(0).getX();
            vY = visibleLocations.get(0).getY();
        } else {
            switch (v) {
                case 0:
                    vX = thisLocation.getX();
                    vY = thisLocation.getY() + 1;
                    break;
                case 1:
                    vX = thisLocation.getX() + 1;
                    vY = thisLocation.getY();
                    break;
                case 2:
                    vX = thisLocation.getX();
                    vY = -1;
                    break;
                case 3:
                    vX = -1;
                    vY = thisLocation.getY();
                    break;

            }
        }
        System.out.println("this direction:" + v + " vX:" + vX + " vY:" + vY + " nX:" + nX + " nY:" + nY);
        System.out.println();

        if (move == true && vX == nX && vY == nY && !visibleLocations.get(0).isFilled() && visibleLocations.get(0).getAnt() == null) {
           
            return EAction.MoveForward;
        } else if (vX == nX || vY == nY) {
            return EAction.TurnLeft;
        } else if (vX > nX && vY > nY && v == 0
                || vX < nX && vY > nY && v == 3
                || vX < nX && vY < nY && v == 2
                || vX > nX && vY < nY && v == 1) {
            return EAction.TurnLeft;
        } else if (vX < nX && vY > nY && v == 0
                || vX < nX && vY < nY && v == 3
                || vX > nX && vY < nY && v == 2
                || vX > nX && vY > nY && v == 1) {
            return EAction.TurnRight;
        } else {

            return EAction.Pass;
        }
    }

    public boolean checkRoute(Node start, Node goal, Graph graph) {
        if (findShortestPath(start, goal, graph) == null) {
            return true;
        } else {
            System.out.println("Returned false in check route");
            return false;
        }
    }

    public boolean canFindPath(Node start, Node goal, Graph graph) {
        List<Node> nodes = graph.getNodes();
        nodes.stream().forEach((n) -> {
            n.resetNode();
        });
        nodes = findShortestPath(start, goal, graph);
        return nodes != null;
    }

    public static List<Node> getLength(Node start, Node goal, Graph graph) {
        List<Node> nodes = graph.getNodes();
        nodes.stream().forEach((n) -> {
            n.resetNode();
        });
        nodes = findShortestPath(start, goal, graph);
        System.out.println(nodes);
        return nodes;
    }

    private static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    private List<Node> findToBlocked(Node start, Node goal, Graph graph) {
        Queue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        start.setGVal(0);

        Node curNode = start;
        while (true) {
            for (Edge edge : curNode) {
                Node other = edge.getEnd();
                if (other.isBlocked()) {
                    goal = other;
                }
                if (!closedSet.contains(other) && !other.isRock() && !other.isTempBlocked()) {
                    double newG = edge.getWeight() + curNode.getGVal();
                    if (other.getGVal() > newG) {
                        other.setGVal(newG);
                        other.setPrev(curNode);
                    }
                    if (!openSet.contains(other)) {
                        other.setHVal(getMinimumDist(other, goal));
                        openSet.add(other);
                    }
                }
            }
            if (openSet.isEmpty()) {
                return null;
            }
            closedSet.add(curNode);
            curNode = openSet.poll();
            {
                if (curNode == goal) {
                    ArrayList<Node> res = new ArrayList<>();
                    do {
                        res.add(curNode);
                        curNode = curNode.getPrev();
                    } while (curNode != null);
                    Collections.reverse(res);
                    return res;
                }
            }
        }
    }

    public EAction handleBlocked(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, Node start, Node goal, Graph graph, List<EAction> possibleActions) {
        List<Node> nodeList;
        boolean sa;
        nodeList = findToBlocked(start, goal, graph);
        if (nodeList != null && !visibleLocations.isEmpty()) {
            sa = nodeList.get(1) == graph.getNode(visibleLocations.get(0).getX(), visibleLocations.get(0).getY());
        } else {
            sa = false;
        }

        System.out.println("So is it true? " + sa);
        try {
            if (nodeList.get(1) == null) {
                return EAction.Pass;
            }
        } catch (NullPointerException e) {
            return EAction.Pass;
        }
        
        if (thisAnt.carriesSoil() && nodeList.get(1).isBlocked() || thisAnt.carriesSoil() && nodeList.get(1).isTempBlocked()) {
            if(!visibleLocations.isEmpty()) {
            if (!visibleLocations.get(0).isFilled() && !visibleLocations.get(0).isRock()) {
                return EAction.DropSoil;
            }
            }
            return closestEmptyNode(thisLocation, graph, thisAnt);
        } else if (!thisAnt.carriesSoil() && nodeList.get(1).isBlocked() && sa) {
            System.out.println("INSIDE DIGOUT!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return EAction.DigOut;
        } else if (nodeList != null) {
            int nX = 0;
            int nY = 0;
            nX = (int) nodeList.get(1).getXPos();
            nY = (int) nodeList.get(1).getYPos();
            return findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);

        } else {
            return EAction.Pass;
        }

    }

    public EAction closestEmptyNode(ILocationInfo thisLocation, Graph g, IAntInfo thisAnt) {
        switch (thisAnt.getDirection()) {
            case 0:
                if (g.getNode(thisLocation.getX() - 1, thisLocation.getY()) == null || !g.getNode(thisLocation.getX() - 1, thisLocation.getY()).isBlocked()) {
                    return EAction.TurnLeft;
                } else if ( g.getNode(thisLocation.getX() + 1, thisLocation.getY()) == null || !g.getNode(thisLocation.getX() + 1, thisLocation.getY()).isBlocked()) {
                    return EAction.TurnRight;
                } else {
                    return EAction.TurnLeft;
                }
            case 1:
                if (g.getNode(thisLocation.getX(), thisLocation.getY() + 1) == null || !g.getNode(thisLocation.getX(), thisLocation.getY() + 1).isBlocked()) {
                    return EAction.TurnLeft;
                } else if (!g.getNode(thisLocation.getX(), thisLocation.getY() - 1).isBlocked() || g.getNode(thisLocation.getX(), thisLocation.getY() - 1) == null) {
                    return EAction.TurnRight;
                } else {
                    return EAction.TurnLeft;
                }
            case 2:
                if (g.getNode(thisLocation.getX() + 1, thisLocation.getY()) == null || !g.getNode(thisLocation.getX() + 1, thisLocation.getY()).isBlocked()) {
                    return EAction.TurnLeft;
                } else if (g.getNode(thisLocation.getX() - 1, thisLocation.getY()) == null || !g.getNode(thisLocation.getX() - 1, thisLocation.getY()).isBlocked() && !g.getNode(thisLocation.getX() - 1, thisLocation.getY()).isTempBlocked()) {
                    return EAction.TurnRight;
                } else {
                    return EAction.TurnLeft;
                }
            case 3:
                if (g.getNode(thisLocation.getX(), thisLocation.getY() - 1) == null || !g.getNode(thisLocation.getX(), thisLocation.getY() - 1).isBlocked()) {
                    return EAction.TurnLeft;
                } else if (g.getNode(thisLocation.getX(), thisLocation.getY() + 1) == null || !g.getNode(thisLocation.getX(), thisLocation.getY() + 1).isBlocked()) {
                    return EAction.TurnRight;
                } else {
                    return EAction.TurnLeft;
                }
            default:
                return EAction.Pass;
        }
    }
}
