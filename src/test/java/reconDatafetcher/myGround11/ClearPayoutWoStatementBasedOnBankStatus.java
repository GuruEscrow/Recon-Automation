package reconDatafetcher.myGround11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ClearPayoutWoStatementBasedOnBankStatus {
	
	//API credentials
	private String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJlODdjZTAzMS0yZDZmLTQ0ZjAtYmY0Zi00ODg2Yjc2ZmIzMzIiLCJuYW1lIjpbImdldF9heGlzX2JhbmtfYWNjb3VudF9zdGF0ZW1lbnQiLCJnZXRfcGF5b3V0X2xvZ19lbnRyaWVzX2RhdGVfcmFuZ2UiLCJnZXRfdmFuX2NvbGxlY3RzX2VudHJpZXNfZGF0ZV9yYW5nZSIsImNoZWNrX2FuZF9wcm9jZXNzX3N0YXR1c193aXRoX2F4aXNfYmFuayIsImdldF9wYXlvdXRfbG9nX2VudHJpZXMiLCJjaGVja19zdGF0dXNfd2l0aF9heGlzX2JhbmsiLCJyZXZlcnRfcGF5b3V0c193aXRoX3V0ciJdLCJhdXRob3JpemVkX3BlcnNvbiI6eyJuYW1lIjoiQW51c3JlZSBWaW5vZCJ9LCJ0eXBlIjoic2VydmljZSIsImVudiI6ImxpdmUiLCJpYXQiOjE3MjY1NDgwMDd9.A6GWK1uflE2Qxx28vip5QsahM4nCsXLaueA_wL2Hc8s";

	private String baseUrl = "https://cas.myground11.co.in";

	//Input file
	private String processedPayouts = "D:/BRS/proccessed_po_wo_statements.json";
	private String processingPayouts = "D:/BRS/processing_po_wo_statements.json";
	
	@Test
	public void clearProcessedPayouts() {
		
		//Processing the processedPayouts without statement
		if (Files.exists(Paths.get(processedPayouts))) {
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream(processedPayouts);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String processedPayoutLog = null;
				while ((processedPayoutLog = reader.readLine()) != null) {
					
					JsonObject parsedPayoutLog = JsonParser.parseString(processedPayoutLog).getAsJsonObject();
					String crn = parsedPayoutLog.get("crn").getAsString();
					
					String bankStatusresponse = checkBankStatus_using_crn(crn);
					JsonObject parsedBankStatus = JsonParser.parseString(bankStatusresponse).getAsJsonObject();
					String bankStatus = parsedBankStatus.get("data").getAsJsonObject().get("PAYMENTSTATUS").getAsString();
					
					String resolveAPIResponse = resolvePayoutStatusAccordingToBankStatus(crn);
					
					String payoutLogResponse = getPayoutLogEntry_using_crn(crn);
					
					JsonObject payoutLog = JsonParser.parseString(payoutLogResponse).getAsJsonArray().get(0).getAsJsonObject();
					 
					String payoutLogStatus = payoutLog.get("payout_status").getAsString();
					
					if(bankStatus.equals("7")&&payoutLogStatus.equals("processed")) {
						System.out.println(crn+" Cleared_With_Success");
					}else if(bankStatus.equals("8")&&payoutLogStatus.equals("failed")){
						System.out.println(crn+" Cleared_With_Failed");
					}else if(bankStatus.equals("9")) {
						System.out.println(crn+" Pending_Deemed");
					}else {
						System.out.println(crn+" Manualresolution");
					}
					
				}
				
				System.out.println("***************Processed Payout cleared***************");
				clearInProgressPayouts();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Test
	public void clearInProgressPayouts() {

		// Processing the processedPayouts without statement
		if (Files.exists(Paths.get(processingPayouts))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(processingPayouts);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String processedPayoutLog = null;
				while ((processedPayoutLog = reader.readLine()) != null) {

					JsonObject parsedPayoutLog = JsonParser.parseString(processedPayoutLog).getAsJsonObject();
					String crn = parsedPayoutLog.get("crn").getAsString();

					String bankStatusresponse = checkBankStatus_using_crn(crn);
					JsonObject parsedBankStatus = JsonParser.parseString(bankStatusresponse).getAsJsonObject();
					String bankStatus = parsedBankStatus.get("data").getAsJsonObject().get("PAYMENTSTATUS")
							.getAsString();

					String resolveAPIResponse = resolvePayoutStatusAccordingToBankStatus(crn);

					String payoutLogResponse = getPayoutLogEntry_using_crn(crn);

					JsonObject payoutLog = JsonParser.parseString(payoutLogResponse).getAsJsonArray().get(0)
							.getAsJsonObject();

					String payoutLogStatus = payoutLog.get("payout_status").getAsString();

					if (bankStatus.equals("7") && payoutLogStatus.equals("processed")) {
						System.out.println(crn + " Cleared_With_Success");
					} else if (bankStatus.equals("8") && payoutLogStatus.equals("failed")) {
						System.out.println(crn + " Cleared_With_Failed");
					} else if (bankStatus.equals("9")) {
						System.out.println(crn + " Pending_Deemed");
					} else {
						System.out.println(crn + " Manualresolution");
					}

				}
				System.out.println("***************InProgress Payout cleared***************");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * This method used to check the bank status using crn
	 * @throws Exception 
	 */
	public String checkBankStatus_using_crn(String crn) throws Exception {
        String payoutResolveUrl = baseUrl+"/v1/service/check_status_with_rbl_bank";
        
        String payoutResolveRequest = "{\n" +
        		"    \"ledger_label\": \"MYGROUND11409002362954\",\n" +
        		"    \"crn\": \""+crn+"\"\n" +
        		"}";
        
        String payoutStatusResponse = triggerApi(payoutResolveUrl, payoutResolveRequest);
        
        return payoutStatusResponse;
	}
	
	/**
	 * This method used to resolve the status in the payout log
	 * @throws Exception 
	 */
	public String resolvePayoutStatusAccordingToBankStatus(String crn) throws Exception {
		
		   /**
         * Calling payout resolve api
         */
        String payoutResolveUrl = baseUrl+"/v1/service/check_and_process_status_with_rbl_bank";
        
        String payoutResolveRequest = "{\n" +
        		"    \"ledger_label\": \"MYGROUND11409002362954\",\n" +
        		"    \"crn\": \""+crn+"\"\n" +
        		"}";
        
        String resolveResponse = triggerApi(payoutResolveUrl, payoutResolveRequest);
        
        return resolveResponse;
	}
	
	/**
	 * This below method used to get PayoutLog
	 * @throws Exception 
	 */
	public String getPayoutLogEntry_using_crn(String crn) throws Exception {
		   String payoutStatusUrl = baseUrl+"/v1/service/get_payout_log_entries";
           
           String payoutStatusRequest = "{\n" +
           		"    \"well_formed_json\": true,\n" +
           		"    \"crn_arr\": [\n" +
           		"        \""+crn+"\"\n" +
           		"    ]\n" +
           		"}";
           
           String payoutLogResponse = triggerApi(payoutStatusUrl, payoutStatusRequest);
           
          return payoutLogResponse;
	}
	
	
	/**
	 * This below method used to do API call
	 */
	  // Generic API trigger method
    public String triggerApi(String url, String requestBody) throws Exception {
    	HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .body(requestBody).asString();

            return response.getBody();
    }
}
