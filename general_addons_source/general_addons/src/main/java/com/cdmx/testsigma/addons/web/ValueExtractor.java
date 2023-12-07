/* Change Logs: AUTO-860 | This addon will extract the value from an object and store in a variable based on the value entered | 01/12/23 */


package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Action(actionText = "Get the corresponding value from the Two Dimensional array based on the given user-value and store in a runtime variable",
        description = "This addon will extract the value from an object and store in a variable based on the value entered",
        applicationType = ApplicationType.WEB)
public class ValueExtractor extends WebAction {

    @TestData(reference = "array")
    private com.testsigma.sdk.TestData testArray;

    @TestData(reference = "user-value")
    private com.testsigma.sdk.TestData testValue;

    @TestData(reference = "variable")
    private com.testsigma.sdk.TestData runtimeVar;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        logger.info("Initiating execution");
        System.out.println("Hello");
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        try {





            // Extract key-value pairs into a map
            Map<String, Object> dataMap = new HashMap<>();

            // Regular expression to match key-value pairs in the format: "key": value
            Pattern pattern = Pattern.compile("\"(\\w+)\": (\\d*\\.?\\d+)");
            Matcher matcher = pattern.matcher(testArray.getValue().toString());

            while (matcher.find()) {
                String key = matcher.group(1);
                String valueString = matcher.group(2);

                // Convert the value to Double and put it in the map
                dataMap.put(key, Double.parseDouble(valueString));
            }



            

            // Retrieve user-provided value
            String targetValue = testValue.getValue().toString();

            // Extract and store the corresponding value
            Object targetValueObject = dataMap.get(targetValue);
            if (targetValueObject == null) {
                throw new Exception("Target value not found in the array.");
            }

            // Handle different data types before storing in runtime variable
            String targetNumericValue;
            if (targetValueObject instanceof String) {
                targetNumericValue = (String) targetValueObject;
            } else if (targetValueObject instanceof Double) {
                targetNumericValue = String.valueOf((Double) targetValueObject);
            } else {
                throw new Exception("Unexpected data type found in the array.");
            }

            runTimeData.setKey(runtimeVar.getValue().toString());
            runTimeData.setValue(targetNumericValue);

            result = Result.SUCCESS;
            setSuccessMessage("Successfully generated the amount value as " + targetNumericValue + " and stored in runtime variable " + runtimeVar.getValue().toString());
        } catch (Exception e) {
            result = com.testsigma.sdk.Result.FAILED;
            setErrorMessage(e.getMessage());
        }
        return result;
    }
}