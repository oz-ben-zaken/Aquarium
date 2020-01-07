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
import java.util.HashSet;
import java.util.Hashtable;
import javax.swing.*;

/**
 * A class that extends JDialog and implements ActionListener and used for the Prototype Design Pattern. It
 * allows a duplication of an existing Swimmable and also allows changing the duplicated traits like color,
 * size etc.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class DuplicateAnimalDialog extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private static Hashtable<Integer, Swimmable> swimmableMap;

	/**
	 * Default Constructor, handles the creation of the JDialog including it's components and defining 
	 * the Dialog visibility and behavior.
	 * @param containingPanel - the AquaPanel that triggered the dialog.
	 */
	public DuplicateAnimalDialog(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
		swimmableMap  = CreateHashTable();
		setSize(600,300);	
		setLayout(new BorderLayout());
		this.setLocationRelativeTo(containingPanel);
		
		// ----------- Dialog Components ----------- //		
		JLabel choose_Label = new JLabel("Choose animal to duplicate and click the Duplicate button:");
		JButton duplicate_button = new JButton("Duplicate");
		duplicate_button.addActionListener(this);
		CreateTable();
		
		// ----------- Add Components To Dialog ----------- //
		add(choose_Label, BorderLayout.NORTH);
		add(duplicate_button, BorderLayout.SOUTH);
		add(this.scrollPane, BorderLayout.CENTER);
		
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
	 * Overrides the ActionListener method and handles the actions that needs to be performed when clicking on
	 * buttons and clickable components.
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
		Swimmable selectedSwimmable = swimmableMap.get(selectedIndex);
		Swimmable newSwimmable = (Swimmable)selectedSwimmable.clone();
		ChangeDuplicatedAttributes(newSwimmable);
		dispose();			
	}
	
	/**
	 * Creates Hashtable from the AquaPanel's Swimmable HashSet. Used Hashtable as it allows maintaining an 
	 * order when iterating over the Swimmables, unlike hashset.
	 * @return Hashtable<Integer, Swimmable> - the newly created Hashtable.
	 */
	public Hashtable<Integer, Swimmable> CreateHashTable()
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
	 * Creates the JTable for showing the user the existing Swimmables from which he can choose a Swimmable
	 * to duplicate.
	 */
	public void CreateTable()
	{
		Object[][] data =  new Object[swimmableMap.size()][7];
		for(int i = 0; i < swimmableMap.size(); i++)
		{
			Swimmable swimmable = swimmableMap.get(i);
			data[i][0] = swimmable.getAnimalName();
			data[i][1] = swimmable.getColor();
			data[i][2] = swimmable.getSize();
			data[i][3] = swimmable.getHorSpeed();
			data[i][4] = swimmable.getVerSpeed();
			data[i][5] = swimmable.getEatCount();
			data[i][6] = swimmable.GetEatFrequency();
		}	
		this.table = new JTable(data, containingPanel.JTableColumnNames);
		
		this.scrollPane = new JScrollPane(table);
		this.table.setFillsViewportHeight(true);
		this.scrollPane.setFocusable(true);
		this.add(scrollPane, BorderLayout.CENTER);			
		this.revalidate();
		this.repaint();				
	}
	
	/**
	 * Receives the newly duplicated Swimmable and shows the user a new dialog, allowing to change the 
	 * attributes of the Swimmable, like color, size etc.
	 * @param newCreature
	 */
	public void ChangeDuplicatedAttributes(Swimmable newCreature)
	{
		int userChoice = JOptionPane.showConfirmDialog(this, 
		        "Would you like to change the duplicated animal attributes?\n"
		        + "(Vertical Speed, Horizontal Speed, Color, Size)", "Change Attributes Message Box",
		        JOptionPane.YES_NO_OPTION);
		if (userChoice == JOptionPane.NO_OPTION) 
		{	
			containingPanel.AddNewAnimal((Swimmable)newCreature);
			((Swimmable)newCreature).start();
			return;
		}	
		
		JDialog dialog = new JDialog();
		dialog.setSize(300,300);	
		dialog.setLayout(new GridLayout(5, 2, 3, 3));
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		dialog.setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/8);
		dialog.setAlwaysOnTop(true);
		
		// ----------- Dialog Labels ----------- //
		JLabel chooseSize_Label = new JLabel("Choose size:");
		JLabel HorizontalSpeed_Label = new JLabel("Choose horizontal speed:");
		JLabel VerticalSpeed_Label = new JLabel("Choose vertical speed:");
		JLabel chooseColor_Label = new JLabel("Choose color:");
		
		// ----------- UserInput Components  ----------- //	
		JTextField sizeTextField = new JTextField();	
		JSlider horizontalSpeedSlider = AddAnimalDialog.CreateJSliderForSpeed();
		JSlider verticalSpeedSlider = AddAnimalDialog.CreateJSliderForSpeed();	
		JComboBox<String> color_ComboBox = AddAnimalDialog.CreateComboBox(AquaPanel.colorsArr);
		
		// ----------- Set Fields To Current Attributes Values ----------- //	
		sizeTextField.setText(String.valueOf(((Swimmable)newCreature).getSize()));
		horizontalSpeedSlider.setValue(((Swimmable)newCreature).getHorSpeed());
		verticalSpeedSlider.setValue(((Swimmable)newCreature).getVerSpeed());
		color_ComboBox.setSelectedItem(((Swimmable)newCreature).getColor());
		
		// ----------- Add Labels To Dialog ----------- //		
		dialog.add(chooseSize_Label);
		dialog.add(sizeTextField);
		
		dialog.add(HorizontalSpeed_Label);
		dialog.add(horizontalSpeedSlider);
		
		dialog.add(VerticalSpeed_Label);
		dialog.add(verticalSpeedSlider);
			
		dialog.add(chooseColor_Label);
		dialog.add(color_ComboBox);
		
		JButton addButton = new JButton("Save Attributes");
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String errorMessage = new String();

				if(color_ComboBox.getSelectedIndex() == 0)
					errorMessage+= "\n-Color wasn't selected.";
					
				String sizeStr = sizeTextField.getText();
				if(sizeStr.length() == 0)
					errorMessage+= "\n-Size must be an integer.";
				else
				{
					try
					{
						int parsedSize = Integer.parseInt(sizeTextField.getText());
						if(parsedSize < 20 || parsedSize > 320)
							errorMessage+= "\n-Size must be between 20 to 320.";
					}
					catch(NumberFormatException err)
					{
						err.toString();
						errorMessage+= "\n-Size must be an integer.";
					}
				}	
				
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
			    
				int horizontalSpeed = horizontalSpeedSlider.getValue();
				int verticalSpeed = verticalSpeedSlider.getValue();
				int size = Integer.parseInt(sizeTextField.getText());
				
				newCreature.setHorSpeed(horizontalSpeed);
				newCreature.setVerSpeed(verticalSpeed);
				newCreature.setSize(size);
				newCreature.setColor(color);
				newCreature.start();
				containingPanel.AddNewAnimal(newCreature);
				dispose();
				dialog.dispose();
			}		
		});
		
		dialog.add(addButton);
		// ----------- JDialog Visibility && Closing ----------- //
		dialog.setVisible(true);	
		dialog.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				dispose();		
			}
		});
	}
	
}
