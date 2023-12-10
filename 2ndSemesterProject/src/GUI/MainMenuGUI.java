package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Admin;
import Model.Employee;
import Model.Janitor;
import Model.Receptionist;
import Model.SalesAssistant;

public class MainMenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Employee employee;
	private JButton orderButton;
	private JButton bookingButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuGUI frame = new MainMenuGUI(null);
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
	public MainMenuGUI(Employee employee) {
		this.employee = employee;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 77, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		bookingButton = new JButton("Booking");
		bookingButton.setVisible(false);
		bookingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				bookingButtonClicked();
			}
		});
		
		JLabel mainMenuLabel = new JLabel("Main Menu");
		mainMenuLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		GridBagConstraints gbc_mainMenuLabel = new GridBagConstraints();
		gbc_mainMenuLabel.insets = new Insets(0, 0, 5, 0);
		gbc_mainMenuLabel.gridx = 0;
		gbc_mainMenuLabel.gridy = 0;
		panel.add(mainMenuLabel, gbc_mainMenuLabel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		GridBagConstraints gbc_bookingButton = new GridBagConstraints();
		gbc_bookingButton.insets = new Insets(0, 0, 5, 0);
		gbc_bookingButton.gridx = 0;
		gbc_bookingButton.gridy = 3;
		panel.add(bookingButton, gbc_bookingButton);
		
		orderButton = new JButton("Create Order");
		orderButton.setVisible(false);
		GridBagConstraints gbc_orderButton = new GridBagConstraints();
		gbc_orderButton.insets = new Insets(0, 0, 5, 0);
		gbc_orderButton.gridx = 0;
		gbc_orderButton.gridy = 4;
		panel.add(orderButton, gbc_orderButton);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 5;
		panel.add(panel_2, gbc_panel_2);
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOutButtonClicked();
			}
		});
		GridBagConstraints gbc_logOutButton = new GridBagConstraints();
		gbc_logOutButton.gridx = 0;
		gbc_logOutButton.gridy = 6;
		panel.add(logOutButton, gbc_logOutButton);
		
		displayButtonBasedOnAccessLevel();
	}

	private void bookingButtonClicked() {
		BookingInfoGUI customerInfoGUI = new BookingInfoGUI(employee);
		customerInfoGUI.setVisible(true);
		dispose();
	}

	private void logOutButtonClicked() {
		LoginGUI loginGUI = new LoginGUI();
		loginGUI.setVisible(true);
		dispose();
	}

	private void displayButtonBasedOnAccessLevel() {
		
		
		if (employee instanceof Admin) {
			bookingButton.setVisible(true);
			orderButton.setVisible(true);
		} else if (employee instanceof Receptionist) {
			bookingButton.setVisible(true);
		} else if (employee instanceof SalesAssistant) {
			orderButton.setVisible(true);
		} else if (employee instanceof Janitor) {
			
		}
	}
}
