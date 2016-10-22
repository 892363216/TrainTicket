package pers.chenxu.trainticket.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DatabaseSolve {
	
	private Connection connection;
	private PreparedStatement preStatement;
	
	public DatabaseSolve(){
		try {
			initDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initDatabase() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Driver Loaded");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket","jackie","892363216cx");
		System.out.println("Database Connected");
		
	}
	public void closeConnection() throws SQLException{
		connection.close();
	}
	public void AddUser(String user_id,String[] info) throws SQLException{
		String query="Insert into user_info values(?,?,?,?,?)";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, user_id);
		preStatement.setString(2, info[0]);
		preStatement.setString(3, info[1]);
		preStatement.setString(4, info[2]);
		preStatement.setString(5, info[5]);
		preStatement.executeUpdate();	
		addPass(user_id,info[3],info[4]);//��ӳ˿�
	}
	public boolean SelectUser(String username) throws SQLException{
		String query="select count(*) from user_info where user_name=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, username);
		ResultSet rst=preStatement.executeQuery();
		int num=0;
		while(rst.next())
			num=rst.getInt(1);
		if(num>0)
			return true;
		else return false;	
	}
//�û��Ƿ�ƥ��
	public String isUserMatched(String username,String password) throws SQLException{
		String result=null;
		String query="select * from user_info where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, username);
		ResultSet rst=preStatement.executeQuery();
		if(rst.next()){
			if(password.equals(rst.getString(3))){
				result=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(4)+" "+rst.getString(5);
			}
		}
		return result;
	}
	
//�����û���Ϣ
	public String getUserInfo(String username) throws SQLException{
		String result=null;
		String query="select * from user_info where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, username);
		ResultSet rst=preStatement.executeQuery();
		if(rst.next()){
				result=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(4)+" "+rst.getString(5);
		}
		return result;
	}
	//�����û�����
	public int countUser() throws SQLException{
		String st="select count(*) from user_info";
		Statement ste=connection.createStatement();
		ResultSet rs=ste.executeQuery(st);
		rs.next();
		return rs.getInt(1);
	}
	//���ض�������
	public int countOrder() throws SQLException{
		String st="select count(*) from order_info";
		Statement ste=connection.createStatement();
		ResultSet rs=ste.executeQuery(st);
		rs.next();
		return rs.getInt(1);
	}
	
	public String queryStation(String stationid) throws SQLException{
		String query="select * from stop_time where station_train_code=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,stationid);
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(6)+" "+rst.getString(7)
					+" "+rst.getString(5)+" ";
		}
		return result;
	}
