package ie.gmit.g00309646;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class updateBus extends JFrame {

	private JPanel contentPane;
	private JTextField busIDField;
	private JTextField sourceField;
	private JTextField destinationField;
	private static JComboBox comboBox = new JComboBox();
	private static JComboBox comboBox_1 = new JComboBox();
	private static JComboBox comboBox_2 = new JComboBox();
	private String tempName;
	private String tempFrom;
	private String tempTo;
	private String tempTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					updateBus frame = new updateBus(false, 1);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public updateBus(boolean if_admin, int key) {
		ArrayList<Integer> hours = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++){
		   hours.add(i);
		}
		
		ArrayList<Integer> mins = new ArrayList<Integer>();
		for (int i = 0; i <= 59; i++){
		   mins.add(i);
		}
		
		String[] choice = {"AM", "PM"};
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Update bus - "+key);
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 240);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
			}
		});
		
		btnBack.setBounds(172, 134, 80, 23);
		contentPane.add(btnBack);
		
		JLabel lblBusIdname = new JLabel("Bus ID/Name");
		lblBusIdname.setForeground(Color.WHITE);
		lblBusIdname.setBounds(10, 13, 86, 17);
		contentPane.add(lblBusIdname);
		
		JLabel lblSourceStation = new JLabel("Source station");
		lblSourceStation.setForeground(Color.WHITE);
		lblSourceStation.setBounds(10, 44, 86, 17);
		contentPane.add(lblSourceStation);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setForeground(Color.WHITE);
		lblDestination.setBounds(10, 75, 86, 17);
		contentPane.add(lblDestination);
		
		JLabel lblTime = new JLabel("Time (HH:MM)");
		lblTime.setForeground(Color.WHITE);
		lblTime.setBounds(10, 106, 86, 17);
		contentPane.add(lblTime);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bus_number = busIDField.getText();
				String depart_from = sourceField.getText();
				String going_to = destinationField.getText();
				String bus_time = null;
				if(comboBox_1.getSelectedItem().equals(0) || comboBox_1.getSelectedItem().equals(1) || comboBox_1.getSelectedItem().equals(2) || comboBox_1.getSelectedItem().equals(3)
						|| comboBox_1.getSelectedItem().equals(4) || comboBox_1.getSelectedItem().equals(5) || comboBox_1.getSelectedItem().equals(6)
						|| comboBox_1.getSelectedItem().equals(7) || comboBox_1.getSelectedItem().equals(8) || comboBox_1.getSelectedItem().equals(9)) {
					bus_time = comboBox.getSelectedItem() + ".0" + comboBox_1.getSelectedItem() + comboBox_2.getSelectedItem().toString();
				}
				else{
					bus_time = comboBox.getSelectedItem() + "." + comboBox_1.getSelectedItem() + comboBox_2.getSelectedItem().toString();
				}
				
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
					Statement stmt = conn.createStatement();
					
					String strSelect = "update bus_table set bus_number='"+bus_number+"', depart_from='"+depart_from+"', going_to='"+going_to+"', bus_time='"+bus_time+"' where bus_id="+key;
			 
			        stmt.executeUpdate(strSelect);
			        
			        mainWindow.refreshBuses();
			        mainWindow.refreshTimetable();
			        mainWindow.populate();
			        finish();

			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      } // end of try/catch
			} //actionPerformed
		});
		btnUpdate.setBounds(48, 134, 80, 23);
		contentPane.add(btnUpdate);
		
		sourceField = new JTextField();
		sourceField.setColumns(10);
		sourceField.setBounds(106, 42, 162, 23);
		contentPane.add(sourceField);
		
		destinationField = new JTextField();
		destinationField.setColumns(10);
		destinationField.setBounds(106, 73, 162, 23);
		contentPane.add(destinationField);
		
		busIDField = new JTextField("");
		busIDField.setBounds(106, 11, 162, 23);
		contentPane.add(busIDField);
		busIDField.setColumns(10);
		
		comboBox.setModel(new DefaultComboBoxModel(hours.toArray()));
		comboBox.setBounds(106, 104, 50, 20);
		contentPane.add(comboBox);
		
		comboBox_1.setModel(new DefaultComboBoxModel(mins.toArray()));
		comboBox_1.setBounds(162, 104, 50, 20);
		contentPane.add(comboBox_1);
		
		comboBox_2 = new JComboBox(choice);
		comboBox_2.setBounds(218, 104, 52, 20);
		contentPane.add(comboBox_2);
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table where bus_id="+key;
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	while(rset.next()) {
	        	
	        	int tempID = rset.getInt("bus_id");
	        	busIDField.setText(rset.getString("bus_number"));
	    		sourceField.setText(rset.getString("depart_from"));
	    		destinationField.setText(rset.getString("going_to"));
	    		tempTime = rset.getString("bus_time");
	    			
	    		String min;
	    		String[] time = tempTime.split("\\.");
	    		//time[0] <-- this is out hour
	    		String timeChoice = time[0].substring(Math.max(time[0].length() - 2, 0)); // <-- This is AM / PM depending on whatever is in database for given bus
	
	    		if(time[1].length() == 4){
	    			min = time[1].substring(0, 2); 
	    		}
	    		else{
	    			min = time[1].substring(0, 1);
	    		}
	    		
	    		comboBox.setSelectedItem(Integer.parseInt(time[0]));
	    		comboBox_1.setSelectedItem(Integer.parseInt(min));
	    		if(timeChoice.equalsIgnoreCase("am")){
	    			comboBox_2.setSelectedItem("AM");
	    		}
	    		else{
	    			comboBox_2.setSelectedItem("PM");
	    		}
	        }  
        	
	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
	}
	
	public void finish(){
		this.dispose();
	}
}