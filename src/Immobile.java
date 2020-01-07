/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.*;

/**
 * Class that implements the SeaCreature interface (Abstract Factory Design Pattern) and represents 
 * a SeaCreature (Swimmable and Immobile) that has a name, color, size and coordinates.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public abstract class Immobile implements SeaCreature
{
	String name;

	public Immobile() // Default Constructor
	{
		this.name = "None";
	}
	
	public Immobile(String name) // Custom Constructor
	{
		this.name = name;
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public void SetName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns the name of the plant as a String
	 * @return String - the String representation of the plant name
	 */
	public abstract String GetPlantName();
	
	/**
	 * Receives a Graphics object and draws the Immobile 
	 */
	public abstract void drawCreature(Graphics g);
	
	/**
	 * Returns the color of the Immobile as a Color object
	 * @return Color - the color of the Immobile
	 */
	public abstract Color GetColor();
	
	/**
	 * Returns the string representation of the Immobile's color
	 * @return String - the String representation of the Color object
	 */
	public abstract String GetColorName();
	
	/**
	 * Sets the size of the immobile to the received size
	 * @param size - int representing the new size that needs to be set
	 */
	public abstract void SetSize(int size);
	
	/**
	 * Sets the X coordinate of the Immobile
	 * @param value - int value of the X coordinate
	 */
	public abstract void SetX(int value);
	
	/**
	 * Sets the Y coordinate of the Immobile
	 * @param value- int value of the Y coordinate
	 */
	public abstract void SetY(int value);
	
	/**
	 * Returns the X coordinate of the Immobile
	 * @return int - the X coordinate
	 */
	public abstract int GetX();
	
	/**
	 * Returns the X coordinate of the Immobile
	 * @return int - the X coordinate
	 */
	public abstract int GetY();
	
	/**
	 * Returns the size of the immobile
	 * @return int - the size
	 */
	public abstract int GetSize();
	
	/**
	 * Saves the State of the immobile (Memento Design Pattern) in a ImmobileMememnto object the 
	 * Immobile contains.
	 */
	abstract public void SaveState();
	
	/**
	 * Restores the saved state of the Immobile (Memento Design Pattern) from the ImmobileMemento object the 
	 * Immobile contains.
	 */
	abstract public void RestoreState();
	
	/**
	 * Checks if the Immobile has a saved state by checking if the ImmobileMemento object is null or not.
	 * @return boolean - either true or false, depends on the outcome.
	 */
	abstract public boolean HasSavedState();
}
