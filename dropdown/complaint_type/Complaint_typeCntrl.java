package dropdown.complaint_type;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Complaint_typeCntrl
 */
@WebServlet("/dropdown/complaint_type/complaint_typeCntrl")
public class Complaint_typeCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Complaint_typeCntrl() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page= request.getParameter("page");
		String opr = request.getParameter("opr");
		int pageNo = (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("pageNo")));
		int limit= (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("limit")));
		
		RequestDispatcher rd;
		Complaint_typeDBService complaint_typeDBService =new Complaint_typeDBService();
		Complaint_type complaint_type =new Complaint_type();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("complaint_typeDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaint_typeCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList();
				else { //pagination
					int totalPages= complaint_typeDBService.getTotalPages(limit);
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("complaint_typeDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				complaint_type.setDefaultValues();
				complaint_type.displayValues();
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("addNewComplaint_type.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("updateComplaint_type.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("updateNextComplaint_type.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				complaint_type.setDefaultValues();
				complaint_type.displayValues();
				request.setAttribute("complaint_type",complaint_type);
				
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList();
				else { //pagination
					int totalPages= complaint_typeDBService.getTotalPages(limit);
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("saveShowNextComplaint_type.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type.setId(id);
				complaint_typeDBService.deleteComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("deleteComplaint_typeSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("viewComplaint_type.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewComplaint_type")) 
		{
			if(opr.equals("save"))
			{
				complaint_type.setRequestParam(request);
				complaint_type.displayValues();
				complaint_typeDBService.createComplaint_type(complaint_type);
				request.setAttribute("complaint_type",complaint_type);
				if(pageNo!=0) {//pagination
					int totalPages= complaint_typeDBService.getTotalPages(limit);
					homeURL="complaint_typeCntrl?page=complaint_typeDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewComplaint_typeSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextComplaint_type")) 
		{
			if(opr.equals("update"))
			{
				complaint_type.setRequestParam(request);
				complaint_typeDBService.updateComplaint_type(complaint_type);
				request.setAttribute("complaint_type",complaint_type);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("complaint_typeCntrl?page=complaint_typeDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextComplaint_type")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				complaint_type.setRequestParam(request);
				complaint_type.displayValues();
				complaint_typeDBService.createComplaint_type(complaint_type);
				request.setAttribute("complaint_type",complaint_type);
				if(pageNo!=0) {//pagination
					int totalPages= complaint_typeDBService.getTotalPages(limit);
					homeURL="complaint_typeCntrl?page=complaint_typeDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList();
				else { //pagination
					int totalPages= complaint_typeDBService.getTotalPages(limit);
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("saveShowNextComplaint_type.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				complaint_type.setRequestParam(request);
				complaint_typeDBService.updateComplaint_type(complaint_type);
				request.setAttribute("complaint_type",complaint_type);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					complaint_type.setId(id);
					complaint_typeDBService.deleteComplaint_type(id);
					request.setAttribute("complaint_type",complaint_type);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("complaint_typeCntrl?page=complaint_typeDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateComplaint_type")) 
		{
			if(opr.equals("update"))
			{
				complaint_type.setRequestParam(request);
				complaint_typeDBService.updateComplaint_type(complaint_type);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("updateComplaint_typeSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewComplaint_type")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("printComplaint_type.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchComplaint_type"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaint_typeCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				complaint_type.setRequestParam(request);
				complaint_type.displayValues();
				request.getSession().setAttribute("complaint_typeSearch",complaint_type);
				request.setAttribute("opr","search");
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=complaint_typeDBService.getTotalPages(complaint_type,limit);
					pageNo=1;
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("searchComplaint_type.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				complaint_type.setRequestParam(request);
				complaint_type.displayValues();
				request.getSession().setAttribute("complaint_typeSearch",complaint_type);
				request.setAttribute("opr","complaint_type");
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=complaint_typeDBService.getTotalPages(complaint_type,limit);
					pageNo=1;
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("searchComplaint_typeDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				complaint_type=(Complaint_type)request.getSession().getAttribute("complaint_typeSearch");
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type);
				else { //pagination
					int totalPages= complaint_typeDBService.getTotalPages(complaint_type,limit);
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("searchComplaint_type.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				complaint_type=(Complaint_type)request.getSession().getAttribute("complaint_typeSearch");
				ArrayList<Complaint_type> complaint_typeList =new ArrayList<Complaint_type>();
				if(pageNo==0)
				complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type);
				else { //pagination
					int totalPages= complaint_typeDBService.getTotalPages(complaint_type,limit);
					complaint_typeList = complaint_typeDBService.getComplaint_typeList(complaint_type,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaint_typeList",complaint_typeList);
				rd = request.getRequestDispatcher("searchComplaint_type.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				complaint_type.setDefaultValues();
				complaint_type.displayValues();
				request.getSession().setAttribute("complaint_typeSearch",complaint_type);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchComplaint_type.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("updateComplaint_type.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint_type.setId(id);
				complaint_typeDBService.deleteComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("deleteComplaint_typeSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				complaint_type = complaint_typeDBService.getComplaint_type(id);
				request.setAttribute("complaint_type",complaint_type);
				rd = request.getRequestDispatcher("viewComplaint_type.jsp");
				rd.forward(request, response);
			}
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URI("page=updateComplaint_type&opr=close&homePage=complaint_typeDashboard");
		String v = uri.getQuery();
		
	}
}
