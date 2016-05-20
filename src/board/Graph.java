package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Tobias Grundtvig
 */
public class Graph
{
   private final ArrayList<Node> nodes;
   private final ArrayList<Edge> edges;
   private int gateNumber = -1;

   public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
   
    public int getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(int gateNumber) {
        this.gateNumber = gateNumber;
    }
   
    public void tempBlockedCountertick(){
        for(Node node : nodes){
            if( node.isTempBlocked() ){
                node.tickTempBlockedCounter();
            }
        }
    }
    
    public List<Node> getNodes(){
        return nodes;
    }
    
    public Node createNode(String name, double xPos, double yPos){
        Node res = new Node(name, xPos, yPos);
        nodes.add(res);
        return res;
    }
    
    public void createEdge(Node begin, Node end, double weight){
       Edge edge = new Edge(begin, end, weight);
       edges.add(edge);
    }
    
    public void setBlocked(int x, int y, boolean a) {
        for(Node node : nodes) {
            if(node.getXPos() == x && node.getYPos() == y) {
                node.setBlocked(a);
            }
        }
    }
    
    public Node getNode(int x, int y) {
        for(Node node : nodes) {
            if(node.getXPos() == x && node.getYPos() == y) {
                return node;
            }
        }
        return null;
    }
    
    public int getWorldSizeX(){
        Node res = null;
        for(Node temp : nodes){
            if( res == null || temp.getXPos() > res.getXPos() ){
                res = temp;
            }
        }
        return (int) res.getXPos() +1;
    }
    
    public int getWorldSizeY(){
        Node res = null;
        for(Node temp : nodes){
            if( res == null || temp.getYPos() > res.getYPos() ){
                res = temp;
            }
        }
        return (int) res.getYPos() +1;
    }
}
