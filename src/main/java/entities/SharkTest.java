package entities;

import entities.Shark;
import javafx.scene.image.ImageView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SharkTest {

    @Test
    public void testSharkInitialization() {
        // Create an ImageView for the shark (you can use a mock or a real ImageView)
        ImageView sharkImageView = new ImageView();

        // Create a Shark object
        Shark shark = new Shark(50, 100, 5.0, sharkImageView);

        // Check if the Shark object is not null
        assertNotNull(shark);

        // Check if the sharkImageView is set correctly
        assertEquals(sharkImageView, shark.getSharkView());

        // Check if the initial positionX is set correctly
        assertEquals(50, shark.getPositionX(), 0.01);

        // Check if the initial positionY is set correctly
        assertEquals(100, shark.getPositionY(), 0.01);

        // Check if the speed is set correctly
        assertEquals(5.0, shark.getSpeed(), 0.01);
    }
}