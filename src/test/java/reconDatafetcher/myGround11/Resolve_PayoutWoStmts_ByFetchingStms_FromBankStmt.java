package reconDatafetcher.myGround11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Resolve_PayoutWoStmts_ByFetchingStms_FromBankStmt {

//	Separating the processed payouts in payout without statments
	@Test
	public void seprateProcessedPayoutFromPayoutWoStmts() throws IOException {

		String payoutwsPath = "C:/BRS/EOD/2025/04/27/out/payout_wo_statement.json";
		String payloadwsPath = "C:/BRS/EOD/2025/04/27/out/payload_wo_statement.json";

		String failedPayouts = "D:/BRS/failed_po_wo_statements.json";
		String processedPayouts = "D:/BRS/proccessed_po_wo_statements.json";
		String failedPayloads = "D:/BRS/failed_payload_wo_statements.json";
		String processedPayloads = "D:/BRS/proccessed_payload_wo_statements.json";
		String payloadsWoPayout = "D:/BRS/payload_wo_payout.json";
		String processingPayouts = "D:/BRS/processing_po_wo_statements.json";
		String processingPayloads = "D:/BRS/processing_payload_wo_statements.json";

		if (!(Files.exists(Path.of(failedPayouts)))) {
			try {
				Files.createFile(Path.of(failedPayouts));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!(Files.exists(Path.of(processedPayouts)))) {
			try {
				Files.createFile(Path.of(processedPayouts));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!(Files.exists(Path.of(failedPayloads)))) {
			try {
				Files.createFile(Path.of(failedPayloads));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!(Files.exists(Path.of(processedPayloads)))) {
			try {
				Files.createFile(Path.of(processedPayloads));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!(Files.exists(Path.of(payloadsWoPayout)))) {
			try {
				Files.createFile(Path.of(payloadsWoPayout));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!(Files.exists(Path.of(processingPayouts)))) {
			try {
				Files.createFile(Path.of(processingPayouts));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!(Files.exists(Path.of(processingPayloads)))) {
			try {
				Files.createFile(Path.of(processingPayloads));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Truncating the file before writing it
		Files.newBufferedWriter(Path.of(failedPayouts), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(processedPayouts), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(failedPayloads), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(processedPayloads), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(payloadsWoPayout), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(processingPayouts), StandardOpenOption.TRUNCATE_EXISTING);
		Files.newBufferedWriter(Path.of(processingPayloads), StandardOpenOption.TRUNCATE_EXISTING);

		Map<String, Object> payoutWSMap = new LinkedHashMap<String, Object>();
		ArrayList<String> failedPayoutref = new ArrayList<String>();
		ArrayList<String> proccessedPayoutref = new ArrayList<String>();
		ArrayList<String> processingPayoutref = new ArrayList<String>();
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
					String payout_status = payoutLog.get("payout_status").asText();

					payoutWSMap.put(payout_ref, payoutwsPayoutlog);
					if (payout_status.equals("failed")) {
						FileWriter write = new FileWriter(failedPayouts, true);
						failedPayoutref.add(payout_ref);
						
						write.write(payoutwsPayoutlog);
						write.write(System.lineSeparator());
						write.close();
					} else if(payout_status.equals("processed")){
						FileWriter write = new FileWriter(processedPayouts, true);
						proccessedPayoutref.add(payout_ref);
						
						write.write(payoutwsPayoutlog);
						write.write(System.lineSeparator());
						write.close();
					} else {
						FileWriter write = new FileWriter(processingPayouts, true);
						processingPayoutref.add(payout_ref);
						
						write.write(payoutwsPayoutlog);
						write.write(System.lineSeparator());
						write.close();
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		}

		// Payload sepration
		ArrayList<String> payloadPayoutref = new ArrayList<String>();
		ArrayList<String> duplicatePayloadPayoutref = new ArrayList<String>();
		if (Files.exists(Paths.get(payloadwsPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payloadwsPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payloadWSlog = null;
				while ((payloadWSlog = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payload = mapper.readTree(payloadWSlog);
					ArrayNode arrayNode = (ArrayNode)payload.get("payouts");
					JsonNode payoutBlock = arrayNode.get(0);
					
					String payoutRef = payoutBlock.get("payout_ref").asText();
                    if(payloadPayoutref.contains(payoutRef)) {
                    	duplicatePayloadPayoutref.add(payoutRef);
                    }else {
                    	payloadPayoutref.add(payoutRef);
                    }
					
					if (failedPayoutref.contains(payoutRef)) {
						FileWriter write = new FileWriter(failedPayloads, true);

						write.write(payloadWSlog);
						write.write(System.lineSeparator());
						write.close();
					} else if(proccessedPayoutref.contains(payoutRef)){
						FileWriter write = new FileWriter(processedPayloads, true);

						write.write(payloadWSlog);
						write.write(System.lineSeparator());
						write.close();
					} else if(processingPayoutref.contains(payoutRef)){
						FileWriter write = new FileWriter(processingPayloads, true);

						write.write(payloadWSlog);
						write.write(System.lineSeparator());
						write.close();
						
					}else {
				
						FileWriter write = new FileWriter(payloadsWoPayout, true);

						write.write(payloadWSlog);
						write.write(System.lineSeparator());
						write.close();
					}
				}
				reader.close();
//				System.out.println(duplicatePayloadPayoutref);
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		}

	}

//	Fetching the Statements for the payouts without statements
	@Test
	public void fetchStmts_forProccessedPayoutWoStmts() {

		// Converting the excel format statements to json format file and Contains key
		// as UTR and Value as Json format statement
		Map<String, String> stmsInJson = convertStmJson();
		System.out.println(stmsInJson.size());

		// File paths
		String payoutsFilePath = "D:/payout_wo_statement.json";
		String stmsFilePath = "D:/stms.json";
		String stmsNotFound = "D:/stms_not_found.json";

		// Creating if stmts file is not present
		if (!Files.exists(Path.of(stmsFilePath))) {
			try {
				Files.createFile(Path.of(stmsFilePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Creating if stms_not_found file is not presents
		if (!Files.exists(Path.of(stmsNotFound))) {
			try {
				Files.createFile(Path.of(stmsNotFound));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Checking the stms for payouts
		if (Files.exists(Paths.get(payoutsFilePath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payoutsFilePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				long stmtNum = 1;
				String payoutwsPayoutlog = null;
				while ((payoutwsPayoutlog = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payoutwsPayoutlog);
					String utr = payoutLog.get("utr").asText();
					String payout_ref = payoutLog.get("payout_ref").asText();
					String payout_status = payoutLog.get("payout_status").asText();
					String payout_amount = payoutLog.get("amount").asText();

					if (stmsInJson.containsKey(utr)) {
						String stms = stmsInJson.get(utr);
						ObjectMapper map = new ObjectMapper();
						JsonNode jsonStms = mapper.readTree(stms);
						String stm_amount = payoutLog.get("amount").asText();

						JsonObject responseObj = JsonParser.parseString(stmsInJson.get(utr)).getAsJsonObject();
						responseObj.addProperty("serialNumber", stmtNum);
						stmtNum++;

						if (payout_amount.equals(stm_amount)) {
							try {
								FileWriter writeString = new FileWriter(stmsFilePath, true);
								writeString.write(responseObj.toString());
								writeString.write(System.lineSeparator());
								writeString.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							System.out.println(payoutwsPayoutlog);
						}
					} else {
						try {
							FileWriter writeString = new FileWriter(stmsNotFound, true);
							writeString.write(payoutwsPayoutlog);
							writeString.write(System.lineSeparator());
							writeString.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				reader.close();
			} catch (Exception e) {
				System.err
						.println("Error: at FileInputStream or readLine while Fetchin payoutws file " + e.getMessage());
			}
		}
	}

	public Map<String, String> convertStmJson() {

		Map<String, String> map = new HashMap<String, String>();
		String csvFile = "D:/stms.csv";
		String line = "";
		String cvsSplitBy = ",";

		long stmtNum = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			long rowNum = 1;
			while ((line = br.readLine()) != null) {
				String tran_date = null;
				String chq_no = null;
				String particulars = null;
				String dr_amount = null;
				String cr_amount = null;
				String mode = null;

				// Storing the particular row values accordingly to column name
				int columnNum = 1;
				String[] values = line.split(cvsSplitBy);
				for (String value : values) {

					if (rowNum != 1) {

						if (columnNum == 1) {
							tran_date = value;
						}
						if (columnNum == 2) {
							chq_no = value;
						}
						if (columnNum == 3) {
							particulars = value;
						}
						if (columnNum == 4) {
							dr_amount = value;
						}
						if (columnNum == 5) {
							cr_amount = value;
						}

					}
					columnNum++;
				}

				if (rowNum != 1) {
					// Extract the UTR from particulars
					String utr = null;
					String[] particulars_array = particulars.split("/");

					if (particulars_array[0].equals("IMPS")) {
						// 2 index is the UTR in imps
						utr = particulars_array[2];
						mode = "IMPS";
					} else if (particulars_array[0].equals("NEFT")) {
						// 1st index is the UTR in NEFT
						utr = particulars_array[1];
						mode = "NEFT";
					} else if (particulars_array[0].equals("IFT")) {
						// 1st index is the UTR in IFT
						utr = particulars_array[1];
						mode = "IFT";
					} else if (particulars_array[0].equals("RTGS")) {
						// 1st index is the UTR in RTGS
						utr = particulars_array[1];
						mode = "RTGS";
					} else if (particulars_array[0].equals("UPI")) {
						utr = particulars_array[2];
						mode = "UPI";
					} else if (particulars_array[0].equals("UPIP2PPAY")) {
						utr = particulars_array[2];
						mode = "UPIP2PPAY";
					} else {
						System.out.println(Arrays.toString(particulars_array));
					}

					// If UTR is not null creating the statement into json format and updating into
					// map
					if (utr != null) {
						ObjectMapper mapper = new ObjectMapper();

						Map<String, Object> stmJsonMap = new LinkedHashMap<String, Object>();
						stmJsonMap.put("serialNumber", stmtNum);
						stmJsonMap.put("transactionDate", tran_date);
						stmJsonMap.put("pstdDate", "");
						stmJsonMap.put("particulars", particulars);
						stmJsonMap.put("chqNumber", " ");
						stmJsonMap.put("valueDate", " ");

						if (!dr_amount.equals(" ")) {
							stmJsonMap.put("amount", dr_amount);
							stmJsonMap.put("drcr", "DR");
						}
						if (!cr_amount.equals(" ")) {
							stmJsonMap.put("amount", cr_amount);
							stmJsonMap.put("drcr", "CR");
						}

						stmJsonMap.put("balance", " ");
						stmJsonMap.put("paymentMode", mode);
						stmJsonMap.put("utrNumber", utr);

						stmJsonMap.put("internalReferenceNumber", " ");
						stmJsonMap.put("remittingBranch", " ");
						stmJsonMap.put("remittingBankName", " ");
						stmJsonMap.put("remittingAccountNumber", " ");
						stmJsonMap.put("remittingAccountName", " ");
						stmJsonMap.put("remittingIFSC", " ");
						stmJsonMap.put("benficiaryBranch", " ");
						stmJsonMap.put("benficiaryName", " ");
						stmJsonMap.put("benficiaryAccountNumber", " ");
						stmJsonMap.put("benficiaryIFSC", " ");
						stmJsonMap.put("channel", mode);
						stmJsonMap.put("timeStamp", " ");
						stmJsonMap.put("remarks", " ");
						stmJsonMap.put("transactionCurrencyCode", " ");

						String stmJsonString = mapper.writeValueAsString(stmJsonMap);
						stmtNum++;
						map.put(utr, stmJsonString);
					}
				}

//				System.out.println(map);
				rowNum++;
			}
			if (br != null)
				br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}
//	Fetching the Statements for the payouts without statements
}
