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
 * Class that implements the MarineAnimal Interface and handles the paint of the Marine Animal (Swimmable).
 * This class is also related to the Decorator Design Pattern.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 *
 */
public class MarineAnimalDecorator implements MarineAnimal
{
	MarineAnimal marineAnimal;
	
	/**
	 * Default constructor for initiating the MarineAnimal object
	 * @param marineAnimal - the MarineAnimal object
	 */
	public MarineAnimalDecorator(MarineAnimal marineAnimal)
	{
		this.marineAnimal = marineAnimal;
	}
	
	/**
	 * Function that overrides the MarineAnimal Interface PaintFish and handles the painting of the MarineAnimal.
	 */
	@Override
	public void PaintFish(Color color) 
	{
		marineAnimal.PaintFish(color);
	}

}
