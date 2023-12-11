// Platform.java
package entities;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Platform {
    private AnchorPane plane;
    private double planeHeight;
    private double planeWidth;
    private double widthMax = 160;
    private double widthMin = 45;

    private  double spaceMin = 10;
    private double spaceMax = 100;

    private Random random = new Random();

    public Platform(AnchorPane plane, double planeHeight, double planeWidth) {
        this.plane = plane;
        this.planeHeight = planeHeight;
        this.planeWidth = planeWidth;
    }

    public ArrayList<Rectangle> createFirstPlatforms() {
        double platformHeight = 248;

        // Create static platforms with fixed positions
        double positionX_fixed = 54;
        double positionY_fixed = 385;

        // Create dynamic platforms
        double recWidth = random.nextDouble() * (widthMax - widthMin) + widthMin;
        double space = random.nextDouble() * (spaceMax - spaceMin) + spaceMin;

        double width1 = recWidth;

        // platform1
        Rectangle whitePlatform1 = new Rectangle(positionX_fixed, positionY_fixed, recWidth, platformHeight);
        whitePlatform1.setFill(Color.WHITE);

        Rectangle redPlatform1 = new Rectangle(positionX_fixed + recWidth * 0.375, positionY_fixed, recWidth * 0.25, 10);
        redPlatform1.setFill(Color.RED);

        // Add the rectangles to the plane
        plane.getChildren().addAll(whitePlatform1, redPlatform1);

        ArrayList<Rectangle> firstPlatforms = new ArrayList<>(List.of(whitePlatform1, redPlatform1));

        return firstPlatforms;
    }
}