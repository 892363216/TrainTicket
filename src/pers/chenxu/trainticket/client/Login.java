package pers.chenxu.trainticket.client;
import pers.chenxu.trainticket.server.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.*;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JTextField textField;
	private JLabel label_1;
	private JPasswordField passwordField;
	private JButton button;
	private JButton button_2;
	private JButton button_3;
	private JButton button_1;
	private JLabel label_2;
	private JTextField uname_t;
	private JTextField tel_t;
	private JTextField id_t;
	private JTextField name_t;
	private JTextField acc_t;
	
	//private Message message;
	public ObjectOutputStream toServer;
	public ObjectInputStream fromServer;
	public Socket socket=null;
	private JPasswordField password_t;
	private JPasswordField passwordField_2;
	private MyModel tableModel;
	private JTable table;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private ButtonGroup btg;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private ButtonGroup btg_2;
	private ButtonGroup btg3;//��Ʊҳ������
	private JTextField main_text;
	private JTable table_1;
	private CardLayout cardLayout;
	private String name="�û�";
	private JLabel unameLable;
	private boolean isLog=false;//�û��Ƿ��¼
	private String userInfo[]=new String[6];
	private String trainInfo[][]=null;
	private Object searchTrains[][]=null;
	private String searchPrice[][]=null;
	private Object userIDNum[][]=null;
	private String orderContent[][]=null;
	private String ticContent[][]=null;
	private String zhongzhuan[][]=null;
	
	private JLabel uid_t;
	private JLabel un_t;
	private JLabel uc_t;
	private JLabel utel_t;
	private MyModel dtmCode;//�����β�ѯ�ı��
	private MyModel dtmRem;//��Ʊ��ѯ�ı��
	
	private JTextField start_text;
	private JTextField end_text;
	private Font font=new Font("΢���ź�",Font.PLAIN,14);
	private JTable ID_table;
	private JTextField checi;
	private JTextField chufa;
	private JTextField zhongdian;
	private JTextField fashi;
	private JTextField daoshi;
	private JTextField piaojia;
	private int chooseSeat=4;//Ĭ��Ϊһ����
	private int remain=0;//��Ʊ
	private String orderID;
	private JTable order_table;
	private JTable tic_table;
	private JButton button_6;
	private JTextField newID;
	private JTextField newName;
	private JTextField begin_text;
	private JTextField fini_text;
	private JTable mid_table;
	private JTable pre_table;
	private JTable last_table;
	public Font textFont=new Font("΢���ź�",Font.PLAIN,14);
	public Font infoFont=new Font("�����п�", Font.PLAIN, 14);
	private JPanel mainPane;
	private JTextField month_field;
	private JTextField day_field;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Login() throws IOException {
		Message message=new Message();	
		this.setTitle("��Ʊ��Ʊ������Ϣϵͳ");
		try {
			socket=new Socket("localhost",8000);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//���ӷ�����
		toServer=new ObjectOutputStream(socket.getOutputStream());
		fromServer=new ObjectInputStream(socket.getInputStream());
		

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(460, 100, 526, 466);
		setBounds(100, 100, 883, 450);
		contentPane = new JPanel();
		//contentPane=new picPanel();//try
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//�ܽ������ò��ֹ�����
		cardLayout=new CardLayout();
		contentPane.setLayout(cardLayout);
		
		//panel = new JPanel();
		panel = new picPanel();
		contentPane.add(panel, "��¼ҳ");
		panel.setLayout(null);
		
		label = new JLabel("\u5E10\u53F7");
		label.setBackground(Color.WHITE);
		label.setFont(new Font("��Բ", Font.BOLD, 16));
		label.setBounds(416, 214, 54, 15);
		panel.add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("����", Font.PLAIN, 16));
		textField.setBounds(480, 208, 144, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBackground(Color.WHITE);
		label_1.setFont(new Font("��Բ", Font.BOLD, 16));
		label_1.setBounds(416, 276, 54, 15);
		panel.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("����", Font.PLAIN, 16));
		passwordField.setBounds(480, 270, 144, 25);
		panel.add(passwordField);
		
		button = new JButton("\u767B\u5F55");
		
		
		//��¼��ť
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String info=textField.getText()+" "+passwordField.getText();
				Message message=new Message(0,info);
				try {
					//if(isLogin(message)){//��¼�ɹ���
					if(transMsg(message)){//��¼�ɹ���
						JOptionPane.showMessageDialog(null, "��¼�ɹ���","System Information",JOptionPane.INFORMATION_MESSAGE);
						unameLable.setText(name+",");
						isLog=true;//�ѵ�¼
						
						uid_t.setText(userInfo[0]);
						un_t.setText(userInfo[1]);
						uc_t.setText(userInfo[2]);						
						utel_t.setText(userInfo[3]);
						
						cardLayout.show(contentPane, "mainFrame");
					}
					else JOptionPane.showMessageDialog(null, "�û������������","System Information",JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setFont(new Font("��Բ", Font.PLAIN, 14));
		button.setBounds(462, 333, 65, 23);
		panel.add(button);
		
		button_2 = new JButton("\u6CE8\u518C");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		//ע�ᰴť
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.next(contentPane);
			}
		});
		button_2.setFont(new Font("��Բ", Font.PLAIN, 14));
		button_2.setBounds(559, 333, 65, 23);
		panel.add(button_2);
		
		//��ť������¼
		button_3 = new JButton("������¼>>");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(contentPane, "mainFrame");
			}
		});
		button_3.setFont(new Font("��Բ", Font.PLAIN, 14));
		button_3.setBounds(693, 333, 114, 23);
		panel.add(button_3);
		
		/*
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 867, 332);
		panel_3.add(new picPanel());
		panel.add(panel_3);
		*/
		//panel_1 = new JPanel();
		panel_1 = new picPanel2();
		contentPane.add(panel_1, "ע��ҳ");
		panel_1.setLayout(null);
		
		button_1 = new JButton("<<\u8FD4\u56DE\u767B\u5F55");
		//���ص�¼��ť
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.previous(contentPane);;
			}
		});
		button_1.setFont(new Font("��Բ", Font.PLAIN, 14));
		button_1.setBounds(10, 30, 111, 23);
		panel_1.add(button_1);
		
		label_2 = new JLabel("\u8BF7\u586B\u5199\u6CE8\u518C\u4FE1\u606F\uFF1A");
		label_2.setForeground(new Color(255, 255, 255));
		label_2.setFont(new Font("��Բ", Font.BOLD | Font.ITALIC, 13));
		label_2.setBounds(10, 77, 209, 15);
		panel_1.add(label_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 102, 870, 2);
		panel_1.add(separator);
		
		JLabel uname = new JLabel("�û���");
		uname.setForeground(new Color(255, 255, 255));
		uname.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		uname.setBounds(37, 115, 84, 15);
		panel_1.add(uname);
		
		JLabel upassword = new JLabel("����");
		upassword.setForeground(new Color(255, 255, 255));
		upassword.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		upassword.setBounds(37, 150, 66, 15);
		panel_1.add(upassword);
		
		JLabel tel = new JLabel("�ֻ���");
		tel.setForeground(new Color(255, 255, 255));
		tel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		tel.setBounds(37, 220, 84, 15);
		panel_1.add(tel);
		
		JLabel idnum = new JLabel("���֤��");
		idnum.setForeground(new Color(255, 255, 255));
		idnum.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		idnum.setBounds(37, 255, 77, 15);
		panel_1.add(idnum);
		
		JLabel name = new JLabel("����");
		name.setForeground(new Color(255, 255, 255));
		name.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		name.setBounds(37, 290, 77, 15);
		panel_1.add(name);
		
		JLabel account = new JLabel("�˻����");
		account.setForeground(new Color(255, 255, 255));
		account.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		account.setBounds(37, 325, 84, 15);
		panel_1.add(account);
		
		uname_t = new JTextField();
		uname_t.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		uname_t.setBounds(130, 110, 134, 23);
		panel_1.add(uname_t);
		uname_t.setColumns(10);
		
		tel_t = new JTextField();
		tel_t.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		tel_t.setColumns(10);
		tel_t.setBounds(130, 215, 134, 23);
		panel_1.add(tel_t);
		
		id_t = new JTextField();
		id_t.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		id_t.setColumns(10);
		id_t.setBounds(130, 250, 134, 23);
		panel_1.add(id_t);
		
		name_t = new JTextField();
		name_t.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		name_t.setColumns(10);
		name_t.setBounds(130, 285, 134, 23);
		panel_1.add(name_t);
		
		acc_t = new JTextField();
		acc_t.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		acc_t.setColumns(10);
		acc_t.setBounds(130, 320, 134, 23);
		panel_1.add(acc_t);
	
		////////////////////////////////////////////////////////////////////////////////////
		//ע��ҳ��ע�ᰴť
		JButton button_4 = new JButton("\u70B9\u51FB\u6CE8\u518C...");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String str=uname_t.getText()+" "+password_t.getText()+" "+acc_t.getText()+" "+id_t.getText()
							+" "+name_t.getText()+" "+tel_t.getText();
				Message sign=new Message(1,str);
	
				try {
					if(transMsg(sign)){
						JOptionPane.showMessageDialog(null, "ע��ɹ���\n �����ʺ�Ϊ��"+orderID+",���������ʺŵ�¼��","System Information",JOptionPane.INFORMATION_MESSAGE);
						cardLayout.show(contentPane, "��¼ҳ");
					}
					
					else JOptionPane.showMessageDialog(null, "�û������Ϸ���","System information",JOptionPane.ERROR_MESSAGE);
				} catch (HeadlessException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_4.setFont(new Font("��Բ", Font.PLAIN, 14));
		button_4.setBounds(233, 365, 111, 23);
		panel_1.add(button_4);
		
		JLabel lblNewLabel = new JLabel("\u91CD\u590D\u5BC6\u7801");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		lblNewLabel.setBounds(37, 185, 84, 15);
		panel_1.add(lblNewLabel);
		
		password_t = new JPasswordField();
		password_t.setBounds(129, 143, 135, 26);
		panel_1.add(password_t);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(130, 179, 135, 26);
		panel_1.add(passwordField_2);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, panel_1}));
		
		mainPane=new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(null);
		
		JPanel main_panel_1 = new JPanel();
		main_panel_1.setBounds(5, 0, 857, 412);
		mainPane.add(main_panel_1);
		main_panel_1.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tabbedPane.setBounds(157, 10, 713, 402);
		main_panel_1.add(tabbedPane);
		
		JPanel main_panel_2 = new JPanel();
		tabbedPane.addTab("��ѯ", null, main_panel_2, null);
		main_panel_2.setLayout(null);
		
		JPanel queryPane = new JPanel();
		queryPane.setBounds(0, 10, 756, 347);
		main_panel_2.add(queryPane);
		
		CardLayout card=new CardLayout();
		queryPane.setLayout(card);
		JPanel remainPane = new JPanel();
		remainPane.setForeground(Color.LIGHT_GRAY);
		queryPane.add(remainPane, "���β�ѯ");
		remainPane.setLayout(null);
		
		JLabel main_label = new JLabel("\u51FA\u53D1\u5730");
		main_label.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		main_label.setBounds(246, 65, 54, 15);
		remainPane.add(main_label);
		
		JLabel main_label_1 = new JLabel("\u76EE\u7684\u5730");
		main_label_1.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		main_label_1.setBounds(428, 65, 54, 15);
		remainPane.add(main_label_1);
		
		JLabel main_label2 = new JLabel("\u6708");
		main_label2.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		main_label2.setBounds(94, 54, 24, 36);
		remainPane.add(main_label2);
		
