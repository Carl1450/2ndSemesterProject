package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class FinishBookingGUI extends JFrame {

	private JLabel insertFirstnameLabel;
	private JLabel insertLastnameLabel;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel insertPhoneNumberLabel;
	private JLabel insertEmailLabel;
	private JLabel insertStreetNameLabel;
	private JLabel insertStreetNumberLabel;
	private JLabel insertZipcodeLabel;
	private JLabel insertCityLabel;
	private JLabel insertStartDateLabel;
	private JLabel insertEndDateLabel;
	private JLabel insertCampsiteLabel;
	private JLabel insertPriceLabel;
	private JLabel insertEmployeeLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinishBookingGUI frame = new FinishBookingGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FinishBookingGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 215, 0, 0 };
		gbl_panel.rowHeights = new int[] { 16, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 0, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel.add(panel_5, gbc_panel_5);

		JLabel customerInfoLabel = new JLabel("Customer Information");
		panel_5.add(customerInfoLabel);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 0;
		panel.add(panel_6, gbc_panel_6);

		JLabel bookingInfoLabel = new JLabel("Booking Information");
		panel_6.add(bookingInfoLabel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 102, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel firstnameLabel = new JLabel("Firstname:");
		GridBagConstraints gbc_firstnameLabel = new GridBagConstraints();
		gbc_firstnameLabel.anchor = GridBagConstraints.WEST;
		gbc_firstnameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_firstnameLabel.gridx = 0;
		gbc_firstnameLabel.gridy = 0;
		panel_3.add(firstnameLabel, gbc_firstnameLabel);

		insertFirstnameLabel = new JLabel("");
		GridBagConstraints gbc_insertFirstnameLabel = new GridBagConstraints();
		gbc_insertFirstnameLabel.anchor = GridBagConstraints.WEST;
		gbc_insertFirstnameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertFirstnameLabel.gridx = 1;
		gbc_insertFirstnameLabel.gridy = 0;
		// insertFirstnameLabel.setText(firstName);
		panel_3.add(insertFirstnameLabel, gbc_insertFirstnameLabel);

		JLabel lastnameLabel = new JLabel("Lastname:");
		GridBagConstraints gbc_lastnameLabel = new GridBagConstraints();
		gbc_lastnameLabel.anchor = GridBagConstraints.WEST;
		gbc_lastnameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lastnameLabel.gridx = 0;
		gbc_lastnameLabel.gridy = 1;
		panel_3.add(lastnameLabel, gbc_lastnameLabel);

		insertLastnameLabel = new JLabel("");
		GridBagConstraints gbc_insertLastnameLabel = new GridBagConstraints();
		gbc_insertLastnameLabel.anchor = GridBagConstraints.WEST;
		gbc_insertLastnameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertLastnameLabel.gridx = 1;
		gbc_insertLastnameLabel.gridy = 1;
		panel_3.add(insertLastnameLabel, gbc_insertLastnameLabel);
		// setLastNameLabel();

		JLabel phoneNumberLabel = new JLabel("Phone Number:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 0;
		gbc_phoneNumberLabel.gridy = 2;
		panel_3.add(phoneNumberLabel, gbc_phoneNumberLabel);

		insertPhoneNumberLabel = new JLabel("");
		GridBagConstraints gbc_insertPhoneNumberLabel = new GridBagConstraints();
		gbc_insertPhoneNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_insertPhoneNumberLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertPhoneNumberLabel.gridx = 1;
		gbc_insertPhoneNumberLabel.gridy = 2;
		panel_3.add(insertPhoneNumberLabel, gbc_insertPhoneNumberLabel);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 3;
		panel_3.add(emailLabel, gbc_emailLabel);

		insertEmailLabel = new JLabel("");
		GridBagConstraints gbc_insertEmailLabel = new GridBagConstraints();
		gbc_insertEmailLabel.anchor = GridBagConstraints.WEST;
		gbc_insertEmailLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertEmailLabel.gridx = 1;
		gbc_insertEmailLabel.gridy = 3;
		panel_3.add(insertEmailLabel, gbc_insertEmailLabel);

		JLabel streetNameLabel = new JLabel("Street Name:");
		GridBagConstraints gbc_streetNameLabel = new GridBagConstraints();
		gbc_streetNameLabel.anchor = GridBagConstraints.WEST;
		gbc_streetNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_streetNameLabel.gridx = 0;
		gbc_streetNameLabel.gridy = 4;
		panel_3.add(streetNameLabel, gbc_streetNameLabel);

		insertStreetNameLabel = new JLabel("");
		GridBagConstraints gbc_insertStreetNameLabel = new GridBagConstraints();
		gbc_insertStreetNameLabel.anchor = GridBagConstraints.WEST;
		gbc_insertStreetNameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertStreetNameLabel.gridx = 1;
		gbc_insertStreetNameLabel.gridy = 4;
		panel_3.add(insertStreetNameLabel, gbc_insertStreetNameLabel);

		JLabel streetNumberLabel = new JLabel("Street Number:");
		GridBagConstraints gbc_streetNumberLabel = new GridBagConstraints();
		gbc_streetNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_streetNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_streetNumberLabel.gridx = 0;
		gbc_streetNumberLabel.gridy = 5;
		panel_3.add(streetNumberLabel, gbc_streetNumberLabel);

		insertStreetNumberLabel = new JLabel("");
		GridBagConstraints gbc_insertStreetNumberLabel = new GridBagConstraints();
		gbc_insertStreetNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_insertStreetNumberLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertStreetNumberLabel.gridx = 1;
		gbc_insertStreetNumberLabel.gridy = 5;
		panel_3.add(insertStreetNumberLabel, gbc_insertStreetNumberLabel);

		JLabel zipCodeLabel = new JLabel("Zipcode:");
		GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
		gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_zipCodeLabel.gridx = 0;
		gbc_zipCodeLabel.gridy = 6;
		panel_3.add(zipCodeLabel, gbc_zipCodeLabel);

		insertZipcodeLabel = new JLabel("");
		GridBagConstraints gbc_insertZipcodeLabel = new GridBagConstraints();
		gbc_insertZipcodeLabel.anchor = GridBagConstraints.WEST;
		gbc_insertZipcodeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertZipcodeLabel.gridx = 1;
		gbc_insertZipcodeLabel.gridy = 6;
		panel_3.add(insertZipcodeLabel, gbc_insertZipcodeLabel);

		JLabel cityLabel = new JLabel("City:");
		GridBagConstraints gbc_cityLabel = new GridBagConstraints();
		gbc_cityLabel.anchor = GridBagConstraints.WEST;
		gbc_cityLabel.insets = new Insets(0, 0, 0, 5);
		gbc_cityLabel.gridx = 0;
		gbc_cityLabel.gridy = 7;
		panel_3.add(cityLabel, gbc_cityLabel);

		insertCityLabel = new JLabel("");
		GridBagConstraints gbc_insertCityLabel = new GridBagConstraints();
		gbc_insertCityLabel.anchor = GridBagConstraints.WEST;
		gbc_insertCityLabel.gridx = 1;
		gbc_insertCityLabel.gridy = 7;
		panel_3.add(insertCityLabel, gbc_insertCityLabel);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_1.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 115, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel startDateLabel = new JLabel("Start Date:");
		GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
		gbc_startDateLabel.anchor = GridBagConstraints.WEST;
		gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startDateLabel.gridx = 0;
		gbc_startDateLabel.gridy = 0;
		panel_4.add(startDateLabel, gbc_startDateLabel);

		insertStartDateLabel = new JLabel("");
		GridBagConstraints gbc_insertStartDateLabel = new GridBagConstraints();
		gbc_insertStartDateLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_insertStartDateLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertStartDateLabel.gridx = 1;
		gbc_insertStartDateLabel.gridy = 0;
		panel_4.add(insertStartDateLabel, gbc_insertStartDateLabel);

		JLabel endDateLabel = new JLabel("End Date:");
		GridBagConstraints gbc_endDateLabel = new GridBagConstraints();
		gbc_endDateLabel.anchor = GridBagConstraints.WEST;
		gbc_endDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_endDateLabel.gridx = 0;
		gbc_endDateLabel.gridy = 1;
		panel_4.add(endDateLabel, gbc_endDateLabel);

		insertEndDateLabel = new JLabel("");
		GridBagConstraints gbc_insertEndDateLabel = new GridBagConstraints();
		gbc_insertEndDateLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_insertEndDateLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertEndDateLabel.gridx = 1;
		gbc_insertEndDateLabel.gridy = 1;
		panel_4.add(insertEndDateLabel, gbc_insertEndDateLabel);

		JLabel campsiteLabel = new JLabel("Campsite:");
		GridBagConstraints gbc_campsiteLabel = new GridBagConstraints();
		gbc_campsiteLabel.anchor = GridBagConstraints.WEST;
		gbc_campsiteLabel.insets = new Insets(0, 0, 5, 5);
		gbc_campsiteLabel.gridx = 0;
		gbc_campsiteLabel.gridy = 2;
		panel_4.add(campsiteLabel, gbc_campsiteLabel);

		insertCampsiteLabel = new JLabel("");
		GridBagConstraints gbc_insertCampsiteLabel = new GridBagConstraints();
		gbc_insertCampsiteLabel.anchor = GridBagConstraints.WEST;
		gbc_insertCampsiteLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertCampsiteLabel.gridx = 1;
		gbc_insertCampsiteLabel.gridy = 2;
		panel_4.add(insertCampsiteLabel, gbc_insertCampsiteLabel);

		JLabel priceLabel = new JLabel("Price:");
		GridBagConstraints gbc_priceLabel = new GridBagConstraints();
		gbc_priceLabel.anchor = GridBagConstraints.WEST;
		gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_priceLabel.gridx = 0;
		gbc_priceLabel.gridy = 3;
		panel_4.add(priceLabel, gbc_priceLabel);

		insertPriceLabel = new JLabel("");
		GridBagConstraints gbc_insertPriceLabel = new GridBagConstraints();
		gbc_insertPriceLabel.anchor = GridBagConstraints.WEST;
		gbc_insertPriceLabel.insets = new Insets(0, 0, 5, 0);
		gbc_insertPriceLabel.gridx = 1;
		gbc_insertPriceLabel.gridy = 3;
		panel_4.add(insertPriceLabel, gbc_insertPriceLabel);

		JLabel employeeLabel = new JLabel("Employee:");
		GridBagConstraints gbc_employeeLabel = new GridBagConstraints();
		gbc_employeeLabel.anchor = GridBagConstraints.WEST;
		gbc_employeeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_employeeLabel.gridx = 0;
		gbc_employeeLabel.gridy = 4;
		panel_4.add(employeeLabel, gbc_employeeLabel);

		insertEmployeeLabel = new JLabel("");
		GridBagConstraints gbc_insertEmployeeLabel = new GridBagConstraints();
		gbc_insertEmployeeLabel.anchor = GridBagConstraints.WEST;
		gbc_insertEmployeeLabel.gridx = 1;
		gbc_insertEmployeeLabel.gridy = 4;
		panel_4.add(insertEmployeeLabel, gbc_insertEmployeeLabel);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerInfoGUI customerInfoGUI = new CustomerInfoGUI();
				customerInfoGUI.setVisible(true);
				dispose();
			}
		});

		backButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(backButton);

		JButton finishButton = new JButton("Finish");
		finishButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_2.add(finishButton);

	}

	public void setCustomerInfo(String firstName, String lastName, String phoneNumber, String email, String streetName,
			String streetNumber, String zipCode, String city) {
		insertFirstnameLabel.setText(firstName);
		insertLastnameLabel.setText(lastName);
		insertPhoneNumberLabel.setText(phoneNumber);
		insertEmailLabel.setText(email);
		insertStreetNameLabel.setText(streetName);
		insertStreetNumberLabel.setText(streetNumber);
		insertZipcodeLabel.setText(zipCode);
		insertCityLabel.setText(city);
	}

	public void setBookingInfo(String startDate, String endDate) {
		insertStartDateLabel.setText(startDate);
		insertEndDateLabel.setText(endDate);
	}

}
