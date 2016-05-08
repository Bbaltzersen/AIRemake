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
                        System.out.println("inside if gval: " + other.isBlocked());
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
                    do
                    {
                        res.add(curNode);
                        curNode = curNode.getPrev();
                        
                    }
                    while(curNode != null);
                    Collections.reverse(res);
                    return res;
                }
            }

        }
    }

    public EAction NextStep(IAntInfo thisAnt, List<ILocationInfo> visibleLocations, Node start, Node goal,Graph graph) {
        System.out.println(start);
        System.out.println(goal);
        List<Node> nodes;
        nodes = findShortestPath(start, goal,graph);
        System.out.println("Nodes in path: " + nodes);
        int vX = -1;
        int vY = -1;
        if (!visibleLocations.isEmpty()) {
            vX = visibleLocations.get(0).getX();
            vY = visibleLocations.get(0).getY();
        }
        
        int nX = (int) nodes.get(1).getXPos();
        int nY = (int) nodes.get(1).getYPos();
        System.out.println("nx: "+ nX+ ", nY: "+nY);
        System.out.println("vx: "+ vX+ ", vY: "+vY);
        if (vX == nX && vY == nY) {
            return EAction.MoveForward;
        } else if (vX != nX || vY != nY) {
            if (vX == nX || vY == nY) {
                return EAction.TurnLeft;
            } else if (vX < nX && vY < nY || vX > nX && vY > nY || vX > nX && vY < nY) {
                return EAction.TurnLeft;
            } else if (vX < nX && vY > nY) {
                return EAction.TurnRight;
            } else {
                System.out.println("Pass in second if statement");
                return EAction.Pass;
            }
        } else {
            System.out.println("Pass in first if statement");
            return EAction.Pass;
        }
    }
}
