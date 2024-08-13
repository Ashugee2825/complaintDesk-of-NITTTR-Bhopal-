package dropdown.open;

import java.util.ArrayList;

public class OpenService {

	public void displayList(ArrayList<Open> openList)
	{
		openList.forEach((open) -> print(open));
	}
	
	public void print(Open open)
	{
		open.displayValues();
	}
	
}
