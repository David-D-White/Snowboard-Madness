import java.awt.Color;
import processing.core.PApplet;

public class Label 
{
	private PApplet canvas;
	private Color colour;
	private String text;
	private int posX, posY, size;
	
	public Label (PApplet canvas, String text, int posX, int posY, int size, Color colour)
	{
		this.canvas = canvas;
		this.text = text;
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		this.colour = colour;
	}
	
	public void draw ()
	{
		canvas.textSize(size);
		canvas.fill(colour.getRGB());
		canvas.text(text, posX, posY);
	}
}
