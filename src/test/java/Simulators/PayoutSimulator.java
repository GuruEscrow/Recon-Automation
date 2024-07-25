package Simulators;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import DataBase.UpdateDataIntoDB;
import genricLibraries.PropertiesUtility;
import simulator.resoucrse.Encode_DecodeUsingBase64;
import simulator.resoucrse.PrivateKeyFileNameGetter;
import simulator.resoucrse.SignatureGenerator;
import simulator.resoucrse.URL;

public class PayoutSimulator {

	public static void impsPayout(String apiKey, String privateKey, String payout_Ref, double amount, String payoutMode,
			String acc_name, String acc_no, String ifsc_no, String txn_Note) throws Exception {
		PropertiesUtility property=new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);
		Map<String, Object> escrowData = new LinkedHashMap<>();
		
		Random random=new Random();
		int randomNum=random.nextInt(5);

		/*
		 * If randomNum = 1 the payout should failed and credit should happen with statement immediately after debit (done)
		 * If randomNum = 2 the payout should failed and credit should happen with statement next day (T+1)
		 * If randomNum = 3 the payout should failed and i.e debit, credit, debit should happen in same batch
		 * If randomNum = 4 the payout should failed and i.e debit, credit in same batch but again debit in next day
		 * If randomNum = 5 change the account number in beneficiary details while generating the statement 
		 * If randomNum = 6 change the IFSC number in beneficiary details while generating the statement
		 * If randomNum = 7 change the VPA in beneficiary details while generating the statement(UPI payment)
		 * If randomNum = 8 Statement should generate without uploading the payoutlog and payload
		 * If randomNum = 9 payload and payout should upload without generating the statement 
		 * If randomNum = 10 change the api key while uploading payload into file
		 * If randomNum = 11 Change the ledger label in payout log
		 * If randomNum = 12 Change the signature in payload and upload both payout log and statement
		 */
		

		// Fetching ledger using apiKey
		String ledger = Encode_DecodeUsingBase64.decodeLedger(apiKey);

		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String currentDate = date.format(formatter);
		Map<String, Object> payout = new LinkedHashMap<>();
		payout.put("payout_ref", payout_Ref);
		payout.put("amount", amount);
		payout.put("payout_mode", payoutMode);
		payout.put("transaction_note", txn_Note);

		Map<String, Object> payee = new LinkedHashMap<>();
		payee.put("user_ref", "test-user");
		payee.put("company_name", "guru softwares");
		payee.put("user_name", "guruprasad");
		payee.put("user_mobile_number", "8970486528");

		// Add payee to payout
		payout.put("payee", payee);

		Map<String, Object> beneficiary = new LinkedHashMap<>();
		beneficiary.put("account_name", acc_name);
		beneficiary.put("account_no", acc_no);
		beneficiary.put("ifsc_no", ifsc_no);

		// Add beneficiary to payout
		payout.put("beneficiary", beneficiary);

		JSONArray payoutArray = new JSONArray();
		payoutArray.add(payout);

		// Add payout to escrowData as array
		escrowData.put("payouts", payoutArray);

		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/M/yyyy, h:mm:ss a");
		escrowData.put("timestamp", date.format(formatter1));

		int privateKeyNum = (int) (Math.random() * 10) + 1;
		String FileName = PrivateKeyFileNameGetter.getFileName(privateKeyNum);

		// Generating signature using SHA256 algorithm and encoding in Base64 (json body
		// and private key required)
		Gson json = new Gson();
		String signature = SignatureGenerator.generateRSASignature(json.toJson(escrowData), privateKey);

		// Fetching the public certificate
		String publicCrtPath = "./PublicCrt/" + ledger + ".crt";

		// Verify signature with public certificate
		Map pubCrtDetails = Encode_DecodeUsingBase64.verifySignature(publicCrtPath, signature, json.toJson(escrowData));

