// HelloController.java
package com.example.stickhero;

import entities.*;
import entities.Character;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController implements Initializable {
    private HelloController controller;

    private Random random = new Random();

    AnimationTimer gameLoop;
    @FXML
    private AnchorPane plane;

    @FXML
    private Label score;

    @FXML
    public ImageView cherryImageView;

    private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0;
    private boolean gameStarted = false;


    double stickEnd;
    double rectangleRange;
    private Stage stage;
    private Scene scene;
    private Line stickLine;
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Character character;
    private Shark shark;
//    private List<Cherry> cherries = new ArrayList<>();
    private double cherrySize = 20; // You can adjust the size based on your needs
    private double planeWidth = 800; // Set it to the actual width of your game plane
    private double planeHeight = 730; // Set it to the actual height of your game plane

    private boolean isStickExtending = false;

    private Stick currentStick;  // Added variable to keep track of the current stick

    private Platform platformHandler;

    @FXML
    private ImageView characterImageView;

    double characterPosX = 100;

    @FXML
    private ImageView sharkImageView;
    private Timeline timeline;
    private MediaPlayer mediaPlayer;
    private Parent root;
    private boolean isSpaceBarPressed = false;
    private int firstTime = 0;
    Timeline running;


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
        } else if (event.getCode() == KeyCode.DOWN) {
            // Invert the character when the down arrow key is pressed
            characterImageView.setY(characterImageView.getY() + 60);
            characterImageView.setScaleY(characterImageView.getScaleY() * -1);
        } else if (event.getCode() == KeyCode.UP) {
            characterImageView.setY(characterImageView.getY() - 60);
            characterImageView.setScaleY(characterImageView.getScaleY() * -1);
        }
    }

    public boolean charLanded() {
        return rectangle2.getX() <= stickLine.getEndX() && rectangle2.getX() + rectangle2.getWidth() >= stickLine.getEndX();
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

            stickEnd = newEndX;
            rectangleRange = rectangle2.getX() + rectangle2.getWidth();

            // Set the new endX and endY
            stickLine.setEndX(newEndX);
            stickLine.setEndY(newEndY);


            // Translate the character
//            character.translate();
            final int[] x = {(int) character.getPositionX()};
            running = new Timeline(new KeyFrame(Duration.millis(5), event1 -> {
                if (charLanded()) {
                    if (collectCherry()) {
                        cherryImageView.setOpacity(0);
                        scoreCounter++;
                        score.setText(String.valueOf(scoreCounter));
                    }
                    if (x[0] < stickLine.getEndX() + 30) {
                        System.out.println(stickLine.getEndX());
                        System.out.println(stickLine.getStartX());
                        System.out.println(x[0]);
                        moveCharacterX(characterImageView, 1, x[0]);
                        x[0]++;
                        System.out.println(x[0]);// Adjust the speed based on your preference
                    } else {
                        stop();
                        resetGame();
                    }
                } else {
                    if (x[0] < stickLine.getEndX() + 30) {
                        System.out.println(stickLine.getEndX());
                        System.out.println(stickLine.getStartX());
                        System.out.println(x[0]);
                        moveCharacterX(characterImageView, 1, x[0]);
                        x[0]++;
                        System.out.println(x[0]);// Adjust the speed based on your preference
                    } else {
                        stop();
                        fallCharacter();
                        try {
                            switchToEnd();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                })
            );
            running.setCycleCount(Animation.INDEFINITE);
            running.play();


            // Move the character up if its the first case of stick extension

            System.out.println("Character moved forward to positionX: " + character.getPositionX());
            character.moveUp();

            firstTime++;

            if (controller != null) {
                // Set the gameStarted flag
                controller.setGameStarted(true);
            }

            // Initialize the next stick
            double stickStartX = 157.0;  // Use a fixed value or adjust as needed
            double stickStartY = 375.0;  // Use a fixed value or adjust as needed

            currentStick = new Stick(stickStartX, stickStartY);
            plane.getChildren().add(currentStick.getStickLine());  // Add the next stick to the scene
        }
    }

    private void stop()
    {
        running.stop();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the media player
        // Implementing Singleton Design Pattern, so that only one instance of MediaPlayer is created
        mediaPlayer();

        double posX, posY, width, height;
        rectangle1 = new Rectangle(54, 385, 105, 248);
        rectangle1.setFill(Color.WHITE);

        rectangle2 = new Rectangle(330, 385, 105, 248);
        rectangle2.setFill(Color.WHITE);

        double planeHeight = 730;
        double planeWidth = 834;

        // Set initial coordinates for the stick
        double startX = 157.0;
        double startY = 380.0;
        double endX = 157.0;
        double endY = 375.0;

        // Create a new Line object
        stickLine = new Line(startX, startY, endX, endY);

        // Set the width and color of the line
        stickLine.setStrokeWidth(10.0);
        stickLine.setStroke(Color.rgb(150, 75, 0));

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

        // Initialize the first stick
        double stickStartX = 157.0;  // Use a fixed value or adjust as needed
        double stickStartY = 375.0;  // Use a fixed value or adjust as needed

        currentStick = new Stick(stickStartX, stickStartY);
        if (plane != null) {
            plane.getChildren().add(currentStick.getStickLine());
            plane.getChildren().add(rectangle1);
            plane.getChildren().add(rectangle2);
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    update(HelloController.this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        gameLoop.start();
    }

    Rectangle rectangle;
    public Image cherryImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/stickhero/9162409-removebg-preview.png")));

    public boolean collectCherry() {
        return cherryImageView.getX() == characterImageView.getX();
    }

    private void resetGame() {
        // Reset the character position
        character.moveDown();
        // Reset the stick position
        stickLine.setEndX(stickLine.getStartX());
        stickLine.setEndY(stickLine.getStartY());

        // Reset other necessary game state variables
        // (e.g., reset firstTime, clear the rectangles, etc.)
        rectangle = generateRandomRectangle();

//        cherries.add(new Cherry(random.nextDouble() * (planeWidth - cherrySize), random.nextDouble() * (planeHeight - cherrySize)));
        Random random1 = new Random();
        int x = random1.nextInt(75)+50;
        cherryImageView.setOpacity(1);
        cherryImageView.setX(x);
        characterImageView.setX(5);


//        cherries.forEach(Cherry::resetCherry);
    }

    private void mainReset() {

        Rectangle temp = rectangle2;
        rectangle1 = rectangle2;
        rectangle2 = rectangle;

    }
    int isDead = 0;

    // Called every game frame
    private void update(HelloController controller) throws IOException {
        gameTime++;
        accelerationTime++;

        // Set the gameStarted flag
        controller.setGameStarted(true);

//        // Check for cherry collection
//        checkCherryCollection();

        if (madeContact(stickLine, rectangle2)) {
            System.out.println("Collision");
            scoreCounter++;
            score.setText(String.valueOf(scoreCounter));
            gameLoop.stop();

            // Introduce a delay before moving rectangles and resetting the game
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                resetGame();
                mainReset();

            });
            delay.play();
        } else {
            System.out.println("No Collision");
            scoreCounter++;
            gameLoop.stop();

            // Check if the stickLine is extending, then trigger falling
            if (isStickExtending) {
                fallCharacter();
            }

            switchToEnd();
        }
    }

//    private void checkCherryCollection() {
//        Iterator<Cherry> iterator = cherries.iterator();
//        while (iterator.hasNext()) {
//            Cherry cherry = iterator.next();
//            if (!cherry.isCollected() && cherry.checkCollision(characterImageView)) {
//                // Cherry collected
//                cherry.collectCherry();
//                scoreCounter++; // Update the score
//                score.setText(String.valueOf(scoreCounter));
//            }
//        }
//    }

    // Method to make the character fall down
    private void fallCharacter() {
        Timeline fallTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    moveCharacterY(characterImageView, 3.5);
                    isDead = 1;
                })

        );
        fallTimeline.setCycleCount(Animation.INDEFINITE);
        fallTimeline.play();
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
        if (stage != null) {
            stage.setScene(homeScene);
            stage.show();
        } else {
            System.err.println("stage is null. Please check your FXML file.");
        }
    }


    public void switchToEnd() throws IOException {
        if (isDead != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Cannot switch to end scene. Please check collision or game state.");
        }
    }

    private boolean madeContact(Line stick, Rectangle rectangle) {

        double stickEndX = stickEnd;
        double rectangleStartX = rectangle.getX();
        double rectangleEndX = rectangle.getX() + rectangle.getWidth();

        return stickEndX >= rectangleStartX && stickEndX <= rectangleEndX;
    }

    private void moveRectangleOutOfScreen(Rectangle rectangle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    movePlatform(rectangle, -3); // Adjust the amount based on your preference
                })
        );
        timeline.setCycleCount((int) ((int) rectangle.getWidth() / 1.5)); // Adjust the duration based on the width of the rectangle
        timeline.play();
    }

    private void movePlatform(Rectangle platform, double amount) {
        platform.setX(platform.getX() + amount);
    }

    private void moveCharacterX(ImageView characterImageView, double amount, int x) {
        characterImageView.setX(characterImageView.getX() + amount);
        x++;
    }

    private void moveCharacterY(ImageView characterImageView, double amount) {
        characterImageView.setY(characterImageView.getY() + amount);
    }



    private double widthMax = 160;
    private double widthMin = 45;

    private double spaceMin = 10;
    private double spaceMax = 250;

// posX = pos of rectangle2 + space

    private Rectangle generateRandomRectangle() {
        double randomWidth = random.nextDouble() * (widthMax - widthMin) + widthMin;
        double randomSpace = random.nextDouble() * (spaceMax - spaceMin) + spaceMin;
        double randomX = random.nextDouble() * (400 - 250) + 250;

        Rectangle newRandomRectangle = new Rectangle(randomX, planeHeight - randomWidth, randomWidth, 248);
        newRandomRectangle.setFill(Color.WHITE);

        plane.getChildren().add(newRandomRectangle);

        return newRandomRectangle;
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene4(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew4.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene5(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew5.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene6(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew6.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene7(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew7.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene8(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew8.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene9(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew9.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene10(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew10.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene11(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew11.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void mediaPlayer() {
        Media media = new Media(String.valueOf(getClass().getResource("/com/example/stickhero/sb_indreams(chosic.com).mp3")));

        // Create a MediaPlayer
        mediaPlayer = new MediaPlayer(media);

        // Set the volume (0.0 to 1.0)
        mediaPlayer.setVolume(0.5);

        // Set cycle count (MediaPlayer.INDEFINITE for indefinite looping)
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the music
        mediaPlayer.play();
    }
}