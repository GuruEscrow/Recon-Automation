package recon.datafetcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import simulator.resoucrse.URL;

public class FetchingUnresolvedStmsForThatDay {

	@Test
	public void getUnresolvedStmsUTR() throws ParseException {

		// Dates
		String startDate = "2024-11-23";

		// Formatting the start date and adding the plus 1 day for end date
		SimpleDateFormat pstDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(startDate);
		// String endDate = date.plusDays(1).format(formatter);

		/*----------------------------------------------Creating Folder and files if not exists----------------------------------------------*/
		SimpleDateFormat yearFolNameFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFolNameFormat = new SimpleDateFormat("MM");
		SimpleDateFormat dayFolNameFormat = new SimpleDateFormat("dd");
		SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd");

		String year = "/" + yearFolNameFormat.format(date);
		String month = "/" + monthFolNameFormat.format(date);
		String day = "/" + dayFolNameFormat.format(date);
		String inFolder = "/in";
		String outFolder = "/out/";

		// ---------------Fetching statements with missing filed File details into
		// unresolvedStmMap

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

					String lookingPstDate = pstDateFormat.format(date);
					if (pstDate.contains(lookingPstDate)) {
						System.out.println(utr);
					}

				}
				reader.close();
			} catch (Exception e) {
				System.err.println(
						"Error: at FileInputStream or readLine while Fetchin unresolvedStm file " + e.getMessage());
			}
		}
	}

}
