package dropdown.underProcessing;
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
 * Servlet implementation class UnderProcessingCntrl
 */
@WebServlet("/dropdown/underProcessing/underProcessingCntrl")
public class UnderProcessingCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnderProcessingCntrl() {
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
		UnderProcessingDBService underProcessingDBService =new UnderProcessingDBService();
		UnderProcessing underProcessing =new UnderProcessing();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("underProcessingDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="underProcessingCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList();
				else { //pagination
					int totalPages= underProcessingDBService.getTotalPages(limit);
					underProcessingList = underProcessingDBService.getUnderProcessingList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("underProcessingDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				underProcessing.setDefaultValues();
				underProcessing.displayValues();
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("addNewUnderProcessing.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("updateUnderProcessing.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("updateNextUnderProcessing.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				underProcessing.setDefaultValues();
				underProcessing.displayValues();
				request.setAttribute("underProcessing",underProcessing);
				
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList();
				else { //pagination
					int totalPages= underProcessingDBService.getTotalPages(limit);
					underProcessingList = underProcessingDBService.getUnderProcessingList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("saveShowNextUnderProcessing.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing.setId(id);
				underProcessingDBService.deleteUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("deleteUnderProcessingSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("viewUnderProcessing.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewUnderProcessing")) 
		{
			if(opr.equals("save"))
			{
				underProcessing.setRequestParam(request);
				underProcessing.displayValues();
				underProcessingDBService.createUnderProcessing(underProcessing);
				request.setAttribute("underProcessing",underProcessing);
				if(pageNo!=0) {//pagination
					int totalPages= underProcessingDBService.getTotalPages(limit);
					homeURL="underProcessingCntrl?page=underProcessingDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewUnderProcessingSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextUnderProcessing")) 
		{
			if(opr.equals("update"))
			{
				underProcessing.setRequestParam(request);
				underProcessingDBService.updateUnderProcessing(underProcessing);
				request.setAttribute("underProcessing",underProcessing);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("underProcessingCntrl?page=underProcessingDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextUnderProcessing")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				underProcessing.setRequestParam(request);
				underProcessing.displayValues();
				underProcessingDBService.createUnderProcessing(underProcessing);
				request.setAttribute("underProcessing",underProcessing);
				if(pageNo!=0) {//pagination
					int totalPages= underProcessingDBService.getTotalPages(limit);
					homeURL="underProcessingCntrl?page=underProcessingDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList();
				else { //pagination
					int totalPages= underProcessingDBService.getTotalPages(limit);
					underProcessingList = underProcessingDBService.getUnderProcessingList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("saveShowNextUnderProcessing.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				underProcessing.setRequestParam(request);
				underProcessingDBService.updateUnderProcessing(underProcessing);
				request.setAttribute("underProcessing",underProcessing);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					underProcessing.setId(id);
					underProcessingDBService.deleteUnderProcessing(id);
					request.setAttribute("underProcessing",underProcessing);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("underProcessingCntrl?page=underProcessingDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateUnderProcessing")) 
		{
			if(opr.equals("update"))
			{
				underProcessing.setRequestParam(request);
				underProcessingDBService.updateUnderProcessing(underProcessing);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("updateUnderProcessingSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewUnderProcessing")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("printUnderProcessing.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchUnderProcessing"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="underProcessingCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				underProcessing.setRequestParam(request);
				underProcessing.displayValues();
				request.getSession().setAttribute("underProcessingSearch",underProcessing);
				request.setAttribute("opr","search");
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=underProcessingDBService.getTotalPages(underProcessing,limit);
					pageNo=1;
					underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("searchUnderProcessing.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				underProcessing.setRequestParam(request);
				underProcessing.displayValues();
				request.getSession().setAttribute("underProcessingSearch",underProcessing);
				request.setAttribute("opr","underProcessing");
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=underProcessingDBService.getTotalPages(underProcessing,limit);
					pageNo=1;
					underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("searchUnderProcessingDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				underProcessing=(UnderProcessing)request.getSession().getAttribute("underProcessingSearch");
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing);
				else { //pagination
					int totalPages= underProcessingDBService.getTotalPages(underProcessing,limit);
					underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("searchUnderProcessing.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				underProcessing=(UnderProcessing)request.getSession().getAttribute("underProcessingSearch");
				ArrayList<UnderProcessing> underProcessingList =new ArrayList<UnderProcessing>();
				if(pageNo==0)
				underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing);
				else { //pagination
					int totalPages= underProcessingDBService.getTotalPages(underProcessing,limit);
					underProcessingList = underProcessingDBService.getUnderProcessingList(underProcessing,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("underProcessingList",underProcessingList);
				rd = request.getRequestDispatcher("searchUnderProcessing.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				underProcessing.setDefaultValues();
				underProcessing.displayValues();
				request.getSession().setAttribute("underProcessingSearch",underProcessing);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchUnderProcessing.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("updateUnderProcessing.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				underProcessing.setId(id);
				underProcessingDBService.deleteUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("deleteUnderProcessingSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				underProcessing = underProcessingDBService.getUnderProcessing(id);
				request.setAttribute("underProcessing",underProcessing);
				rd = request.getRequestDispatcher("viewUnderProcessing.jsp");
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
		URI uri = new URI("page=updateUnderProcessing&opr=close&homePage=underProcessingDashboard");
		String v = uri.getQuery();
		
	}
}
