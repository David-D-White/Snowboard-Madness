import java.awt.Color;
import processing.core.PApplet;
import processing.core.PFont;

public class Label 
{
	private PApplet canvas;
	private Color colour;
	private String text;
	private int posX, posY;
	private PFont font;
	
	public Label (PApplet canvas, String text, int posX, int posY, PFont font, Color colour)
	{
		this.canvas = canvas;
		this.text = text;
		this.posX = posX;
		this.posY = posY;
		this.font = font;
		this.colour = colour;
	}
	
	public void draw ()
	{
		canvas.textFont(font);
		canvas.fill(colour.getRGB());
		canvas.text(text, posX, posY);
	}
}
