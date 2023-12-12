package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JTextPane;

public class CreateTaskGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTaskGUI frame = new CreateTaskGUI();
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
	public CreateTaskGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 50, 30));
		contentPane.setPreferredSize(new Dimension(50, 30));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][grow]", "[][][grow][][][]"));
		
		JLabel lblNewLabel = new JLabel("Task");
		lblNewLabel.setRequestFocusEnabled(false);
		lblNewLabel.setPreferredSize(new Dimension(50, 30));
		lblNewLabel.setSize(new Dimension(50, 30));
		contentPane.add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("Prio");
		contentPane.add(lblNewLabel_1, "cell 2 1");
		
		table = new JTable();
		contentPane.add(table, "cell 0 2,grow");
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, "flowx,cell 2 2,growx,aligny top");
		
		JLabel lblNewLabel_2 = new JLabel("EndDate");
		contentPane.add(lblNewLabel_2, "cell 2 3,alignx right");
		
		textField = new JTextField();
		textField.setInheritsPopupMenu(true);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textField, "cell 2 4,alignx right,aligny bottom");
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		contentPane.add(btnNewButton_1, "flowx,cell 2 5,alignx right");
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, "cell 2 5,alignx right");
	}

}
