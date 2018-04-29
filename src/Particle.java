public class Particle
{
    private float posX, posY, size, velX, velY, gravity = (float) 0.38;
    private int relPos;
    private SnowboardMadness screen;

    public Particle(SnowboardMadness screen)
    {
	this.screen = screen;
	this.posX = 0;
	this.posY = 0;
	this.velX = 0;
	this.velY = 0;
	this.size = screen.random(4, 10);
    }

    public void spawn()
    {
	relPos = screen.player.relPos();
	switch (relPos)
	{
	case 0:
	    posX = 80 - screen.player.width() / 2;
	    posY = screen.height - screen.random(0, screen.player.height());
	    velX = -(float) screen.random(7, 15);
	    velY = -(float) screen.random(-5, 5);
	    break;
	case 2:
	    posX = screen.width - 80 + screen.player.width() / 2;
	    posY = screen.height - screen.random(0, screen.player.height());
	    velX = (float) screen.random(7, 15);
	    velY = -(float) screen.random(-5, 5);
	    break;
	}
    }

    public void draw()
    {
	if (screen.height < posY || 0 > posX || 0 > posY || screen.width < posX || relPos == 1)
	{
	    screen.player.particles().remove(this);
	}
	else
	{
	    posX += velX;
	    posY += velY;
	    velY += gravity;
	    screen.fill(200);
	    screen.rect(posX, posY, size, size);
	}
    }
}
