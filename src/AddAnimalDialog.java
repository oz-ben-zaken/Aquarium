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

import javax.swing.*;



/**
 * A class that extends JDialog and handles the addition of new animal (Swimmable) to the aquarium.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class AddAnimalDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private AquaPanel containingPanel;
	private JTextField sizeTextField;
	private JSlider horizontalSpeedSlider;
	private JSlider verticalSpeedSlider;
	private JComboBox<String> type_ComboBox;
	private JComboBox<String> color_ComboBox;
	private JSlider eatFrequencySlider;
	
	public static final String[] colorsArr = AquaPanel.colorsArr;
	final String[] typesArr = {"Type", "Fish", "JellyFish"};
	
	
	/**
	 * Default constructor - creating the JLabels, JButtons and all the other components of the dialog and
	 * defines the basic behavior and settings of the JDialog.
	 * @param containingPanel
	 */
	public AddAnimalDialog(AquaPanel containingPanel)
	{
		this.containingPanel = containingPanel;
			
		setSize(300,300);	
		setLayout(new GridLayout(7, 2, 3, 3));
		Point panelLocationOnScreen = containingPanel.getLocationOnScreen();
		setLocation((int)panelLocationOnScreen.getX() + (int)containingPanel.getSize().getHeight()/2, 
				(int)panelLocationOnScreen.getY() + (int)containingPanel.getSize().getHeight()/8);
		
		// ----------- Dialog Labels ----------- //
		JLabel chooseType_Label = new JLabel("Choose type:");
		JLabel chooseSize_Label = new JLabel("Choose size:");
		JLabel HorizontalSpeed_Label = new JLabel("Choose horizontal speed:");
		JLabel VerticalSpeed_Label = new JLabel("Choose vertical speed:");
		JLabel chooseColor_Label = new JLabel("Choose color:");
		JLabel eatFrequency_Label = new JLabel("Choose Eat Frequency:");
		// ----------- UserInput Components  ----------- //
		this.type_ComboBox = CreateComboBox(typesArr);	
		this.sizeTextField = new JTextField();	
		this.horizontalSpeedSlider = CreateJSliderForSpeed();
		this.verticalSpeedSlider = CreateJSliderForSpeed();	
		this.color_ComboBox = CreateComboBox(colorsArr);
		this.eatFrequencySlider = CreateJSliderForSpeed();
		// ----------- Add Labels To Dialog ----------- //
		add(chooseType_Label);
		add(type_ComboBox);
		
		add(chooseSize_Label);
		add(sizeTextField);
		
		add(HorizontalSpeed_Label);
		add(horizontalSpeedSlider);
		
		add(VerticalSpeed_Label);
		add(verticalSpeedSlider);
			
		add(chooseColor_Label);
		add(color_ComboBox);
		
		add(eatFrequency_Label);
		add(eatFrequencySlider);
		
		JButton addButton = new JButton("Add To Aquarium");
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
	 * Creates and return a JSlider for choosing the horizontal and vertical speed of  the Swimmable.
	 * @return JSlider - the newly created JSlider
	 */
	public static JSlider CreateJSliderForSpeed()
	{
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setVisible(true);
		slider.setMinimum(1);
		return slider;
	}
	
	/**
	 * Creates a combo box for parameters that offers multiple choices, like choosing the color of the Swimmable
	 * and choosing the type (Fish, JellyFish etc).
	 * @param namesArr - A string array containing the names of the choices that will be presented in the combobox
	 * @return JComboBox - a newly creates String combobox
	 */
	public static JComboBox<String> CreateComboBox(String[] namesArr)
	{
		JComboBox<String> comboBox = new JComboBox<>();	
		for(int i = 0; i < namesArr.length; i++)
			comboBox.addItem(namesArr[i]);
		return comboBox;
	}

	/**
	 * A function that overrides the ActionListener function that handles the action to be performed when clicking
	 * on a clickable component (mostly JButton).
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String errorMessage = new String();

		if(color_ComboBox.getSelectedIndex() == 0)
			errorMessage+= "\n-Color wasn't selected.";
		if(type_ComboBox.getSelectedIndex() == 0)
			errorMessage+= "\n-Type wasn't selected.";
			
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
		if(colorsArr[color_ComboBox.getSelectedIndex()] == colorsArr[1])
			color = Color.GREEN;
		else if(colorsArr[color_ComboBox.getSelectedIndex()] == colorsArr[2])
			color = Color.BLUE;
		else if(colorsArr[color_ComboBox.getSelectedIndex()] == colorsArr[3])
			color = Color.RED;
		else if(colorsArr[color_ComboBox.getSelectedIndex()] == colorsArr[4])
			color = Color.YELLOW;
		else if(colorsArr[color_ComboBox.getSelectedIndex()] == colorsArr[5])
			color = Color.BLACK;
		else
			color = Color.GREEN;
	    

		int horizontalSpeed = horizontalSpeedSlider.getValue();
		int verticalSpeed = verticalSpeedSlider.getValue();
		int size = Integer.parseInt(sizeTextField.getText());
		int eatFrequency = eatFrequencySlider.getValue();
		
		
		AbstractSeaFactory factory = FactoryProducer.getFactory("AnimalFactory"); // Gets the relevant factory
		SeaCreature newCreature = null;
		if(type_ComboBox.getSelectedIndex() == 1) // If the user chose "Fish"
			newCreature = factory.produceSeaCreature(AbstractSeaFactory.Type.FISH); // Produces a Fish with the factory it got
		else
			newCreature = factory.produceSeaCreature(AbstractSeaFactory.Type.JELLYFISH); // Produces a JellyFish with the factory it got
		
		// Uses set-functions to set the private fields of the new creates SeaCreature, using a casting to Swimmable
		((Swimmable)newCreature).setHorSpeed(horizontalSpeed);
		((Swimmable)newCreature).setVerSpeed(verticalSpeed);
		((Swimmable)newCreature).setSize(size);
		((Swimmable)newCreature).setColor(color);
		((Swimmable)newCreature).setContainingPanel(this.containingPanel);
		((Swimmable)newCreature).SetEatFrequency(eatFrequency);
		((Swimmable)newCreature).start();
		this.containingPanel.AddNewAnimal((Swimmable)newCreature);		
		
		
		JOptionPane optPane = new JOptionPane("Your new " + typesArr[type_ComboBox.getSelectedIndex()] + " was added successfully.\n");
		JDialog endDialog = optPane.createDialog("");
		endDialog.setAlwaysOnTop(true);
		endDialog.setVisible(true);
		
		dispose();
	}
}
