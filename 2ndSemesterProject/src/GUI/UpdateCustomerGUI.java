package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Control.CustomerController;
import Database.ConnectionEnvironment;
import Model.Customer;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateCustomerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField phoneNumberTextField;
	private JTextField emailTextField;
	private JTextField addressTextField;
	private JTextField zipCodeTextField;
	private JTextField cityTextField;

	private Customer customer;
	private CustomerController customerController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateCustomerGUI frame = new UpdateCustomerGUI(null);
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
	public UpdateCustomerGUI(Customer customer) {
		this.customer = customer;
		customerController = new CustomerController(ConnectionEnvironment.PRODUCTION);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel updateCustomerLabel = new JLabel("Update Customer");
		updateCustomerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(updateCustomerLabel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		panel_1.add(backButton);

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButtonClicked();
			}
		});
		panel_1.add(updateButton);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel nameLabel = new JLabel("Name:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		panel_2.add(nameLabel, gbc_nameLabel);

		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		panel_2.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 0;
		gbc_phoneNumberLabel.gridy = 1;
		panel_2.add(phoneNumberLabel, gbc_phoneNumberLabel);

		phoneNumberTextField = new JTextField();
		GridBagConstraints gbc_phoneNumberTextField = new GridBagConstraints();
		gbc_phoneNumberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_phoneNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumberTextField.gridx = 1;
		gbc_phoneNumberTextField.gridy = 1;
		panel_2.add(phoneNumberTextField, gbc_phoneNumberTextField);
		phoneNumberTextField.setColumns(10);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 2;
		panel_2.add(emailLabel, gbc_emailLabel);

		emailTextField = new JTextField();
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.insets = new Insets(0, 0, 5, 0);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 1;
		gbc_emailTextField.gridy = 2;
		panel_2.add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);

		JLabel addressLabel = new JLabel("Address:");
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_addressLabel.gridx = 0;
		gbc_addressLabel.gridy = 3;
		panel_2.add(addressLabel, gbc_addressLabel);

		addressTextField = new JTextField();
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.insets = new Insets(0, 0, 5, 0);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 1;
		gbc_addressTextField.gridy = 3;
		panel_2.add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);

		JLabel zipCodeLabel = new JLabel("Zip Code:");
		GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
		gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_zipCodeLabel.gridx = 0;
		gbc_zipCodeLabel.gridy = 4;
		panel_2.add(zipCodeLabel, gbc_zipCodeLabel);

		zipCodeTextField = new JTextField();
		GridBagConstraints gbc_zipCodeTextField = new GridBagConstraints();
		gbc_zipCodeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_zipCodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zipCodeTextField.gridx = 1;
		gbc_zipCodeTextField.gridy = 4;
		panel_2.add(zipCodeTextField, gbc_zipCodeTextField);
		zipCodeTextField.setColumns(10);

		JLabel cityLabel = new JLabel("City:");
		GridBagConstraints gbc_cityLabel = new GridBagConstraints();
		gbc_cityLabel.anchor = GridBagConstraints.WEST;
		gbc_cityLabel.insets = new Insets(0, 0, 0, 5);
		gbc_cityLabel.gridx = 0;
		gbc_cityLabel.gridy = 5;
		panel_2.add(cityLabel, gbc_cityLabel);

		cityTextField = new JTextField();
		GridBagConstraints gbc_cityTextField = new GridBagConstraints();
		gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityTextField.gridx = 1;
		gbc_cityTextField.gridy = 5;
		panel_2.add(cityTextField, gbc_cityTextField);
		cityTextField.setColumns(10);

		init();

	}

	private void init() {
		setTextFields();
	}

	private void setTextFields() {
		nameTextField.setText(customer.getName());
		phoneNumberTextField.setText(customer.getPhoneNumber());
		emailTextField.setText(customer.getEmail());

		String[] splitAddress = customer.getAddress().split("\\s+");

		addressTextField.setText(splitAddress[0] + " " + splitAddress[1]);
		zipCodeTextField.setText(splitAddress[3]);
		cityTextField.setText(splitAddress[2]);
	}

	private void updateButtonClicked() {
		String oldName = customer.getName();
		String oldPhoneNumber = customer.getPhoneNumber();
		String oldEmail = customer.getEmail();
		String oldAddress = customer.getAddress();

		String newName = nameTextField.getText();
		String newPhoneNumber = phoneNumberTextField.getText();
		String newEmail = emailTextField.getText();
		String newStreet = addressTextField.getText();
		String newZipCode = zipCodeTextField.getText();
		String newCity = cityTextField.getText();

		String newAddress = newStreet + " " + newCity + " " + newZipCode;

		if (!newName.equals(oldName) || !newPhoneNumber.equals(oldPhoneNumber) || !newEmail.equals(oldEmail)
				|| !newAddress.equals(oldAddress)) {

			// Debugging: Print values before setting
			System.out.println("Before setting values:");
			System.out.println("newName: " + newName);
			System.out.println("newPhoneNumber: " + newPhoneNumber);
			System.out.println("newEmail: " + newEmail);
			System.out.println("newAddress: " + newAddress);

			customer.setName(newName);
			customer.setPhoneNumber(newPhoneNumber);
			customer.setEmail(newEmail);
			customer.setAddress(newAddress);

			// Debugging: Print values after setting
			System.out.println("After setting values:");
			System.out.println("customer.getName(): " + customer.getName());
			System.out.println("customer.getPhoneNumber(): " + customer.getPhoneNumber());
			System.out.println("customer.getEmail(): " + customer.getEmail());
			System.out.println("customer.getAddress(): " + customer.getAddress());

			customerController.updateCustomer(customer);

			System.out.println("Customer information successfully updated.");
		} else {
			System.out.println("No changes to update.");
		}

	}
}
