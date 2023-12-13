package GUI;

import Control.EmployeeController;
import Database.ConnectionEnvironment;
import Model.Employee;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField employeeIdTextField;
	private EmployeeController employeeController;
	private JPasswordField employeePasswordTextField;

	private ConnectionEnvironment env = ConnectionEnvironment.PRODUCTION;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		employeeController = new EmployeeController(env);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel employeeLoginLabel = new JLabel("Employee Login");
		employeeLoginLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		panel.add(employeeLoginLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panel_1.add(panel_3, gbc_panel_3);

		JLabel employeeIdLabel = new JLabel("Employee ID");
		GridBagConstraints gbc_employeeIdLabel = new GridBagConstraints();
		gbc_employeeIdLabel.anchor = GridBagConstraints.WEST;
		gbc_employeeIdLabel.insets = new Insets(0, 0, 5, 5);
		gbc_employeeIdLabel.gridx = 1;
		gbc_employeeIdLabel.gridy = 1;
		panel_1.add(employeeIdLabel, gbc_employeeIdLabel);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);

		employeeIdTextField = new JTextField();
		GridBagConstraints gbc_employeeIdTextField = new GridBagConstraints();
		gbc_employeeIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_employeeIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_employeeIdTextField.gridx = 1;
		gbc_employeeIdTextField.gridy = 2;
		panel_1.add(employeeIdTextField, gbc_employeeIdTextField);
		employeeIdTextField.setColumns(10);

		JLabel employeePasswordLabel = new JLabel("Password");
		GridBagConstraints gbc_employeePasswordLabel = new GridBagConstraints();
		gbc_employeePasswordLabel.anchor = GridBagConstraints.WEST;
		gbc_employeePasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_employeePasswordLabel.gridx = 1;
		gbc_employeePasswordLabel.gridy = 3;
		panel_1.add(employeePasswordLabel, gbc_employeePasswordLabel);

		employeePasswordTextField = new JPasswordField();
		GridBagConstraints gbc_employeePasswordTextField = new GridBagConstraints();
		gbc_employeePasswordTextField.insets = new Insets(0, 0, 0, 5);
		gbc_employeePasswordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_employeePasswordTextField.gridx = 1;
		gbc_employeePasswordTextField.gridy = 4;
		panel_1.add(employeePasswordTextField, gbc_employeePasswordTextField);
		enterKey();

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(cancelButton);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButtonClicked();
			}
		});
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(loginButton);
	}

	private void loginButtonClicked() {
		String employeeId = employeeIdTextField.getText().trim();
		String employeePassword = new String(employeePasswordTextField.getPassword());

		try {
			if (employeeId.isEmpty() || employeePassword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Type in Employee ID and Password", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				Employee employee = employeeController.findEmployeeById(Integer.parseInt(employeeId));
				if (employeeController.validateLogin(employee, employeePassword) == true) {
					MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
					mainMenuGUI.setVisible(true);
					dispose();
				} else {
					employeeIdTextField.setText("");
					employeePasswordTextField.setText("");
					JOptionPane.showMessageDialog(this, "Invalid Employee ID or Password", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (NumberFormatException e) {
			employeeIdTextField.setText("");
			employeePasswordTextField.setText("");
			JOptionPane.showMessageDialog(this, "Invalid Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void enterKey() {
		employeePasswordTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButtonClicked();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

}
