// HelloController.java
package com.example.stickhero;

import entities.Character;
import entities.Shark;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    AnimationTimer gameLoop;
    @FXML
    private AnchorPane plane;

    @FXML
    private Text score;

    private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0;


    private Stage stage;
    private Scene scene;
    private Line stickLine;
    private Character character;
    private Shark shark;

    @FXML
    private ImageView characterImageView;

    @FXML
    private ImageView sharkImageView;
    private Timeline timeline;
    private boolean isSpaceBarPressed = false;
    private boolean isStickExtending = false;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStickLine(Line stickLine) {
        this.stickLine = stickLine;
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            isSpaceBarPressed = true;
            isStickExtending = true;
            stickLine.setEndY(stickLine.getEndY() - 10);

            // Make the line visible by setting opacity to 1
            stickLine.setOpacity(1.0);
        }
    }

    @FXML
    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            isSpaceBarPressed = false;
            isStickExtending = false;

            double angle = Math.toRadians(stickLine.getRotate());

            // Calculate the new endX and endY
            double length = Math.abs(stickLine.getStartY() - stickLine.getEndY());
            double newEndX = stickLine.getStartX() + length * Math.cos(angle);
            double newEndY = stickLine.getStartY() - length * Math.sin(angle);

            // Set the new endX and endY
            stickLine.setEndX(newEndX);
            stickLine.setEndY(newEndY);

            // Move the character Up
            character.moveUp();
            System.out.println("Character moved forward to positionX: " + character.getPositionX());

            // Translate the character
            character.translate(stickLine.getEndX() - stickLine.getStartX() + 30);
        }
    }


    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        double planeHeight = 730;
        double planeWidth = 834;


        // Set initial coordinates for the stick
        double startX = 157.0;
        double startY = 475.0;
        double endX = 157.0;
        double endY = 475.0;

        // Create a new Line object
        stickLine = new Line(startX, startY, endX, endY);


        // Set the width and color of the line
        stickLine.setStrokeWidth(10.0);
        stickLine.setStroke(javafx.scene.paint.Color.rgb(150, 75, 0));

        // Set the initial opacity to zero
        stickLine.setOpacity(0.0);

        // Initialize the scene
        scene = new Scene(new AnchorPane());

        // Create a KeyFrame to update the stick line position
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event -> {
            if (isStickExtending) {
                // Update the position of the stick line
                stickLine.setEndY(stickLine.getEndY() - 1);
            }
        });

        // Create a timeline with the key frame
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Initialize the character
        character = new Character(157.0, 400, 1.0, characterImageView);

        // Initialize the shark
        shark = new Shark(-6, 185, 1.0, sharkImageView);

        // Translate the shark
        shark.translate(725);

        // Move the shark Down
        shark.moveDown();
        System.out.println("Shark moved forward to positionX: " + shark.getPositionX());

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        gameLoop.start();
    }

    // Called every game frame
    private void update() {
        gameTime++;
        accelerationTime++;


//        if (isStickExtending && collisionDetection(stickLine, platforms)) {
//            // Handle collision (stop the moving platforms, display game over, etc.)
//            gameLoop.stop();
//            System.out.println("Game Over! Collision detected.");
//            return;
//        }

    }

    public void switchToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // Get the controller from the loader
        HelloController controller = loader.getController();

        // Set the stage for the controller
        controller.setStage(stage);

        // Set the stickLine for the controller
        controller.setStickLine(stickLine);

        // Get the AnchorPane from the loaded root
        AnchorPane anchorPane = (AnchorPane) root;

        // Add the stickLine to the AnchorPane
        anchorPane.getChildren().add(stickLine);

        // Set up the scene with the loaded root
        Scene homeScene = new Scene(root);

        // Set up key event handlers
        homeScene.setOnKeyPressed(controller::handleKeyPress);
        homeScene.setOnKeyReleased(controller::handleKeyRelease);

        // Set the scene to the stage
        stage.setScene(homeScene);
        stage.show();
    }

    public void switchToEnd() throws IOException {
        if (stickLine != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("stickLine is null. Please check your FXML file.");
        }
    }

    public boolean collisionDetection(Line stick, ArrayList<Rectangle> obstacles) {
        for (Rectangle rectangle : obstacles) {
            if (stick.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }
}