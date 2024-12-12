package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import genricLibraries.ExcelUtility;
import genricLibraries.UtilitiesPath;
import simulator.resoucrse.URL;

public class CRN_Fetch {

	static Map<String, Object> payout_logMap; // Key is payout_ref
	static Map<String, String> bankTxnReport;

	/*
	 * Fetching the unresolved statement for the give day. For Dr statements
	 * fetching CRN from that day payout log(only "PROCCESSED" payouts) based on
	 * amount and bene account number For Cr statements fetching CRN from previous
	 * day payout log based on UTR
	 */
	@Test
	public void crnFetchFrom_PayoutLog_UsingUnresolvedStmts() throws ParseException {
		// Dates
		String startDate = "2024-12-07";

		// Formatting the start date and adding the plus 1 day for end date
		DateTimeFormatter pstDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(startDate, formatter);
		// String endDate = date.plusDays(1).format(formatter);

		/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
		DateTimeFormatter yearFolNameFormat = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter monthFolNameFormat = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter dayFolNameFormat = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter fileNameFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String year = "/" + yearFolNameFormat.format(date);
		String month = "/" + monthFolNameFormat.format(date);
		String day = "/" + dayFolNameFormat.format(date);
		String inFolder = "/in/";
		String outFolder = "/out/";

		// -------------------Fetching Current day payouts log and storing in the map
		String payoutLogPath = URL.EOD_PATH + year + month + day + inFolder + URL.payoutLog;
		Map<String, String> payoutsMap = new LinkedHashMap<String, String>();
		if (Files.exists(Paths.get(payoutLogPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(payoutLogPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payout = null;
				while ((payout = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payout);

					String utr = payoutLog.get("utr").asText();
					String amt = payoutLog.get("amount").asText();
					String beneAccNo = payoutLog.get("bene_ac_no").asText();
					String crn = payoutLog.get("crn").asText();
					String payout_status = payoutLog.get("payout_status").asText();

					if (utr.equals("null")||payout_status.equals("processing")) {
						System.out.println(crn);
						payoutsMap.put(beneAccNo + "_" + amt, crn);
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		}

		// -------------------Fetching Previous day payouts log and storing in the map
		LocalDate privousDay = date.minusDays(1);
		String prvYear = "/" + privousDay.format(yearFolNameFormat);
		String prvMonth = "/" + privousDay.format(monthFolNameFormat);
		String prvday = "/" + privousDay.format(dayFolNameFormat);
		String previousDaypayoutLogPath = URL.EOD_PATH + prvYear + prvMonth + prvday + inFolder + URL.payoutLog;
		Map<String, String> previousPayoutsMap = new LinkedHashMap<String, String>();
		if (Files.exists(Paths.get(previousDaypayoutLogPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(previousDaypayoutLogPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String payout = null;
				while ((payout = reader.readLine()) != null) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode payoutLog = mapper.readTree(payout);

					String utr = payoutLog.get("utr").asText();
					String amt = payoutLog.get("amount").asText();
					String beneAccNo = payoutLog.get("bene_ac_no").asText();
					String crn = payoutLog.get("crn").asText();

					previousPayoutsMap.put(utr, crn);

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		}

		// ---------------Fetching statements with missing filed File details into
		// unresolvedStmMap
		// System.out.println(payoutsMap);
		System.out.println("Payouts loaded to Map");
		String unresolvedStmPath = URL.EOD_PATH + year + month + day + outFolder + URL.unresolvedStm;
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
					String pstDate = payoutLog.get("pstdDate").asText();
					String beneAccNo = payoutLog.get("benficiaryAccountNumber").asText();
					String drCr = payoutLog.get("drcr").asText();
					String amt = payoutLog.get("amount").asText();
					String payoutMode = payoutLog.get("paymentMode").asText();

					String lookingPstDate = pstDateFormat.format(date);
					if (pstDate.contains(lookingPstDate)) {
						if (drCr.equals("DR")) {
							String searchString = beneAccNo + "_" + amt;
							if (payoutsMap.containsKey(searchString)) {
								System.out.println(utr + " DR " + payoutsMap.get(searchString));
							} else {
								if (payoutMode.equals("UPI")) {
									if (payoutsMap.containsKey("null_" + amt)) {
										System.out.println(utr + " DR " + payoutsMap.get("null_" + amt));
									} else {
										System.out.println(utr + " DR " + "UPI_No_payoutlog");
									}
								} else {
									System.out.println(utr + " DR " + "No_payoutlog");
								}
							}
						} else {
							if (previousPayoutsMap.containsKey(utr)) {
								System.out.println(utr + " CR " + previousPayoutsMap.get(utr));
							} else {
								System.out.println(utr + " CR " + "No_payoutlog");
							}
						}
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		}
	}

//	Fetching the bank statement from the bank transaction report by upload the UTR for INPUT in excel file
	@Test
	public static void fetch_crn_from_txnreport_for_bankstmts() {

		ExcelUtility excel = new ExcelUtility();
		excel.excelInit(UtilitiesPath.EXCEL_PATH);

		bankTxnReport = excel.readDataFromExcel("TxnReport");
		System.out.println(bankTxnReport.size());

		for (int i = 0; i < excel.getLastRowNum("CRN fetch"); i++) {

			String utr = excel.readDataFromExcel("CRN fetch", i, 0);
			System.out.println(utr);
			if (utr.equals("")) {
				break;
			}

			if (bankTxnReport.containsKey(utr)) {
				String crn = bankTxnReport.get(utr);
				excel.writeToExcel("CRN fetch", i, 1, crn, UtilitiesPath.EXCEL_PATH);
			} else {
				excel.writeToExcel("CRN fetch", i, 1, "No payout log", UtilitiesPath.EXCEL_PATH);
			}
		}

		excel.closeExcel();

	}

//	Extracting the CRN from payoutWoCredit or from payout log
	@Test
	public static void extract_crn_from_payoutwocredti() {

		Map payoutWDMap = new LinkedHashMap<>();
		String pwdPath = "C:/BRS/EOD/2024/09/20/out/payout_wo_debit.json";
		if (Files.exists(Paths.get(pwdPath))) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(pwdPath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String pwdStms = null;
				while ((pwdStms = reader.readLine()) != null) {
					String json = pwdStms.substring(pwdStms.indexOf("{"));
					String properJson = json.substring(0, json.indexOf(":[info]:"));

					// System.out.println(properJson);
					JsonObject object = JsonParser.parseString(properJson).getAsJsonObject();
					System.out.println(object.get("crn").getAsString());
				}
				reader.close();
			} catch (Exception e) {
				System.err.println("Error: at FileInputStream or readLine while Fetchin pwd file " + e.getMessage());
			}

		}
	}

}
