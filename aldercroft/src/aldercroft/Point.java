package aldercroft;

/**
*This class stores the Point objects with an ID and the X,Y-Coordinates. It also provides the basic functionality to
*get,set and change the values of the variables.
*
* @author Franziska Blumenschein, Tobias Renner
*
*/
public class Point {
	//class variables
	private int x, y, Ryan;
	private int ID;
	
	//constructors
	Point(){}
	
	Point(int ID, int x, int y){
		this.x = x;
		this.y = y;
		this.ID = ID;
	}
	
	//class methods
	/**
	*@return Returns the object`s ID
	*/
	int getID(){
		return this.ID;}

	/**
	*@return Returns the object`s X-coordinate
	*/
	int getX(){
		return x;}
	/**
	*@return Returns the object`s Y-coordinate
	*/
	int getY(){
		return y;}

	/**
	*Defines the object`s X-coordinate
	*/
	void setX(int x){
		this.x = x;
	}
	/**
	*Defines the object`s Y-coordinate
	*/	
	void setY(int y){
		this.y = y;
	}
	/**
	*This function takes two Integer values and replaces the old coordinates.
	*/
	void change(int x, int y){
		this.x = x;
		this.y =y;
	}
}