/////////��Ʊ��ѯ��ť
		JButton main_button = new JButton("\u4F59\u7968\u67E5\u8BE2");
		main_button.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		main_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		main_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String start=start_text.getText();
				String end=end_text.getText();
				String info=start+" "+end+" "+month_field.getText()+"/"+day_field.getText();
				Message msg=new Message(2,info);
				
				try {
					//if(searchTrain(msg)){
					if(transMsg(msg)){
						String[] schTraTitle={"����", "����ʱ��", "����ʱ��", "���", "һ����","������"};
						dtmRem=new MyModel(searchTrains,schTraTitle);
						table.setModel(dtmRem);
						Message msg1=new Message(4,info);
						//searchTrainPrice(msg1);
						transMsg(msg1);
					}
					else JOptionPane.showMessageDialog(null, "û��ֱ���г���","System Information",JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		main_button.setBounds(583, 56, 103, 32);
		remainPane.add(main_button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setAutoscrolls(true);
		scrollPane.setViewportBorder(new LineBorder(Color.LIGHT_GRAY));
		scrollPane.setBounds(45, 110, 630, 187);
		remainPane.add(scrollPane);
		
		tableModel=new MyModel(new Object[][] {
			{" ", " ", " ", " ", " "," "},
		},
		new String[] {
			"����", "����ʱ��", "����ʱ��", "���", "һ����","������"
		});
		
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.getTableHeader().setFont(font);
		table.setShowVerticalLines(false);
		table.setRowHeight(24);
		table.setBackground(new Color(192, 192, 192));
		table.setForeground(Color.BLACK);
		table.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		table.setModel(new DefaultTableModel(
			
		));
		scrollPane.setViewportView(table);
		
		
		radioButton = new JRadioButton("\u4F59\u7968\u67E5\u8BE2");
		radioButton.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		radioButton.setSelected(true);
		radioButton.setBounds(84, 23, 121, 23);
		remainPane.add(radioButton);
		
		//��һ�Ű����β�ѯ��ť
		radioButton_1 = new JRadioButton("\u8F66\u6B21\u67E5\u8BE2");
		radioButton_1.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		radioButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				radioButton.setSelected(true);
				card.next(queryPane);
			}
		});
		radioButton_1.setBounds(232, 23, 121, 23);
		remainPane.add(radioButton_1);
		
		btg=new ButtonGroup();
		btg_2=new ButtonGroup();
		
		btg.add(radioButton);
		btg.add(radioButton_1);
		
		JLabel label_4 = new JLabel("\u65E5");
		label_4.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		label_4.setBounds(189, 65, 21, 15);
		remainPane.add(label_4);
		
