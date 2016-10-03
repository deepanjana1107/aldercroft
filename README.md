Authors:Alex Boie, Cristina Balmus, Deepanjana Majumdar, Franziska Blumenschein & Tobias Renner  

About:
This project entitled Aldercroft, offers a Java Graphical User Interface that allows users to have an interactive 
experience with plotting points and lines and saving their actions in a database. Aldercroft, additionally, offers
the functionality to load and store points and lines database information in CSV format, they can also be stored 
selectively.  

Installation: 
This project was built and runs in a Java Eclipse environment. In order to install the program, create a workspace 
in eclipse. Extract the ‘Aldercroft’ folder from the ‘.zip’ file, and import the project to the workspace. Once the 
import is complete, a reference library needs to be connected. The file for this library is the ‘opencsv-3.1.jar’ 
file located in the main ‘Aldercroft’ folder. The library can be connected in the 
‘Project>Properties>Java Build Path>Libraries>Add External JARs’ dialog. 

Use: 
The project can be run by initializing the ‘aldercroftGUI.java’ file. 

Usage Notes: 
This program uses an internal database to permanently store data. Data is loaded from this database every time the 
program is started. Upon starting the program the database data is copied to JTables. Any changes (edits, additions, 
deletions, imports) are made only to the data stored in the JTables. To make these changes permanent and push them 
to the database, you need to save your changes.  If you neglect to save your changes, they will not be reflected the 
next time you open the program.

To edit rows in the table, you need to follow these steps. Click on ‘Edit row’ (initializes the editing process), 
edit the values in the row, then click on ‘Edit row’ again (stops the editing process). 
