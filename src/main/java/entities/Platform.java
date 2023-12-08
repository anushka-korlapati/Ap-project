//// Platform.java
//package entities;
//
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Platform {
//
//    private AnchorPane plane;
//    private double planeHeight;
//    private double planeWidth;
//    private double widthMax = 160;
//    private double widthMin = 45;
//
//    private  double spaceMin = 10;
//    private double spaceMax = 100;
//
//    private Random random = new Random();
//
//    public Platform(AnchorPane plane, double planeHeight, double planeWidth) {
//        this.plane = plane;
//        this.planeHeight = planeHeight;
//        this.planeWidth = planeWidth;
//    }
//
//    public ArrayList <Rectangle> createPlatforms() {
//        int platformHeight = 254;
//
//        // Create static platforms with fixed positions
//        int positionX_fixed = 100;
//        int positionY_fixed = 477;
//
//        // Create dynamic platforms
//        double recWidth = random.nextDouble() * (widthMax - widthMin) + widthMin;
//        double space = random.nextDouble() * (spaceMax - spaceMin) + spaceMin;
//
//        // platform1
//        Rectangle whitePlatform1 = new Rectangle(positionX_fixed, positionY_fixed, recWidth, platformHeight);
//        whitePlatform1.setFill(javafx.scene.paint.Color.WHITE);
//
//        Rectangle redPlatform1 = new Rectangle(positionX_fixed + recWidth * 0.375, positionY_fixed, recWidth * 0.25, 10);
//        redPlatform1.setFill(javafx.scene.paint.Color.RED);
//
//    }
//
//}