/////////���β�ѯ����
		JPanel trainInfoPane = new JPanel();
		queryPane.add( trainInfoPane, "���β�ѯ");
		trainInfoPane.setLayout(null);
	//������Ʊ��ѯ��ť	
		radioButton_2 = new JRadioButton("��Ʊ��ѯ");
		radioButton_2.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		radioButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.previous(queryPane);
				radioButton_3.setSelected(true);
			}
		});
		radioButton_2.setBounds(84, 23, 121, 23);
		trainInfoPane.add(radioButton_2);
		
		radioButton_3 = new JRadioButton("���β�ѯ");
		radioButton_3.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		radioButton_3.setSelected(true);
		radioButton_3.setBounds(232, 23, 121, 23);
		trainInfoPane.add(radioButton_3);
		
		btg_2.add(radioButton_2);
		btg_2.add(radioButton_3);
		
		JLabel main_label_3 = new JLabel("\u8F66\u6B21");
		main_label_3.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		main_label_3.setBounds(268, 76, 54, 15);
		trainInfoPane.add(main_label_3);
		
		main_text = new JTextField();
		main_text.setBounds(314, 73, 66, 21);
		trainInfoPane.add(main_text);
		main_text.setColumns(10);
		
		//�����β�ѯ��ť�������¼�
		JButton main_button_1 = new JButton("\u67E5\u8BE2");
		main_button_1.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		main_button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String traincode=main_text.getText();
				Message msg=new Message(3,traincode);
				try {
					//if(queryTrainByCode(msg))
					if(transMsg(msg))
					{	
						String[] titleCode={"վ��","վ��","��վʱ��","����ʱ��","���"};
						dtmCode=new MyModel(trainInfo,titleCode);
						table_1.setModel(dtmCode);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		main_button_1.setBounds(438, 72, 66, 23);
		trainInfoPane.add(main_button_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(45, 110, 630, 187);
		trainInfoPane.add(scrollPane_2);
		
		
		table_1 = new JTable();
		table_1.setRowHeight(24);
		table_1.setBackground(Color.LIGHT_GRAY);
		table_1.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		table_1.setShowVerticalLines(false);
		scrollPane_2.setViewportView(table_1);

////////��Ʊ���		
		JPanel buy_panel = new JPanel();
		tabbedPane.addTab("��Ʊ", null, buy_panel, null);
		buy_panel.setLayout(null);
		
		JLabel label_12 = new JLabel("ѡ��˿�");
		label_12.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_12.setBounds(41, 25, 73, 21);
		buy_panel.add(label_12);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(41, 60, 474, 61);
		buy_panel.add(scrollPane_3);
		
		ID_table = new JTable();
		ID_table.setBackground(Color.LIGHT_GRAY);
		ID_table.setShowVerticalLines(false);
		ID_table.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		ID_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_3.setViewportView(ID_table);
		
	//��Ʊҳ���ȷ�ϰ�ť	
		JButton button_9 = new JButton("ȷ��");
		button_9.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		button_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			try {
				buyTicket();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			}
		});
		
		button_9.setBounds(537, 313, 93, 30);
		buy_panel.add(button_9);
		
		JLabel label_13 = new JLabel("\u8BA2\u5355\u4FE1\u606F\uFF1A");
		label_13.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_13.setBounds(41, 155, 73, 21);
		buy_panel.add(label_13);
		
		JLabel buy_train_no = new JLabel("\u8F66\u6B21\uFF1A");
		buy_train_no.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_train_no.setBounds(41, 212, 54, 15);
		buy_panel.add(buy_train_no);
		
		JLabel buy_start_station = new JLabel("\u51FA\u53D1\u7AD9\uFF1A");
		buy_start_station.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_start_station.setBounds(41, 250, 54, 15);
		buy_panel.add(buy_start_station);
		
		JLabel buy_end_station = new JLabel("\u7EC8\u70B9\u7AD9\uFF1A");
		buy_end_station.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_end_station.setBounds(41, 291, 54, 15);
		buy_panel.add(buy_end_station);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(41, 184, 474, 2);
		buy_panel.add(separator_1);
		
		JLabel buy_start_time = new JLabel("\u51FA\u53D1\u65F6\u95F4\uFF1A");
		buy_start_time.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_start_time.setBounds(344, 250, 73, 15);
		buy_panel.add(buy_start_time);
		
		JLabel buy_end_time = new JLabel("\u5230\u8FBE\u65F6\u95F4\uFF1A");
		buy_end_time.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_end_time.setBounds(344, 291, 73, 15);
		buy_panel.add(buy_end_time);
		
		checi = new JTextField();
		checi.setEditable(false);
		checi.setBounds(105, 209, 93, 21);
		buy_panel.add(checi);
		checi.setColumns(10);
		
		chufa = new JTextField();
		chufa.setEditable(false);
		chufa.setBounds(105, 247, 93, 21);
		buy_panel.add(chufa);
		chufa.setColumns(10);
		
		zhongdian = new JTextField();
		zhongdian.setEditable(false);
		zhongdian.setColumns(10);
		zhongdian.setBounds(105, 288, 93, 21);
		buy_panel.add(zhongdian);
		
		fashi = new JTextField();
		fashi.setEditable(false);
		fashi.setColumns(10);
		fashi.setBounds(422, 247, 93, 21);
		buy_panel.add(fashi);
		
		daoshi = new JTextField();
		daoshi.setEditable(false);
		daoshi.setColumns(10);
		daoshi.setBounds(422, 288, 93, 21);
		buy_panel.add(daoshi);
		
		JLabel buy_price = new JLabel("\u7968\u4EF7\uFF1A");
		buy_price.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		buy_price.setBounds(344, 212, 54, 15);
		buy_panel.add(buy_price);
		
		piaojia = new JTextField();
		piaojia.setEditable(false);
		piaojia.setColumns(10);
		piaojia.setBounds(422, 209, 93, 21);
		buy_panel.add(piaojia);
		
		JButton button_8 = new JButton("ˢ��");
		button_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message msgs=new Message(5,uid_t.getText());
				try {
					transMsg(msgs);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				String[] title={"���֤��","����","ȷ��"};
				MyModel mdo=new MyModel(userIDNum,title);
				ID_table.setModel(mdo);
			}
		});
		button_8.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		button_8.setBounds(537, 91, 93, 30);
		buy_panel.add(button_8);
		
