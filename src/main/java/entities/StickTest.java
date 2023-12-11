package entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StickTest {

    @Test
    public void testCreateStick() {
        // Create a stick
        Stick stick = new Stick(10, 20);

        // Get the stick line
        Line stickLine = stick.getStickLine();

        // Verify that the stick line is not null
        assertNotNull(stickLine);

        // Verify the initial length is 0
        assertEquals(0.0, stick.getLength(), 0.001);

        // Verify the initial coordinates of the stick line
        assertEquals(10.0, stickLine.getStartX(), 0.001);
        assertEquals(20.0, stickLine.getStartY(), 0.001);
        assertEquals(10.0, stickLine.getEndX(), 0.001);
        assertEquals(20.0, stickLine.getEndY(), 0.001);

        // Verify the stroke width and color
        assertEquals(10.0, stickLine.getStrokeWidth(), 0.001);
        assertEquals(Color.rgb(150, 75, 0), stickLine.getStroke());

        // Verify the initial opacity is 0
        assertEquals(0.0, stickLine.getOpacity(), 0.001);
    }
}