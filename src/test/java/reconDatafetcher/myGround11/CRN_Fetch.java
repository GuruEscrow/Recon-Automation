package reconDatafetcher.myGround11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import simulator.resoucrse.URL;

public class CRN_Fetch {
	
	private String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJlODdjZTAzMS0yZDZmLTQ0ZjAtYmY0Zi00ODg2Yjc2ZmIzMzIiLCJuYW1lIjpbImdldF9heGlzX2JhbmtfYWNjb3VudF9zdGF0ZW1lbnQiLCJnZXRfcGF5b3V0X2xvZ19lbnRyaWVzX2RhdGVfcmFuZ2UiLCJnZXRfdmFuX2NvbGxlY3RzX2VudHJpZXNfZGF0ZV9yYW5nZSIsImNoZWNrX2FuZF9wcm9jZXNzX3N0YXR1c193aXRoX2F4aXNfYmFuayIsImdldF9wYXlvdXRfbG9nX2VudHJpZXMiLCJjaGVja19zdGF0dXNfd2l0aF9heGlzX2JhbmsiLCJyZXZlcnRfcGF5b3V0c193aXRoX3V0ciJdLCJhdXRob3JpemVkX3BlcnNvbiI6eyJuYW1lIjoiQW51c3JlZSBWaW5vZCJ9LCJ0eXBlIjoic2VydmljZSIsImVudiI6ImxpdmUiLCJpYXQiOjE3MjY1NDgwMDd9.A6GWK1uflE2Qxx28vip5QsahM4nCsXLaueA_wL2Hc8s";

	private String baseUrl = "https://cas.myground11.co.in";
	  // Generic API trigger method
    public String triggerApi(String url, String requestBody) throws Exception {
    	HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .body(requestBody).asString();

            return response.getBody();
    }

    @Test
    public void testApiTriggerAndPrintFields() {
        try {
            String url = baseUrl+"/v1/service/check_status_with_rbl_bank";

			// All UTRs you provided
			String[] utrArray = { "511627044519", "511627027539", "511626027672", "511625055046", "511625055061",
					"511628027673", "511625055198", "511627027678", "511628027752", "511626027782", "511626027783",
					"511627027810", "511627027861", "511625055462", "511627027882", "511628027821", "511628027840",
					"511625055444", "511625055645", "511625055646", "511628027965", "511628027948", "511626028019",
					"511625055744", "511628028008", "511626028006", "511628028015" };

            for (String utr : utrArray) {
                // Create request body for each UTR dynamically
                // Define the request body
                String requestBody = "{\n" +
                        "    \"ledger_label\":\"MYGROUND11409002362954\",\n" +
                        "    \"utr_arr\": [\n" +
                        "        \""+utr+"\"\n" +
                        "    ]\n" +
                        "}";

                // Call API
                String responseBody = triggerApi(url, requestBody);

                // Extract and print fields
                JsonObject jsonParsedResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                
                JsonObject statusDetails = jsonParsedResponse.get("data").getAsJsonObject().get("utr").getAsJsonObject();

                String orgTransactionId = statusDetails.get("ORGTRANSACTIONID").getAsString();
                String paymentStatus = statusDetails.get("PAYMENTSTATUS").getAsString();
                String amount = statusDetails.get("AMOUNT").getAsString();

                
                
                /**
                 * Call payout status api
                 */
//                String payoutStatusUrl = baseUrl+"/v1/service/get_payout_log_entries";
//                
//                String payoutStatusRequest = "{\n" +
//                		"    \"well_formed_json\": true,\n" +
//                		"    \"crn_arr\": [\n" +
//                		"        \""+orgTransactionId+"\"\n" +
//                		"    ]\n" +
//                		"}";
//                
//                String payoutStatusResponse = triggerApi(payoutStatusUrl, payoutStatusRequest);
//                
//                JsonObject payoutLog = JsonParser.parseString(payoutStatusRequest).getAsJsonArray().get(0).getAsJsonObject();
//                
                /**
                 * Calling payout resolve api
                 */
                String payoutResolveUrl = baseUrl+"/v1/service/check_and_process_status_with_rbl_bank";
                
                String payoutResolveRequest = "{\n" +
                		"    \"ledger_label\": \"MYGROUND11409002362954\",\n" +
                		"    \"crn\": \""+orgTransactionId+"\"\n" +
                		"}";
                
                String payoutStatusResponse = triggerApi(payoutResolveUrl, payoutResolveRequest);
                
                System.out.println(utr +" "+orgTransactionId +" "+paymentStatus+" "+amount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}