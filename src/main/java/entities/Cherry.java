package entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry {
    private double positionX;
    private double positionY;
    private boolean isCollected;
    private ImageView cherryImageView;

    public Cherry(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isCollected = false;

        // Assuming you have an image for the cherry, update the path accordingly
        Image cherryImage = new Image("com/example/stickhero/9162409-removebg-preview.png");
        cherryImageView = new ImageView(cherryImage);
        cherryImageView.setX(positionX);
        cherryImageView.setY(positionY);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public ImageView getCherryImageView() {
        return cherryImageView;
    }

    public void collectCherry() {
        isCollected = true;
        // Hide the cherry image or remove it from the scene
        cherryImageView.setVisible(false);
    }

    public boolean checkCollision(ImageView characterImageView) {
        double cherryX = cherryImageView.getX();
        double cherryY = cherryImageView.getY();

        double characterX = characterImageView.getX();
        double characterY = characterImageView.getY();

        double cherryWidth = cherryImageView.getBoundsInParent().getWidth();
        double cherryHeight = cherryImageView.getBoundsInParent().getHeight();

        double characterWidth = characterImageView.getBoundsInParent().getWidth();
        double characterHeight = characterImageView.getBoundsInParent().getHeight();

        // Check for collision by comparing the bounds of the cherry and character
        return cherryX < characterX + characterWidth &&
                cherryX + cherryWidth > characterX &&
                cherryY < characterY + characterHeight &&
                cherryY + cherryHeight > characterY;
    }

    public void setCherryPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        cherryImageView.setX(positionX);
        cherryImageView.setY(positionY);
    }

    // You might need a method to reset the cherry (if the game resets)
    public void resetCherry() {
        isCollected = false;
        // Show the cherry image and reposition it
        cherryImageView.setVisible(true);
        cherryImageView.setX(positionX);
        cherryImageView.setY(positionY);
    }
}