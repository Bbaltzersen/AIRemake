/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ants;

import board.Edge;
import board.IHeuristic;
import board.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author bbalt
 */
public class AntFindPath {
    private final IHeuristic heuristic;

    public AntFindPath(IHeuristic heuristic)
    {
        this.heuristic = heuristic;
    }
    
    public Iterable<Node> findShortestPath(Node start, Node goal) {
        Queue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        start.setGVal(0);
        Node curNode = start;
        while (true) {
            System.out.println("Considering node: " + curNode);
            for (Edge edge : curNode) {
                Node other = edge.getEnd();
                if (!closedSet.contains(other) && !other.isBlocked()) {
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
                System.out.println("OpenSet is empty");
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
}
