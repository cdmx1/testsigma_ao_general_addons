
/* Change Logs: General task | Function to upload a file using robot | 16/11/23 */


package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import lombok.Data;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "Upload file using a file path Testdata",
        description = "This addon will upload the file using a file path",
        applicationType = ApplicationType.WEB)
public class UploadFile extends WebAction {
	
  @TestData(reference = "Testdata")
  private com.testsigma.sdk.TestData testdata;

  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    logger.info("Initiating execution");
    com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
    String filePath = testdata.getValue().toString();

    try {
    		uploadFile(filePath);
    	    result = Result.SUCCESS;
    		setSuccessMessage("Successfully uploaded file from path" + filePath);
    
    } catch (AWTException | IOException | InterruptedException e) {
    	result = Result.FAILED;
    	setErrorMessage("Failed to upload file");
    }
  return result;

    // your custom code ends here
}

public static void uploadFile(String filePath) throws AWTException, IOException, InterruptedException {
    

    StringSelection ss = new StringSelection(filePath);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(ss, null);

    Robot robot = new Robot();
    robot.delay(1000);

    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);

    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
	}
}