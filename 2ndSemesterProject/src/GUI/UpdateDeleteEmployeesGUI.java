package GUI;

import Control.EmployeeController;
import Database.ConnectionEnvironment;
import Model.Address;
import Model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UpdateDeleteEmployeesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneNumberTextField;
	private JPanel panel_1;
	private JButton backButton;
	private JButton updateButton;
	private JPanel panel_2;
	private JLabel searchForEmployeeLabel;
	private JTextField employeeSearchTextField;
	private JButton deleteButton;
	private JScrollPane scrollPane;
	private JTable employeeTable;

	private Employee currentEmployee;

	private EmployeeTableModel employeeTableModel;

	private EmployeeController employeeController;

	private int lastSelectedRow;
	private JLabel addressLabel;
	private JTextField addressTextField;
	private JLabel zipCodeLabel;
	private JLabel cityLabel;
	private JTextField zipcodeTextField;
	private JTextField cityTextField;
	private JLabel roleLabel;
	private JComboBox roleComboBox;

	public UpdateDeleteEmployeesGUI(Employee employee) {

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

		roleLabel = new JLabel("Role:");
		GridBagConstraints gbc_roleLabel = new GridBagConstraints();
		gbc_roleLabel.anchor = GridBagConstraints.WEST;
		gbc_roleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_roleLabel.gridx = 0;
		gbc_roleLabel.gridy = 6;
		panel.add(roleLabel, gbc_roleLabel);

		roleComboBox = new JComboBox();
		GridBagConstraints gbc_roleComboBox = new GridBagConstraints();
		gbc_roleComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_roleComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_roleComboBox.gridx = 1;
		gbc_roleComboBox.gridy = 6;
		panel.add(roleComboBox, gbc_roleComboBox);
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

		searchForEmployeeLabel = new JLabel("Employee");
		GridBagConstraints gbc_searchForEmployeeLabel = new GridBagConstraints();
		gbc_searchForEmployeeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchForEmployeeLabel.anchor = GridBagConstraints.EAST;
		gbc_searchForEmployeeLabel.gridx = 0;
		gbc_searchForEmployeeLabel.gridy = 0;
		panel_2.add(searchForEmployeeLabel, gbc_searchForEmployeeLabel);

		employeeSearchTextField = new JTextField();
		employeeSearchTextField.setText("Search for name ...");
		GridBagConstraints gbc_employeeSearchTextField = new GridBagConstraints();
		gbc_employeeSearchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_employeeSearchTextField.gridx = 1;
		gbc_employeeSearchTextField.gridy = 0;
		panel_2.add(employeeSearchTextField, gbc_employeeSearchTextField);
		employeeSearchTextField.setColumns(10);

		employeeSearchTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (employeeSearchTextField.getText().equals("Search for name ...")) {
					employeeSearchTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (employeeSearchTextField.getText().equals("")) {
					employeeSearchTextField.setText("Search for name ...");
				}
			}
		});

		employeeSearchTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateEmployeeTable(false);

			}
		});

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		employeeTable = new JTable();
		scrollPane.setViewportView(employeeTable);

		employeeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int selectedRow = employeeTable.rowAtPoint(e.getPoint());

				if (selectedRow == lastSelectedRow) {
					employeeTable.getSelectionModel().clearSelection();
					clearemployeeTextFields();
					lastSelectedRow = -1;
					deleteButton.setEnabled(false);
					updateButton.setEnabled(false);
				} else {
					lastSelectedRow = selectedRow;
					fillOutEmployeeInfo();
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
		this.currentEmployee = employee;
		employeeController = new EmployeeController(ConnectionEnvironment.PRODUCTION);
		updateEmployeeTable(false);

		fillOutRoleComboBox();

	}

	private void updateEmployeeTable(boolean retrieveNewData) {

		String employeeNameString = employeeSearchTextField.getText();
		if (employeeNameString.equals("Search for name ...")) {
			employeeNameString = "";
		}

		List<Employee> employees = employeeController.getAllEmployees(true);

		employeeTableModel = new EmployeeTableModel(employees);
		employeeTable.setModel(employeeTableModel);
	}

	private void updateButtonClicked() {

		Employee employee = getSelectedEmployee();

		String newName = nameTextField.getText();
		String newAddress = addressTextField.getText();
		int newZipCode = Integer.parseInt(zipcodeTextField.getText());
		String newCity = cityTextField.getText();
		String newPhoneNumber = phoneNumberTextField.getText();
		String newEmail = emailTextField.getText();
		String newRole = employee.toString();

		if (employeeController.updateEmployee(employee, newName, newAddress, newZipCode, newCity, newPhoneNumber,
				newEmail, newRole)) {
			JOptionPane.showMessageDialog(null, "Employee updated successfully", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			updateEmployeeTable(true);
		} else {
			JOptionPane.showMessageDialog(null, "Cannot update employee", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void deleteButtonClicked() {

		if (employeeController.deleteEmployee(getSelectedEmployee().getId())) {
			JOptionPane.showMessageDialog(null, "Employee deleted successfully", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			updateEmployeeTable(true);
			clearemployeeTextFields();
			updateButton.setEnabled(false);
			deleteButton.setEnabled(false);

		} else {
			JOptionPane.showMessageDialog(null, "Cannot delete employee", "Error", JOptionPane.ERROR_MESSAGE);

		}

	}

	private Employee getSelectedEmployee() {
		return employeeTableModel.getEmployee(employeeTable.getSelectedRow());
	}

	private void backButtonClicked() {
		EditEmployeeGUI editEmployeeGUI = new EditEmployeeGUI(currentEmployee);
		editEmployeeGUI.setVisible(true);
		dispose();
	}

	private void fillOutEmployeeInfo() {

		Employee selectedEmployee = getSelectedEmployee();

		nameTextField.setText(selectedEmployee.getName());
		emailTextField.setText(selectedEmployee.getEmail());
		phoneNumberTextField.setText(selectedEmployee.getPhoneNumber());

		Address employeeAddress = selectedEmployee.getAddress();
		addressTextField.setText(employeeAddress.getStreet() + " " + employeeAddress.getStreetNo());

		cityTextField.setText(employeeAddress.getCity());
		zipcodeTextField.setText(Integer.toString(employeeAddress.getZipCode()));

		roleComboBox.setSelectedItem(selectedEmployee.toString());
	}

	private void clearemployeeTextFields() {
		nameTextField.setText("");
		emailTextField.setText("");
		phoneNumberTextField.setText("");

		addressTextField.setText("");

		zipcodeTextField.setText("");
		cityTextField.setText("");

	}

	private void fillOutRoleComboBox() {
		String[] roles = { "SalesAssistant", "Receptionist", "Janitor", "Admin" };
		for (String role : roles) {
			roleComboBox.addItem(role);
		}
	}

}
