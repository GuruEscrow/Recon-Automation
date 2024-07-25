package demon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import DataBase.UpdateDataIntoDB;
import Simulators.PushSub_MainBrachSimulator;
import genricLibraries.PropertiesUtility;
import simulator.resoucrse.URL;

public class SubBranchQueue {

	public static void main(String[] args){

//---------------------------------------------DataBase connection to fetch the Sub branch queue----------------------------------------------		
		while (true) {
			PropertiesUtility property = new PropertiesUtility();
			property.propertiesInit(URL.PROPERTY_PATH);

			boolean isStmPushedToMainBranch = false;
			LocalTime date = LocalTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

			// Batch1 down time timings
			String batch1_startTime = property.readData("batch1_startTime");
			String batch1_endTime = property.readData("batch1_endTime");
			LocalTime batch1_downstartTime = LocalTime.parse(batch1_startTime, format);
			LocalTime batch1_downendTimeFrm = LocalTime.parse(batch1_endTime, format);

			if (!(date.isAfter(batch1_downstartTime) && date.isBefore(batch1_downendTimeFrm))) {
				// JDBC URL, username, and password of the MySQL database
				String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/payouts?useSSL=false";
				String username = "Guruprasad";
				String password = "MySql@#123";
				Connection connection = null;
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String subQueueQuery = "SELECT * FROM bank.sub_branch_statement";

					// connecting to mysql database and executing the query
					connection = DriverManager.getConnection(jdbcUrl, username, password);
					preparedStatement = connection.prepareStatement(subQueueQuery);
					resultSet = preparedStatement.executeQuery();

					// iterating on number rows found in result
					while (resultSet.next()) {

						// Moving the statement from sub branch to main branch statement
						String statement = resultSet.getString("sub_statement");
						isStmPushedToMainBranch = PushSub_MainBrachSimulator.subQueueToMainBrachStm(statement);

						// Deleting the statement from sub branch queue after passing to main branch
						String utr = resultSet.getString("utr");
						if (isStmPushedToMainBranch) {
							UpdateDataIntoDB.deleteTxnFromQueue(utr);
						}
					}
					if (isStmPushedToMainBranch) {
						System.out.println("Sub Branch queue cleared...");
					}else {
						System.out.println("Empty queue");
						TimeUnit.MINUTES.sleep(1);
					}
				} catch (Exception e) {
					System.err.println("Data base error" + e.getMessage());
				} finally {
					// Closing the data base connection safely
					try {
						if (resultSet != null)
							resultSet.close();
						if (preparedStatement != null)
							preparedStatement.close();
						if (connection != null)
							connection.close();
					}catch(Exception e) {
						System.out.println("Problem while closing sub branch statement data base connection: "+e.getMessage());
					}
					
				}
			}
		}

	}

}
