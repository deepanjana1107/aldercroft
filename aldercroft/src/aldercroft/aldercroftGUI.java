package aldercroft;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


/**
 * 
 * aldercroftGUI implements the GUI of the aldercroft program
 * @author Deepanjana (GUI implmentation), Cristina (GUI design)
 *
 */

public class aldercroftGUI extends JFrame  {

	//Declaration of the local lists to store the objects and the changes
	/**
	 * Stores the imported point objects
	 */
	static List<Point> points = new ArrayList<Point>();
	/**
	 * Stores the imported line objects
	 */
	static List<Line> lines = new ArrayList<Line>();
	/**
	 * Stores the selected point objects
	 */
	static List<Point> selPoi = new ArrayList<Point>();
	/**
	 * Stores the selected line objects
	 */
	static List<Line> selLin = new ArrayList<Line>();
	/**
	 * Stores the point objects which are drawn on the panel
	 */
	static List<Point> onPanelPoint = new ArrayList<Point>();
	/**
	 * Stores the line objects which are drawn on the panel
	 */
	static List<Line> onPanelLine = new ArrayList<Line>();
	/**
	 * Help-list to temporally store points
	 */
	static List<Point> points_help = new ArrayList<Point>();
	/**
	 * Help-list to temporally store lines
	 */
	static List<Line> line_help = new ArrayList<Line>();
	/**
	 * Help-list to temporally store point ID`s
	 */
	static List<Integer> points_id = new ArrayList<Integer>();
	/**
	 * Help-list to temporally store line ID`s
	 */
	static List<Integer> line_id = new ArrayList<Integer>();
	
	private static final long serialVersionUID = 1L;
	public static final Graphics g = null;
	public static String type_selected = null;
	public static int selTable = 1;
	
	private JPanel contentPane;
	static JMenuItem mntmCreate;
	static JButton btnDrawPoint;
	static JMenuItem mntmDelete;
	static JButton btnDrawLine;
	static DrawPanel panelPaint;
	static JTextPane txtpnTableName;
	static JScrollPane  scrollPane; 
	static JTable tableDB;
	static Statement s;
	protected ResultSet results, catalog, columns;
	private DefaultTreeModel model;
	private JTree treeDB = new JTree();
	protected DatabaseMetaData meta;
	protected Connection conn;
	protected String tablename;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aldercroftGUI frame = new aldercroftGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the GUI with a connection to the access database
	 * @throws SQLException 
	 */
	public aldercroftGUI() throws Exception {
		
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			File dbFile = new File("src/aldercroft/database/aldercroft.accdb");
			String path = dbFile.getAbsolutePath();
			System.out.print(path);
			conn =DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+path); 
			
