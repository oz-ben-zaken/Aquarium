/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.*;
import java.util.concurrent.*;

/**
 * A class that h and extends the abstract class Swimmable
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class Jellyfish extends Swimmable
{
	private Color col;
	private int size, x_front, y_front, x_dir,y_dir, eatCount;
	private boolean suspended, isThreadAlive;
	private AquaPanel containingPanel;
	private CyclicBarrier foodCyclicBarrier;
	private SwimmableMemento memento;
	int eatFrequency;
	HungerState hungerState;
	int turnsUntilHunger;
	/**
	 * Default JellyFish constructor, sets all variables to their default values
	 */
	public Jellyfish() 
	{
		super(); // Calls the default constructor of Swimmable
		col = null;
		size = 0;
		x_front = 0;
		y_front = 0;
		x_dir = 1;
		y_dir = 1;
		eatCount = 0;
		suspended = false;
		containingPanel = null;
		isThreadAlive = true;
		foodCyclicBarrier = null;
		memento = null;
		eatFrequency = 0;
		hungerState = new Satiated(containingPanel);
		turnsUntilHunger = 0;
	}
	
	/**
	 * Custom constructor which sets the values of the color,size,horSpeed,verSpeed and containingPanel to the
	 * received values.
	 * @param _col - Color variable representing the color of the JellyFish
	 * @param _size - int variable representing the size of the JellyFish
	 * @param horSpeed - int variable representing the horizontal speed of the JellyFish
	 * @param verSpeed- int variable representing the vertical speed of the JellyFish
	 * @param containingPanel - AquaPanel representing the JPanel that contains the JellyFish and handles it movement
	 * and drawing.
	 */
	public Jellyfish(Color _col, int _size, int horSpeed, int verSpeed, AquaPanel containingPanel)
	{
		super(horSpeed, verSpeed); // Calls the default constructor of Swimmable
		col = _col;
		size = _size;
		x_front = containingPanel.getWidth()/2;
		y_front = containingPanel.getHeight()/2;
		x_dir = 1;
		y_dir = 1;
		eatCount = 0;
		suspended = false;
		this.containingPanel = containingPanel;
		isThreadAlive = true;
		foodCyclicBarrier = null;
		memento = null;
		hungerState = new Satiated(containingPanel);
		turnsUntilHunger = 0;
	}
	
	/**
	 * Returns the name of the JellyFish ("JellyFish")
	 * @return String - the name of the JellyFish 
	 */
	@Override
	public String getAnimalName() 
	{
		return new String("Jellyfish");
	}
	
	/**
	 * Receives a Graphics object and paints in it the JellyFish.
	 * @param g - Graphics object on which the JellyFish is drawn.
	 */
	@Override
	public void drawAnimal(Graphics g) 
	{
		int numLegs;
		if(size<40) 
			numLegs = 5; 
		else if(size<80) 
			numLegs = 9; 
		else numLegs = 12;
		
		g.setColor(col);
		g.fillArc(x_front - size/2, y_front - size/4, size, size/2, 0, 180);
		
		for(int i = 0; i < numLegs; i++)
			g.drawLine(x_front - size/2 + size/numLegs + size*i/(numLegs+1), 
					y_front, x_front - size/2 + size/numLegs + size*i/(numLegs+1), 
					y_front+size/3);			
	}

	/**
	 * Sets the JellyFish in a suspended mode, stopping it from swimming.
	 */
	@Override
	public void setSuspend() 
	{
		this.suspended = true;
	}

	/**
	 * Sets the JellyFish in a resumed mode, allowing it to continue swimming.
	 */
	@Override
	public synchronized void setResume() 
	{
		this.suspended = false;
		notify();
	}

	/**
	 * Sets a CyclicBarrier for the JellyFish, which stops all other Swimmables when food was thrown until the last 
	 * swimmable called await() method and only then all Swimmables starts swimming towards the good 
	 * simultaneously along with the current JellyFish.
	 * @param b
	 */
	@Override
	public void setBarrier(CyclicBarrier b) 
	{
		foodCyclicBarrier = b;
	}

	/**
	 * Returns the size of the JellyFish.
	 * @return int - the size
	 */
	@Override
	public int getSize() 
	{
		return size;
	}

	/**
	 * Increases the eat-counter of the JellyFish, which means the fish successfully ate food.
	 */
	@Override
	public void eatInc() 
	{
		eatCount++;
		this.hungerState = new Satiated(containingPanel);
		this.turnsUntilHunger = this.eatFrequency;
	}

	/**
	 * Returns the eat-counter, the number of times the JellyFish successfully ate food.
	 * @return int - the counter
	 */
	@Override
	public int getEatCount() 
	{
		return eatCount;
	}

	/**
	 * String representation of the JellyFish's color
	 * @return String - the color of the fish
	 */
	@Override
	public String getColor() 
	{
		return ("(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")");
	}
	
	/**
	 * The run method for running the JellyFish (Swimmable) in it's own thread. The thread runs as long as the
	 * isThreadAlive variable is true, which is set to false only if the program finishes or if the aquarium 
	 * was reset. The method handles the JellyFish movement within the boundaries of it's containing panel. It also
	 * checks if the JellyFish was set to sleep, waken up, or if food was thrown and then it directs the JellyFish
	 * towards the middle of the screen in order to eat the thrown food.
	 */
	public void run() 
	{
		super.run();
		while (isThreadAlive == true) 
		{
			while (suspended == false) 
			{
				try 
				{
					Thread.sleep(20);
				} catch (InterruptedException e) 
				{
					e.toString();
				}
				
				if(containingPanel.WasFoodThrown() == true && foodCyclicBarrier != null)
				{
					try 
					{
						foodCyclicBarrier.await();
					} 
					catch (InterruptedException |NullPointerException| BrokenBarrierException e) 
					{
						e.printStackTrace();
					}

				}
				int old_x_dir = this.x_dir;
				this.hungerState.PerformAction(this);
				this.containingPanel.repaint();
				
				if(this.turnsUntilHunger <= 0)
				{
					if(this.hungerState.GetState().equalsIgnoreCase("Satiated"))
					{	
						this.hungerState = new Hungry(containingPanel);
						this.notifyOfHunger(); // Notifies the observer (AquaPanel) the Jellyfish needs to eat
					}
				}
				if(old_x_dir != this.x_dir)
					this.turnsUntilHunger--;
			}
			synchronized (this) 
			{
				while (suspended == true) 
				{
					try 
					{
						wait();
					} 
					catch (InterruptedException e) 
					{
						e.toString();
					}
				}
			}
		}		
	}
	
	/**
	 * A function that sets the isThreadAlive variable to false, indicating the thread no longer runs which ends 
	 * the 'run' method and therefore kills the thread. The function helps properly shut down the thread, mostly
	 * for the 'reset' option available in the programm.
	 */
	@Override
	public void KillSwimmableThread() 
	{
		isThreadAlive = false;	
		suspended = true;
	}
	
	@Override
	public void drawCreature(Graphics g) 
	{
		this.drawAnimal(g);
	}
	
	@Override
	public void setColor(Color color) 
	{
		this.col = color;
	}

	@Override
	public void setSize(int size) 
	{
		this.size = size;
	}
	
	@Override
	public void setContainingPanel(AquaPanel panel) 
	{
		this.containingPanel = panel;
		x_front = containingPanel.getWidth()/2;
		y_front = containingPanel.getHeight()/2;
		hungerState = new Satiated(panel);
	}

	@Override
	public void PaintFish(Color color) 
	{
		this.col = color;
	}
	
	@Override
	public void set_X_Front(int value) 
	{
		this.x_front = value;
	}

	@Override
	public void set_Y_Front(int value) 
	{
		this.y_front = value;
	}
	
	@Override
	public void SaveState() 
	{
		this.memento = new SwimmableMemento(col, size, x_front, y_front, verSpeed, horSpeed);
	}
	
	@Override
	public void RestoreState() 
	{
		if(this.memento != null)
			this.memento.restoreState(this);
	}
	
	@Override
	public boolean HasSavedState() 
	{
		if(this.memento != null)
			return true;
		return false;
	}
	
	@Override
	public void SetEatFrequency(int freq) 
	{
		eatFrequency = freq;
		turnsUntilHunger = freq;
	}

	@Override
	public int GetEatFrequency() 
	{
		return eatFrequency;
	}
	
	@Override
	public int Get_X_Front() 
	{
		return x_front;
	}

	@Override
	public int Get_Y_Front() 
	{
		return y_front;
	}
	
	@Override
	public void Set_X_Direction(int dir) 
	{
		this.x_dir = dir;
	}

	@Override
	public void Set_Y_Direction(int dir) 
	{
		this.y_dir = dir;
	}

	@Override
	public int Get_X_Direction() 
	{
		return this.x_dir;
	}

	@Override
	public int Get_Y_Direction() 
	{
		return this.y_dir;
	}
	
	@Override
	public Object clone() 
	{
		Object clonedObject = new Jellyfish(col, size, horSpeed, verSpeed, containingPanel);
		((Jellyfish)clonedObject).SetEatFrequency(this.eatFrequency);
		return clonedObject;
	}
	
	/**
	 * Function that allows the Jellyfish to notify the Aquarium (AquaPanel) it's hungry which consequently
	 * asks the user to drop food to the aquarium and feed the swimmables.
	 */
	@Override
	public void notifyOfHunger() 
	{
		this.containingPanel.GetHungerNotification();
	}
}
