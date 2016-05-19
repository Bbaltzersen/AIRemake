package airemake;

//import Ants.CarrierExplore;
import static Ants.AntMethods.gateOneLocation;
import static Ants.AntMethods.gateTwoLocation;
import static Ants.AntMethods.isInWallArea;
import Ants.AntNest;
import static Ants.CarrierLogic.generalCarrierControl;
//import static Ants.CarrierLogic.generalCarrierControl;
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
    Graph graph = new Graph(); // collective map
    AntNest nest = new AntNest();
    
    int startPos;
    int starX;
    int starY;
    int worldSizeX;
    int worldSizeY;
    int roundNumber;
    int gateNumber = -1;
    
    public int getWorldSizeX() {
        return worldSizeX;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }
    

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
        switch (thisAnt.getAntType()) {
            case QUEEN:
                Node[][] nodes = new Node[worldSizeX][worldSizeY];
                IHeuristic h = new EulerHeristic();
                this.worldSizeX = worldSizeX;
                this.worldSizeY = worldSizeY;
                // Define Start Position
                // <editor-fold defaultstate="collapsed"> </editor-fold>
                if (thisLocation.getX() == 0 && thisLocation.getY() == 0) {
                    this.startPos = 1; // South West
                }   if (thisLocation.getX() == 0 && thisLocation.getY() > 0) {
                    this.startPos = 2; // North West
                }   if (thisLocation.getX() > 0 && thisLocation.getY() == 0) {
                    this.startPos = 3; // South East
                }   if (thisLocation.getX() > 0 && thisLocation.getY() > 0) {
                    this.startPos = 4; // North east
                }   this.starX = thisLocation.getX();
                this.starY = thisLocation.getY();
                // </editor-fold>
                // Create Board
                // <editor-fold defaultstate="collapsed">
                for (int y = 0; y < worldSizeY; ++y) {
                    for (int x = 0; x < worldSizeX; ++x) {
                        nodes[x][y] = graph.createNode("N", x, y);
                    }
                }
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
                // </editor-fold>
                break;
            case CARRIER:
                nest.setCarriers(nest.getCarriers() + 1);
                break;
            case SCOUT:
                nest.setScouts(nest.getScouts() + 1);
                break;
            default:
                nest.setWarriors(nest.getWarriors() + 1);
                break;
        }
    }
    
    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn){
        if(turn > roundNumber ){
            this.roundNumber = turn;
             graph.tempBlockedCountertick();
        }
        
        
    }
    
    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
//        System.out.println(thisAnt.antID()+", "+thisAnt.getAntType()+"----------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("this ant healt and hitpoint: "+thisAnt.getHealth()+" : "+thisAnt.getHitPoints());
//        int tempBnodes = 0;
//        List<Node> bnodes = graph.getNodes();
//        for(Node n : bnodes){
//            if(n.isTempBlocked()) {
//                tempBnodes += 1;
//            }
//        }
//        System.out.println("Amount of tempBlocked nodes: " + tempBnodes);
//        System.out.println("GATE NUMBER PICKED: "+graph.getGateNumber());
//        for(Node node : gateOneLocation( graph, startPos)){
//            System.out.println("GATE "+node.getXPos()+", "+node.getYPos());
//        }
//        for(Node node : gateTwoLocation( graph, startPos)){
//            System.out.println("GATE "+node.getXPos()+", "+node.getYPos());
//        }
        
        
        addLocationsInfoToGraph(visibleLocations,thisLocation,thisAnt);
        
        if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            return queen.generalQueenControl(thisAnt, thisLocation, visibleLocations, possibleActions, graph, startPos, roundNumber, starX, starY);
        }
        if (thisAnt.getAntType().equals(EAntType.CARRIER)) {
            return generalCarrierControl( thisAnt,  thisLocation,  visibleLocations, possibleActions,  graph, queen ,  roundNumber, startPos);
        }
        return EAction.Pass;
    }
    
    private void addLocationsInfoToGraph(List<ILocationInfo> visibleLocations,ILocationInfo thisLocation, IAntInfo thisAnt) {
        for (ILocationInfo location : visibleLocations) {
            Node node = graph.getNode(location.getX(), location.getY());
            node.setDiscovered(true);
            node.setFoodCount(location.getFoodCount());
            if ( location.isRock() ) {
                node.setRock();
            }
            if ( location.isFilled() ) {
                node.setBlocked(true);
            }
            if ( !location.isFilled() ) {
                node.setBlocked(false);
                node.removeTempBlockedCounter();
            }     
            if( isInWallArea( node,  startPos,  graph)    &&   node.isWall()   ||  isInWallArea( node,  startPos,  graph)  &&  node.isRock() ){
                node.setWall(true);
            }
            if( isInWallArea( node,  startPos,  graph)    &&   !location.isFilled() &&  !node.isRock()){
                node.setWall(false);
            }
           
            try{
                int id = location.getAnt().getTeamInfo().getTeamID();
                if( id == thisAnt.getTeamInfo().getTeamID() ){
                     node.setTempBlockedCounter();
                }
            }catch(Exception e){
            }
            
            
            
        }
       Node node = graph.getNode(thisLocation.getX(), thisLocation.getY());
       node.setFoodCount(thisLocation.getFoodCount());
    }
    
    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        if(nest.getCarriers() <= 2) {
            egg.set(EAntType.CARRIER, this);
        } else {
            egg.set(EAntType.WARRIOR, this);
        }
    }
    
    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        System.out.println("was attacked");
        }
    
    @Override
    public void onDeath(IAntInfo thisAnt) {
        switch (thisAnt.getAntType()) {
            case CARRIER:
                nest.setCarriers(nest.getCarriers() + 1);
                break;
            case SCOUT:
                nest.setScouts(nest.getScouts() + 1);
                break;
            default:
                nest.setWarriors(nest.getWarriors() + 1);
                break;
        }
        }
    
    @Override
    public void onStartMatch(int worldSizeX, int worldSizeY) {
        }
    
    @Override
    public void onStartRound(int round) {
            
      }
    
    @Override
    public void onEndRound(int yourMajor, int yourMinor, int enemyMajor, int enemyMinor) {            
       }
    
    @Override
    public void onEndMatch(int yourScore, int yourWins, int enemyScore, int enemyWins) {
     }
    
    
    
}