		if ((Boolean) pubCrtDetails.get("signatureValid")) {

			escrowData.put("signature", signature);
			escrowData.put("escrow_id", "TransactionAccount");
			escrowData.put("payout_status", "pending_addition_to_payout_queue");
			escrowData.put("api_client_doc",
					ClientDetailsSimulator.detailsDoc(ledger, apiKey, (String) pubCrtDetails.get("prodPubCert")));

			DateTimeFormatter prefixDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String prefixDate = date.format(prefixDateFormatter);
			
			/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
			DateTimeFormatter yearFolNameFormat=DateTimeFormatter.ofPattern("yyyy");
			DateTimeFormatter monthFolNameFormat=DateTimeFormatter.ofPattern("yyyy_MM");
			DateTimeFormatter dayFolNameFormat=DateTimeFormatter.ofPattern("dd");
			DateTimeFormatter fileNameFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			String yearFolderPath=URL.AUTORECON_FOL_PATH+date.format(yearFolNameFormat);
			if(!(Files.exists(Paths.get(yearFolderPath)))) {
				Files.createDirectories(Paths.get(yearFolderPath));
			}
			yearFolderPath+="/";
			
			String monthFolderPath=yearFolderPath+date.format(monthFolNameFormat);
			if(!(Files.exists(Paths.get(monthFolderPath)))) {
				Files.createDirectories(Paths.get(monthFolderPath));
			}
			monthFolderPath+="/";
			
			String dayFolderPath=monthFolderPath+date.format(dayFolNameFormat);
			if(!(Files.exists(Paths.get(dayFolderPath)))) {
				Files.createDirectories(Paths.get(dayFolderPath));
			}
			dayFolderPath+="/";
			
			String payloadFilePath=dayFolderPath+date.format(fileNameFormat)+URL.PAYLOAD_FILE_NAME;
			if(!(Files.exists(Paths.get(payloadFilePath)))) {
				Files.createFile(Paths.get(payloadFilePath));
				property.writeToProperties("payloadPath", payloadFilePath, URL.PROPERTY_PATH);
			}
			
			String payoutLogFilePath=dayFolderPath+date.format(fileNameFormat)+URL.PAYOUT_LOG_FILE_NAME;
			if(!(Files.exists(Paths.get(payoutLogFilePath)))) {
				Files.createFile(Paths.get(payoutLogFilePath));
				property.writeToProperties("payoutLogPath", payoutLogFilePath, URL.PROPERTY_PATH);
			}
			
			String stmFilePath=dayFolderPath+date.format(fileNameFormat)+URL.BANK_STM_FILE_NAME;
			if(!(Files.exists(Paths.get(stmFilePath)))) {
				Files.createFile(Paths.get(stmFilePath));
				property.writeToProperties("bankStmPath", stmFilePath, URL.PROPERTY_PATH);
			}
			
			FileWriter SignedPayloadWriter = null;
			try {
				SignedPayloadWriter = new FileWriter(property.readData("payloadPath"), true);
				SignedPayloadWriter.write(prefixDate + ":[info]:" + json.toJson(escrowData));
				SignedPayloadWriter.write(System.lineSeparator());
			} catch (Exception e) {
				System.err.println("Provide correct signed payload file path: "+e.getMessage());
			} finally {
				if (SignedPayloadWriter != null)
					SignedPayloadWriter.close();
			}
			System.out.println("Signed payload uploaded");

			// Updating payoutlog by retrieving the signed payload
			ObjectMapper mapper = new ObjectMapper();
			JsonNode signedPayload = mapper.readTree(json.toJson(escrowData));
			JsonNode payoutsDetails = signedPayload.get("payouts").get(0);

			String ladgerlabel = signedPayload.get("api_client_doc").get("ledger_label").asText();
			String payoutRef = payoutsDetails.get("payout_ref").asText();
			double amt = payoutsDetails.get("amount").asDouble();
			String txnNote = payoutsDetails.get("transaction_note").asText();
			String accNo = payoutsDetails.get("beneficiary").get("account_no").asText();
			String ifsc = payoutsDetails.get("beneficiary").get("ifsc_no").asText();
			String accName = payoutsDetails.get("beneficiary").get("account_name").asText();
			String transCode = signedPayload.get("api_client_doc").get("tran_code").asText();
			String timeStamp = signedPayload.get("timestamp").asText();
			String payout_Mode = payoutsDetails.get("payout_mode").asText();

			PayoutLogSimulator.logThePayout(ladgerlabel, payoutRef, amt, txnNote, accNo, ifsc, accName, transCode,timeStamp,payout_Mode,randomNum);

		} else {
			System.err.println("Invalid Signature");
		}

	}

//	// converting the LinkedHashMap to JSON object
//	private static String convertToJson(Map<String, Object> payload) {
//		// For simplicity, converting to a string manually
//		StringBuilder jsonBuilder = new StringBuilder("{");
//		
//		for (Map.Entry<String, Object> entry : payload.entrySet()) {
//			jsonBuilder.append("\"").append(entry.getKey()).append("\":");
//			if (entry.getValue() instanceof String) {
//				jsonBuilder.append("\"").append(entry.getValue()).append("\",");
//			} else {
//				jsonBuilder.append(entry.getValue()).append(",");
//			}
//		}
//		// Remove the trailing comma if there are entries
//		if (payload.size() > 0) {
//			jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
//		}
//
//		jsonBuilder.append("}");
//
//		return jsonBuilder.toString();
//	}
}
