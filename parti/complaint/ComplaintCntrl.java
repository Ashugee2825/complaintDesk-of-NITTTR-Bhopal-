package parti.complaint;
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
 * Servlet implementation class ComplaintCntrl
 */
@WebServlet("/parti/complaint/complaintCntrl")
public class ComplaintCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComplaintCntrl() {
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
		ComplaintDBService complaintDBService =new ComplaintDBService();
		Complaint complaint =new Complaint();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("complaintDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaintCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList();
				else { //pagination
					int totalPages= complaintDBService.getTotalPages(limit);
					complaintList = complaintDBService.getComplaintList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("complaintDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				complaint.setDefaultValues();
				complaint.displayValues();
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("addNewComplaint.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("updateComplaint.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("updateNextComplaint.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				complaint.setDefaultValues();
				complaint.displayValues();
				request.setAttribute("complaint",complaint);
				
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList();
				else { //pagination
					int totalPages= complaintDBService.getTotalPages(limit);
					complaintList = complaintDBService.getComplaintList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("saveShowNextComplaint.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint.setId(id);
				complaintDBService.deleteComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("deleteComplaintSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("viewComplaint.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewComplaint")) 
		{
			if(opr.equals("save"))
			{
				complaint.setRequestParam(request);
				complaint.displayValues();
				complaintDBService.createComplaint(complaint);
				request.setAttribute("complaint",complaint);
				if(pageNo!=0) {//pagination
					int totalPages= complaintDBService.getTotalPages(limit);
					homeURL="complaintCntrl?page=complaintDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewComplaintSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextComplaint")) 
		{
			if(opr.equals("update"))
			{
				complaint.setRequestParam(request);
				complaintDBService.updateComplaint(complaint);
				request.setAttribute("complaint",complaint);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("complaintCntrl?page=complaintDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextComplaint")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				complaint.setRequestParam(request);
				complaint.displayValues();
				complaintDBService.createComplaint(complaint);
				request.setAttribute("complaint",complaint);
				if(pageNo!=0) {//pagination
					int totalPages= complaintDBService.getTotalPages(limit);
					homeURL="complaintCntrl?page=complaintDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList();
				else { //pagination
					int totalPages= complaintDBService.getTotalPages(limit);
					complaintList = complaintDBService.getComplaintList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("saveShowNextComplaint.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				complaint.setRequestParam(request);
				complaintDBService.updateComplaint(complaint);
				request.setAttribute("complaint",complaint);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					complaint.setId(id);
					complaintDBService.deleteComplaint(id);
					request.setAttribute("complaint",complaint);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("complaintCntrl?page=complaintDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateComplaint")) 
		{
			if(opr.equals("update"))
			{
				complaint.setRequestParam(request);
				complaintDBService.updateComplaint(complaint);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("updateComplaintSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewComplaint")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("printComplaint.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchComplaint"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="complaintCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				complaint.setRequestParam(request);
				complaint.displayValues();
				request.getSession().setAttribute("complaintSearch",complaint);
				request.setAttribute("opr","search");
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList(complaint);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=complaintDBService.getTotalPages(complaint,limit);
					pageNo=1;
					complaintList = complaintDBService.getComplaintList(complaint,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("searchComplaint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				complaint.setRequestParam(request);
				complaint.displayValues();
				request.getSession().setAttribute("complaintSearch",complaint);
				request.setAttribute("opr","complaint");
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList(complaint);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=complaintDBService.getTotalPages(complaint,limit);
					pageNo=1;
					complaintList = complaintDBService.getComplaintList(complaint,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("searchComplaintDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				complaint=(Complaint)request.getSession().getAttribute("complaintSearch");
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList(complaint);
				else { //pagination
					int totalPages= complaintDBService.getTotalPages(complaint,limit);
					complaintList = complaintDBService.getComplaintList(complaint,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("searchComplaint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				complaint=(Complaint)request.getSession().getAttribute("complaintSearch");
				ArrayList<Complaint> complaintList =new ArrayList<Complaint>();
				if(pageNo==0)
				complaintList = complaintDBService.getComplaintList(complaint);
				else { //pagination
					int totalPages= complaintDBService.getTotalPages(complaint,limit);
					complaintList = complaintDBService.getComplaintList(complaint,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("complaintList",complaintList);
				rd = request.getRequestDispatcher("searchComplaint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				complaint.setDefaultValues();
				complaint.displayValues();
				request.getSession().setAttribute("complaintSearch",complaint);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchComplaint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("updateComplaint.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				complaint.setId(id);
				complaintDBService.deleteComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("deleteComplaintSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				complaint = complaintDBService.getComplaint(id);
				request.setAttribute("complaint",complaint);
				rd = request.getRequestDispatcher("viewComplaint.jsp");
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
		URI uri = new URI("page=updateComplaint&opr=close&homePage=complaintDashboard");
		String v = uri.getQuery();
		
	}
}
