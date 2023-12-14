package GUI;

import Control.ProductController;
import Database.ConnectionEnvironment;
import Model.Employee;
import Model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;
import java.util.List;

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
    private JTable productTable;

    private Employee employee;

    private ProductTableModel productTableModel;

    private ProductController productController;

    private int lastSelectedRow;
    private JLabel priceLabel;
    private JTextField priceTextField;

    /**
     * Create the frame.
     */
    public EditProductsGUI(Employee employee) {

        setSize(800, 600);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel BarcodeLabel = new JLabel("Barcode:");
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

        JLabel NameLabel = new JLabel("Name:");
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

        JLabel stockNumberLabel = new JLabel("Stock number:");
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
        
        priceLabel = new JLabel("Price:");
        GridBagConstraints gbc_priceLabel = new GridBagConstraints();
        gbc_priceLabel.anchor = GridBagConstraints.WEST;
        gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
        gbc_priceLabel.gridx = 0;
        gbc_priceLabel.gridy = 3;
        panel.add(priceLabel, gbc_priceLabel);
        
        priceTextField = new JTextField();
        GridBagConstraints gbc_priceTextField = new GridBagConstraints();
        gbc_priceTextField.insets = new Insets(0, 0, 5, 0);
        gbc_priceTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_priceTextField.gridx = 1;
        gbc_priceTextField.gridy = 3;
        panel.add(priceTextField, gbc_priceTextField);
        priceTextField.setColumns(10);
        GridBagConstraints gbc_createUpdateButton = new GridBagConstraints();
        gbc_createUpdateButton.insets = new Insets(0, 0, 5, 0);
        gbc_createUpdateButton.gridx = 1;
        gbc_createUpdateButton.gridy = 4;
        panel.add(createUpdateButton, gbc_createUpdateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteButtonClicked();
            }
        });
        GridBagConstraints gbc_deleteButton = new GridBagConstraints();
        gbc_deleteButton.gridx = 1;
        gbc_deleteButton.gridy = 5;
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
        productSearchTextField.setText("Search for barcode ...");
        GridBagConstraints gbc_productSearchTextField = new GridBagConstraints();
        gbc_productSearchTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_productSearchTextField.gridx = 1;
        gbc_productSearchTextField.gridy = 0;
        panel_2.add(productSearchTextField, gbc_productSearchTextField);
        productSearchTextField.setColumns(10);

        productSearchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productSearchTextField.getText().equals("Search for barcode ...")) {
                    productSearchTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productSearchTextField.getText().equals("")) {
                    productSearchTextField.setText("Search for barcode ...");
                }
            }
        });

        productSearchTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateProductTable(false);

            }
        });

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        panel_1.add(scrollPane, gbc_scrollPane);

        productTable = new JTable();
        scrollPane.setViewportView(productTable);


        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = productTable.rowAtPoint(e.getPoint());

                if (selectedRow == lastSelectedRow) {
                    productTable.getSelectionModel().clearSelection();
                    clearProductTextFields();
                    lastSelectedRow = -1;
                    setUpdateCreateButtonTo("Create");
                } else {
                    lastSelectedRow = selectedRow;
                    fillOutProductInfo();
                    setUpdateCreateButtonTo("Update");
                    deleteButton.setEnabled(true);
                }
            }
        });

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

        init(employee);
    }

    private void init(Employee employee) {
        lastSelectedRow = -1;
        this.employee = employee;
        productController = new ProductController(ConnectionEnvironment.PRODUCTION);
        updateProductTable(false);
    }

    private void updateProductTable(boolean retrieveNewData) {

        String barcodeSting = productSearchTextField.getText();
        if (barcodeSting.equals("Search for barcode ...")) {
            barcodeSting = "";
        }

        List<Product> products = productController.findProductsStartingWith(barcodeSting, retrieveNewData);

        productTableModel = new ProductTableModel(products);
        productTable.setModel(productTableModel);
    }

    private void createUpdateButtonClicked() {

        String barcode = barcodeTextField.getText();
        String productName = nameTextField.getText();
        String stockNumber = stockNumberTextField.getText();
        String price = priceTextField.getText();

        if (createUpdateButton.getText().equals("Create")) {
            productController.saveProduct(barcode, productName, stockNumber, price);
        } else if (createUpdateButton.getText().equals("Update")) {
            Product product = productTableModel.getProduct(productTable.getSelectedRow());
            productController.updateProduct(product.getBarcode(), barcode, productName, stockNumber);
        }

        updateProductTable(true);
    }

    private void deleteButtonClicked() {
        Product product = productTableModel.getProduct(productTable.getSelectedRow());
        if (productController.deleteProduct(product)) {
            updateProductTable(true);
        } else {
            JOptionPane.showMessageDialog(null,
                    "A conflict in the database occurred, Product can not be deleted",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    private void backButtonClicked() {
        MainMenuGUI mainMenuGUI = new MainMenuGUI(employee);
        mainMenuGUI.setVisible(true);
        dispose();
    }


    private void fillOutProductInfo() {
        Product product = productTableModel.getProduct(productTable.getSelectedRow());

        barcodeTextField.setText(Integer.toString(product.getBarcode()));
        nameTextField.setText(product.getName());
        stockNumberTextField.setText(Integer.toString(product.getStockNumber()));
        priceTextField.setText(Float.toString(product.getPrice().getPrice()));

    }

    private void setUpdateCreateButtonTo(String buttonText) {
        createUpdateButton.setText(buttonText);
    }

    private void clearProductTextFields() {
        barcodeTextField.setText("");
        nameTextField.setText("");
        stockNumberTextField.setText("");
    }

}
