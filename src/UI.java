import java.awt.Color;
import java.util.ArrayList;

public class UI
{
    private SnowboardMadness canvas;
    private ArrayList<Button> menuButtons, gameOverButtons;
    private ArrayList<Label> menuLabels, gameOverLabels;

    public UI(SnowboardMadness canvas)
    {
	this.canvas = canvas;
	menuButtons = new ArrayList<Button>();
	menuLabels = new ArrayList<Label>();
	gameOverButtons = new ArrayList<Button>();
	gameOverLabels = new ArrayList<Label>();
	// Create menu
	menuLabels.add(new Label(canvas, "Snowboard", canvas.width / 2, 100, 50, Color.BLUE));
	menuLabels.add(new Label(canvas, "Madness", canvas.width / 2, 150, 50, Color.BLUE));
	menuButtons.add(new Button(canvas, canvas.width / 2, 260, 120, 40, new Color(150, 150, 150), new Color(220, 220, 220), "New Game"));
	menuButtons.add(new Button(canvas, canvas.width / 2, 320, 120, 40, new Color(150, 150, 150), new Color(220, 220, 220), "Quit"));
	// Create gameOver
	gameOverLabels.add(new Label(canvas, "GAME OVER", canvas.width / 2, 220, 50, Color.BLUE));
	gameOverButtons.add(new Button(canvas, canvas.width / 2 - 80, 280, 120, 40, new Color(150, 150, 150), new Color(220, 220, 220), "Play Again"));
	gameOverButtons.add(new Button(canvas, canvas.width / 2 + 80, 280, 120, 40, new Color(150, 150, 150), new Color(220, 220, 220), "Menu"));
    }

    public void updateMenu()
    {
	for (int i = 0; i < menuButtons.size(); i++)
	{
	    menuButtons.get(i).update();
	    if (menuButtons.get(i).clickState() == true)
	    {
		if (i == 0)
		{
		    canvas.gameState = 1;
		}
		else if (i == 1)
		{
		    System.exit(0);
		}
	    }
	}
    }

    public void updateGameOver()
    {
	for (int i = 0; i < gameOverButtons.size(); i++)
	{
	    gameOverButtons.get(i).update();
	    if (gameOverButtons.get(i).clickState() == true)
	    {
		canvas.score.reset();
		canvas.player.resetCooldown();
		canvas.obstacles.clear();
		canvas.trees.clear();
		canvas.speedCalc();
		if (i == 0)
		{
		    canvas.gameState = 1;
		}
		else if (i == 1)
		{
		    canvas.gameState = 0;
		}
	    }
	}
    }

    public void drawMenu()
    {
	for (int i = 0; i < menuButtons.size(); i++)
	{
	    menuButtons.get(i).draw();
	}
	for (int i = 0; i < menuLabels.size(); i++)
	{
	    menuLabels.get(i).draw();
	}
    }

    public void drawGameOver()
    {
	for (int i = 0; i < gameOverButtons.size(); i++)
	{
	    gameOverButtons.get(i).draw();
	}
	for (int i = 0; i < gameOverLabels.size(); i++)
	{
	    gameOverLabels.get(i).draw();
	}
    }
}
