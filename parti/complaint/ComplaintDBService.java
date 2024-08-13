package parti.complaint;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class ComplaintDBService {
	Connection con;
	
	
	public ComplaintDBService()
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
		String query="select count(*) from poc_complaint";
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
	public int getTotalPages(Complaint complaint,int limit)
	{
		String query=getDynamicQuery2(complaint);
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
	
	
	public int getComplaintId(Complaint complaint)
	{
		int id=0;
		String query="select id from poc_complaint";
String whereClause = " where "+ "complaintType=? and detail=? and complaintDt=? and updateBy=? and updateTime=? and complaintStatus=? and updatedt=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint.getComplaintType());
pstmt.setString(2, complaint.getDetail());
pstmt.setDate(3, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getComplaintDt())));
pstmt.setDate(4, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateBy())));
pstmt.setDate(5, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateTime())));
pstmt.setString(6, complaint.getComplaintStatus());
pstmt.setDate(7, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdatedt())));
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
	public void createComplaint(Complaint complaint)
	{
		
String query="INSERT INTO poc_complaint(complaintType,detail,complaintDt,updateBy,updateTime,complaintStatus,updatedt) VALUES(?,?,?,?,?,?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint.getComplaintType());
pstmt.setString(2, complaint.getDetail());
pstmt.setDate(3, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getComplaintDt())));
pstmt.setDate(4, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateBy())));
pstmt.setDate(5, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateTime())));
pstmt.setString(6, complaint.getComplaintStatus());
pstmt.setDate(7, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdatedt())));
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getComplaintId(complaint);
		complaint.setId(id);
	}
	public void updateComplaint(Complaint complaint)
	{
		
String query="update poc_complaint set "+"complaintType=?,detail=?,complaintDt=?,updateBy=?,updateTime=?,complaintStatus=?,updatedt=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, complaint.getComplaintType());
pstmt.setString(2, complaint.getDetail());
pstmt.setDate(3, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getComplaintDt())));
pstmt.setDate(4, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateBy())));
pstmt.setDate(5, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdateTime())));
pstmt.setString(6, complaint.getComplaintStatus());
pstmt.setDate(7, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(complaint.getUpdatedt())));
pstmt.setInt(8, complaint.getId());
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
	
	public Complaint getComplaint(int id)
	{
		Complaint complaint =new Complaint();
		String query="select * from poc_complaint where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
complaint.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint.setComplaintType(rs.getString("complaintType")==null?"":rs.getString("complaintType"));
complaint.setComplaintTypeValue(rs.getString("complaintType")==null?"":getValue(rs.getString("complaintType"),"dd_complaint_type"));
complaint.setDetail(rs.getString("detail")==null?"":rs.getString("detail"));
complaint.setComplaintDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("complaintDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("complaintDt")));
complaint.setUpdateBy(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateBy")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateBy")));
complaint.setUpdateTime(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateTime")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateTime")));
complaint.setComplaintStatus(rs.getString("complaintStatus")==null?"":rs.getString("complaintStatus"));
complaint.setComplaintStatusValue(rs.getString("complaintStatus")==null?"":getValue(rs.getString("complaintStatus"),"dd_open"));
complaint.setUpdatedt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updatedt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updatedt")));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaint;
	}
	
	
	public ArrayList<Complaint> getComplaintList()
	{
		ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
		String query="select * from poc_complaint";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Complaint complaint =new Complaint();
complaint.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint.setComplaintType(rs.getString("complaintType")==null?"":rs.getString("complaintType"));
complaint.setComplaintTypeValue(rs.getString("complaintType")==null?"":getValue(rs.getString("complaintType"),"dd_complaint_type"));
complaint.setDetail(rs.getString("detail")==null?"":rs.getString("detail"));
complaint.setComplaintDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("complaintDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("complaintDt")));
complaint.setUpdateBy(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateBy")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateBy")));
complaint.setUpdateTime(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateTime")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateTime")));
complaint.setComplaintStatus(rs.getString("complaintStatus")==null?"":rs.getString("complaintStatus"));
complaint.setComplaintStatusValue(rs.getString("complaintStatus")==null?"":getValue(rs.getString("complaintStatus"),"dd_open"));
complaint.setUpdatedt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updatedt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updatedt")));
	    	complaintList.add(complaint);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaintList;
	}
	
	public ArrayList<Complaint> getComplaintList(int pageNo,int limit)
	{
		ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
String query="select * from poc_complaint limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	Complaint complaint =new Complaint();
complaint.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint.setComplaintType(rs.getString("complaintType")==null?"":rs.getString("complaintType"));
complaint.setComplaintTypeValue(rs.getString("complaintType")==null?"":getValue(rs.getString("complaintType"),"dd_complaint_type"));
complaint.setDetail(rs.getString("detail")==null?"":rs.getString("detail"));
complaint.setComplaintDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("complaintDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("complaintDt")));
complaint.setUpdateBy(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateBy")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateBy")));
complaint.setUpdateTime(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateTime")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateTime")));
complaint.setComplaintStatus(rs.getString("complaintStatus")==null?"":rs.getString("complaintStatus"));
complaint.setComplaintStatusValue(rs.getString("complaintStatus")==null?"":getValue(rs.getString("complaintStatus"),"dd_open"));
complaint.setUpdatedt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updatedt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updatedt")));
	    	complaintList.add(complaint);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return complaintList;
	}
	
	public void deleteComplaint(int id) {
		
			String query="delete from poc_complaint where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(Complaint complaint)
{
String query="select * from poc_complaint ";
String whereClause="";
whereClause+=(complaint.getId()==0?"":" id="+complaint.getId());
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintType()||complaint.getComplaintType().equals(""))?"":" complaintType='"+complaint.getComplaintType()+"'";
else
whereClause+=(null==complaint.getComplaintType()||complaint.getComplaintType().equals(""))?"":" and complaintType='"+complaint.getComplaintType()+"'";
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintDtFrom()||DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom()).equals("1111-11-11"))?"":" (complaintDt between '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom())+"' and '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtTo())+"')";
else
whereClause+=(null==complaint.getComplaintDtFrom()||DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom()).equals("1111-11-11"))?"":" and (complaintDt between '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom())+"' and '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtTo())+"')";
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintStatus()||complaint.getComplaintStatus().equals(""))?"":" complaintStatus='"+complaint.getComplaintStatus()+"'";
else
whereClause+=(null==complaint.getComplaintStatus()||complaint.getComplaintStatus().equals(""))?"":" and complaintStatus='"+complaint.getComplaintStatus()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(Complaint complaint)
{
String query="select count(*) from poc_complaint ";
String whereClause="";
whereClause+=(complaint.getId()==0?"":" id="+complaint.getId());
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintType()||complaint.getComplaintType().equals(""))?"":" complaintType='"+complaint.getComplaintType()+"'";
else
whereClause+=(null==complaint.getComplaintType()||complaint.getComplaintType().equals(""))?"":" and complaintType='"+complaint.getComplaintType()+"'";
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintDtFrom()||DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom()).equals("1111-11-11"))?"":" (complaintDt between '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom())+"' and '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtTo())+"')";
else
whereClause+=(null==complaint.getComplaintDtFrom()||DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom()).equals("1111-11-11"))?"":" and (complaintDt between '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtFrom())+"' and '"+DateService.getDTSYYYMMDDFormat(complaint.getComplaintDtTo())+"')";
if(whereClause.equals(""))
whereClause+=(null==complaint.getComplaintStatus()||complaint.getComplaintStatus().equals(""))?"":" complaintStatus='"+complaint.getComplaintStatus()+"'";
else
whereClause+=(null==complaint.getComplaintStatus()||complaint.getComplaintStatus().equals(""))?"":" and complaintStatus='"+complaint.getComplaintStatus()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<Complaint> getComplaintList(Complaint complaint)
{
ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
String query=getDynamicQuery(complaint);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Complaint complaint2 =new Complaint();
complaint2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint2.setComplaintType(rs.getString("complaintType")==null?"":rs.getString("complaintType"));
complaint2.setComplaintTypeValue(rs.getString("complaintType")==null?"":getValue(rs.getString("complaintType"),"dd_complaint_type"));
complaint2.setDetail(rs.getString("detail")==null?"":rs.getString("detail"));
complaint2.setComplaintDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("complaintDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("complaintDt")));
complaint2.setUpdateBy(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateBy")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateBy")));
complaint2.setUpdateTime(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateTime")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateTime")));
complaint2.setComplaintStatus(rs.getString("complaintStatus")==null?"":rs.getString("complaintStatus"));
complaint2.setComplaintStatusValue(rs.getString("complaintStatus")==null?"":getValue(rs.getString("complaintStatus"),"dd_open"));
complaint2.setUpdatedt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updatedt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updatedt")));
    	complaintList.add(complaint2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return complaintList;
}
	
