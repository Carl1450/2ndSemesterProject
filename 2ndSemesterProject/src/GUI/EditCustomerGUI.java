package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Employee;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class EditCustomerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MainMenuGUI mainMenuGUI;
	private Employee employee;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditCustomerGUI frame = new EditCustomerGUI(null);
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
	public EditCustomerGUI(Employee employee) {
		this.employee = employee;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel editCustomerLabel = new JLabel("Edit Customer");
		editCustomerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(editCustomerLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JButton createCustomerButton = new JButton("New Customer");
		createCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createCustomerButtonClicked();
			}
		});
		GridBagConstraints gbc_createCustomerButton = new GridBagConstraints();
		gbc_createCustomerButton.insets = new Insets(0, 0, 5, 0);
		gbc_createCustomerButton.gridx = 0;
		gbc_createCustomerButton.gridy = 1;
		panel_1.add(createCustomerButton, gbc_createCustomerButton);

		JButton updateCustomerButton = new JButton("Update Customer");
		GridBagConstraints gbc_updateCustomerButton = new GridBagConstraints();
		gbc_updateCustomerButton.insets = new Insets(0, 0, 5, 0);
		gbc_updateCustomerButton.gridx = 0;
		gbc_updateCustomerButton.gridy = 2;
		panel_1.add(updateCustomerButton, gbc_updateCustomerButton);

		JButton deleteCustomerButton = new JButton("Delete Customer");
		deleteCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCustomerButtonClicked();
			}
		});
		GridBagConstraints gbc_deleteCustomerButton = new GridBagConstraints();
		gbc_deleteCustomerButton.gridx = 0;
		gbc_deleteCustomerButton.gridy = 3;
		panel_1.add(deleteCustomerButton, gbc_deleteCustomerButton);

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
	}

	private void backButtonClicked() {
		mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}

	private void createCustomerButtonClicked() {
		CreateCustomerGUI createCustomerGUI = new CreateCustomerGUI(employee);
		createCustomerGUI.setVisible(true);
		dispose();
	}

	private void updateCustomerButtonClicked() {

	}

	private void deleteCustomerButtonClicked() {

	}

}
