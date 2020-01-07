/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * A class that implements the AbstractSeaFactory interface (Abstract Factory Design-Pattern) and represents
 * a concrete class for producing an Animal (Like Swimmable). 
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class AnimalFactory implements AbstractSeaFactory
{
	 
	public AnimalFactory() // Default Constructor
	{
		
	}
	
	/**
	 * Overrides the AbstractSeaFactory function and handles the creation of new SeaCreature (Animal/Swimmable)
	 * @param type - a Type parameter, representing the SeaCreature that needs to be created, either FISH or 
	 * JELLYFISH as defined in AbstractSeaFactory as an Enum.
	 * @return SeaCreature - the newly created SeaCreature
	 */
	@Override
	public SeaCreature produceSeaCreature(Type type) 
	{
		SeaCreature newCreature = null;
		switch(type)
		{
		case FISH: 
			newCreature = new Fish();
			break;
		case JELLYFISH:
			newCreature = new Jellyfish();
			break;
		default:
			newCreature = new Fish();
			break;
		}	
		return newCreature;
	}

}
