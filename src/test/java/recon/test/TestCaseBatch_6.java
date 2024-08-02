package recon.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestCaseBatch_6 extends ReconTestExecution {

	@Test(priority = 6, dependsOnMethods = "batch5")
	public static void batch6() {
		SoftAssert validatation = new SoftAssert();

		fileDate = "2024/07/03";
		System.out.println(fileDate + " test results");
		validateEntryCount();

		// -------------Test case in depth validating i.e check the each txn in
		// respective files based on key (utr or payout_ref)

		/* TC-1, TC-7, TC-10, TC-13, TC-26, TC-28, TC-30 */
		String tc_1_utr = "418010292157";
		String tc_1_payoutref = "20240628104705GuruLiveImps156";
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_1_utr), "The txn (" + tc_1_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_1_utr), "The txn (" + tc_1_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_1_utr), "The txn (" + tc_1_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_1_payoutref),
				"The txn (" + tc_1_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_1_payoutref), "The txn (" + tc_1_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_1_payoutref),
				"The txn (" + tc_1_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_1_payoutref),
				"The txn (" + tc_1_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_1_utr), "The txn (" + tc_1_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_1_utr),
				"The txn (" + tc_1_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-2, TC-32 */
		String tc_2_utr = "418060636054";
		String tc_2_payoutref = "20240628111840GuruLiveUPI068";
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_2_utr), "The txn (" + tc_2_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_2_utr), "The txn (" + tc_2_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_2_utr), "The txn (" + tc_2_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_2_payoutref), "The txn (" + tc_2_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_2_payoutref),
				"The txn (" + tc_2_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_2_payoutref),
				"The txn (" + tc_2_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_2_utr), "The txn (" + tc_2_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_2_utr),
				"The txn (" + tc_2_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-3 */
		String tc_3_utr = "418318534644";
		String tc_3_payoutref = "20240701184240GuruLiveImps157";
		// valid
		validatation.assertTrue(unresolvedStmMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is not present in unresolvedStms which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_3_utr), "The txn (" + tc_3_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_3_payoutref),
				"The txn (" + tc_3_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_3_payoutref), "The txn (" + tc_3_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_3_utr), "The txn (" + tc_3_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_3_utr), "The txn (" + tc_3_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_3_payoutref),
				"The txn (" + tc_3_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_3_utr),
				"The txn (" + tc_3_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_3_utr), "The txn (" + tc_3_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);

		/* TC-4 */
		String tc_4_utr = "418411294735";
		String tc_4_payoutref = "20240702110814GuruLiveImps158";
		// valid
		validatation.assertTrue(payloadWSMap.containsKey(tc_4_payoutref),
				"The txn (" + tc_4_payoutref + ") is not present in payloadWoStm which should be there on " + fileDate);
		validatation.assertTrue(payoutWSMap.containsKey(tc_4_payoutref),
				"The txn (" + tc_4_payoutref + ") is not present in payoutWoStm which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_4_utr), "The txn (" + tc_4_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_4_utr), "The txn (" + tc_4_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_4_utr), "The txn (" + tc_4_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_payoutref + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_4_payoutref),
				"The txn (" + tc_4_utr + ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_4_utr), "The txn (" + tc_4_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_4_utr),
				"The txn (" + tc_4_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-5 */
		String tc_5_utr = "418495698099";
		String tc_5_payoutref = "20240702112004GuruLiveUPI069";
		// valid
		validatation.assertTrue(payloadWSMap.containsKey(tc_5_payoutref),
				"The txn (" + tc_5_payoutref + ") is not present in payloadWoStm which should be there on " + fileDate);
		validatation.assertTrue(payoutWSMap.containsKey(tc_5_payoutref),
				"The txn (" + tc_5_payoutref + ") is not present in payoutWoStm which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_5_utr), "The txn (" + tc_5_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_5_utr), "The txn (" + tc_5_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_5_utr), "The txn (" + tc_5_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_5_payoutref), "The txn (" + tc_5_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_5_utr), "The txn (" + tc_5_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_5_utr),
				"The txn (" + tc_5_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-6 */
		String tc_6_utr = "418318534644";
		String tc_6_payoutref = "";
		// valid
		validatation.assertTrue(unresolvedStmMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is not present in unresolvedStms which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_6_utr), "The txn (" + tc_6_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_6_utr), "The txn (" + tc_6_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_6_utr), "The txn (" + tc_6_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_6_payoutref), "The txn (" + tc_6_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_6_utr),
				"The txn (" + tc_6_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_6_utr), "The txn (" + tc_6_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_6_payoutref),
				"The txn (" + tc_6_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_6_payoutref),
				"The txn (" + tc_6_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);

		/* TC-8, TC-16 */
		String tc_8_utr = "418411492943";
		String tc_8_payoutref = "20240702114451GuruLiveImps159";
		// valid
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_8_utr), "The txn (" + tc_8_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_8_payoutref),
				"The txn (" + tc_8_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_8_payoutref),
				"The txn (" + tc_8_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_8_utr), "The txn (" + tc_8_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_8_utr), "The txn (" + tc_8_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_8_payoutref), "The txn (" + tc_8_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_8_utr), "The txn (" + tc_8_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_8_utr),
				"The txn (" + tc_8_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-9 */
		String tc_9_utr = "418412694971";
		String tc_9_payoutref = "20240702122011GuruLiveImps160";
		// valid
		validatation.assertTrue(payoutWCMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_9_utr), "The txn (" + tc_9_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_9_payoutref),
				"The txn (" + tc_9_payoutref + ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_9_payoutref),
				"The txn (" + tc_9_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_9_utr), "The txn (" + tc_9_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_9_utr), "The txn (" + tc_9_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_9_payoutref), "The txn (" + tc_9_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_9_utr), "The txn (" + tc_9_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_9_utr),
				"The txn (" + tc_9_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-11 */
		String tc_11_utr = "418413948429";
		String tc_11_payoutref = "20240702130310GuruLiveImps161";
		// valid
		// Invalid
		validatation.assertFalse(powoplMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(brsMap.containsKey(tc_11_utr), "The txn (" + tc_11_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_11_payoutref),
				"The txn (" + tc_11_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_11_payoutref), "The txn (" + tc_11_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_11_utr), "The txn (" + tc_11_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_11_utr), "The txn (" + tc_11_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_11_payoutref), "The txn (" + tc_11_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_11_utr), "The txn (" + tc_11_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_11_utr),
				"The txn (" + tc_11_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-12 */
		String tc_12_utr = "418413047835";
		String tc_12_payoutref = "20240702132024GuruLiveImps162";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_12_utr), "The txn (" + tc_12_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		validatation.assertTrue(powoplMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is not present in payoutWoPayload which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payoutWSMap.containsKey(tc_12_payoutref),
				"The txn (" + tc_12_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_12_payoutref), "The txn (" + tc_12_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_12_utr), "The txn (" + tc_12_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_12_utr), "The txn (" + tc_12_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_12_payoutref), "The txn (" + tc_12_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_12_utr), "The txn (" + tc_12_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_12_utr),
				"The txn (" + tc_12_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-14 */
		String tc_14_utr = "418414593036";
		String tc_14_payoutref = "20240702145931GuruLiveImps163";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_14_utr), "The txn (" + tc_14_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_14_payoutref), "The txn (" + tc_14_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_14_payoutref),
				"The txn (" + tc_14_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_14_utr), "The txn (" + tc_14_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_14_utr), "The txn (" + tc_14_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_14_payoutref), "The txn (" + tc_14_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_14_utr), "The txn (" + tc_14_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_14_utr),
				"The txn (" + tc_14_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-15 */
		String tc_15_utr = "418415710539";
		String tc_15_payoutref = "20240702151958GuruLiveImps164";
		// valid
		validatation.assertTrue(payloadWSMap.containsKey(tc_15_payoutref), "The txn (" + tc_15_payoutref
				+ ") is not present in payloadWoStm which should be there on " + fileDate);
		validatation.assertTrue(payoutWSMap.containsKey(tc_15_payoutref),
				"The txn (" + tc_15_payoutref + ") is not present in payoutWoStm which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_15_utr), "The txn (" + tc_15_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_15_utr), "The txn (" + tc_15_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_15_utr), "The txn (" + tc_15_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_15_payoutref), "The txn (" + tc_15_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_15_utr), "The txn (" + tc_15_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_15_utr),
				"The txn (" + tc_15_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-17 */
		String tc_17_utr = "418415753757";
		String tc_17_payoutref = "20240702152757GuruLiveImps165";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_17_utr), "The txn (" + tc_17_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_17_payoutref), "The txn (" + tc_17_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_17_payoutref), "The txn (" + tc_17_payoutref
				+ ") is not present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_17_utr), "The txn (" + tc_17_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_17_utr), "The txn (" + tc_17_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_17_payoutref), "The txn (" + tc_17_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_17_utr), "The txn (" + tc_17_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_17_utr),
				"The txn (" + tc_17_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-18 */
		String tc_18_utr = "418415897422";
		String tc_18_payoutref = "20240702155416GuruLiveImps166";
		// valid
		validatation.assertTrue(payoutWCMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_18_utr), "The txn (" + tc_18_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_18_payoutref), "The txn (" + tc_18_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_18_payoutref), "The txn (" + tc_18_payoutref
				+ ") is not present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_18_utr), "The txn (" + tc_18_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_18_utr), "The txn (" + tc_18_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_18_payoutref), "The txn (" + tc_18_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_18_utr), "The txn (" + tc_18_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_18_utr),
				"The txn (" + tc_18_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-19 */
		String tc_19_utr = "418416998078";
		String tc_19_payoutref = "20240702161244GuruLiveImps167";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_19_utr), "The txn (" + tc_19_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		validatation.assertTrue(payoutWCMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_19_payoutref), "The txn (" + tc_19_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_19_payoutref),
				"The txn (" + tc_19_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_19_utr), "The txn (" + tc_19_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_19_utr), "The txn (" + tc_19_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_19_payoutref), "The txn (" + tc_19_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_19_utr), "The txn (" + tc_19_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_19_utr),
				"The txn (" + tc_19_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-20 */
		String tc_20_utr = "418416109506";
		String tc_20_payoutref = "20240702163247GuruLiveImps168";
		// valid
		validatation.assertTrue(payoutWCMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_20_utr), "The txn (" + tc_20_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_20_payoutref), "The txn (" + tc_20_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_20_payoutref),
				"The txn (" + tc_20_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_20_utr), "The txn (" + tc_20_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_20_utr), "The txn (" + tc_20_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_20_payoutref), "The txn (" + tc_20_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_20_utr), "The txn (" + tc_20_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_20_utr),
				"The txn (" + tc_20_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-21 */
		String tc_21_utr = "418416222778";
		String tc_21_payoutref = "20240702165316GuruLiveImps169";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_21_utr), "The txn (" + tc_21_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_21_payoutref), "The txn (" + tc_21_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_21_payoutref),
				"The txn (" + tc_21_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_21_utr), "The txn (" + tc_21_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_21_utr), "The txn (" + tc_21_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_21_payoutref), "The txn (" + tc_21_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_21_utr), "The txn (" + tc_21_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_21_utr),
				"The txn (" + tc_21_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-22 */
		String tc_22_utr = "418418660067";
		String tc_22_payoutref = "20240702180711GuruLiveImps170";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_22_utr), "The txn (" + tc_22_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_22_payoutref), "The txn (" + tc_22_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_22_payoutref),
				"The txn (" + tc_22_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_22_utr), "The txn (" + tc_22_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_22_utr), "The txn (" + tc_22_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_22_payoutref), "The txn (" + tc_22_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_22_utr), "The txn (" + tc_22_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_22_utr),
				"The txn (" + tc_22_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-23 */
		String tc_23_utr = "418418727807";
		String tc_23_payoutref = "20240702181818GuruLiveImps171";
		// valid
		validatation.assertTrue(payoutWCMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_23_utr), "The txn (" + tc_23_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_23_payoutref), "The txn (" + tc_23_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_23_payoutref),
				"The txn (" + tc_23_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_23_utr), "The txn (" + tc_23_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_23_utr), "The txn (" + tc_23_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is not present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_23_payoutref), "The txn (" + tc_23_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_23_utr), "The txn (" + tc_23_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_23_utr),
				"The txn (" + tc_23_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-24 */
		String tc_24_utr = "";
		String tc_24_payoutref = "20240702184023GuruLiveImps172";
		// valid
		validatation.assertTrue(payloadWSMap.containsKey(tc_24_payoutref), "The txn (" + tc_24_payoutref
				+ ") is not present in payloadWoStm which should be there on " + fileDate);
		validatation.assertTrue(payoutWSMap.containsKey(tc_24_payoutref),
				"The txn (" + tc_24_payoutref + ") is not present in payoutWoStm which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_24_utr), "The txn (" + tc_24_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_24_utr), "The txn (" + tc_24_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_24_utr), "The txn (" + tc_24_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_24_payoutref), "The txn (" + tc_24_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_24_utr), "The txn (" + tc_24_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_24_utr),
				"The txn (" + tc_24_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-25 */
		String tc_25_utr = "418418972949";
		String tc_25_payoutref = "20240702185516GuruLiveImps173";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_25_utr), "The txn (" + tc_25_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		validatation.assertTrue(payoutWCMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		validatation.assertTrue(ppwmore1stmMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is not present in ppwmore1stm which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_25_payoutref), "The txn (" + tc_25_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_25_payoutref),
				"The txn (" + tc_25_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_25_utr), "The txn (" + tc_25_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_25_utr), "The txn (" + tc_25_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_25_payoutref), "The txn (" + tc_25_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_25_utr), "The txn (" + tc_25_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_25_utr),
				"The txn (" + tc_25_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-27 */
		String tc_27_utr = "418510310393";
		String tc_27_payoutref = "20240703104547GuruLiveImps174";
		// valid
		validatation.assertTrue(invalidBDMap.containsKey(tc_27_utr), "The txn (" + tc_27_utr
				+ ") is not present in invalid bene details which should be there on " + fileDate);
		validatation.assertTrue(payoutWCMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is not present in payoutWoCredit which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_27_payoutref), "The txn (" + tc_27_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_27_payoutref),
				"The txn (" + tc_27_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(brsMap.containsKey(tc_27_utr), "The txn (" + tc_27_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_27_utr), "The txn (" + tc_27_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_27_payoutref), "The txn (" + tc_27_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_27_utr), "The txn (" + tc_27_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_27_utr),
				"The txn (" + tc_27_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-29 */
		String tc_29_utr = "418511397426";
		String tc_29_payoutref = "20240703110210GuruLiveImps175";
		// valid
		validatation.assertTrue(invalidBDMap.containsKey(tc_29_utr), "The txn (" + tc_29_utr
				+ ") is not present in invalid bene details which should not be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_29_payoutref), "The txn (" + tc_29_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_29_payoutref),
				"The txn (" + tc_29_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(brsMap.containsKey(tc_29_utr), "The txn (" + tc_29_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_29_utr), "The txn (" + tc_29_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_29_payoutref), "The txn (" + tc_29_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_29_utr), "The txn (" + tc_29_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_29_utr),
				"The txn (" + tc_29_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-31 */
		String tc_31_utr = "418511440038";
		String tc_31_payoutref = "20240703110950GuruLiveImps176";
		// valid
		validatation.assertTrue(invalidBDMap.containsKey(tc_31_utr), "The txn (" + tc_31_utr
				+ ") is not present in invalid bene details which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_31_payoutref), "The txn (" + tc_31_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_31_payoutref),
				"The txn (" + tc_31_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(brsMap.containsKey(tc_31_utr), "The txn (" + tc_31_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_31_utr), "The txn (" + tc_31_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_31_payoutref), "The txn (" + tc_31_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_31_utr), "The txn (" + tc_31_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_31_utr),
				"The txn (" + tc_31_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-33 */
		String tc_33_utr = "418514879410";
		String tc_33_payoutref = "20240703111943GuruLiveUPI072";
		// valid
		validatation.assertTrue(brsMap.containsKey(tc_33_utr), "The txn (" + tc_33_utr
				+ ") is not present in the brs_complete file which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(payloadWSMap.containsKey(tc_33_payoutref), "The txn (" + tc_33_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_33_payoutref),
				"The txn (" + tc_33_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_33_utr), "The txn (" + tc_33_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_33_utr), "The txn (" + tc_33_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_33_payoutref), "The txn (" + tc_33_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_33_utr), "The txn (" + tc_33_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(unresolvedStmMap.containsKey(tc_33_utr),
				"The txn (" + tc_33_utr + ") is present in unresolvedStms which should not be there on " + fileDate);

		/* TC-34 */
		// String tc_34_utr=""; utr not there
		String tc_34_payoutref = "20240703111943GuruLiveUPI073";
		// valid
		validatation.assertTrue(payoutWSMap.containsKey(tc_34_payoutref),
				"The txn (" + tc_34_payoutref + ") is not present in payoutWoStm which should be there on " + fileDate);

		/* TC-35 */
		String tc_35_utr = "417600521699";
		String tc_35_payoutref = "";
		// valid
		validatation.assertTrue(unresolvedStmMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is not present in unresolvedStms which should be there on " + fileDate);
		// Invalid
		validatation.assertFalse(brsMap.containsKey(tc_35_utr), "The txn (" + tc_35_utr
				+ ") is present in the brs_complete file which should not be there on " + fileDate);
		validatation.assertFalse(cowostm.containsKey(tc_35_utr), "The txn (" + tc_35_utr
				+ ") is present in collect_wo_statement which should not be there on " + fileDate);
		validatation.assertFalse(fpwmore2stmMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in fpwmore2stm which should not be there on " + fileDate);
		validatation.assertFalse(invalidBDMap.containsKey(tc_35_utr), "The txn (" + tc_35_utr
				+ ") is present in invalid bene details which should not be there on " + fileDate);
		validatation.assertFalse(invalidSMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in invalid signature which should not be there on " + fileDate);
		validatation.assertFalse(ppwmore1stmMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in ppwmore1stm which should not be there on " + fileDate);
		validatation.assertFalse(payloadWpayoutMap.containsKey(tc_35_payoutref), "The txn (" + tc_35_payoutref
				+ ") is present in payloadWoPayout which should not be there on " + fileDate);
		validatation.assertFalse(payoutWCMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in payoutWoCredit which should not be there on " + fileDate);
		validatation.assertFalse(payoutWDMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in payoutWoDebit which should not be there on " + fileDate);
		validatation.assertFalse(powoplMap.containsKey(tc_35_utr),
				"The txn (" + tc_35_utr + ") is present in payoutWoPayload which should not be there on " + fileDate);
		validatation.assertFalse(stmWmfMap.containsKey(tc_35_utr), "The txn (" + tc_35_utr
				+ ") is present in stmWithMissingFiled which should not be there on " + fileDate);
		validatation.assertFalse(payloadWSMap.containsKey(tc_35_payoutref), "The txn (" + tc_35_payoutref
				+ ") is present in payloadWoStm which should not be there on " + fileDate);
		validatation.assertFalse(payoutWSMap.containsKey(tc_35_payoutref),
				"The txn (" + tc_35_payoutref + ") is present in payoutWoStm which should not be there on " + fileDate);

		validatation.assertAll();
		System.out.println(fileDate + " batch test result are passed");
		System.out.println("---------------------------------------------------------------------------\n");
	}
}
