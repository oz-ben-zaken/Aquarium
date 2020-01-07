/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * Interface for implementing the Observer Design Pattern.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public interface Observer 
{
	/**
	 * Function that allows a Swimmable to notify the Aquarium (AquaPanel) it's hungry which consequently
	 * asks the user to drop food to the aquarium and feed the swimmables.
	 */
	public void notifyOfHunger();
}
