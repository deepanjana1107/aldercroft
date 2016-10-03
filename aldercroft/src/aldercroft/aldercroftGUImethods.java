package aldercroft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import com.opencsv.*;

/**
 * This class implement the functionality behind the aldercroftGUI.java interface. It also connects the GUI with
 * the functionality of the DrawPanel.java.
 * @author Franziska Blumenschein, Tobias Renner
 * 
 *
 */

public class aldercroftGUImethods {
	
	public static void doExit() {
		 //System.exit(0);
		
	}

	/**
	 * This function sets the content of the Help section.
	 * @author Balmus, Cristina
	 */
	public static void showHelp() {
		JTextArea textArea=new JTextArea("The Lines&Points GUI is structured as follows: " +
				"\n - Database table tree; \n - Table content panel; \n - Canvas. \n When the program opens, you are already connected to the database.\n" +
				"\n Q: How do I open a table?\n A: Just click on it and it will open." +
				"\n\n Q: How do I see existing points/lines?\n A: Simply select the entries in the table by holding the Ctrl or Shift keys and then use the 'Draw Point'/'Draw Line' buttons \n at the bottom. " +
				"Your objects will be drawn on the canvas." +
				"\n\n Q: How do I export selected entries in the table?\n A: Use the 'Export selected' button at the bottom of the table." +
				"\n\n Q: How do I draw or edit objects on the canvas?\n A: For this you have the button menus on top of the canvas. Use these to select objects displayed on the canvas, \n move them or edit them. " +
				"\n\n Q: Can I select objects on the canvas that I want to export?\n A: Use the 'Select point objects'/'Select line objects' buttons on top of the canvas." +
				"\n\n Q: Which format is my exported files?\n A: Objects are exported as .csv." +
				"\n\n Q: What file format do I need for import?\n A: You can only import .csv files." +
				"\n\n Q: How can I clear the canvas?\n A: Use the 'Clear screen' button on top of the canvas." +
				"\n\n Q: How do I save all objects drawn on the canvas? \n A: Use the 'Save' button on top of the canvas." +
				"\n\n Q: Can I add/edit/delete rows inside the table? \n Yes. For this use the table menu on top of the table.");
		JScrollPane scrollPane = new JScrollPane(textArea);  
		scrollPane.setPreferredSize(new Dimension(680,500));
		JOptionPane.showMessageDialog(null, scrollPane, "Help - Lines and Points GUI", JOptionPane.QUESTION_MESSAGE);
		}
	
	/**
	 * This function exports either points or lines to a .csv file
	 * @author Boie, Alexander
	 */
	public static void doExport() {
		int rowCnt;
		String loc = "";
		String loc2 = "";
		//Initialize export dialog.
		final JFileChooser exportDialog = new JFileChooser();
		int userSelection = exportDialog.showSaveDialog(null);
		exportDialog.setDialogTitle("Save");
		//Create new file for exported data, set file type to csv.
		if(userSelection == JFileChooser.APPROVE_OPTION){
			loc=exportDialog.getSelectedFile().getAbsolutePath();
			if (!loc.endsWith(".csv")){
				loc2 = loc + ".csv";
			}
			else{
				loc2 = loc;
			}
			//determine which table, lines(default) or point, should be exported.
			try{	
			FileWriter writer = new FileWriter(loc2);
			//write table to new file (if lines).
			if(aldercroftGUI.selTable==1){
				rowCnt=aldercroftGUI.lines.size();
				for (int i=0;i<rowCnt;i++){			
					writer.append(aldercroftGUI.lines.get(i).getX1()+",");
					writer.append(aldercroftGUI.lines.get(i).getY1()+",");
					writer.append(aldercroftGUI.lines.get(i).getX2()+",");
					writer.append(aldercroftGUI.lines.get(i).getY2()+",");
					writer.append('\n');
			}}
			//write table to new file (if points).
			else{
				rowCnt=aldercroftGUI.points.size();
				for (int i=0;i<rowCnt;i++){
					writer.append(aldercroftGUI.points.get(i).getX()+",");
					writer.append(aldercroftGUI.points.get(i).getY()+",");
					writer.append('\n');
			}}	
			//close file writer.
			writer.flush();
			writer.close();
			}
			catch(IOException e){
				e.printStackTrace();
	}}}		 

