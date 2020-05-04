/* RedeployChoiceBoxEventListener.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: This class is a simple ChangeListener that watches a ChoiceBox for changes.
 */

import javafx.beans.value.*;

public class RedeployChoiceBoxEventListener implements ChangeListener{
	@Override
	public void changed(ObservableValue v, Object newValue, Object oldValue) {
		System.out.println(v.getValue());
		shiftSelected();
	}
	
	// this will fire when the user selects a shift from the ScheduleControls.java ChoiceBox,
	// which will then signal readiness for the Redeploy Button control to fire and subsequently switch
	// shifts.
	public boolean shiftSelected() {
		return true;
	}
}
