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

    private final IHeuristic heuristic;

    public AntFindPath(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    public List<Node> findShortestPath(Node start, Node goal, Graph graph) {
        Queue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        start.setGVal(0);

        Node curNode = start;
        while (true) {

            for (Edge edge : curNode) {

                Node other = edge.getEnd();
                if (!closedSet.contains(other) && !other.isBlocked() && !other.isRock()) {
                    double newG = edge.getWeight() + curNode.getGVal();
                    if (other.getGVal() > newG) {
                        other.setGVal(newG);
                        other.setPrev(curNode);
                    }
                    if (!openSet.contains(other)) {
                        other.setHVal(heuristic.getMinimumDist(other, goal));
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

    public EAction NextStep(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, Node start, Node goal, Graph graph) {

        List<Node> nodes;
        nodes = findShortestPath(start, goal, graph);
        int v = thisAnt.getDirection();

        int nX = 0;
        int nY = 0;
        if(nodes != null) {
        if (nodes.size() >= 2) {
            nX = (int) nodes.get(1).getXPos();
            nY = (int) nodes.get(1).getYPos();
        }
        }

        return findDirection(nX, nY, thisLocation, visibleLocations, thisAnt, true);
    }

    public EAction findDirection(int nX, int nY, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, IAntInfo thisAnt, boolean move) {
       
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
        System.out.println();

        if (move == true && vX == nX && vY == nY) {
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

}
