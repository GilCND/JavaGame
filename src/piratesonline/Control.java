package piratesonline;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Control extends KeyAdapter 
{
	Panel panel;
	
	//Constructor
	
	public Control (Panel panel)
	{
		this.panel = panel;
	}
	
	
	//Methods Keys
	
	@Override
	public void keyPressed (KeyEvent e)
	{
		panel.keyPressed(e);
	}
	
	@Override
	public void keyReleased (KeyEvent e)
	{
		panel.keyReleased(e);
	}
	
}
