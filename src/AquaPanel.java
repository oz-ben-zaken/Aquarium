/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CyclicBarrier;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * A class that represents an aquarium-panel, extending the built-in JPanel and shows the aquarium, including
 * the animals it contains and a bottom panel for controlling the aquarium (add animal, reset etc). 
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */

public class AquaPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel bottomPanel; 
	private HashSet<Swimmable> swimmables;
	private HashSet<Immobile> immobiles;
	private JButton[] buttonsArr;
	private AquaFrame containingPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private int tableClicksCounter;
	private Boolean foodThrown;
	private Image backgroundImage;
	//private JLabel imageContentPane;
	private WormSingleton worm;
	
	final String[] buttonsNames = {"Add Animal","Add Plant", "Sleep", "Wake up"
			, "Reset","Food", "Info", "Duplicate Animal", "Decorator", "Exit"};
	public final String[] JTableColumnNames = {"Animal", "Color", "Size", "HorSpeed",
            "VerSpeed", "Eat Counter", "Eat Frequency"};
	public final static String[] colorsArr = {"Color", "Green", "Blue", "Red", "Yellow","Black"};
	
	/**
	 * Custom Constructor - receives the AquaFrame that contains the panel and a windowListener for the "exit" option
	 * presented in the panel and creates a new panel with a surface for drawing the aquarium background and animals
	 * (Swimmables) and a bottom-panel with buttons for controlling the aquarium. 
	 * @param windowListener - WindowListener for closing the progress appropriately
	 * @param containingPanel - The AquaFrame that contains the panel
	 */
	public AquaPanel(WindowListener windowListener, AquaFrame containingPanel) // Default Constructor
	{
		setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout());	
		setBackground(Color.WHITE);
		swimmables = new HashSet<Swimmable>();
		immobiles = new HashSet<Immobile>();
		this.containingPanel = containingPanel;
		tableClicksCounter = 0;
		foodThrown = false;
		backgroundImage = null;
		//imageContentPane = new JLabel();
		worm = WormSingleton.getInstance(containingPanel);
		// ------------------------ JButtons Declarations & ActionListeners  ------------------------ //
		this.buttonsArr = new JButton[buttonsNames.length];
		for(int i = 0; i < buttonsArr.length; i++)
		{
			this.buttonsArr[i] = new JButton(this.buttonsNames[i]);
			this.buttonsArr[i].addActionListener(this);
		}
		this.buttonsArr[9].addActionListener(exit_button_ActionListener(windowListener)); // Add custom listener to Exit

		// ------------------------ Add Buttons To The BottomPanel ------------------------ //
		for(int i = 0; i < this.buttonsArr.length; i++)
			bottomPanel.add(this.buttonsArr[i]);
		add(bottomPanel,BorderLayout.SOUTH);			
	}
	
	/**
	 * Overrides the ActionListener method and handles the actions that needs to be performed when clicking on
	 * buttons and clickable components.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String source = null;
		try
		{
			source = ((JButton)e.getSource()).getText();
		}
		catch(Exception err){}
		if(source == null)
			return;
		if(source == this.buttonsNames[0]) // Add new animal option
		{
			if(swimmables.size() < 5)
			{
				AddAnimalDialog animalDialog = new AddAnimalDialog(this);
				animalDialog.setAlwaysOnTop(true);
				//repaint();
			}
			else
				JOptionPane.showMessageDialog(null, "You may not add more than 5 swimmables.\n"
						+ "If you wish to add more, remove an existing swimmable.");
		}
		else if(source == this.buttonsNames[1]) // Add new plant option
		{
			if(immobiles.size() < 5)
			{
				AddPlantDialog plantDialog = new AddPlantDialog(this);
				plantDialog.setAlwaysOnTop(true);
			}
			else
				JOptionPane.showMessageDialog(null, "You may not add more than 5 immobiles.\n"
						+ "If you wish to add more, remove an existing immobile.");
			
		}
		else if(source == this.buttonsNames[2]) // Suspend option
		{
			for(Swimmable swimmable:this.swimmables)
				swimmable.setSuspend();
		}
		else if(source == this.buttonsNames[3]) // Wake-up option
		{
			for(Swimmable swimmable:this.swimmables)
				swimmable.setResume();
		}
		else if(source == this.buttonsNames[4]) // Reset option
		{
			for(Swimmable swimmable:this.swimmables) 
			{	
				swimmable.KillSwimmableThread(); // Kills each of the swimmable threads
				swimmable.interrupt();
				swimmable = null;
			}
			swimmables.clear();
			for(@SuppressWarnings("unused") Immobile immobile:this.immobiles)
				immobile = null;
			immobiles.clear(); 
			this.repaint();
			JOptionPane.showMessageDialog(null, "Your aquarium has been cleared!");
		}
		else if(source == this.buttonsNames[5]) // Throw food option
		{
			if(swimmables != null && swimmables.size() > 0)
			{				
				CyclicBarrier cyclicBarrier = new CyclicBarrier(swimmables.size()); // Initialize the CyclicBarrier
				for(Swimmable swimmable:swimmables)
				{
					swimmable.setBarrier(cyclicBarrier); // Sets each thread's barrier
				}	
				foodThrown = true;
			}
		}
		else if(source == this.buttonsNames[6]) // Info option
		{
			this.tableClicksCounter++;
			if(this.tableClicksCounter%2 != 0)
			{					
				Object[][] data =  new Object[this.swimmables.size()+1][7]; // Plus 1 for the "Total"		
				int i =0, totalEatCounter = 0;
				for(Swimmable swimmable:swimmables)
				{
					if(i < data.length)
					{
						data[i][0] = swimmable.getAnimalName();
						data[i][1] = swimmable.getColor();
						data[i][2] = swimmable.getSize();
						data[i][3] = swimmable.getHorSpeed();
						data[i][4] = swimmable.getVerSpeed();
						data[i][5] = swimmable.getEatCount();
						data[i][6] = swimmable.GetEatFrequency();
						totalEatCounter+= swimmable.getEatCount();
						i++;
					}
				}	
				data[this.swimmables.size()][0] = "Total";
				data[this.swimmables.size()][5] = totalEatCounter;
				this.table = new JTable(data, JTableColumnNames);
				
				this.scrollPane = new JScrollPane(table);
				this.table.setFillsViewportHeight(true);
				this.scrollPane.setFocusable(true);
				this.add(scrollPane, BorderLayout.CENTER);	
				
				// --------------------------
				table.setOpaque(false);
				((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
				scrollPane.setOpaque(false);
				scrollPane.getViewport().setOpaque(false);
				// --------------------------
				
				this.revalidate();
				this.repaint();
			}
			else
			{
				this.scrollPane.setVisible(false);
				this.scrollPane = null;
			}
		}
		
		else if(source == this.buttonsNames[7]) // Duplicate Animal
		{
			if(swimmables.size() < 5)
			{
				DuplicateAnimalDialog duplicateDialog = new DuplicateAnimalDialog(this);
				duplicateDialog.setAlwaysOnTop(true);
			}
			else
				JOptionPane.showMessageDialog(null, "You may not add more than 5 swimmables.\n"
						+ "If you wish to duplicate, remove an existing swimmable.");
		}
		
		else if(source == this.buttonsNames[8]) // JPanelDecorator
		{
			JFrame container = new JFrame();
			container.setLayout(new BorderLayout());
			container.setSize(600,300);
			JPanelDecorator decoratorPanel = new JPanelDecorator(this);
			container.add(decoratorPanel, BorderLayout.CENTER);
			container.setLocationRelativeTo(this);
			container.setAlwaysOnTop(true);
			container.setVisible(true);
		}
	}
	
	/**
	 * Function that handles the "exit" option and returns a WindowListener that allows closing the program.
	 * @param windowListener - a WindowListener for closing the program via the AquaFrame
	 * @return ActionListener - the action that needs to be performed when clicking "exit"
	 */
	private ActionListener exit_button_ActionListener(WindowListener windowListener)
	{
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				windowListener.windowClosing(null);
			}
		};
		return listener;
	}
		
	/**
	 * Overrides the JComponent method and handles the drawing of the Swimmables within the aquarium, drawing of
	 * the thrown food (Draws a worm if food was thrown) and a background image if the user chose one.
	 */
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		if(backgroundImage != null)
		{	
			if(scrollPane != null)
			{	
				scrollPane.getGraphics().drawImage(backgroundImage, 0, 0, getWidth(), getHeight() - bottomPanel.getHeight(), this);
			}		
			else
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight() - bottomPanel.getHeight(), this);
		}
		for(Immobile i:this.immobiles)
			i.drawCreature(g);
		for(Swimmable s:this.swimmables)
			if(s != null)
				s.drawAnimal(g);
		if(foodThrown == true)
			worm.DrawWorm(g);
		if(this.scrollPane != null)
			this.scrollPane.setVisible(true);
	}
	
	/**
	 * Function that allows the AddAnimalDialog to add a new Swimmable to the hashset as long as there are less
	 * than 5 Swimmables in the aquarium. 
	 * @param s - the newly created Swimmable that needs to be added.
	 */
	public void AddNewAnimal(Swimmable s)
	{
		if(this.swimmables.size() <= 5)
			this.swimmables.add(s);
		else
			JOptionPane.showMessageDialog(null, "You may not add more than 5 swimmables.\n"
					+ "If you wish to add more, remove an existing swimmable.");
	}
	
	/**
	 * Function that allows the AddPlantDialog to add a new Immobile to the hashset as long as there are less
	 * than 5 Immobiles in the aquarium. 
	 * @param newPlant - the newly created Immobile that needs to be added.
	 */
	public void AddNewPlant(Immobile newPlant)
	{
		if(this.immobiles.size() <= 5)
			this.immobiles.add(newPlant);
		else
			JOptionPane.showMessageDialog(null, "You may not add more than 5 immobiles.\n"
					+ "If you wish to add more, remove an existing immobile.");
	}
	
	
	/**
	 * 
	 */
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		if(this.swimmables != null)
		{
			for(Swimmable swimmable:this.swimmables)
				if(swimmable != null)
					swimmable.interrupt();	
		}
	}
	
	/**
	 * Function that allows classes that inherits from Swimmable (like Fish and JellyFish) to check if any food
	 * was thrown into the aquarium so the Swimmable could move towards the food inorder to eat it.
	 * @return - Boolean, either true or false, depends on the outcome
	 */
	public Boolean WasFoodThrown()
	{
		return this.foodThrown;
	}
	
	/**
	 * Function that allows classes that inherits from Swimmable (like Fish and JellyFish) to inform the panel 
	 * once a Swimmable successfully reached 5 pixels away from the worm and ate it.  
	 */
	public void EatWorm()
	{
		this.foodThrown = false;
		for(Swimmable s:this.swimmables)
			s.setBarrier(null);
	}
	
	/**
	 * Returns the height of the bottom panel. Used for defining the borders in which a Swimmable may move.
	 * @return int- the height of the panel 
	 */
	public int GetBottomPanelHeight()
	{
		return this.bottomPanel.getHeight();
	}
	
	/**
	 * Returns the height of the upper menu by retrieving it from the Aquaframe. Used for defining the upper border
	 * for the Swimmables.
	 * @return int - the height of the upper-menu.
	 */
	public int GetUpperMenuHeight()
	{
		return containingPanel.GetUpperMenuHeight(); // Calls the AquaFrame's function to get the height
	}
	
	/**
	 * Allows to place a background image at the panel of the aquarium.
	 * @param image - the Image object that needs to be set as a background-image.
	 */
	public void SetBackgroundImage(Image image)
	{
		this.backgroundImage = image;
		repaint();
		this.bottomPanel.setVisible(true);
		
		/*JLabel background = new JLabel(new ImageIcon(image));
        background.setLayout(new BorderLayout());
        this.imageContentPane.add(background);
        this.add(imageContentPane);*/
	}
	
	/**
	 * Removes the background image from the panel.
	 */
	public void RemoveBackgroundImage()
	{
		this.backgroundImage = null;
	}
	
	/**
	 * Returns the HashSet containing the Aquarium Swimmables.
	 * @return HashSet<Swimmable> - the HashSet containing the aquarium swimmables
	 */
	public HashSet<Swimmable> GetSwimmables()
	{
		return this.swimmables;
	}
	
	/**
	 * Returns the HashSet containing the Aquarium Immobiles.
	 * @return HashSet<Immobile> - the HashSet containing the aquarium Immobiles
	 */
	public HashSet<Immobile> GetImmobiles()
	{
		return this.immobiles;
	}
	
	/**
	 * A function for Observer Design Pattern for getting a notification from an aquarium's swimmable
	 * about it's hunger, which asks the user to throw food and feed the swimmables.
	 */
	public void GetHungerNotification()
	{
		if(this.foodThrown == true)
	    	return;
		Object[] options = {"OK"};
		// Show the message to the user in a thread in order to avoid stopping the movement of the Aquarium swimmables
		Thread dialogThread = new Thread(new Runnable()
		{
			public void run()
			{
			    JOptionPane.showOptionDialog(null,
			    		"One of your swimmables is hungry, feed him!","Hunger Notification",
		                   JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
		});
		dialogThread.start();    
	}
}