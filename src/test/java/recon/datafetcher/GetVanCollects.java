package recon.datafetcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetVanCollects {
	
	static final String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI1ODc4MmIzMi1lMzM4LTQ0YjMtYWU3YS00YjQxOTE2Njg1YTUiLCJuYW1lIjpbImdldF9heGlzX2JhbmtfYWNjb3VudF9zdGF0ZW1lbnQiLCJnZXRfcGF5b3V0X2xvZ19lbnRyaWVzX2RhdGVfcmFuZ2UiLCJnZXRfdmFuX2NvbGxlY3RzX2VudHJpZXNfZGF0ZV9yYW5nZSIsImNoZWNrX2FuZF9wcm9jZXNzX3N0YXR1c193aXRoX2F4aXNfYmFuayIsImdldF9wYXlvdXRfbG9nX2VudHJpZXMiLCJjaGVja19zdGF0dXNfd2l0aF9heGlzX2JhbmsiXSwiYXV0aG9yaXplZF9wZXJzb24iOnsibmFtZSI6IkRpbmFrYXIgSmFpbiJ9LCJ0eXBlIjoic2VydmljZSIsImVudiI6ImxpdmUiLCJpYXQiOjE3MjIzNDQxNzh9.2lrLVLBQG8lZvAGrLJMpsS4OfO3hKfbfhN5gUoSJNEQ";
    static final String baseURL = "https://devaudit.eazypayouts.com/";
    
// -------------------------To fetch the vanCollects using api-------------------------
	public static Response fetchVanCollect(String startDate, String endDate) {

		// JSONObject outerBody = new JSONObject();
		JSONObject van_collect_body = new JSONObject();
		van_collect_body.put("start_timestamp", startDate);
		van_collect_body.put("end_timestamp", endDate);

		// It is the apikey for header and to access the api
        String URL = baseURL+"v1/service/get_van_collects_entries_date_range";

		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("apikey", apiKey).body(van_collect_body.toJSONString()).baseUri(URL).when().post().then().assertThat()
				.statusCode(200).extract().response();

		// .log().body() to print the request body which we sent this should hard coded
		// after baseUri

		return response;
	}

// ---------------------------To fetch the payout_log by using api-------------------
	public static Response fetchPayoutLog(String startDate, String endDate) {

		// JSONObject outerBody = new JSONObject();
		JSONObject payout_log_body = new JSONObject();
		payout_log_body.put("start_timestamp", startDate);
		payout_log_body.put("end_timestamp", endDate);

		// It is the apikey for header and to access the api
		String URL = baseURL+"v1/service/get_payout_log_entries_date_range";

		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("apikey", apiKey).body(payout_log_body.toJSONString()).baseUri(URL).when().post().then().assertThat()
				.statusCode(200).extract().response();

		// .log().body() to print the request body which we sent this should hard coded
		// after baseUri

		return response;
	}

	@Test
	public static void call() throws IOException {
		//Dates
		String startDate = "2024-08-18 00:00:00";
		
		//Formatting the start date and adding the plus 1 day for end date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(startDate, formatter);
		String endDate = date.plusDays(1).format(formatter);
	
		/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
		DateTimeFormatter yearFolNameFormat = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter monthFolNameFormat = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter dayFolNameFormat = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String year = "/" + date.format(yearFolNameFormat);
		String month = "/" + date.format(monthFolNameFormat);
		String day = "/" + date.format(dayFolNameFormat);
		String inFolder = "/in";
		
		// Checking and creating the folder directory
		String eodFolderPath = "D:/Phedora/Eazypayouts/get-axis-stmt/EOD" + year + month + day + inFolder;
		if (!(Files.exists(Paths.get(eodFolderPath)))) {
			Files.createDirectories(Paths.get(eodFolderPath));
		}
		eodFolderPath += "/";

		//File paths
		String van_co_fileName = eodFolderPath + "van_collects.json";
		String payout_log_fileName = eodFolderPath + "payout_log.json";
		
// ---------------------Calling the fetch van collect to fetch the data------------------
		Response vanCollectResponse = fetchVanCollect(startDate, endDate);

		// creating the InputStream from the vanCollectResponse
		InputStream inputStream = vanCollectResponse.asInputStream();
		// Defining the path where the file will be saved
		if (!(Files.exists(Paths.get(van_co_fileName)))) {
			File outputFile = new File(van_co_fileName);
		}

		// Writing the InputStream to a file in given path
		try (FileOutputStream fileOutputStream = new FileOutputStream(van_co_fileName)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
			System.out.println("Van collect File downloaded successfully: " + van_co_fileName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to download the van collect file.");
		}
// -----------------------------van_collects file downloaded successfully-------------------------------

		
// ---------------------Calling the fetch van collect to fetch the data------------------
		Response payout_response = fetchPayoutLog(startDate, endDate);

		// Creating the InputStream from the payout_response
		InputStream payout_inputStream = payout_response.asInputStream();
		// Defining the path where the file will be saved
		if (!(Files.exists(Paths.get(payout_log_fileName)))) {
			File payout_outputFile = new File(payout_log_fileName);
		}

		// Writing the InputStream to a file in given path
		try (FileOutputStream fileOutputStream = new FileOutputStream(payout_log_fileName)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = payout_inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
			System.out.println("Payout log File downloaded successfully: " + payout_log_fileName);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to download the payout log file.");
		}
// -----------------------------van_collects file downloaded successfully-------------------------------

	}

}
