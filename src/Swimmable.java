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
import java.util.concurrent.CyclicBarrier;

/**
 * class that represents a swimmable object (for example fish, jellyfish etc) that's able to move 
 * around in a panel and eat a food thrown for it.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public abstract class Swimmable extends Thread implements Cloneable,SeaCreature, MarineAnimal, Observer
{
	public final int MIN_SIZE = 20, MAX_SIZE = 320; // size in pixels for both, fish and jellyfish
	public final int MIN_HOR_SPEED = 1, MAX_HOR_SPEED = 10;
	public final int MIN_VER_SPEED = 1, MAX_VER_SPEED = 10;
	protected int horSpeed; 
	protected int verSpeed; 
	
	/**
	 * Default Swimmable constructor, sets the horizontal and vertical speed to default value of 0
	 */
	public Swimmable() 
	{ 
		horSpeed = 1; 
		verSpeed = 1; 
	} 
	
	/**
	 * Custom Swimmable constructor, sets the horizontal and vertical speed to the received speeds.
	 * @param hor - the horizontal speed as an Integer
	 * @param ver - the vertical speed as an Integer
	 */
	public Swimmable(int hor, int ver)  
	{ 
		horSpeed = hor; 
		verSpeed = ver; 
	}
	
	/**
	 * Returns the horizontal speed of the Swimmable
	 * @return int - the horizontal speed
	 */
	public int getHorSpeed() { return horSpeed; } 
	
	/**
	 * Returns the vertical speed of the Swimmable
	 * @return int - the vertical speed
	 */
	public int getVerSpeed() { return verSpeed; } 
	
	/**
	 * Sets the horizontal speed of the Swimmable
	 * @param hor - the int representing the horizontal speed
	 */
	public void setHorSpeed(int hor) { horSpeed = hor; } 
	
	/**
	 * Sets the vertical speed of the Swimmable
	 * @param ver - the int representing the vertical speed
	 */
	public void setVerSpeed(int ver) { verSpeed = ver; }
	
	/**
	 * Returns the name of the Swimmable animal (for example "fish", "jellyfish" etc)
	 * @return String - the name of the Swimmable animal
	 */
	abstract public String getAnimalName();
	
	/**
	 * Receives a Graphics object and paints in it the Swimmable.
	 * @param g - Graphics object on which the Swimmable is drawn.
	 */
	abstract public void drawAnimal(Graphics g);
	
	/**
	 * Sets the Swimmable in a suspended mode, stopping it from swimming.
	 */
	abstract public void setSuspend();
	
	/**
	 * Sets the swimmable in a resumed mode, allowing it to continue swimming.
	 */
	abstract public void setResume(); 
	
	/**
	 * Sets a CyclicBarrier for the Swimmable, which stops all swimmable when food was thrown until the last 
	 * swimmable called await() method and only then all Swimmables starts swimming towards the good 
	 * simultaneously.
	 * @param b
	 */
	abstract public void setBarrier(CyclicBarrier b); 
	
	/**
	 * Returns the size of the Swimmable.
	 * @return int - the size
	 */
	abstract public int getSize(); 
	
	/**
	 * Increases the eat-counter of the Swimmable, which means the Swimmable successfully ate food.
	 */
	abstract public void eatInc(); 
	
	/**
	 * Returns the eat-counter, the number of times the Swimmable successfully ate food.
	 * @return int - the counter
	 */
	abstract public int getEatCount(); 
	
	/**
	 * String representation of the Swimmable's color
	 * @return String - the color of the Swimmable
	 */
	abstract public String getColor();
	
	/**
	 * A function that sets the isThreadAlive variable to false, indicating the thread no longer runs which ends 
	 * the 'run' method and therefore kills the thread. The function helps properly shut down the thread, mostly
	 * for the 'reset' option available in the programm.
	 */
	abstract public void KillSwimmableThread();
	
	
	abstract public void setColor(Color color);
	abstract public void setSize(int size);
	abstract public void setContainingPanel(AquaPanel panel);
	
	abstract public void set_X_Front(int value);
	abstract public void set_Y_Front(int value);
	public abstract int Get_X_Front();
	public abstract int Get_Y_Front();
	
	
	abstract public void Set_X_Direction(int dir);
	abstract public void Set_Y_Direction(int dir);
	abstract public int Get_X_Direction();
	abstract public int Get_Y_Direction();
	
	abstract public void SaveState();
	abstract public void RestoreState();
	abstract public boolean HasSavedState();
	abstract public void SetEatFrequency(int freq);
	abstract public int GetEatFrequency();

	@Override
	public abstract Object clone();
}
