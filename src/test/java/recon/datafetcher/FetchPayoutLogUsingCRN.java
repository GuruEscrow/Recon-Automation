package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import simulator.resoucrse.URL;

public class FetchPayoutLogUsingCRN {

	@Test
	public void crnFetchFrom_PayoutLog_UsingUnresolvedStmts() throws ParseException {
		// Dates
		String startDate = "2025-04-27";

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

					if (!(payoutsMap.containsKey(crn))) {
						payoutsMap.put(crn, payout_status);
					} else {
						String allDuplicateStatus = payoutsMap.get(crn) + "_" + payout_status;
						payoutsMap.put(crn, allDuplicateStatus);
						// System.out.println(currentPayoutMapKey+" "+crn);
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		} else {
			System.out.println("File doesnot exits: --> path: " + payoutLogPath);
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
					String payout_status = payoutLog.get("payout_status").asText();

					if (!(previousPayoutsMap.containsKey(crn))) {
						previousPayoutsMap.put(crn, payout_status);
					} else {
						String allDuplicateStatus = previousPayoutsMap.get(crn) + "_" + payout_status;
						previousPayoutsMap.put(crn, allDuplicateStatus);
						// System.out.println(currentPayoutMapKey+" "+crn);
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		} else {
			System.out.println("File doesnot exits: --> path: " + previousDaypayoutLogPath);
		}

		/*
		 * Fetching the unresolved statements from the input date give Checking the
		 * payout logs by making the key of each statements key for DR statement (KEY:
		 * beneAccNumber_amount) key for upi DR statement (KEY: null_amount) key for CR
		 * statement (KEY: UTR)
		 */
		String[] orgTransactionIds = {
			    "DlZEaGsDCG9",
			    "DlZEaCoUCos",
			    "DlZEaCqXCpB",
			    "DlZEaCqXCpA",
			    "DlZEaCqgCpC",
			    "DlZEaCqyCpK",
			    "DlZEaCr4CpQ",
			    "DlZEaCrACpN",
			    "DlZEaCsdCpT",
			    "DlZEaCsoCpX",
			    "DlZEaCsoCpW",
			    "DlZEaCtXCpc",
			    "DlZEaCuYCpt",
			    "DlZEaCuYCpu",
			    "DlZEaCuqCp2",
			    "DlZEaCtdCpd",
			    "DlZEaCt8Cpl",
			    "DlZEaCuOCpq",
			    "DlZEaCwKc",
			    "DlZEaCwKd",
			    "DlZEaCwUe",
			    "DlZEaCwBa",
			    "DlZEaCxNo",
			    "DlZEaCxWq",
			    "DlZEaCxWr",
			    "DlZEaCwzk",
			    "DlZEaCxdu"
			};

		for (String crn : orgTransactionIds) {

			if (payoutsMap.containsKey(crn)) {
				System.out.println(crn + " " + payoutsMap.get(crn) + " CurrentDayPayout");
			} else if (previousPayoutsMap.containsKey(crn)) {
				System.out.println(crn + " " + previousPayoutsMap.get(crn) + " PreviousDay");
			} else {
				System.out.println(crn + " NotFound");
			}
		}

	}
}
