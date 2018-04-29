import java.util.ArrayList;

import processing.core.PConstants;
import processing.core.PImage;

public class Player
{
    private int posX, posY, width, height, relPos;
    private PImage[] player;
    private SnowboardMadness canvas;
    private int pKeyCode;
    private int cooldownTime, cooldownState;
    private ArrayList<Particle> particles;

    public Player(SnowboardMadness canvas, PImage[] player, int relPos, int cooldownTime)
    {
	width = player[relPos].width;
	height = player[relPos].height;
	this.relPos = relPos;
	this.canvas = canvas;
	this.player = player;
	this.cooldownTime = cooldownTime;
	particles = new ArrayList<Particle>();
    }

    // ----------Behaviours----------//
    public void draw()
    {
	if (cooldownState % 8 == 0)
	{
	    canvas.image(player[relPos], posX, posY);
	}
	for (int i = 0; i < particles.size(); i++)
	{
	    particles.get(i).draw();
	}
    }

    private void update()
    {
	// Update position based on keyStroke
	if (canvas.keyPressed && pKeyCode != canvas.keyCode)
	{
	    if (canvas.keyCode == PConstants.RIGHT && relPos < 2)
	    {
		relPos++;
	    }
	    else if (canvas.keyCode == PConstants.LEFT && relPos > 0)
	    {
		relPos--;
	    }
	    height = player[relPos].height;
	    width = player[relPos].width;
	}
	// Update whether or not the key is being held
	if (canvas.keyPressed && pKeyCode != canvas.keyCode)
	{
	    pKeyCode = canvas.keyCode;
	}
	else if (!canvas.keyPressed)
	{
	    pKeyCode = -1;
	}
	if (cooldownState > 0)
	{
	    cooldownState--;
	}
	for (int i = 0; i < 4; i++)
	{
	    Particle p = new Particle(canvas);
	    p.spawn();
	    particles.add(p);
	}
    }

    public void move()
    {
	update();
	posX = 160 * relPos + 80;
	posY = canvas.height - height / 2;
    }

    public boolean overlaps(Obstacle obst)
    {
	return (Math.abs(obst.posX() - posX) <= obst.width() / 2 + width / 2 && Math.abs(obst.posY() - posY) <= obst.height() / 2 + height / 2);
    }

    public void collide(Obstacle obst)
    {
	if (cooldownState <= 0)
	{
	    cooldownState = cooldownTime;
	    canvas.score.loseLife();
	}
    }

    public void resetCooldown()
    {
	cooldownState = 0;
    }

    // ----------Accessors----------//
    public float posX()
    {
	return posX;
    }

    public float posY()
    {
	return posY;
    }

    public float width()
    {
	return width;
    }

    public float height()
    {
	return height;
    }

    public int relPos()
    {
	return relPos;
    }

    public ArrayList<Particle> particles()
    {
	return particles;
    }
}
