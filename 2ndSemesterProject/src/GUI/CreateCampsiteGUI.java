package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Control.CampsiteController;
import Database.ConnectionEnvironment;
import Model.Campsite;
import Model.Employee;

public class CreateCampsiteGUI extends JFrame {

	private JPanel contentPane;
	private JTextField siteNumberTextField;
	private JTextField sectionTextField;
	private JTextField roadTextField;
	private JTextField typeTextField;
	private JTextField maxPeopleTextField;
	private JTextField depositTextField;
	private JTextField feeTextField;
	private JTextField searchTextField;
	private Employee employee;
	private int siteNo;
	private String section;
	private String road;
	private String type;
	private int maxPeople;
	private float deposit;
	private float fee;
	private Date effectiveDate;
	private float price;
	private CampsiteController campsiteController;
	private ConnectionEnvironment env = ConnectionEnvironment.PRODUCTION;
	private JTable campsiteTable;
	private JTextField priceTextField;
	private TableRowSorter<TableModel> rowSorter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateCampsiteGUI frame = new CreateCampsiteGUI();
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
	public CreateCampsiteGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 45, 0 };
		gbl_panel.rowHeights = new int[] { 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel siteNumberLabel = new JLabel("Site Number");
		GridBagConstraints gbc_siteNumberLabel = new GridBagConstraints();
		gbc_siteNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_siteNumberLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_siteNumberLabel.gridx = 0;
		gbc_siteNumberLabel.gridy = 0;
		panel.add(siteNumberLabel, gbc_siteNumberLabel);

		siteNumberTextField = new JTextField();
		GridBagConstraints gbc_siteNumberTextField = new GridBagConstraints();
		gbc_siteNumberTextField.insets = new Insets(0, 0, 5, 0);
		gbc_siteNumberTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_siteNumberTextField.gridx = 1;
		gbc_siteNumberTextField.gridy = 0;
		panel.add(siteNumberTextField, gbc_siteNumberTextField);
		siteNumberTextField.setColumns(10);

		JLabel sectionLabel = new JLabel("Section");
		GridBagConstraints gbc_sectionLabel = new GridBagConstraints();
		gbc_sectionLabel.anchor = GridBagConstraints.WEST;
		gbc_sectionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sectionLabel.gridx = 0;
		gbc_sectionLabel.gridy = 1;
		panel.add(sectionLabel, gbc_sectionLabel);

		sectionTextField = new JTextField();
		GridBagConstraints gbc_sectionTextField = new GridBagConstraints();
		gbc_sectionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_sectionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_sectionTextField.gridx = 1;
		gbc_sectionTextField.gridy = 1;
		panel.add(sectionTextField, gbc_sectionTextField);
		sectionTextField.setColumns(10);

		JLabel roadLabel = new JLabel("Road");
		GridBagConstraints gbc_roadLabel = new GridBagConstraints();
		gbc_roadLabel.anchor = GridBagConstraints.WEST;
		gbc_roadLabel.insets = new Insets(0, 0, 5, 5);
		gbc_roadLabel.gridx = 0;
		gbc_roadLabel.gridy = 2;
		panel.add(roadLabel, gbc_roadLabel);

		roadTextField = new JTextField();
		GridBagConstraints gbc_roadTextField = new GridBagConstraints();
		gbc_roadTextField.insets = new Insets(0, 0, 5, 0);
		gbc_roadTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_roadTextField.gridx = 1;
		gbc_roadTextField.gridy = 2;
		panel.add(roadTextField, gbc_roadTextField);
		roadTextField.setColumns(10);

		JLabel typeLabel = new JLabel("Type");
		GridBagConstraints gbc_typeLabel = new GridBagConstraints();
		gbc_typeLabel.anchor = GridBagConstraints.WEST;
		gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_typeLabel.gridx = 0;
		gbc_typeLabel.gridy = 3;
		panel.add(typeLabel, gbc_typeLabel);

		typeTextField = new JTextField();
		GridBagConstraints gbc_typeTextField = new GridBagConstraints();
		gbc_typeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_typeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_typeTextField.gridx = 1;
		gbc_typeTextField.gridy = 3;
		panel.add(typeTextField, gbc_typeTextField);
		typeTextField.setColumns(10);

		JLabel maxPeopleLabel = new JLabel("Max People");
		GridBagConstraints gbc_maxPeopleLabel = new GridBagConstraints();
		gbc_maxPeopleLabel.anchor = GridBagConstraints.WEST;
		gbc_maxPeopleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_maxPeopleLabel.gridx = 0;
		gbc_maxPeopleLabel.gridy = 4;
		panel.add(maxPeopleLabel, gbc_maxPeopleLabel);

		maxPeopleTextField = new JTextField();
		GridBagConstraints gbc_maxPeopleTextField = new GridBagConstraints();
		gbc_maxPeopleTextField.insets = new Insets(0, 0, 5, 0);
		gbc_maxPeopleTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxPeopleTextField.gridx = 1;
		gbc_maxPeopleTextField.gridy = 4;
		panel.add(maxPeopleTextField, gbc_maxPeopleTextField);
		maxPeopleTextField.setColumns(10);

		JLabel depositLabel = new JLabel("Deposit");
		GridBagConstraints gbc_depositLabel = new GridBagConstraints();
		gbc_depositLabel.anchor = GridBagConstraints.WEST;
		gbc_depositLabel.insets = new Insets(0, 0, 5, 5);
		gbc_depositLabel.gridx = 0;
		gbc_depositLabel.gridy = 5;
		panel.add(depositLabel, gbc_depositLabel);

		depositTextField = new JTextField();
		GridBagConstraints gbc_depositTextField = new GridBagConstraints();
		gbc_depositTextField.insets = new Insets(0, 0, 5, 0);
		gbc_depositTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_depositTextField.gridx = 1;
		gbc_depositTextField.gridy = 5;
		panel.add(depositTextField, gbc_depositTextField);
		depositTextField.setColumns(10);

		JLabel feeLabel = new JLabel("Fee");
		GridBagConstraints gbc_feeLabel = new GridBagConstraints();
		gbc_feeLabel.anchor = GridBagConstraints.WEST;
		gbc_feeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_feeLabel.gridx = 0;
		gbc_feeLabel.gridy = 6;
		panel.add(feeLabel, gbc_feeLabel);

		feeTextField = new JTextField();
		GridBagConstraints gbc_feeTextField = new GridBagConstraints();
		gbc_feeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_feeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_feeTextField.gridx = 1;
		gbc_feeTextField.gridy = 6;
		panel.add(feeTextField, gbc_feeTextField);
		feeTextField.setColumns(10);

		JLabel priceLabel = new JLabel("Price");
		GridBagConstraints gbc_priceLabel = new GridBagConstraints();
		gbc_priceLabel.anchor = GridBagConstraints.WEST;
		gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_priceLabel.gridx = 0;
		gbc_priceLabel.gridy = 7;
		panel.add(priceLabel, gbc_priceLabel);

		priceTextField = new JTextField();
		GridBagConstraints gbc_priceTextField = new GridBagConstraints();
		gbc_priceTextField.insets = new Insets(0, 0, 5, 0);
		gbc_priceTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_priceTextField.gridx = 1;
		gbc_priceTextField.gridy = 7;
		panel.add(priceTextField, gbc_priceTextField);
		priceTextField.setColumns(10);

		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButtonClicked();
			}
		});
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 5, 0);
		gbc_createButton.gridx = 1;
		gbc_createButton.gridy = 8;
		panel.add(createButton, gbc_createButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonClicked();
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.insets = new Insets(0, 0, 5, 0);
		gbc_deleteButton.gridx = 1;
		gbc_deleteButton.gridy = 9;
		panel.add(deleteButton, gbc_deleteButton);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		campsiteTable = new JTable();
		scrollPane.setViewportView(campsiteTable);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonClicked();
			}
		});
		panel_1.add(backButton);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.NORTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 30, 30, 30, 30, 58, 58, 45, 0 };
		gbl_panel_3.rowHeights = new int[] { 13, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel searchLabel = new JLabel("Search");
		GridBagConstraints gbc_searchLabel = new GridBagConstraints();
		gbc_searchLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_searchLabel.gridx = 5;
		gbc_searchLabel.gridy = 0;
		panel_3.add(searchLabel, gbc_searchLabel);

		searchTextField = new JTextField();
		searchTextField.setToolTipText("");
		GridBagConstraints gbc_searchTextField = new GridBagConstraints();
		gbc_searchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchTextField.gridx = 6;
		gbc_searchTextField.gridy = 0;
		panel_3.add(searchTextField, gbc_searchTextField);
		searchTextField.setColumns(10);
		
		searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Search for site number ...")) {
                	searchTextField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().equals("")) {
                	searchTextField.setText("Search for site number ...");
                }
            }
        });
		init();
	}

	private void init() {
		campsiteController = new CampsiteController(env);
		List<Campsite> campsites = campsiteController.getCampsites();
		CampsiteCRUDTableModel campsiteCRUDTableModel = new CampsiteCRUDTableModel(campsites);
		campsiteTable.setModel(campsiteCRUDTableModel);
		rowSorter();
	}

	private void rowSorter() {
		rowSorter = new TableRowSorter<>(campsiteTable.getModel());
        campsiteTable.setRowSorter(rowSorter);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
        	@Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	updateFilter();
            }
        }); 
	}

	private void updateFilter() {
		String text = searchTextField.getText();
        if (text.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }


	private void getTextFieldData() {
		try {
			siteNo = Integer.parseInt(siteNumberTextField.getText());
			section = sectionTextField.getText();
			road = roadTextField.getText();
			type = typeTextField.getText();
			maxPeople = Integer.parseInt(maxPeopleTextField.getText());
			deposit = Integer.parseInt(depositTextField.getText());
			fee = Integer.parseInt(feeTextField.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void createButtonClicked() {
		getTextFieldData();
		boolean saved = campsiteController.saveCampsite(siteNo, section, road, type, maxPeople, deposit, fee,
				effectiveDate, price);
		if (saved) {
			JOptionPane.showMessageDialog(this, "Succesfully created campsite");
		} else {
			JOptionPane.showMessageDialog(this, "Error: Failed to create campsite");
		}
	}

	private void deleteButtonClicked() {
	}

	private void backButtonClicked() {
		MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}

}
