package GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import Control.TaskController;
import Database.ConnectionEnvironment;
import Model.Janitor;
import Model.Task;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JScrollBar;

public class CreateTaskGUI extends JFrame {

    private List<Janitor> janitorList;
    private JPanel contentPane;
    private JList listJanitors;
    private JTable tableTasks;
    private TaskTableModel taskTableModel;
    private JTable table_1;

    /**
     * Create the frame.
     */
    public CreateTaskGUI() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(750, 600);
	setLocationRelativeTo(null);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	setContentPane(contentPane);
	GridBagLayout gbl_contentPane = new GridBagLayout();
	gbl_contentPane.columnWidths = new int[] { 150, 150, 0, 0 };
	gbl_contentPane.rowHeights = new int[] { 0, 475, 25, 25, 0 };
	gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
	gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	contentPane.setLayout(gbl_contentPane);

	JLabel lblDescription = new JLabel("Description");
	lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblDescription = new GridBagConstraints();
	gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
	gbc_lblDescription.gridx = 0;
	gbc_lblDescription.gridy = 0;
	contentPane.add(lblDescription, gbc_lblDescription);

	JLabel lblJanitors = new JLabel("Janitors");
	lblJanitors.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblJanitors = new GridBagConstraints();
	gbc_lblJanitors.insets = new Insets(0, 0, 5, 5);
	gbc_lblJanitors.gridx = 1;
	gbc_lblJanitors.gridy = 0;
	contentPane.add(lblJanitors, gbc_lblJanitors);

	JLabel lblTasks = new JLabel("Tasks");
	lblTasks.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblTasks = new GridBagConstraints();
	gbc_lblTasks.insets = new Insets(0, 0, 5, 0);
	gbc_lblTasks.gridx = 2;
	gbc_lblTasks.gridy = 0;
	contentPane.add(lblTasks, gbc_lblTasks);

	JPanel panel_left = new JPanel();
	panel_left.setLayout(null);
	GridBagConstraints gbc_panel_left = new GridBagConstraints();
	gbc_panel_left.insets = new Insets(0, 0, 5, 5);
	gbc_panel_left.fill = GridBagConstraints.BOTH;
	gbc_panel_left.gridx = 0;
	gbc_panel_left.gridy = 1;
	contentPane.add(panel_left, gbc_panel_left);

	JTextArea textAreaDescription = new JTextArea();
	textAreaDescription.setBounds(0, 0, 145, 470);
	textAreaDescription.setLineWrap(true);
	panel_left.add(textAreaDescription);

	JPanel panel_middle = new JPanel();
	panel_middle.setLayout(null);
	GridBagConstraints gbc_panel_middle = new GridBagConstraints();
	gbc_panel_middle.insets = new Insets(0, 0, 5, 5);
	gbc_panel_middle.fill = GridBagConstraints.BOTH;
	gbc_panel_middle.gridx = 1;
	gbc_panel_middle.gridy = 1;
	contentPane.add(panel_middle, gbc_panel_middle);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(0, 0, 145, 470);
	panel_middle.add(scrollPane);

	listJanitors = new JList();
	scrollPane.setViewportView(listJanitors);

	JPanel panel_right = new JPanel();
	panel_right.setLayout(null);
	GridBagConstraints gbc_panel_right = new GridBagConstraints();
	gbc_panel_right.insets = new Insets(0, 0, 5, 0);
	gbc_panel_right.fill = GridBagConstraints.BOTH;
	gbc_panel_right.gridx = 2;
	gbc_panel_right.gridy = 1;
	contentPane.add(panel_right, gbc_panel_right);

	//tableTasks = new JTable();
	//GridBagConstraints gbc_tableTasks = new GridBagConstraints();
	//gbc_tableTasks.insets = new Insets(0, 0, 5, 0);
	//gbc_tableTasks.fill = GridBagConstraints.BOTH;
	//gbc_tableTasks.gridx = 0;
	//gbc_tableTasks.gridy = 0;
	//panel_right.add(tableTasks, gbc_tableTasks);
	
	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.setBounds(0, 0, 426, 470);
	panel_right.add(scrollPane_1);
	
	tableTasks = new JTable();
	scrollPane_1.setViewportView(tableTasks);

