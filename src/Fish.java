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


//import sun.reflec
/**
 * A class that represents a Fish and extends the abstract class Swimmable
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class Fish extends Swimmable
{
	private Color col;
	private int size, x_front, y_front, x_dir, y_dir, eatCount;
	private boolean isFishDrawn, suspended, isThreadAlive;
	private AquaPanel containingPanel;
	private CyclicBarrier foodCyclicBarrier;
	private SwimmableMemento memento;
	int eatFrequency;
	HungerState hungerState;
	int turnsUntilHunger;
	
	/**
	 * Default Fish constructor, sets all variables to their default values
	 */
	public Fish() 
	{
		super(); // Calls the default constructor of Swimmable
		col = null;
		size = 0;
		x_front = 0;
		y_front = 0;
		x_dir = 1;
		y_dir = 1;
		eatCount = 0;
		isFishDrawn = false;
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
	 * @param _col - Color variable representing the color of the fish
	 * @param _size - int variable representing the size of the fish
	 * @param horSpeed - int variable representing the horizontal speed of the fish
	 * @param verSpeed- int variable representing the vertical speed of the fish
	 * @param containingPanel - AquaPanel representing the JPanel that contains the fish and handles it movement
	 * and drawing.
	 */
	public Fish(Color _col, int _size, int horSpeed, int verSpeed,
			AquaPanel containingPanel) // Custom Constructor
	{
		super(horSpeed, verSpeed); // Calls the default constructor of Swimmable
		col = _col;
		size = _size;	
		x_front = containingPanel.getWidth()/2;
		y_front = containingPanel.getHeight()/2;
		x_dir = 1;
		y_dir = 1;
		eatCount = 0;
		isFishDrawn = false;
		suspended = false;
		this.containingPanel = containingPanel;
		isThreadAlive = true;
		foodCyclicBarrier = null;
		memento = null;
		hungerState = new Satiated(containingPanel);
	}

	/**
	 * Returns the name of the Fish ("fish")
	 * @return String - the name of the Fish 
	 */
	@Override
	public String getAnimalName() 
	{
		return new String("Fish");
	}

	/**
	 * Receives a Graphics object and paints in it the fish.
	 * @param g - Graphics object on which the fish is drawn.
	 */
	@Override
	public void drawAnimal(Graphics g) 
	{
		if (this.isFishDrawn == false) 
		{
			g.setColor(col);
			if (x_dir == 1) // fish swims to right side
			{
				// Body of fish
				g.fillOval(x_front - size, y_front - size / 4, size, size / 2);

				// Tail of fish
				int[] x_t = { x_front - size - size / 4,
						x_front - size - size / 4, x_front - size };
				int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
				Polygon t = new Polygon(x_t, y_t, 3);
				g.fillPolygon(t);

				// Eye of fish
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue()));
				g2.fillOval(x_front - size / 5, y_front - size / 10, size / 10,
						size / 10);

				// Mouth of fish
				if (size > 70)
					g2.setStroke(new BasicStroke(3));
				else if (size > 30)
					g2.setStroke(new BasicStroke(2));
				else
					g2.setStroke(new BasicStroke(1));
				g2.drawLine(x_front, y_front, x_front - size / 10, y_front
						+ size / 10);
				g2.setStroke(new BasicStroke(1));
			} 
			else // fish swims to left side
			{
				// Body of fish
				g.fillOval(x_front, y_front - size / 4, size, size / 2);

				// Tail of fish
				int[] x_t = { x_front + size + size / 4,
						x_front + size + size / 4, x_front + size };
				int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
				Polygon t = new Polygon(x_t, y_t, 3);
				g.fillPolygon(t);

				// Eye of fish
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue()));
				g2.fillOval(x_front + size / 10, y_front - size / 10,
						size / 10, size / 10);

				// Mouth of fish
				if (size > 70)
					g2.setStroke(new BasicStroke(3));
				else if (size > 30)
					g2.setStroke(new BasicStroke(2));
				else
					g2.setStroke(new BasicStroke(1));
				g2.drawLine(x_front, y_front, x_front + size / 10, y_front
						+ size / 10);
				g2.setStroke(new BasicStroke(1));
			}
		}
	}

	/**
	 * Sets the fish in a suspended mode, stopping it from swimming.
	 */
	@Override
	public void setSuspend() 
	{
		this.suspended = true;
	}
	
	/**
	 * Sets the fish in a resumed mode, allowing it to continue swimming.
	 */
	@Override
	public synchronized void setResume() 
	{
		this.suspended = false;
		notify();
	}
	
	/**
	 * Sets a CyclicBarrier for the fish, which stops all other Swimmables when food was thrown until the last 
	 * swimmable called await() method and only then all Swimmables starts swimming towards the good 
	 * simultaneously along with the current fish.
	 * @param b
	 */
	@Override
	public void setBarrier(CyclicBarrier b) 
	{
		foodCyclicBarrier = b;
		
	}
	/**
	 * Returns the size of the fish.
	 * @return int - the size
	 */
	@Override
	public int getSize() 
	{
		return size;
	}

	/**
	 * Increases the eat-counter of the fish, which means the fish successfully ate food.
	 */
	@Override
	public void eatInc() 
	{
		eatCount++;
		this.hungerState = new Satiated(containingPanel);
		this.turnsUntilHunger = this.eatFrequency;
	}

	/**
	 * Returns the eat-counter, the number of times the fish successfully ate food.
	 * @return int - the counter
	 */
	@Override
	public int getEatCount() 
	{
		return eatCount;
	}

	/**
	 * String representation of the fish's color
	 * @return String - the color of the fish
	 */
	@Override
	public String getColor() 
	{
		return ("(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")");
	}

	/**
	 * The run method for running the fish (Swimmable) in it's own thread. The thread runs as long as the
	 * isThreadAlive variable is true, which is set to false only if the program finishes or if the aquarium 
	 * was reset. The method handles the fish movement within the boundaries of it's containing panel. It also
	 * checks if the fish was set to sleep, waken up, or if food was thrown and then it directs the fish
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
						this.notifyOfHunger(); // Notifies the observer (AquaPanel) the fish needs to eat
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

	/**
	 * Overrides the SeaCreature drawCreature function that handles the drawing of the creature
	 */
	@Override
	public void drawCreature(Graphics g) 
	{
		this.drawAnimal(g);
	}

	/**
	 * 
	 */
	@Override
	public void setColor(Color color) 
	{
		this.col = color;
	}

	/**
	 * 
	 */
	@Override
	public void setSize(int size) 
	{
		this.size = size;
	}

	/**
	 * 
	 */
	@Override
	public void setContainingPanel(AquaPanel panel) 
	{
		this.containingPanel = panel;
		x_front = containingPanel.getWidth()/2;
		y_front = containingPanel.getHeight()/2;
		hungerState = new Satiated(panel);
	}

	/**
	 * 
	 */
	@Override
	public void PaintFish(Color color) 
	{
		this.col = color;
	}

	/**
	 * 
	 */
	@Override
	public void set_X_Front(int value) 
	{
		this.x_front = value;
	}

	/**
	 * 
	 */
	@Override
	public void set_Y_Front(int value) 
	{
		this.y_front = value;
	}

	/**
	 * 
	 */
	@Override
	public void SaveState() 
	{
		this.memento = new SwimmableMemento(col, size, x_front, y_front, verSpeed, horSpeed);
	}

	/**
	 * 
	 */
	@Override
	public void RestoreState() 
	{
		if(this.memento != null)
			this.memento.restoreState(this);
	}

	/**
	 * 
	 */
	@Override
	public boolean HasSavedState() 
	{
		if(this.memento != null)
			return true;
		return false;
	}

	/**
	 * 
	 */
	@Override
	public void SetEatFrequency(int freq) 
	{
		eatFrequency = freq;
		turnsUntilHunger = freq;
	}

	/**
	 * 
	 */
	@Override
	public int GetEatFrequency() 
	{
		return eatFrequency;
	}

	/**
	 * 
	 */
	@Override
	public int Get_X_Front() 
	{
		return x_front;
	}

	/**
	 * 
	 */
	@Override
	public int Get_Y_Front() 
	{
		return y_front;
	}

	/**
	 * 
	 */
	@Override
	public void Set_X_Direction(int dir) 
	{
		this.x_dir = dir;
	}

	/**
	 * 
	 */
	@Override
	public void Set_Y_Direction(int dir) 
	{
		this.y_dir = dir;
	}

	/**
	 * 
	 */
	@Override
	public int Get_X_Direction() 
	{
		return this.x_dir;
	}

	/**
	 * 
	 */
	@Override
	public int Get_Y_Direction() 
	{
		return this.y_dir;
	}

	/**
	 * 
	 */
	@Override
	public Object clone() 
	{
		Object clonedObject = new Fish(col, size, horSpeed, verSpeed, containingPanel);
		((Fish)clonedObject).SetEatFrequency(this.eatFrequency);
		return clonedObject;
	}

	/**
	 * Function that allows a Swimmable to notify the Aquarium (AquaPanel) it's hungry which consequently
	 * asks the user to drop food to the aquarium and feed the swimmables.
	 */
	@Override
	public void notifyOfHunger() 
	{
		this.containingPanel.GetHungerNotification();
	}
}