			System.out.println("Connected to database");

		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		
		}
		// Runs SQL query to get point's coordinates and ids from database
		s = conn.createStatement();
		ResultSet rn = s.executeQuery("SELECT Point.ID, XTable.XValue, YTable.YValue FROM ((Point INNER JOIN XTable ON Point.IDX=XTable.ID) INNER JOIN YTable ON Point.IDY=YTable.ID);");
				
		while(rn.next()) {
		// Add all points attributes from the query above to the list points
		int list_id = Integer.parseInt(rn.getString(1)); 
		int list_x = Integer.parseInt(rn.getString(2)); 
		int list_y = Integer.parseInt(rn.getString(3)); 
		points.add( new Point(list_id, list_x, list_y));		
		}
		// Runs SQL query to get line's coordinates and ids from database
		ResultSet rm = s.executeQuery("SELECT Line.ID,XTable.XValue, YTable.YValue, XTab.XValue, YTab.YValue FROM ((((Line INNER JOIN XTable ON Line.IDX1=XTable.ID) INNER JOIN YTable ON Line.IDY1=YTable.ID) INNER JOIN XTable AS XTab ON Line.IDX2 = XTab.ID) INNER JOIN YTable AS YTab ON Line.IDY2 = YTab.ID);");
		
		while(rm.next()) {
		// Add all line attributes from the the query above to the list lines
		int list_id = Integer.parseInt(rm.getString(1)); 
		int list_x1 = Integer.parseInt(rm.getString(2)); 
		int list_y1 = Integer.parseInt(rm.getString(3)); 
		int list_x2 = Integer.parseInt(rm.getString(4)); 
		int list_y2 = Integer.parseInt(rm.getString(5));
		lines.add( new Line(list_id, list_x1, list_y1, list_x2, list_y2));		
		}
		// Prints all points to the console
		System.out.println("Point list");
		for(Point p:points)
			System.out.println(p.getID() +" "+  p.getX() +" "+ p.getY() + " ");
		//Prints all lines to the console
		System.out.println("Line list");
		for(Line l:lines)
			System.out.println(l.getID() +" "+  l.getX1() +" "+ l.getY1() +" "+  l.getX2() +" "+ l.getY2() + " ");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(aldercroftGUI.class.getResource("/aldercroft/resources/database.png")));
		setTitle("Lines & Points GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 935, 538);
		setResizable(false);
			
		JMenuBar menuBarMain = new JMenuBar();
		setJMenuBar(menuBarMain);
		
		JMenu mnFile = new JMenu("File");
		menuBarMain.add(mnFile);
			
		
		JMenuItem importFile = new JMenuItem("Import table");
		importFile.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Import2.png")));
		importFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method to import file
				aldercroftGUImethods.doImport();
			}
		});
		mnFile.add(importFile);
		
		JMenuItem exportFile = new JMenuItem("Export table");
		exportFile.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Export.png")));
		exportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method to export file
				aldercroftGUImethods.doExport();
			}
		});
		mnFile.add(exportFile);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Exit.gif")));
		exit.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				// call method to exit program
				aldercroftGUImethods.doExit();
			}
		});
		mnFile.add(exit);
		
		
		JMenu mnHelp = new JMenu("Help");
		menuBarMain.add(mnHelp);
		JMenuItem help = new JMenuItem("Show Help");
		help.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Help.png")));
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// call method to show help
				aldercroftGUImethods.showHelp();
			}
		});
		mnHelp.add(help);
		
		JMenuItem about = new JMenuItem("About");
		about.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/About.png")));
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// show about aldercroft
				aldercroftGUImethods.showAbout();
			}
		});
		mnHelp.add(about);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		treeDB.setBounds(5, 5, 257, 104);
		treeDB.setRootVisible(false);
		treeDB.setVisibleRowCount(10);
		treeDB.setBackground(new Color (246,246,246));
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		root.removeAllChildren();
        
        model.nodeChanged(root);
        treeDB.setRootVisible(true);
		treeDB.setShowsRootHandles(true);
        model.reload();
        try {
        	 // get list of all tables in the database
        	root.setUserObject("Aldercroft"); // set root node to the name of the database
		    int i = 1;
			DatabaseMetaData md = conn.getMetaData();
			String[] tables = {"TABLE"};
    		ResultSet rs = md.getTables(null,null,"%",tables);
			
			while (rs.next() && i<3) {
				// now recursively add table names under the root (db name)	
			  String helpString = rs.getString(3);
			  DefaultMutableTreeNode child = new DefaultMutableTreeNode(helpString);
		      model.insertNodeInto(child, root, root.getChildCount());
		      i++;
			}

         } catch (SQLException e1) {
			e1.printStackTrace();
		}

        treeDB.expandPath(new TreePath(root));	
		contentPane.setLayout(null);
		
		// Must show name of selected table from JTree
		txtpnTableName = new JTextPane();
		txtpnTableName.setFont(UIManager.getFont("CheckBoxMenuItem.font"));
		txtpnTableName.setText(tablename);
		txtpnTableName.setEditable(false);
		txtpnTableName.setBounds(5, 140, 257, 20);
		txtpnTableName.setBackground(new Color (246,246,246));
		contentPane.add(txtpnTableName);
		
		panelPaint = new DrawPanel();
		contentPane.add(panelPaint);
		JPanel panelCanvasToolbar = new JPanel();
		panelCanvasToolbar.setBounds(265, 5, 658, 33);
		contentPane.add(panelCanvasToolbar);
		panelCanvasToolbar.setLayout(null);
		
		JToolBar toolBarCanvas = new JToolBar();
		toolBarCanvas.setBounds(0, 0, 658, 33);
		panelCanvasToolbar.add(toolBarCanvas);
		
		//Button to select objects on canvas
		JButton btnSelect = new JButton("");
		btnSelect.setToolTipText("Select");
		btnSelect.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/SelectF4.png")));
		btnSelect.setMargin(new Insets(0, 0, 0, 0));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aldercroftGUImethods.doSelect();
			}
		});
		toolBarCanvas.add(btnSelect);
		
		//Draw points selected in JTable
				btnDrawPoint = new JButton("Draw Point");
				btnDrawPoint.setFont(UIManager.getFont("CheckBoxMenuItem.font"));
				btnDrawPoint.setBounds(105, 456, 75, 23);
				btnDrawPoint.setMargin(new Insets(0, 0, 0, 0));
				btnDrawPoint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						aldercroftGUImethods.drawPoint();						
					}
				});
				contentPane.add(btnDrawPoint);
				
				//Draw lines selected in JTable
				btnDrawLine = new JButton("Draw Line");
				btnDrawLine.setFont(UIManager.getFont("CheckBoxMenuItem.font"));
				btnDrawLine.setBounds(185, 456, 75, 23);
				btnDrawLine.setMargin(new Insets(0, 0, 0, 0));
				btnDrawLine.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						aldercroftGUImethods.drawLine();
					}
				});
				contentPane.add(btnDrawLine);
				
				
				
		treeDB.setModel(model);
		contentPane.add(treeDB);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 160, 257, 290);
		scrollPane.setName("Table");
		contentPane.add(scrollPane);
		MouseListener ml = new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        int selRow = treeDB.getRowForLocation(e.getX(), e.getY());
		        TreePath selPath = treeDB.getPathForLocation(e.getX(), e.getY());
		        //Shows line or point list in the jtable, depending on the clicked jtree item
		        if(selRow != -1) {
		        	
		            if(e.getClickCount() == 1) {
		            	int whichRow = selRow;
		            	
	            		scrollPane.setBounds(5, 160, 257, 290);
	            		contentPane.add(scrollPane);
		            	if(whichRow==1){ //Line
		            		btnDrawLine.setEnabled(true);
		               		btnDrawPoint.setEnabled(false);
		               		
		            		txtpnTableName.setText("Lines");
		            		type_selected="Lines";
		            		tableDB=aldercroftGUImethods.openLineTable(lines);
		            		tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		            			aldercroftGUImethods.mouseListener_selectLine(evt);		        	        
		        	        	}
		        	        	});
		            		
		            		scrollPane.setViewportView(tableDB);
		            		System.out.println("You single clicked " + selRow + " " +selPath);
		            		System.out.println(contentPane.getComponents());
		            		selTable = whichRow;
		            	}
		               	if(whichRow==2){ //Point
		               		btnDrawLine.setEnabled(false);
		               		btnDrawPoint.setEnabled(true);
		               		
		               		tableDB=aldercroftGUImethods.openPointTable(points);
		               		type_selected="Points";
		               		tableDB.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent evt) {
		               			aldercroftGUImethods.mouseListener_selectPoint(evt);
		               			}
		        	        	});
		               		
		            		txtpnTableName.setText("Points");
		            		scrollPane.setViewportView(tableDB);
		            				            	   
		            		System.out.println("You single clicked " + selRow + " " +selPath);
		            		System.out.println(contentPane.getComponents());
		            		System.out.println(scrollPane.getName());
		            		selTable = whichRow;
		            		}
		            }		                 
		        }
		    }
		};
		treeDB.addMouseListener(ml);
		
		//Button to move objects inside canvas
		JButton btnEdit = new JButton("");
		btnEdit.setToolTipText("Move");
		btnEdit.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/EditF.png")));
		btnEdit.setMargin(new Insets(0, 0, 0, 0));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method to move points&lines
				aldercroftGUImethods.doMove();
			}
		});
		toolBarCanvas.add(btnEdit);
		
		JButton btnDraw = new JButton("");
		btnDraw.setToolTipText("Draw");
		btnDraw.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/PencilF.png")));
		btnDraw.setMargin(new Insets(0, 0, 0, 0));
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method for Paint
				aldercroftGUImethods.doPaint();
			}
		});
		toolBarCanvas.add(btnDraw);
		
		//Button to select point objects drawn on canvas
		JButton btnAreaSelectPoint = new JButton("");
		btnAreaSelectPoint.setToolTipText("Select point objects");
		btnAreaSelectPoint.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/AreaSelect_point.png")));
		btnAreaSelectPoint.setMargin(new Insets(0, 0, 0, 0));
		btnAreaSelectPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// call method for bounding box select point
				aldercroftGUImethods.doPointSelect();
			}
		});
		toolBarCanvas.add(btnAreaSelectPoint);
		
		//Button to select line objects drawn on canvas
		JButton btnAreaSelectLine = new JButton("");
		btnAreaSelectLine.setToolTipText("Select line objects");
		btnAreaSelectLine.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/AreaSelect_line2.png")));
		btnAreaSelectLine.setMargin(new Insets(0, 0, 0, 0));
		btnAreaSelectLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method for bounding box select line
				aldercroftGUImethods.doLineSelect();
			}
		});
		toolBarCanvas.add(btnAreaSelectLine);
		
		
		//Button to save all objects drawn on canvas
		JButton btnSaveAll = new JButton("");
		btnSaveAll.setToolTipText("Save objects");
		btnSaveAll.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Save2.png")));
		btnSaveAll.setMargin(new Insets(0, 0, 0, 0));
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method for save all object on paintPanel
				try {
					aldercroftGUImethods.doSaveAll();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Button to clear objects from canvas
		JButton btnClear = new JButton("");
		btnClear.setToolTipText("Clear screen");
		btnClear.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/ClearF2.png")));
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				aldercroftGUImethods.doClearPanel();
			}
		});
		toolBarCanvas.add(btnClear);
		toolBarCanvas.add(btnSaveAll);
		
		
		//Exports points/lines selected in the JTable
		JButton btnExportSelected = new JButton("Export selected");
		btnExportSelected.setFont(UIManager.getFont("CheckBoxMenuItem.font"));
		btnExportSelected.setBounds(5, 456, 95, 23);
		btnExportSelected.setMargin(new Insets(0, 0, 0, 0));
		btnExportSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method to export selected point/lines 
				aldercroftGUImethods.doExportSelected();
			}
		});
		contentPane.add(btnExportSelected);
		
		
		JMenuBar menuBarTable = new JMenuBar();
		menuBarTable.setBounds(5, 109, 257, 30);
		contentPane.add(menuBarTable);
		
		JMenu mnTable = new JMenu("");
		mnTable.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Table.png")));
		menuBarTable.add(mnTable);
		
		mntmCreate = new JMenuItem("Add row");
		mntmCreate.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/AddRow.png")));
		mntmCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method for adding new row
				aldercroftGUImethods.doAddRow(type_selected);
				
				
			}
		});
		mnTable.add(mntmCreate);
		
		JMenuItem mntmEdit = new JMenuItem("Edit row");
		mntmEdit.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/EditRow.png")));
		mntmEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method for editing row
				aldercroftGUImethods.doEditRow(type_selected);
			}
		});
		mnTable.add(mntmEdit);
		
		mntmDelete = new JMenuItem("Delete row");
		mntmDelete.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/DeleteRow.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method to delete row
				aldercroftGUImethods.doDeleteRow(type_selected);
					}
		});
		mnTable.add(mntmDelete);
		
		JMenuItem mntmSaveEdits = new JMenuItem("Save");
		mntmSaveEdits.setIcon(new ImageIcon(aldercroftGUI.class.getResource("/aldercroft/resources/Save_Table.png")));
		mntmSaveEdits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call method for save all object on paintPanel
				try {
					aldercroftGUImethods.doSaveAll();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTable.add(mntmSaveEdits);
	} // end of constructor
}