	JPanel panelPriority = new JPanel();
	GridBagConstraints gbc_panelPriority = new GridBagConstraints();
	gbc_panelPriority.insets = new Insets(0, 0, 5, 5);
	gbc_panelPriority.fill = GridBagConstraints.BOTH;
	gbc_panelPriority.gridx = 0;
	gbc_panelPriority.gridy = 2;
	contentPane.add(panelPriority, gbc_panelPriority);
	GridBagLayout gbl_panelPriority = new GridBagLayout();
	gbl_panelPriority.columnWidths = new int[] { 51, 32, 0 };
	gbl_panelPriority.rowHeights = new int[] { 13, 0 };
	gbl_panelPriority.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
	gbl_panelPriority.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	panelPriority.setLayout(gbl_panelPriority);

	JLabel lblPriority = new JLabel("Priority");
	GridBagConstraints gbc_lblPriority = new GridBagConstraints();
	gbc_lblPriority.anchor = GridBagConstraints.WEST;
	gbc_lblPriority.insets = new Insets(0, 0, 0, 5);
	gbc_lblPriority.gridx = 0;
	gbc_lblPriority.gridy = 0;
	panelPriority.add(lblPriority, gbc_lblPriority);

	JComboBox comboBoxPriority = new JComboBox();
	for (int i = 0; i < 3; i++) {
	    comboBoxPriority.addItem(i + 1);
	}
	GridBagConstraints gbc_comboBoxPriority = new GridBagConstraints();
	gbc_comboBoxPriority.fill = GridBagConstraints.HORIZONTAL;
	gbc_comboBoxPriority.gridx = 1;
	gbc_comboBoxPriority.gridy = 0;
	panelPriority.add(comboBoxPriority, gbc_comboBoxPriority);
	
	JPanel panelBottomRow = new JPanel();
	GridBagConstraints gbc_panelBottomRow = new GridBagConstraints();
	gbc_panelBottomRow.gridwidth = 3;
	gbc_panelBottomRow.insets = new Insets(0, 0, 0, 5);
	gbc_panelBottomRow.fill = GridBagConstraints.BOTH;
	gbc_panelBottomRow.gridx = 0;
	gbc_panelBottomRow.gridy = 3;
	contentPane.add(panelBottomRow, gbc_panelBottomRow);
	panelBottomRow.setLayout(new BorderLayout(0, 0));
	
	JPanel panel = new JPanel();
	panelBottomRow.add(panel, BorderLayout.WEST);
	
	JLabel lblDeadline = new JLabel("Deadline");
	panel.add(lblDeadline);
	
        DatePickerSettings dateSettings = new DatePickerSettings();
	
	DatePicker comboBox = new DatePicker(dateSettings);
	panel.add(comboBox);
	
	JPanel panel_2 = new JPanel();
	panelBottomRow.add(panel_2, BorderLayout.EAST);
	
	JButton btnBack = new JButton("Back");
	panel_2.add(btnBack);
	
	JButton btnAddTask = new JButton("Add Task");
	panel_2.add(btnAddTask);

	init();
    }

    private void init() {
	TaskController taskController = new TaskController(ConnectionEnvironment.PRODUCTION);
	janitorList = taskController.getAllJanitors();
	String[] janitorArray = new String[janitorList.size()];
	for (int i = 0; i < janitorList.size(); i++) {
	    janitorArray[i] = janitorList.get(i).getName();
	}
	listJanitors.setListData(janitorArray);

	updateTableModel();
    }
    
    private void updateTableModel() {
	TaskController taskController = new TaskController(ConnectionEnvironment.PRODUCTION);
	List<Task> taskList = taskController.getUncompletedTasks(0, false);
	taskTableModel = new TaskTableModel(taskList);
	tableTasks.setModel(taskTableModel);
	tableTasks.setDefaultRenderer(Object.class, new MultilineCellRenderer());
	tableTasks.setRowHeight(50);
	
	// Make the priority field to take up less space, making space for others
	tableTasks.getColumnModel().getColumn(0).setPreferredWidth(20);
	// Setting the size of the deadline field, making it readable for most scales
	tableTasks.getColumnModel().getColumn(2).setMinWidth(60);
	tableTasks.getColumnModel().getColumn(2).setPreferredWidth(60);
    }
    
    static class MultilineCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                JTextArea textArea = new JTextArea(value.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(true);
                textArea.setBackground(cellComponent.getBackground());
                return textArea;
            }
            return cellComponent;
        }
    }
}
