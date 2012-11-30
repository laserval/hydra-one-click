package com.findwise.hydra.oneclick;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;

public class MongoProcessManager {
	
	private static final String MONGO_EXECUTABLE = "bin" + File.separator + "mongod";
	private static final String MONGO_OPTION_FORK = "--fork";
	private static final String MONGO_OPTION_LOGPATH = "--logpath";
	private static final String MONGO_OPTION_LOGPATH_DEFAULT = "mongo.log";
	private static final String MONGO_OPTION_DBPATH = "--dbpath";
	private static final String MONGO_OPTION_DBPATH_DEFAULT= "db";
	
	private String mongo_home;
	private String log_dir;
	
	public MongoProcessManager(Map<ConfigurationOptions, String> config) {
		mongo_home = config.get(ConfigurationOptions.MONGO_DIR);
		log_dir = config.get(ConfigurationOptions.LOG_DIR);
		
	}
	
	
	public void startMongo() throws IOException {
		String mongoPath = mongo_home + MONGO_EXECUTABLE;
		ProcessBuilder pb = new ProcessBuilder().command(mongoPath, 
				MONGO_OPTION_DBPATH + "=" + mongo_home + MONGO_OPTION_DBPATH_DEFAULT,
				MONGO_OPTION_LOGPATH + "=" + log_dir + MONGO_OPTION_LOGPATH_DEFAULT);
		pb.redirectErrorStream(true);
		Process p = pb.start();

		PumpStreamHandler pump = new PumpStreamHandler(System.out);
		pump.setProcessOutputStream(p.getInputStream());
		
		ProcessDestroyer pd = new ShutdownHookProcessDestroyer();
		pd.add(p);
	}
}
