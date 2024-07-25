package simulator.resoucrse;

public class PrivateKeyFileNameGetter {

	public static String getFileName(int privatekeyNum) {

		String fileName = "";
		switch (privatekeyNum) {

		case 1:
			fileName = "Adarsh Enterprises";
			break;
		case 2:
			fileName = "Anu Technologies";
			break;
		case 3:
			fileName = "Aravind softs";
			break;
		case 4:
			fileName = "Guru Softwares";
			break;
		case 5:
			fileName = "Hemanth Softwares";
			break;
		case 6:
			fileName = "Naveen Constraction";
			break;
		case 7:
			fileName = "Nethaji Softwares";
			break;
		case 8:
			fileName = "Prasad Softs";
			break;
		case 9:
			fileName = "SK interior";
			break;
		case 10:
			fileName = "Virat 18";
			break;
		}
		return fileName;
	}
}