/*
	public String searchTrain(String start,String end) throws SQLException{
		String query="select a.station_train_code,a.start_time,b.arrive_time,b.day_difference-a.day_difference,a.1st_seat,a.2nd_seat "
				   + "from (select * from stop_time where station_name=?) a,(select * from stop_time where station_name=?) b  "
				   + "where a.station_train_code=b.station_train_code and a.ID<b.ID";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,start);
		preStatement.setString(2,end);
		
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)
					+" "+rst.getString(5)+" "+rst.getString(6)+" ";
		}
		return result;
	}
	*/
	public String searchTrain(String start,String end,String date) throws SQLException{
		String query="select a.station_train_code,a.start_time,b.arrive_time,b.day_difference-a.day_difference "
				   + "from (select * from stop_time where station_name=?) a,(select * from stop_time where station_name=?) b  "
				   + "where a.station_train_code=b.station_train_code and a.ID<b.ID";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,start);
		preStatement.setString(2,end);
		
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			String query2="select count(*) from ticket_info "
						+ "where departure_date=? and station_train_code=? and isValid='��'"
						+ "group by seat_type";
			preStatement=connection.prepareStatement(query2);
			preStatement.setString(2,rst.getString(1));
			preStatement.setString(1,date);
			ResultSet rst2=preStatement.executeQuery();
			rst2.next();
			result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)
					+" "+rst2.getString(1)+" ";
			rst2.next();
			result+=rst2.getString(1)+" ";
		}
		return result;
	}
	//Ʊ�۲�ѯ
	public String searchPrice(String start,String end)throws SQLException{
		String query="select a.station_train_code,a.start_time,b.arrive_time,b.day_difference-a.day_difference,0.5*(b.distance-a.distance),0.22*(b.distance-a.distance) "
				+ "from (select * from stop_time where station_name=?) a,(select * from stop_time where station_name=?) b  "
				+ "where a.station_train_code=b.station_train_code and a.ID<b.ID";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,start);
		preStatement.setString(2,end);
		
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)
					+" "+rst.getString(5)+" "+rst.getString(6)+" ";
		}
		return result;
	}
	//���֤�Ų�ѯ
	public String searchIDNum(String uid) throws SQLException{
		String query="select passenger_id,real_name from psg_info where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,uid);
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" ";
		}
		System.out.println(result);
		return result;
	}
	//���Ӷ���
	public void incOrder(String orderID,String uid,String date,int num,String isValid,double price) throws SQLException{
		String query="insert into order_info values (?,?,?,?,?,?)";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,orderID);
		preStatement.setString(2,uid);
		preStatement.setString(3,date);
		preStatement.setInt(4,num);
		preStatement.setString(5,isValid);
		preStatement.setDouble(6,price);		
		preStatement.executeUpdate();
		subCount(uid,price);
	}
	//��Ϣ��˳����λ���͡���ʱ�������š����α�š����֤�š�Ʊ�ۡ������ء�Ŀ�ĵء��û��ʺ�
	public String buyTicket(String []info) throws SQLException{
		//�ҵ�һ�ſ�Ʊ����������
		String search="select ticket_id ,car_no,seat_no from ticket_info "
				+ "where station_train_code=? and seat_type=? and departure_date=? and isValid='��'";
		preStatement=connection.prepareStatement(search);
		preStatement.setString(1,info[3]);
		if(info[0].equals("1st_seat"))
		preStatement.setString(2,"һ����");
		else preStatement.setString(2,"������");
		preStatement.setString(3,info[1]);
		ResultSet rst=preStatement.executeQuery();
		rst.next();
		String ticId=rst.getString(1);
		String result=rst.getString(2)+" "+rst.getString(3);
		
		//�����ſ�Ʊ���и���
		String query="update ticket_info "
				+ "set order_id=?,id_number=?,price=?,start_station_name=? ,end_station_name=?,isValid='��'"
				+ "where ticket_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,info[2]);	
		preStatement.setString(2,info[4]);
		preStatement.setString(3,info[5]);
		preStatement.setString(4,info[6]);
		preStatement.setString(5,info[7]);
		preStatement.setString(6,ticId);
		preStatement.executeUpdate();
		
		
		return result;
	}
	
	
	//��ѯĳ�˿���ĳ�����ĳ���εĳ�Ʊ�Ƿ����
	public boolean isTicExist(String idno,String day,String trainNo) throws SQLException{
		String query="select count(*) from ticket_info where station_train_code=? and id_number=? and departure_date=? and isValid='��'";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,trainNo);
		preStatement.setString(2,idno);
		preStatement.setString(3,day);
		
		ResultSet rst=preStatement.executeQuery();
		rst.next();
		if(rst.getInt(1)>0){
			return true;
		}
		return false;
	}
	//��ѯʵʱ��Ʊ
	public int remainNum(String date,String trainNo,String type) throws SQLException{//��վ�����α�ţ���������
		String query;
		if(type.equals("1st_seat")){
		query="select count(*) from ticket_info where station_train_code=? and departure_date=? and seat_type='һ����'";
		}else
			query="select count(*) from ticket_info where station_train_code=? and departure_date=? and seat_type='������'";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,trainNo);
		preStatement.setString(2,date);
		ResultSet rs=preStatement.executeQuery();
		int result=0;
		rs.next();
		result=rs.getInt(1);
		return result;
	}
	

	//done
	public String OrderInfo(String uid) throws SQLException{
		String query="select * from order_info where user_id=? and is_Valid='��'";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,uid);
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(3)+" "+rst.getString(4)+" "+rst.getString(6)
					+" ";
		}
		return result;
	}
	public String searchOrder(String orderid) throws SQLException{
		String query="select * from ticket_info where order_id=? and isValid='��'";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,orderid);
		ResultSet rst=preStatement.executeQuery();
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)
					+" "+rst.getString(5)+" "+rst.getString(7)+" "+rst.getString(8)+" "
					+rst.getString(9)+" "+rst.getString(10)+" "+rst.getString(11)+" ";
		}
		return result;
	}
	public void subCount(String uid,double num) throws SQLException{
		String query="update user_info set remainder=remainder-? where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(2,uid);
		preStatement.setDouble(1,num);
		preStatement.executeUpdate();
	}
	public void addCount(String uid,double num)throws SQLException{
		String query="update user_info set remainder=remainder+? where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(2,uid);
		preStatement.setDouble(1,num);
		preStatement.executeUpdate();
	}
	public void cancelOrder(String orderId) throws SQLException{
		connection.setAutoCommit(false);//�ر��Զ��ύ
		//��������Ϊ��Ч
		String query="update order_info set is_Valid='��' where order_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,orderId);
		preStatement.executeUpdate();		
		//�����ӻ�ȥ
		String querys="select user_id,total_Price from order_info where order_id=?";
		preStatement=connection.prepareStatement(querys);
		preStatement.setString(1,orderId);
		ResultSet rst2=preStatement.executeQuery();
		rst2.next();
		addCount(rst2.getString(1), rst2.getDouble(2));
		//��Ʊ��Ϊ��Ч
		String query2="update ticket_info set isValid='��' where order_id=?";
		preStatement=connection.prepareStatement(query2);
		preStatement.setString(1,orderId);
		System.out.println(preStatement);
		preStatement.executeUpdate();	
		
		connection.commit();//�ύ
		connection.setAutoCommit(true);//���Զ��ύ
	}
	
	
	public void addPass(String uid,String id,String name) throws SQLException{
		String query="insert into psg_info values(?,?,?)";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,id);
		preStatement.setString(2,name);
		preStatement.setString(3,uid);
		preStatement.executeUpdate();
	}
	
	public boolean isExistUAI(String userID,String IDN) throws SQLException{
		String s="select count(*) from psg_info where user_id=? and passenger_id=?";
		preStatement=connection.prepareStatement(s);
		preStatement.setString(1, userID);
		preStatement.setString(2, IDN);
		ResultSet rst=preStatement.executeQuery();
		rst.next();
		if(rst.getInt(1)>0){
			return true;
		}
		else return false;
	}
	//����Ա���û������Ƿ�ƥ��
	public boolean isManagerMatched(String info[]) throws SQLException{
		String result=null;
		String query="select manager_pwd from manager_info where manager_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, info[0]);
		ResultSet rst=preStatement.executeQuery();
		if(rst.next()){
			if(info[1].equals(rst.getString(1))){
				return true;
			}
		}
		return false;
	}
	
	//����һ������Ա
	public boolean incManager(String []info) throws SQLException{
		String query="select count(*) from manager_info where manager_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, info[0]);
		ResultSet rst=preStatement.executeQuery();
		if(rst.next()){
			if(rst.getInt(1)>0){
				return false;//�Ѿ����������ˣ����ܼ�
			}
		}
		String query2="insert into manager_info values(?,?,?)";
		preStatement=connection.prepareStatement(query2);
		preStatement.setString(1, info[0]);
		preStatement.setString(2, info[1]);
		preStatement.setString(3, info[2]);
		
		preStatement.executeUpdate();
		return true;
	}
	//�õ����е��û���Ϣ
	public String getAllUser() throws SQLException{
		Statement st=connection.createStatement();
		ResultSet rst=st.executeQuery("select *from user_info");
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(4)
			+" "+rst.getString(5)+" ";
		}
		return result;
	}
	//�õ��̶��û�����Ϣ
	public String getAUser(String uid) throws SQLException{
		String result="";
		String query="select * from user_info where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,uid);
		ResultSet rst=preStatement.executeQuery();
		
		if(rst.next()){
		result+=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(4)
		+" "+rst.getString(5)+" "+rst.getString(6)+" "+rst.getString(7)+" ";
		}
		return result;
	}
	//ɾ���û������֤��
	public void deleteUser(String info) throws SQLException{
		String query="delete from user_info where user_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,info);
		preStatement.executeUpdate();
	}
	//����Ʊ��ϵͳ//undone
	public void updateTic(String[] info) throws SQLException{
		connection.setAutoCommit(false);
		String query="select distinct order_id from ticket_info where station_train_code=? and departure_date=? and start_station_name=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,info[0]);
		preStatement.setString(2,info[1]);
		preStatement.setString(3,info[2]);
		ResultSet rs=preStatement.executeQuery();
		String oID="";
		if(rs.next())	
		oID=rs.getString(1);//�鵽Ҫ���µĶ�����
		System.out.println(preStatement);
		//���¶���
		String query2="update order_info set is_valid='��' where order_id=?";
		preStatement=connection.prepareStatement(query2);
		preStatement.setString(1,oID);
		preStatement.executeUpdate();		
		//ɾ����Ʊ
		String query3="delete from ticket_info where  station_train_code=? and departure_date=? and start_station_name=?";
		preStatement=connection.prepareStatement(query3);
		preStatement.setString(1,info[0]);
		preStatement.setString(2,info[1]);
		preStatement.setString(3,info[2]);
		preStatement.executeUpdate();
		connection.commit();
		connection.setAutoCommit(true);
	}
	//������תվ
	public String queryAnotherStation(String info[]) throws SQLException{
		String query="select a.station_name from"
				+ "(select distinct station_name from stop_time where station_train_code in"
				+ "(select station_train_code from stop_time where station_name=?)) a,"
				+ "(select distinct station_name from stop_time where station_train_code in"
				+ "(select station_train_code from stop_time where station_name=?)) b "
				+ "where a.station_name=b.station_name";
		String result="";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1,info[0]);
		preStatement.setString(2,info[1]);
		ResultSet rs=preStatement.executeQuery();
		while(rs.next()){
			result+=rs.getString(1)+" ";
		}
	 return	result;
	}
	
	public boolean isOrderValid(String orderId) throws SQLException{
		String query="select is_valid from order_info where order_id=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, orderId);
		ResultSet rst=preStatement.executeQuery();
		if(rst.next()){
			if(rst.getString(1).equals("��"))
				return true;
		}
		return false;
	}
	
	public String checiInfo(String checiId) throws SQLException{
		String query="select * from train_info where station_train_code=?";
		preStatement=connection.prepareStatement(query);
		preStatement.setString(1, checiId);
		ResultSet rst=preStatement.executeQuery();
		String result="";
		if(rst.next()){
			result=rst.getString(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)+" "+rst.getString(5);
		}
			return result;
	}
	//undone
	public void fabuTic(String day) throws SQLException{
		Statement st=connection.createStatement();
		ResultSet rst=st.executeQuery("select station_train_code from train_info");
		String[] s1=day.split("/");
		int count=100000;
		while(rst.next()){
			String train=rst.getString(1);
			for(int i=1;i<3;i++){
				for(int j=1;j<=10;j++){
					String query="insert into ticket_info(ticket_id,car_no,seat_no,seat_type,departure_date,station_train_code,isValid)"
							+ "values(?,?,?,?,?,?,?)";
					preStatement=connection.prepareStatement(query);
					preStatement.setString(1, s1[0]+s1[1]+count);
					preStatement.setInt(2, i);
					preStatement.setInt(3, j);
					if(i==1){
						preStatement.setString(4, "һ����");
					}else preStatement.setString(4, "������");
					preStatement.setString(5, day);
					preStatement.setString(6, train);
					preStatement.setString(7, "��");
					preStatement.executeUpdate();
					count++;
				}
			}
		}
	}
	
	public String getAllTrain() throws SQLException{
		String query="select * from train_info";
		Statement st=connection.createStatement();
		ResultSet rst=st.executeQuery(query);
		String result="";
		while(rst.next()){
			result+=rst.getString(1)+" "+rst.getString(2)+" "
					+rst.getString(3)+" "+rst.getString(4)+" "
					+rst.getString(5)+" "+rst.getString(6)+" ";
		}
		return result;
	}
	
}
