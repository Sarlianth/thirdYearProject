package ie.gmit.g00309646;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

//imports iText API to create PDF file from provided information
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class displayTicket extends JFrame {

	// Declaring class variables
	private static final long serialVersionUID = 1L;
	
	// Variables for database connection
	private static String dbHost = "sql8.freemysqlhosting.net";
	private static String dbPort = "3306";
	private static String dbNameCon = "sql8160217";
	private static String dbUsername = "sql8160217";
	private static String dbPassword = "XRN5N6f6BG";
	
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
	private static int id = 0;
	
	private static int ticketDbBusID = 0;
	private static String ticketDbBusNumber = "";
	private static String ticketDbJourneyDate = "";
	private static int ticketDbAdult = 0;
	private static int ticketDbStudent = 0;
	private static int ticketDbUserID = 0;
	private static double ticketDbTotalPrice = 0.00;
	private static String usersDbName = "";
	private static String usersDbSurname = "";
	private static String busDbSource = "";
	private static String busDbDestination = "";
	
	//iText API for PDF file
	private static String FILE = "./sample.pdf";
	private static com.itextpdf.text.Font catFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static com.itextpdf.text.Font redFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, Font.PLAIN, BaseColor.RED);
	private static com.itextpdf.text.Font smallBold = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// BeautyEye API to make the application look better
				try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// Create the frame
				try {
					displayTicket frame = new displayTicket(0);
					// Make the frame appear in the middle of the screen
					frame.setLocationRelativeTo(null);
					// Set the frame visible
					frame.setVisible(true);
					// Default close operation - DISPOSE
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
				
				// When frame is created use the variable from passed parameter in constructor for the ticket id to be used in this frame
				id = ticketID;
				
				// Connect to database to query the ticket information
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
					Statement stmt = conn.createStatement();
					
					// String for the query
					String strSelect = "select * from ticket where ticket_id="+ticketID;
			 
					// Execute the query
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
		        	
		        	// Connect to database again to query all information about passenger from the ticket
		        	try {
						Connection conn2 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
						Statement stmt2 = conn2.createStatement();
						
						// String for the query
						String strSelect2 = "select name, surname from users where id="+ticketDbUserID;
				 
						// Execute the query
				        ResultSet rset2 = stmt2.executeQuery(strSelect2);
				 
			        	while(rset2.next()) {
			        		usersDbName = rset2.getString("name");
			        		usersDbSurname = rset2.getString("surname");
				        }  
			        	
			        	// Connect to database again to query all information about the bus from this ticket
			        	try {
							Connection conn3 = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbNameCon+"?useSSL=false", dbUsername, dbPassword); 
							Statement stmt3 = conn3.createStatement();
							
							String strSelect3 = "select depart_from, going_to from bus_table where bus_id="+ticketDbBusID;
					 
					        ResultSet rset3 = stmt3.executeQuery(strSelect3);
					 
				        	while(rset3.next()) {
				        		busDbSource = rset3.getString("depart_from");
				        		busDbDestination = rset3.getString("going_to");
					        }  
				        	
				        	// Close the connection
				        	rset3.close();
					        stmt3.close();
							conn3.close();
				        	
					      } catch(SQLException ex) {
					    	  ex.printStackTrace();
					      }

			        	// Close the connection
			        	rset2.close();
				        stmt2.close();
						conn2.close();
			        	
				      } catch(SQLException ex) {
				    	  ex.printStackTrace();
				      }
		        	
		        	// Close the connection
		        	rset.close();
			        stmt.close();
					conn.close();
		        
			      } catch(SQLException ex) {
			    	  ex.printStackTrace();
			      }
				
				// Display all the information gathered from database into textFields 
				ticketIDlbl.setText(String.valueOf(ticketID));
				busIDlbl.setText(String.valueOf(ticketDbBusID));
				busNumberlbl.setText(ticketDbBusNumber);
				journeyLbl.setText(busDbSource+" - "+busDbDestination);
				journeyDatelbl.setText(ticketDbJourneyDate);
				adultsLbl.setText(String.valueOf(ticketDbAdult));
				studentsLbl.setText(String.valueOf(ticketDbStudent));
				totalPricelbl.setText("�"+String.valueOf(ticketDbTotalPrice));
				boughtBylbl.setText(usersDbName+" "+usersDbSurname);
			}
		});
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Ticket - "+ticketID);
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 390);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));

		// Back button listener
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// When closing this frame refresh the buses and the timetable, so the information there is accurate and up-to-date
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
		
		// Save PDF button listener
		btnSavePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Try to create new document using the iText API to save the ticket into PDF format in users machine
				try {
					// File name 
					FILE = "./"+ticketID+".pdf";
					// Create new instance of document
					Document document = new Document();
					// Output stream for the document 
					PdfWriter.getInstance(document, new FileOutputStream(FILE));
					// Open the document
					document.open();
					// Add metadata to document (seperate method)
					addMetaData(document);
					// Add page into the document (seperate method)
					addPage(document);
					// Finally close the document
					document.close();
					
					// Display message to user and close the frame
					JOptionPane.showMessageDialog(null, ticketID+".pdf successfully saved");
					finish();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSavePdf.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSavePdf.setBounds(222, 262, 117, 23);
		contentPane.add(btnSavePdf);
	}
	
	// Method to dispose the frame
	public void finish(){
		this.dispose();
	}
	
	
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
    		// Applying metadata for the document
            document.addTitle("Ticket ID - "+id);
            document.addKeywords("Java, MySQL, Project, IrishBus, App, Final, Year");
            document.addAuthor("Adrian Sypos");
            document.addCreator("Adrian Sypos");
    }

    // Method to add a page into document
    private static void addPage(Document document) throws DocumentException {
    		// Create new instance of paragraph
            Paragraph preface = new Paragraph();
            
            // add empty lines and all the information onto the page inside paragraph
            addEmptyLine(preface, 1);
            preface.add(new Paragraph("Ticket ID - "+id, catFont));

            addEmptyLine(preface, 1);
            preface.add(new Paragraph("Ticket generated autimatically on " + new Date(),smallBold));
            
            // Using all the data from database to represent a ticket
            addEmptyLine(preface, 3);
            preface.add(new Paragraph("Ticket ID - "+String.valueOf(id), smallBold));
            preface.add(new Paragraph("Bus ID - "+String.valueOf(ticketDbBusID), smallBold));
            preface.add(new Paragraph("Bus name - "+ticketDbBusNumber, smallBold));
            preface.add(new Paragraph("Journey - "+busDbSource+" - "+busDbDestination, smallBold));
            preface.add(new Paragraph("Journey date - "+ticketDbJourneyDate, smallBold));
            preface.add(new Paragraph("Adults - "+String.valueOf(ticketDbAdult), smallBold));
            preface.add(new Paragraph("Students - "+String.valueOf(ticketDbStudent), smallBold));
            preface.add(new Paragraph("Total price - "+"�"+String.valueOf(ticketDbTotalPrice), smallBold));
            preface.add(new Paragraph("Bought by - "+usersDbName+" "+usersDbSurname, smallBold));

            addEmptyLine(preface, 8);
            preface.add(new Paragraph("Thank you for buying the ticket with IrishBusApp", redFont));

            // Add the paragraph to the document
            document.add(preface);
            // Start a new page
            document.newPage();
    }

    // Method to add a new empty line into a document
    private static void addEmptyLine(Paragraph paragraph, int number) {
            for (int i = 0; i < number; i++) {
                    paragraph.add(new Paragraph(" "));
            }
    }
	
}