	/**
	 * This function imports a .csv file and appends the data to the existing tables.
	 * @author Boie, Alexander
	 */
	public static void doImport() {
		String loc = "";
		File loc2;
		String impRow[] = null;
		//Initialize export dialog.
		final JFileChooser importDialog = new JFileChooser();
		int userSelection = importDialog.showOpenDialog(null);
		importDialog.setDialogTitle("Import");
		//Throw error if selected file is not csv format.
		if(userSelection == JFileChooser.APPROVE_OPTION){
			loc=importDialog.getSelectedFile().getAbsolutePath();
			if (!loc.endsWith(".csv")){
				JOptionPane.showMessageDialog(null, "File type must be .csv");				
			}
			//Get location of file to be imported.
			else{
				loc2=importDialog.getSelectedFile();
				//create new openCSV reader and initaize the import.
				try {
					CSVReader csvReader = new CSVReader(new FileReader(loc2));
					try {
						while((impRow=csvReader.readNext()) !=null){
							//get column count from csv. used to determine if impoorting points or lines. 
							int columnCount=impRow.length;
							System.out.println(columnCount);
							//import points
							if(columnCount==5){ 
								int j = new Integer(impRow[0]);
								int k = new Integer(impRow[1]);
								int l = new Integer(impRow[2]);
								int m = new Integer(impRow[3]);
								doAddLine(j,k,l,m);
							}
							//import points
							else{
								int n= new Integer(impRow[0]);
								int o = new Integer(impRow[1]);
								doAddPoint(n,o);
					}}}
					catch (IOException e) {
						e.printStackTrace();
				}}
				catch (FileNotFoundException e) {
					e.printStackTrace();	
	}}}}
			

	/**
	*This function sets the Modus to "line" to allow the user to draw a line
	*/
	public static void drawLine() {
		
		aldercroftGUI.panelPaint.setModus("line");
		aldercroftGUI.panelPaint.repaint();
		}
	
	/**
	*This function sets the Modus to "point" to allow the user to draw a point
	*/
	public static void drawPoint() {
		
		aldercroftGUI.panelPaint.setModus("point");
		aldercroftGUI.panelPaint.repaint();
	}
	
	/**
	*This function sets the Modus to "select" to allow the user to select objects
	*/
	public static void doSelect() {
		aldercroftGUI.panelPaint.setModus("select");
		System.out.println(aldercroftGUI.panelPaint.modus);
		
	}
	
	/**
	*This function sets the Modus to "draw" to draw selected objects
	*/
	public static void doPaint() {
		aldercroftGUI.panelPaint.setModus("draw");
		aldercroftGUI.panelPaint.repaint();
	}

	/**
	 * This function sets the content of the About section.
	 * @author Balmus, Cristina
	 */
	public static void showAbout() {
	JOptionPane.showMessageDialog(null, "Authors:" +
			"\n - Balmus, Cristina - Graphical Designer\n - Blumenschein, Franziska - Developer\n - Boie, Alex - Project Manager\n - Majumdar, Deepanjana - Tester\n - Renner, Tobias - Developer" +
			"\n\n The 'Lines and points GUI' Program was developed for the Software Engineering/Programming lecture at the " +
			"\n Hochschule Karlsruhe Technik und Wirtschaft - HsKA." +
			"\n January, 2015", null, JOptionPane.INFORMATION_MESSAGE, null);
	}

	
/**
*This function creates a new JTable and fills it with data from the point list.
*@return Returns a table with the point objects
*@param The Input Parameter is a point list which provides the data for the table
*/

	public static JTable openPointTable(List<Point> point) {
		 aldercroftGUI.mntmCreate.setEnabled(true);
		 aldercroftGUI.mntmDelete.setEnabled(true);
		int size_points = point.size();
		int size_points2 = aldercroftGUI.points.size();
		final Integer[][] rowData = new Integer[size_points+1][3];
		//Reads Data from the points list
		for (int i = 0; i < size_points;i++){
			rowData[i][0] = point.get(i).getID();
			rowData[i][1] = point.get(i).getX();
			rowData[i][2] = point.get(i).getY();
		
				}
		// Adds a row with zeros at the end of the table
		int idx;
		if (size_points2>=1)
			idx = aldercroftGUI.points.get(size_points2-1).getID();
		else
			idx = 0;
		int j = size_points;
		rowData[j][0] = idx+1;
		rowData[j][1] = 0;
		rowData[j][2] = 0;
		
		String[] columnNames = {"ID", "X", "Y"};
		//New JTable with the defined Data and Names
		final JTable tableDB = new JTable(rowData, columnNames)
	    {  
			// return the value of the selected position
			public Integer getValueAt(int row, int col) { 
	            return rowData[row][col]; 
	        }
			
			@Override
			//checks if the selected value is editable
            public boolean isCellEditable(int row, int col)  
            {  
            	if (col == 0) {
            	      return false;
            	   }  else {
            	      return true;
            	   } 
            }  
            @Override
            //sets the value of a selected position
            public void setValueAt(Object aValue, int row, int col)
            {
                
                if(1 == col) {
                	rowData[row][col] =  Integer.parseInt((String) aValue);
                    
                }
                else if(2 == col) {
                	rowData[row][col] = Integer.parseInt((String) aValue);
                }
               
            }
                       
        };  

        
       
		tableDB.setBounds(5, 160, 257, 290);
		tableDB.setBackground(new Color (246,246,246));
		tableDB.setShowHorizontalLines(true);
		tableDB.setOpaque(true);
		tableDB.setFillsViewportHeight(true);
		
		return tableDB;
	}

