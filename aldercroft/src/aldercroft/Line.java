package aldercroft;
/**
*This class stores the Line objects with an ID and the X,Y-Coordinates of the starting and ending point. 
*It also provides the basic functionality to get and set the values of the variables.
 * @author Franziska Blumenschein, Tobias Renner
 */
public class Line {
	//class variables
	private int ID;
	private int x1, x2, y1, y2;
	
	//constructor
	Line(int ID, int x1, int y1, int x2, int y2){
		this.ID = ID;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	//class methods
	/**
	*@return Returns the object`s ID
	*/
	int getID(){
		return this.ID;}
	/**
	*@return Returns the X-coordinate of the starting point
	*/
	int getX1(){
		return x1;}
	
	/**
	*@return Returns the Y-coordinate of the starting point
	*/
	int getY1(){
		return y1;}
	
	/**
	*@return Returns the X-coordinate of the ending point
	*/
	int getX2(){
		return x2;}
	
	/**
	*@return Returns the Y-coordinate of the ending point
	*/
	int getY2(){
		return y2;}
	
	/**
	*Defines the X-coordinate of the starting Point
	*/
	void setX1(int x1){
		this.x1 = x1;
	}
	
	/**
	*Defines the Y-coordinate of the starting Point
	*/
	void setY1(int y1){
		this.y1 = y1;
	}
	
	/**
	*Defines the X-coordinate of the ending Point
	*/
	void setX2(int x2){
		this.x2 = x2;
	}
	
	/**
	*Defines the Y-coordinate of the ending Point
	*/
	void setY2(int y2){
		this.y2 = y2;
	}
}
