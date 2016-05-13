/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import board.IHeuristic;

/**
 *
 * @author Tobias Grundtvig
 */
public class EulerHeristic implements IHeuristic
{

    @Override
    public double getMinimumDist(Node a, Node b)
    {
        double dx = b.getXPos() - a.getXPos();
        double dy = b.getYPos() - a.getYPos();
        return Math.sqrt(dx*dx + dy*dy);
    }
    
}
