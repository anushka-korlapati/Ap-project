// Character.java
package entities;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Character {
    private ImageView characterView;
    private double positionX;
    private double positionY;
    private double speed;
    private boolean isWalking;
    private boolean isUpsideDown;
    private boolean isFalling;
    private boolean isStickUp;
    private int cherriesCollected;

    public ImageView getCharacterView() {
        return characterView;
    }

    public void setCharacterView(ImageView characterView) {
        this.characterView = characterView;
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

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public boolean isUpsideDown() {
        return isUpsideDown;
    }

    public void setUpsideDown(boolean upsideDown) {
        isUpsideDown = upsideDown;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isStickUp() {
        return isStickUp;
    }

    public void setStickUp(boolean stickUp) {
        isStickUp = stickUp;
    }

    public int getCherriesCollected() {
        return cherriesCollected;
    }

    public void setCherriesCollected(int cherriesCollected) {
        this.cherriesCollected = cherriesCollected;
    }

    public void moveForward() {
        positionX += speed;
        characterView.setX(characterView.getX()+50);

        // Update the layoutX of the character's ImageView
        characterView.setLayoutX(positionX);
    }

    public void moveUp() {
        characterView.setY(characterView.getY()-10);
    }

    public void moveDown() {
        characterView.setY(characterView.getY()+10);
    }

    public void translate(double distance) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(characterView);
        translate.setDuration(Duration.millis(1500));
        translate.setByX(distance);
        translate.play();
    }

    public void fall() {
        // fall function
    }

    public void upsideDownWalk() {
        // upside down walk function
    }

    public void collectCherries() {
        // collect cherries function
    }

    public void stickPosition() {
        // stick position function
    }

    public void jump() {
        // jump function
    }

    public void changeCharacter() {
        // change function
    }

    public Character(double positionX, double positionY, double speed, ImageView characterImageView) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.characterView = characterImageView;
    }
}