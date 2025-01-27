package xupt.se.ttms.dao;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import xupt.se.ttms.idao.iFinanceDAO;
import xupt.se.ttms.model.Finance;
import xupt.se.util.DBUtil;

public class FinanceDAO implements iFinanceDAO
{
    @SuppressWarnings("finally")
    @Override
    public int insert(Finance fin)
    {
        int result=0;
        try
        {
            String sql="insert into sale(sale_ID, emp_id, sale_time,sale_payment,sale_change,sale_type,sale_status,play_name )"
                    + "values ("+fin.getsale_id()+","+fin.getemp_id()+","+fin.getsale_time()+","+fin.getsale_payment()+","+fin.getsale_change()+""
                    		+ ","+fin.getsale_type()+","+fin.getsale_status()+","+fin.getplay_name()+")";
            DBUtil db=new DBUtil();
            db.openConnection();
            ResultSet rst=db.getInsertObjectIDs(sql);
            if(rst != null && rst.first())
            {
                fin.setsale_id(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            result=1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }
    @Override
    @SuppressWarnings("finally")
    public int update(Finance fin)
    {
        int result=0;
        String sql="";
        try
        {
        	if(fin.getsale_id()==1)
        	{
        		sql="update sale set  sale_status = '0' where sale_id = " + fin.getsale_id();
        	}
        	else
        	{
        		sql="update sale set  sale_status = '1' where sale_id = " + fin.getsale_id();
        	}
            
            DBUtil db=new DBUtil();
            db.openConnection();
            result=db.execCommand(sql);
            db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }


//    public int delete(int ID)
//    {
//        int result=0;
//        try
//        {
//            String sql="delete from sale where Sale_ID = " + ID;
//            DBUtil db=new DBUtil();
//            db.openConnection();
//            result=db.execCommand(sql);
//            db.close();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @SuppressWarnings("finally")
    public String selectsaleid(int emp_id)
    {
        DBUtil db=null;
        String result="";
        try
        {
            String sql="select sale_ID,emp_id from sale  where emp_id= " + emp_id;
            db=new DBUtil();
            if(!db.openConnection())
            {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst=db.execQuery(sql);
            if(rst != null)
            {
                while(rst.next())
                {
                    result=rst.getString("sale_id");
                }
            }
            db.close(rst);
            db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public List<Finance> select(int emp_id)
    {
        DBUtil db=null;
        List<Finance> finList=null;
        finList=new LinkedList<Finance>();
        String sql="";
        try
        {
            //Sale_id.trim();
        	if(emp_id>0)
        	{
        		sql="select * from sale where emp_id = " + emp_id;
        	}
        	else
        	{
        		sql="select * from sale where emp_id >0 ";
        	}
            db=new DBUtil();
            if(!db.openConnection())
            {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst=db.execQuery(sql);
            if(rst != null)
            {
                while(rst.next())
                {
                    Finance fin=new Finance();
                    fin.setsale_id(rst.getInt("sale_id"));
                    fin.setemp_id(rst.getInt("emp_id"));
                    fin.setsale_time(rst.getString("sale_time"));
                    fin.setsale_payment(rst.getString("sale_payment"));
                    fin.setsale_change(rst.getString("sale_change"));
                    fin.setsale_type(rst.getInt("sale_type"));
                    fin.setsale_status(rst.getInt("sale_status"));
                    fin.setplay_name(rst.getString("play_name"));
                    finList.add(fin);
                }
            }
            db.close(rst);
            db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return finList;
        }
    }

	//@Override
    public int amount(String name)
    {
        DBUtil db=new DBUtil();
        db.openConnection();
        int amount=0;
       
        String sql="select sale_id from sale where sale_type = 1 and sale_status = 1 and play_name = '"+name+"'";
        try {
        	ResultSet resultSet=db.execQuery(sql);
        	while(resultSet.next())
        	{
        	    amount++;	
        	}
        }catch(Exception e) {
        	throw new RuntimeException("查找销售编号失败",e);
        }
    	return amount;
    }
    public int refund(String name)
    {
        DBUtil db=new DBUtil();
        db.openConnection();
        int refund=0;
        String sql="select sale_id from sale where sale_type = -1 and play_name = '"+name+"'";
        try {
        	ResultSet resultSet=db.execQuery(sql);
        	while(resultSet.next())
        	{
        	    refund++;	
        	}
        }catch(Exception e) {
        	throw new RuntimeException("查找销售编号失败",e);
        }
    	return refund;
    }
    public List<Finance> datesale(String saledate)
    {
    	int n;
    	String saledates="";
        String[] date=new String[365];
        List<Finance> finList=null;
        finList=new LinkedList<Finance>();
    	String sale_payment="";
    	DBUtil db=new DBUtil();
    	db.openConnection();
    	String sql="select sale_time from sale where sale_time like '%"+saledate+"%'";//找出所有的日期
    	try {
    		ResultSet resultSet=db.execQuery(sql);
    		while(resultSet.next())
    		{
    			int flag=0;
    	    	int flag1=0;
    	    	float sale=0;
                saledates=resultSet.getString("sale_time");
                if(saledate.equals("")||saledate.length()==10)  saledates=saledates.substring(0,10);
                else if(saledate.length()==4) saledates=saledates.substring(0,4);
                else if(saledate.length()==7) saledates=saledates.substring(0,7);
                for(n=0;;)
                {
                	if(date[n]==null) break;
                	else n++;
                }
              //  System.out.println("changdu:"+n);
                if(n==0) date[0]=saledates;
                for(int i=0;i<n;i++)
                {
                	//System.out.println("date:"+date[i]+"   saledats:"+saledates+"");
                	if(date[i].equals(saledates))
                	{
                		flag=1;
                		break;
                	}
                	else
                	{
                		for(int j=0;j<date.length;j++)
                		{
                			date[j]=saledates;
                			flag1=1;
                			break;
                		}
                		
                	}
                	if(flag1==1) break;
                }
               
              //  System.out.println(saledates);//!!!!!!!!!!!!!!!!!!!!!!
                if(flag==0)
                {
                    String sql1="select sale_payment from sale where sale_time like '%"+saledates+"%'";
                    try {
                	    ResultSet resultSet1=db.execQuery(sql1);
                	    while(resultSet1.next())
                	    {
                		    sale_payment=resultSet1.getString("sale_payment");
                		    sale+=Float.parseFloat(sale_payment);
                	    }
                	    Finance fin=new Finance();
                	    fin.setsale_time(saledates);
                	    fin.setsale_payment(String.valueOf(sale));
                	    finList.add(fin);
                    }catch(Exception e){
            		    throw new RuntimeException("查找销售记录失败",e);
            	    }
                }
    		}
    	}catch(Exception e){
    		throw new RuntimeException("查找销售日期失败",e);
    	}
    	return finList;
    }
    /*int PlayId=0;
    int SchedId=0;
	String sql="select play_id from play where ticket_status = 1 and play_name = '"+name+"'";
	System.out.println("sql:"+sql);
	try {
    	ResultSet resultSet=db.execQuery(sql);
    	while(resultSet.next())
    	{
    		PlayId=resultSet.getInt("play_id");
    		//System.out.println("StudioId:"+StudioId);
    	}
    }catch(Exception e) {
    	throw new RuntimeException("查找剧目编号失败",e);
    }
	String sql2="select sched_id from schedule where play_id = "+SchedId;
	try {
    	ResultSet resultSet1=db.execQuery(sql2);
    	while(resultSet1.next())
    	{
    		SchedId=resultSet1.getInt("sched_id");
    		String sql3="select sale_id from sale where sale_type = 1 and sale_status = 1 and play_name="+SchedId;
    		try {
            	ResultSet resultSet2=db.execQuery(sql3);
            	while(resultSet2.next())
            	{
            	    amount++;	
            	}
            }catch(Exception e) {
            	throw new RuntimeException("查找剧目编号失败",e);
            }
    	}
    }catch(Exception e) {
    	throw new RuntimeException("查找剧目编号失败",e);
    }*/
}
