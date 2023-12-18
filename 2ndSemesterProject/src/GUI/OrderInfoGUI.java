package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Control.OrderController;
import Database.ConnectionEnvironment;
import Model.Employee;
import Model.Order;
import Model.OrderLine;
import Model.Product;

public class OrderInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable productTable;
	private JTextField barcodeTextField;
	private JTextField quantityTextField;
	private Employee employee;
	private OrderLineTableModel orderLineTableModel;
	private ArrayList<OrderLine> orderLines;
	private OrderController orderController;
	private ConnectionEnvironment env = ConnectionEnvironment.PRODUCTION;

	public OrderInfoGUI(Order currentOrder, OrderLineTableModel productTableModel, Employee employee) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonClicked();
			}
		});
		panel.add(cancelButton);

		JButton confirmButton = new JButton("Confirm order");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmOrderClicked(currentOrder);
			}
		});
		panel.add(confirmButton);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.EAST);

		productTable = new JTable();
		productTable.setEnabled(false);
		scrollPane.setViewportView(productTable);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 273, 45, 0 };
		gbl_panel_1.rowHeights = new int[] { 13, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel addProductLabel = new JLabel("Add product");
		GridBagConstraints gbc_addProductLabel = new GridBagConstraints();
		gbc_addProductLabel.anchor = GridBagConstraints.WEST;
		gbc_addProductLabel.insets = new Insets(0, 0, 0, 5);
		gbc_addProductLabel.gridx = 0;
		gbc_addProductLabel.gridy = 0;
		panel_1.add(addProductLabel, gbc_addProductLabel);

		JLabel productListLabel = new JLabel("Product list");
		GridBagConstraints gbc_productListLabel = new GridBagConstraints();
		gbc_productListLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_productListLabel.gridx = 1;
		gbc_productListLabel.gridy = 0;
		panel_1.add(productListLabel, gbc_productListLabel);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_2.add(panel_3, gbc_panel_3);

		JLabel barcodeLabel = new JLabel("Barcode");
		GridBagConstraints gbc_barcodeLabel = new GridBagConstraints();
		gbc_barcodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_barcodeLabel.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		gbc_barcodeLabel.gridx = 0;
		gbc_barcodeLabel.gridy = 1;
		panel_2.add(barcodeLabel, gbc_barcodeLabel);

		barcodeTextField = new JTextField();
		GridBagConstraints gbc_barcodeTextField = new GridBagConstraints();
		gbc_barcodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_barcodeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_barcodeTextField.gridx = 1;
		gbc_barcodeTextField.gridy = 1;
		panel_2.add(barcodeTextField, gbc_barcodeTextField);
		barcodeTextField.setColumns(10);

		JLabel quantitylabel = new JLabel("Quantity");
		GridBagConstraints gbc_quantitylabel = new GridBagConstraints();
		gbc_quantitylabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_quantitylabel.insets = new Insets(0, 0, 5, 5);
		gbc_quantitylabel.gridx = 0;
		gbc_quantitylabel.gridy = 2;
		panel_2.add(quantitylabel, gbc_quantitylabel);

		quantityTextField = new JTextField();
		GridBagConstraints gbc_quantityTextField = new GridBagConstraints();
		gbc_quantityTextField.insets = new Insets(0, 0, 5, 5);
		gbc_quantityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_quantityTextField.gridx = 1;
		gbc_quantityTextField.gridy = 2;
		panel_2.add(quantityTextField, gbc_quantityTextField);
		quantityTextField.setColumns(10);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonClicked(currentOrder);
			}
		});
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.anchor = GridBagConstraints.EAST;
		gbc_addButton.insets = new Insets(0, 0, 0, 5);
		gbc_addButton.gridx = 1;
		gbc_addButton.gridy = 3;
		panel_2.add(addButton, gbc_addButton);

		init(productTableModel, currentOrder, employee);
	}

	private void init(OrderLineTableModel productTableModel, Order currentOrder, Employee employee) {
		this.employee = employee;
		orderController = new OrderController(employee, env, currentOrder);
		orderLines = new ArrayList<>();
		if (productTableModel == null) {
			productTableModel = new OrderLineTableModel(orderLines);
		} else {
			this.orderLineTableModel = productTableModel;
			this.orderLines.addAll(productTableModel.getData());
		}
		productTable.setModel(productTableModel);
	}

	private void cancelButtonClicked() {
		MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
		mainMenuGUI.setVisible(true);
		dispose();
	}

	private void addButtonClicked(Order currentOrder) {
		String barcodeText = barcodeTextField.getText().trim();
		String quantityText = quantityTextField.getText().trim();

		if (barcodeText.isEmpty() || quantityText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Error: Barcode and quantity must be provided");
			return;
		}

		try {
			int barcode = Integer.parseInt(barcodeTextField.getText());
			int quantity = Integer.parseInt(quantityTextField.getText());

			Product product = orderController.findProductByBarcode(barcode);

			OrderLine orderLine = new OrderLine(product, quantity);

			if (product != null) {
				boolean updated = false;
				for (OrderLine existingOrderLine : orderLines) {
					if (existingOrderLine.getProduct().getBarcode() == product.getBarcode()) {
						existingOrderLine.setQuantity(existingOrderLine.getQuantity() + quantity);
						updated = true;
						break;
					}
				}
				if (!updated) {
					orderController.addOrderLine(currentOrder, orderLine);
					orderLines.add(orderLine);
				}
				barcodeTextField.setText("");
				quantityTextField.setText("");
				updateProductTableModel();
			} else {
				JOptionPane.showMessageDialog(this, "Error: No product found");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error: Invalid input for barcode or quantity");
		}
	}

	private void updateProductTableModel() {
		orderLineTableModel = new OrderLineTableModel(orderLines);
		orderLineTableModel.setData(orderLines);
		productTable.setModel(orderLineTableModel);
	}

	private void confirmOrderClicked(Order currentOrder) {
		if (orderLineTableModel == null) {
			JOptionPane.showMessageDialog(this, "Error: Add product to order");
		} else {
			FinishOrderGUI finishOrderGUI = new FinishOrderGUI(currentOrder, orderLineTableModel, employee);
			finishOrderGUI.setVisible(true);
			dispose();
		}
	}

}
