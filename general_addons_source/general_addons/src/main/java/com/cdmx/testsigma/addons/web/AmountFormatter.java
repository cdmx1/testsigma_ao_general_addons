
/* Change Logs: AUTO-859 | Function to change the number into the amount format with commas - eg. : 1,000,000 | 01/12/23 */


package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import java.text.NumberFormat;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "Convert the amount into the comma format and store in runtime variable",
        description = "This addon will convert the amount to the comma format and store in a variable",
        applicationType = ApplicationType.WEB)
public class AmountFormatter extends WebAction {
  

  @TestData(reference = "amount")
  private com.testsigma.sdk.TestData testAmount;

  @TestData(reference = "variable")
  private com.testsigma.sdk.TestData runtimeVar;
  
  @RunTimeData
  private com.testsigma.sdk.RunTimeData runTimeData;

  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    // Set the formatting to use commas
    numberFormat.setGroupingUsed(true);

    logger.info("Initiating execution");
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    try 
    {
    	
    	String amount = testAmount.getValue().toString();
        double number = numberFormat.parse(amount).doubleValue();

            // Format and display the number with commas
            String formattedAmount = numberFormat.format(number);



		runTimeData.setKey(runtimeVar.getValue().toString());
		runTimeData.setValue(formattedAmount);
       
       result = Result.SUCCESS;
       setSuccessMessage("Successfully generated the amount value  as " + formattedAmount + " and stored in runtime varibale " + runtimeVar.getValue().toString());
    }
    catch (Exception e) 
    {
        result = com.testsigma.sdk.Result.FAILED;
        setErrorMessage(errorMessage);
	} 
    return result;
  }
}