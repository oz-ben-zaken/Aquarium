/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * A class that implements the HungerState interface (State Design-Pattern) and represents a state of the 
 * swimmable being satiated.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class Satiated implements HungerState
{
	private AquaPanel containingPanel;
	
	/**
	 * Default constructor, initiating the AquaPanel private field.
	 * @param containingPanel - AquaPanel that contains the Swimmable
	 */
	public Satiated(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
	}
	
	/**
	 * Calls the SwimNormally function that increases the x_front and y_front according to the Swimmable speed
	 * and direction (x direction and y direction).
	 * @param swimmable - The Swimmable that holds the Satiated HungerState variable that requires an action to 
	 * be performed.
	 */
	@Override
	public void PerformAction(Swimmable swimmable) 
	{
		Satiated.SwimNormally(containingPanel, swimmable);
	}
	
	/**
	 * Handles the normal swimming of the Swimmable by increasing the x_front and y_front and changing direction
	 * of x and y if needed, according to the swimmable vertical and horizontal speed.
	 * The function is defined as static to allow the Hungry HungerState to use it when the Swimmable is Hungry
	 * and no food was thrown to the aquarium.
	 * @param containingPanel - the AquaPanel containing the Swimmable
	 * @param swimmable - The Swimmable that requires an action to be performed.
	 */
	public static void SwimNormally(AquaPanel containingPanel, Swimmable swimmable)
	{
		int x_front = swimmable.Get_X_Front(), y_front = swimmable.Get_Y_Front();
		int verSpeed = swimmable.getVerSpeed(), horSpeed = swimmable.getHorSpeed();
		int x_dir = swimmable.Get_X_Direction(), y_dir = swimmable.Get_Y_Direction();
		int size = swimmable.getSize();
		if (x_front < containingPanel.getWidth() && x_front > 0)
			x_front += verSpeed*x_dir;
		else 
		{
			x_front+= size*x_dir*(-1);
			x_dir = x_dir*(-1);
		}
		
		if (y_front < containingPanel.getHeight() - containingPanel.GetBottomPanelHeight() - size/5 && 
				y_front > size/7 + containingPanel.GetUpperMenuHeight())
			y_front += horSpeed*y_dir;
		else 
		{
			y_front+= horSpeed*y_dir*(-1);
			y_dir = y_dir*(-1);
		}		
		swimmable.set_X_Front(x_front);
		swimmable.set_Y_Front(y_front);
		swimmable.Set_X_Direction(x_dir);
		swimmable.Set_Y_Direction(y_dir);
		containingPanel.repaint();
	}

	
	@Override
	public String GetState() 
	{
		return "Satiated";
	}
}
