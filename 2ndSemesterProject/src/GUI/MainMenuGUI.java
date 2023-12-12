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
import Model.Order;
import Model.Receptionist;
import Model.SalesAssistant;
import java.awt.FlowLayout;

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
		gbl_panel.rowHeights = new int[]{35, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		bookingButton = new JButton("Booking");
		bookingButton.setVisible(false);
		bookingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				bookingButtonClicked();
			}
		});
		GridBagConstraints gbc_bookingButton = new GridBagConstraints();
		gbc_bookingButton.anchor = GridBagConstraints.NORTH;
		gbc_bookingButton.insets = new Insets(0, 0, 5, 0);
		gbc_bookingButton.gridx = 0;
		gbc_bookingButton.gridy = 0;
		panel.add(bookingButton, gbc_bookingButton);
		
		orderButton = new JButton("Create Order");
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderButtonClicked();
			}
		});
		orderButton.setVisible(false);
		GridBagConstraints gbc_orderButton = new GridBagConstraints();
		gbc_orderButton.insets = new Insets(0, 0, 5, 0);
		gbc_orderButton.gridx = 0;
		gbc_orderButton.gridy = 1;
		panel.add(orderButton, gbc_orderButton);
		
		JButton taskButton = new JButton("Tasks");
		taskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskButtonClicked();
			}
		});
		GridBagConstraints gbc_taskButton = new GridBagConstraints();
		gbc_taskButton.insets = new Insets(0, 0, 5, 0);
		gbc_taskButton.gridx = 0;
		gbc_taskButton.gridy = 2;
		panel.add(taskButton, gbc_taskButton);
		
		JButton editCustomerButton = new JButton("Edit Customer");
		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerButtonClicked();
			}
		});
		GridBagConstraints gbc_editCustomerButton = new GridBagConstraints();
		gbc_editCustomerButton.gridx = 0;
		gbc_editCustomerButton.gridy = 3;
		panel.add(editCustomerButton, gbc_editCustomerButton);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton logOutButton = new JButton("Log Out");
		panel_1.add(logOutButton);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JLabel mainMenuLabel = new JLabel("Main Menu");
		panel_2.add(mainMenuLabel);
		mainMenuLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOutButtonClicked();
			}
		});
		
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
	
	private void orderButtonClicked() {
		Order currentOrder = new Order(employee);
		OrderInfoGUI orderInfoGUI = new OrderInfoGUI(currentOrder);
		orderInfoGUI.setVisible(true);
		dispose();
	}
	
	private void editCustomerButtonClicked() {
		EditCustomerGUI editCustomerGUI = new EditCustomerGUI(employee);
		editCustomerGUI.setVisible(true);
		dispose();
	}
	
	private void taskButtonClicked() {
		CreateTaskGUI createTaskGUI = new CreateTaskGUI();
		createTaskGUI.setVisible(true);
		dispose();
	}
}