////////�˿���Ϣ���		
		JPanel main_panel_4 = new JPanel();
		tabbedPane.addTab("�˿���Ϣ", null, main_panel_4, null);
		main_panel_4.setLayout(null);
		
		JLabel label_5 = new JLabel("�ʺ�");
		label_5.setBounds(65, 66, 54, 15);
		main_panel_4.add(label_5);
		label_5.setFont(textFont);
		
		JLabel label_7 = new JLabel("�û���");
		label_7.setBounds(65, 123, 54, 15);
		main_panel_4.add(label_7);
		label_7.setFont(textFont);
		
		JLabel label_8 = new JLabel("���");
		label_8.setBounds(65, 180, 54, 15);
		main_panel_4.add(label_8);
		label_8.setFont(textFont);
		
		JLabel label_11 = new JLabel("�ֻ���");
		label_11.setBounds(65, 243, 54, 15);
		main_panel_4.add(label_11);
		label_11.setFont(textFont);
		
		uid_t = new JLabel("\u672A\u77E5");
		uid_t.setFont(infoFont);
		uid_t.setBounds(165, 68, 159, 15);
		main_panel_4.add(uid_t);
			
		un_t = new JLabel("\u672A\u77E5");
		un_t.setBounds(165, 125, 159, 15);
		main_panel_4.add(un_t);
		un_t.setFont(infoFont);
		
		uc_t = new JLabel("\u672A\u77E5");
		uc_t.setBounds(165, 180, 159, 15);
		main_panel_4.add(uc_t);
		uc_t.setFont(infoFont);
		
		utel_t = new JLabel("\u672A\u77E5");
		utel_t.setBounds(165, 245, 159, 15);
		main_panel_4.add(utel_t);
		utel_t.setFont(infoFont);
		
		JButton button_13 = new JButton("ˢ��");
		button_13.setFont(textFont);
		button_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshUser();
			}
		});
		button_13.setBounds(65, 315, 93, 23);
		main_panel_4.add(button_13);
		
		JLabel label_17 = new JLabel("\u6DFB\u52A0\u4E58\u5BA2\uFF1A");
		label_17.setBounds(386, 139, 74, 15);
		main_panel_4.add(label_17);
		label_17.setFont(textFont);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(386, 164, 196, 2);
		main_panel_4.add(separator_4);
		
		JLabel label_18 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		label_18.setBounds(386, 198, 74, 15);
		main_panel_4.add(label_18);
		label_18.setFont(textFont);
		
		newID = new JTextField();
		newID.setBounds(474, 196, 108, 21);
		main_panel_4.add(newID);
		newID.setColumns(10);
		
		JLabel label_19 = new JLabel("\u59D3\u540D");
		label_19.setBounds(415, 239, 54, 15);
		main_panel_4.add(label_19);
		label_19.setFont(textFont);
		
		newName = new JTextField();
		newName.setBounds(474, 237, 106, 21);
		main_panel_4.add(newName);
		newName.setColumns(10);
