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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * A class that extends JFrame and used as a frame that containg the aquarium panel and has an upper menu 
 * with different options. The class also extends ActionListener to allow ovveride of the actionPerformed method
 * instead of using anonymous classes for defining an action listener for each JButton.
 * @author Mai Ben-Ami
 * @author Oz Ben Zaken
 */
public class AquaFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private AquaPanel panel;
	
	private final String[] fileMenuItemsStrs ={"Exit"};
	private final String[] backgroundMenuItemsStrs ={"Image", "Image From Net", "Blue", "None"};
	private final String[] helpMenuItemsStrs ={"Help"};
	private final String[] mementoMenuItemsStrs ={"Save Object State", "Restore Object State"};
	private JMenuItem[] fileMenuItemsArr;
	private JMenuItem[] backgroundMenuItemsArr;
	private JMenuItem[] helpMenuItemsArr;
	private JMenuItem[] mementoMenuItemsArr;
	
	/*private Image backgroundImage;
	private JLabel imageContentPane;
	 */

	
	/**
	 * Default constructor - handles the creation of the JFrame along with the components such as JButtons and 
	 * JMenu. It also sets an actionListener for the clickable components like JButton.
	 */
	public AquaFrame() 
	{			
		super("Mai && Oz's Aquarium"); // Calls the JFrame constructor to initialize the frame name

		// ---------------------------- Creating Frame Components ---------------------------- //
		
		CreateFrameMenu();		

		// ---------------------------- Adding Frame Components ---------------------------- //
		getContentPane().add(this.menu, BorderLayout.NORTH);
	
		WindowListener listener = new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				handleProgramClosingAttemp();			
			}
		};
		addWindowListener(listener);
		
		this.panel = new AquaPanel(listener, this);
		getContentPane().add(this.panel, BorderLayout.CENTER);
		
		// ---------------------------- Basic Frame Settings ---------------------------- //	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		pack();
		setSize(1180, 600);
		//this.setLocationByPlatform(true);
		setLocationRelativeTo(null);
		//this.setAlwaysOnTop(true);
		setVisible(true);
	}
	
	/**
	 * Function that creates the upper menu of the frame, as well as the menu items and sets their action listeners.
	 */
	private void CreateFrameMenu()
	{		
		this.menu = new JMenuBar();	
		// ---------------- First Dropdown menu - File ---------------- //
		JMenu firstMenu = new JMenu("File");
		
		fileMenuItemsArr = new JMenuItem[fileMenuItemsStrs.length];
		for(int i = 0; i < fileMenuItemsArr.length; i++)
		{
			fileMenuItemsArr[i] = new JMenuItem(fileMenuItemsStrs[i]);
			fileMenuItemsArr[i].addActionListener(this);
			firstMenu.add(fileMenuItemsArr[i]);
		}
		menu.add(firstMenu);
	
		// ---------------- Second Dropdown menu - BackGround ---------- //	
		JMenu secondMenu = new JMenu("BackGround");
		
		backgroundMenuItemsArr = new JMenuItem[backgroundMenuItemsStrs.length];
		for(int i = 0; i < backgroundMenuItemsStrs.length; i++)
		{
			backgroundMenuItemsArr[i] = new JMenuItem(backgroundMenuItemsStrs[i]);
			backgroundMenuItemsArr[i].addActionListener(this);
			secondMenu.add(backgroundMenuItemsArr[i]);
		}
		menu.add(secondMenu);		
		
		// ---------------- Third Dropdown menu - Help ---------------- //
		JMenu thirdMenu = new JMenu("Help");
		
		this.helpMenuItemsArr = new JMenuItem[helpMenuItemsStrs.length];
		for(int i = 0; i < helpMenuItemsStrs.length; i++)
		{
			helpMenuItemsArr[i] = new JMenuItem(helpMenuItemsStrs[i]);
			helpMenuItemsArr[i].addActionListener(this);
			thirdMenu.add(helpMenuItemsArr[i]);
		}

		menu.add(thirdMenu);
		
		// ---------------- Forth Dropdown menu - Memento ---------------- //
		JMenu forthMenu = new JMenu("Memento");
		
		this.mementoMenuItemsArr = new JMenuItem[mementoMenuItemsStrs.length];
		for(int i = 0; i < mementoMenuItemsStrs.length; i++)
		{
			mementoMenuItemsArr[i] = new JMenuItem(mementoMenuItemsStrs[i]);
			mementoMenuItemsArr[i].addActionListener(this);
			forthMenu.add(mementoMenuItemsArr[i]);
		}

		menu.add(forthMenu);
	}
	
	/**
	 * Function that handles the closing of the program by asking the user if he sure he wants to quit, using 
	 * a confirm dialog.
	 */
	public void handleProgramClosingAttemp()
	{
		int userChoice = JOptionPane.showConfirmDialog(this, 
				        "Are you sure you want to exit the program?", "Exit Program Message Box",
				        JOptionPane.YES_NO_OPTION);
		if (userChoice == JOptionPane.YES_OPTION) 
		{	
			dispose();
			System.exit(0);
		}	
	}

	/**
	 * Returns the height of the upper frame menu. Used for the swimmables upper border of movement.
	 * @return
	 */
	public int GetUpperMenuHeight()
	{
		return this.menu.getHeight();
	}

	/**
	 * Overrides the ActionListener method and handles the actions that needs to be performed when a button was
	 * clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String source = null;
		try
		{
			source = ((JMenuItem)e.getSource()).getText();
		}
		catch(Exception err)
		{
			err.printStackTrace();
			return;
		}
			
		if(source == backgroundMenuItemsStrs[0]) // If source == 'Image'
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("C:\\"));
			// Add files filter to display only pictures format
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "tif");
			chooser.addChoosableFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false); // Disables the "all files" option		
			chooser.showOpenDialog(this);	
			File file = chooser.getSelectedFile();
			if(file == null)
				return;
			String path = file.getAbsolutePath();
			Image image = new ImageIcon(path).getImage();	
			panel.SetBackgroundImage(image);
		}
		else if(source == backgroundMenuItemsStrs[1]) // if source == "Image from net"
		{
			/*
			 *  The following loads an image from a specific address from the internet. It runs in a seperate 
			 *  thread as this action can be time consuming and could slow down the program or freeze the 
			 *  program until the image is loaded.
			*/
			Thread loadImageThread = new Thread(new Runnable(){
				public void run() 
				{				
					BufferedImage image = null;
					try 
					{
						image = ImageIO.read(new URL("https://i.ytimg.com/vi/VMLjLDr6k1A/maxresdefault.jpg"));
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					panel.SetBackgroundImage(image);
				}
			});
			loadImageThread.start();
		}
		else if(source == backgroundMenuItemsStrs[2]) // if source == "Blue"
		{
			panel.RemoveBackgroundImage();
			panel.setBackground(Color.BLUE);
			panel.repaint();
		}
		else if(source == backgroundMenuItemsStrs[3]) // if source == "None"
		{
			panel.RemoveBackgroundImage();
			panel.setBackground(Color.WHITE);
			panel.repaint();
		}		
		else if(source == helpMenuItemsStrs[0]) // if source == "Help"
		{
			JOptionPane.showMessageDialog(this, "HomeWork 3\n" + "GUI @ Threads");
		}
		else if(source == fileMenuItemsStrs[0]) // If source == exit
		{
			handleProgramClosingAttemp();
		}
		else if(source == mementoMenuItemsStrs[0]) // if source == "Save Object State"
		{
			String[] options = new String[2];
			options[0] = new String("Swimmable");
			options[1] = new String("SeaPlant");
			@SuppressWarnings("unused")
			MementoSaveDialog dialog;
			int userChoice = JOptionPane.showOptionDialog(this,"Choose type to save it's state","", 
					0,JOptionPane.INFORMATION_MESSAGE,null,options,null);	
			if(userChoice == 0)
				dialog = new MementoSaveDialog(panel, "Swimmable");
			else if(userChoice == 1)
				dialog = new MementoSaveDialog(panel, "SeaPlant");
		}
		else if(source == mementoMenuItemsStrs[1]) // if source == "Restore Object State"
		{
			String[] options = new String[2];
			options[0] = new String("Swimmable");
			options[1] = new String("SeaPlant");
			@SuppressWarnings("unused")
			MementoRestoreDialog dialog;
			int userChoice = JOptionPane.showOptionDialog(this,"Choose type to restore it's state","", 
					0,JOptionPane.INFORMATION_MESSAGE,null,options,null);	
			if(userChoice == 0)
				dialog = new MementoRestoreDialog(panel, "Swimmable");
			else if(userChoice == 1)
				dialog = new MementoRestoreDialog(panel, "SeaPlant");
		}
	}
	
	
	
	/**
	 * A main method for running and testing the program.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		AquaFrame aquaFrame = new AquaFrame();	
		aquaFrame.toString(); // TO BE REMOVED
	}
}


