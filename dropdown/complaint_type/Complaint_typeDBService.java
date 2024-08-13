package dropdown.complaint_type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class Complaint_typeDBService {
	Connection con;
	
	
	public Complaint_typeDBService()
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
		String query="select count(*) from dd_complaint_type";
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
	public int getTotalPages(Complaint_type complaint_type,int limit)
	{
		String query=getDynamicQuery2(complaint_type);
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
	
	
	public int getComplaint_typeId(Complaint_type complaint_type)
	{
		int id=0;
		String query="select id from dd_complaint_type";
String whereClause = " where "+ "code=? and value=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint_type.getCode());
pstmt.setString(2, complaint_type.getValue());
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
	public void createComplaint_type(Complaint_type complaint_type)
	{
		
String query="INSERT INTO dd_complaint_type(code,value) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint_type.getCode());
pstmt.setString(2, complaint_type.getValue());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getComplaint_typeId(complaint_type);
		complaint_type.setId(id);
	}
	public void updateComplaint_type(Complaint_type complaint_type)
	{
		
String query="update dd_complaint_type set "+"code=?,value=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint_type.getCode());
pstmt.setString(2, complaint_type.getValue());
pstmt.setInt(3, complaint_type.getId());
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
	
	public Complaint_type getComplaint_type(int id)
	{
		Complaint_type complaint_type =new Complaint_type();
		String query="select * from dd_complaint_type where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
complaint_type.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint_type.setCode(rs.getString("code")==null?"":rs.getString("code"));
complaint_type.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaint_type;
	}
	
	
	public ArrayList<Complaint_type> getComplaint_typeList()
	{
		ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
		String query="select * from dd_complaint_type";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Complaint_type complaint_type =new Complaint_type();
complaint_type.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint_type.setCode(rs.getString("code")==null?"":rs.getString("code"));
complaint_type.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	complaint_typeList.add(complaint_type);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaint_typeList;
	}
	
	public ArrayList<Complaint_type> getComplaint_typeList(int pageNo,int limit)
	{
		ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
String query="select * from dd_complaint_type limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Complaint_type complaint_type =new Complaint_type();
complaint_type.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint_type.setCode(rs.getString("code")==null?"":rs.getString("code"));
complaint_type.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	complaint_typeList.add(complaint_type);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaint_typeList;
	}
	
	public void deleteComplaint_type(int id) {
		
			String query="delete from dd_complaint_type where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(Complaint_type complaint_type)
{
String query="select * from dd_complaint_type ";
String whereClause="";
whereClause+=(null==complaint_type.getCode()||complaint_type.getCode().equals(""))?"":" code='"+complaint_type.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(Complaint_type complaint_type)
{
String query="select count(*) from dd_complaint_type ";
String whereClause="";
whereClause+=(null==complaint_type.getCode()||complaint_type.getCode().equals(""))?"":" code='"+complaint_type.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<Complaint_type> getComplaint_typeList(Complaint_type complaint_type)
{
ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
String query=getDynamicQuery(complaint_type);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Complaint_type complaint_type2 =new Complaint_type();
complaint_type2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint_type2.setCode(rs.getString("code")==null?"":rs.getString("code"));
complaint_type2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	complaint_typeList.add(complaint_type2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return complaint_typeList;
}
	
public ArrayList<Complaint_type> getComplaint_typeList(Complaint_type complaint_type,int pageNo,int limit)
{
ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
String query=getDynamicQuery(complaint_type);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Complaint_type complaint_type2 =new Complaint_type();
complaint_type2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint_type2.setCode(rs.getString("code")==null?"":rs.getString("code"));
complaint_type2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	complaint_typeList.add(complaint_type2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return complaint_typeList;
}
	
	
	public static void main(String[] args) {
		
		Complaint_typeDBService complaint_typeDBService =new Complaint_typeDBService();
		
		
		
		 //Test-1 : Create Complaint_type
		  
		  Complaint_type complaint_type = new Complaint_type(); complaint_type.setDefaultValues();
		  complaint_typeDBService.createComplaint_type(complaint_type);
		  
		 ArrayList<Complaint_type> complaint_typeList = complaint_typeDBService.getComplaint_typeList();
		Complaint_typeService complaint_typeService =new Complaint_typeService();
		complaint_typeService.displayList(complaint_typeList);
		
	}
}
