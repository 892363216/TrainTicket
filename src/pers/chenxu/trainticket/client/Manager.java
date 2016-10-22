package pers.chenxu.trainticket.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pers.chenxu.trainticket.server.Message;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import java.awt.Color;

public class Manager extends JFrame {
	
	public ObjectOutputStream toServer;
	public ObjectInputStream fromServer;
	public Socket socket=null;
	
	private JPanel contentPane;
	private JTextField maid_text;
	private JTextField id_text;
	private JTextField name_text;
	private JTextField uid_text;
	private JTextField fa_date;
	private JPasswordField pass_field;
	private JPasswordField passwordField;
	private JPanel panel;
	private JPanel panel_1;
	private JTable table1;
	private String userInfo[][]=null;
	private String checiInfo[][]=null;
	private DefaultTableModel dtmTrain;
	
	private JTextField checi;
	private JTextField riqi;
	private JTextField yifazhan;
	private JTextField textField;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager frame = new Manager();
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
	public Manager() throws IOException {
		
		try {
			socket=new Socket("localhost",8000);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//连接服务器
		toServer=new ObjectOutputStream(socket.getOutputStream());
		fromServer=new ObjectInputStream(socket.getInputStream());
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 402);
		contentPane = new picPanel2();
		//contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cardLayout=new CardLayout();
		contentPane.setLayout(cardLayout);
		
		panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel, "登录页");
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u5E10\u53F7\uFF1A");
		label.setFont(new Font("幼圆", Font.BOLD, 15));
		label.setBounds(254, 157, 54, 15);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801\uFF1A");
		label_1.setFont(new Font("幼圆", Font.BOLD, 15));
		label_1.setBounds(254, 202, 54, 15);
		panel.add(label_1);
		
		maid_text = new JTextField();
		maid_text.setBounds(318, 154, 133, 21);
		panel.add(maid_text);
		maid_text.setColumns(10);
		
