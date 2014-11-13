package scoreos;

import org.lightcouch.CouchDbProperties;

public class ScOREOs {
	private CouchDbProperties properties;
	
	public ScOREOs(){
		
	}

	public CouchDbProperties setConnectionSettings(String dbName,
													String dbUserName,
													String dbUserPasswd,
													String dbProtocol,
													String dbHost,
													int dbPort){
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
