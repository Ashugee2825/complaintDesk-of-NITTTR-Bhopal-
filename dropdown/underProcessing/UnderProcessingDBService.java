package dropdown.underProcessing;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class UnderProcessingDBService {
	Connection con;
	
	
	public UnderProcessingDBService()
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
		String query="select count(*) from dd_underProcessing";
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
	public int getTotalPages(UnderProcessing underProcessing,int limit)
	{
		String query=getDynamicQuery2(underProcessing);
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
	
	
	public int getUnderProcessingId(UnderProcessing underProcessing)
	{
		int id=0;
		String query="select id from dd_underProcessing";
String whereClause = " where "+ "code=? and value=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, underProcessing.getCode());
pstmt.setString(2, underProcessing.getValue());
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
	public void createUnderProcessing(UnderProcessing underProcessing)
	{
		
String query="INSERT INTO dd_underProcessing(code,value) VALUES(?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, underProcessing.getCode());
pstmt.setString(2, underProcessing.getValue());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getUnderProcessingId(underProcessing);
		underProcessing.setId(id);
	}
	public void updateUnderProcessing(UnderProcessing underProcessing)
	{
		
String query="update dd_underProcessing set "+"code=?,value=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, underProcessing.getCode());
pstmt.setString(2, underProcessing.getValue());
pstmt.setInt(3, underProcessing.getId());
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
	
	public UnderProcessing getUnderProcessing(int id)
	{
		UnderProcessing underProcessing =new UnderProcessing();
		String query="select * from dd_underProcessing where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
underProcessing.setId(rs.getInt("id")==0?0:rs.getInt("id"));
underProcessing.setCode(rs.getString("code")==null?"":rs.getString("code"));
underProcessing.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return underProcessing;
	}
	
	
	public ArrayList<UnderProcessing> getUnderProcessingList()
	{
		ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
		String query="select * from dd_underProcessing";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	UnderProcessing underProcessing =new UnderProcessing();
underProcessing.setId(rs.getInt("id")==0?0:rs.getInt("id"));
underProcessing.setCode(rs.getString("code")==null?"":rs.getString("code"));
underProcessing.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	underProcessingList.add(underProcessing);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return underProcessingList;
	}
	
	public ArrayList<UnderProcessing> getUnderProcessingList(int pageNo,int limit)
	{
		ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
String query="select * from dd_underProcessing limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	UnderProcessing underProcessing =new UnderProcessing();
underProcessing.setId(rs.getInt("id")==0?0:rs.getInt("id"));
underProcessing.setCode(rs.getString("code")==null?"":rs.getString("code"));
underProcessing.setValue(rs.getString("value")==null?"":rs.getString("value"));
	    	underProcessingList.add(underProcessing);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return underProcessingList;
	}
	
	public void deleteUnderProcessing(int id) {
		
			String query="delete from dd_underProcessing where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(UnderProcessing underProcessing)
{
String query="select * from dd_underProcessing ";
String whereClause="";
whereClause+=(null==underProcessing.getCode()||underProcessing.getCode().equals(""))?"":" code='"+underProcessing.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(UnderProcessing underProcessing)
{
String query="select count(*) from dd_underProcessing ";
String whereClause="";
whereClause+=(null==underProcessing.getCode()||underProcessing.getCode().equals(""))?"":" code='"+underProcessing.getCode()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<UnderProcessing> getUnderProcessingList(UnderProcessing underProcessing)
{
ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
String query=getDynamicQuery(underProcessing);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
UnderProcessing underProcessing2 =new UnderProcessing();
underProcessing2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
underProcessing2.setCode(rs.getString("code")==null?"":rs.getString("code"));
underProcessing2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	underProcessingList.add(underProcessing2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return underProcessingList;
}
	
public ArrayList<UnderProcessing> getUnderProcessingList(UnderProcessing underProcessing,int pageNo,int limit)
{
ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
String query=getDynamicQuery(underProcessing);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
UnderProcessing underProcessing2 =new UnderProcessing();
underProcessing2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
underProcessing2.setCode(rs.getString("code")==null?"":rs.getString("code"));
underProcessing2.setValue(rs.getString("value")==null?"":rs.getString("value"));
    	underProcessingList.add(underProcessing2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return underProcessingList;
}
	
	
	public static void main(String[] args) {
		
		UnderProcessingDBService underProcessingDBService =new UnderProcessingDBService();
		
		
		
		 //Test-1 : Create UnderProcessing
		  
		  UnderProcessing underProcessing = new UnderProcessing(); underProcessing.setDefaultValues();
		  underProcessingDBService.createUnderProcessing(underProcessing);
		  
		 ArrayList<UnderProcessing> underProcessingList = underProcessingDBService.getUnderProcessingList();
		UnderProcessingService underProcessingService =new UnderProcessingService();
		underProcessingService.displayList(underProcessingList);
		
	}
}