		//登录页的注册按钮
		JButton sign = new JButton("注册");
		sign.setFont(new Font("幼圆", Font.PLAIN, 14));
		sign.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(contentPane, "注册页");
			}
		});
		sign.setBounds(254, 265, 93, 34);
		panel.add(sign);
		
		JButton getin = new JButton("登录");
		getin.setFont(new Font("幼圆", Font.PLAIN, 14));
		getin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String info=maid_text.getText()+" "+pass_field.getText();
				Message mess=new Message(20,info);
				if(transMsg(mess)){
					JOptionPane.showMessageDialog(null, "登录成功！","System Information",JOptionPane.INFORMATION_MESSAGE);
				cardLayout.show(contentPane, "主页面");
				}else JOptionPane.showMessageDialog(null, "用户名或密码错误！","System Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		getin.setBounds(421, 265, 93, 34);
		panel.add(getin);
		
		pass_field = new JPasswordField();
		pass_field.setBounds(318, 199, 133, 21);
		panel.add(pass_field);
		
		JLabel label_15 = new JLabel("\u7BA1\u7406\u5458\u767B\u5F55");
		label_15.setFont(new Font("隶书", Font.BOLD, 22));
		label_15.setBounds(308, 83, 143, 39);
		panel.add(label_15);
		
		JLabel label_16 = new JLabel("\u706B\u8F66\u552E\u7968\u7CFB\u7EDF");
		label_16.setFont(new Font("楷体", Font.BOLD, 26));
		label_16.setBounds(278, 39, 197, 34);
		panel.add(label_16);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		contentPane.add(panel_1, "注册页");
		panel_1.setLayout(null);
		
		JLabel label_2 = new JLabel("\u59D3\u540D\uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(102, 155, 54, 15);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\u5E10\u53F7\uFF1A");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_3.setBounds(102, 100, 87, 15);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("\u5BC6\u7801\uFF1A");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_4.setBounds(102, 205, 54, 15);
		panel_1.add(label_4);
		
		id_text = new JTextField();
		id_text.setBounds(167, 97, 122, 21);
		panel_1.add(id_text);
		id_text.setColumns(10);
		
		name_text = new JTextField();
		name_text.setBounds(166, 152, 123, 21);
		panel_1.add(name_text);
		name_text.setColumns(10);
		
		JLabel label_5 = new JLabel("\u8BF7\u586B\u5199\u4EE5\u4E0B\u6CE8\u518C\u4FE1\u606F\uFF1A");
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_5.setBounds(47, 39, 163, 21);
		panel_1.add(label_5);
	//注册页的点击注册按钮	
		JButton signed = new JButton("\u6CE8\u518C");
		signed.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		signed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String info=id_text.getText()+" "+name_text.getText()+" "+passwordField.getEchoChar();
				Message mess=new Message(21,info);
				if(transMsg(mess)){
					JOptionPane.showMessageDialog(null, "注册成功！","System Information",JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(contentPane, "主页面");
				}
				else JOptionPane.showMessageDialog(null, "用户名不合法！","System Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		signed.setBounds(321, 268, 93, 23);
		panel_1.add(signed);
		
		//返回登陆
		JButton reDenglu = new JButton("返回登录");
		reDenglu.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		reDenglu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardLayout.show(contentPane, "登录页");
			}
		});
		reDenglu.setBounds(102, 268, 93, 23);
		panel_1.add(reDenglu);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(166, 202, 123, 21);
		panel_1.add(passwordField);
		
		JPanel main_Pane = new JPanel();
		contentPane.add(main_Pane, "主页面");
		main_Pane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(10, 50, 658, 304);
		main_Pane.add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("用户管理", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel label_6 = new JLabel("\u7528\u6237\u540D\uFF1A");
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_6.setBounds(56, 51, 68, 15);
		panel_2.add(label_6);
		
		uid_text = new JTextField();
		uid_text.setBounds(134, 48, 96, 21);
		panel_2.add(uid_text);
		uid_text.setColumns(10);
		
		String[] title={"帐号","用户名","余额","手机号"};
		//查询按钮
		JButton button = new JButton("\u67E5\u8BE2");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message msg=new Message(23,uid_text.getText());
				transMsg(msg);
				DefaultTableModel dtm=new DefaultTableModel(userInfo,title);
				table1.setModel(dtm);
			}
		});
		button.setBounds(292, 47, 93, 23);
		panel_2.add(button);
		
		//显示所有
		JButton button_1 = new JButton("显示所有");
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message msg=new Message(22," ");
				transMsg(msg);	
				DefaultTableModel dtm=new DefaultTableModel(userInfo,title);
				table1.setModel(dtm);
			}
		});
		button_1.setBounds(412, 47, 93, 23);
		panel_2.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(44, 95, 461, 149);
		panel_2.add(scrollPane);
		
		table1 = new JTable();
		table1.setBackground(Color.LIGHT_GRAY);
		table1.setRowHeight(20);
		table1.setFont(new Font("幼圆", Font.PLAIN, 13));
		scrollPane.setViewportView(table1);
	//删除用户
		JButton button_2 = new JButton("\u5220\u9664");
		button_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int target=table1.getSelectedRow();
				String info=userInfo[target][0];
				Message msg=new Message(24,info);
				transMsg(msg);
				//刷新以下用户的table
				Message msg2=new Message(22," ");
				transMsg(msg2);	
				DefaultTableModel dtm=new DefaultTableModel(userInfo,title);
				table1.setModel(dtm);
			}
		});
		button_2.setBounds(412, 266, 93, 23);
		panel_2.add(button_2);
		
		JPanel fa_riqi = new JPanel();
		tabbedPane.addTab("票务管理", null, fa_riqi, null);
		fa_riqi.setLayout(null);
		
		JLabel label_7 = new JLabel("\u5DF2\u53D1\u8F66\u7968\u66F4\u65B0");
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_7.setBounds(36, 20, 110, 24);
		fa_riqi.add(label_7);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(33, 54, 248, 2);
		fa_riqi.add(separator);
		
		JLabel label_12 = new JLabel("\u8F66\u7968\u53D1\u653E");
		label_12.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_12.setBounds(36, 191, 65, 15);
		fa_riqi.add(label_12);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(36, 216, 248, 2);
		fa_riqi.add(separator_1);
		
		JLabel label_13 = new JLabel("\u65E5\u671F\uFF1A");
		label_13.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_13.setBounds(47, 228, 54, 15);
		fa_riqi.add(label_13);
		
		fa_date = new JTextField();
		fa_date.setBounds(100, 225, 66, 21);
		fa_riqi.add(fa_date);
		fa_date.setColumns(10);
	
		//发放车票	
		JButton button_4 = new JButton("\u53D1\u653E\u8F66\u7968");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String day=fa_date.getText();
				Message msg=new Message(27,day);
				transMsg(msg);
				JOptionPane.showMessageDialog(null, "发放成功！","System Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		button_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button_4.setBounds(279, 228, 93, 23);
		fa_riqi.add(button_4);
		
		JLabel label_8 = new JLabel("\u8F66\u6B21\uFF1A");
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_8.setBounds(36, 66, 54, 15);
		fa_riqi.add(label_8);
		
		checi = new JTextField();
		checi.setBounds(88, 63, 66, 21);
		fa_riqi.add(checi);
		checi.setColumns(10);
		
		JLabel label_9 = new JLabel("\u65E5\u671F\uFF1A");
		label_9.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_9.setBounds(211, 66, 54, 15);
		fa_riqi.add(label_9);
		
		riqi = new JTextField();
		riqi.setBounds(260, 63, 66, 21);
		fa_riqi.add(riqi);
		riqi.setColumns(10);
		
		JLabel label_10 = new JLabel("\uFF08\u683C\u5F0F\u4E3A \u6708/\u65E5\uFF09");
		label_10.setBounds(348, 66, 126, 15);
		fa_riqi.add(label_10);
		
		JLabel label_11 = new JLabel("\u5DF2\u53D1\u7AD9\uFF1A");
		label_11.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_11.setBounds(21, 102, 80, 15);
		fa_riqi.add(label_11);
		
		yifazhan = new JTextField();
		yifazhan.setBounds(88, 100, 66, 21);
		fa_riqi.add(yifazhan);
		yifazhan.setColumns(10);
		
		//票务更新按钮
		JButton button_3 = new JButton("票务更新");
		button_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String info=checi.getText()+" "+riqi.getText()+" "+yifazhan.getText();
				Message msg=new Message(25,info);
				transMsg(msg);
				JOptionPane.showMessageDialog(null, "票务更新成功！","System Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		button_3.setBounds(279, 154, 93, 23);
		fa_riqi.add(button_3);
		tabbedPane.setFont(new Font("微软雅黑",Font.PLAIN,18));
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("车次管理", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel label_14 = new JLabel("\u8F66\u6B21\u7F16\u53F7\uFF1A");
		label_14.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_14.setBounds(89, 55, 77, 15);
		panel_3.add(label_14);
		
		textField = new JTextField();
		textField.setBounds(165, 52, 66, 21);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JButton search = new JButton("查询");
		search.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Message msg=new Message(3,textField.getText());
				if(transMsg(msg))
				{	
					String[] titleCode={"站次","站名","到站时间","发车时间","天差"};
					dtmTrain=new MyModel(checiInfo,titleCode);
					table.setModel(dtmTrain);
				}
			}
		});
		search.setBounds(273, 51, 93, 23);
		panel_3.add(search);
		
		JButton searchAll = new JButton("查询所有");
		searchAll.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		searchAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Message msg=new Message(26,"");
				if(transMsg(msg))
				{	
					String[] titleCode={"车次编号","起始站","终点站","始发时间","终到时间","距离"};
					dtmTrain=new DefaultTableModel(checiInfo,titleCode);
					table.setModel(dtmTrain);
				}
			}
		});
		searchAll.setBounds(409, 51, 93, 23);
		panel_3.add(searchAll);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(54, 87, 484, 161);
		panel_3.add(scrollPane_1);
		
		table = new JTable();
		table.setRowHeight(20);
		table.setFont(new Font("幼圆", Font.PLAIN, 13));
		scrollPane_1.setViewportView(table);
		
		JButton button_7 = new JButton("\u5220\u9664\u9009\u4E2D");
		button_7.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button_7.setBounds(409, 258, 93, 23);
		panel_3.add(button_7);
	}
	
	public boolean transMsg(Message msg){
		Object obj = null;
		try {
			toServer.writeObject(msg);
			toServer.flush();
			obj=fromServer.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj instanceof Message){
			Message mes=(Message)obj;
			if(mes.getInfo()!=null){
				String[] cache=mes.getInfo().split(" ");
				int type=mes.getType();
				switch(type){
				case 3:{//按条件查询车次
					int sum=cache.length/5;
					checiInfo=new String[sum][5];   //把结果分解到二维数组中
					int cursor=0;
					for(int i=0;i<sum;i++)
						for(int j=0;j<5;j++){
							if(cursor<cache.length){
							checiInfo[i][j]=cache[cursor];
							cursor++;}
						}
					break;
				}
				case 20:{
					if(cache[0].equals("true"))
						return true;
					else return false;
				}
				case 21:{
					if(cache[0].equals("true"))
						return true;
					else return false;	
				}
				case 22:{
					int sum=cache.length/4;
					userInfo=new String[sum][4];   //把结果分解到二维数组中
					int cursor=0;
					for(int i=0;i<sum;i++)
						for(int j=0;j<4;j++){
							if(cursor<cache.length){
							userInfo[i][j]=cache[cursor];
							cursor++;}
						}
					break;
				}
				case 23:{//返回所有用户
					userInfo=new String[1][6];
					for(int i=0;i<6;i++){
						userInfo[0][i]=cache[i];
						System.out.print(userInfo[0][i]+" ");
					}
					break;
				}
				case 24:{//具体查询用户
					break;
				}
				case 25:{//票务系统更新，删除已经发车的车票，并且将订单设为无效
					break;
				}
				case 26:{
					int sum=cache.length/6;
					checiInfo=new String[sum][6]; 
					int cursor=0;
					for(int i=0;i<sum;i++){
						for(int j=0;j<6;j++){
							if(cursor<cache.length){
								checiInfo[i][j]=cache[cursor];
							cursor++;}
						}
					}
					break;
				}
				}
				
			return true;
			}		
	}return false;
	}
	
	class picPanel2 extends JPanel{
		ImageIcon icon;  
		Image img;  
		public picPanel2()  
		{   
		icon=new ImageIcon("1.jpeg");  
		img=icon.getImage();  
		}   
		public void paintComponent(Graphics g)  
		{   
		super.paintComponent(g);  
		g.drawImage(img,0,0,null );  
		}   
	}
}