	/**
	*This function creates a new JTable and fills it with data from the selected-point list.
	*@return Returns a table with the selected point objects
	*@param The Input Parameter is a point list which provides the data for the table
	*/
	public static JTable openSelectPointTable(List<Point> point) {
		int size_points = point.size();
		
		final Integer[][] rowData = new Integer[size_points][3];
		//Reads Data from the point list
		for (int i = 0; i < size_points;i++){
			rowData[i][0] = point.get(i).getID();
			rowData[i][1] = point.get(i).getX();
			rowData[i][2] = point.get(i).getY();
		
				}
				
		String[] columnNames = {"ID", "X", "Y"};
		//New JTable with the defined Data and Names
		final JTable tableDB = new JTable(rowData, columnNames)
	    {  
			
			public Integer getValueAt(int row, int col) { 
	            return rowData[row][col]; 
	        }
			
			@Override		    	
            public boolean isCellEditable(int row, int col)  
            {  
            	if (col == 0) {
            	      return false;
            	   }  else {
            	      return true;
            	   } 
            }  
            @Override
            //Sets the value of a selected position
            public void setValueAt(Object aValue, int row, int col)
            {
                
                if(1 == col) {
                	rowData[row][col] =  Integer.parseInt((String) aValue);
                    
                }
                else if(2 == col) {
                	rowData[row][col] = Integer.parseInt((String) aValue);
                }
               
            }
                       
        };  

        
       
		tableDB.setBounds(5, 160, 257, 290);
		tableDB.setBackground(new Color (246,246,246));
		tableDB.setShowHorizontalLines(true);
		tableDB.setOpaque(true);
		tableDB.setFillsViewportHeight(true);
		
		return tableDB;
	}
	
	/**
	*This function creates a new JTable and fills it with data from the line list.
	**@return Returns a table with the line objects
	*@param The Input Parameter is a line list which provides the data for the table
	*/	
	public static JTable openLineTable(List<Line> line) {
		 aldercroftGUI.mntmCreate.setEnabled(true);
		 aldercroftGUI.mntmDelete.setEnabled(true);
			int size_lines = line.size();
			int size_lines2 = aldercroftGUI.lines.size();
			final Integer[][] rowData = new Integer[size_lines+1][5];
			//Reads Data from the line list
			for (int i = 0; i < size_lines;i++){
				rowData[i][0] = line.get(i).getID();
				rowData[i][1] = line.get(i).getX1();
				rowData[i][2] = line.get(i).getY1();
				rowData[i][3] = line.get(i).getX2();
				rowData[i][4] = line.get(i).getY2();
			
					}
			int idx;
			if (size_lines2>=1)
				idx = aldercroftGUI.lines.get(size_lines2-1).getID();
			else
				idx = 0;
			// Adds a row with zeros at the end of the table
			int j = size_lines;
			rowData[j][0] = idx+1;
			rowData[j][1] = 0;
			rowData[j][2] = 0;
			rowData[j][3] = 0;
			rowData[j][4] = 0;
			String[] columnNames = {"ID", "X1", "Y1","X2", "Y2"};
			//New JTable with the defined Data and Names
			final JTable tableDB = new JTable(rowData, columnNames)
		    {  
				public Integer getValueAt(int row, int col) { 
		            return rowData[row][col]; 
		        }
				
				@Override		    	
	            public boolean isCellEditable(int row, int col)  
	            {  
	            	if (col == 0) {
	            	      return false;
	            	   }  else {
	            	      return true;
	            	   } 
	            }  
	            @Override
	            //sets the value of a selected position
	            public void setValueAt(Object aValue, int row, int col)
	            {
	                if(1 == col) {
	                	rowData[row][col] =  Integer.parseInt((String) aValue);
	                    
	                }
	                else if(2 == col) {
	                	rowData[row][col] = Integer.parseInt((String) aValue);
	                }
	                else if(3 == col) {
	                	rowData[row][col] = Integer.parseInt((String) aValue);
	                }
	                else if(4 == col) {
	                	rowData[row][col] = Integer.parseInt((String) aValue);
	                }
	                  }	            	            

	        };  
	        	
			tableDB.setBounds(5, 160, 257, 290);
			tableDB.setBackground(new Color (246,246,246));
			tableDB.setShowHorizontalLines(true);
			tableDB.setOpaque(true);
			tableDB.setFillsViewportHeight(true);
			tableDB.setRowSelectionAllowed(true);  
			tableDB.setColumnSelectionAllowed(false); 
			tableDB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			return tableDB;
		}
	/**
	*This function creates a new JTable and fills it with data from the selected line list.
	**@return Returns a table with the selected line objects
	*@param The Input Parameter is a line list which provides the data for the table
	*/
	public static JTable openSelectLineTable(List<Line> line) {
		int size_lines = line.size();
		
		final Integer[][] rowData = new Integer[size_lines][5];
		//Reads Data from the line list
		for (int i = 0; i < size_lines;i++){
			rowData[i][0] = line.get(i).getID();
			rowData[i][1] = line.get(i).getX1();
			rowData[i][2] = line.get(i).getY1();
			rowData[i][3] = line.get(i).getX2();
			rowData[i][4] = line.get(i).getY2();
		
				}
		
		String[] columnNames = {"ID", "X1", "Y1","X2", "Y2"};
		
		//New JTable with the defined Data and Names
		final JTable tableDB = new JTable(rowData, columnNames)
	    {  
			public Integer getValueAt(int row, int col) { 
	            return rowData[row][col]; 
	        }
			
			@Override		 
			
            public boolean isCellEditable(int row, int col)  
            {  
            	if (col == 0) {
            	      return false;
            	   }  else {
            	      return true;
            	   } 
            }  
            @Override
            public void setValueAt(Object aValue, int row, int col)
            {
                if(1 == col) {
                	rowData[row][col] =  Integer.parseInt((String) aValue);
                    
                }
                else if(2 == col) {
                	rowData[row][col] = Integer.parseInt((String) aValue);
                }
                else if(3 == col) {
                	rowData[row][col] = Integer.parseInt((String) aValue);
                }
                else if(4 == col) {
                	rowData[row][col] = Integer.parseInt((String) aValue);
                }
                  }	            	            

        };  
        	
		tableDB.setBounds(5, 160, 257, 290);
		tableDB.setBackground(new Color (246,246,246));
		tableDB.setShowHorizontalLines(true);
		tableDB.setOpaque(true);
		tableDB.setFillsViewportHeight(true);
		tableDB.setRowSelectionAllowed(true);  
		tableDB.setColumnSelectionAllowed(false); 
		tableDB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return tableDB;
	}
	
		
	/**
	*This function sets the Modus to "move" to allow the user to move an object
	*/	
	public static void doMove() {
		aldercroftGUI.panelPaint.setModus("move");
		aldercroftGUI.panelPaint.repaint();		
	}

