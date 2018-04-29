import java.awt.Color;

import processing.core.PApplet;

public class Button 
{
	private boolean clickState,heldState;
	private float posX, posY, width, height;
	private Color on, off;
	private String label;
	private PApplet canvas;
	private long resetTime, clickedTime;
	private int textSize;
	
	public Button (PApplet canvas, float posX, float posY, float width, float height, 
			Color on, Color off, String label)
	{
		this.canvas = canvas;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.on = on;
		this.off = off;
		this.label = label;
		textSize = (int) (height/2);
		resetTime = 100;
	}
	//----------Behaviours----------//
	public boolean hover ()
	{
		return (Math.abs(canvas.mouseX - posX) < (width +1)/2 && Math.abs(canvas.mouseY - posY) < (height +1) /2);
	}
	
	public boolean held()
	{
		return (hover() && canvas.mousePressed && canvas.mouseButton == PApplet.LEFT);
	}
	
	public boolean clicked()
	{
		if (held() && !heldState)
		{
			heldState = true;
			return true;
		}
		else if (held() && heldState)
		{
			return false;
		}
		else
		{
			heldState = false;
			return false;
		}
	}
	
	public void toggleState()
	{
		clickState = !clickState;
	}
	
	public void update ()
	{
		long current = System.currentTimeMillis();
		if (clicked() && current > clickedTime + resetTime)
		{
			toggleState();
			clickedTime = System.currentTimeMillis();
		}
		else if (clickState && current > clickedTime + resetTime)
		{
			clickState = false;
		}
	}
	
	public void draw()
	{
		if (clickState)
		{
			canvas.fill(on.getRGB());
		}
		else
		{
			canvas.fill(off.getRGB());
		}
		canvas.rectMode(PApplet.CENTER);
		canvas.strokeWeight(4);
		canvas.stroke(50);
		canvas.rect(posX,  posY, width, height);
		canvas.strokeWeight(0);
		canvas.rectMode(PApplet.CORNER);
		canvas.fill(0);
		canvas.noStroke();
		canvas.textSize(textSize);
		canvas.text(label, posX, posY+textSize/2);
	}
	
	//----------Accessors----------//
	public boolean clickState()
	{
		return clickState;
	}
	
	public float PosX() 
	{
		return posX;
	}
	
	public float PosY() 
	{
		return posY;
	}

	public float Width() 
	{
		return width;
	}

	public float Height()
	{
		return height;
	}

	public String Label() 
	{
		return label;
	}

	//---------Setters----------//
	public void setPosX(float posX) 
	{
		this.posX = posX;
	}

	public void setPosY(float posY) 
	{
		this.posY = posY;
	}

	public void setWidth(float width) 
	{
		this.width = width;
	}

	public void setHeight(float height) 
	{
		this.height = height;
	}

	public void setOnColour(Color on) 
	{
		this.on = on;
	}

	public void setOffolour(Color off) 
	{
		this.off = off;
	}

	public void setLabel(String label) 
	{
		this.label = label;
	}
	
}
