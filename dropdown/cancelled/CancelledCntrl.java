package dropdown.cancelled;
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
 * Servlet implementation class CancelledCntrl
 */
@WebServlet("/dropdown/cancelled/cancelledCntrl")
public class CancelledCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelledCntrl() {
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
		CancelledDBService cancelledDBService =new CancelledDBService();
		Cancelled cancelled =new Cancelled();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("cancelledDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="cancelledCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList();
				else { //pagination
					int totalPages= cancelledDBService.getTotalPages(limit);
					cancelledList = cancelledDBService.getCancelledList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("cancelledDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				cancelled.setDefaultValues();
				cancelled.displayValues();
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("addNewCancelled.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("updateCancelled.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("updateNextCancelled.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				cancelled.setDefaultValues();
				cancelled.displayValues();
				request.setAttribute("cancelled",cancelled);
				
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList();
				else { //pagination
					int totalPages= cancelledDBService.getTotalPages(limit);
					cancelledList = cancelledDBService.getCancelledList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("saveShowNextCancelled.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled.setId(id);
				cancelledDBService.deleteCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("deleteCancelledSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("viewCancelled.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewCancelled")) 
		{
			if(opr.equals("save"))
			{
				cancelled.setRequestParam(request);
				cancelled.displayValues();
				cancelledDBService.createCancelled(cancelled);
				request.setAttribute("cancelled",cancelled);
				if(pageNo!=0) {//pagination
					int totalPages= cancelledDBService.getTotalPages(limit);
					homeURL="cancelledCntrl?page=cancelledDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewCancelledSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextCancelled")) 
		{
			if(opr.equals("update"))
			{
				cancelled.setRequestParam(request);
				cancelledDBService.updateCancelled(cancelled);
				request.setAttribute("cancelled",cancelled);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("cancelledCntrl?page=cancelledDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextCancelled")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				cancelled.setRequestParam(request);
				cancelled.displayValues();
				cancelledDBService.createCancelled(cancelled);
				request.setAttribute("cancelled",cancelled);
				if(pageNo!=0) {//pagination
					int totalPages= cancelledDBService.getTotalPages(limit);
					homeURL="cancelledCntrl?page=cancelledDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList();
				else { //pagination
					int totalPages= cancelledDBService.getTotalPages(limit);
					cancelledList = cancelledDBService.getCancelledList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("saveShowNextCancelled.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				cancelled.setRequestParam(request);
				cancelledDBService.updateCancelled(cancelled);
				request.setAttribute("cancelled",cancelled);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					cancelled.setId(id);
					cancelledDBService.deleteCancelled(id);
					request.setAttribute("cancelled",cancelled);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("cancelledCntrl?page=cancelledDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateCancelled")) 
		{
			if(opr.equals("update"))
			{
				cancelled.setRequestParam(request);
				cancelledDBService.updateCancelled(cancelled);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("updateCancelledSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewCancelled")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("printCancelled.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchCancelled"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="cancelledCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				cancelled.setRequestParam(request);
				cancelled.displayValues();
				request.getSession().setAttribute("cancelledSearch",cancelled);
				request.setAttribute("opr","search");
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList(cancelled);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=cancelledDBService.getTotalPages(cancelled,limit);
					pageNo=1;
					cancelledList = cancelledDBService.getCancelledList(cancelled,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("searchCancelled.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				cancelled.setRequestParam(request);
				cancelled.displayValues();
				request.getSession().setAttribute("cancelledSearch",cancelled);
				request.setAttribute("opr","cancelled");
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList(cancelled);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=cancelledDBService.getTotalPages(cancelled,limit);
					pageNo=1;
					cancelledList = cancelledDBService.getCancelledList(cancelled,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("searchCancelledDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				cancelled=(Cancelled)request.getSession().getAttribute("cancelledSearch");
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList(cancelled);
				else { //pagination
					int totalPages= cancelledDBService.getTotalPages(cancelled,limit);
					cancelledList = cancelledDBService.getCancelledList(cancelled,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("searchCancelled.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				cancelled=(Cancelled)request.getSession().getAttribute("cancelledSearch");
				ArrayList<Cancelled> cancelledList =new ArrayList<Cancelled>();
				if(pageNo==0)
				cancelledList = cancelledDBService.getCancelledList(cancelled);
				else { //pagination
					int totalPages= cancelledDBService.getTotalPages(cancelled,limit);
					cancelledList = cancelledDBService.getCancelledList(cancelled,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("cancelledList",cancelledList);
				rd = request.getRequestDispatcher("searchCancelled.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				cancelled.setDefaultValues();
				cancelled.displayValues();
				request.getSession().setAttribute("cancelledSearch",cancelled);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchCancelled.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("updateCancelled.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				cancelled.setId(id);
				cancelledDBService.deleteCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("deleteCancelledSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				cancelled = cancelledDBService.getCancelled(id);
				request.setAttribute("cancelled",cancelled);
				rd = request.getRequestDispatcher("viewCancelled.jsp");
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
		URI uri = new URI("page=updateCancelled&opr=close&homePage=cancelledDashboard");
		String v = uri.getQuery();
		
	}
}
