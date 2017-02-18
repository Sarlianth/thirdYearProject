package ie.gmit.g00309646;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addBus extends JFrame {

	private JPanel contentPane;
	private JTextField busIDField;
	private JTextField sourceField;
	private JTextField destinationField;
	private static JComboBox comboBox = new JComboBox();
	private static JComboBox comboBox_1 = new JComboBox();
	private static JComboBox comboBox_2 = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addBus frame = new addBus(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addBus(boolean if_admin) {	
		ArrayList<Integer> hours = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++){
		   hours.add(i);
		}
		
		ArrayList<Integer> mins = new ArrayList<Integer>();
		for (int i = 1; i <= 59; i++){
		   mins.add(i);
		}
		
		String[] choice = {"AM", "PM"};
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Add new bus");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 197);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.refreshBuses();
				finish();
			}
		});
		btnBack.setBounds(188, 134, 80, 23);
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
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
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
					
					String strSelect = "insert into bus_table (bus_number,depart_from,going_to,bus_time) values ('"+bus_number+"', '"+depart_from+"', '"+going_to+"', '"+bus_time+"')";
			 
			        stmt.executeUpdate(strSelect);
			        
			        mainWindow.refreshBuses();
			        finish();

			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      } // end of try/catch
			} //actionPerformed
		});
		btnAdd.setBounds(10, 134, 80, 23);
		contentPane.add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				comboBox.setSelectedItem(12);
				comboBox_1.setSelectedItem(30);
				comboBox_2.setSelectedItem("AM");
				sourceField.setText("");
				destinationField.setText("");
				busIDField.setText("");
				
			}
		});
		btnClear.setBounds(98, 135, 80, 23);
		contentPane.add(btnClear);
		
		sourceField = new JTextField();
		sourceField.setColumns(10);
		sourceField.setBounds(106, 42, 162, 20);
		contentPane.add(sourceField);
		
		destinationField = new JTextField();
		destinationField.setColumns(10);
		destinationField.setBounds(106, 73, 162, 20);
		contentPane.add(destinationField);
		
		busIDField = new JTextField("");
		busIDField.setBounds(106, 11, 162, 20);
		contentPane.add(busIDField);
		busIDField.setColumns(10);
		
		comboBox.setModel(new DefaultComboBoxModel(hours.toArray()));
		comboBox.setBounds(106, 104, 46, 20);
		contentPane.add(comboBox);
		comboBox.setSelectedItem(12);
		
		comboBox_1.setModel(new DefaultComboBoxModel(mins.toArray()));
		comboBox_1.setBounds(162, 103, 46, 20);
		contentPane.add(comboBox_1);
		comboBox_1.setSelectedItem(30);
		
		comboBox_2 = new JComboBox(choice);
		comboBox_2.setBounds(218, 104, 50, 20);
		contentPane.add(comboBox_2);
		comboBox_2.setSelectedItem("AM");
	}
	
	public void finish(){
		this.dispose();
	}
}
