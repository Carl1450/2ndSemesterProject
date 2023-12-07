package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Control.BookingController;
import Model.Campsite;
import Model.Customer;
import Model.Employee;
import Model.Price;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class BookingInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameTextField;
	private JTextField lastnameField;
	private JTextField phoneNumberField;
	private JTextField emailField;
	private JTextField streetNameField;
	private JTextField streetNumberField;
	private JTextField zipCodeField;
	private JTextField cityField;
	private JTextField startDateField;
	private JTextField endDateField;
	private List<Campsite> campsites;
	private JTable campsiteTable;
	private BookingController bookingController;
	private Employee employee;
	private Customer customer;

	private CampsiteTableModel campsiteTableModel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingInfoGUI frame = new BookingInfoGUI(null);
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
	public BookingInfoGUI(Employee employee) {
		init(employee);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{61, 0, 0};
		gbl_panel.rowHeights = new int[]{16, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel firstnameLabel = new JLabel("Firstname:");
		firstnameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_firstnameLabel = new GridBagConstraints();
		gbc_firstnameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_firstnameLabel.anchor = GridBagConstraints.WEST;
		gbc_firstnameLabel.gridx = 0;
		gbc_firstnameLabel.gridy = 0;
		panel.add(firstnameLabel, gbc_firstnameLabel);

		firstnameTextField = new JTextField();
		GridBagConstraints gbc_firstnameTextField = new GridBagConstraints();
		gbc_firstnameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_firstnameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstnameTextField.gridx = 1;
		gbc_firstnameTextField.gridy = 0;
		panel.add(firstnameTextField, gbc_firstnameTextField);
		firstnameTextField.setColumns(10);

		JLabel lastnameLabel = new JLabel("Lastname:");
		GridBagConstraints gbc_lastnameLabel = new GridBagConstraints();
		gbc_lastnameLabel.anchor = GridBagConstraints.WEST;
		gbc_lastnameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lastnameLabel.gridx = 0;
		gbc_lastnameLabel.gridy = 1;
		panel.add(lastnameLabel, gbc_lastnameLabel);

		lastnameField = new JTextField();
		GridBagConstraints gbc_lastnameField = new GridBagConstraints();
		gbc_lastnameField.insets = new Insets(0, 0, 5, 0);
		gbc_lastnameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastnameField.gridx = 1;
		gbc_lastnameField.gridy = 1;
		panel.add(lastnameField, gbc_lastnameField);
		lastnameField.setColumns(10);

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 0;
		gbc_phoneNumberLabel.gridy = 2;
		panel.add(phoneNumberLabel, gbc_phoneNumberLabel);

		phoneNumberField = new JTextField();
		GridBagConstraints gbc_phoneNumberField = new GridBagConstraints();
		gbc_phoneNumberField.insets = new Insets(0, 0, 5, 0);
		gbc_phoneNumberField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumberField.gridx = 1;
		gbc_phoneNumberField.gridy = 2;
		panel.add(phoneNumberField, gbc_phoneNumberField);
		phoneNumberField.setColumns(10);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 3;
		panel.add(emailLabel, gbc_emailLabel);

		emailField = new JTextField();
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 3;
		panel.add(emailField, gbc_emailField);
		emailField.setColumns(10);

		JLabel streetNameLabel = new JLabel("Street Name:");
		GridBagConstraints gbc_streetNameLabel = new GridBagConstraints();
		gbc_streetNameLabel.anchor = GridBagConstraints.WEST;
		gbc_streetNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_streetNameLabel.gridx = 0;
		gbc_streetNameLabel.gridy = 4;
		panel.add(streetNameLabel, gbc_streetNameLabel);

		streetNameField = new JTextField();
		GridBagConstraints gbc_streetNameField = new GridBagConstraints();
		gbc_streetNameField.insets = new Insets(0, 0, 5, 0);
		gbc_streetNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_streetNameField.gridx = 1;
		gbc_streetNameField.gridy = 4;
		panel.add(streetNameField, gbc_streetNameField);
		streetNameField.setColumns(10);

		JLabel streetNumberLabel = new JLabel("Street Number:");
		GridBagConstraints gbc_streetNumberLabel = new GridBagConstraints();
		gbc_streetNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_streetNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_streetNumberLabel.gridx = 0;
		gbc_streetNumberLabel.gridy = 5;
		panel.add(streetNumberLabel, gbc_streetNumberLabel);

		streetNumberField = new JTextField();
		GridBagConstraints gbc_streetNumberField = new GridBagConstraints();
		gbc_streetNumberField.insets = new Insets(0, 0, 5, 0);
		gbc_streetNumberField.fill = GridBagConstraints.HORIZONTAL;
		gbc_streetNumberField.gridx = 1;
		gbc_streetNumberField.gridy = 5;
		panel.add(streetNumberField, gbc_streetNumberField);
		streetNumberField.setColumns(10);

		JLabel zipCodeLabel = new JLabel("Zipcode:");
		GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
		gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_zipCodeLabel.gridx = 0;
		gbc_zipCodeLabel.gridy = 6;
		panel.add(zipCodeLabel, gbc_zipCodeLabel);

		zipCodeField = new JTextField();
		GridBagConstraints gbc_zipCodeField = new GridBagConstraints();
		gbc_zipCodeField.insets = new Insets(0, 0, 5, 0);
		gbc_zipCodeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_zipCodeField.gridx = 1;
		gbc_zipCodeField.gridy = 6;
		panel.add(zipCodeField, gbc_zipCodeField);
		zipCodeField.setColumns(10);

		JLabel cityLabel = new JLabel("City:");
		GridBagConstraints gbc_cityLabel = new GridBagConstraints();
		gbc_cityLabel.anchor = GridBagConstraints.WEST;
		gbc_cityLabel.insets = new Insets(0, 0, 0, 5);
		gbc_cityLabel.gridx = 0;
		gbc_cityLabel.gridy = 7;
		panel.add(cityLabel, gbc_cityLabel);

		cityField = new JTextField();
		GridBagConstraints gbc_cityField = new GridBagConstraints();
		gbc_cityField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cityField.gridx = 1;
		gbc_cityField.gridy = 7;
		panel.add(cityField, gbc_cityField);
		cityField.setColumns(10);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{61, 0, 0};
		gbl_panel_1.rowHeights = new int[]{16, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		JLabel startDateLabel = new JLabel("Start Date:");
		GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
		gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startDateLabel.anchor = GridBagConstraints.WEST;
		gbc_startDateLabel.gridx = 0;
		gbc_startDateLabel.gridy = 0;
		panel_1.add(startDateLabel, gbc_startDateLabel);

		startDateField = new JTextField();
		GridBagConstraints gbc_startDateField = new GridBagConstraints();
		gbc_startDateField.insets = new Insets(0, 0, 5, 0);
		gbc_startDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_startDateField.gridx = 1;
		gbc_startDateField.gridy = 0;
		panel_1.add(startDateField, gbc_startDateField);
		startDateField.setColumns(10);

		JLabel endDateLabel = new JLabel("End Date:");
		GridBagConstraints gbc_endDateLabel = new GridBagConstraints();
		gbc_endDateLabel.anchor = GridBagConstraints.WEST;
		gbc_endDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_endDateLabel.gridx = 0;
		gbc_endDateLabel.gridy = 1;
		panel_1.add(endDateLabel, gbc_endDateLabel);

		endDateField = new JTextField();
		GridBagConstraints gbc_endDateField = new GridBagConstraints();
		gbc_endDateField.insets = new Insets(0, 0, 5, 0);
		gbc_endDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_endDateField.gridx = 1;
		gbc_endDateField.gridy = 1;
		panel_1.add(endDateField, gbc_endDateField);
		endDateField.setColumns(10);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchButtonClicked();
			}
		});
		searchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.insets = new Insets(0, 0, 5, 0);
		gbc_searchButton.anchor = GridBagConstraints.EAST;
		gbc_searchButton.gridx = 1;
		gbc_searchButton.gridy = 2;
		panel_1.add(searchButton, gbc_searchButton);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		JLabel availableCampsitesLabel = new JLabel("Available Campsites");
		GridBagConstraints gbc_availableCampsitesLabel = new GridBagConstraints();
		gbc_availableCampsitesLabel.anchor = GridBagConstraints.WEST;
		gbc_availableCampsitesLabel.insets = new Insets(0, 0, 5, 0);
		gbc_availableCampsitesLabel.gridx = 0;
		gbc_availableCampsitesLabel.gridy = 0;
		panel_2.add(availableCampsitesLabel, gbc_availableCampsitesLabel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane);

		campsiteTable = new JTable();
		scrollPane.setViewportView(campsiteTable);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.EAST;
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel_2.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonClicked();
			}
		});
		backButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_3.add(backButton);

		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				confirmButtonClicked();
			}
		});
		confirmButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_3.add(confirmButton);

		phoneNumberField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					fillCustomerInfo(phoneNumberField.getText());
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	private void init(Employee employee) {
		this.employee = employee;
		this.bookingController = new BookingController(this.employee);
		bookingController.startBooking();
	}

	private void tableModel() {
		campsiteTableModel = new CampsiteTableModel(campsites);
		campsiteTableModel.setData(campsites);
		campsiteTable.setModel(campsiteTableModel);
	}

	private void searchButtonClicked() {
		String startDate = startDateField.getText();
		String endDate = endDateField.getText();

		if (isDate(startDate) && isDate(endDate)) {
			campsites = bookingController.getAvailableCampsites(startDate,
					endDate);
			tableModel();
		} else {
			JOptionPane.showMessageDialog(contentPane,
					"You have entered an invalid date.\n Format is YYYY-MM-DD");
		}
	}

	private boolean isDate(String dateString) {
		try {
			LocalDate date = LocalDate.parse(dateString);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	private void confirmButtonClicked() {
		String firstName = firstnameTextField.getText();
		String lastName = lastnameField.getText();
		String phoneNumber = phoneNumberField.getText();
		String email = emailField.getText();
		String streetName = streetNameField.getText();
		String streetNumber = streetNumberField.getText();
		String zipCode = zipCodeField.getText();
		String city = cityField.getText();
		String startDate = startDateField.getText();
		String endDate = endDateField.getText();
		int rowIndex = campsiteTable.getSelectedRow();
		Campsite currentCampsite = campsiteTableModel.getCampsite(rowIndex);
		bookingController.addCampsiteToBooking(currentCampsite, startDate,
				endDate);

		FinishBookingGUI finishBookingGUI = new FinishBookingGUI(this.employee,
				this.bookingController);

		finishBookingGUI.setVisible(true);
		dispose();
	}

	private void backButtonClicked() {
		MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}

	private void fillCustomerInfo(String phoneNumber) {
		customer = bookingController.findCustomerByPhoneNumber(phoneNumber);
		if (customer != null) {
			String[] address = customer.getAddress().split(" ");
			streetNameField.setText(address[0]);
			streetNumberField.setText(address[1]);
			cityField.setText(address[2]);
			zipCodeField.setText(address[3]);

			emailField.setText(customer.getEmail());

			String[] name = customer.getName().split(" ");
			firstnameTextField.setText(name[0]);
			lastnameField.setText(name[1]);
		} else {
			streetNameField.setText("");
			streetNumberField.setText("");
			cityField.setText("");
			zipCodeField.setText("");

			emailField.setText("");

			firstnameTextField.setText("");
			lastnameField.setText("");
		}
	}

}
