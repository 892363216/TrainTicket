package pers.chenxu.trainticket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleServer implements Runnable{
	private Socket socket;
	private ObjectInputStream inputFromClient;
	private ObjectOutputStream outputClient;
	private DatabaseSolve database;
	private boolean isConnect=true;
public SingleServer(Socket socket){
	database=new DatabaseSolve();
	this.socket=socket;
}
public void run(){
	try {
		inputFromClient=new ObjectInputStream(socket.getInputStream());
		outputClient=new ObjectOutputStream(socket.getOutputStream());
		while(isConnect){	
				Object obj = inputFromClient.readObject();
				if(obj instanceof Message){
					Message msg=(Message)obj;	
					Message result=this.solveMessage(msg);
					outputClient.writeObject(result);
					outputClient.flush();	
		}
		}
	 }catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	closeCon();
}
public void closeCon(){
	try {
		inputFromClient.close();
		outputClient.close();
		socket.close();	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public Message solveMessage(Message msg) throws SQLException{
	int type=msg.getType();
	String[] info=msg.getInfo().split(" ");
	Message message=new Message();
	switch(type){
	case 0:{//登录
		message.setType(0);
	    message.setInfo(database.isUserMatched(info[0],info[1]));
		//System.out.println(message.getInfo());
		break;
		}
	case 1:{//注册
		message.setType(1);
		if(database.SelectUser(info[0])){
			message.setInfo("false");
		}
		else {
			String userID=ExUserId();
			database.AddUser(userID, info);
			message.setInfo(userID);
		}
		break;
	}
	case 2:{//余票查询
		message.setType(2);
		message.setInfo(database.searchTrain(info[0], info[1],info[2]).trim());			
		break;
	}
	case 3:{//车次查询
		message.setType(3);
		message.setInfo(database.queryStation(msg.getInfo()).trim());
		break;
	}
	case 4:{//票价查询
		message.setType(4);
		message.setInfo(database.searchPrice(info[0],info[1]).trim());
		break;
	}
	case 5:{//用户身份证号查询
		message.setType(5);
		message.setInfo(database.searchIDNum(info[0]).trim());
		break;
	}
	case 6:{//增加订单
		message.setType(6);
		String orID=ExOrderId();
		String time=info[1]+" "+info[2];//把时间合并，因为它们原本中间有空格，split后就没了
		database.incOrder(orID, info[0],time,Integer.parseInt(info[3]),info[4],Double.parseDouble(info[5]));
	    message.setInfo(orID);//返回订单号
	    break;
	}
	case 7:{//查看是否曾经购票
		message.setType(7);
		if(database.isTicExist(info[0], info[1], info[2]))
			message.setInfo("true");
		else message.setInfo("false");
		break;
	}
	case 8:{//查询实时余票
		message.setType(8);
		int result=database.remainNum(info[0], info[1], info[2]);
		message.setInfo(result+"");
		break;
	}
	case 9:{//减票
		/*message.setType(9);
		for(int i=0;i<info.length;i++)
			System.out.print(info[i]+" ");
		if(database.subTic(info))
			message.setInfo("true");
		else message.setInfo("false");*/
		break;
	}
	case 10:{//更新票务系统
		message.setType(10);
		message.setInfo(database.buyTicket(info));
		break;
	}
	case 11:{
		message.setType(11);
		message.setInfo(database.OrderInfo(info[0]));
		break;
	}
	case 12:{
		message.setType(12);
		message.setInfo(database.searchOrder(info[0]));
		break;
	}
	case 13:{//取消订单
		message.setType(13);
		if(database.isOrderValid(info[0])){
		database.cancelOrder(info[0]);
		message.setInfo("true");}
		else message.setInfo("false");
		break;
	}
	case 14:{
		message.setType(14);
		message.setInfo(database.getUserInfo(info[0]));
		break;
	}
	case 15:{
		message.setType(15);
		if(!database.isExistUAI(info[0], info[1])){
			database.addPass(info[0], info[1], info[2]);
			message.setInfo("true");}
		else message.setInfo("false");
		break;
	}
	case 16:{
		message.setType(16);
		message.setInfo(database.queryAnotherStation(info));
		break;
	}
	//以下为管理员面板与服务器的通话
	case 20:{
		message.setType(20);
		if(database.isManagerMatched(info))
			message.setInfo("true");
		else message.setInfo("false");
		break;
	}
	case 21:{
		message.setType(21);
		if(database.incManager(info))
			message.setInfo("true");
		else message.setInfo("false");
		break;
	}
	case 22:{
		message.setType(22);
		message.setInfo(database.getAllUser().trim());
		break;
	}
	case 23:{
		message.setType(23);
		message.setInfo(database.getAUser(info[0]));
		break;
	}
	case 24:{
		message.setType(24);
		database.deleteUser(info[0]);
		message.setInfo("true");
		break;
	}
	case 25:{
		message.setType(25);
		database.updateTic(info);
		break;
	}
	case 26:{
		message.setType(26);
		message.setInfo(database.getAllTrain());
		break;
	}
	case 27:{
		message.setType(27);
		System.out.println(info.length);
		database.fabuTic(info[0]);
		break;
	}
	case 30:{
		isConnect=false;
		break;
	}
	}
	return message;
}

public String ExUserId() throws SQLException{
	int base=database.countUser()+1;
	SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
	String dat=sdf.format(new Date());
	String str=String.format("%04d", base);
	return dat+str;
}
public String ExOrderId() throws SQLException{
	int base=database.countOrder()+1;
	SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
	String dat=sdf.format(new Date());
	String str=String.format("%03d", base);
	return dat+str;
}

}
