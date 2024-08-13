package dropdown.open;
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
 * Servlet implementation class OpenCntrl
 */
@WebServlet("/dropdown/open/openCntrl")
public class OpenCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenCntrl() {
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
		OpenDBService openDBService =new OpenDBService();
		Open open =new Open();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("openDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="openCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<Open> openList =new ArrayList<Open>();
				
				if(pageNo==0)
				openList = openDBService.getOpenList();
				else { //pagination
					int totalPages= openDBService.getTotalPages(limit);
					openList = openDBService.getOpenList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("openDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				open.setDefaultValues();
				open.displayValues();
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("addNewOpen.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("updateOpen.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("updateNextOpen.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				open.setDefaultValues();
				open.displayValues();
				request.setAttribute("open",open);
				
				ArrayList<Open> openList =new ArrayList<Open>();
				
				if(pageNo==0)
				openList = openDBService.getOpenList();
				else { //pagination
					int totalPages= openDBService.getTotalPages(limit);
					openList = openDBService.getOpenList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("saveShowNextOpen.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open.setId(id);
				openDBService.deleteOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("deleteOpenSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("viewOpen.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewOpen")) 
		{
			if(opr.equals("save"))
			{
				open.setRequestParam(request);
				open.displayValues();
				openDBService.createOpen(open);
				request.setAttribute("open",open);
				if(pageNo!=0) {//pagination
					int totalPages= openDBService.getTotalPages(limit);
					homeURL="openCntrl?page=openDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewOpenSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextOpen")) 
		{
			if(opr.equals("update"))
			{
				open.setRequestParam(request);
				openDBService.updateOpen(open);
				request.setAttribute("open",open);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("openCntrl?page=openDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextOpen")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				open.setRequestParam(request);
				open.displayValues();
				openDBService.createOpen(open);
				request.setAttribute("open",open);
				if(pageNo!=0) {//pagination
					int totalPages= openDBService.getTotalPages(limit);
					homeURL="openCntrl?page=openDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				
				ArrayList<Open> openList =new ArrayList<Open>();
				if(pageNo==0)
				openList = openDBService.getOpenList();
				else { //pagination
					int totalPages= openDBService.getTotalPages(limit);
					openList = openDBService.getOpenList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("saveShowNextOpen.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				open.setRequestParam(request);
				openDBService.updateOpen(open);
				request.setAttribute("open",open);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					open.setId(id);
					openDBService.deleteOpen(id);
					request.setAttribute("open",open);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("openCntrl?page=openDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateOpen")) 
		{
			if(opr.equals("update"))
			{
				open.setRequestParam(request);
				openDBService.updateOpen(open);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("updateOpenSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewOpen")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("printOpen.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchOpen"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="openCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				open.setRequestParam(request);
				open.displayValues();
				request.getSession().setAttribute("openSearch",open);
				request.setAttribute("opr","search");
				ArrayList<Open> openList =new ArrayList<Open>();
				if(pageNo==0)
				openList = openDBService.getOpenList(open);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=openDBService.getTotalPages(open,limit);
					pageNo=1;
					openList = openDBService.getOpenList(open,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("searchOpen.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				open.setRequestParam(request);
				open.displayValues();
				request.getSession().setAttribute("openSearch",open);
				request.setAttribute("opr","open");
				ArrayList<Open> openList =new ArrayList<Open>();
				if(pageNo==0)
				openList = openDBService.getOpenList(open);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=openDBService.getTotalPages(open,limit);
					pageNo=1;
					openList = openDBService.getOpenList(open,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("searchOpenDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				open=(Open)request.getSession().getAttribute("openSearch");
				ArrayList<Open> openList =new ArrayList<Open>();
				if(pageNo==0)
				openList = openDBService.getOpenList(open);
				else { //pagination
					int totalPages= openDBService.getTotalPages(open,limit);
					openList = openDBService.getOpenList(open,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("searchOpen.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				open=(Open)request.getSession().getAttribute("openSearch");
				ArrayList<Open> openList =new ArrayList<Open>();
				if(pageNo==0)
				openList = openDBService.getOpenList(open);
				else { //pagination
					int totalPages= openDBService.getTotalPages(open,limit);
					openList = openDBService.getOpenList(open,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("openList",openList);
				rd = request.getRequestDispatcher("searchOpen.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				open.setDefaultValues();
				open.displayValues();
				request.getSession().setAttribute("openSearch",open);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchOpen.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("updateOpen.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				open.setId(id);
				openDBService.deleteOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("deleteOpenSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				open = openDBService.getOpen(id);
				request.setAttribute("open",open);
				rd = request.getRequestDispatcher("viewOpen.jsp");
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
		URI uri = new URI("page=updateOpen&opr=close&homePage=openDashboard");
		String v = uri.getQuery();
		
	}
}
