package pl.kwisniewski.database.abstr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

public class AbstractSpringDBUnitAnnotation{
	
	
	private final static Logger LOGGER = Logger.getLogger(AbstractSpringDBUnitAnnotation.class.getName());
	
	private static String url;
	private static String driverClassName;
	private static String userName;
	private static String password;
	
	
	private static IDatabaseConnection connection;	
	
	public void beforeClass() {			
		openDBUnit();
	}
	
	public  void tearDown(){
		closeDBUnit();
	}
		
	protected void openDBUnit() {
		
		Connection jdbcConnection = null;

		try {
			
			Class.forName(driverClassName);
			jdbcConnection = DriverManager.getConnection(url, userName, password);	
			connection = new DatabaseConnection(jdbcConnection);
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with opening DbUnit connection", e);
		}
		
	}

	protected void closeDBUnit() {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Problem with closing DbUnit connection", e);
		}
	}

	protected void executeDataFile(String path) {

		openDBUnit();
		
		try {
			IDataSet dataSet = new FlatXmlDataSet(this.getClass()
					.getClassLoader().getResourceAsStream(path));

			Assert.assertNotNull(connection);
			Assert.assertNotNull(dataSet);

			DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with execute data file", e);
		}
		
		closeDBUnit();

	}
	
	
	// ======================== GETTERS AND SETTERS ============================ //
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		AbstractSpringDBUnitAnnotation.url = url;
	}

	public String getDriverClassName() {		
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		AbstractSpringDBUnitAnnotation.driverClassName = driverClassName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		AbstractSpringDBUnitAnnotation.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		AbstractSpringDBUnitAnnotation.password = password;
	}	

}
