package dropdown.cancelled;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
 
public class CancelledDBService {
	Connection con;
	
	
	public CancelledDBService()
	{
		DBConnectionDTO conDTO = new DBConnectionDTO();
		con=conDTO.getConnection();
	}
	
	public void closeConnection()
	{
		try {
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
public int getTotalPages(int limit)
	{
		String query="select count(*) from dd_cancelled";
	    int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	//pagination
	public int getTotalPages(Cancelled cancelled,int limit)
	{
		String query=getDynamicQuery2(cancelled);
		int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	
	public int getCancelledId(Cancelled cancelled)
	{
		int id=0;
		String query="select id from dd_cancelled";
        String whereClause = " where "+ "code=? and value=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, cancelled.getCode());
pstmt.setString(2, cancelled.getValue());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()) {
	       	id = rs.getInt("id");
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return id;
	}
	public void createCancelled(Cancelled cancelled)
	{
		
String query="INSERT INTO dd_cancelled(code,value) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, cancelled.getCode());
pstmt.setString(2, cancelled.getValue());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getCancelledId(cancelled);
		cancelled.setId(id);
	}
	public void updateCancelled(Cancelled cancelled)
	{
		
String query="update dd_cancelled set "+"code=?,value=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, cancelled.getCode());
pstmt.setString(2, cancelled.getValue());
pstmt.setInt(3, cancelled.getId());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	    	System.out.println(e);
		}
		
	}
	public String getValue(String code,String table) {
		
		String value="";
		String query="select value from "+table+" where code='"+code+"'";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	    	value=rs.getString("value");
	    }
		}
		catch (Exception e) {
			System.out.println(e);
		}
	    return value;
	}
	
	public Cancelled getCancelled(int id)
	{
		Cancelled cancelled =new Cancelled();
		String query="select * from dd_cancelled where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
cancelled.setId(rs.getInt("id")==0?0:rs.getInt("id"));
cancelled.setCode(rs.getString("code")==null?"":rs.getString("code"));
cancelled.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return cancelled;
	}
	
	
	public ArrayList<Cancelled> getCancelledList()
	{
		ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
		String query="select * from dd_cancelled";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Cancelled cancelled =new Cancelled();
cancelled.setId(rs.getInt("id")==0?0:rs.getInt("id"));
cancelled.setCode(rs.getString("code")==null?"":rs.getString("code"));
cancelled.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	cancelledList.add(cancelled);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return cancelledList;
	}
	
	public ArrayList<Cancelled> getCancelledList(int pageNo,int limit)
	{
		ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
String query="select * from dd_cancelled limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Cancelled cancelled =new Cancelled();
cancelled.setId(rs.getInt("id")==0?0:rs.getInt("id"));
cancelled.setCode(rs.getString("code")==null?"":rs.getString("code"));
cancelled.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	cancelledList.add(cancelled);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return cancelledList;
	}
	
	public void deleteCancelled(int id) {
		
			String query="delete from dd_cancelled where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(Cancelled cancelled)
{
String query="select * from dd_cancelled ";
String whereClause="";
whereClause+=(null==cancelled.getCode()||cancelled.getCode().equals(""))?"":" code='"+cancelled.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(Cancelled cancelled)
{
String query="select count(*) from dd_cancelled ";
String whereClause="";
whereClause+=(null==cancelled.getCode()||cancelled.getCode().equals(""))?"":" code='"+cancelled.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<Cancelled> getCancelledList(Cancelled cancelled)
{
ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
String query=getDynamicQuery(cancelled);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Cancelled cancelled2 =new Cancelled();
cancelled2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
cancelled2.setCode(rs.getString("code")==null?"":rs.getString("code"));
cancelled2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	cancelledList.add(cancelled2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return cancelledList;
}
	
public ArrayList<Cancelled> getCancelledList(Cancelled cancelled,int pageNo,int limit)
{
ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
String query=getDynamicQuery(cancelled);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Cancelled cancelled2 =new Cancelled();
cancelled2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
cancelled2.setCode(rs.getString("code")==null?"":rs.getString("code"));
cancelled2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	cancelledList.add(cancelled2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return cancelledList;
}
	
	
	public static void main(String[] args) {
		
		CancelledDBService cancelledDBService =new CancelledDBService();
		
		
		
		 //Test-1 : Create Cancelled
		  
		  Cancelled cancelled = new Cancelled(); cancelled.setDefaultValues();
		  cancelledDBService.createCancelled(cancelled);
		  
		 ArrayList<Cancelled> cancelledList = cancelledDBService.getCancelledList();
		CancelledService cancelledService =new CancelledService();
		cancelledService.displayList(cancelledList);
		
	}
}
