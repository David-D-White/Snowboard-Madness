import processing.core.PApplet;
import processing.core.PImage;

public class ScoreBoard
{
    private int score, start_lives, lives;
    private PApplet screen;
    private PImage live, dead;

    public ScoreBoard(PApplet screen, int start_lives, PImage live, PImage dead)
    {
	this.screen = screen;
	this.start_lives = start_lives;
	this.live = live;
	this.dead = dead;
	lives = start_lives;
	lives = start_lives;
    }

    public void increase(int value)
    {
	score += value;
    }

    public void decrease(int value)
    {
	score -= value;
    }

    public void loseLife()
    {
	lives -= 1;
    }

    public void reset()
    {
	lives = start_lives;
	score = 0;
    }

    public void draw()
    {
	screen.fill(255, 0, 0);
	screen.textAlign(screen.LEFT);
	screen.textSize(32);
	screen.text(score, 16, 32);
	screen.textAlign(screen.CENTER);
	for (int i = 0; i < start_lives; i++)
	{
	    if (i < lives)
	    {
		screen.image(live, screen.width - 10 - live.width / 2 - (live.width + 10) * i, 10 + live.height / 2);

	    }
	    else
	    {
		screen.image(dead, screen.width - 10 - dead.width / 2 - (dead.width + 10) * i, 10 + dead.height / 2);
	    }
	}
    }

    public int lives()
    {
	return lives;
    }

    public int score()
    {
	return score;
    }
}
