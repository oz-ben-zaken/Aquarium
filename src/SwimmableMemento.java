/*
 * Name: Mai Ben Ami
 * ID: 307889402
 * Name: Oz Ben Zaken
 * ID: 204015614
 * Campus: Ashdod
 * Homework Number 4 - Aquarium + Design Patterns
 */

import java.awt.Color;


public class SwimmableMemento 
{
	Color color;
	int size, x_front, y_front, verSpeed, horSpeed; 
	
	public SwimmableMemento(Color _color, int _size, int _xFront, int _yFront, int _verSpeed, int _horSpeed)
	{
		this.color = _color;
		this.size = _size;
		this.x_front = _xFront;
		this.y_front = _yFront;
		this.verSpeed = _verSpeed;
		this.horSpeed = _horSpeed;
	}
	
	public void restoreState(Swimmable swimmable)
	{
		swimmable.setColor(color);
		swimmable.setSize(size);
		swimmable.set_X_Front(x_front);
		swimmable.set_Y_Front(y_front);
		swimmable.setVerSpeed(verSpeed);
		swimmable.setHorSpeed(horSpeed);
	}
}
