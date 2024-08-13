package dropdown.closed;
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
 * Servlet implementation class ClosedCntrl
 */
@WebServlet("/dropdown/closed/closedCntrl")
public class ClosedCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClosedCntrl() {
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
		ClosedDBService closedDBService =new ClosedDBService();
		Closed closed =new Closed();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("closedDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="closedCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				
				if(pageNo==0)
				closedList = closedDBService.getClosedList();
				else { //pagination
					int totalPages= closedDBService.getTotalPages(limit);
					closedList = closedDBService.getClosedList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("closedDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				closed.setDefaultValues();
				closed.displayValues();
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("addNewClosed.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("updateClosed.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("updateNextClosed.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				closed.setDefaultValues();
				closed.displayValues();
				request.setAttribute("closed",closed);
				
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				
				if(pageNo==0)
				closedList = closedDBService.getClosedList();
				else { //pagination
					int totalPages= closedDBService.getTotalPages(limit);
					closedList = closedDBService.getClosedList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("saveShowNextClosed.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed.setId(id);
				closedDBService.deleteClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("deleteClosedSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("viewClosed.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewClosed")) 
		{
			if(opr.equals("save"))
			{
				closed.setRequestParam(request);
				closed.displayValues();
				closedDBService.createClosed(closed);
				request.setAttribute("closed",closed);
				if(pageNo!=0) {//pagination
					int totalPages= closedDBService.getTotalPages(limit);
					homeURL="closedCntrl?page=closedDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewClosedSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextClosed")) 
		{
			if(opr.equals("update"))
			{
				closed.setRequestParam(request);
				closedDBService.updateClosed(closed);
				request.setAttribute("closed",closed);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("closedCntrl?page=closedDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextClosed")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				closed.setRequestParam(request);
				closed.displayValues();
				closedDBService.createClosed(closed);
				request.setAttribute("closed",closed);
				if(pageNo!=0) {//pagination
					int totalPages= closedDBService.getTotalPages(limit);
					homeURL="closedCntrl?page=closedDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				if(pageNo==0)
				closedList = closedDBService.getClosedList();
				else { //pagination
					int totalPages= closedDBService.getTotalPages(limit);
					closedList = closedDBService.getClosedList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("saveShowNextClosed.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				closed.setRequestParam(request);
				closedDBService.updateClosed(closed);
				request.setAttribute("closed",closed);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					closed.setId(id);
					closedDBService.deleteClosed(id);
					request.setAttribute("closed",closed);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("closedCntrl?page=closedDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateClosed")) 
		{
			if(opr.equals("update"))
			{
				closed.setRequestParam(request);
				closedDBService.updateClosed(closed);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("updateClosedSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewClosed")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("printClosed.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchClosed"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="closedCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				closed.setRequestParam(request);
				closed.displayValues();
				request.getSession().setAttribute("closedSearch",closed);
				request.setAttribute("opr","search");
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				if(pageNo==0)
				closedList = closedDBService.getClosedList(closed);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=closedDBService.getTotalPages(closed,limit);
					pageNo=1;
					closedList = closedDBService.getClosedList(closed,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("searchClosed.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				closed.setRequestParam(request);
				closed.displayValues();
				request.getSession().setAttribute("closedSearch",closed);
				request.setAttribute("opr","closed");
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				if(pageNo==0)
				closedList = closedDBService.getClosedList(closed);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=closedDBService.getTotalPages(closed,limit);
					pageNo=1;
					closedList = closedDBService.getClosedList(closed,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("searchClosedDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				closed=(Closed)request.getSession().getAttribute("closedSearch");
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				if(pageNo==0)
				closedList = closedDBService.getClosedList(closed);
				else { //pagination
					int totalPages= closedDBService.getTotalPages(closed,limit);
					closedList = closedDBService.getClosedList(closed,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("searchClosed.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				closed=(Closed)request.getSession().getAttribute("closedSearch");
				ArrayList<Closed> closedList =new ArrayList<Closed>();
				if(pageNo==0)
				closedList = closedDBService.getClosedList(closed);
				else { //pagination
					int totalPages= closedDBService.getTotalPages(closed,limit);
					closedList = closedDBService.getClosedList(closed,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("closedList",closedList);
				rd = request.getRequestDispatcher("searchClosed.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				closed.setDefaultValues();
				closed.displayValues();
				request.getSession().setAttribute("closedSearch",closed);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchClosed.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("updateClosed.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				closed.setId(id);
				closedDBService.deleteClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("deleteClosedSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				closed = closedDBService.getClosed(id);
				request.setAttribute("closed",closed);
				rd = request.getRequestDispatcher("viewClosed.jsp");
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
		URI uri = new URI("page=updateClosed&opr=close&homePage=closedDashboard");
		String v = uri.getQuery();
		
	}
}
