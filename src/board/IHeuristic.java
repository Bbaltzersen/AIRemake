/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import board.Node;

/**
 *
 * @author Tobias Grundtvig
 */
public interface IHeuristic
{
    public double getMinimumDist(Node a, Node b);
}