//��ӳ˿�
		JButton button_20 = new JButton("\u6DFB\u52A0");
		button_20.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		button_20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String str=uid_t.getText()+" "+newID.getText()+" "+newName.getText();
	            Message add=new Message(15,str);
	            try {
					if(!transMsg(add)){
						JOptionPane.showMessageDialog(null, "�����ظ���ӣ�","System Information",JOptionPane.ERROR_MESSAGE);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_20.setBounds(386, 283, 93, 23);
		main_panel_4.add(button_20);
		
		JPanel main_panel_5 = new JPanel();
		tabbedPane.addTab("�ҵĶ���", null, main_panel_5, null);
		main_panel_5.setLayout(null);
		
		JLabel label_14 = new JLabel("\u5168\u90E8\u8BA2\u5355\uFF1A");
		label_14.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_14.setBounds(25, 42, 72, 27);
		main_panel_5.add(label_14);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(25, 69, 401, 2);
		main_panel_5.add(separator_2);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(25, 84, 403, 118);
		main_panel_5.add(scrollPane_4);
		
		order_table = new JTable();
		order_table.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		order_table.setShowVerticalLines(false);
		order_table.setBackground(Color.LIGHT_GRAY);
		scrollPane_4.setViewportView(order_table);
		
		JLabel label_15 = new JLabel("\u8BA2\u5355\u8BE6\u60C5\uFF1A");
		label_15.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_15.setBounds(25, 220, 72, 22);
		main_panel_5.add(label_15);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		scrollPane_5.setBounds(25, 265, 624, 91);
		main_panel_5.add(scrollPane_5);
		
		tic_table = new JTable();
		tic_table.setRowHeight(24);
		tic_table.setBackground(Color.LIGHT_GRAY);
		tic_table.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		tic_table.setShowVerticalLines(false);
		scrollPane_5.setViewportView(tic_table);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(25, 252, 624, 2);
		main_panel_5.add(separator_3);
		
		JButton button_10 = new JButton("\u8BA2\u5355\u8BE6\u60C5");
		button_10.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		button_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int cursors=order_table.getSelectedRow();
				if(cursors>=0){
					String order_id=(String) order_table.getValueAt(cursors, 0);
					Message orinfo=new Message(12,order_id);
					try {
						transMsg(orinfo);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String[] ticTitle={"��Ʊ���","�����","����","��λ����","��������","����","���֤��","Ʊ��","������","Ŀ�ĵ�"};
					MyModel ticmodel=new MyModel(ticContent,ticTitle);
					tic_table.setModel(ticmodel);
				}
			}
		});
		button_10.setBounds(461, 170, 93, 32);
		main_panel_5.add(button_10);
	
		//ȡ��������ť	
		JButton button_11 = new JButton("\u53D6\u6D88\u8BA2\u5355");
		button_11.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		button_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int target=order_table.getSelectedRow();
				if(target>=0){
					String cancelId=(String) order_table.getValueAt(target, 0);
					Message cancel=new Message(13,cancelId);
					try {
						if(transMsg(cancel)){
							JOptionPane.showMessageDialog(null, "ȡ�������ɹ���","System Information",JOptionPane.INFORMATION_MESSAGE);
						}
						else JOptionPane.showMessageDialog(null, "������ʧЧ������ȡ����","System Information",JOptionPane.INFORMATION_MESSAGE);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					refreshOrder();
				}
			}
		});
		button_11.setBounds(461, 128, 93, 32);
		main_panel_5.add(button_11);
	
		//����ҳ��ˢ�°�ť
		JButton button_12 = new JButton("\u5237\u65B0");
		button_12.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		button_12.addMouseListener(new MouseAdapter() {
			@Override//���ˢ�°�ť�������¼�
			public void mouseClicked(MouseEvent arg0) {
		            refreshOrder();
			}
		});
		button_12.setBounds(461, 84, 93, 34);
		main_panel_5.add(button_12);
		
		//�������ں�ʱ��Ĺ���
		SpinnerNumberModel spmodel=new SpinnerNumberModel(9,9,9,1);
		
		SpinnerNumberModel model2=new SpinnerNumberModel(20,20,21,1);
		
		start_text = new JTextField();
		start_text.setBounds(287, 62, 81, 21);
		remainPane.add(start_text);
		start_text.setColumns(10);
		
		end_text = new JTextField();
		end_text.setColumns(10);
		end_text.setBounds(472, 62, 81, 21);
		remainPane.add(end_text);
		
		//��ѯƱ�۰�ť�������¼�
		JButton button_7 = new JButton("\u7968\u4EF7\u67E5\u8BE2");
		button_7.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		button_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String start=start_text.getText();
				String end=end_text.getText();
				String info=start+" "+end;
				Message msg=new Message(4,info);
				
				try {
					//if(searchTrainPrice(msg)){
					if(transMsg(msg)){
						String[] schTraTitle={"����", "����ʱ��", "����ʱ��", "���", "һ����","������"};
						dtmRem=new MyModel(searchPrice,schTraTitle);
						table.setModel(dtmRem);
					}
					else JOptionPane.showMessageDialog(null, "û��ֱ���г���","System Information",JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException ev) {
					// TODO Auto-generated catch block
					ev.printStackTrace();
				}
			}
		});
		button_7.setBounds(583, 14, 103, 32);
		remainPane.add(button_7);

