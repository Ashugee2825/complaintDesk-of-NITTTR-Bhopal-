package parti.complaint;

import java.util.ArrayList;

public class ComplaintService {

	public void displayList(ArrayList<Complaint> complaintList)
	{
		complaintList.forEach((complaint) -> print(complaint));
	}
	
	public void print(Complaint complaint)
	{
		complaint.displayValues();
	}
	
}
