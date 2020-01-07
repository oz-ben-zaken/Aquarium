/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.Color;

/**
 * Interface that represents a Marine Animal that is paint-able by using the PaintFish function
 * The Interface implements Decorator Design Pattern
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public interface MarineAnimal 
{
	/**
	 * Paints the MarineAnimal in the received color.
	 * @param color - the Color object for the painting
	 */
	public void PaintFish(Color color);
}
