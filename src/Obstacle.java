import processing.core.PImage;

public class Obstacle
{
    private SnowboardMadness canvas;
    private Line[] lanes;
    private PImage sprite;
    private float posX, posY, width, height, velY, heightProportion;
    private Obstacle pair;
    private boolean givePoints;
    private float _accel;
    public static float accel = (float) 5, iVel = 15f;

    public Obstacle(SnowboardMadness canvas, Line[] lanes, float posY, PImage sprite, boolean givePoints)
    {
	this.canvas = canvas;
	this.lanes = lanes;

	this.posY = posY;
	this.posX = lanes[1].getX(posY);
	this.velY = iVel;

	width = sprite.width;
	height = sprite.height;
	this.sprite = sprite;
	heightProportion = (float) sprite.height / sprite.width;
	
	_accel = accel;

	this.givePoints = givePoints;
    }

    // ----------Behaviours----------//
    public void addPair(Line[] pairLanes)
    {
	pair = new Obstacle(canvas, pairLanes, posY, sprite, givePoints);
    }

    public void move()
    {
	posY += velY * canvas.deltaTime;
	if (posY >= canvas.height + sprite.height / 2 || posX <= 0 - width / 2 || posX >= canvas.width + width / 2)
	{
	    if (givePoints && canvas.gameState == 1)
	    {
		canvas.score();
	    }
	    canvas.obstacles.remove(this);
	    canvas.trees.remove(this);
	}
	width = Math.abs(lanes[0].getX(posY) - lanes[2].getX(posY));
	height = width * heightProportion;
	posX = lanes[1].getX(posY);
	velY *= Math.pow(_accel, canvas.deltaTime);
	if (pair != null)
	{
	    pair.posY = posY;
	    pair.posX = pair.lanes[1].getX(posY);
	    pair.width = width;
	    pair.height = height;
	}
    }

    public void draw()
    {
	canvas.image(sprite, posX, posY, width, height);
	if (pair != null)
	{
	    canvas.image(sprite, pair.posX, pair.posY, width, height);
	}
	canvas.rectMode(canvas.CORNER);
    }

    public boolean overlaps(Player player)
    {
	boolean overlaps = false;
	if (posY + height / 2 >= player.posY() - player.height() / 4 && width / 2 <= 98)
	{
	    overlaps = player.overlaps(this);
	    if (pair != null)
		overlaps = overlaps || player.overlaps(pair);
	}
	return overlaps;
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
}
