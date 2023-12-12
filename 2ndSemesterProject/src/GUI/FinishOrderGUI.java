package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


import Model.Order;


public class FinishOrderGUI extends JFrame {

	private JPanel contentPane;
	private JTable productTable;
	private static Order currentOrder;
	private JLabel filledDateLabel;
	private JLabel filledEmployeeLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinishOrderGUI frame = new FinishOrderGUI(currentOrder);
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
	public FinishOrderGUI(Order currentOrder) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel orderOverviewLabel = new JLabel("Order overview");
		orderOverviewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(orderOverviewLabel, BorderLayout.NORTH);
		
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
		
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishButtonClicked();
			}
		});
		panel_2.add(finishButton);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel.add(panel_4, gbc_panel_4);
		
		JLabel dateLabel = new JLabel("Date:");
		GridBagConstraints gbc_dateLabel = new GridBagConstraints();
		gbc_dateLabel.anchor = GridBagConstraints.WEST;
		gbc_dateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dateLabel.gridx = 0;
		gbc_dateLabel.gridy = 1;
		panel.add(dateLabel, gbc_dateLabel);
		
		filledDateLabel = new JLabel("");
		GridBagConstraints gbc_filledDateLabel = new GridBagConstraints();
		gbc_filledDateLabel.anchor = GridBagConstraints.WEST;
		gbc_filledDateLabel.insets = new Insets(0, 0, 5, 0);
		gbc_filledDateLabel.gridx = 1;
		gbc_filledDateLabel.gridy = 1;
		panel.add(filledDateLabel, gbc_filledDateLabel);
		
		JLabel employeeLabel = new JLabel("Employee:");
		GridBagConstraints gbc_employeeLabel = new GridBagConstraints();
		gbc_employeeLabel.anchor = GridBagConstraints.WEST;
		gbc_employeeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_employeeLabel.gridx = 0;
		gbc_employeeLabel.gridy = 2;
		panel.add(employeeLabel, gbc_employeeLabel);
		
		filledEmployeeLabel = new JLabel("");
		GridBagConstraints gbc_filledEmployeeLabel = new GridBagConstraints();
		gbc_filledEmployeeLabel.anchor = GridBagConstraints.WEST;
		gbc_filledEmployeeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_filledEmployeeLabel.gridx = 1;
		gbc_filledEmployeeLabel.gridy = 2;
		panel.add(filledEmployeeLabel, gbc_filledEmployeeLabel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		panel.add(panel_1, gbc_panel_1);
		
		JLabel productLabel = new JLabel("Products:");
		GridBagConstraints gbc_productLabel = new GridBagConstraints();
		gbc_productLabel.anchor = GridBagConstraints.WEST;
		gbc_productLabel.insets = new Insets(0, 0, 5, 5);
		gbc_productLabel.gridx = 0;
		gbc_productLabel.gridy = 6;
		panel.add(productLabel, gbc_productLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 7;
		panel.add(scrollPane, gbc_scrollPane);
		
		productTable = new JTable();
		scrollPane.setViewportView(productTable);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 8;
		panel.add(panel_3, gbc_panel_3);
		
		JLabel totalPriceLabel = new JLabel("Total Price:");
		GridBagConstraints gbc_totalPriceLabel = new GridBagConstraints();
		gbc_totalPriceLabel.anchor = GridBagConstraints.WEST;
		gbc_totalPriceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totalPriceLabel.gridx = 0;
		gbc_totalPriceLabel.gridy = 9;
		panel.add(totalPriceLabel, gbc_totalPriceLabel);
		
		JLabel filledTotalPriceLabel = new JLabel("");
		GridBagConstraints gbc_filledTotalPriceLabel = new GridBagConstraints();
		gbc_filledTotalPriceLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_filledTotalPriceLabel.insets = new Insets(0, 0, 5, 0);
		gbc_filledTotalPriceLabel.gridx = 1;
		gbc_filledTotalPriceLabel.gridy = 9;
		panel.add(filledTotalPriceLabel, gbc_filledTotalPriceLabel);
		
		init(currentOrder);
	}

	private void init(Order currentOrder) {
		String dateString = String.valueOf(currentOrder.getFormattedDate());
		String employeeString = String.valueOf(currentOrder.getEmployee().getName());
		filledDateLabel.setText(dateString);
        filledEmployeeLabel.setText(employeeString);
	}
	
	public void backButtonClicked() {
		OrderInfoGUI orderInfoGUI = new OrderInfoGUI(currentOrder);
		orderInfoGUI.setVisible(true);
		dispose();
	}
	
	private void finishButtonClicked() {
		
	}
}
