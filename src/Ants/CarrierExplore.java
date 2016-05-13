///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Ants;
//
//import aiantwars.EAction;
//import aiantwars.IAntInfo;
//import aiantwars.ILocationInfo;
//import airemake.AntControl;
//import board.EulerHeristic;
//import board.Graph;
//import board.Node;
//import java.util.List;
//
///**
// *
// * @author bbalt
// */
//public class CarrierExplore {
//
//    AntFindPath fRoute = new AntFindPath(new EulerHeristic());
//
//    public EAction exploreRandom(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions, Graph g, int startPos, int roundNumber, int starX, int starY) {
//        List<Node> nodes = g.getNodes();
//        for (Node n : nodes) {
//            n.resetNode(); // du bruger resetNode flere steder, hvorfor?? er det kun for at sætte previous node til null??
//        }
//        if (thisAnt.getFoodLoad() < 10 && thisLocation.getFoodCount() != 0 ) {
//            g.getNode(thisLocation.getX(), thisLocation.getY()).setFoodCount(thisLocation.getFoodCount() - 1);
//            return EAction.PickUpFood;
//
//        } else if (thisAnt.getFoodLoad() == 10) {
//            return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), getToStartGoal(starX, starY, startPos, g), g);
//        }
//        if (possibleActions.contains(EAction.DigOut)) {
//            return EAction.DigOut;
//        } else if (thisAnt.getFoodLoad() < 10) {
//            return fRoute.NextStep(thisAnt, thisLocation, visibleLocations, g.getNode(thisLocation.getX(), thisLocation.getY()), g.getNode(new AntControl().getWorldSizeX() / 2, new AntControl().getWorldSizeY() / 2), g);
//
//        } else {
//            return EAction.Pass;
//        }
//
//    }
//
//    public Node getToStartGoal(int starX, int starY, int sPos, Graph g) {
//        if (sPos == 1) {
//            return g.getNode(starX + 1, starY);
//        }
//        if (sPos == 2) {
//            return g.getNode(starX, starY - 1);
//        }
//        if (sPos == 3) {
//            return g.getNode(starX, starY + 1);
//        }
//        if (sPos == 4) {
//            return g.getNode(starX, starY - 1);
//        } else {
//            return g.getNode(starX, starY);
//        }
//    }
//}