	/**
	*This function sets the Modus to "line_select" to allow the user to select a line
	*/
	public static void doLineSelect() {
		aldercroftGUI.panelPaint.setModus("line_select");
		aldercroftGUI.panelPaint.repaint();
	}
	/**
	*This function sets the Modus to "point_select" to allow the user to select a point
	*/
	public static void doPointSelect() {
		aldercroftGUI.panelPaint.setModus("point_select");
		aldercroftGUI.panelPaint.repaint();
		
	}
	/**
	*This function stores all objects from the local lists in the Database
	*/
	public static void doSaveAll() throws SQLException{
		aldercroftGUI.s.executeUpdate("DROP TABLE Point");
		aldercroftGUI.s.executeUpdate("CREATE TABLE Point (ID AUTOINCREMENT  , IDX integer NOT NULL, IDY integer NOT NULL, PRIMARY KEY(ID), FOREIGN KEY (IDX) REFERENCES XTable (ID), FOREIGN KEY (IDY) REFERENCES YTable (ID))");
		
		for(Point p:aldercroftGUI.points){
			int x= p.getX();
			int y= p.getY();
			System.out.println(x);
		ResultSet rs = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM XTable WHERE XValue="+x+";");
		int x_exists=0;
		int idx=0;
		while(rs.next()){
			x_exists = rs.getInt(1);}
		if(x_exists==1){
			ResultSet rs2 = aldercroftGUI.s.executeQuery("SELECT ID FROM XTable WHERE XValue="+x+";");
			while(rs2.next())
				idx = rs2.getInt(1);	
		}
		else if(x_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO XTable (XValue) VALUES ("+x+");");
			ResultSet rs3 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs3.next()){
				idx = rs3.getInt(1);
				}
		}
		ResultSet rs4 = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM YTable WHERE YValue="+y+";");
		int y_exists=0;
		int idy=0;
		while(rs4.next()){
			y_exists = rs4.getInt(1);}
		if(y_exists==1){
			ResultSet rs5 = aldercroftGUI.s.executeQuery("SELECT ID FROM YTable WHERE YValue="+y+";");
			while(rs5.next())
				idy = rs5.getInt(1);	
		}
		else if(y_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO YTable(YValue) VALUES ("+y+");");
			ResultSet rs6 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs6.next()){
				idy = rs6.getInt(1);}
		}
		
