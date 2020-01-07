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
 * Class that implements the Decorator Design Pattern. It allows changing of the SeaCreature's color by using
 * the MarineAnimalDecorator.
 * @author Mai Ben Ami
 * @author Oz Ben Zaken
 */
public class JPanelDecorator extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private JTable table;
	private JScrollPane scrollPane;	
	private Swimmable[] swimmablesArr;
	
	/**
	 * Default constructur that initiates the JPanel component and sets the visibility and behavior.
	 * @param containingPanel - The AquaPanel that initiated the JPanel
	 */
	public JPanelDecorator(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
		this.swimmablesArr = ConstructArrayFromHashSet(containingPanel);
		setSize(600,300);	
		setLayout(new BorderLayout());
		//this.setLocationRelativeTo(containingPanel);
		//this.setAlwaysOnTop(true);
		// ----------- Dialog Components ----------- //		
		JLabel choose_Label = new JLabel("Choose animal to change it's color:");
		JButton duplicate_button = new JButton("Change Selected Animal Color");
		duplicate_button.addActionListener(this);
		CreateTable();
		
		// ----------- Add Components To Dialog ----------- //
		add(choose_Label, BorderLayout.NORTH);
		add(duplicate_button, BorderLayout.SOUTH);
		add(this.scrollPane, BorderLayout.CENTER);
		
		// ----------- JDialog Visibility && Closing ----------- //
		setVisible(true);	
		/*addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				dispose();		
			}
		});*/
	}
	
	/**
	 * Overrides the actionPerformed method of ActionListener and defines the actions that needs to be performed
	 * for the JPanel components.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		JOptionPane optPane;
		if(table.getSelectionModel().isSelectionEmpty() == true)
		{
			optPane = new JOptionPane("No row was selected, try again please.\n");
			JDialog endDialog = optPane.createDialog("");
			endDialog.setAlwaysOnTop(true);
			endDialog.setVisible(true);
			return;
		}
		int selectedIndex = table.getSelectionModel().getMinSelectionIndex();	
		ChangeColorDialog(selectedIndex);
		//this.r
	}
	
	/**
	 * Creates the JTable for showing the user all available Swimmables from which he can choose one to change
	 * it's color.
	 */
	public void CreateTable()
	{
		Object[][] data =  new Object[containingPanel.GetSwimmables().size()][7];
		
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

		this.table = new JTable(data, containingPanel.JTableColumnNames);
		
		this.scrollPane = new JScrollPane(table);
		this.table.setFillsViewportHeight(true);
		this.scrollPane.setFocusable(true);
		//this.add(scrollPane, BorderLayout.NORTH);			
		//this.revalidate();
		//this.repaint();				
	}
	
	/**
	 * Creates a JColorChooser dialog and applies the selected color to the Swimmable by using the 
	 * MarineAnimalDecorator.
	 * @param selectedIndex - the index from the JTable of the selected Swimmable.
	 */
	public void ChangeColorDialog(int selectedIndex)
	{
		Color swimmableColor = JColorChooser.showDialog(this,"Choose Swimmable Color", Color.white);
		if(swimmableColor != null)
		{
			MarineAnimalDecorator decorator = new MarineAnimalDecorator(swimmablesArr[selectedIndex]);
			decorator.PaintFish(swimmableColor);
			JOptionPane optPane;	
			optPane = new JOptionPane("The new color has been applied successfully.\n");
			JDialog endDialog = optPane.createDialog("");
			endDialog.setAlwaysOnTop(true);
			endDialog.setVisible(true);
			/*for(int i = 0; i < swimmablesArr.length; i++)
			{
				if(i == selectedIndex)
				{
					MarineAnimalDecorator decorator = new MarineAnimalDecorator(swimmablesArr[i]);
					decorator.PaintFish(swimmableColor);
					//swimmablesArr[i].setColor(color);
					JOptionPane optPane;	
					optPane = new JOptionPane("The new color has been applied successfully.\n");
					JDialog endDialog = optPane.createDialog("");
					endDialog.setAlwaysOnTop(true);
					endDialog.setVisible(true);
					return;
				}
			}*/
		}
		/*JDialog dialog = new JDialog();
		dialog.setSize(200,200);	
		dialog.setLayout(new BorderLayout());
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		dialog.setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/16);
		dialog.setAlwaysOnTop(true);
		// ----------- Dialog Labels ----------- //
		JLabel chooseColor_Label = new JLabel("Choose color:");
		
		// ----------- UserInput Components  ----------- //	
		JComboBox<String> color_ComboBox = AddAnimalDialog.CreateComboBox(AquaPanel.colorsArr);
		
		// ----------- Add Components To Dialog ----------- //					
		dialog.add(chooseColor_Label, BorderLayout.NORTH);
		dialog.add(color_ComboBox, BorderLayout.CENTER);
		
		JButton saveButton = new JButton("Save Color");
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String errorMessage = new String();

				if(color_ComboBox.getSelectedIndex() == 0)
					errorMessage+= "\n-Color wasn't selected.";
				
				if(errorMessage.length() > 0)
				{
					JOptionPane optPane = new JOptionPane("The following errors occured:\n"+errorMessage);
					JDialog errorDialog = optPane.createDialog("");
					errorDialog.setAlwaysOnTop(true);
					errorDialog.setVisible(true);
					return;
				}
				
				Color color;
				if(AddAnimalDialog.colorsArr[color_ComboBox.getSelectedIndex()] == AddAnimalDialog.colorsArr[1])
					color = Color.GREEN;
				else if(AddAnimalDialog.colorsArr[color_ComboBox.getSelectedIndex()] == AddAnimalDialog.colorsArr[2])
					color = Color.BLUE;
				else if(AddAnimalDialog.colorsArr[color_ComboBox.getSelectedIndex()] == AddAnimalDialog.colorsArr[3])
					color = Color.RED;
				else if(AddAnimalDialog.colorsArr[color_ComboBox.getSelectedIndex()] == AddAnimalDialog.colorsArr[4])
					color = Color.YELLOW;
				else if(AddAnimalDialog.colorsArr[color_ComboBox.getSelectedIndex()] == AddAnimalDialog.colorsArr[5])
					color = Color.BLACK;
				else
					color = Color.GREEN;
				
				for(int i = 0; i < swimmablesArr.length; i++)
				{
					if(i == selectedIndex)
					{
						MarineAnimalDecorator decorator = new MarineAnimalDecorator(swimmablesArr[i]);
						decorator.PaintFish(color);
						//swimmablesArr[i].setColor(color);
						JOptionPane optPane;	
						optPane = new JOptionPane("The new color has been applied successfully.\n");
						JDialog endDialog = optPane.createDialog("");
						endDialog.setAlwaysOnTop(true);
						endDialog.setVisible(true);
						dialog.dispose();
						return;
					}
				}
			}			
		});
		dialog.add(saveButton, BorderLayout.SOUTH);
		
		// ----------- JDialog Visibility && Closing ----------- //
		dialog.setVisible(true);	
		this.setVisible(false);
		dialog.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				dispose();		
			}
		});*/
	}
	
	/**
	 * Creates an Array from the AquaPanel's swimmable HashSet in order to maintain order of iteration 
	 * when handling selection from the JTable by index.
	 * @param containingPanel - the AquaPanel containing the Swimmable HashSet
	 * @return Swimmable[] - the newly creates Array of Swimmable
	 */
	public static Swimmable[] ConstructArrayFromHashSet(AquaPanel containingPanel)
	{
		Swimmable[] swimmables = new Swimmable[containingPanel.GetSwimmables().size()];		
		Iterator<Swimmable> iterator = containingPanel.GetSwimmables().iterator(); 
		int i = 0;
		while(iterator.hasNext() == true)
		{
			swimmables[i] = iterator.next();
			i++;
		}	
		return swimmables;
	}
}
