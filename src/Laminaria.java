/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class that extends the abstract class Immobile and represents a Laminaria plant 
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class Laminaria extends Immobile
{
	int size, x, y;
	Color color;
	private ImmobileMemento memento;
	
	public Laminaria() // Default Constructor
	{
		super("NoName");
		SetSize(20);
		this.color = Color.GREEN;
		this.x = 0;
		this.y = 0;
		memento = null;
	}
	public Laminaria(String name, int size, int x, int y) // Custom Constructor
	{
		super(name);
		SetSize(size);
		this.x = x;
		this.y = y;
		memento = null;
	}

	/**
	 * Returns the name of the plant as a String
	 * @return String - the String representation of the plant name
	 */
	@Override
	public String GetPlantName() 
	{
		return "Laminaria";
	}

	/**
	 * Receives a Graphics object and draws the Immobile 
	 */
	@Override
	public void drawCreature(Graphics g) 
	{
		g.setColor(color);
		g.fillArc(x-size/20, y-size, size/10, size*4/5, 0, 360);
		g.fillArc(x-size*3/20, y-size*13/15, size/10, size*2/3, 0, 360);
		g.fillArc(x+size/20, y-size*13/15, size/10, size*2/3, 0, 360);
		g.drawLine(x, y, x, y-size/5);
		g.drawLine(x, y, x-size/10, y-size/5);
		g.drawLine(x, y, x+size/10, y-size/5);
	}
	
	/**
	 * Returns the color of the Immobile as a Color object
	 * @return Color - the color of the Immobile
	 */
	@Override
	public Color GetColor() 
	{
		return this.color;
	}
	
	/**
	 * Returns the string representation of the Immobile's color
	 * @return String - the String representation of the Color object
	 */
	@Override
	public String GetColorName() 
	{
		return "Green";
	}
	
	/**
	 * Sets the size of the immobile to the received size
	 * @param size - int representing the new size that needs to be set
	 */
	@Override
	public void SetSize(int size) 
	{
		this.size = size;
	}
	
	/**
	 * Sets the X coordinate of the Immobile
	 * @param value - int value of the X coordinate
	 */
	@Override
	public void SetX(int value) 
	{
		this.x = value;
	}
	
	/**
	 * Sets the Y coordinate of the Immobile
	 * @param value- int value of the Y coordinate
	 */
	@Override
	public void SetY(int value) 
	{
		this.y = value;
	}
	
	/**
	 * Returns the X coordinate of the Immobile
	 * @return int - the X coordinate
	 */
	@Override
	public int GetX() 
	{
		return this.x;
	}
	
	/**
	 * Returns the X coordinate of the Immobile
	 * @return int - the X coordinate
	 */
	@Override
	public int GetY() 
	{
		return this.y;
	}
	
	/**
	 * Returns the size of the immobile
	 * @return int - the size
	 */
	@Override
	public int GetSize() 
	{
		return this.size;
	}
	
	/**
	 * Saves the State of the immobile (Memento Design Pattern) in a ImmobileMememnto object the 
	 * Immobile contains.
	 */
	@Override
	public void SaveState() 
	{
		this.memento = new ImmobileMemento(size, x,y);
	}
	
	/**
	 * Restores the saved state of the Immobile (Memento Design Pattern) from the ImmobileMemento object the 
	 * Immobile contains.
	 */
	@Override
	public void RestoreState() 
	{
		if(this.memento != null)
			memento.restoreState(this);
	}
	
	/**
	 * Checks if the Immobile has a saved state by checking if the ImmobileMemento object is null or not.
	 * @return boolean - either true or false, depends on the outcome.
	 */
	@Override
	public boolean HasSavedState() 
	{
		if(this.memento != null)
			return true;
		return false;
	}
}