		aldercroftGUI.s.executeUpdate("INSERT INTO Point(IDX,IDY) VALUES ("+idx+","+idy+");");
		System.out.println("Point inserted: "+x+ ", "+y);
	}
	
		aldercroftGUI.s.executeUpdate("DROP TABLE Line");
		aldercroftGUI.s.executeUpdate("CREATE TABLE Line (ID AUTOINCREMENT  , IDX1 integer NOT NULL, IDY1 integer NOT NULL, IDX2 integer NOT NULL, IDY2 integer NOT NULL, PRIMARY KEY(ID), FOREIGN KEY (IDX1) REFERENCES XTable (ID), FOREIGN KEY (IDY1) REFERENCES YTable (ID), FOREIGN KEY (IDX2) REFERENCES XTable (ID),  FOREIGN KEY (IDY2) REFERENCES YTable (ID))");
		
		for(Line l:aldercroftGUI.lines){
			int x1= l.getX1();
			int y1= l.getY1();
			int x2= l.getX2();
			int y2= l.getY2();
			System.out.println(x1);
		ResultSet rs = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM XTable WHERE XValue="+x1+";");
		int x_exists=0;
		int idx1=0;
		while(rs.next()){
			x_exists = rs.getInt(1);}
		if(x_exists==1){
			ResultSet rs2 = aldercroftGUI.s.executeQuery("SELECT ID FROM XTable WHERE XValue="+x1+";");
			while(rs2.next())
				idx1 = rs2.getInt(1);	
		}
		else if(x_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO XTable (XValue) VALUES ("+x1+");");
			ResultSet rs3 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs3.next()){
				idx1 = rs3.getInt(1);
				}
		}
		ResultSet rs4 = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM XTable WHERE XValue="+x2+";");
		int x2_exists=0;
		int idx2=0;
		while(rs4.next()){
			x2_exists = rs4.getInt(1);}
		if(x2_exists==1){
			ResultSet rs5 = aldercroftGUI.s.executeQuery("SELECT ID FROM XTable WHERE XValue="+x2+";");
			while(rs5.next())
				idx2 = rs5.getInt(1);	
		}
		else if(x2_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO XTable (XValue) VALUES ("+x2+");");
			ResultSet rs6 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs6.next()){
				idx2 = rs6.getInt(1);
				}
		}
		ResultSet rs7 = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM YTable WHERE YValue="+y1+";");
		int y_exists=0;
		int idy1=0;
		while(rs7.next()){
			y_exists = rs7.getInt(1);}
		if(y_exists==1){
			ResultSet rs8 = aldercroftGUI.s.executeQuery("SELECT ID FROM YTable WHERE YValue="+y1+";");
			while(rs8.next())
				idy1 = rs8.getInt(1);	
		}
		else if(y_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO YTable(YValue) VALUES ("+y1+");");
			ResultSet rs9 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs9.next()){
				idy1 = rs9.getInt(1);}
		}
		
		ResultSet rs10 = aldercroftGUI.s.executeQuery("SELECT COUNT(ID) FROM YTable WHERE YValue="+y2+";");
		int y2_exists=0;
		int idy2=0;
		while(rs10.next()){
			y2_exists = rs10.getInt(1);}
		if(y2_exists==1){
			ResultSet rs11 = aldercroftGUI.s.executeQuery("SELECT ID FROM YTable WHERE YValue="+y2+";");
			while(rs11.next())
				idy2 = rs11.getInt(1);	
		}
		else if(y2_exists==0){
			aldercroftGUI.s.executeUpdate("INSERT INTO YTable (YValue) VALUES ("+y2+");");
			ResultSet rs12 = aldercroftGUI.s.executeQuery("SELECT @@Identity");
			while(rs12.next()){
				idy2 = rs12.getInt(1);
				}
		}
		
		aldercroftGUI.s.executeUpdate("INSERT INTO Line(IDX1,IDY1,IDX2,IDY2) VALUES ("+idx1+","+idy1+","+idx2+","+idy2+");");
		System.out.println("Line inserted: "+x1+ ", "+y1+", "+ x2+ ", "+y2);
	}
		
	}
	/**
	*This function adds a new point object to table and the lists
	*/
	public static void doAddPoint(int x, int y){
		//Determines the ID of the new point
		int size = aldercroftGUI.points.size();
		int id =1;
		if(size>0)
		 id = aldercroftGUI.points.get(size-1).getID()+1;

		//Adds the point to the points list
		aldercroftGUI.points.add(new Point(id,x,y));
		//Adds the point to the onPanelPoint list
		aldercroftGUI.onPanelPoint.add(new Point(id, x, y));  
		//Refreshes the table to get the new point
		aldercroftGUI.tableDB=aldercroftGUImethods.openPointTable(aldercroftGUI.points);
		aldercroftGUI.type_selected="Points";
		aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
			aldercroftGUImethods.mouseListener_selectPoint(evt);
			}
    	});
		aldercroftGUI.mntmCreate.setEnabled(true);
		aldercroftGUI.mntmDelete.setEnabled(true);
		aldercroftGUI.txtpnTableName.setText("Points");
		aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	
	}
	/**
	*This function adds a new line object to table and the lists
	*/
	public static void doAddLine(int x1, int y1, int x2, int y2){
		//Determines the ID of the new line
		if(x1!=x2&&y1!=y2){
		int size = aldercroftGUI.lines.size();
		int id = 1;
		if(size>0)
			id = aldercroftGUI.lines.get(size-1).getID()+1;

		//Adds the new line to the lines list
		aldercroftGUI.lines.add(new Line(id,x1,y1,x2,y2));
		//Adds the new line to the onPanelLine list
		aldercroftGUI.onPanelLine.add(new Line(id,x1,y1,x2,y2));
		//Refreshes the table to get the new line
		aldercroftGUI.tableDB=aldercroftGUImethods.openLineTable(aldercroftGUI.lines);
		aldercroftGUI.type_selected="Lines";
		aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		aldercroftGUImethods.mouseListener_selectLine(evt);		        	        
    	}
    	});
		aldercroftGUI.txtpnTableName.setText("Lines");
		aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
		}
	}
	
	/**
	*This function adds an object which was insert in the table to the lists
	*/		
	public static void doAddRow(String type_selected) {
		//Adding a line object
		System.out.println(type_selected);
		if(type_selected =="Lines"){
			//Gets the values out of the last table row
			int row  = aldercroftGUI.lines.size();
			int id=(int) aldercroftGUI.tableDB.getValueAt(row,0);
			int x1=(int) aldercroftGUI.tableDB.getValueAt(row,1);
			int y1=(int) aldercroftGUI.tableDB.getValueAt(row,2);
			int x2=(int) aldercroftGUI.tableDB.getValueAt(row,3);
			int y2=(int) aldercroftGUI.tableDB.getValueAt(row,4);
			//Add new object to the list with the determined values
			aldercroftGUI.lines.add(new Line(id,x1,y1,x2,y2));
			//Refreshes the table to get a new input-row
			aldercroftGUI.tableDB=aldercroftGUImethods.openLineTable(aldercroftGUI.lines);
			
			aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
			aldercroftGUImethods.mouseListener_selectLine(evt);		        	        
        	}
        	});
    	System.out.println("Row added");
    	
		}
		//Adding a point object
    	else if(type_selected =="Points"){
			//Gets the values out of the last table row
    		int row= aldercroftGUI.points.size();
    		System.out.println("Selected row: "+aldercroftGUI.txtpnTableName.getText());
    		int id=(int) aldercroftGUI.tableDB.getValueAt(row,0);
    		int x1=(int) aldercroftGUI.tableDB.getValueAt(row,1);
    		int y1=(int) aldercroftGUI.tableDB.getValueAt(row,2);
			//Add new object to the list with the determined values
    	aldercroftGUI.points.add(new Point(id,x1,y1));
		//Refreshes the table to get a new input-row
    	aldercroftGUI.tableDB=aldercroftGUImethods.openPointTable(aldercroftGUI.points);
    	
    	aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
   			aldercroftGUImethods.mouseListener_selectPoint(evt);
   			}
        	});
    	System.out.println("Row added");
    	}
		aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	}

	/**
	*This function edits an object in the table and updates the lists
	*/	
	public static void doEditRow(String type_selected) {
		//Editing a line object
		if(type_selected=="Lines"){
			//Gets selected rows
			int[] selection = aldercroftGUI.tableDB.getSelectedRows();
			for(int j:selection)
				System.out.println("Selected rows: "+j);
			//Gets values of the selected rows
			for(int i:selection){
				int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
				int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
				int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
				int x2=(int) aldercroftGUI.tableDB.getValueAt(i,3);
				int y2=(int) aldercroftGUI.tableDB.getValueAt(i,4);
				System.out.println("Selected rows "+i);
				//Searches for the changed line in the lines list and updates its values
				for(Line l:aldercroftGUI.lines){
					if(id==l.getID()){
						l.setX1(x1);
						l.setY1(y1);
						l.setX2(x2);	
						l.setY2(y2);
					}
				}
				//Searches for the changed line in the selected lines list and updates its values
				for(Line l:aldercroftGUI.selLin){
					if(id==l.getID()){
						l.setX1(x1);
						l.setY1(y1);
						l.setX2(x2);	
						l.setY2(y2);
					}
				}
				//Searches for the changed line in the onPanelLine list and updates its values
				for(Line l:aldercroftGUI.onPanelLine){
					if(id==l.getID()){
						l.setX1(x1);
						l.setY1(y1);
						l.setX2(x2);	
						l.setY2(y2);
					}
				}
		}
		}
		//Editing a line object
		else if(type_selected=="Points"){
			//Gets selected rows
			int[] selection = aldercroftGUI.tableDB.getSelectedRows();
			
			//Gets values of selected rows
			for(int i:selection){
				int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
				int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
				int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
				System.out.println("Selected rows "+i);
				//Searches for the changed point in the points list and updates its values
				for(Point p:aldercroftGUI.points){
					if(p.getID()==id){
				p.setX(x1);
				p.setY(y1);
				}	}
				//Searches for the changed point in the selected points list and updates its values
				for(Point p:aldercroftGUI.selPoi){
					if(p.getID()==id){
				p.setX(x1);
				p.setY(y1);
				}	}
				//Searches for the changed point in the onPanelPoint list and updates its values
				for(Point p:aldercroftGUI.onPanelPoint){
					if(p.getID()==id){
				p.setX(x1);
				p.setY(y1);
				}	}
    	System.out.println("Selection length "+selection.length);
    	
    	}
		}
		aldercroftGUI.panelPaint.setModus("draw");
		aldercroftGUI.panelPaint.repaint();
	}	
	
	/**
	*This function gets the selected rows of the table and stores the associated objects in the selected object lists
	*@return Returns an Integer Array of with the selected rows in the Point table
	*/	
	public static int[] mouseListener_selectPoint(MouseEvent evt) {
		//Get selected rows
		int[] selection = aldercroftGUI.tableDB.getSelectedRows();
		//clear selPoi list
			if(selection.length==1)
				aldercroftGUI.selPoi.clear();
		//Stores the values of the selected rows in the selPoi List
		for(int i:selection){
    	int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
    	int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
    	int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
    	if(aldercroftGUI.selPoi.size()>0){
    		boolean exists = false;
    		for(Point p:aldercroftGUI.selPoi){
    			
    			if(p.getID()==id){
    				exists = true;
    				break;
    			}  
    			}
    		if (exists == false)
				aldercroftGUI.selPoi.add(new Point(id,x1,y1));
			
    	}
    	else
    		aldercroftGUI.selPoi.add(new Point(id,x1,y1));
    	
    	System.out.println("Row"+i);
    	for(Point l:aldercroftGUI.selPoi)
		System.out.println(l.getID() +" "+  l.getX() +" "+ l.getY());
    	System.out.println("Length selcted rows"+selection.length);
    	
    	}
		aldercroft.DrawPanel.modus="table_point_select";
		aldercroftGUI.panelPaint.repaint();
			return selection;
	}
	
	/**
	*This function gets the selected rows of the table and stores the associated objects in the selected object lists
	*@return Returns an Integer Array of with the selected rows in the Line table
	*/	
	public static int[] mouseListener_selectLine(MouseEvent evt) {
		
		//Get selected row
		int[] selection = aldercroftGUI.tableDB.getSelectedRows();
		//Clear selLin list
			if(selection.length==1)
				aldercroftGUI.selLin.clear();
			
			//Stores the values of the selected rows in the selLin List
			for(int i:selection){
				int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
				int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
				int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
				int x2=(int) aldercroftGUI.tableDB.getValueAt(i,3);
				int y2=(int) aldercroftGUI.tableDB.getValueAt(i,4);
				if(aldercroftGUI.selLin.size()>0){
					boolean exists = false;
					for(Line l:aldercroftGUI.selLin){
						if(l.getID()==id){
							exists = true;
							break;
							}	    		
		    	}
				if (exists == false)
	    			aldercroftGUI.selLin.add(new Line(id,x1,y1,x2,y2));
				}
				else
					aldercroftGUI.selLin.add(new Line(id,x1,y1,x2,y2));
				System.out.println("Selected row"+i);
				for(Line l:aldercroftGUI.selLin)
					System.out.println(l.getID() +" "+  l.getX1() +" "+ l.getY1() +" "+  l.getX2() +" "+ l.getY2() + " ");
			}
			aldercroft.DrawPanel.modus="table_line_select";
			aldercroftGUI.panelPaint.repaint();
			return selection;
	}

	/**
	*This function gets the selected rows of the table and deletes the associated objects in the table and the lists
	*/	
	public static void doDeleteRow(String type_selected) {
		
		//Delete lines
		if(type_selected=="Lines"){
			//Clears the temporary help lists
			aldercroftGUI.line_id.clear();
			aldercroftGUI.line_help.clear();
			//Gets selected rows
			int[] selection = aldercroftGUI.tableDB.getSelectedRows();
			int count = 0;
			int pos =0;
			for(int j:selection)
				System.out.println("Selected rows: "+j);
			//Stores the values of the selected rows in the line_help list and deletes them in the lines list
			for(int i:selection){
				//Is necessary because after the first delete the row numbers change
				i = i-count;
				int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
				int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
				int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
				int x2=(int) aldercroftGUI.tableDB.getValueAt(i,3);
				int y2=(int) aldercroftGUI.tableDB.getValueAt(i,4);
				aldercroftGUI.line_help.add(new Line(id,x1,y1,x2,y2));
				System.out.println("Deleted rows "+i);
				aldercroftGUI.lines.remove(i);
				count++;				
		}
		pos = 0;
		//checks if some deleted lines (line_help) are on the panel (onPanelLine)
		for(Line l:aldercroftGUI.onPanelLine){
			for(Line l2:aldercroftGUI.line_help){
				if(l.getID()==l2.getID())
					//Stores the row number of the onPanelList in the line_id list
					aldercroftGUI.line_id.add(pos);
				}
			pos++;
		}
		count=0;
		//deletes the objects from the panel
		for(int i:aldercroftGUI.line_id){
			aldercroftGUI.onPanelLine.remove(i);
			count++;
		}	
		//Refreshes the table
		aldercroftGUI.tableDB=aldercroftGUImethods.openLineTable(aldercroftGUI.lines);
    	aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
			aldercroftGUImethods.mouseListener_selectLine(evt);		        	        
        	}
        	});
	}
		
		//Delete Points
		else if(type_selected=="Points"){
			aldercroftGUI.points_id.clear();
			aldercroftGUI.points_help.clear();
			//Get selected rows
			int[] selection = aldercroftGUI.tableDB.getSelectedRows();
			int count = 0;
			int pos =0;
			
			//Stores the values of the selected rows in the points_help list and deletes them in the points list
			for(int i:selection){
				//Is necessary because after the first delete the row numbers change
				i = i-count;
				int id=(int) aldercroftGUI.tableDB.getValueAt(i,0);
				int x1=(int) aldercroftGUI.tableDB.getValueAt(i,1);
				int y1=(int) aldercroftGUI.tableDB.getValueAt(i,2);
				aldercroftGUI.points_help.add(new Point(id,x1,y1));
				System.out.println("Deleted rows "+i);
				aldercroftGUI.points.remove(i);
				count++;
					
    	System.out.println("Selection length "+selection.length);    	
    	}
			pos = 0;
			//checks if some deleted points (points_help) are on the panel (onPanelPoint)
			for(Point p:aldercroftGUI.onPanelPoint){
				for(Point p2:aldercroftGUI.points_help){
					if(p.getID()==p2.getID()){
						//Stores the row number of the onPanelList in the points_id list
						aldercroftGUI.points_id.add(pos);
						System.out.println("Position "+pos);}
					}
				pos++;
			}
			count=0;
			//Deletes the points from the panel
			for(int i:aldercroftGUI.points_id){
				System.out.println("Position id "+i);
				aldercroftGUI.onPanelPoint.remove(i);
				count++;
			}
			//Refreshes the table
			aldercroftGUI.tableDB=aldercroftGUImethods.openPointTable(aldercroftGUI.points);
	    	aldercroftGUI.tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
	   			aldercroftGUImethods.mouseListener_selectPoint(evt);
	   			}
	        	});
		}
		//Repaints the panel
		aldercroftGUI.panelPaint.setModus("draw");
		aldercroftGUI.panelPaint.repaint();
		aldercroftGUI.scrollPane.setViewportView(aldercroftGUI.tableDB);
	
	}	
	/**
	*This function removes all objects from the panel
	*/		
