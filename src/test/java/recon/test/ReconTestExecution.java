package recon.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import simulator.resoucrse.URL;

public class ReconTestExecution {

	// Declared the starting date of recon according to test case or testing
	static String fileDate;

	// Declaring the maps to store the file entries
	static Map<String, Object> brsMap; // Key is UTR
	static Map<String, Object> cowostm; // Key is UTR
	static Map<String, Object> fpwmore2stmMap; // Key is UTR
	static Map<String, Object> invalidBDMap; // Key is UTR
	static Map<String, Object> invalidSMap; // Key is payout_ref
	static Map<String, Object> ppwmore1stmMap; // Key is UTR
	static Map<String, Object> payloadWpayoutMap; // Key is payout_ref
	static Map<String, Object> payloadWSMap; // Key is payout_ref
	static Map<String, Object> payoutWCMap; // Key is UTR
	static Map<String, Object> payoutWDMap; // Key is UTR
	static Map<String, Object> powoplMap; // Key is UTR
	static Map<String, Object> payoutWSMap; // Key is payout_ref
	static Map<String, Object> stmWmfMap; // Key is Serial Number
	static Map<String, Object> unresolvedStmMap; // Key is UTR

//	-----------------To read the files according to date and storing all the details of file separately in Map---------------
	public static void retriveDataFromOutFiles() {

		// Parsing the date to required format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate date = LocalDate.parse(fileDate, format);

		// Creating the formats to create a path to files
		DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd");
		// Storing the folder names in string with mentioned format
		String year = date.format(yearFormat) + "/";
		String month = date.format(monthFormat) + "/";
		String day = date.format(dayFormat) + "/out/";

		// Universal path or folder path
		String path = URL.EOD_PATH + year + month + day;

		// ---------------Fetching brs file and store it in brsMap-------------------
		brsMap = new LinkedHashMap<String, Object>();
		String brsPath = path + URL.brsComplete;
		if (Files.exists(Paths.get(brsPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(brsPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String brsPayload = null;
				while ((brsPayload = reader.readLine()) != null) {
					int startIndex = brsPayload.indexOf("{");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonStm_Payload = mapper.readTree(brsPayload.substring(startIndex));
					JsonNode jsonStm = jsonStm_Payload.get("stmt");
					String utr = jsonStm.get("utrNumber").asText();

					brsMap.put(utr, brsPayload.substring(startIndex));
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin brs file " + e.getMessage());
			}
		}

		// --------------Fetching collects wo statement details File and storing in
		// cowostm
		cowostm = new LinkedHashMap<>();
		String cowostmPath = path + URL.cowostatement;
		if (Files.exists(Paths.get(cowostmPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(cowostmPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String collectDetails = null;
				while ((collectDetails = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode colDtlJson = mapper.readTree(collectDetails);
					String utr = colDtlJson.get("utr_number").asText();
					cowostm.put(utr, collectDetails);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin collect withou statement file "
						+ e.getMessage());
			}

		}

		// --------------Fetching failed payout with more than 2 statement and storing
		// fpwduMap (need to edit)
		fpwmore2stmMap = new LinkedHashMap<>();
		String fpwmore2stmPath = path + URL.fpwmore2stm;
		if (Files.exists(Paths.get(fpwmore2stmPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(fpwmore2stmPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String fpwmore2stms = null;
				while ((fpwmore2stms = reader.readLine()) != null) {
					ObjectMapper mapper=new ObjectMapper();
					JsonNode payoutWithStms=mapper.readTree(fpwmore2stms);
					
					String utr = payoutWithStms.get("payout").get("utr").asText();
					String payout_ref = payoutWithStms.get("payout").get("payout_ref").asText();
					fpwmore2stmMap.put(utr, fpwmore2stms);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin fpwmore2stm file " + e.getMessage());
			}

		}

		// ---------------------Fetching invalid bene details file and storing in
		// invalidBDMap
		invalidBDMap = new LinkedHashMap<>();
		String inbdPath = path + URL.invalidBeneDetails;
		if (Files.exists(Paths.get(inbdPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(inbdPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String invalidBeneD = null;
				while ((invalidBeneD = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					String[] array = mapper.readValue(invalidBeneD, String[].class);
					String utr = array[0];
					invalidBDMap.put(utr, invalidBeneD);

				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin invalid bene details file "
						+ e.getMessage());
			}

		}

		// ---------------------Fetching invalid signature details file and storing in
		// invalidSMap
		invalidSMap = new LinkedHashMap<>();
		String insPath = path + URL.invalidSignature;
		if (Files.exists(Paths.get(insPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(insPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String insPayload = null;
				while ((insPayload = reader.readLine()) != null) {
					int startIndex = insPayload.indexOf("{");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonPayload = mapper.readTree(insPayload.substring(startIndex));
					ArrayNode arrayNode = (ArrayNode) jsonPayload.get("payouts");
					JsonNode payoutDetails = arrayNode.get(0);
					String payout_ref = payoutDetails.get("payout_ref").asText();
					invalidSMap.put(payout_ref, String.valueOf(jsonPayload));
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin invalid signature details file "
						+ e.getMessage());
			}

		}

		// -----------Fetching passed payout with more than 1 statement details file and
		// storing (need to edit)
		// in ppwduMap
		ppwmore1stmMap = new LinkedHashMap<>();
		String ppwmore1stmPath = path + URL.ppwmore1stm;
		if (Files.exists(Paths.get(ppwmore1stmPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(ppwmore1stmPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String ppwmore1Stms = null;
				while ((ppwmore1Stms = reader.readLine()) != null) {
					ObjectMapper mapper=new ObjectMapper();
					JsonNode payoutWithStms=mapper.readTree(ppwmore1Stms);
					
					String utr = payoutWithStms.get("payout").get("utr").asText();
					String payout_ref = payoutWithStms.get("payout").get("payout_ref").asText();
					ppwmore1stmMap.put(utr, ppwmore1Stms);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin ppwdu file " + e.getMessage());
			}

		}

		// --------------Fetching payload without payout details file and storing in
		// payloadWpayoutMap
		payloadWpayoutMap = new LinkedHashMap<String, Object>();
		String pwpPath = path + URL.payloadWoPayout;
		if (Files.exists(Paths.get(pwpPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(pwpPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String pwpPayload = null;
				while ((pwpPayload = reader.readLine()) != null) {
					int startIndex = pwpPayload.indexOf("{");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonPayload = mapper.readTree(pwpPayload.substring(startIndex));
					ArrayNode arrayNode = (ArrayNode) jsonPayload.get("payouts");
					JsonNode payoutDetails = arrayNode.get(0);
					String payout_ref = payoutDetails.get("payout_ref").asText();
					payloadWpayoutMap.put(payout_ref, pwpPayload);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin pwp file " + e.getMessage());
			}
		}

		// --------------Fetching payload without statement details file and storing in
		// payloadWSMap
		payloadWSMap = new LinkedHashMap<String, Object>();
		String pwsPath = path + URL.payloadWoStatement;
		if (Files.exists(Paths.get(pwsPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(pwsPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String pwsPayload = null;
				while ((pwsPayload = reader.readLine()) != null) {
					int startIndex = pwsPayload.indexOf("{");
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonPayload = mapper.readTree(pwsPayload.substring(startIndex));
					ArrayNode arrayNode = (ArrayNode) jsonPayload.get("payouts");
					JsonNode payoutDetails = arrayNode.get(0);
					String payout_ref = payoutDetails.get("payout_ref").asText();
					payloadWSMap.put(payout_ref, pwsPayload);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin pws file " + e.getMessage());
			}
		}

		// -------------Fetching payout without credit details File and storing into
		// payoutWCMap
		payoutWCMap = new LinkedHashMap<>();
		String pwcPath = path + URL.payoutWoCredit;
		if (Files.exists(Paths.get(pwcPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(pwcPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String pwcStms = null;
				while ((pwcStms = reader.readLine()) != null) {
					String utr = pwcStms.substring(0, pwcStms.indexOf(":"));
					String stmArray = pwcStms.substring(pwcStms.indexOf(":") + 1).trim();
					payoutWCMap.put(utr, stmArray);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin pwc file " + e.getMessage());
			}

		}

		// --------------Fetching payout without debit details File and storing into
		// payoutWDMap
		payoutWDMap = new LinkedHashMap<>();
		String pwdPath = path + URL.payoutWoDebit;
		if (Files.exists(Paths.get(pwdPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(pwdPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String pwdStms = null;
				while ((pwdStms = reader.readLine()) != null) {
					String utr = pwdStms.substring(0, pwdStms.indexOf(":"));
					String stmArray = pwdStms.substring(pwdStms.indexOf(":") + 1).trim();
					payoutWDMap.put(utr, stmArray);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin pwd file " + e.getMessage());
			}

		}

		// ------------Fetching payout_wo_payload details File and storing into
		// powoplMap
		powoplMap = new LinkedHashMap<>();
		String powoplPath = path + URL.payoutWoPayload;
		if (Files.exists(Paths.get(powoplPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(powoplPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String powoplDetails = null;
				while ((powoplDetails = reader.readLine()) != null) {
					int index = powoplDetails.indexOf("{");
					ObjectMapper mapper=new ObjectMapper();
					JsonNode payoutLog=mapper.readTree(powoplDetails.substring(index));
					String utr = payoutLog.get("utr").asText();
					powoplMap.put(utr, powoplDetails.substring(index));
				}
				reader.close();
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetching powopl file " + e.getMessage());
			}

		}

		// ------------Fetching payout wo statement details File and storing into
		// payoutWSMap
		payoutWSMap = new LinkedHashMap<>();
		String payoutwsPath = path + URL.payoutWoStm;
		if (Files.exists(Paths.get(payoutwsPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payoutwsPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payoutwsPayoutlog = null;
				while ((payoutwsPayoutlog = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payoutwsPayoutlog);
					String utr = payoutLog.get("utr").asText();
					String payout_ref = payoutLog.get("payout_ref").asText();

					payoutWSMap.put(payout_ref, payoutwsPayoutlog);
				}
				reader.close();
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		}

		// ---------------Fetching statements with missing filed File details into
		// stmWmfMap
		stmWmfMap = new LinkedHashMap<>();
		String stmWmfPath = path + URL.stmWithMissingFields;
		if (Files.exists(Paths.get(stmWmfPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(stmWmfPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String stmWithoutUtr = null;
				while ((stmWithoutUtr = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(stmWithoutUtr);

					String internalRefNum = payoutLog.get("internalReferenceNumber").asText();
					String serial_num = payoutLog.get("serialNumber").asText();

					stmWmfMap.put(serial_num, stmWithoutUtr);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin stmWmf file " + e.getMessage());
			}
		}

		// ---------------Fetching statements with missing filed File details into
		// unresolvedStmMap
		unresolvedStmMap = new LinkedHashMap<>();
		String unresolvedStmPath = path + URL.unresolvedStm;
		if (Files.exists(Paths.get(unresolvedStmPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(unresolvedStmPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String unresovedStm = null;
				while ((unresovedStm = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(unresovedStm);

					String utr = payoutLog.get("utrNumber").asText();
					String serial_num = payoutLog.get("serialNumber").asText();

					unresolvedStmMap.put(utr, unresovedStm);
				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		}

	}
	// ----------------------All file data is uploaded into Map and Array---------------------------------------------
	
	
	// ---------------------Asserting the result by using the entry count in each file according to date
	public static void validateEntryCount() {

		// Calling the data retriever method
		retriveDataFromOutFiles();

		// Declaring the variable to assign the entry count in each file according to
		// it's date
		int brsDataCount = 0;
		int collectWoStmDataCount = 0;
		int fpwmore2stmDataCount = 0;
		int invalidDBDataCount = 0;
		int invalidSDataCount = 0;
		int ppwmore1stmDataCount = 0;
		int payloadWpayoutDataCount = 0;
		int payloadWSDataCount = 0;
		int payoutWCDataCount = 0;
		int payoutWoplDataCount = 0;
		int payoutWDDataCount = 0;
		int payoutWSDataCount = 0;
		int stmWmfDataCount = 0;
		int unresolvedStmDataCount = 0;

		// This will assign the count of each file according to a date
		switch (fileDate) {

		case "2024/06/28":
			brsDataCount = 2;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 3;
			payloadWSDataCount = 19;
			payoutWCDataCount = 0;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 23;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 0;
			break;

		case "2024/06/29":
			brsDataCount = 0;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 19;
			payoutWCDataCount = 0;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 23;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 0;
			break;

		case "2024/06/30":
			brsDataCount = 0;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 19;
			payoutWCDataCount = 0;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 23;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 0;
//			Testing data
//			brsDataCount = 2;
//			collectWoStmDataCount = 0;
//			fpwmore2stmDataCount = 1;
//			invalidDBDataCount = 3;
//			invalidSDataCount = 1;
//			ppwmore1stmDataCount = 2;
//			payloadWpayoutDataCount = 3;
//			payloadWSDataCount = 11;
//			payoutWCDataCount = 2;
//			payoutWDDataCount = 1;
//			payoutWoplDataCount = 1;
//			payoutWSDataCount = 15;
//			stmWmfDataCount = 1;
//			unresolvedStmDataCount = 8;
			break;

		case "2024/07/01":
			brsDataCount = 1;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 19;
			payoutWCDataCount = 0;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 1;
			payoutWSDataCount = 22;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 0;
			break;

		case "2024/07/02":
			brsDataCount = 9;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 1;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 2;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 11;
			payoutWCDataCount = 7;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 1;
			payoutWSDataCount = 13;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 2;
			break;

		case "2024/07/03":
			brsDataCount = 8;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 3;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 1;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 4;
			payoutWCDataCount = 7;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 1;
			payoutWSDataCount = 5;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 2;
			break;

		case "2024/07/04":
			brsDataCount = 3;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 3;
			payoutWCDataCount = 5;
			payoutWDDataCount = 0;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 4;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 3;
			break;

		case "2024/07/05":
			brsDataCount = 5;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 1;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 1;
			payloadWSDataCount = 4;
			payoutWCDataCount = 7;
			payoutWDDataCount = 1;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 5;
			stmWmfDataCount = 1;
			unresolvedStmDataCount = 3;
			break;
		
		case "2024/07/06":
			brsDataCount = 10;
			collectWoStmDataCount = 1;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 1;
			invalidSDataCount = 1;
			ppwmore1stmDataCount = 1;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 4;
			payoutWCDataCount = 9;
			payoutWDDataCount = 1;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 5;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 4;
			break;
			
		case "2024/07/07":
			brsDataCount = 3;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 4;
			payoutWCDataCount = 9;
			payoutWDDataCount = 1;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 5;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 5;
			break;
			
		case "2024/07/08":
			brsDataCount = 0;
			collectWoStmDataCount = 0;
			fpwmore2stmDataCount = 0;
			invalidDBDataCount = 0;
			invalidSDataCount = 0;
			ppwmore1stmDataCount = 0;
			payloadWpayoutDataCount = 0;
			payloadWSDataCount = 4;
			payoutWCDataCount = 9;
			payoutWDDataCount = 1;
			payoutWoplDataCount = 0;
			payoutWSDataCount = 5;
			stmWmfDataCount = 0;
			unresolvedStmDataCount = 5;
			break;
		}
		


		// Validating the actual value with expected one using assertion
		SoftAssert validate = new SoftAssert();

		validate.assertEquals(brsMap.size(), brsDataCount, "BRS file count not matching on " + fileDate);
		validate.assertEquals(cowostm.size(), collectWoStmDataCount,
				"Collect without statement count not matching on " + fileDate);
		validate.assertEquals(fpwmore2stmMap.size(), fpwmore2stmDataCount,
				"fpwithmorethan2stm file count not matching on " + fileDate);
		validate.assertEquals(invalidBDMap.size(), invalidDBDataCount,
				"invalidBD file count not matching on " + fileDate);
		validate.assertEquals(invalidSMap.size(), invalidSDataCount,
				"invalidSignature file count not matching on " + fileDate);
		validate.assertEquals(ppwmore1stmMap.size(), ppwmore1stmDataCount,
				"ppwithmorethan1stm file count not matching on " + fileDate);
		validate.assertEquals(payloadWpayoutMap.size(), payloadWpayoutDataCount,
				"payloadWpayout file count not matching on " + fileDate);
		validate.assertEquals(payloadWSMap.size(), payloadWSDataCount,
				"payloadWS file count not matching on " + fileDate);
		validate.assertEquals(payoutWCMap.size(), payoutWCDataCount, "payoutWC file count not matching on " + fileDate);
		validate.assertEquals(payoutWDMap.size(), payoutWDDataCount, "payoutWD file count not matching on " + fileDate);
		validate.assertEquals(powoplMap.size(), payoutWoplDataCount, "payout without payload file count not matching on " + fileDate);
		validate.assertEquals(payoutWSMap.size(), payoutWSDataCount, "payoutWS file count not matching on " + fileDate);
		validate.assertEquals(stmWmfMap.size(), stmWmfDataCount, "stmWmf file count not matching on " + fileDate);
		validate.assertEquals(unresolvedStmMap.size(), unresolvedStmDataCount,
				"unresolvedStm file count not matching on " + fileDate);
		//System.out.println(invalidBDMap);

		System.out.println("brs_complete count: "+brsMap.size()); // 2
		System.out.println("collect_wo_statement: "+cowostm.size()); // 0
		System.out.println("failed_payout_with_more_than_2_statement: "+fpwmore2stmMap.size()); // 1
		System.out.println("invalid_bene_details: "+invalidBDMap.size()); // 3
		System.out.println("invalid_signature: "+invalidSMap.size()); // 1
		System.out.println("passed_payout_more_than_1_stm: "+ppwmore1stmMap.size()); // 2
		System.out.println("payload_wo_payout: "+payloadWpayoutMap.size()); // 3
		System.out.println("payload_wo_stm: "+payloadWSMap.size()); // 11
		System.out.println("payout_wo_credit: "+payoutWCMap.size()); // 2
		System.out.println("payout_wo_debit: "+payoutWDMap.size()); // 1
		System.out.println("payout_wo_payload: "+powoplMap.size()); // 1
		System.out.println("payout_wo_stm: "+payoutWSMap.size()); // 15
		System.out.println("statement_with_missing_field: "+stmWmfMap.size()); // 1
		System.out.println("unresolved_stm: "+unresolvedStmMap.size()); // 8

		validate.assertAll();
	}
	// The files entries count which doesn't match it with expected shows the Assert Exception
	
	@Test
	public static void execute() {
		
		TestCaseBatch_1.batch1();
		TestCaseBatch_2.batch2();
		TestCaseBatch_3.batch3();
		TestCaseBatch_4.batch4();
		TestCaseBatch_5.batch5();
		TestCaseBatch_6.batch6();
		TestCaseBatch_7.batch7();
		TestCaseBatch_8.batch8();
		TestCaseBatch_9.batch9();
		TestCaseBatch_10.batch10();
		TestCaseBatch_11.batch11();
		
	}

}
