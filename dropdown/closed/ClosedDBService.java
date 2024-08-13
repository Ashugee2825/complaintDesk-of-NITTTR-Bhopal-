package dropdown.closed;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class ClosedDBService {
	Connection con;
	
	
	public ClosedDBService()
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
		String query="select count(*) from dd_closed";
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
	public int getTotalPages(Closed closed,int limit)
	{
		String query=getDynamicQuery2(closed);
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
	
	
	public int getClosedId(Closed closed)
	{
		int id=0;
		String query="select id from dd_closed";
String whereClause = " where "+ "code=? and value=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, closed.getCode());
pstmt.setString(2, closed.getValue());
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
	public void createClosed(Closed closed)
	{
		
String query="INSERT INTO dd_closed(code,value) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, closed.getCode());
pstmt.setString(2, closed.getValue());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getClosedId(closed);
		closed.setId(id);
	}
	public void updateClosed(Closed closed)
	{
		
String query="update dd_closed set "+"code=?,value=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, closed.getCode());
pstmt.setString(2, closed.getValue());
pstmt.setInt(3, closed.getId());
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
	
	public Closed getClosed(int id)
	{
		Closed closed =new Closed();
		String query="select * from dd_closed where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
closed.setId(rs.getInt("id")==0?0:rs.getInt("id"));
closed.setCode(rs.getString("code")==null?"":rs.getString("code"));
closed.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return closed;
	}
	
	
	public ArrayList<Closed> getClosedList()
	{
		ArrayList<Closed> closedList =new ArrayList<Closed>();
		String query="select * from dd_closed";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Closed closed =new Closed();
closed.setId(rs.getInt("id")==0?0:rs.getInt("id"));
closed.setCode(rs.getString("code")==null?"":rs.getString("code"));
closed.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	closedList.add(closed);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return closedList;
	}
	
	public ArrayList<Closed> getClosedList(int pageNo,int limit)
	{
		ArrayList<Closed> closedList =new ArrayList<Closed>();
String query="select * from dd_closed limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Closed closed =new Closed();
closed.setId(rs.getInt("id")==0?0:rs.getInt("id"));
closed.setCode(rs.getString("code")==null?"":rs.getString("code"));
closed.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	closedList.add(closed);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return closedList;
	}
	
	public void deleteClosed(int id) {
		
			String query="delete from dd_closed where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(Closed closed)
{
String query="select * from dd_closed ";
String whereClause="";
whereClause+=(null==closed.getCode()||closed.getCode().equals(""))?"":" code='"+closed.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(Closed closed)
{
String query="select count(*) from dd_closed ";
String whereClause="";
whereClause+=(null==closed.getCode()||closed.getCode().equals(""))?"":" code='"+closed.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<Closed> getClosedList(Closed closed)
{
ArrayList<Closed> closedList =new ArrayList<Closed>();
String query=getDynamicQuery(closed);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Closed closed2 =new Closed();
closed2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
closed2.setCode(rs.getString("code")==null?"":rs.getString("code"));
closed2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	closedList.add(closed2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return closedList;
}
	
public ArrayList<Closed> getClosedList(Closed closed,int pageNo,int limit)
{
ArrayList<Closed> closedList =new ArrayList<Closed>();
String query=getDynamicQuery(closed);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Closed closed2 =new Closed();
closed2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
closed2.setCode(rs.getString("code")==null?"":rs.getString("code"));
closed2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	closedList.add(closed2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return closedList;
}
	
	
	public static void main(String[] args) {
		
		ClosedDBService closedDBService =new ClosedDBService();
		
		
		
		 //Test-1 : Create Closed
		  
		  Closed closed = new Closed(); closed.setDefaultValues();
		  closedDBService.createClosed(closed);
		  
		 ArrayList<Closed> closedList = closedDBService.getClosedList();
		ClosedService closedService =new ClosedService();
		closedService.displayList(closedList);
		
	}
}
