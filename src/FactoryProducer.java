/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * Class for getting a factory by passing an information (String) about the relevant factory.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class FactoryProducer 
{
	/**
	 * Function that gets information about the factory and returns the relevant factory.
	 * @param factoryName - A string representing the factory that needs to be returned
	 * @return AbstractSeaFactory - the relevant factory that fits the received information.
	 */
	public static AbstractSeaFactory getFactory(String factoryName)
	{
		if(factoryName.equalsIgnoreCase("AnimalFactory"))
			return new AnimalFactory();
		
		else if(factoryName.equalsIgnoreCase("PlantFactory"))
			return new PlantFactory();
		
		return null;
	}
}
