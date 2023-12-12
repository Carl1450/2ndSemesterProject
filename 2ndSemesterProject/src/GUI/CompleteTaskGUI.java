package GUI;

import Control.TaskController;
import Database.ConnectionEnvironment;
import Model.Employee;
import Model.Task;
import com.sun.tools.javac.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompleteTaskGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable taskTable;
	private TaskTableModel taskTableModel;

	private TaskController taskController;

	private Employee employee;
	private JButton refreshButton;
	private JButton backButton;
	private JButton completeTaskButton;


	public CompleteTaskGUI(Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{213, 10, 0};
		gbl_contentPane.rowHeights = new int[]{10, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Current Tasks:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshButtonClicked();
			}
		});
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.insets = new Insets(0, 0, 5, 0);
		gbc_refreshButton.gridx = 1;
		gbc_refreshButton.gridy = 0;
		contentPane.add(refreshButton, gbc_refreshButton);
		
		taskTable = new JTable();
		GridBagConstraints gbc_taskTable = new GridBagConstraints();
		gbc_taskTable.gridwidth = 2;
		gbc_taskTable.gridheight = 7;
		gbc_taskTable.insets = new Insets(0, 0, 5, 0);
		gbc_taskTable.fill = GridBagConstraints.BOTH;
		gbc_taskTable.gridx = 0;
		gbc_taskTable.gridy = 1;
		contentPane.add(taskTable, gbc_taskTable);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 8;
		contentPane.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButtonClicked();
			}
		});
		backButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(backButton);
		
		completeTaskButton = new JButton("Complete Task");
		completeTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				completeTaskButtonClicked();
			}
		});
		completeTaskButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(completeTaskButton);

		init(employee);
	}

	private void init(Employee employee) {
		this.employee = employee;
		taskController = new TaskController(ConnectionEnvironment.PRODUCTION);
		updateTaskTabelModel();
	}

	private void updateTaskTabelModel() {
		List<Task> tasks = taskController.getUncompletedTasks(employee.getId());
		taskTableModel = new TaskTableModel(tasks);
		taskTableModel.setData(tasks);
		taskTable.setModel(taskTableModel);
	}

	private void refreshButtonClicked() {
		updateTaskTabelModel();
	}

	private void completeTaskButtonClicked() {

		Task task = taskTableModel.getTask(taskTable.getSelectedRow());
		taskController.finishTask(task);
		updateTaskTabelModel();
	}

	private void backButtonClicked(){
		MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}
}
