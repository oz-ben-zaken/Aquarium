/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */


/**
 * An interface for implementing the Abstract Factory design-pattern.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public interface AbstractSeaFactory 
{
	static public enum Type{FISH, JELLYFISH, ZOSTERA, LAMINARIA};
	
	/**
	 * 
	 * @param type - a Type parameter, representing the SeaCreature that needs to be created
	 * @return SeaCreature - the newly created SeaCreature
	 */
	public SeaCreature produceSeaCreature(Type type);
}
