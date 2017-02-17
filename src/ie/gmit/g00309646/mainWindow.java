package ie.gmit.g00309646;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class mainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ArrayList<String> sourceStations = new ArrayList<String>();
	private static ArrayList<String> destinationStations = new ArrayList<String>();
	private JComboBox comboBox = new JComboBox();
	private JComboBox comboBox_1 = new JComboBox();
	private static JLabel lblAvailableBus = new JLabel("Available bus: ");
	private static ArrayList<String> availableBuses = new ArrayList<String>();
	private static JComboBox comboBox_2 = new JComboBox();
	private static JTabbedPane tabbedPane;
	private static JPanel panel_2 = new JPanel();
	private static JPanel panel_1 = new JPanel();
	private static JPanel panel_3 = new JPanel();
	private static JPanel panel = new JPanel();
	private JButton btnSubmit = new JButton("Search");
	private JButton button = new JButton("Logout");
	private JButton btnSelect = new JButton("Select");
	private JLabel lblNewLabel = new JLabel("Source station:");
	private	JLabel lblDestination = new JLabel("Destination:");
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//main
	

	/**
	 * Create the frame.
	 */
	public mainWindow(boolean if_admin) {
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("IrishBusApp");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 343);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//////////////////////////////////////////////
		///////SETUP TABBED PANE AND PANELS//////////
		/////////////////////////////////////////////
		UIManager.put("TabbedPane.selected", Color.getHSBColor(300,150,200));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBackground(Color.DARK_GRAY);
		tabbedPane.setBounds(10, 11, 549, 295);
		contentPane.add(tabbedPane);
		
		tabbedPane.addTab("Reservation", panel);
		tabbedPane.addTab("Bus management", panel_1);
		tabbedPane.addTab("Admin panel", panel_2);
		tabbedPane.addTab("Tickets", panel_3);
		
		panel.setLayout(null);
		panel_1.setLayout(null);
		panel_2.setLayout(null);
		panel_3.setLayout(null);
		
		panel.setBackground(Color.DARK_GRAY);
		panel_1.setBackground(Color.DARK_GRAY);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 45, 524, 211);
		
		panel_1.add(scrollPane);
		refreshBuses();
		
		JButton btnAddBus = new JButton("Add");
		btnAddBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBus frame = new addBus(if_admin);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				finish();
			}
		});
		btnAddBus.setBounds(10, 11, 89, 23);
		panel_1.add(btnAddBus);
		
		JButton btnDeleteBus = new JButton("Delete");
		btnDeleteBus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the bus? \n ","Warning", dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){

					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "delete from bus_table where bus_id like " + table.getValueAt(table.getSelectedRow(), 0);
				 
				        stmt.executeUpdate(strSelect);
				        
				        refreshBuses();

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      } // end of try/catch
				}
			}
		});
		btnDeleteBus.setBounds(109, 11, 89, 23);
		panel_1.add(btnDeleteBus);
		
		JButton btnUpdateBus = new JButton("Update ");
		btnUpdateBus.setBounds(208, 11, 89, 23);
		panel_1.add(btnUpdateBus);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				refreshBuses();
				
			}
		});
		btnRefresh.setBounds(445, 11, 89, 23);
		panel_1.add(btnRefresh);
	
		panel_2.setBackground(Color.DARK_GRAY);
		panel_3.setBackground(Color.DARK_GRAY);
		
		//if current user does not have administrator privileges disable the admin panel tab 
		if(!if_admin){
			tabbedPane.setEnabledAt(2, false);
		}
		
		//get the bus stations from database and populate them into array lists
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select depart_from, going_to from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
	        while(rset.next()) {
	        	if(!sourceStations.contains(rset.getString("depart_from"))){
	        		sourceStations.add(rset.getString("depart_from"));
	        	}
	        	if(!destinationStations.contains(rset.getString("going_to"))){
	        		destinationStations.add(rset.getString("going_to"));
	        	}
	        }
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	    }
		
		
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 26, 91, 19);
		panel.add(lblNewLabel);
	
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(239, 28, 79, 14);
		panel.add(lblDestination);
		
		comboBox.setBounds(111, 25, 118, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(sourceStations.toArray()));
		
		comboBox_1.setBounds(313, 25, 118, 20);
		panel.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(destinationStations.toArray()));
		
		btnSubmit.setBounds(114, 61, 317, 23);
		panel.add(btnSubmit);
		lblAvailableBus.setForeground(Color.WHITE);
		
		lblAvailableBus.setBounds(10, 96, 91, 19);
		panel.add(lblAvailableBus);
		
		comboBox_2.setBounds(114, 95, 317, 20);
		panel.add(comboBox_2);

		btnSelect.setBounds(114, 123, 317, 23);
		panel.add(btnSelect);
		
		button.setBounds(569, 11, 88, 295);
		contentPane.add(button);
		
		//////////////////////////////////////////////
		///////LOGOUT BUTTON ACTION LISTENER/////////
		/////////////////////////////////////////////
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
				login frame = new login();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	
		//////////////////////////////////////////////
		///////SUBMIT BUTTON ACTION LISTENER/////////
		/////////////////////////////////////////////
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				availableBuses.clear();
				
				if(!comboBox.getSelectedItem().equals(comboBox_1.getSelectedItem())){
					System.out.println(comboBox.getSelectedItem() + "  " + comboBox_1.getSelectedItem());
					
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "select * from bus_table where depart_from LIKE '" + comboBox.getSelectedItem() + "' and going_to LIKE '" + comboBox_1.getSelectedItem() + "'";
				 
				        ResultSet rset = stmt.executeQuery(strSelect);
				 
			        	while(rset.next()) {
				        	
				        	int tempID = rset.getInt("bus_id");
				        	String tempBusNo = rset.getString("bus_number");
				        	String tempDepartFrom = rset.getString("depart_from");
				        	String tempGoingTo = rset.getString("going_to");
				        	String tempBusTime = rset.getString("bus_time");
				        	
				        	String availableBus = tempBusNo + ": " + tempDepartFrom + " to " + tempGoingTo + " @ " + tempBusTime;
				        	availableBuses.add(availableBus);

				        	comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
				        }  
			        	

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
					
				}
				else{
					
					System.out.println("Wrong! Try again..");
					availableBuses.clear();
					comboBox_2.setModel(new DefaultComboBoxModel(availableBuses.toArray()));
					
				}
				
			}
		});
    }
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}
	
	//////////////////////////////////////////////
	///////METHOD TO REFRESH THE BUSES TABLE//////
	/////////////////////////////////////////////
	public void refreshBuses(){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	table = new JTable(buildTableModel(rset));
	        scrollPane.setViewportView(table);
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	       // table.setEnabled(false);
	        table.setForeground(Color.WHITE);
			table.setBorder(null);
			table.setBackground(Color.GRAY);
			table.setBounds(10, 11, 192, 63);

	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	public void finish(){
		this.setVisible(false);
		this.dispose();
	}
}