public ArrayList<Complaint> getComplaintList(Complaint complaint,int pageNo,int limit)
{
ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
String query=getDynamicQuery(complaint);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
Complaint complaint2 =new Complaint();
complaint2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
complaint2.setComplaintType(rs.getString("complaintType")==null?"":rs.getString("complaintType"));
complaint2.setComplaintTypeValue(rs.getString("complaintType")==null?"":getValue(rs.getString("complaintType"),"dd_complaint_type"));
complaint2.setDetail(rs.getString("detail")==null?"":rs.getString("detail"));
complaint2.setComplaintDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("complaintDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("complaintDt")));
complaint2.setUpdateBy(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateBy")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateBy")));
complaint2.setUpdateTime(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateTime")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateTime")));
complaint2.setComplaintStatus(rs.getString("complaintStatus")==null?"":rs.getString("complaintStatus"));
complaint2.setComplaintStatusValue(rs.getString("complaintStatus")==null?"":getValue(rs.getString("complaintStatus"),"dd_open"));
complaint2.setUpdatedt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updatedt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updatedt")));
    	complaintList.add(complaint2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return complaintList;
}
	
	
	public static void main(String[] args) {
		
		ComplaintDBService complaintDBService =new ComplaintDBService();
		
		
		
		 //Test-1 : Create Complaint
		  
		  Complaint complaint = new Complaint(); complaint.setDefaultValues();
		  complaintDBService.createComplaint(complaint);
		  
		 ArrayList<Complaint> complaintList = complaintDBService.getComplaintList();
		ComplaintService complaintService =new ComplaintService();
		complaintService.displayList(complaintList);
		
	}
}
