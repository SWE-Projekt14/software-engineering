package scoreos;

import org.lightcouch.CouchDbProperties;

public class CouchDBProperties {
	
	public CouchDbProperties setCouchDBSettings(String dbName,
												String dbUserName,
												String dbUserPasswd,
												String dbProtocol,
												String dbHost,
												int dbPort,
												CouchDbProperties properties){
		properties.setDbName(dbName)
			.setUsername(dbUserName)
			.setPassword(dbUserPasswd)
			.setProtocol(dbProtocol)
			.setHost(dbHost)
			.setPort(dbPort)
			.setCreateDbIfNotExist(false)
			.setConnectionTimeout(0)
			.setMaxConnections(100);
		
		return(properties);
	}
	
}
