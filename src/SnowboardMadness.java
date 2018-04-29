import java.awt.Color;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class SnowboardMadness extends PApplet
{
    // SkyColour
    int sky = new Color(94, 215, 255).getRGB();
    // Images
    PImage rock, boardLive, boardDead;
    PImage[] tree, board;
    // perspective
    final int VANISH_X = 240, VANISH_Y = 60;
    // obstacles
    ArrayList<Obstacle> obstacles, trees;
    final int OBSTACLE_WIDTH = 200, TREE_WIDTH = 360, I_TREE_HEIGHT = 110, I_OBST_HEIGHT = 140;
    int treeSpawnSpeed = 7, treeSpawn = treeSpawnSpeed, treeCount = treeSpawn;
    int obstSpawnSpeed = 3, obstSpawn = obstSpawnSpeed, obstCount = obstSpawn;
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

    public void settings()
    {
	size(480, 720);
    }

    public void setup()
    {
	background(255);
	frameRate(60);
	surface.setTitle("Snowboard Madness");
	PImage icon = loadImage("resources/Icon.png");
	surface.setIcon(icon);
	
	// Menus
	menus = new UI(this);
	// Load Images
	board = new PImage[]
	{ loadImage(".//resources/Player/BoardLeft.png"), loadImage(".//resources/Player/Board.png"), loadImage(".//resources/Player/BoardRight.png") };
	rock = loadImage(".//resources/Rock.png");
	tree = new PImage[]
	{ loadImage(".//resources/Trees/Tree1.png"), loadImage(".//resources/Trees/Tree2.png") };
	boardLive = loadImage(".//resources/Lives/Alive.png");
	boardDead = loadImage(".//resources/Lives/Dead.png");
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
	player = new Player(this, board, 1, 60);
	// add score
	score = new ScoreBoard(this, 4, boardLive, boardDead);
	speedCalc();

	imageMode(CENTER);
	textAlign(CENTER);
	noStroke();
    }

    public void draw()
    {
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
	    player.move();
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
	Obstacle.accel = (float) (1.025 + ((int) score.score() * 0.01) * 0.001);

	float a = log(Obstacle.accel);
	float b = a / Obstacle.iVel;
	a = 1 / a;
	float t = log((height - I_TREE_HEIGHT) * b) * a / treeSpawnSpeed;
	treeSpawn = (int) t;

	t = log((height - I_OBST_HEIGHT) * b) * a / obstSpawnSpeed;
	obstSpawn = (int) t;
    }

    public void spawnObstacles()
    {
	obstCount--;
	if (obstCount <= 0)
	{
	    obstCount = obstSpawn;
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
	treeCount--;
	if (treeCount <= 0)
	{
	    treeCount = treeSpawn;
	    Obstacle temp = new Obstacle(this, outerRightLane, I_TREE_HEIGHT, tree[(int) random(0, 2)], false);
	    trees.add(temp);
	    temp.addPair(outerLeftLane);
	}
    }

    public static void main(String[] args)
    {
	PApplet.main("SnowboardMadness");
    }
}
