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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class displayTicket extends JFrame {

	private JPanel contentPane;
	private static JButton btnBack = new JButton("Back");

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
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Add new bus");
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.refreshBuses();
				mainWindow.refreshTimetable();
				finish();
			}
		});
		btnBack.setBounds(304, 237, 80, 23);
		contentPane.add(btnBack);
		
		JButton btnSavePdf = new JButton("Save PDF");
		btnSavePdf.setBounds(205, 237, 89, 23);
		contentPane.add(btnSavePdf);
		
		JLabel lblTicketId = new JLabel("Ticket ID: ");
		lblTicketId.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTicketId.setForeground(Color.WHITE);
		lblTicketId.setBounds(20, 11, 80, 17);
		contentPane.add(lblTicketId);
		
		JLabel label = new JLabel("Ticket ID: ");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setBounds(20, 39, 80, 17);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Ticket ID: ");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Arial", Font.PLAIN, 14));
		label_1.setBounds(20, 67, 80, 17);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Ticket ID: ");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Arial", Font.PLAIN, 14));
		label_2.setBounds(20, 95, 80, 17);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Ticket ID: ");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Arial", Font.PLAIN, 14));
		label_3.setBounds(20, 123, 80, 17);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("Ticket ID: ");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Arial", Font.PLAIN, 14));
		label_4.setBounds(20, 151, 80, 17);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("Ticket ID: ");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Arial", Font.PLAIN, 14));
		label_5.setBounds(20, 179, 80, 17);
		contentPane.add(label_5);
	}
	
	public void finish(){
		this.dispose();
	}
}
