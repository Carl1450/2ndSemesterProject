package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

public class MainMenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Employee employee;
	private JButton orderButton;
	private JButton bookingButton;
	private OrderLineTableModel productTableModel;
	private JButton taskButton;
	private JButton editCustomerButton;
	private JButton editCampsiteButton;
	private JButton editProductButton;
	private JButton editEmployeesButton;

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
		gbl_panel.columnWidths = new int[] { 125, 125, 125 };
		gbl_panel.rowHeights = new int[] { 35, 0, 0, 0, 15, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_bookingButton.insets = new Insets(0, 0, 5, 5);
		gbc_bookingButton.gridx = 0;
		gbc_bookingButton.gridy = 1;
		panel.add(bookingButton, gbc_bookingButton);

		orderButton = new JButton("Create Order");
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderButtonClicked();
			}
		});
		orderButton.setVisible(false);
		GridBagConstraints gbc_orderButton = new GridBagConstraints();
		gbc_orderButton.insets = new Insets(0, 0, 5, 5);
		gbc_orderButton.gridx = 1;
		gbc_orderButton.gridy = 1;
		panel.add(orderButton, gbc_orderButton);

		taskButton = new JButton("Tasks");
		taskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskButtonClicked();
			}
		});
		taskButton.setVisible(false);

		GridBagConstraints gbc_taskButton = new GridBagConstraints();
		gbc_taskButton.insets = new Insets(0, 0, 5, 0);
		gbc_taskButton.gridx = 2;
		gbc_taskButton.gridy = 1;
		panel.add(taskButton, gbc_taskButton);

		editCustomerButton = new JButton("Edit Customer");
		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerButtonClicked();
			}
		});
		editCustomerButton.setVisible(false);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		GridBagConstraints gbc_editCustomerButton = new GridBagConstraints();
		gbc_editCustomerButton.insets = new Insets(0, 0, 5, 5);
		gbc_editCustomerButton.gridx = 0;
		gbc_editCustomerButton.gridy = 3;
		panel.add(editCustomerButton, gbc_editCustomerButton);

		editProductButton = new JButton("Edit Products");
		editProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editProductButtonClicked();
			}
		});
		editProductButton.setVisible(false);

		GridBagConstraints gbc_editProductButton = new GridBagConstraints();
		gbc_editProductButton.insets = new Insets(0, 0, 5, 5);
		gbc_editProductButton.gridx = 1;
		gbc_editProductButton.gridy = 3;
		panel.add(editProductButton, gbc_editProductButton);

		editCampsiteButton = new JButton("Edit Campsites");
		editCampsiteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCampsiteButtonClicked();
			}
		});
		editCampsiteButton.setVisible(false);

		GridBagConstraints gbc_editCampsiteButton = new GridBagConstraints();
		gbc_editCampsiteButton.insets = new Insets(0, 0, 5, 0);
		gbc_editCampsiteButton.gridx = 2;
		gbc_editCampsiteButton.gridy = 3;
		panel.add(editCampsiteButton, gbc_editCampsiteButton);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 4;
		panel.add(panel_4, gbc_panel_4);

		editEmployeesButton = new JButton("Edit Employees");
		editEmployeesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editEmployeesButtonClicked();
			}
		});
		editEmployeesButton.setVisible(false);

		GridBagConstraints gbc_editEmployeesButton = new GridBagConstraints();
		gbc_editEmployeesButton.insets = new Insets(0, 0, 5, 5);
		gbc_editEmployeesButton.gridx = 1;
		gbc_editEmployeesButton.gridy = 5;
		panel.add(editEmployeesButton, gbc_editEmployeesButton);

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
			taskButton.setVisible(true);
			editCustomerButton.setVisible(true);
			editCampsiteButton.setVisible(true);
			editProductButton.setVisible(true);
			editEmployeesButton.setVisible(true);

		} else if (employee instanceof Receptionist) {
			bookingButton.setVisible(true);
			orderButton.setVisible(true);
			taskButton.setVisible(true);
		} else if (employee instanceof SalesAssistant) {
			orderButton.setVisible(true);
		} else if (employee instanceof Janitor) {
			taskButton.setVisible(true);
		}
	}

	private void orderButtonClicked() {
		Order currentOrder = new Order(employee);
		OrderInfoGUI orderInfoGUI = new OrderInfoGUI(currentOrder, productTableModel, employee);
		orderInfoGUI.setVisible(true);
		dispose();
	}

	private void editCustomerButtonClicked() {
		EditCustomerGUI editCustomerGUI = new EditCustomerGUI(employee);
		editCustomerGUI.setVisible(true);
		dispose();
	}

	private void taskButtonClicked() {
		if (employee instanceof Janitor) {
			CompleteTaskGUI completeTaskGUI = new CompleteTaskGUI(employee);
			completeTaskGUI.setVisible(true);
		} else {
			CreateTaskGUI createTaskGUI = new CreateTaskGUI(employee);
			createTaskGUI.setVisible(true);
			dispose();
		}

		dispose();
	}

	private void editProductButtonClicked() {
		EditProductsGUI editProductsGUI = new EditProductsGUI(employee);
		editProductsGUI.setVisible(true);
		dispose();
	}

	private void editEmployeesButtonClicked() {
		EditEmployeeGUI editEmployeeGUI = new EditEmployeeGUI(employee);
		editEmployeeGUI.setVisible(true);
		dispose();
	}

	private void editCampsiteButtonClicked() {
		CreateCampsiteGUI editCampsiteGUI = new CreateCampsiteGUI(employee);
		editCampsiteGUI.setVisible(true);
		dispose();
	}

}
