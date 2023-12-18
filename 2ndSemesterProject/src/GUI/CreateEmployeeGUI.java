package GUI;

import Control.EmployeeController;
import Database.ConnectionEnvironment;
import Model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateEmployeeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField phoneNumberTextField;
	private JTextField emailTextField;
	private JTextField addressTextField;
	private JTextField zipCodeTextField;
	private JTextField cityTextField;

	private EmployeeController employeeController;
	private Employee employee;
	private EditEmployeeGUI editEmployeeGUI;
	private JTextField cprTextField;
	private JTextField passwordTextField;

	private JComboBox roleComboBox;

	public CreateEmployeeGUI(Employee employee) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel createEmployeeLabel = new JLabel("Create Employee");
		createEmployeeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(createEmployeeLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 25, 25, 25, 25, 25, 25, 0, 0, 0, 25 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel nameLabel = new JLabel("Name:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		panel_1.add(nameLabel, gbc_nameLabel);

		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 0;
		panel_1.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 0;
		gbc_phoneNumberLabel.gridy = 1;
		panel_1.add(phoneNumberLabel, gbc_phoneNumberLabel);

		phoneNumberTextField = new JTextField();
		GridBagConstraints gbc_phoneNumberTextField = new GridBagConstraints();
		gbc_phoneNumberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_phoneNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumberTextField.gridx = 1;
		gbc_phoneNumberTextField.gridy = 1;
		panel_1.add(phoneNumberTextField, gbc_phoneNumberTextField);
		phoneNumberTextField.setColumns(10);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 2;
		panel_1.add(emailLabel, gbc_emailLabel);

		emailTextField = new JTextField();
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.insets = new Insets(0, 0, 5, 0);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 1;
		gbc_emailTextField.gridy = 2;
		panel_1.add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);

		JLabel addressLabel = new JLabel("Address:");
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_addressLabel.gridx = 0;
		gbc_addressLabel.gridy = 3;
		panel_1.add(addressLabel, gbc_addressLabel);

		addressTextField = new JTextField();
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.insets = new Insets(0, 0, 5, 0);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 1;
		gbc_addressTextField.gridy = 3;
		panel_1.add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);

		JLabel zipCodeLabel = new JLabel("Zip Code:");
		GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
		gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_zipCodeLabel.gridx = 0;
		gbc_zipCodeLabel.gridy = 4;
		panel_1.add(zipCodeLabel, gbc_zipCodeLabel);

		zipCodeTextField = new JTextField();
		GridBagConstraints gbc_zipCodeTextField = new GridBagConstraints();
		gbc_zipCodeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_zipCodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zipCodeTextField.gridx = 1;
		gbc_zipCodeTextField.gridy = 4;
		panel_1.add(zipCodeTextField, gbc_zipCodeTextField);
		zipCodeTextField.setColumns(10);

		JLabel cityLabel = new JLabel("City:");
		GridBagConstraints gbc_cityLabel = new GridBagConstraints();
		gbc_cityLabel.anchor = GridBagConstraints.WEST;
		gbc_cityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cityLabel.gridx = 0;
		gbc_cityLabel.gridy = 5;
		panel_1.add(cityLabel, gbc_cityLabel);

		cityTextField = new JTextField();
		GridBagConstraints gbc_cityTextField = new GridBagConstraints();
		gbc_cityTextField.insets = new Insets(0, 0, 5, 0);
		gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityTextField.gridx = 1;
		gbc_cityTextField.gridy = 5;
		panel_1.add(cityTextField, gbc_cityTextField);
		cityTextField.setColumns(10);

		JLabel roleLabel = new JLabel("Role:");
		GridBagConstraints gbc_roleLabel = new GridBagConstraints();
		gbc_roleLabel.anchor = GridBagConstraints.WEST;
		gbc_roleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_roleLabel.gridx = 0;
		gbc_roleLabel.gridy = 6;
		panel_1.add(roleLabel, gbc_roleLabel);

		roleComboBox = new JComboBox();
		GridBagConstraints gbc_roleComboBox = new GridBagConstraints();
		gbc_roleComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_roleComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_roleComboBox.gridx = 1;
		gbc_roleComboBox.gridy = 6;
		panel_1.add(roleComboBox, gbc_roleComboBox);

		JLabel cprLabel = new JLabel("CPR-Number:");
		GridBagConstraints gbc_cprLabel = new GridBagConstraints();
		gbc_cprLabel.anchor = GridBagConstraints.WEST;
		gbc_cprLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cprLabel.gridx = 0;
		gbc_cprLabel.gridy = 7;
		panel_1.add(cprLabel, gbc_cprLabel);

		cprTextField = new JTextField();
		GridBagConstraints gbc_cprTextField = new GridBagConstraints();
		gbc_cprTextField.insets = new Insets(0, 0, 5, 0);
		gbc_cprTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cprTextField.gridx = 1;
		gbc_cprTextField.gridy = 7;
		panel_1.add(cprTextField, gbc_cprTextField);
		cprTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.WEST;
		gbc_passwordLabel.insets = new Insets(0, 0, 0, 5);
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 8;
		panel_1.add(passwordLabel, gbc_passwordLabel);

		passwordTextField = new JTextField();
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordTextField.gridx = 1;
		gbc_passwordTextField.gridy = 8;
		panel_1.add(passwordTextField, gbc_passwordTextField);
		passwordTextField.setColumns(10);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked();
			}
		});
		panel_2.add(cancelButton);

		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButtonClicked();
			}
		});
		panel_2.add(createButton);

		init(employee);

	}

	private void init(Employee employee) {
		this.employee = employee;
		employeeController = new EmployeeController(ConnectionEnvironment.PRODUCTION);

		fillOutRoleComboBox();
	}

	private void fillOutRoleComboBox() {
		String[] roles = { "SalesAssistant", "Receptionist", "Janitor", "Admin" };
		for (String role : roles) {
			roleComboBox.addItem(role);
		}
	}

	private void createButtonClicked() {

		String name = nameTextField.getText().trim();
		String phoneNumber = phoneNumberTextField.getText().trim();
		String email = emailTextField.getText().trim();
		String address = addressTextField.getText().trim();
		String zipcode = zipCodeTextField.getText().trim();
		String city = cityTextField.getText().trim();
		String role = (String) roleComboBox.getSelectedItem();
		String cprNo = cprTextField.getText().trim();
		String password = passwordTextField.getText().trim();

		if (allFieldsFilledOut() && isValidName() && isValidAddress()) {

			int employeeId = employeeController.saveEmployee(name, phoneNumber, email, address, zipcode, city, role,
					cprNo, password);
			if (employeeId != -1) {
				JOptionPane.showMessageDialog(null,
						"Employee Successfully created in the system. Their id is: " + employeeId, "Success",
						JOptionPane.INFORMATION_MESSAGE);
				resetTextFields();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cannot create customer, missing information.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void resetTextFields() {
		nameTextField.setText("");
		phoneNumberTextField.setText("");
		emailTextField.setText("");
		addressTextField.setText("");
		zipCodeTextField.setText("");
		cityTextField.setText("");
		roleComboBox.setSelectedIndex(0);
		cprTextField.setText("");
		passwordTextField.setText("");
	}

	private boolean allFieldsFilledOut() {
		boolean valid = true;

		String name = nameTextField.getText().trim();
		String phoneNumber = phoneNumberTextField.getText().trim();
		String email = emailTextField.getText().trim();
		String address = addressTextField.getText().trim();
		String zipcode = zipCodeTextField.getText().trim();
		String city = cityTextField.getText().trim();
		String role = (String) roleComboBox.getSelectedItem();
		String cprNo = cprTextField.getText().trim();
		String password = passwordTextField.getText().trim();

		if (name.equals("") || phoneNumber.equals("") || email.equals("") || address.equals("") || zipcode.equals("")
				|| city.equals("") || role.equals("") || cprNo.equals("") || password.equals("")) {
			valid = false;
		}

		return valid;
	}

	private boolean isValidName() {
		String fullName = nameTextField.getText();
		String[] nameParts = fullName.split(" ");
		return nameParts.length == 2 && !nameParts[0].isEmpty() && !nameParts[1].isEmpty();
	}

	private boolean isValidAddress() {
		String fullAddress = addressTextField.getText();
		String[] addressParts = fullAddress.split(" ");
		return addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty();
	}

	private void cancelButtonClicked() {
		editEmployeeGUI = new EditEmployeeGUI(employee);
		editEmployeeGUI.setVisible(true);
		dispose();
	}

}
