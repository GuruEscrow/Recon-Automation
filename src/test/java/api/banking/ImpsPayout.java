package api.banking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import DataBase.FetchDataFromDB;
import Simulators.PayoutSimulator;
import genricLibraries.PropertiesUtility;
import simulator.resoucrse.Encode_DecodeUsingBase64;
import simulator.resoucrse.PrivateKeyFileNameGetter;
import simulator.resoucrse.URL;

public class ImpsPayout {
	String apiKey;
	String privateKey;
	String payoutRef;
	int amt;
	String payoutMode;
	String bene_acc_name;
	String bene_acc_no;
	String bene_ifsc;
	String txnNote;

	public ImpsPayout() {

		int privateKeyNum = (int) (Math.random() * 10) + 1;
		String FileName = PrivateKeyFileNameGetter.getFileName(privateKeyNum);

		PropertiesUtility property = new PropertiesUtility();
		property.propertiesInit(URL.PROPERTY_PATH);
		int payoutCount = Integer.parseInt(property.readData("payoutRefCount"));
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String currentDate = date.format(formatter);

		try {
			privateKey = Files.readString(Paths.get("./PrivateKey/" + FileName + ".key"));
		} catch (IOException e) {
			System.err.println("Mention a correct private key file path: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			apiKey = Files.readString(Paths.get("./ApiKey/" + FileName + ".key"));
		} catch (IOException e) {
			System.err.println("Mention a correct api key file path: " + e.getMessage());
		}

		payoutRef = currentDate + "GuruRecoPayTest" + payoutCount;
		property.writeToProperties("payoutRefCount", String.valueOf(++payoutCount), URL.PROPERTY_PATH);
		
		Random random=new Random();
		amt = random.nextInt(10)+1;
		payoutMode = "IMPS";
		bene_acc_name = "Guruprasad";
		bene_acc_no = "89704856";
		bene_ifsc = "ABCD0123456";
		txnNote = "Guruprasad";
	}

	public static void main(String[] args) throws Exception {
		
		//for(int i=1;i<=10;i++){
			ImpsPayout payout = new ImpsPayout();

			// Fetching ledger using apiKeys
	        String ledger = Encode_DecodeUsingBase64.decodeLedger(payout.apiKey);
			
			if (payout.amt <= FetchDataFromDB.getAvlBlc(ledger)) {
				PayoutSimulator.impsPayout(payout.apiKey, payout.privateKey, payout.payoutRef, payout.amt, payout.payoutMode,
						payout.bene_acc_name, payout.bene_acc_no, payout.bene_ifsc, payout.txnNote);
			} else {
	           System.out.println("Insufficent Balance please load money!");
			}
           //TimeUnit.SECONDS.sleep(30);
		//}
		
	}

}
