package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Stick {
    private double length;
    private Line stickLine;

    public Stick(double startX, double startY) {
        this.length = 0;
        createStick(startX, startY);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Line getStickLine() {
        return stickLine;
    }

    public void createStick(double startX, double startY) {
        this.stickLine = new Line(startX, startY, startX, startY);
        stickLine.setStrokeWidth(10.0);
        stickLine.setStroke(Color.rgb(150, 75, 0));

        // Set the initial opacity to zero
        stickLine.setOpacity(0.0);
    }
}