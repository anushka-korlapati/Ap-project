package game;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class GameEngine {
    private Stage stage;
    private boolean gameRunning;
    private Character character;
    private boolean isPaused;
    private boolean isStart;

    public GameEngine(Stage stage) {
        this.stage = stage;
        this.gameRunning = false;
        // Initialize other game components
// commented        this.character = new Character(/* pass required parameters */);
        // Initialize platforms, score, etc.
    }

    public void startGame() {
        if (!gameRunning) {
            gameRunning = true;
            // Start the game loop
            AnimationTimer gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    renderGame();
                }
            };
            gameLoop.start();
        }
    }

    public void renderGame() {
        // Render the game scene
        // Render character, platforms, cherries, and other game elements
    }

    public void pauseGame() {
        gameRunning = false;
        // Implement pause functionality
    }

    public void resumeGame() {
        if (!gameRunning) {
            gameRunning = true;
            // Implement resume functionality
        }
    }

    public void endGame() {
        gameRunning = false;
        // Implement game over actions, maybe display score, allow saving, etc.
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void checkDistance() {
        //check Distance
    }
}