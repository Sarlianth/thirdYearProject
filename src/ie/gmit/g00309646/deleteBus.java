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
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class deleteBus extends JFrame {

	private JPanel contentPane;
	private static JComboBox comboBox = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					deleteBus frame = new deleteBus(false);
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
	public deleteBus(boolean if_admin) {		
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Delete bus");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 122);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ArrayList<String> buses = new ArrayList<String>();
		String bus;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
			Statement stmt = conn.createStatement();
			
			String strSelect = "select * from bus_table";
	 
	        ResultSet rset = stmt.executeQuery(strSelect);
	 
        	while(rset.next()){
        		
        		int id = rset.getInt("bus_id");
        		String name = rset.getString("bus_number");
        		String from = rset.getString("depart_from");
        		String to = rset.getString("going_to");
        		String time = rset.getString("bus_time");
        		
        		bus = id + " - " + name + " - " + from  + " - " + to + " - " + time;
        		
        		buses.add(bus);
        	}
        	
        	comboBox.setModel(new DefaultComboBoxModel(buses.toArray()));

	      } catch(SQLException ex) {
	    	  ex.printStackTrace();
	      }
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mainWindow frame = new mainWindow(if_admin);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				finish();

			}
		});
		btnBack.setBounds(165, 59, 80, 23);
		contentPane.add(btnBack);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selected = comboBox.getSelectedItem().toString();
				String[] part = selected.split(" - ");
				
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the following bus? \n " + selected,"Warning", dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){

					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
						Statement stmt = conn.createStatement();
						
						String strSelect = "delete from bus_table where bus_id like " + part[0];
				 
				        stmt.executeUpdate(strSelect);
				        
				        mainWindow frame = new mainWindow(if_admin);
				        frame.setLocationRelativeTo(null);
				        frame.setVisible(true);
				        finish();

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      } // end of try/catch
				} // if option yes		
			} // end of delete button event handler
		});
		
		btnDelete.setBounds(32, 59, 80, 23);
		contentPane.add(btnDelete);
		
		comboBox.setBounds(10, 11, 258, 23);
		contentPane.add(comboBox);
	}
	
	public void finish(){
		this.dispose();
	}
}
