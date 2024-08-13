package parti.complaint;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class Complaint {
 
int id;
String complaintType;
String complaintTypeValue;
String detail;
Date complaintDt;
Date complaintDtFrom;
Date complaintDtTo;
Date updateBy;
Date updateByFrom;
Date updateByTo;
Date updateTime;
Date updateTimeFrom;
Date updateTimeTo;
String complaintStatus;
String complaintStatusValue;
Date updatedt;
Date updatedtFrom;
Date updatedtTo;

public int getId()
{
return id;
}
public void setId(int id)
{
this.id=id;
}
public String getComplaintType()
{
return complaintType;
}
public String getComplaintTypeValue()
{
return complaintTypeValue;
}
public void setComplaintType(String complaintType)
{
this.complaintType=complaintType;
}
public void setComplaintTypeValue(String complaintTypeValue)
{
this.complaintTypeValue = complaintTypeValue;
}
public String getDetail()
{
return detail;
}
public void setDetail(String detail)
{
this.detail=detail;
}
public Date getComplaintDt()
{
return complaintDt;
}
public Date getComplaintDtFrom()
{
return complaintDtFrom;
}
public Date getComplaintDtTo()
{
return complaintDtTo;
}
public void setComplaintDt(Date complaintDt)
{
this.complaintDt=complaintDt;
}
public void setComplaintDtFrom(Date complaintDtFrom)
{
this.complaintDtFrom=complaintDtFrom;
}
public void setComplaintDtTo(Date complaintDtTo)
{
this.complaintDtTo=complaintDtTo;
}
public Date getUpdateBy()
{
return updateBy;
}
public Date getUpdateByFrom()
{
return updateByFrom;
}
public Date getUpdateByTo()
{
return updateByTo;
}
public void setUpdateBy(Date updateBy)
{
this.updateBy=updateBy;
}
public void setUpdateByFrom(Date updateByFrom)
{
this.updateByFrom=updateByFrom;
}
public void setUpdateByTo(Date updateByTo)
{
this.updateByTo=updateByTo;
}
public Date getUpdateTime()
{
return updateTime;
}
public Date getUpdateTimeFrom()
{
return updateTimeFrom;
}
public Date getUpdateTimeTo()
{
return updateTimeTo;
}
public void setUpdateTime(Date updateTime)
{
this.updateTime=updateTime;
}
public void setUpdateTimeFrom(Date updateTimeFrom)
{
this.updateTimeFrom=updateTimeFrom;
}
public void setUpdateTimeTo(Date updateTimeTo)
{
this.updateTimeTo=updateTimeTo;
}
public String getComplaintStatus()
{
return complaintStatus;
}
public String getComplaintStatusValue()
{
return complaintStatusValue;
}
public void setComplaintStatus(String complaintStatus)
{
this.complaintStatus=complaintStatus;
}
public void setComplaintStatusValue(String complaintStatusValue)
{
this.complaintStatusValue = complaintStatusValue;
}
public Date getUpdatedt()
{
return updatedt;
}
public Date getUpdatedtFrom()
{
return updatedtFrom;
}
public Date getUpdatedtTo()
{
return updatedtTo;
}
public void setUpdatedt(Date updatedt)
{
this.updatedt=updatedt;
}
public void setUpdatedtFrom(Date updatedtFrom)
{
this.updatedtFrom=updatedtFrom;
}
public void setUpdatedtTo(Date updatedtTo)
{
this.updatedtTo=updatedtTo;
}


public void setRequestParam(HttpServletRequest request) {

this.setId(null!=request.getParameter("id")&&!request.getParameter("id").equals("")?Integer.parseInt((String)request.getParameter("id")):0);
this.setComplaintType(null!=request.getParameter("complaintType")?request.getParameter("complaintType"):"");
this.setDetail(null!=request.getParameter("detail")?request.getParameter("detail"):"");
this.setComplaintDt(null!=request.getParameter("complaintDt")&&!request.getParameter("complaintDt").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("complaintDt")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintDtFrom(null!=request.getParameter("complaintDtFrom")&&!request.getParameter("complaintDtFrom").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("complaintDtFrom")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintDtTo(null!=request.getParameter("complaintDtTo")&&!request.getParameter("complaintDtTo").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("complaintDtTo")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateBy(null!=request.getParameter("updateBy")&&!request.getParameter("updateBy").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateBy")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateByFrom(null!=request.getParameter("updateByFrom")&&!request.getParameter("updateByFrom").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateByFrom")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateByTo(null!=request.getParameter("updateByTo")&&!request.getParameter("updateByTo").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateByTo")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTime(null!=request.getParameter("updateTime")&&!request.getParameter("updateTime").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateTime")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTimeFrom(null!=request.getParameter("updateTimeFrom")&&!request.getParameter("updateTimeFrom").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateTimeFrom")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTimeTo(null!=request.getParameter("updateTimeTo")&&!request.getParameter("updateTimeTo").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateTimeTo")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintStatus(null!=request.getParameter("complaintStatus")?request.getParameter("complaintStatus"):"");
this.setUpdatedt(null!=request.getParameter("updatedt")&&!request.getParameter("updatedt").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updatedt")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdatedtFrom(null!=request.getParameter("updatedtFrom")&&!request.getParameter("updatedtFrom").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updatedtFrom")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdatedtTo(null!=request.getParameter("updatedtTo")&&!request.getParameter("updatedtTo").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updatedtTo")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));

}

public void displayReqParam(HttpServletRequest request) {


System.out.println("------Begin:Request Param Values---------");
System.out.println("id = "+request.getParameter("id"));
System.out.println("complaintType = "+request.getParameter("complaintType"));
System.out.println("detail = "+request.getParameter("detail"));
System.out.println("complaintDt = "+request.getParameter("complaintDt"));
System.out.println("complaintDtFrom = "+request.getParameter("complaintDtFrom"));
System.out.println("complaintDtTo = "+request.getParameter("complaintDtTo"));
System.out.println("updateBy = "+request.getParameter("updateBy"));
System.out.println("updateByFrom = "+request.getParameter("updateByFrom"));
System.out.println("updateByTo = "+request.getParameter("updateByTo"));
System.out.println("updateTime = "+request.getParameter("updateTime"));
System.out.println("updateTimeFrom = "+request.getParameter("updateTimeFrom"));
System.out.println("updateTimeTo = "+request.getParameter("updateTimeTo"));
System.out.println("complaintStatus = "+request.getParameter("complaintStatus"));
System.out.println("updatedt = "+request.getParameter("updatedt"));
System.out.println("updatedtFrom = "+request.getParameter("updatedtFrom"));
System.out.println("updatedtTo = "+request.getParameter("updatedtTo"));

System.out.println("------End:Request Param Values---------");
}

public void displayValues() {

System.out.println("Id = "+this.getId());
System.out.println("ComplaintType = "+this.getComplaintType());
System.out.println("Detail = "+this.getDetail());
System.out.println("ComplaintDt = "+DateService.getDTSYYYMMDDFormat(this.getComplaintDt()));
System.out.println("ComplaintDtFrom = "+DateService.getDTSYYYMMDDFormat(this.getComplaintDtFrom()));
System.out.println("ComplaintDtTo = "+DateService.getDTSYYYMMDDFormat(this.getComplaintDtTo()));
System.out.println("UpdateBy = "+DateService.getDTSYYYMMDDFormat(this.getUpdateBy()));
System.out.println("UpdateByFrom = "+DateService.getDTSYYYMMDDFormat(this.getUpdateByFrom()));
System.out.println("UpdateByTo = "+DateService.getDTSYYYMMDDFormat(this.getUpdateByTo()));
System.out.println("UpdateTime = "+DateService.getDTSYYYMMDDFormat(this.getUpdateTime()));
System.out.println("UpdateTimeFrom = "+DateService.getDTSYYYMMDDFormat(this.getUpdateTimeFrom()));
System.out.println("UpdateTimeTo = "+DateService.getDTSYYYMMDDFormat(this.getUpdateTimeTo()));
System.out.println("ComplaintStatus = "+this.getComplaintStatus());
System.out.println("Updatedt = "+DateService.getDTSYYYMMDDFormat(this.getUpdatedt()));
System.out.println("UpdatedtFrom = "+DateService.getDTSYYYMMDDFormat(this.getUpdatedtFrom()));
System.out.println("UpdatedtTo = "+DateService.getDTSYYYMMDDFormat(this.getUpdatedtTo()));

}

public void setDefaultValues() {

this.setComplaintType("");
this.setDetail("");
this.setComplaintDt(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintDtFrom(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintDtTo(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateBy(DateService.getSTDYYYYMMDDFormat("ABC"));
this.setUpdateByFrom(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateByTo(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTime(DateService.getSTDYYYYMMDDFormat("00:00:00"));
this.setUpdateTimeFrom(DateService.getSTDYYYYMMDDFormat("00:00:00"));
this.setUpdateTimeTo(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setComplaintStatus("");
this.setUpdatedt(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdatedtFrom(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdatedtTo(DateService.getSTDYYYYMMDDFormat("11-11-1111"));

}
}