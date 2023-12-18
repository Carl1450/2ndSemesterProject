package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Control.CustomerController;
import Database.ConnectionEnvironment;
import Model.Customer;
import Model.Employee;

public class CreateCustomerGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField phoneNumberTextField;
    private JTextField emailTextField;
    private JTextField addressTextField;
    private JTextField zipCodeTextField;
    private JTextField cityTextField;

    private CustomerController customerController;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private int zipCode;
    private String city;
    private Employee employee;
    private EditCustomerGUI editCustomerGUI;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateCustomerGUI frame = new CreateCustomerGUI(null);
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
    public CreateCustomerGUI(Employee employee) {
        this.employee = employee;
        customerController = new CustomerController(ConnectionEnvironment.PRODUCTION);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel createCustomerLabel = new JLabel("Create Customer");
        createCustomerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        panel.add(createCustomerLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0, 0};
        gbl_panel_1.rowHeights = new int[]{25, 25, 25, 25, 25, 25, 25};
        gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
        gbc_cityLabel.insets = new Insets(0, 0, 0, 5);
        gbc_cityLabel.gridx = 0;
        gbc_cityLabel.gridy = 5;
        panel_1.add(cityLabel, gbc_cityLabel);

        cityTextField = new JTextField();
        GridBagConstraints gbc_cityTextField = new GridBagConstraints();
        gbc_cityTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_cityTextField.gridx = 1;
        gbc_cityTextField.gridy = 5;
        panel_1.add(cityTextField, gbc_cityTextField);
        cityTextField.setColumns(10);

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
    }

    private void getTextFieldData() {
        name = nameTextField.getText();
        phoneNumber = phoneNumberTextField.getText();
        email = emailTextField.getText();
        address = addressTextField.getText();

        try {
            zipCode = Integer.parseInt(zipCodeTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        city = cityTextField.getText();

    }

    private void createButtonClicked() {
        getTextFieldData();

        if (!isValidName(name)) {
            showErrorMessage("Please enter a valid name with both first name and last name.");
            return;
        }

        if (!isValidAddress(address)) {
            showErrorMessage("Please enter valid address with street name and number.");
            return;
        }


        boolean saved = customerController.saveCustomerToDB(name, phoneNumber, email, address, zipCode, city);

        if (saved) {
            showMessageDialog("Customer successfully saved.");
            editCustomerGUI = new EditCustomerGUI(employee);
            editCustomerGUI.setVisible(true);
            dispose();
        } else {
            showErrorMessage("Failed to save customer.");
        }
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidName(String fullName) {
        String[] nameParts = fullName.split(" ");
        return nameParts.length == 2 && !nameParts[0].isEmpty() && !nameParts[1].isEmpty();
    }

    private boolean isValidAddress(String fullAddress) {
        String[] addressParts = fullAddress.split(" ");
        return addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty();
    }

    private void cancelButtonClicked() {
        editCustomerGUI = new EditCustomerGUI(employee);
        editCustomerGUI.setVisible(true);
        dispose();
    }

}
