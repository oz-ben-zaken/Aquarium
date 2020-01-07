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
import javax.swing.*;


/**
 * Class that allows Swimmable and Immobile to restore it's state to the previously saved state.
 * The class implements the Memento Design Pattern.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class MementoRestoreDialog extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private String type;

	private static Hashtable<Integer, Swimmable> swimmableMap;
	private static Hashtable<Integer, Immobile> immobileMap;
	private static Hashtable<Integer, Swimmable> swimmableSavedStateMap;
	private static Hashtable<Integer, Immobile> immobileSavedStateMap;
	
	private JTable mementoTable;
	private JScrollPane mementoScrollPane;
	private JButton button;
	private JLabel label;
	
	/**
	 * Default constructor for initiating the JDialog components
	 * @param containingPanel - the AquaPanel that triggered the function
	 * @param type - String representation of the type, either Swimmable or Immobile
	 */
	public MementoRestoreDialog(AquaPanel containingPanel, String type)
	{
		this.containingPanel = containingPanel;
		this.type = type;
		swimmableMap = CreateSwimmableHashTable();
		immobileMap = CreateImmobileHashTable();
		swimmableSavedStateMap = null;
		immobileSavedStateMap = null;
		
		setSize(600,300);	
		setLayout(new BorderLayout());
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/8);
		this.setAlwaysOnTop(true);
		
		// ----------- JDialog Components ----------- //
		if(type.equalsIgnoreCase("Swimmable"))
		{
			label = new JLabel("Choose Animal To Restore It's State:");
			button = new JButton("Restore Animal State");
		}
		else
		{
			label = new JLabel("Choose Plant To Restore It's State:");
			button = new JButton("Save Plant State");
		}
		CreateTable(type);	
		add(label,BorderLayout.NORTH);
		add(mementoScrollPane, BorderLayout.CENTER);	
		button.addActionListener(this);
		add(button, BorderLayout.SOUTH);
		
		// ----------- JDialog Visibility && Closing ----------- //
		setVisible(true);	
		addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				dispose();		
			}
		});
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Creates Swimmable HashTable from the AquaPanel's HashSet in order to maintain order of iteration over
	 * the Swimmables when selecting index from the JTable.
	 * @return HashTable<Integer, Swimmable> - the newly creates HashTable 
	 */
	public Hashtable<Integer, Swimmable> CreateSwimmableHashTable()
	{
		Hashtable<Integer, Swimmable> hashTable = new Hashtable<Integer, Swimmable>();
		HashSet<Swimmable> swimmables = containingPanel.GetSwimmables();
		int i = 0;
		for(Swimmable swimmable:swimmables)
		{
			hashTable.put(i, swimmable);
			i++;
		}		
		return hashTable;
	}
	
	/**
	 * Creates Immobile HashTable from the AquaPanel's HashSet in order to maintain order of iteration over
	 * the Immobiles when selecting index from the JTable.
	 * @return HashTable<Integer, Immobile> - the newly creates HashTable 
	 */
	public Hashtable<Integer, Immobile> CreateImmobileHashTable()
	{
		Hashtable<Integer, Immobile> hashTable = new Hashtable<Integer, Immobile>();
		HashSet<Immobile> immobiles = containingPanel.GetImmobiles();
		int i = 0;
		for(Immobile immobile:immobiles)
		{
			hashTable.put(i, immobile);
			i++;
		}		
		return hashTable;
	}
	
	/**
	 * Creates a JTable in order to show the user all available Immobiles or Swimmables, based on the choice.
	 * @param type - String representing the type, either Immobile or Swimmable
	 */
	public void CreateTable(String type)
	{
		if(type.equalsIgnoreCase("Swimmable"))
		{
			swimmableSavedStateMap = new Hashtable<Integer, Swimmable>();
			int j = 0;
			for(int i = 0; i < swimmableMap.size(); i++)
			{
				Swimmable swimmable = swimmableMap.get(i);
				if(swimmable.HasSavedState() == true)
				{	
					swimmableSavedStateMap.put(j, swimmable);
					j++;
				}
			}
			Object[][] data =  new Object[swimmableSavedStateMap.size()][7];
			j = 0;
			for(int i = 0; i < swimmableSavedStateMap.size(); i++)
			{
				Swimmable swimmable = swimmableSavedStateMap.get(i);
				if(swimmable.HasSavedState() == true)
				{
					data[j][0] = swimmable.getAnimalName();
					data[j][1] = swimmable.getColor();
					data[j][2] = swimmable.getSize();
					data[j][3] = swimmable.getHorSpeed();
					data[j][4] = swimmable.getVerSpeed();
					data[j][5] = swimmable.getEatCount();
					data[j][6] = swimmable.GetEatFrequency();
					j++;
				}
			}	
			this.mementoTable = new JTable(data, containingPanel.JTableColumnNames);		
		}
		else
		{
			immobileSavedStateMap = new Hashtable<Integer, Immobile>();
			int j = 0;
			for(int i = 0; i < immobileMap.size(); i++)
			{
				Immobile immobile = immobileMap.get(i);
				if(immobile.HasSavedState() == true)
				{
					immobileSavedStateMap.put(j, immobile);
					j++;
				}
			}
			Object[][] data =  new Object[immobileSavedStateMap.size()][5];
			j = 0;
			for(int i = 0; i < immobileSavedStateMap.size(); i++)
			{
				Immobile immobile = immobileSavedStateMap.get(i);
				if(immobile.HasSavedState() == true)
				{
					data[j][0] = immobile.GetPlantName();
					data[j][1] = immobile.GetSize();
					data[j][2] = immobile.GetX();
					data[j][3] = immobile.GetY();
					data[j][4] = "Green";
					j++;
				}
			}	
			String []columnNames = {"Plant Type","Size", "X","Y", "Color"};
			this.mementoTable = new JTable(data, columnNames);
		}
		
		this.mementoScrollPane = new JScrollPane(mementoTable);
		this.mementoTable.setFillsViewportHeight(true);
		this.mementoScrollPane.setFocusable(true);
		this.add(mementoScrollPane, BorderLayout.CENTER);			
		this.revalidate();
		this.repaint();				
	}
	
	/**
	 * Overrides the ActionListener function and handles the actions that needs to performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JOptionPane optPane;
		String source = null;
		try
		{
			source = ((JButton)e.getSource()).getText();
		}
		catch(Exception err)
		{
			err.printStackTrace();
			return;
		}
		
		if(source == button.getText())
		{
			if(mementoTable.getSelectionModel().isSelectionEmpty() == true)
			{
				optPane = new JOptionPane("No row was selected, try again please.\n");
				JDialog endDialog = optPane.createDialog("");
				endDialog.setAlwaysOnTop(true);
				endDialog.setVisible(true);
				return;
			}
			int selectedIndex = mementoTable.getSelectionModel().getMinSelectionIndex();
			if(this.type.equalsIgnoreCase("Swimmable"))
			{
				swimmableSavedStateMap.get(selectedIndex).RestoreState();
			}
			else // If type == "SeaPlant"
			{
				immobileSavedStateMap.get(selectedIndex).RestoreState();
			}
		}		
	}
}
