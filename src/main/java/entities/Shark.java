package entities;

import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Shark {
    private ImageView sharkView;
    private double positionX;
    private double positionY;
    private double speed;

    public ImageView getSharkView() {
        return sharkView;
    }

    public void setSharkView(ImageView sharkView) {
        this.sharkView = sharkView;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void moveForward() {
        positionX += speed;
        sharkView.setX(sharkView.getX()+50);

        // Update the layoutX of the character's ImageView
        sharkView.setLayoutX(positionX);
    }

    public void moveUp() {
        sharkView.setY(sharkView.getY()-10);
    }

    public void moveDown() {
        if (sharkView != null) {
            sharkView.setY(sharkView.getY()+10);
        }
    }

    public void translate(double distance) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sharkView);
        translate.setDuration(Duration.millis(15000));
        translate.setByX(distance);
        translate.play();
    }


    public void moveForwardContinuously() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                while (true) {
                    moveForward();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Handle interruption if needed
                        break;
                    }
                }
                return null;
            }
        };

        new Thread(task).start();
    }


    public Shark(double positionX, double positionY, double speed, ImageView sharkView) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.sharkView = sharkView;
    }
}