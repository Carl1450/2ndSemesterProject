package GUI;

import Model.Employee;
import com.sun.tools.javac.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditProductsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField barcodeTextField;
	private JTextField nameTextField;
	private JTextField stockNumberTextField;
	private JPanel panel_1;
	private JButton backButton;
	private JButton createUpdateButton;
	private JPanel panel_2;
	private JLabel lblNewLabel_3;
	private JTextField productSearchTextField;
	private JButton deleteButton;
	private JScrollPane scrollPane;
	private JTable table;

	private Employee employee;

	/**
	 * Create the frame.
	 */
	public EditProductsGUI(Employee employee) {
		this.employee = employee;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 259, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel BarcodeLabel = new JLabel("Barcode");
		GridBagConstraints gbc_BarcodeLabel = new GridBagConstraints();
		gbc_BarcodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_BarcodeLabel.anchor = GridBagConstraints.WEST;
		gbc_BarcodeLabel.gridx = 0;
		gbc_BarcodeLabel.gridy = 0;
		panel.add(BarcodeLabel, gbc_BarcodeLabel);
		
		barcodeTextField = new JTextField();
		GridBagConstraints gbc_barcodeTextField = new GridBagConstraints();
		gbc_barcodeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_barcodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_barcodeTextField.gridx = 1;
		gbc_barcodeTextField.gridy = 0;
		panel.add(barcodeTextField, gbc_barcodeTextField);
		barcodeTextField.setColumns(10);
		
		JLabel NameLabel = new JLabel("Name");
		GridBagConstraints gbc_NameLabel = new GridBagConstraints();
		gbc_NameLabel.anchor = GridBagConstraints.WEST;
		gbc_NameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_NameLabel.gridx = 0;
		gbc_NameLabel.gridy = 1;
		panel.add(NameLabel, gbc_NameLabel);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 1;
		panel.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		
		JLabel stockNumberLabel = new JLabel("Stock number");
		GridBagConstraints gbc_stockNumberLabel = new GridBagConstraints();
		gbc_stockNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_stockNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stockNumberLabel.gridx = 0;
		gbc_stockNumberLabel.gridy = 2;
		panel.add(stockNumberLabel, gbc_stockNumberLabel);
		
		stockNumberTextField = new JTextField();
		GridBagConstraints gbc_stockNumberTextField = new GridBagConstraints();
		gbc_stockNumberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_stockNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_stockNumberTextField.gridx = 1;
		gbc_stockNumberTextField.gridy = 2;
		panel.add(stockNumberTextField, gbc_stockNumberTextField);
		stockNumberTextField.setColumns(10);
		
		createUpdateButton = new JButton("Create");
		createUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createUpdateButtonClicked();
			}
		});
		GridBagConstraints gbc_createUpdateButton = new GridBagConstraints();
		gbc_createUpdateButton.insets = new Insets(0, 0, 5, 0);
		gbc_createUpdateButton.gridx = 1;
		gbc_createUpdateButton.gridy = 3;
		panel.add(createUpdateButton, gbc_createUpdateButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonClicked();
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.gridx = 1;
		gbc_deleteButton.gridy = 4;
		panel.add(deleteButton, gbc_deleteButton);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
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
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		lblNewLabel_3 = new JLabel("Products");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panel_2.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		productSearchTextField = new JTextField();
		productSearchTextField.setText("Seach for barcode ...");
		GridBagConstraints gbc_productSearchTextField = new GridBagConstraints();
		gbc_productSearchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_productSearchTextField.gridx = 1;
		gbc_productSearchTextField.gridy = 0;
		panel_2.add(productSearchTextField, gbc_productSearchTextField);
		productSearchTextField.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
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
	}

	private void createUpdateButtonClicked() {

	}

	private void deleteButtonClicked() {

	}

	private void backButtonClicked() {
		MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}

}