public static void doClearPanel(){
	//Sets Modus for the DrawPanel and repaint it
	aldercroftGUI.panelPaint.setModus("");
	aldercroftGUI.panelPaint.repaint();
	//Clears the onPanel lists
	aldercroftGUI.onPanelPoint.clear();
	aldercroftGUI.onPanelLine.clear();
	}
	
/**
 * This function exports selected points or lines to a .csv file
 * @author Boie, Alexander
 */
	public static void doExportSelected() {
		int rowCnt;
		String loc = "";
		String loc2 = "";
		//Initialize export selected dialog.
		final JFileChooser exportDialog = new JFileChooser();
		int userSelection = exportDialog.showSaveDialog(null);
		exportDialog.setDialogTitle("Save");
		//Create new file for exported data, set file type to csv.
		if(userSelection == JFileChooser.APPROVE_OPTION){
			loc=exportDialog.getSelectedFile().getAbsolutePath();
			if (!loc.endsWith(".csv")){
				loc2 = loc + ".csv";
		}}
		else{
			loc2 = loc;
		}
		//determine which table, selected lines(default) or selected point, should be exported.
		try{	
			FileWriter writer = new FileWriter(loc2);
			//write table to new file (if selected lines).
			if(aldercroftGUI.selTable==1){
				rowCnt=aldercroftGUI.selLin.size();
				for (int i=0;i<rowCnt;i++){		
					System.out.println(aldercroftGUI.selLin);
					writer.append(aldercroftGUI.selLin.get(i).getX1()+",");
					writer.append(aldercroftGUI.selLin.get(i).getY1()+",");
					writer.append(aldercroftGUI.selLin.get(i).getX2()+",");
					writer.append(aldercroftGUI.selLin.get(i).getY2()+",");
					writer.append('\n');
			}}
			//write table to new file (if selected points).
			else{
				rowCnt=aldercroftGUI.selPoi.size();
				for (int i=0;i<rowCnt;i++){
					writer.append(aldercroftGUI.selPoi.get(i).getX()+",");
					writer.append(aldercroftGUI.selPoi.get(i).getY()+",");
					writer.append('\n');
			}}
			//close file writer.
			writer.flush();
			writer.close();
			}
			catch(IOException e){
				e.printStackTrace();		
		 }}}
