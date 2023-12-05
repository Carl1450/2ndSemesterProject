package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField employeeNumberTextField;
	private JTextField employeePasswordTextField;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panel_1.add(panel_3, gbc_panel_3);
		
		JLabel employeeNumberLabel = new JLabel("Employee Number");
		GridBagConstraints gbc_employeeNumberLabel = new GridBagConstraints();
		gbc_employeeNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_employeeNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_employeeNumberLabel.gridx = 1;
		gbc_employeeNumberLabel.gridy = 1;
		panel_1.add(employeeNumberLabel, gbc_employeeNumberLabel);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);
		
		employeeNumberTextField = new JTextField();
		GridBagConstraints gbc_employeeNumberTextField = new GridBagConstraints();
		gbc_employeeNumberTextField.insets = new Insets(0, 0, 5, 5);
		gbc_employeeNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_employeeNumberTextField.gridx = 1;
		gbc_employeeNumberTextField.gridy = 2;
		panel_1.add(employeeNumberTextField, gbc_employeeNumberTextField);
		employeeNumberTextField.setColumns(10);
		
		JLabel employeePasswordLabel = new JLabel("Password");
		GridBagConstraints gbc_employeePasswordLabel = new GridBagConstraints();
		gbc_employeePasswordLabel.anchor = GridBagConstraints.WEST;
		gbc_employeePasswordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_employeePasswordLabel.gridx = 1;
		gbc_employeePasswordLabel.gridy = 3;
		panel_1.add(employeePasswordLabel, gbc_employeePasswordLabel);
		
		employeePasswordTextField = new JTextField();
		GridBagConstraints gbc_employeePasswordTextField = new GridBagConstraints();
		gbc_employeePasswordTextField.insets = new Insets(0, 0, 0, 5);
		gbc_employeePasswordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_employeePasswordTextField.gridx = 1;
		gbc_employeePasswordTextField.gridy = 4;
		panel_1.add(employeePasswordTextField, gbc_employeePasswordTextField);
		employeePasswordTextField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(cancelButton);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(loginButton);
	}

}
