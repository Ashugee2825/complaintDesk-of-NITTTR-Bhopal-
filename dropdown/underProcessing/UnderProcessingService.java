package dropdown.underProcessing;

import java.util.ArrayList;

public class UnderProcessingService {

	public void displayList(ArrayList<UnderProcessing> underProcessingList)
	{
		underProcessingList.forEach((underProcessing) -> print(underProcessing));
	}
	
	public void print(UnderProcessing underProcessing)
	{
		underProcessing.displayValues();
	}
	
}
