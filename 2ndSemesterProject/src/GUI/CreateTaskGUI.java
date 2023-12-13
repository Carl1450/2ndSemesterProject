package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CreateTaskGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTable table_1;
	private JDatePickerImpl datePicker; 
	private JPanel pnlEventTime; 
	private JPanel contentPanel; 
	private UtilDateModel eventPanelModel;
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
	 * @param eventDateModel 
	 * @param eventDateModel 
	 * @param datePanel 
	 */
	@SuppressWarnings("unchecked")
	public CreateTaskGUI() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 361);
		contentPane = new JPanel();
		contentPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				
			}
		});
		contentPane.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				
			
			}
		});
		contentPane.setBounds(new Rectangle(0, 0, 50, 30));
		contentPane.setPreferredSize(new Dimension(50, 30));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][][grow][grow][grow]", "[][][grow][][][][grow][][]"));
		
		JLabel lblNewLabel = new JLabel("Describtion");
		lblNewLabel.setRequestFocusEnabled(false);
		lblNewLabel.setPreferredSize(new Dimension(50, 30));
		lblNewLabel.setSize(new Dimension(50, 30));
		contentPane.add(lblNewLabel, "cell 0 1");
		
		JLabel lblNewLabel_2 = new JLabel("Janitors");
		contentPane.add(lblNewLabel_2, "cell 2 1");
		
		JLabel lblNewLabel_3 = new JLabel("Task");
		contentPane.add(lblNewLabel_3, "cell 4 1,alignx center");
		
		textField = new JTextField();
		contentPane.add(textField, "cell 0 2,grow");
		textField.setColumns(10);
		
		table_1 = new JTable();
		contentPane.add(table_1, "cell 2 2,grow");
		
		table = new JTable();
		contentPane.add(table, "cell 4 2,grow");
		
		JLabel lblNewLabel_1 = new JLabel("priority");
		contentPane.add(lblNewLabel_1, "cell 0 5,alignx left");
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, "flowx,cell 1 5 2 1,growx,aligny top");
		
		JLabel lblNewLabel_4 = new JLabel("Deadline");
		contentPane.add(lblNewLabel_4, "cell 0 6");
		
		
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				eventPanelModel = new UtilDateModel(); 
				eventPanelModel.setSelected(true); 
				pnlEventTime.setLayout(null);
				datePicker = new JDatePickerImpl(null);
				datePicker.setBounds(20,35,202,21);
				pnlEventTime.add(datePicker);
				Date currentDate = LocalDateTime();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(currentDate);
				int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
				eventPanelModel.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get (Calendar.DAY_OF_MONTH)+1);
				pnlEventTime.add(datePicker);
			}
			private Date LocalDateTime() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {""}));
		contentPane.add(comboBox_1, "cell 2 6,growx");
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		contentPane.add(btnNewButton_1, "flowx,cell 4 8,alignx right");
		
		

		
	}

	private Object JDatePanelImpl(Object eventDateModel) {
		// TODO Auto-generated method stub
		return null;
	}



}
