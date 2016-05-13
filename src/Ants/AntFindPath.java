/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
            double heuristicX = start.getXPos() -   goal.getXPos();
            double heuristicY = start.getYPos()  -   goal.getYPos();

            //sæt heuristic value til positiv værdi 
                if(heuristicX < 0) { heuristicX = heuristicX * -1; }
                if(heuristicY < 0) { heuristicY = heuristicY * -1; }
        
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
                if (!closedSet.contains(other) && !other.isBlocked() && !other.isRock() && !other.isAntHere()) {
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
            if (!curNode.isBlocked()) {
                closedSet.add(curNode);
            }
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

        //List<Node> shortestPath = findShortestPath(start, goal, graph);
        System.out.println("moving from "+start+" to "+goal);
        int nX = 0;
        int nY = 0;
        if(nodes != null) {
            if (nodes.size() >= 2) { //hvorfor 2 ??
                nX = (int) nodes.get(1).getXPos();
                nY = (int) nodes.get(1).getYPos();
            }
            return findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, possibleActions, true);
        } else {
            return EAction.Pass;
        }
    }

    public static EAction findDirection(int nX, int nY, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, IAntInfo thisAnt, List<EAction> possibleActions ,boolean move) {
        
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
        System.out.println("this direction:"+v+" vX:"+vX+" vY:"+vY+" nX:"+nX+" nY:"+nY);
        System.out.println();

        if (move == true && vX == nX && vY == nY && !visibleLocations.get(0).isFilled() &&  visibleLocations.get(0).getAnt() == null) {
           System.out.println("moveforward");
            return EAction.MoveForward;
        } else if(move == true && vX == nX && vY == nY && !visibleLocations.get(0).isFilled() && possibleActions.contains(EAction.DigOut)){
            System.out.println("digout");
            return EAction.DigOut;
        }else if (vX == nX || vY == nY) {
            System.out.println("turnleft");
            return EAction.TurnLeft;
        } else if (vX > nX && vY > nY && v == 0
                || vX < nX && vY > nY && v == 3
                || vX < nX && vY < nY && v == 2
                || vX > nX && vY < nY && v == 1) {
            System.out.println("turnleft");
            return EAction.TurnLeft;
        } else if (vX < nX && vY > nY && v == 0
                || vX < nX && vY < nY && v == 3
                || vX > nX && vY < nY && v == 2
                || vX > nX && vY > nY && v == 1) {
            System.out.println("turnRight");
            return EAction.TurnRight;
        } else {
            
            return EAction.Pass;
        }
    }
    
    public static List<Node> getLength(Node start, Node goal, Graph graph) {
        System.out.println("Start: " + start);
        System.out.println("End: " + goal);
        System.out.println("Graph: " + graph.getNodes().size());
        System.out.println("In method " +findShortestPath(start, goal, graph));
        List<Node> nodes;
        nodes = findShortestPath(start, goal, graph);
        System.out.println(nodes);
        return nodes;
    }

    private static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }
}
