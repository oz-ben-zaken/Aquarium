/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * Interface that represents the Hunger State of the Swimmable, implementing the State Design Pattern.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public interface HungerState 
{
	/**
	 * Performs an action according to the current HungerState of the Swimmable.
	 * @param swimmable - The Swimmable that holds the HungerState variable that requires an action to be performed.
	 */
	public void PerformAction(Swimmable swimmable);
	
	/**
	 * Returns the Hunger State of the Swimmable
	 * @return String - the HungerState represented as a string
	 */
	public String GetState();
}