////////��ť������ô��г���Ʊ��
		JButton buy_button = new JButton("����ô��г���Ʊ");
		buy_button.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		buy_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message msg=new Message(5,uid_t.getText());
				if(isLog){
					try {
						transMsg(msg);
						String[] title={"���֤��","����","ȷ��"};
						MyModel mdo=new MyModel(userIDNum,title);
						ID_table.setModel(mdo);
						
						int c=table.getSelectedRow();
						if(c>=0){
						//remain=Integer.parseInt((String) searchTrains[c][chooseSeat]);
						checi.setText((String) table.getValueAt(c, 0));
						chufa.setText(start_text.getText());
						zhongdian.setText(end_text.getText());
						piaojia.setText(searchPrice[c][chooseSeat]);
						fashi.setText((String) table.getValueAt(c, 1));
						daoshi.setText((String) table.getValueAt(c, 2));
						
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else JOptionPane.showMessageDialog(null, "�𾴵��û������ȵ�¼��","System Information",JOptionPane.ERROR_MESSAGE);
				tabbedPane.setSelectedComponent(buy_panel);
			}
		});
		buy_button.setBounds(492, 307, 198, 30);
		remainPane.add(buy_button);
		
		JRadioButton first_class = new JRadioButton("һ����");
		first_class.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		first_class.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chooseSeat=4;
			}
		});
		first_class.setSelected(true);
		first_class.setBounds(232, 311, 121, 23);
		remainPane.add(first_class);
		
		JRadioButton second_class = new JRadioButton("������");
		second_class.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		second_class.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooseSeat=5;
			}
		});
		second_class.setBounds(361, 311, 103, 23);
		remainPane.add(second_class);
		
		btg3=new ButtonGroup();
		btg3.add(first_class);
		btg3.add(second_class);
		
		month_field = new JTextField();
		month_field.setBounds(30, 62, 54, 21);
		month_field.setText("10");//Ĭ����10��10
		remainPane.add(month_field);
		month_field.setColumns(10);
		
		day_field = new JTextField();
		day_field.setColumns(10);
		day_field.setBounds(125, 62, 54, 21);
		day_field.setText("10");
		remainPane.add(day_field);
		
		//��ת��ѯ����
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("��ת��ѯ", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel label_16 = new JLabel("���");
		label_16.setBounds(204, 44, 54, 15);
		panel_3.add(label_16);
		label_16.setFont(textFont);
		
		begin_text = new JTextField();
		begin_text.setBounds(256, 41, 66, 21);
		panel_3.add(begin_text);
		begin_text.setColumns(10);
		
		JLabel label_20 = new JLabel("�յ�");
		label_20.setBounds(382, 44, 54, 15);
		panel_3.add(label_20);
		label_20.setFont(textFont);
		
		fini_text = new JTextField();
		fini_text.setBounds(429, 41, 66, 21);
		panel_3.add(fini_text);
		fini_text.setColumns(10);
		
		JButton chaxun = new JButton("��ѯ");
		chaxun.setFont(textFont);
		chaxun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String infos=begin_text.getText()+" "+fini_text.getText();
				Message msg=new Message(16,infos);
				String []titles={"��תվ"};
				try {
					transMsg(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultTableModel def=new DefaultTableModel(zhongzhuan,titles);
				mid_table.setModel(def);
			}
		});
		chaxun.setBounds(569, 41, 93, 22);
		panel_3.add(chaxun);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(33, 78, 104, 222);
		panel_3.add(scrollPane_1);
		
		mid_table = new JTable();
		mid_table.setBackground(Color.LIGHT_GRAY);
		mid_table.setRowHeight(24);
		mid_table.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		scrollPane_1.setViewportView(mid_table);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		scrollPane_6.setBounds(177, 115, 506, 88);
		panel_3.add(scrollPane_6);
		
		pre_table = new JTable();
		pre_table.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		pre_table.setRowHeight(24);
		pre_table.setBackground(Color.LIGHT_GRAY);
		scrollPane_6.setViewportView(pre_table);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(177, 243, 506, 94);
		panel_3.add(scrollPane_7);
		
		last_table = new JTable();
		last_table.setRowHeight(24);
		last_table.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		last_table.setBackground(Color.LIGHT_GRAY);
		scrollPane_7.setViewportView(last_table);
	/////�鿴����
		JButton button_14 = new JButton("�鿴����");
		button_14.setFont(textFont);
		button_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String start=begin_text.getText();
				int tar=mid_table.getSelectedRow();
				String end=(String) mid_table.getValueAt(tar, 0);
				Message msg=new Message(2,start+" "+end+" "+"10/10");
				String[] schTraTitle={"����", "����ʱ��", "����ʱ��", "���", "һ����","������"};
				 try {//��ʾ��ת������ϸ��Ϣ
				transMsg(msg);
				DefaultTableModel dtm=new DefaultTableModel(searchTrains,schTraTitle);
				pre_table.setModel(dtm);
				Message msgs=new Message(2,end+" "+fini_text.getText()+" "+"10/10");	
				transMsg(msgs);
				DefaultTableModel dtms=new DefaultTableModel(searchTrains,schTraTitle);
				last_table.setModel(dtms);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
		});
		button_14.setBounds(33, 314, 104, 23);
		panel_3.add(button_14);
		
		JLabel label_21 = new JLabel("\u8D77\u70B9");
		label_21.setBounds(268, 89, 54, 15);
		panel_3.add(label_21);
		label_21.setFont(textFont);
		
		JLabel label_22 = new JLabel("\u2014\u2014\u2014\u2014>");
		label_22.setBounds(338, 90, 78, 15);
		panel_3.add(label_22);
		
		JLabel label_23 = new JLabel("\u4E2D\u8F6C\u7AD9");
		label_23.setBounds(441, 90, 54, 15);
		panel_3.add(label_23);
		label_23.setFont(textFont);
		
		JLabel label_24 = new JLabel("\u4E2D\u8F6C\u7AD9");
		label_24.setBounds(268, 218, 54, 15);
		panel_3.add(label_24);
		label_24.setFont(textFont);
		
		JLabel label_25 = new JLabel("\u2014\u2014\u2014\u2014>");
		label_25.setBounds(338, 218, 78, 15);
		panel_3.add(label_25);
		
		JLabel label_26 = new JLabel("\u7EC8\u70B9");
		label_26.setBounds(441, 218, 54, 15);
		panel_3.add(label_26);
		label_26.setFont(textFont);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 137, 382);
		main_panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label_3 = new JLabel("\u5C0A\u656C\u7684");
		label_3.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_3.setBounds(0, 10, 52, 15);
		panel_2.add(label_3);
		
		unameLable = new JLabel("\u7528\u6237\uFF0C");
		unameLable.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		unameLable.setBounds(49, 11, 54, 15);
		panel_2.add(unameLable);
		
		JLabel label_6 = new JLabel("\u6B22\u8FCE\u6765\u5230\u552E\u7968\u7CFB\u7EDF");
		label_6.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_6.setBounds(0, 35, 137, 27);
		panel_2.add(label_6);
		
		JButton button_5 = new JButton("\u767B\u5F55");
		
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(contentPane, "��¼ҳ");
				start_text.setText("");
				end_text.setText("");
				String[] schTraTitle={"����", "����ʱ��", "����ʱ��", "���", "һ����","������"};
				searchTrains=null;
				dtmRem=new MyModel(searchTrains,schTraTitle);
				table.setModel(dtmRem);
			}
		});
		button_5.setFont(new Font("��Բ", Font.PLAIN, 16));
		button_5.setBounds(20, 94, 93, 37);
		panel_2.add(button_5);
		
		button_6 = new JButton("\u6CE8\u518C");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "ע��ҳ");
			}
		});
		button_6.setFont(new Font("��Բ", Font.PLAIN, 16));
		button_6.setBounds(20, 156, 93, 37);
		panel_2.add(button_6);
		
		//�����¼����ô��¼��ť��û����
		if(isLog){
			button_5.setEnabled(false);
			button_6.setEnabled(false);
			}
		else {button_5.setEnabled(true);
		      button_6.setEnabled(true);
		}
		contentPane.add(mainPane,"mainFrame");
		Dimension d=new Dimension(20,20);
		
		//�ر�ʱ��
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(final WindowEvent wde){
				try {
					Message msg=new Message(30,"");
					transMsg(msg);
					toServer.close();
					fromServer.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				System.exit(0);
			}
		});
	}
	
	public boolean transMsg(Message msg) throws ClassNotFoundException{
		try{
			toServer.writeObject(msg);
			toServer.flush();
			Object obj=fromServer.readObject();
			if(obj instanceof Message){
				Message mes=(Message)obj;
				if(mes.getInfo()!=null){
					String[] cache=mes.getInfo().split(" ");
					int type=mes.getType();
					switch(type){
					case 0:{//��¼
						userInfo=mes.getInfo().split(" ");
						name=userInfo[1];
						break;
					}
					case 1:{//ע��
						if(cache[0].equals("false"))
							return false;
						else orderID=cache[0];
					}
					case 2:{//��ѯ��Ʊ�ķ��ؽ��
						int sum=cache.length/6;
						searchTrains=new Object[sum][6];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++){
							for(int j=0;j<6;j++){
								if(cursor<cache.length){
								searchTrains[i][j]=cache[cursor];
								cursor++;}
							}//�Ƿ�����ʼ��Ϊδѡ��
						}
						break;
					}
					case 3:{//��ѯ����
						int sum=cache.length/5;
						trainInfo=new String[sum][5];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++)
							for(int j=0;j<5;j++){
								if(cursor<cache.length){
								trainInfo[i][j]=cache[cursor];
								cursor++;}
							}
						break;
					}
					case 4:{//��ѯƱ�۷��صĽ��
						int sum=cache.length/6;
						searchPrice=new String[sum][6];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++){
							for(int j=0;j<6;j++){
								if(cursor<cache.length){
								searchPrice[i][j]=cache[cursor];
								cursor++;}
							}
						}
						break;
					}
					case 5:{//��ѯ�û��ʺ��µĳ˿�
						int sum=cache.length/2;
						userIDNum=new Object[sum][3];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++){
							for(int j=0;j<2;j++){
								if(cursor<cache.length){
									userIDNum[i][j]=cache[cursor];
								cursor++;}
							}userIDNum[i][2]=false;//ȷ�Ͽ��ʼ��Ϊδѡ��
						}
						break;
					}
					case 6:{
						orderID=cache[0];
						break;
					}
					case 7:{//��ѯ�Ƿ�֮ǰ�ѹ�
						if(cache[0].equals("true"))
							return true;	
						else return false;
					}
					case 8:{//��ѯʵʱ��Ʊ
						remain=Integer.parseInt(cache[0]);
						break;
					}
					case 9:{//��Ʊ
						if(cache[0].equals(true))
							return true;
						else return false;
					}
					case 10:{//��Ʊ
						
						break;
					}
					case 11:{//ˢ�¶���
						int sum=cache.length/5;
						orderContent=new String[sum][4];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++){							
					        orderContent[i][0]=cache[cursor];
					        orderContent[i][1]=cache[cursor+1]+"-"+cache[cursor+2];
					        orderContent[i][2]=cache[cursor+3];
					        orderContent[i][3]=cache[cursor+4];								
						    cursor+=5;
							}
						break;
					}
					case 12:{//�õ���������
						int sum=cache.length/10;
						System.out.println(cache.length);
						ticContent=new String[sum][10];   //�ѽ���ֽ⵽��ά������
						int cursor=0;
						for(int i=0;i<sum;i++){							
							for(int j=0;j<10;j++){
								if(cursor<cache.length){
									ticContent[i][j]=cache[cursor];
								cursor++;}
							}
							}
						break;
					}
					case 13:{//ȡ������
						if(cache[0].equals("true"))
							return true;
						else return false;
					}
					case 14:{//�����û���Ϣ
						userInfo=mes.getInfo().split(" ");
						break;
					}
					case 15:{//���ӳ�Ա
						if(cache[0].equals("true"))
							return true;
						else return false;
					}
					case 16:{//������תվ
						int sum=cache.length;
						zhongzhuan=new String[sum][1];
						for(int i=0;i<sum;i++){
							zhongzhuan[i][0]=cache[i];
						}
						break;
					}
					}
					return true;
			}}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return false;
	}
	
	public void buyTicket() throws ClassNotFoundException{//�ܹ��ɹ���Ʊ��ǰ���У�����Ʊ������㹻���˿Ͳ����������յĵ����г�
		boolean canBuy=true;
		int n=0;//ѡ�еĳ˿�����
		String dep_day=month_field.getText()+"/"+day_field.getText();//��������
		int sum=ID_table.getRowCount();
		ArrayList<String> IDN=new ArrayList<String>();
		for(int i=0;i<sum;i++){
			if((boolean) ID_table.getValueAt(i, 2)){
				IDN.add((String) ID_table.getValueAt(i, 0));
			n++;
		}}
		double totalPrice=n*Double.parseDouble(piaojia.getText());
		int answer=JOptionPane.showConfirmDialog(null, "��Ԥ����"+month_field.getText()+"��"+day_field.getText()+"���� "+
										start_text.getText()+" ���� "+end_text.getText()+" ��"+checi.getText()+"���г�"+
										"\n"+"��"+n+"��Ʊ��"+"�ϼ�"+totalPrice+"Ԫ",
										"ȷ�϶�����Ϣ",JOptionPane.OK_CANCEL_OPTION);
		
		if(answer==JOptionPane.OK_OPTION){//ȷ��
			 	String seatType;
				if(chooseSeat==4) {seatType="1st_seat";}
				else seatType="2nd_seat";
			    Message mess=new Message(8,dep_day+" "+checi.getText()+" "+seatType);//��ѯʵʱ��Ʊ
			    transMsg(mess);//����Ϣ���ݳ�ȥ
	    
			if(Double.parseDouble(uc_t.getText())<totalPrice){//������
	    		JOptionPane.showMessageDialog(null, "���㣡","��Ʊʧ��",JOptionPane.ERROR_MESSAGE);
	    	    canBuy=false;//������
    	} 
			else if(remain<n){//û����Ʊ��
	    	JOptionPane.showMessageDialog(null, "��Ʊ���㣡","��Ʊʧ��",JOptionPane.ERROR_MESSAGE);
	    	canBuy=false;//������
	    }
	    else{	
	    	
	    		for(int index=0;index<n;index++){
	    			String ticInfo=IDN.get(index)+" "+dep_day+" "+checi.getText();
	    			Message check=new Message(7,ticInfo);//����Ƿ�ó˿��ѹ����г����ճ�Ʊ
	    			try {
						if(transMsg(check)){
							JOptionPane.showMessageDialog(null, "���Ķ������ظ��������¹�Ʊ","��Ʊʧ��",JOptionPane.ERROR_MESSAGE);
							canBuy=false;//������
							return;
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}	    
	    		if(canBuy==true){//�����򣬹���    		
	    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    String date=sdf.format(new Date());//�õ��µ�����
			    String infos=uid_t.getText()+" "+date+" "+n+" "+"��"+" "+totalPrice;//��Ϣ������ֱ�Ϊ���û�id���µ����ڡ���Ʊ����
			    Message msg=new Message(6,infos);					//�Ƿ���Ч									
			    transMsg(msg);//���Ӷ���,��ʱ�Ѿ��õ��˶�����
			    
			    //ѭ������Ʊ�ӽ�ȥ���˿��ж��پͼӶ���
			   for(int cur=0;cur<n;cur++){
				   String IDNumber=IDN.get(cur);
			       String ticInfoma=seatType+" "+dep_day+" "+orderID+" "+checi.getText()
			       					+" "+IDNumber+" "+piaojia.getText()+" "
			       					+chufa.getText()+" "+zhongdian.getText()+" "+uid_t.getText();//��Ϣ��˳����λ���͡���ʱ�������š����α�š�
			       																//���֤�š�Ʊ�ۡ������ء�Ŀ�ĵء��û��ʺ�
			   Message ticMsg=new Message(10,ticInfoma);
			   transMsg(ticMsg);
			   }
				}
	}
	}}
	
	public void refreshOrder(){
		String[] orderTitle={"������","�µ�����","��Ʊ����","�ܼ�"};
		Message orIn=new Message(11,uid_t.getText());
		try {
			transMsg(orIn);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultTableModel dtmm=new DefaultTableModel(orderContent,orderTitle);
		order_table.setModel(dtmm);
	}
	public void refreshUser(){
		Message msg=new Message(14,uid_t.getText());
		try {
			transMsg(msg);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uid_t.setText(userInfo[0]);
		un_t.setText(userInfo[1]);
		uc_t.setText(userInfo[2]);						
		utel_t.setText(userInfo[3]);
	}
	
	class picPanel extends JPanel{
		ImageIcon icon;  
		Image img;  
		public picPanel()  
		{   
		icon=new ImageIcon("photo.jpg");  
		img=icon.getImage();  
		}   
		public void paintComponent(Graphics g)  
		{   
		super.paintComponent(g);  
		g.drawImage(img,0,0,null );  
		}   
	}
	class picPanel2 extends JPanel{
		ImageIcon icon;  
		Image img;  
		public picPanel2()  
		{   
		icon=new ImageIcon("333.jpg");  
		img=icon.getImage();  
		}   
		public void paintComponent(Graphics g)  
		{   
		super.paintComponent(g);  
		g.drawImage(img,0,0,null );  
		}   
	}
}
