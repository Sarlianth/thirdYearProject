package ie.gmit.g00309646;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class displayTicket extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JButton btnBack = new JButton("Back");
	private static JButton btnSavePdf = new JButton("Save PDF");
	private static JLabel lblTicketId = new JLabel("Ticket ID:");
	private static JLabel lblBusId = new JLabel("Bus ID:");
	private static JLabel lblBusNumber = new JLabel("Bus number:");
	private static JLabel lblJourneyDate = new JLabel("Journey date:");
	private static JLabel lblAdults = new JLabel("Adults:");
	private static JLabel lblStudents = new JLabel("Students:");
	private static JLabel lblBoughtBy = new JLabel("Bought by:");
	private static JLabel lblTotalPrice = new JLabel("Total price:");
	private static JLabel boughtBylbl = new JLabel("");
	private static JLabel totalPricelbl = new JLabel("");
	private static JLabel studentsLbl = new JLabel("");
	private static JLabel adultsLbl = new JLabel("");
	private static JLabel journeyDatelbl = new JLabel("");
	private static JLabel busNumberlbl = new JLabel("");
	private static JLabel busIDlbl = new JLabel("");
	private static JLabel ticketIDlbl = new JLabel("");
	private final JLabel lblJourney = new JLabel("Journey:");
	private final JLabel journeyLbl = new JLabel("");

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
					displayTicket frame = new displayTicket(0);
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
	public displayTicket(int ticketID) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				
				int ticketDbBusID = 0;
				String ticketDbBusNumber = "";
				String ticketDbJourneyDate = "";
				int ticketDbAdult = 0;
				int ticketDbStudent = 0;
				int ticketDbUserID = 0;
				double ticketDbTotalPrice = 0.00;
				
				String usersDbName = "";
				String usersDbSurname = "";
				
				String busDbSource = "";
				String busDbDestination = "";
				
				
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
					Statement stmt = conn.createStatement();
					
					String strSelect = "select * from ticket where ticket_id="+ticketID;
			 
			        ResultSet rset = stmt.executeQuery(strSelect);
			 
		        	while(rset.next()) {
		        		ticketDbBusID = rset.getInt("bus_id");
						ticketDbBusNumber = rset.getString("bus_number");
						ticketDbJourneyDate = rset.getString("journey_date");
						ticketDbAdult = rset.getInt("adult_quantity");
						ticketDbStudent = rset.getInt("student_quantity");
						ticketDbUserID = rset.getInt("user_id");
						ticketDbTotalPrice = rset.getDouble("totalPrice");
			        }  
		        	
		        	try {
						Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
						Statement stmt2 = conn2.createStatement();
						
						String strSelect2 = "select name, surname from users where id="+ticketDbUserID;
				 
				        ResultSet rset2 = stmt2.executeQuery(strSelect2);
				 
			        	while(rset2.next()) {
			        		usersDbName = rset2.getString("name");
			        		usersDbSurname = rset2.getString("surname");
				        }  
			        	
			        	try {
							Connection conn3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?useSSL=false", "root", ""); 
							Statement stmt3 = conn3.createStatement();
							
							String strSelect3 = "select depart_from, going_to from bus_table where bus_id="+ticketDbBusID;
					 
					        ResultSet rset3 = stmt3.executeQuery(strSelect3);
					 
				        	while(rset3.next()) {
				        		busDbSource = rset3.getString("depart_from");
				        		busDbDestination = rset3.getString("going_to");
					        }  
				        	
					      } catch(SQLException ex) {
					    	  ex.printStackTrace();
					      }

				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
		        
			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      }
				
				ticketIDlbl.setText(String.valueOf(ticketID));
				busIDlbl.setText(String.valueOf(ticketDbBusID));
				busNumberlbl.setText(ticketDbBusNumber);
				journeyLbl.setText(busDbSource+" - "+busDbDestination);
				journeyDatelbl.setText(ticketDbJourneyDate);
				adultsLbl.setText(String.valueOf(ticketDbAdult));
				studentsLbl.setText(String.valueOf(ticketDbStudent));
				totalPricelbl.setText("€"+String.valueOf(ticketDbTotalPrice));
				boughtBylbl.setText(usersDbName+" "+usersDbSurname);
			}
		});
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Ticket - "+ticketID);
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 486, 372);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.refreshBuses();
				mainWindow.refreshTimetable();
				finish();
			}
		});
		btnBack.setBounds(349, 262, 73, 23);
		contentPane.add(btnBack);
		
		lblTicketId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTicketId.setFont(new Font("Arial", Font.BOLD, 14));
		lblTicketId.setForeground(Color.LIGHT_GRAY);
		lblTicketId.setBounds(20, 11, 99, 17);
		contentPane.add(lblTicketId);
		
		lblBusId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusId.setForeground(Color.LIGHT_GRAY);
		lblBusId.setFont(new Font("Arial", Font.BOLD, 14));
		lblBusId.setBounds(20, 39, 99, 17);
		contentPane.add(lblBusId);
		
		lblBusNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBusNumber.setForeground(Color.LIGHT_GRAY);
		lblBusNumber.setFont(new Font("Arial", Font.BOLD, 14));
		lblBusNumber.setBounds(20, 67, 99, 17);
		contentPane.add(lblBusNumber);
		
		lblJourneyDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJourneyDate.setForeground(Color.LIGHT_GRAY);
		lblJourneyDate.setFont(new Font("Arial", Font.BOLD, 14));
		lblJourneyDate.setBounds(20, 123, 99, 17);
		contentPane.add(lblJourneyDate);
		
		lblAdults.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdults.setForeground(Color.LIGHT_GRAY);
		lblAdults.setFont(new Font("Arial", Font.BOLD, 14));
		lblAdults.setBounds(20, 151, 99, 17);
		contentPane.add(lblAdults);
		
		lblStudents.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudents.setForeground(Color.LIGHT_GRAY);
		lblStudents.setFont(new Font("Arial", Font.BOLD, 14));
		lblStudents.setBounds(20, 179, 99, 17);
		contentPane.add(lblStudents);
		
		lblBoughtBy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBoughtBy.setForeground(Color.LIGHT_GRAY);
		lblBoughtBy.setFont(new Font("Arial", Font.BOLD, 14));
		lblBoughtBy.setBounds(20, 234, 99, 17);
		contentPane.add(lblBoughtBy);
		
		lblTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPrice.setForeground(Color.LIGHT_GRAY);
		lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalPrice.setBounds(20, 207, 99, 17);
		contentPane.add(lblTotalPrice);
	
		boughtBylbl.setHorizontalAlignment(SwingConstants.LEFT);
		boughtBylbl.setForeground(Color.LIGHT_GRAY);
		boughtBylbl.setFont(new Font("Arial", Font.PLAIN, 14));
		boughtBylbl.setBounds(129, 234, 293, 17);
		contentPane.add(boughtBylbl);
		
		totalPricelbl.setHorizontalAlignment(SwingConstants.LEFT);
		totalPricelbl.setForeground(Color.LIGHT_GRAY);
		totalPricelbl.setFont(new Font("Arial", Font.PLAIN, 14));
		totalPricelbl.setBounds(129, 207, 293, 17);
		contentPane.add(totalPricelbl);
		
		studentsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		studentsLbl.setForeground(Color.LIGHT_GRAY);
		studentsLbl.setFont(new Font("Arial", Font.PLAIN, 14));
		studentsLbl.setBounds(129, 179, 293, 17);
		contentPane.add(studentsLbl);
		
		adultsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		adultsLbl.setForeground(Color.LIGHT_GRAY);
		adultsLbl.setFont(new Font("Arial", Font.PLAIN, 14));
		adultsLbl.setBounds(129, 151, 293, 17);
		contentPane.add(adultsLbl);
		
		journeyDatelbl.setHorizontalAlignment(SwingConstants.LEFT);
		journeyDatelbl.setForeground(Color.LIGHT_GRAY);
		journeyDatelbl.setFont(new Font("Arial", Font.PLAIN, 14));
		journeyDatelbl.setBounds(129, 123, 293, 17);
		contentPane.add(journeyDatelbl);
		
		busNumberlbl.setHorizontalAlignment(SwingConstants.LEFT);
		busNumberlbl.setForeground(Color.LIGHT_GRAY);
		busNumberlbl.setFont(new Font("Arial", Font.PLAIN, 14));
		busNumberlbl.setBounds(129, 67, 293, 17);
		contentPane.add(busNumberlbl);
		
		busIDlbl.setHorizontalAlignment(SwingConstants.LEFT);
		busIDlbl.setForeground(Color.LIGHT_GRAY);
		busIDlbl.setFont(new Font("Arial", Font.PLAIN, 14));
		busIDlbl.setBounds(129, 39, 293, 17);
		contentPane.add(busIDlbl);
		
		ticketIDlbl.setHorizontalAlignment(SwingConstants.LEFT);
		ticketIDlbl.setForeground(Color.LIGHT_GRAY);
		ticketIDlbl.setFont(new Font("Arial", Font.PLAIN, 14));
		ticketIDlbl.setBounds(129, 11, 293, 17);
		contentPane.add(ticketIDlbl);
		lblJourney.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJourney.setForeground(Color.LIGHT_GRAY);
		lblJourney.setFont(new Font("Arial", Font.BOLD, 14));
		lblJourney.setBounds(20, 95, 99, 17);
		
		contentPane.add(lblJourney);
		journeyLbl.setHorizontalAlignment(SwingConstants.LEFT);
		journeyLbl.setForeground(Color.LIGHT_GRAY);
		journeyLbl.setFont(new Font("Arial", Font.PLAIN, 14));
		journeyLbl.setBounds(129, 95, 293, 17);
		
		contentPane.add(journeyLbl);
		btnSavePdf.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSavePdf.setBounds(230, 262, 109, 23);
		contentPane.add(btnSavePdf);
	}
	
	public void finish(){
		this.dispose();
	}
}