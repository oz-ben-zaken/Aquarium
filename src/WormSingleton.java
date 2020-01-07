/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.*;

public class WormSingleton 
{
	private static WormSingleton instance = null;
	private AquaFrame containingPanel;
	/* A private Constructor prevents any other * class from instantiating.*/
	private WormSingleton(AquaFrame containingPanel)
	{ 
		this.containingPanel = containingPanel;
	} 
	/* Static 'instance' method */ 
	public static WormSingleton getInstance(AquaFrame containingPanel)
	{ 
		if(instance == null) 
			instance = new WormSingleton(containingPanel); 
		return instance; 
	}
	
	public void DrawWorm(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Ariel", Font.BOLD,30));
		g.drawString("S",(containingPanel.getWidth()/2),(containingPanel.getHeight()/2));
	}
}
	