import java.awt.Color;
import java.util.ArrayList;

import com.jogamp.opengl.GL;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.opengl.PJOGL;

public class SnowboardMadness extends PApplet
{
    // SkyColour
    int sky = new Color(94, 215, 255).getRGB();
    // Images & fonts.
    PImage rock, boardLive, boardDead;
    PImage[] tree, board;
    PFont buttonFont, titleFont;
    // perspective
    final int VANISH_X = 240, VANISH_Y = 60;
    // obstacles
    ArrayList<Obstacle> obstacles, trees;
    final int OBSTACLE_WIDTH = 200, TREE_WIDTH = 360, I_TREE_HEIGHT = 110, I_OBST_HEIGHT = 140;
    float treeSpawnSpeed = 6, treeSpawn, treeCount;
    float obstSpawnSpeed = 3.5f, obstSpawn, obstCount;
    // player
    Player player;
    // Lanes
    Line[] outerLeftLane = new Line[3], outerRightLane = new Line[3];
    Line[][] obstLanes = new Line[3][3];
    // Score
    ScoreBoard score;
    // Menus
    int gameState = 0;
    UI menus;

    // Delta Time
    double oldTime, newTime, deltaTime;
    double maxDelta = 1.0 / 30;

    public void settings()
    {
	size(480, 720, P2D);
	PJOGL.setIcon("resources/Icon.png");
    }

    public void setup()
    {
	beginPGL();
	background(255);
	frameRate(240);
	fill(0);
	rect(0, 0, width, height);
	redraw();
	surface.setTitle("Snowboard Madness");
	buttonFont = createFont("resources/Minecraftia-Regular.ttf", 18);
	background(0);
	redraw();
	titleFont = createFont("resources/Minecraftia-Regular.ttf", 40);
	background(255);
	redraw();

	// Menus
	menus = new UI(this);
	// Load Images
	board = new PImage[]
	{ loadImage(".//resources/Player/BoardLeft.png"), loadImage(".//resources/Player/Board.png"), loadImage(".//resources/Player/BoardRight.png") };
	rock = loadImage(".//resources/Rock.png");
	background(0);
	redraw();
	tree = new PImage[]
	{ loadImage(".//resources/Trees/Tree1.png"), loadImage(".//resources/Trees/Tree2.png") };
	boardLive = loadImage(".//resources/Lives/Alive.png");
	boardDead = loadImage(".//resources/Lives/Dead.png");
	background(255);
	redraw();
	// Load Lanes
	for (int i = 0; i < 3; i++)
	{
	    outerLeftLane[i] = new Line(VANISH_X, VANISH_Y, 0 - TREE_WIDTH + (TREE_WIDTH / 2) * i, height - 400);
	    obstLanes[0][i] = new Line(VANISH_X, VANISH_Y, 0 - (OBSTACLE_WIDTH / 2) + (OBSTACLE_WIDTH / 2) * i, height);
	    obstLanes[1][i] = new Line(VANISH_X, VANISH_Y, width / 2 - (OBSTACLE_WIDTH / 2) + (OBSTACLE_WIDTH / 2) * i, height);
	    obstLanes[2][i] = new Line(VANISH_X, VANISH_Y, width - (OBSTACLE_WIDTH / 2) + (OBSTACLE_WIDTH / 2) * i, height);
	    outerRightLane[i] = new Line(VANISH_X, VANISH_Y, width + (TREE_WIDTH / 2) * i, height - 400);
	}
	// add obstacles
	obstacles = new ArrayList<Obstacle>();
	trees = new ArrayList<Obstacle>();
	// add player
	player = new Player(this, board, 1, 1.2f);
	// add score
	score = new ScoreBoard(this, 4, boardLive, boardDead, titleFont);
	speedCalc();

	imageMode(CENTER);
	textAlign(CENTER);
	noStroke();

	oldTime = System.nanoTime() / 1000000000.0;
    }

    public void draw()
    {
	newTime = System.nanoTime() / 1000000000.0;
	deltaTime = newTime - oldTime;
	oldTime = newTime;
	deltaTime = Math.min(deltaTime, maxDelta);

	// snow
	background(250);
	// sky
	fill(sky);
	rect(0, 0, width, 140);
	// all the time
	spawnTrees();
	for (int i = 0; i < trees.size(); i++)
	{
	    trees.get(i).move();
	}
	for (int i = trees.size() - 1; i >= 0; i--)
	{
	    trees.get(i).draw();
	}
	// Only when not on main menu
	if (gameState != 0)
	{
	    spawnObstacles();
	}
	else
	{
	    menus.updateMenu();
	    menus.drawMenu();
	}
	// Only while game running
	if (gameState == 1)
	{
	    player.move((float) deltaTime);
	    player.draw();
	    for (int i = 0; i < obstacles.size(); i++)
	    {
		if (obstacles.get(i).overlaps(player))
		{
		    player.collide(obstacles.get(i));
		}
	    }
	    if (score.lives() <= 0)
	    {
		gameState = 2;
	    }
	}
	// only at gameOver screen
	else if (gameState == 2)
	{
	    menus.updateGameOver();
	    menus.drawGameOver();
	}
    }

    public void score()
    {
	score.increase(10);
	if (score.score() < 10000)
	{
	    speedCalc();
	}
    }

    public void speedCalc()
    {
	Obstacle.accel = (float) (5 + pow(score.score() * 0.002f, 2));

	float a = log(Obstacle.accel);
	float b = a / Obstacle.iVel;
	a = 1 / a;
	float t = log((height - I_TREE_HEIGHT) * b) * a / treeSpawnSpeed;
	treeSpawn = (float) t;

	t = log((height - I_OBST_HEIGHT) * b) * a / obstSpawnSpeed;
	obstSpawn = (float) t;
    }

    public void spawnObstacles()
    {
	if (obstCount <= 0)
	{
	    obstCount += obstSpawn;
	    int pos1, pos2;
	    pos1 = (int) random(0, 3);
	    pos2 = (int) random(0, 3);
	    Obstacle temp = new Obstacle(this, obstLanes[pos1], I_OBST_HEIGHT, rock, true);
	    obstacles.add(temp);
	    if (pos1 != pos2)
	    {
		temp.addPair(obstLanes[pos2]);
	    }
	}
	else
	{
	    obstCount -= deltaTime;
	}
	score.draw();
	for (int i = 0; i < obstacles.size(); i++)
	{
	    obstacles.get(i).move();
	}
	for (int i = obstacles.size() - 1; i >= 0; i--)
	{
	    obstacles.get(i).draw();
	}
    }

    public void spawnTrees()
    {
	if (treeCount <= 0)
	{
	    treeCount += treeSpawn;
	    Obstacle temp = new Obstacle(this, outerRightLane, I_TREE_HEIGHT, tree[(int) random(0, 2)], false);
	    trees.add(temp);
	    temp.addPair(outerLeftLane);
	}
	else
	{
	    treeCount -= deltaTime;
	}
    }

    public static void main(String[] args)
    {
	PApplet.main("SnowboardMadness");
    }
}
