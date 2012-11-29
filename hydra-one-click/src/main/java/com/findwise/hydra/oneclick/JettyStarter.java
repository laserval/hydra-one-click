package com.findwise.hydra.oneclick;

import java.io.File;

import org.mortbay.jetty.runner.Runner;

public class JettyStarter {
	
	private static final String LIB_DIR = "../lib";
	private static final String ADMIN_SERVICE_JAR = "hydra.war";
	private static final String JETTY_OPTION_PORT = "--port";
	private static final String JETTY_OPTION_PORT_DEFAULT = "9090";
	private static final String JETTY_OPTION_LOG = "--log";
	private static final String JETTY_OPTION_LOG_DEFAULT = "../logs/admin-service-log";
	private static final String JETTY_OPTION_PATH = "--path";
	private static final String JETTY_OPTION_PATH_DEFAULT = "/hydra";
	
	public Thread startJetty() {

    	String[] jettyArgs = {
    			JETTY_OPTION_LOG, JETTY_OPTION_LOG_DEFAULT,
    			JETTY_OPTION_PORT, JETTY_OPTION_PORT_DEFAULT,
    			JETTY_OPTION_PATH, JETTY_OPTION_PATH_DEFAULT,
    			LIB_DIR + File.separator + ADMIN_SERVICE_JAR};
    	
    	Thread jettyThread = new Thread(new JettyRunnable(jettyArgs));
    	jettyThread.start();
    	
    	return jettyThread;
	}

	private class JettyRunnable implements Runnable {
		
		private String[] args;
		
		public JettyRunnable(String[] args) {
			this.args = args;
		}
		
		@Override
		public void run() {
			Runner.main(args);
		}
	}
}
