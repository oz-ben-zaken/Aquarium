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
 * a concrete class for producing an Immobile (Plant). 
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class PlantFactory implements AbstractSeaFactory
{

	public PlantFactory() // Default Constructor
	{
		
	}
	
	/**
	 * Overrides the AbstractSeaFactory function and handles the creation of new Immobile (Plant - like Zostera)
	 * @param type - a Type parameter, representing the Immobile that needs to be created, either ZOSTERA or 
	 * LAMINARIA as defined in AbstractSeaFactory as an Enum.
	 * @return SeaCreature - the newly created SeaCreature.
	 */
	@Override
	public SeaCreature produceSeaCreature(Type type) 
	{
		SeaCreature newCreature = null;
		switch(type)
		{
		case LAMINARIA: 
			newCreature = new Laminaria();
			break;
		case ZOSTERA:
			newCreature = new Zostera();
			break;
		default:
			newCreature = new Laminaria();
			break;
		}		
		return newCreature;
	}

}
