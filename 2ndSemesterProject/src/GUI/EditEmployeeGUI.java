package GUI;

import Model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEmployeeGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private MainMenuGUI mainMenuGUI;
    private Employee employee;


    public EditEmployeeGUI(Employee employee) {
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

        JLabel editEmployeeLabel = new JLabel("Edit Employee");
        editEmployeeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        panel.add(editEmployeeLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JButton createEmployeeButton = new JButton("New Employee");
        createEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createEmployeeButtonClicked();
            }
        });
        GridBagConstraints gbc_createEmployeeButton = new GridBagConstraints();
        gbc_createEmployeeButton.insets = new Insets(0, 0, 5, 0);
        gbc_createEmployeeButton.gridx = 0;
        gbc_createEmployeeButton.gridy = 1;
        panel_1.add(createEmployeeButton, gbc_createEmployeeButton);

        JButton updateEmployeeButton = new JButton("Update Employee");
        updateEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmployeeButtonClicked();
            }
        });
        GridBagConstraints gbc_updateEmployeeButton = new GridBagConstraints();
        gbc_updateEmployeeButton.insets = new Insets(0, 0, 5, 0);
        gbc_updateEmployeeButton.gridx = 0;
        gbc_updateEmployeeButton.gridy = 2;
        panel_1.add(updateEmployeeButton, gbc_updateEmployeeButton);

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

    private void createEmployeeButtonClicked() {
        CreateEmployeeGUI createEmployeeGUI = new CreateEmployeeGUI(employee);
        createEmployeeGUI.setVisible(true);
        dispose();
    }

    private void updateEmployeeButtonClicked() {
    	UpdateDeleteEmployeesGUI updateDeleteEmployee = new UpdateDeleteEmployeesGUI(employee);
    	updateDeleteEmployee.setVisible(true);
    	dispose();
    }

}
