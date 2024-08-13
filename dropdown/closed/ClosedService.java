package dropdown.closed;

import java.util.ArrayList;

public class ClosedService {

	public void displayList(ArrayList<Closed> closedList)
	{
		closedList.forEach((closed) -> print(closed));
	}
	
	public void print(Closed closed)
	{
		closed.displayValues();
	}
	
}
