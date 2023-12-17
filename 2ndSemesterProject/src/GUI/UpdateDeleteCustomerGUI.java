package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Control.CustomerController;
import Database.ConnectionEnvironment;
import Model.Address;
import Model.Customer;
import Model.Employee;

public class UpdateDeleteCustomerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneNumberTextField;
	private JPanel panel_1;
	private JButton backButton;
	private JButton updateButton;
	private JPanel panel_2;
	private JLabel searchForCustomerLabel;
	private JTextField customerSearchTextField;
	private JButton deleteButton;
	private JScrollPane scrollPane;
	private JTable customerTable;

	private Employee employee;

	private CustomerTableModel customerTableModel;

	private CustomerController customerController;

	private int lastSelectedRow;
	private JLabel addressLabel;
	private JTextField addressTextField;
	private JLabel zipCodeLabel;
	private JLabel cityLabel;
	private JTextField zipcodeTextField;
	private JTextField cityTextField;

	public UpdateDeleteCustomerGUI(Employee employee) {

		setSize(800, 600);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 259, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel nameLabel = new JLabel("Name");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		panel.add(nameLabel, gbc_nameLabel);

		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		panel.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 1;
		panel.add(emailLabel, gbc_emailLabel);

		emailTextField = new JTextField();
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.insets = new Insets(0, 0, 5, 0);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 1;
		gbc_emailTextField.gridy = 1;
		panel.add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 0;
		gbc_phoneNumberLabel.gridy = 2;
		panel.add(phoneNumberLabel, gbc_phoneNumberLabel);

		phoneNumberTextField = new JTextField();
		GridBagConstraints gbc_phoneNumberTextField = new GridBagConstraints();
		gbc_phoneNumberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_phoneNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumberTextField.gridx = 1;
		gbc_phoneNumberTextField.gridy = 2;
		panel.add(phoneNumberTextField, gbc_phoneNumberTextField);
		phoneNumberTextField.setColumns(10);

		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButtonClicked();
			}
		});

		addressLabel = new JLabel("Address:");
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_addressLabel.gridx = 0;
		gbc_addressLabel.gridy = 3;
		panel.add(addressLabel, gbc_addressLabel);

		addressTextField = new JTextField();
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.insets = new Insets(0, 0, 5, 0);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 1;
		gbc_addressTextField.gridy = 3;
		panel.add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);

		zipCodeLabel = new JLabel("Zipcode: ");
		GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
		gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_zipCodeLabel.gridx = 0;
		gbc_zipCodeLabel.gridy = 4;
		panel.add(zipCodeLabel, gbc_zipCodeLabel);

		zipcodeTextField = new JTextField();
		GridBagConstraints gbc_zipcodeTextField = new GridBagConstraints();
		gbc_zipcodeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_zipcodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zipcodeTextField.gridx = 1;
		gbc_zipcodeTextField.gridy = 4;
		panel.add(zipcodeTextField, gbc_zipcodeTextField);
		zipcodeTextField.setColumns(10);

		cityLabel = new JLabel("City");
		GridBagConstraints gbc_cityLabel = new GridBagConstraints();
		gbc_cityLabel.anchor = GridBagConstraints.WEST;
		gbc_cityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cityLabel.gridx = 0;
		gbc_cityLabel.gridy = 5;
		panel.add(cityLabel, gbc_cityLabel);

		cityTextField = new JTextField();
		GridBagConstraints gbc_cityTextField = new GridBagConstraints();
		gbc_cityTextField.insets = new Insets(0, 0, 5, 0);
		gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityTextField.gridx = 1;
		gbc_cityTextField.gridy = 5;
		panel.add(cityTextField, gbc_cityTextField);
		cityTextField.setColumns(10);
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.insets = new Insets(0, 0, 5, 0);
		gbc_updateButton.gridx = 1;
		gbc_updateButton.gridy = 7;
		panel.add(updateButton, gbc_updateButton);

		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonClicked();
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.gridx = 1;
		gbc_deleteButton.gridy = 8;
		panel.add(deleteButton, gbc_deleteButton);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.anchor = GridBagConstraints.NORTH;
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		searchForCustomerLabel = new JLabel("Customer");
		GridBagConstraints gbc_searchForCustomerLabel = new GridBagConstraints();
		gbc_searchForCustomerLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchForCustomerLabel.anchor = GridBagConstraints.EAST;
		gbc_searchForCustomerLabel.gridx = 0;
		gbc_searchForCustomerLabel.gridy = 0;
		panel_2.add(searchForCustomerLabel, gbc_searchForCustomerLabel);

		customerSearchTextField = new JTextField();
		customerSearchTextField.setText("Search for phone number ...");
		GridBagConstraints gbc_customerSearchTextField = new GridBagConstraints();
		gbc_customerSearchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_customerSearchTextField.gridx = 1;
		gbc_customerSearchTextField.gridy = 0;
		panel_2.add(customerSearchTextField, gbc_customerSearchTextField);
		customerSearchTextField.setColumns(10);

		customerSearchTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (customerSearchTextField.getText().equals("Search for name ...")) {
					customerSearchTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (customerSearchTextField.getText().equals("")) {
					customerSearchTextField.setText("Search for name ...");
				}
			}
		});

		customerSearchTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateCustomerTable(false);

			}
		});

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		customerTable = new JTable();
		scrollPane.setViewportView(customerTable);

		customerTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int selectedRow = customerTable.rowAtPoint(e.getPoint());

				if (selectedRow == lastSelectedRow) {
					customerTable.getSelectionModel().clearSelection();
					clearCustomerTextFields();
					lastSelectedRow = -1;
					deleteButton.setEnabled(false);
					updateButton.setEnabled(false);
				} else {
					lastSelectedRow = selectedRow;
					fillOutCustomerInfo();
					deleteButton.setEnabled(true);
					updateButton.setEnabled(true);
				}
			}
		});

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonClicked();
			}
		});
		backButton.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.EAST;
		gbc_backButton.gridx = 1;
		gbc_backButton.gridy = 2;
		contentPane.add(backButton, gbc_backButton);

		init(employee);
	}

	private void init(Employee employee) {
		lastSelectedRow = -1;
		this.employee = employee;
		customerController = new CustomerController(ConnectionEnvironment.PRODUCTION);
		updateCustomerTable(false);
	}

	private void updateCustomerTable(boolean retrieveNewData) {

		String customerPhoneNumberString = customerSearchTextField.getText();
	    if (customerPhoneNumberString.equals("Search for phone number ...")) {
	        customerPhoneNumberString = "";
	    }

	    List<Customer> customers = customerController.findAllCustomers(customerPhoneNumberString, true);

	    customerTableModel = new CustomerTableModel(customers);
	    customerTable.setModel(customerTableModel);
	}

	private void updateButtonClicked() {

		try {


			String name = nameTextField.getText();
			String email = emailTextField.getText();
			String phoneNumber = phoneNumberTextField.getText();
			String address = addressTextField.getText();
			int zipCode = Integer.parseInt(zipcodeTextField.getText());
			String city = cityTextField.getText();

			// Customer oldCustomer, String name, String email, String phoneNumber, String street, int streetNo, int zipCode, String city

			if (customerController.updateCustomer(getSelectedCustomer(), name, email, phoneNumber, address, zipCode, city)) {
				JOptionPane.showMessageDialog(null, "Customer updated successfully", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				updateCustomerTable(true);
			} else {
				JOptionPane.showMessageDialog(null, "Cannot update Customer", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid Zip Code", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteButtonClicked() {

		try {
			if (customerController.deleteCustomer(getSelectedCustomer().getCustomerId())) {
				JOptionPane.showMessageDialog(null, "Customer deleted successfully", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				updateCustomerTable(true);
				clearCustomerTextFields();
				updateButton.setEnabled(false);
				deleteButton.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "Cannot delete customer", "Error", JOptionPane.ERROR_MESSAGE);

			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid Zip Code", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private Customer getSelectedCustomer() {
		return customerTableModel.getCustomer(customerTable.getSelectedRow());
	}

	private void backButtonClicked() {
		EditCustomerGUI editCustomerGUI = new EditCustomerGUI(employee);
		editCustomerGUI.setVisible(true);
		dispose();
	}

	private void fillOutCustomerInfo() {

		Customer selectedCustomer = customerTableModel.getCustomer(customerTable.getSelectedRow());

		nameTextField.setText(selectedCustomer.getName());
		emailTextField.setText(selectedCustomer.getEmail());
		phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());

		Address customerAddress = selectedCustomer.getAddress();
		addressTextField.setText(customerAddress.getStreet() + " " + customerAddress.getStreetNo());

		cityTextField.setText(customerAddress.getCity());
		zipcodeTextField.setText(Integer.toString(customerAddress.getZipCode()));
	}

	private void clearCustomerTextFields() {
		nameTextField.setText("");
		emailTextField.setText("");
		phoneNumberTextField.setText("");

		addressTextField.setText("");

		zipcodeTextField.setText("");
		cityTextField.setText("");

	}
}
