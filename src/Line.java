
public class Line 
{
	private boolean vertical;
	private float verticalX;
	private float slope;
	private float yInt;
	
	public Line (float x1, float y1, float x2, float y2)
	{
		vertical = x1-x2 == 0;
		if (!vertical)
		{
			slope = (y1 - y2) / (x1- x2);
		}
		else
		{
			verticalX = x1;
		}
		yInt = y1 - (slope * x1);
	}
	
	public float getX(float y)
	{
		if (vertical)
		{
			return verticalX;
		}
		return (y - yInt)/slope;	
	}
	
	public float getY(float x)
	{
		return slope*x + yInt;	
	}
}
