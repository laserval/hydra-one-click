package com.findwise.hydra.oneclick;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoProcessManager {
	
	private static final String MONGO_ADMIN_DB = "admin";
	
	private static final String MONGO_HOME = "../mongodb";
	private static final String MONGO_EXECUTABLE = "bin" + File.separator + "mongod";
	private static final String MONGO_OPTIONS_PREFIX = "--";
	private static final String MONGO_OPTION_FORK = "fork";
	private static final String MONGO_OPTION_LOGPATH = "logpath";
	private static final String MONGO_OPTION_LOGPATH_DEFAULT = "../logs" + File.separator + "mongolog";
	private static final String MONGO_OPTION_DBPATH = "dbpath";
	private static final String MONGO_OPTION_DBPATH_DEFAULT= MONGO_HOME + File.separator + "db";
	
	
	public static boolean startMongo() throws IOException {
		String mongoPath = MONGO_HOME + File.separator + MONGO_EXECUTABLE;
		ProcessBuilder pb = new ProcessBuilder().command(mongoPath, 
				MONGO_OPTIONS_PREFIX + MONGO_OPTION_DBPATH + "=" + MONGO_OPTION_DBPATH_DEFAULT,
				MONGO_OPTIONS_PREFIX + MONGO_OPTION_LOGPATH + "=" + MONGO_OPTION_LOGPATH_DEFAULT);
		pb.redirectErrorStream(true);
		Process p = pb.start();

		PumpStreamHandler pump = new PumpStreamHandler(System.out);
		pump.setProcessOutputStream(p.getInputStream());
		
		ProcessDestroyer pd = new ShutdownHookProcessDestroyer();
		pd.add(p);
		
		return true;
	}
	
	private static class MongoStopper implements Runnable {

		@Override
		public void run() {
			Mongo mongo;
			try {
				mongo = new Mongo();
				DB db = mongo.getDB(MONGO_ADMIN_DB);
				CommandResult shutdownResult = db.command(new BasicDBObject("shutdown", 1));
				mongo.close();
			} catch (UnknownHostException e) {
				System.out.println(": MongoDB could not be stopped");
			}
		}
		
	}
}
