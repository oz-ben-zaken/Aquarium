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
 * A class that extends JDialog and handles the addition of new Plant (Immobile) to the aquarium.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class AddPlantDialog extends JDialog implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private JTextField sizeTextField;
	private JComboBox<String> type_ComboBox;
	
	final String[] colorsArr = AquaPanel.colorsArr;
	final String[] typesArr = {"Type", "Laminaria", "Zostera"};
		
	/**
	 * Default constructor - creating the JLabels, JButtons and all the other components of the dialog and
	 * defines the basic behavior and settings of the JDialog.
	 * @param containingPanel
	 */
	public AddPlantDialog(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
		setSize(200,180);	
		setLayout(new GridLayout(3, 2, 3, 3));
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/8);
		
		// ----------- Dialog Labels ----------- //
		JLabel chooseType_Label = new JLabel("Choose type:");
		JLabel chooseSize_Label = new JLabel("Choose size:");
		
		// ----------- UserInput Components  ----------- //
		this.type_ComboBox = CreateComboBox(typesArr);	
		this.sizeTextField = new JTextField(3);	
		
		// ----------- Add Labels To Panel ----------- //

		add(chooseType_Label);
		add(type_ComboBox);
		add(chooseSize_Label);
		add(sizeTextField);
		JButton addButton = new JButton("Add Plant"); //  To Aquarium
		addButton.addActionListener(this);
		add(addButton);	

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
	 * Creates a combo box for parameters that offers multiple choices, like choosing the 
	 * type (Zostera, Laminaria).
	 * @param namesArr - A string array containing the names of the choices that will be presented in the Combobox
	 * @return JComboBox - a newly creates String combobox
	 */
	private JComboBox<String> CreateComboBox(String[] namesArr)
	{
		JComboBox<String> comboBox = new JComboBox<>();	
		for(int i = 0; i < namesArr.length; i++)
			comboBox.addItem(namesArr[i]);
		return comboBox;
	}
	
	/**
	 * A function that overrides the ActionListener function that handles the action to be performed when 
	 * clicking on a clickable component (mostly JButton).
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String errorMessage = new String();
		
		if(type_ComboBox.getSelectedIndex() == 0)
			errorMessage+= "\n-Type wasn't selected.";
			
		String sizeStr = sizeTextField.getText();
		if(sizeStr.length() == 0)
			errorMessage+= "\n-Size must be filled.";
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
		
		AbstractSeaFactory factory = FactoryProducer.getFactory("PlantFactory"); // Gets the relevant factory
		SeaCreature newCreature = null;
		if(type_ComboBox.getSelectedIndex() == 1) // If the user chose "Laminaria"
			newCreature = factory.produceSeaCreature(AbstractSeaFactory.Type.LAMINARIA);
		else // If user chose "Zostera"
			newCreature = factory.produceSeaCreature(AbstractSeaFactory.Type.ZOSTERA);
		
		
		// Sets the newly creates Plant fields using casting to Immobile and set-functions.
		int size = Integer.parseInt(sizeTextField.getText());
		((Immobile)newCreature).SetSize(size);
		Random random = new Random();
		int randomInt = size + random.nextInt(containingPanel.getWidth() - size); // Random X within the panel
		((Immobile)newCreature).SetX(randomInt);
		randomInt = size + random.nextInt(containingPanel.getHeight() - size); // Random Y within the panel 
		((Immobile)newCreature).SetY(randomInt);
		
		this.containingPanel.AddNewPlant((Immobile)newCreature); // Add the newly created Plant to the Aquarium
		
		JOptionPane optPane = new JOptionPane("Your new " + typesArr[type_ComboBox.getSelectedIndex()] + " was added successfully.\n");
		JDialog endDialog = optPane.createDialog("");
		endDialog.setAlwaysOnTop(true);
		endDialog.setVisible(true);
		this.containingPanel.repaint();
		this.dispose();
	}
}
