package board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Tobias Grundtvig
 */
public class Node implements Iterable<Edge>, Comparable<Node>
{
    public Node(String name, double xPos, double yPos)
    {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        prev = null;
        gVal = Double.POSITIVE_INFINITY;
        hVal = Double.POSITIVE_INFINITY;
        edges = new ArrayList<>();
    }

    private final String name;
    private final double xPos;
    private final double yPos;
    private boolean blocked;
    private int foodCount;
    private boolean rock;
    private boolean wall;
    private boolean discovered;

    public boolean isWall(){
        return this.wall;
    }
    
    public void setWall(boolean isWall){
        this.wall = isWall;
    }
    
    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
    
    public boolean isRock(){
        return rock;
    }
    public void setRock(){
        rock = true;
    }
    public void setFoodCount(int foodCount){
        this.foodCount = foodCount;
    }
    public int getFoodCount(){
        return foodCount;
    }
    
    
    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    private Node prev;
    private double gVal;
    private double hVal;
    private final ArrayList<Edge> edges;

    
    public double getXPos()
    {
        return xPos;
    }

    public double getYPos()
    {
        return yPos;
    }
    
    

    public void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public Node getPrev()
    {
        Node tPrev = prev;
        return tPrev;
    }

    public void setPrev(Node prev)
    {
        this.prev = prev;
    }

    public double getGVal()
    {
        return gVal;
    }

    public void setGVal(double gVal)
    {
        this.gVal = gVal;
    }

    public double getFVal()
    {
        return gVal + hVal;
    }

    public double getHVal()
    {
        return hVal;
    }

    public void setHVal(double hVal)
    {
        this.hVal = hVal;
    }
    
    

    @Override
    public String toString()
    {
        return name + ": (" + xPos + ", " + yPos + ")";
    }

    @Override
    public Iterator<Edge> iterator()
    {
        return edges.iterator();
    }

    @Override
    public int compareTo(Node o)
    {
        if(this.getFVal() < o.getFVal())
        {
            return -1;
        }
        if(this.getFVal() > o.getFVal())
        {
            return 1;
        }
        if(this.getHVal() < o.getHVal())
        {
            return -1;
        }
        if(this.getHVal() > o.getHVal())
        {
            return 1;
        }
        return 0;
    }
    
    public void resetNode() {
        prev = null;
        gVal = Double.POSITIVE_INFINITY;
        hVal = Double.POSITIVE_INFINITY;
    }

}
