package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Control.BookingController;
import Model.Campsite;
import Model.Customer;
import Model.Employee;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class BookingInfoGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField zipCodeField;
    private JTextField cityField;
    private List<Campsite> campsites;
    private JTable campsiteTable;
    private BookingController bookingController;
    private Employee employee;


    private CampsiteTableModel campsiteTableModel;
    private JComboBox startYearComboBox;
    private JComboBox startMonthComboBox;
    private JComboBox startDayComboBox;
    private JComboBox endYearComboBox;
    private JComboBox endMonthComboBox;
    private JComboBox endDayComboBox;

    private boolean customerInfoWarningDisplayed = false;

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
        setSize(750, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 2, 0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{61, 250, 0};
        gbl_panel.rowHeights = new int[]{0, 16, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
        gbc_phoneNumberLabel.anchor = GridBagConstraints.WEST;
        gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
        gbc_phoneNumberLabel.gridx = 0;
        gbc_phoneNumberLabel.gridy = 0;
        panel.add(phoneNumberLabel, gbc_phoneNumberLabel);

        phoneNumberField = new JTextField();
        GridBagConstraints gbc_phoneNumberField = new GridBagConstraints();
        gbc_phoneNumberField.insets = new Insets(0, 0, 5, 0);
        gbc_phoneNumberField.fill = GridBagConstraints.HORIZONTAL;
        gbc_phoneNumberField.gridx = 1;
        gbc_phoneNumberField.gridy = 0;
        panel.add(phoneNumberField, gbc_phoneNumberField);
        phoneNumberField.setColumns(10);

        phoneNumberField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                fillCustomerInfo();
            }
        });
        phoneNumberField.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int currKeyPressed = e.getKeyCode();
                if (currKeyPressed == KeyEvent.VK_ENTER) {
                    fillCustomerInfo();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.anchor = GridBagConstraints.WEST;
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 1;
        panel.add(nameLabel, gbc_nameLabel);

        nameTextField = new JTextField();
        GridBagConstraints gbc_firstnameTextField = new GridBagConstraints();
        gbc_firstnameTextField.insets = new Insets(0, 0, 5, 0);
        gbc_firstnameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstnameTextField.gridx = 1;
        gbc_firstnameTextField.gridy = 1;
        panel.add(nameTextField, gbc_firstnameTextField);
        nameTextField.setColumns(10);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (phoneNumberField.hasFocus()) {
                    fillCustomerInfo();
                }
            }
        });

        JLabel emailLabel = new JLabel("Email:");
        GridBagConstraints gbc_emailLabel = new GridBagConstraints();
        gbc_emailLabel.anchor = GridBagConstraints.WEST;
        gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
        gbc_emailLabel.gridx = 0;
        gbc_emailLabel.gridy = 2;
        panel.add(emailLabel, gbc_emailLabel);

        emailField = new JTextField();
        GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.insets = new Insets(0, 0, 5, 0);
        gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
        gbc_emailField.gridx = 1;
        gbc_emailField.gridy = 2;
        panel.add(emailField, gbc_emailField);
        emailField.setColumns(10);

        JLabel addressLabel = new JLabel("Address:");
        GridBagConstraints gbc_addressLabel = new GridBagConstraints();
        gbc_addressLabel.anchor = GridBagConstraints.WEST;
        gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
        gbc_addressLabel.gridx = 0;
        gbc_addressLabel.gridy = 3;
        panel.add(addressLabel, gbc_addressLabel);

        addressField = new JTextField();
        GridBagConstraints gbc_addressField = new GridBagConstraints();
        gbc_addressField.insets = new Insets(0, 0, 5, 0);
        gbc_addressField.fill = GridBagConstraints.HORIZONTAL;
        gbc_addressField.gridx = 1;
        gbc_addressField.gridy = 3;
        panel.add(addressField, gbc_addressField);
        addressField.setColumns(10);

        JLabel zipCodeLabel = new JLabel("Zipcode:");
        GridBagConstraints gbc_zipCodeLabel = new GridBagConstraints();
        gbc_zipCodeLabel.anchor = GridBagConstraints.WEST;
        gbc_zipCodeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_zipCodeLabel.gridx = 0;
        gbc_zipCodeLabel.gridy = 4;
        panel.add(zipCodeLabel, gbc_zipCodeLabel);

        zipCodeField = new JTextField();
        GridBagConstraints gbc_zipCodeField = new GridBagConstraints();
        gbc_zipCodeField.insets = new Insets(0, 0, 5, 0);
        gbc_zipCodeField.fill = GridBagConstraints.HORIZONTAL;
        gbc_zipCodeField.gridx = 1;
        gbc_zipCodeField.gridy = 4;
        panel.add(zipCodeField, gbc_zipCodeField);
        zipCodeField.setColumns(10);

        JLabel cityLabel = new JLabel("City:");
        GridBagConstraints gbc_cityLabel = new GridBagConstraints();
        gbc_cityLabel.anchor = GridBagConstraints.WEST;
        gbc_cityLabel.insets = new Insets(0, 0, 5, 5);
        gbc_cityLabel.gridx = 0;
        gbc_cityLabel.gridy = 5;
        panel.add(cityLabel, gbc_cityLabel);

        cityField = new JTextField();
        GridBagConstraints gbc_cityField = new GridBagConstraints();
        gbc_cityField.insets = new Insets(0, 0, 5, 0);
        gbc_cityField.fill = GridBagConstraints.HORIZONTAL;
        gbc_cityField.gridx = 1;
        gbc_cityField.gridy = 5;
        panel.add(cityField, gbc_cityField);
        cityField.setColumns(10);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{61, 0, 0};
        gbl_panel_1.rowHeights = new int[]{16, 0, 0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JLabel startDateLabel = new JLabel("Start Date:");
        GridBagConstraints gbc_startDateLabel = new GridBagConstraints();
        gbc_startDateLabel.insets = new Insets(0, 0, 5, 5);
        gbc_startDateLabel.anchor = GridBagConstraints.WEST;
        gbc_startDateLabel.gridx = 0;
        gbc_startDateLabel.gridy = 0;
        panel_1.add(startDateLabel, gbc_startDateLabel);

        JPanel panel_4 = new JPanel();
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        gbc_panel_4.insets = new Insets(0, 0, 5, 0);
        gbc_panel_4.fill = GridBagConstraints.BOTH;
        gbc_panel_4.gridx = 1;
        gbc_panel_4.gridy = 0;
        panel_1.add(panel_4, gbc_panel_4);
        GridBagLayout gbl_panel_4 = new GridBagLayout();
        gbl_panel_4.columnWidths = new int[]{0, 0, 0, 0};
        gbl_panel_4.rowHeights = new int[]{0, 0, 0};
        gbl_panel_4.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_4.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_4.setLayout(gbl_panel_4);

        JLabel yearLabel = new JLabel("Year");
        GridBagConstraints gbc_yearLabel = new GridBagConstraints();
        gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
        gbc_yearLabel.gridx = 0;
        gbc_yearLabel.gridy = 0;
        panel_4.add(yearLabel, gbc_yearLabel);

        JLabel monthLabel = new JLabel("Month");
        GridBagConstraints gbc_monthLabel = new GridBagConstraints();
        gbc_monthLabel.insets = new Insets(0, 0, 5, 5);
        gbc_monthLabel.gridx = 1;
        gbc_monthLabel.gridy = 0;
        panel_4.add(monthLabel, gbc_monthLabel);

        JLabel dayLabel = new JLabel("Day");
        GridBagConstraints gbc_dayLabel = new GridBagConstraints();
        gbc_dayLabel.insets = new Insets(0, 0, 5, 0);
        gbc_dayLabel.gridx = 2;
        gbc_dayLabel.gridy = 0;
        panel_4.add(dayLabel, gbc_dayLabel);

        startYearComboBox = new JComboBox();
        GridBagConstraints gbc_startYearComboBox = new GridBagConstraints();
        gbc_startYearComboBox.insets = new Insets(0, 0, 0, 5);
        gbc_startYearComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_startYearComboBox.gridx = 0;
        gbc_startYearComboBox.gridy = 1;
        panel_4.add(startYearComboBox, gbc_startYearComboBox);

        startMonthComboBox = new JComboBox();
        GridBagConstraints gbc_startMonthComboBox = new GridBagConstraints();
        gbc_startMonthComboBox.insets = new Insets(0, 0, 0, 5);
        gbc_startMonthComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_startMonthComboBox.gridx = 1;
        gbc_startMonthComboBox.gridy = 1;
        panel_4.add(startMonthComboBox, gbc_startMonthComboBox);

        startDayComboBox = new JComboBox();
        GridBagConstraints gbc_startDayComboBox = new GridBagConstraints();
        gbc_startDayComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_startDayComboBox.gridx = 2;
        gbc_startDayComboBox.gridy = 1;
        panel_4.add(startDayComboBox, gbc_startDayComboBox);

        JLabel endDateLabel = new JLabel("End Date:");
        GridBagConstraints gbc_endDateLabel = new GridBagConstraints();
        gbc_endDateLabel.anchor = GridBagConstraints.WEST;
        gbc_endDateLabel.insets = new Insets(0, 0, 5, 5);
        gbc_endDateLabel.gridx = 0;
        gbc_endDateLabel.gridy = 1;
        panel_1.add(endDateLabel, gbc_endDateLabel);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchButtonClicked();
            }
        });

        JPanel panel_5 = new JPanel();
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.insets = new Insets(0, 0, 5, 0);
        gbc_panel_5.fill = GridBagConstraints.BOTH;
        gbc_panel_5.gridx = 1;
        gbc_panel_5.gridy = 1;
        panel_1.add(panel_5, gbc_panel_5);
        GridBagLayout gbl_panel_5 = new GridBagLayout();
        gbl_panel_5.columnWidths = new int[]{0, 0, 0, 0};
        gbl_panel_5.rowHeights = new int[]{0, 0};
        gbl_panel_5.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_5.setLayout(gbl_panel_5);

        endYearComboBox = new JComboBox();
        GridBagConstraints gbc_endYearComboBox = new GridBagConstraints();
        gbc_endYearComboBox.insets = new Insets(0, 0, 0, 5);
        gbc_endYearComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_endYearComboBox.gridx = 0;
        gbc_endYearComboBox.gridy = 0;
        panel_5.add(endYearComboBox, gbc_endYearComboBox);

        endMonthComboBox = new JComboBox();
        GridBagConstraints gbc_endMonthComboBox = new GridBagConstraints();
        gbc_endMonthComboBox.insets = new Insets(0, 0, 0, 5);
        gbc_endMonthComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_endMonthComboBox.gridx = 1;
        gbc_endMonthComboBox.gridy = 0;
        panel_5.add(endMonthComboBox, gbc_endMonthComboBox);

        endDayComboBox = new JComboBox();
        GridBagConstraints gbc_endDayComboBox = new GridBagConstraints();
        gbc_endDayComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_endDayComboBox.gridx = 2;
        gbc_endDayComboBox.gridy = 0;
        panel_5.add(endDayComboBox, gbc_endDayComboBox);
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

        fillJComboBoxes();
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
        String startDateString = formatComboBoxValuesToDate(startYearComboBox, startMonthComboBox, startDayComboBox);
        String endDateString = formatComboBoxValuesToDate(endYearComboBox, endMonthComboBox, endDayComboBox);

        if (isDate(startDateString) && isDate(endDateString)) {
            campsites = bookingController.getAvailableCampsites(startDateString, endDateString);
            tableModel();
        } else {
            JOptionPane.showMessageDialog(contentPane, "You have entered an invalid date.\n Format is YYYY-MM-DD");
        }

        if (campsites.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "No available campsites in this period");
        }
    }

    private boolean isDate(String dateString) {
        try {
            // Check if date can be parsed
            LocalDate date = LocalDate.parse(dateString);
            return true; // Parsed successfully
        } catch (DateTimeParseException e) {
            return false; // Failed to parse, dateString is not a valid date
        }
    }

    private void confirmButtonClicked() {

        String startDateString = formatComboBoxValuesToDate(startYearComboBox, startMonthComboBox, startDayComboBox);
        String endDateString = formatComboBoxValuesToDate(endYearComboBox, endMonthComboBox, endDayComboBox);

        int rowIndex = campsiteTable.getSelectedRow();
        Campsite currentCampsite = campsiteTableModel.getCampsite(rowIndex);
        bookingController.addCampsiteToBooking(currentCampsite, startDateString, endDateString);

        FinishBookingGUI finishBookingGUI = new FinishBookingGUI(this.employee, this.bookingController);

        finishBookingGUI.setVisible(true);
        dispose();
    }

    private void backButtonClicked() {
        bookingController.cancelBooking();
        MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
        mainMenuGUI.setVisible(true);
        dispose();
    }

    private void fillCustomerInfo() {
        if (customerInfoWarningDisplayed) {
            return;
        }

        String phoneNumber = phoneNumberField.getText();
        Customer customer = bookingController.findCustomerByPhoneNumber(phoneNumber);

        if (customer != null) {
            if (informationInAnyTextFields()) {
                customerInfoWarningDisplayed = true; // Set the flag to indicate that the dialog has been shown
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Customer found, do you want to override existing textfields?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == JOptionPane.NO_OPTION || result == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            fillInCustomerTextfields(customer);

        }
    }

    private void fillInCustomerTextfields(Customer customer) {
        nameTextField.setText(customer.getName());
        emailField.setText(customer.getEmail());

        String[] address = customer.getAddress().split(" ");
        addressField.setText(address[0] + " " + address[1]);
        cityField.setText(address[2]);
        zipCodeField.setText(address[3]);
    }

    private boolean informationInAnyTextFields() {
        boolean conflict = false;

        JTextField[] textFields = {nameTextField, emailField, addressField, cityField, zipCodeField};

        for (JTextField textField : textFields) {
            String text = textField.getText().trim();
            if (!text.isEmpty()) {
                conflict = true;
            }
        }

        return conflict;
    }

    private void fillJComboBoxes() {
        // Define the range of years, months, and days
        int startYear = 2023;
        int endYear = 2030;

        // Populate the start and end year JComboBoxes
        populateComboBox(startYearComboBox, startYear, endYear);
        populateComboBox(endYearComboBox, startYear, endYear);

        // Populate the month JComboBoxes (1 to 12)
        populateComboBox(startMonthComboBox, 1, 12);
        populateComboBox(endMonthComboBox, 1, 12);

        // Populate the day JComboBoxes (1 to 31)
        populateComboBox(startDayComboBox, 1, 31);
        populateComboBox(endDayComboBox, 1, 31);
    }

    private void populateComboBox(JComboBox<String> comboBox, int start, int end) {
        for (int i = start; i <= end; i++) {
            comboBox.addItem(String.format("%02d", i));
        }
    }


    private String formatComboBoxValuesToDate(JComboBox<String> yearComboBox, JComboBox<String> monthComboBox, JComboBox<String> dayComboBox) {
        String dateFormattedString = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(yearComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + dayComboBox.getSelectedItem());
            dateFormattedString = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateFormattedString;
    }


}
