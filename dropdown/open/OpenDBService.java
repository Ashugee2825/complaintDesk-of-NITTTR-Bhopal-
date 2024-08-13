package dropdown.open;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class OpenDBService {
	Connection con;
	
	
	public OpenDBService()
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
		String query="select count(*) from dd_open";
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
	public int getTotalPages(Open open,int limit)
	{
		String query=getDynamicQuery2(open);
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
	
	
	public int getOpenId(Open open)
	{
		int id=0;
		String query="select id from dd_open";
String whereClause = " where "+ "code=? and value=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, open.getCode());
pstmt.setString(2, open.getValue());
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
	public void createOpen(Open open)
	{
		
String query="INSERT INTO dd_open(code,value) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, open.getCode());
pstmt.setString(2, open.getValue());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getOpenId(open);
		open.setId(id);
	}
	public void updateOpen(Open open)
	{
		
String query="update dd_open set "+"code=?,value=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, open.getCode());
pstmt.setString(2, open.getValue());
pstmt.setInt(3, open.getId());
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
	
	public Open getOpen(int id)
	{
		Open open =new Open();
		String query="select * from dd_open where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
open.setId(rs.getInt("id")==0?0:rs.getInt("id"));
open.setCode(rs.getString("code")==null?"":rs.getString("code"));
open.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return open;
	}
	
	
	public ArrayList<Open> getOpenList()
	{
		ArrayList<Open> openList =new ArrayList<Open>();
		String query="select * from dd_open";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Open open =new Open();
open.setId(rs.getInt("id")==0?0:rs.getInt("id"));
open.setCode(rs.getString("code")==null?"":rs.getString("code"));
open.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	openList.add(open);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return openList;
	}
	
	public ArrayList<Open> getOpenList(int pageNo,int limit)
	{
		ArrayList<Open> openList =new ArrayList<Open>();
String query="select * from dd_open limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Open open =new Open();
open.setId(rs.getInt("id")==0?0:rs.getInt("id"));
open.setCode(rs.getString("code")==null?"":rs.getString("code"));
open.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	openList.add(open);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return openList;
	}
	
	public void deleteOpen(int id) {
		
			String query="delete from dd_open where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(Open open)
{
String query="select * from dd_open ";
String whereClause="";
whereClause+=(null==open.getCode()||open.getCode().equals(""))?"":" code='"+open.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(Open open)
{
String query="select count(*) from dd_open ";
String whereClause="";
whereClause+=(null==open.getCode()||open.getCode().equals(""))?"":" code='"+open.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<Open> getOpenList(Open open)
{
ArrayList<Open> openList =new ArrayList<Open>();
String query=getDynamicQuery(open);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Open open2 =new Open();
open2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
open2.setCode(rs.getString("code")==null?"":rs.getString("code"));
open2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	openList.add(open2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return openList;
}
	
public ArrayList<Open> getOpenList(Open open,int pageNo,int limit)
{
ArrayList<Open> openList =new ArrayList<Open>();
String query=getDynamicQuery(open);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Open open2 =new Open();
open2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
open2.setCode(rs.getString("code")==null?"":rs.getString("code"));
open2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	openList.add(open2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return openList;
}
	
	
	public static void main(String[] args) {
		
		OpenDBService openDBService =new OpenDBService();
		
		
		
		 //Test-1 : Create Open
		  
		  Open open = new Open(); open.setDefaultValues();
		  openDBService.createOpen(open);
		  
		 ArrayList<Open> openList = openDBService.getOpenList();
		OpenService openService =new OpenService();
		openService.displayList(openList);
		
	}
}
