/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airemake;

import Ants.AntMethods;
import Ants.Queen;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import board.EulerHeristic;
import board.Graph;
import board.IHeuristic;
import board.Node;
import java.util.List;

/**
 *
 * @author bbalt
 */
public class AntControl implements aiantwars.IAntAI {

    Queen queen = new Queen();
    Graph graph = new Graph();
    int startPos;
    int worldSizeX;
    int worldSizeY;

    // <editor-fold defaultstate="collapsed">  
    public boolean inside(int x, int y) {
        return (x >= 0 && x < worldSizeX && y >= 0 && y < worldSizeY);
    }

    public void edgeNeighbour(Graph g, Node[][] nodes, int x, int y, Node m, IHeuristic h) {
        if (inside(x, y)) {
            Node n = nodes[x][y];
            if (n != null) {
                g.createEdge(m, n, h.getMinimumDist(m, n));
            }
        }
    }
    // </editor-fold>

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        if (thisAnt.getAntType().equals(EAntType.QUEEN)) {

            Node[][] nodes = new Node[worldSizeX][worldSizeY];
            IHeuristic h = new EulerHeristic();
            AntMethods def = new AntMethods();

            this.worldSizeX = worldSizeX;
            this.worldSizeY = worldSizeY;
            
            // Define Start Position
            // <editor-fold defaultstate="collapsed">
            if (worldSizeX == 0 || worldSizeY == 0) {
                this.startPos = 1; // South West
            }
            if (worldSizeX == 0 || worldSizeY > 0) {
                this.startPos = 2; // North East
            }
            if (worldSizeX > 0 || worldSizeY == 0) {
                this.startPos = 3; // South West
            }
            if (worldSizeX > 0 || worldSizeY > 0) {
                this.startPos = 4; // North West
            }
            // </editor-fold>
            
            // Create Board
            // <editor-fold defaultstate="collapsed">
            for (int y = 0; y < worldSizeY; ++y) {
                for (int x = 0; x < worldSizeX; ++x) {
                    nodes[x][y] = graph.createNode("N", x, y);
                }
            }
            System.out.println("Success B " + graph.getNodes());
            // </editor-fold>

            // Create Edges
            // <editor-fold defaultstate="collapsed">
            for (int y = 0; y < worldSizeY; ++y) {
                for (int x = 0; x < worldSizeX; ++x) {
                    Node m = nodes[x][y];
                    if (m != null) {
                        //North
                        int nx = x;
                        int ny = y + 1;
                        edgeNeighbour(graph, nodes, nx, ny, m, h);
                        //East
                        nx = x + 1;
                        ny = y;
                        edgeNeighbour(graph, nodes, nx, ny, m, h);
                        //South
                        nx = x;
                        ny = y - 1;
                        edgeNeighbour(graph, nodes, nx, ny, m, h);
                        //West
                        nx = x - 1;
                        ny = y;
                        edgeNeighbour(graph, nodes, nx, ny, m, h);
                    }
                }
            }
            System.out.println("Getting List: " + graph.getNodes().size());
            // </editor-fold>
        }
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt) {
        System.out.println("ID: " + thisAnt.antID() + " onStartTurn");
    }

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            return queen.generalQueenControl(thisAnt, thisLocation, visibleLocations, possibleActions, graph, startPos);
        }
        return EAction.Pass;
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
