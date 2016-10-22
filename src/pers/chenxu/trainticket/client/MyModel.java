package pers.chenxu.trainticket.client;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel{
	public MyModel(){}
	//重写构造方法
	public MyModel(Object[][] data,Object[] columnNames){
		super(data,columnNames);
	}
	public Class getColumnClass(int column){
		return getValueAt(0,column).getClass();
	}
	public boolean isCellEditable(int row,int column){
		Class columnClass=getColumnClass(column);
		return columnClass!=String.class;
	}
}

