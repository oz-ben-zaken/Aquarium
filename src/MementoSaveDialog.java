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
import java.util.Iterator;
import javax.swing.*;


/**
 * Class that allows Swimmable and Immobile to save it's state with the relevant parameters. For Swimmable,
 * it saves size, coordinates and color and for Immobile it saves size and coordinates.
 * The class implements the Memento Design Pattern.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class MementoSaveDialog extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private JTable mementoTable;
	private JScrollPane mementoScrollPane;
	private JButton button;
	private JLabel label;
	private String type;
	private Swimmable[] swimmablesArr;
	private Immobile[] immobilesArr;
	
	/**
	 * Default constructor for initiating the JDialog components
	 * @param containingPanel - the AquaPanel that triggered the function
	 * @param type - String representation of the type, either Swimmable or Immobile
	 */
	public MementoSaveDialog(AquaPanel containingPanel, String type)
	{
		this.containingPanel = containingPanel;
		swimmablesArr = JPanelDecorator.ConstructArrayFromHashSet(containingPanel);
		immobilesArr = ConstructImmobileArrayFromHashSet(containingPanel);
		this.type = type;
		setSize(600,300);	
		setLayout(new BorderLayout());
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/8);
		this.setAlwaysOnTop(true);
		// ----------- JDialog Components ----------- //
		if(type.equalsIgnoreCase("Swimmable"))
		{
			label = new JLabel("Choose Animal To Save It's State:");
			button = new JButton("Save Animal State");
		}
		else
		{
			label = new JLabel("Choose Plant To Save It's State:");
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
	}
	
	/**
	 * Creates a JTable in order to show the user all available Immobiles or Swimmables, based on the choice.
	 * @param type - String representing the type, either Immobile or Swimmable
	 */
	public void CreateTable(String type)
	{
		if(type.equalsIgnoreCase("Swimmable"))
		{
			Object[][] data =  new Object[swimmablesArr.length][7];
			for(int i = 0; i < swimmablesArr.length; i++)
			{
				data[i][0] = swimmablesArr[i].getAnimalName();
				data[i][1] = swimmablesArr[i].getColor();
				data[i][2] = swimmablesArr[i].getSize();
				data[i][3] = swimmablesArr[i].getHorSpeed();
				data[i][4] = swimmablesArr[i].getVerSpeed();
				data[i][5] = swimmablesArr[i].getEatCount();
				data[i][6] = swimmablesArr[i].GetEatFrequency();
			}	
			this.mementoTable = new JTable(data, containingPanel.JTableColumnNames);
		}
		else
		{
			Object[][] data =  new Object[immobilesArr.length][5];
			for(int i = 0; i < immobilesArr.length; i++)
			{
				data[i][0] = immobilesArr[i].GetPlantName();
				data[i][1] = immobilesArr[i].GetSize();
				data[i][2] = immobilesArr[i].GetX();
				data[i][3] = immobilesArr[i].GetY();
				data[i][4] = "Green";
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
			JOptionPane optPane;		
			if(mementoTable.getSelectionModel().isSelectionEmpty() == true)
			{
				optPane = new JOptionPane("No row was selected, try again please.\n");
				JDialog endDialog = optPane.createDialog("");
				endDialog.setAlwaysOnTop(true);
				endDialog.setVisible(true);
				return;
			}
			if(this.type.equalsIgnoreCase("SeaPlant"))
			{
				int selectedIndex = mementoTable.getSelectionModel().getMinSelectionIndex();
				/*TableModel tableModel = mementoTable.getModel();
				String plantType = (String)(tableModel.getValueAt(selectedIndex, 0));
				int size = (int)(tableModel.getValueAt(selectedIndex, 1));
				int xLocation = (int)(tableModel.getValueAt(selectedIndex, 2));
				int yLocation = (int)(tableModel.getValueAt(selectedIndex, 3));*/			
				Immobile selectedImmobile = immobilesArr[selectedIndex];
				selectedImmobile.SaveState();
			}
			else // If type == "Swimmable"
			{
				int selectedIndex = mementoTable.getSelectionModel().getMinSelectionIndex();
				/*TableModel tableModel = mementoTable.getModel();
				String swimmableType = (String)(tableModel.getValueAt(selectedIndex, 0));
				String color = (String)(tableModel.getValueAt(selectedIndex, 1));
				int size = (int)(tableModel.getValueAt(selectedIndex, 2));
				int horSpeed = (int)(tableModel.getValueAt(selectedIndex, 3));
				int verSpeed = (int)(tableModel.getValueAt(selectedIndex, 4));*/				
				Swimmable selectedSwimmable = swimmablesArr[selectedIndex];
				selectedSwimmable.SaveState();
			}
		}
	}
	
	/**
	 * Creates Immobile array from the AquaPanel Swimmable HashSet in order to maintain order of iteration
	 * when the user selects certain index from the table.
	 * @param containingPanel - the AquaPanel containing the Swimmable HashSet
	 * @return Immobile[] - the newly created array
	 */
	public static Immobile[] ConstructImmobileArrayFromHashSet(AquaPanel containingPanel)
	{
		Immobile[] immobiles = new Immobile[containingPanel.GetImmobiles().size()];
		
		Iterator<Immobile> iterator = containingPanel.GetImmobiles().iterator(); 
		int i = 0;
		while(iterator.hasNext() == true)
		{
			immobiles[i] = iterator.next();
			i++;
		}
		
		return immobiles;
	}
}
