package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class FindUpdateCustomerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField phoneNumberTextField;

	private CustomerController customerController;
	private Customer customer;
	private UpdateCustomerGUI updateCustomerGUI;
	private Employee employee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindUpdateCustomerGUI frame = new FindUpdateCustomerGUI();
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
	public FindUpdateCustomerGUI() {
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

		JLabel findCustomerLabel = new JLabel("Find Customer");
		findCustomerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(findCustomerLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		phoneNumberTextField = new JTextField();
		phoneNumberTextField.setBounds(218, 65, 145, 26);
		panel_1.add(phoneNumberTextField);
		phoneNumberTextField.setColumns(10);

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		phoneNumberLabel.setBounds(110, 70, 96, 16);
		panel_1.add(phoneNumberLabel);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonClicked();
			}
		});
		panel_2.add(backButton);

		JButton findButton = new JButton("Find");
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findButtonClicked();
			}
		});
		panel_2.add(findButton);
	}

	private void findButtonClicked() {
		String phoneNumber = phoneNumberTextField.getText();

		customer = customerController.findCustomerByPhoneNumber(phoneNumber);

		if (customer != null) {
			updateCustomerGUI = new UpdateCustomerGUI(customer);
			updateCustomerGUI.setVisible(true);
			dispose();
		} else {
			showErrorMessage("Incorrect phone number");
		}

	}

	private void backButtonClicked() {
		EditCustomerGUI editCustomerGUI = new EditCustomerGUI(employee);
		editCustomerGUI.setVisible(true);
		dispose();
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
