
/* Change Logs: General task | Function to store the value from the clipboard in a variable | 16/11/23 */


package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "Generate the Clipboard Value and store in a runtime variable",
        description = "This addon will generate the Clipboard Value and store in a runtime variable",
        applicationType = ApplicationType.WEB)
public class StoringClipboardValueInVariable extends WebAction {
  
  @TestData(reference = "variable")
  private com.testsigma.sdk.TestData runtimeVar;
  
  @RunTimeData
  private com.testsigma.sdk.RunTimeData runTimeData;

  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    logger.info("Initiating execution");
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    try 
    {
    	
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Get the data from the clipboard

			String clipboardValue = null;
      
            clipboardValue = (String) clipboard.getData(DataFlavor.stringFlavor);

		runTimeData.setKey(runtimeVar.getValue().toString());
		runTimeData.setValue(clipboardValue);
       
       result = Result.SUCCESS;
       setSuccessMessage("Successfully generated the clicpboard value " + clipboardValue + " and store in runtime varibale " + runtimeVar.getValue().toString());
    }
    catch (Exception e) 
    {
        result = com.testsigma.sdk.Result.FAILED;
        setErrorMessage(errorMessage);
	} 
    return result;
  }
}