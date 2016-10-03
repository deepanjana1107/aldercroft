package aldercroft;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;


/**
 * This class implements the functionality for the Draw Panel
 * @author Franziska Blumenschein, Tobias Renner
 *
 */
class DrawPanel extends JPanel implements MouseListener{	 
	    private int xp= -10;
	    private int yp= -10;
	    private int xp1= -10;
	    private int yp1= -10;
	    private int xp2= -10;
	    private int yp2= -10;
	    private int id;
	    private int x_new, y_new, x1_new, y1_new, x2_new, y2_new;
	    private int x1new, y1new, x2new, y2new;
	    static String modus ="";
	    boolean moved =false;
	    
	    /**
	     * Constructor to create the Draw Panel
	     */
	    DrawPanel(){
	    	this.setBounds(265, 40, 658, 443);
	    	this.setBackground(new Color (182,174,161));
	    	this.setVisible(true);
	    this.addMouseListener(this);
	    }
	    /**
	     * This function sets the Modus to interact with the Panel
	     * 
	     */
	    public void setModus(String modus){
	    	this.modus=modus;
	    }
	    
	    @Override
	    /**
	     * Enables the user different interactions with the draw panel depending on the modus when the 
	     * repaint() method is called, like drawing points or lines, selecting or moving an object.
	     */
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	      if(modus=="draw"){
	    	  //Draws all points from the onPanelPoint list
	    	  for(Point p: aldercroftGUI.onPanelPoint){
	    		  xp = p.getX();
	    		  yp = p.getY();
	    		  g.drawOval(xp, yp, 5, 5);
	    	  }
	    	  //Draws all lines from the onPanelLine list
	    	  for(Line l:aldercroftGUI.onPanelLine){
	    		  xp1 = l.getX1();
	    		  yp1 = l.getY1();
	    		  xp2 = l.getX2();
	    		  yp2 = l.getY2();
	    		  g.drawLine(xp1, yp1, xp2, yp2);
	    	  }

	      }
	      else if(modus=="point"){
	    	  //Transfers the points from the selPoi list to the onPanelPoint list to draw them
	    	 for(Point p: aldercroftGUI.selPoi){
	    	  id = p.getID();
	    	  xp = p.getX();
			  yp = p.getY();
			  aldercroftGUI.onPanelPoint.add(new Point(id, xp, yp));
			  
	    	 }
	    	 //Draws the points from onPanelPoint
	    	 for(Point p: aldercroftGUI.onPanelPoint){
	    		  xp = p.getX();
	    		  yp = p.getY();
	    		  g.drawOval(xp, yp, 5, 5);
	    	  }
	    	 //Draws the lines from onPanelLine
	    	 for(Line l:aldercroftGUI.onPanelLine){
	    		  xp1 = l.getX1();
	    		  yp1 = l.getY1();
	    		  xp2 = l.getX2();
	    		  yp2 = l.getY2();
	    		  g.drawLine(xp1, yp1, xp2, yp2);
	    	  }
	      }
	      else if(modus=="line"){
	    	//Transfers the lines from the selLin list to the onPanelLine list to draw them
		      for(Line l: aldercroftGUI.selLin){
		    	  id = l.getID();
		    	  xp1 = l.getX1();
				  yp1 = l.getY1();
				  xp2 = l.getX2();
				  yp2 = l.getY2();
				  aldercroftGUI.onPanelLine.add(new Line(id, xp1, yp1, xp2, yp2));
				  
		      	}
		    //Draws the lines from onPanelLine
		      for(Line l:aldercroftGUI.onPanelLine){
	    		  xp1 = l.getX1();
	    		  yp1 = l.getY1();
	    		  xp2 = l.getX2();
	    		  yp2 = l.getY2();
	    		  g.drawLine(xp1, yp1, xp2, yp2);
	    	  }
		    //Draws the points from onPanelPoint
		      for(Point p: aldercroftGUI.onPanelPoint){
	    		  xp = p.getX();
	    		  yp = p.getY();
	    		  g.drawOval(xp, yp, 5, 5);
	    	  }
		      }
	      else if(modus=="select"){
	    	  //selects objects and stores them in aselection list
	    	  aldercroftGUI.selLin.clear();
	    	  aldercroftGUI.selPoi.clear();
	    	  //check if an object is near (+-5Pixel) to the click location and adds them to the list
	    	  for(Point p:aldercroftGUI.onPanelPoint){
	    		  
	    		  if(p.getX()<=x_new+5 && p.getX()>=x_new-5){
	    			  if(p.getY()<=y_new+5 && p.getY()>=y_new-5){
	    				  id = p.getID();
	    				  xp = p.getX();
			    		  yp = p.getY();
	    				  aldercroftGUI.selPoi.add(new Point(id, p.getX(),p.getY()));
	    				  aldercroftGUI.type_selected="Points";
	    				  
	    			  }
	    		  }
	    		  //Sets the color of the selected object to red and draws all objects on Panel again
	    		  if(aldercroftGUI.selPoi.size()>0){
	    		  
	    		  for(Point p2:aldercroftGUI.onPanelPoint){
	    			  xp = p2.getX();
		    		  yp = p2.getY();
	    			  g.setColor(Color.black); 
		    		  g.drawOval(xp, yp, 5, 5); 
		    		  }
	    		  
	    		  for(Point p3:aldercroftGUI.selPoi){
	    			  xp = p3.getX();
		    		  yp = p3.getY();
	    			  g.setColor(Color.red);
	    			  g.drawOval(xp, yp, 5, 5); 
	    		  }
		    		  aldercroftGUI.tableDB=aldercroftGUImethods.openSelectPointTable(aldercroftGUI.selPoi);
		    		  aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		    				aldercroftGUImethods.mouseListener_selectPoint(evt);
		    				}
		    	    	});
		    		    aldercroftGUI.txtpnTableName.setText("Selected Points");
		    		    aldercroftGUI.mntmCreate.setEnabled(false);
		    		    aldercroftGUI.mntmDelete.setEnabled(false);
		    		    aldercroftGUI.btnDrawLine.setEnabled(false);
		    		    aldercroftGUI.btnDrawPoint.setEnabled(true);
		    		    aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	    		 
	    		  }
	    		  //if no objects are selected
	    		  else {
	    			  for(Point p2:aldercroftGUI.onPanelPoint){
	    				  g.setColor(Color.black);
	    				  g.drawOval(p2.getX(), p2.getY(), 5, 5);
	    			  } 
	    		  }
	    	  }
	    	//check if an object is near (+-5Pixel) to the click location and adds them to the list
	    	  for(Line l:aldercroftGUI.onPanelLine){
	    		  //checks starting point
	    		  if(l.getX1()<=x_new+5 && l.getX1()>=x_new-5){
	    			  if(l.getY1()<=y_new+5&& l.getY1()>=y_new-5){
	    				  id = l.getID();
	    				  aldercroftGUI.selLin.add(new Line(id, l.getX1(),l.getY1(), l.getX2(),l.getY2()));
	    				  aldercroftGUI.type_selected="Lines";
	    			  }
	    		  }
	    		  //checks ending point
	    		  else if(l.getX2()<=x_new+5 && l.getX2()>=x_new-5){
	    			  if(l.getY2()<=y_new+5 && l.getY2()>=y_new-5){
	    				  id = l.getID();
	    				  aldercroftGUI.selLin.add(new Line(id, l.getX1(),l.getY1(), l.getX2(),l.getY2()));
	    				  aldercroftGUI.type_selected="Lines";
	    			  }
	    		  }
	    		  //Sets the color of the selected object to red and draws all objects on Panel again
	    		  if(aldercroftGUI.selLin.size()>0){
	    		  
	    		  for(Line l2:aldercroftGUI.onPanelLine){
	    			  g.setColor(Color.black);
	    			  g.drawLine(l2.getX1(),l2.getY1(), l2.getX2(),l2.getY2());
	    			  }
	    		  for(Line l3:aldercroftGUI.selLin){
					  g.setColor(Color.red);
					  g.drawLine(l3.getX1(),l3.getY1(), l3.getX2(),l3.getY2());
	    		  }
		    		  
		    		  aldercroftGUI.tableDB=aldercroftGUImethods.openSelectLineTable(aldercroftGUI.selLin);
		    		  aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		    				aldercroftGUImethods.mouseListener_selectLine(evt);
		    				}
		    	    	});
		    		    aldercroftGUI.txtpnTableName.setText("Selected Lines");
		    		    aldercroftGUI.mntmCreate.setEnabled(false);
		    		    aldercroftGUI.mntmDelete.setEnabled(false);
		    		    aldercroftGUI.btnDrawLine.setEnabled(true);
		    		    aldercroftGUI.btnDrawPoint.setEnabled(false);
		    		    aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	    		 
	    	  }
	    		  //if nothing was selected
	    		  else{
	    			  for(Line l2:aldercroftGUI.onPanelLine){
	    				  g.setColor(Color.black);
	    				  g.drawLine(l2.getX1(),l2.getY1(), l2.getX2(),l2.getY2());
	    			  }
	    		  }}
	    	  
	      }
	      
	      
	      else if(modus=="table_point_select"){
	    	  if(aldercroftGUI.selPoi.size()>0){
	    		  for(Line l:aldercroftGUI.onPanelLine){
		    		  xp1 = l.getX1();
		    		  yp1 = l.getY1();
		    		  xp2 = l.getX2();
		    		  yp2 = l.getY2();
		    		  g.drawLine(xp1, yp1, xp2, yp2);
		    	  }
	    		  for(Point p3:aldercroftGUI.selPoi){
	    			  id=p3.getID();
	    		  for(Point p2:aldercroftGUI.onPanelPoint){
	    			  xp = p2.getX();
		    		  yp = p2.getY();
	    			  if(p2.getID()==id)
	    				  g.setColor(Color.red);
	    			  else
	    				  g.setColor(Color.black); 
		    		  g.drawOval(xp, yp, 5, 5);
		    		  
	    		  }
	    		  }}	  
	      }
	      
	      else if(modus=="table_line_select"){
	    	  if(aldercroftGUI.selLin.size()>0){
	    		  for(Point p: aldercroftGUI.onPanelPoint){
		    		  xp = p.getX();
		    		  yp = p.getY();
		    		  g.drawOval(xp, yp, 5, 5);
		    	  }
	    		  for(Line l3:aldercroftGUI.selLin){
	    			  id=l3.getID();
	    		  for(Line l2:aldercroftGUI.onPanelLine){
	    			  if(l2.getID()==id)
	    				  g.setColor(Color.red);
	    			  else
	    				  g.setColor(Color.black);
		    		  g.drawLine(l2.getX1(),l2.getY1(), l2.getX2(),l2.getY2());
		    		  
	    		  }
	    		  }
	    	  }
	      }
	      
	      
	      //Point selection with a rectangle
	      else if(modus=="point_select"){
	    	  aldercroftGUI.selLin.clear();
	    	  aldercroftGUI.selPoi.clear();
	    	  g.setColor(Color.blue);
	    	  //Draws rectangle with the onClick (x1_new) and onRelease(x2_new) event
	    	  g.drawRect(x1_new,y1_new,x2_new-x1_new,y2_new-y1_new);
	    	  //checks which points are inside the rectangle and adds them to the selPoi list
	    	  for(Point p:aldercroftGUI.onPanelPoint){
	    		  
	    		  if(p.getX()<=x2_new && p.getX()>=x1_new){
	    			  if(p.getY()<=y2_new && p.getY()>=y1_new){
	    				  id = p.getID();
	    				  xp = p.getX();
			    		  yp = p.getY();
	    				  aldercroftGUI.selPoi.add(new Point(id, p.getX(),p.getY()));
	    				  aldercroftGUI.type_selected="Points";
	    				  
	    			  }
	    		  }
	    		  //sets the color of the selected objects to red and draws them
	    		  if(aldercroftGUI.selPoi.size()>0){
	    		  for(Point p3:aldercroftGUI.onPanelPoint){
	    			  xp = p3.getX();
		    		  yp = p3.getY();
	    			  id=p3.getID();
	    		  for(Point p2:aldercroftGUI.selPoi){
	    			  if(p2.getID()==id)
	    			  {
	    				  g.setColor(Color.red);
	    			  	  break;
	    			  }
	    			  else
	    				  g.setColor(Color.black); 
	    		  }
		    		  g.drawOval(xp, yp, 5, 5);
	    		  }
		    		  aldercroftGUI.tableDB=aldercroftGUImethods.openSelectPointTable(aldercroftGUI.selPoi);
		    		  aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		    				aldercroftGUImethods.mouseListener_selectPoint(evt);
		    				}
		    	    	});
		    		    aldercroftGUI.txtpnTableName.setText("Selected Points");
		    		    aldercroftGUI.mntmCreate.setEnabled(false);
		    		    aldercroftGUI.mntmDelete.setEnabled(false);
		    		    aldercroftGUI.btnDrawLine.setEnabled(false);
		    		    aldercroftGUI.btnDrawPoint.setEnabled(true);
		    		    aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	    		  
	    		  }
	    		  //if nothing was selected
	    		  else {
	    			  for(Point p2:aldercroftGUI.onPanelPoint){
	    				  g.setColor(Color.black);
	    				  g.drawOval(p2.getX(), p2.getY(), 5, 5);
	    			  } 
	    		  }
	    	  }}
	      //Line selection with a rectangle
	      else if(modus=="line_select"){
	    	  aldercroftGUI.selLin.clear();
	    	  aldercroftGUI.selPoi.clear();
	    	  g.setColor(Color.blue);
	    	  //Draws rectangle with the onClick and onRelease event
	    	  g.drawRect(x1_new,y1_new,x2_new-x1_new,y2_new-y1_new);
	    	//checks which lines are inside the rectangle and adds them to the selLin list
	      for(Line l:aldercroftGUI.onPanelLine){
    		  
    		  if(l.getX1()<=x2_new && l.getX1()>=x1_new){
    			  if(l.getY1()<=y2_new&& l.getY1()>=y1_new){
    				  if(l.getX2()<=x2_new+5 && l.getX2()>=x1_new-5){
    					  if(l.getY2()<=y2_new+5 && l.getY2()>=y1_new-5){
    				  
    				  id = l.getID();
    				  aldercroftGUI.selLin.add(new Line(id, l.getX1(),l.getY1(), l.getX2(),l.getY2()));
    				  aldercroftGUI.type_selected="Lines";
    			  }}}
    		  }
    		  else if(l.getX2()<=x2_new+5 && l.getX2()>=x1_new-5){
    			  if(l.getY2()<=y2_new+5 && l.getY2()>=y1_new-5){
    				  if(l.getX1()<=x2_new && l.getX1()>=x1_new){
    	    			  if(l.getY1()<=y2_new&& l.getY1()>=y1_new){
    				  id = l.getID();
    				  aldercroftGUI.selLin.add(new Line(id, l.getX1(),l.getY1(), l.getX2(),l.getY2()));
    				  aldercroftGUI.type_selected="Lines";
    			  }}}
    		  }
    		  //Sets the color for the selected objects to red and draws them
    		  if(aldercroftGUI.selLin.size()>0){
    		  for(Line l3:aldercroftGUI.onPanelLine){
    			  id=l3.getID();
    			  for(Line l2:aldercroftGUI.selLin){
    				  if(l2.getID()==id)
    				  {
    					  g.setColor(Color.red);
    					  break;
    				  }
    			  else
    				  g.setColor(Color.black);
    			  }
    		  
	    		  g.drawLine(l3.getX1(),l3.getY1(), l3.getX2(),l3.getY2());
    		  }
	    		  aldercroftGUI.tableDB=aldercroftGUImethods.openSelectLineTable(aldercroftGUI.selLin);
	    		  aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
	    				aldercroftGUImethods.mouseListener_selectLine(evt);
	    				}
	    	    	});
	    		    aldercroftGUI.txtpnTableName.setText("Selected Lines");
	    		    aldercroftGUI.mntmCreate.setEnabled(false);
	    		    aldercroftGUI.mntmDelete.setEnabled(false);
	    		    aldercroftGUI.btnDrawLine.setEnabled(true);
	    		    aldercroftGUI.btnDrawPoint.setEnabled(false);
	    		    aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
    		  
    	  }
    		  //If nothing was selected
    		  else{
    			  for(Line l2:aldercroftGUI.onPanelLine){
    				  g.setColor(Color.black);
    				  g.drawLine(l2.getX1(),l2.getY1(), l2.getX2(),l2.getY2());
    			  }
    		  }}
    	  
      }
	      //Functionality to move objects
	      else if(modus=="move"){
	    	 
	    	  if(moved==true){
	    		  //Checks if a point is near the clicked position
	    	  for(Point p:aldercroftGUI.onPanelPoint){
	    		  if(p.getX()<=x1_new+5 && p.getX()>=x1_new-5){
	    			  if(p.getY()<=y1_new+5 && p.getY()>=y1_new-5){
	    				  id = p.getID();
	    				  //Sets the new coordinates from the Release event to move the point
	    				  p.setX(x2_new);
			    		  p.setY(y2_new);
	    				  
	    				  aldercroftGUI.type_selected="Points";	 
	    				  //changes the same point in the points list
	    				  for(Point p4:aldercroftGUI.points){
				  			  if(p4.getID()==id){
				  				  p4.setX(x2_new);
				  				  p4.setY(y2_new);
				  			 }  }
				  			//Updates the table
				  			aldercroftGUI.tableDB=aldercroftGUImethods.openPointTable(aldercroftGUI.points);
				  			aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
				  				aldercroftGUImethods.mouseListener_selectPoint(evt);
				  				}
				  	    	});
			    		    aldercroftGUI.txtpnTableName.setText("Points");
			    		    aldercroftGUI.type_selected="Points";
			    		    aldercroftGUI.mntmCreate.setEnabled(true);
			    		    aldercroftGUI.mntmDelete.setEnabled(true);
			    		    aldercroftGUI.btnDrawLine.setEnabled(false);
			    		    aldercroftGUI.btnDrawPoint.setEnabled(true);
			    		    aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);	    		   		      		  
	    	  }}  }	    	
	    	  //Checks if a line is near the clicked position
  		    for(Line l:aldercroftGUI.onPanelLine){
  		    	//Checks the first point
  		    	if(l.getX1()<=x1new+5 && l.getX1()>=x1new-5){
  		    		if(l.getY1()<=y1new+5&& l.getY1()>=y1new-5){
  		    			id = l.getID();
  		    			//Sets the new coordinates from the Release event
  		    			l.setX1(x2new);
  		    			l.setY1(y2new);
	    				
	    				aldercroftGUI.type_selected="Lines";
	    				//Changes the same point in the Lines list
	    				for(Line l4:aldercroftGUI.lines){
    						if(l4.getID()==id){    	    						
    	    							l4.setX1(x2new);
    	    							l4.setY1(y2new);
    	    													}
	    			}
	    				//Updates the table
	    				aldercroftGUI.tableDB=aldercroftGUImethods.openLineTable(aldercroftGUI.lines);
	    				aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
	    					aldercroftGUImethods.mouseListener_selectLine(evt);
	    					}
	    		    	});
			    		  aldercroftGUI.txtpnTableName.setText("Lines");
			    		  aldercroftGUI.type_selected="Lines";
			    		  aldercroftGUI.mntmCreate.setEnabled(true);
			    		  aldercroftGUI.mntmDelete.setEnabled(true);
			    		  aldercroftGUI.btnDrawLine.setEnabled(true);
			    		  aldercroftGUI.btnDrawPoint.setEnabled(false);
			    		  aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	    		}
  		    	}
  		    	//Checks the ending point 
	    		else if(l.getX2()<=x1new+5 && l.getX2()>=x1new-5){
	    			if(l.getY2()<=y1new+5 && l.getY2()>=y1new-5){
	    				id = l.getID();
	    				//Sets new coordinates from Release event
	    				l.setX2(x2new);
  		    			l.setY2(y2new);
	    				aldercroftGUI.type_selected="Lines";
	    				//Changes the same point in the lines list
	    				for(Line l4:aldercroftGUI.lines){
    						if(l4.getID()==id){
    	    							l4.setX2(x2new);
    	    							l4.setY2(y2new);
    	    						}
    							}
	    				//Updates the table
	    				aldercroftGUI.tableDB=aldercroftGUImethods.openLineTable(aldercroftGUI.lines);
	    				aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
	    					aldercroftGUImethods.mouseListener_selectLine(evt);
	    					}
	    		    	});
			    		  aldercroftGUI.txtpnTableName.setText("Lines");
			    		  aldercroftGUI.type_selected="Lines";
			    		  aldercroftGUI.mntmCreate.setEnabled(true);
			    		  aldercroftGUI.mntmDelete.setEnabled(true);
			    		  aldercroftGUI.btnDrawLine.setEnabled(true);
			    		  aldercroftGUI.btnDrawPoint.setEnabled(false);
			    		  aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);	    				
	    			}
	    		}	  }  
	    	  }
		  super.paintComponent(g);
		  //Paints the points from the onPanelPoint list
	      for(Point p1: aldercroftGUI.onPanelPoint){
	    		  xp = p1.getX();
	    		  yp = p1.getY();
	    		  g.drawOval(xp, yp, 5, 5);
	    	  }
	      //Draws all the lines from the onPanelLine list
	    	  for(Line l6:aldercroftGUI.onPanelLine){
	    		  xp1 = l6.getX1();
	    		  yp1 = l6.getY1();
	    		  xp2 = l6.getX2();
	    		  yp2 = l6.getY2();
	    		  g.drawLine(xp1, yp1, xp2, yp2);
	    	  }  	 
	    	  
  		    }   
	    }
	 
	 
	   @Override
	   //MouseEvent to get the clicked coordinates in the image coordinate system
	       public void mouseClicked(MouseEvent evt){
	            x_new = evt.getX();
	            y_new = evt.getY();
	            //Draws the point if modus "draw" is selected
	            if(modus=="draw")
	            	aldercroftGUImethods.doAddPoint(x_new, y_new);
	            
	            repaint();
	       }

		@Override 
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		//MouseEvent to get the coordinates of the Mouse when it is pressed
		public void mousePressed(MouseEvent evt) {
			x1_new = evt.getX();
            y1_new = evt.getY();
			
		}

		@Override
		//MouseEvent to get the coordinates of the Mouse when it is released
		public void mouseReleased(MouseEvent evt) {
			x2_new = evt.getX();
            y2_new = evt.getY();
            //Draws a line if the modus is "draw"
            if(modus=="draw")
            	aldercroftGUImethods.doAddLine(x1_new, y1_new, x2_new,y2_new); 
            //Allows  the user to move an object if the modus is "move"
            if(modus=="move"){
            	moved = true;
            	x1new = x1_new;
            	y1new = y1_new;
            	x2new = x2_new;
            	y2new = y2_new;}
            repaint();
		}
		
}
