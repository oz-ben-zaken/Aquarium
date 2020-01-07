/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

/**
 * Class that implements the HungerState interface (State Design Pattern) and represents and Hungry state of
 * a Swimmable.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class Hungry implements HungerState
{
	private AquaPanel containingPanel;
	
	/**
	 * Default constructor, initiating the AquaPanel private field.
	 * @param containingPanel - AquaPanel that contains the Swimmable
	 */
	public Hungry(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
	}
	
	/**
	 * Checks in the containingPanel if any food was thrown to the aqurium and redirects the swimmable towards 
	 * the middle of the aquarium by calculating the new speed. Otherwise, calls the Satiated SwimNormally
	 * in order to continue move normally without chasing the food.
	 * @param swimmable - The Swimmable that holds the Hungry HungerState variable that requires an action to 
	 * be performed.
	 */
	@Override
	public void PerformAction(Swimmable swimmable) 
	{
		if(containingPanel.WasFoodThrown() == true)
		{
			//this.containingPanel.repaint();
			int old_xFront = swimmable.Get_X_Front(), old_yFront = swimmable.Get_Y_Front();
			double k = Math.abs((double)(old_yFront - containingPanel.getHeight()/2)
					/ (double)(old_xFront - containingPanel.getWidth()/2));
			int oldVerSpeed = swimmable.getVerSpeed(), oldHorSpeed = swimmable.getHorSpeed();
			double old_speed = (double)Math.sqrt((double)oldVerSpeed * oldVerSpeed + 
					(double)oldHorSpeed * oldHorSpeed);
			
			double horSpeed_new = (double)(old_speed / Math.sqrt(k * k + 1));
			double verSpeed_new = (double)horSpeed_new * k;
			
			if(horSpeed_new > 10) // Make sure the new horizontal speed doesn't exceeds the max speed (10)
				horSpeed_new = 10;
			else if(horSpeed_new < 1) // If the new speed is between 0 to 1 (0<=new<=1)
			{
				if(old_xFront != containingPanel.getWidth()) // If the fish hasn't reaches the X line of the worm
					horSpeed_new = 1; // That's the case of the speed being 0.x, for example 0.81
				else
					horSpeed_new = 0; // Continue only with vertical speed
			}
			if(verSpeed_new > 10) // Make sure the new horizontal speed doesn't exceeds the max speed (10)
				verSpeed_new = 10;
			else if(verSpeed_new < 1) // If the new speed is between 0 to 1 (0<=new<=1)
			{
				if(old_xFront != containingPanel.getHeight()) // If the fish hasn't reaches the Y line of the worm
					verSpeed_new = 1; // That's the case of the speed being 0.y, for example 0.81
				else
					verSpeed_new = 0; // Continue only with horizontal speed
			}
			
			/*try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
				e.toString();
			}*/
			if(old_xFront>containingPanel.getWidth()/2)
				swimmable.Set_X_Direction(-1);
			else
				swimmable.Set_X_Direction(1);
			if(old_yFront>containingPanel.getHeight()/2)
				swimmable.Set_Y_Direction(-1);
			else
				swimmable.Set_Y_Direction(1);
			swimmable.set_X_Front(old_xFront + (int)(horSpeed_new*swimmable.Get_X_Direction()));
			swimmable.set_Y_Front(old_yFront + (int)(verSpeed_new*swimmable.Get_Y_Direction()));
			if((Math.abs(swimmable.Get_X_Front()-containingPanel.getWidth()/2) <= 5) && 
					(Math.abs(swimmable.Get_Y_Front()-containingPanel.getHeight()/2) <= 5))
			{
				containingPanel.EatWorm();
				swimmable.eatInc();			
			}			
		}
		else
			Satiated.SwimNormally(containingPanel, swimmable);
	}
	
	/**
	 * Returns the Hunger State of the Swimmable
	 * @return String - the HungerState represented as a string
	 */
	@Override
	public String GetState() 
	{
		return "Hungry";
	}
}
