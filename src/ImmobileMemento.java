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
 * Class that implements the Memento Design Pattern by allowing to save an Immobile state by saving the 
 * Immobile's color, size and coordinates (x&y) and also allowing to restore the saved state.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class ImmobileMemento 
{
	Color color;
	int size, x, y; 
	
	/**
	 * Default construct for saving a state by saving the size and coordinates.
	 * @param _size - int value of the Immobile size 
	 * @param _x - int value of the Immobile x coordinate
	 * @param _y - int value of the Immobile y coordinate
	 */
	public ImmobileMemento(int _size, int _x, int _y)
	{
		this.color = Color.GREEN;
		this.size = _size;
		this.x = _x;
		this.y = _y;
	}
	
	/**
	 * Receives and immobile and sets it's state to the saved state by setting it's paramaters to the saved ones.
	 * @param immobile - the immobile that it's state needs to be restored.
	 */
	public void restoreState(Immobile immobile)
	{
		immobile.SetSize(size);
		immobile.SetX(x);
		immobile.SetY(y);
	}
}
