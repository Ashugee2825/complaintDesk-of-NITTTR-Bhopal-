package dropdown.cancelled;

import java.util.ArrayList;

public class CancelledService {

	public void displayList(ArrayList<Cancelled> cancelledList)
	{
		cancelledList.forEach((cancelled) -> print(cancelled));
	}
	
	public void print(Cancelled cancelled)
	{
		cancelled.displayValues();
	}
	
}
