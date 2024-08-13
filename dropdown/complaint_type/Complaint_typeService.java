package dropdown.complaint_type;

import java.util.ArrayList;

public class Complaint_typeService {

	public void displayList(ArrayList<Complaint_type> complaint_typeList)
	{
		complaint_typeList.forEach((complaint_type) -> print(complaint_type));
	}
	
	public void print(Complaint_type complaint_type)
	{
		complaint_type.displayValues();
	}
	
}
