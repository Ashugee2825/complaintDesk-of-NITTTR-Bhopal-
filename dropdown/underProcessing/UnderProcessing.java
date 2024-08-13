package dropdown.underProcessing;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class UnderProcessing {
 
int id;
String code;
String value;

public int getId()
{
return id;
}
public void setId(int id)
{
this.id=id;
}
public String getCode()
{
return code;
}
public void setCode(String code)
{
this.code=code;
}
public String getValue()
{
return value;
}
public void setValue(String value)
{
this.value=value;
}


public void setRequestParam(HttpServletRequest request) {

this.setId(null!=request.getParameter("id")&&!request.getParameter("id").equals("")?Integer.parseInt((String)request.getParameter("id")):0);
this.setCode(null!=request.getParameter("code")?request.getParameter("code"):"");
this.setValue(null!=request.getParameter("value")?request.getParameter("value"):"");

}

public void displayReqParam(HttpServletRequest request) {


System.out.println("------Begin:Request Param Values---------");
System.out.println("id = "+request.getParameter("id"));
System.out.println("code = "+request.getParameter("code"));
System.out.println("value = "+request.getParameter("value"));

System.out.println("------End:Request Param Values---------");
}

public void displayValues() {

System.out.println("Id = "+this.getId());
System.out.println("Code = "+this.getCode());
System.out.println("Value = "+this.getValue());

}

public void setDefaultValues() {

this.setCode("");
this.setValue("");

}
}