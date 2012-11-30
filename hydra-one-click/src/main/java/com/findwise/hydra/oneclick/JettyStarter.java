package com.findwise.hydra.oneclick;

import java.util.Map;

import org.mortbay.jetty.runner.Runner;

public class JettyStarter {

	private static final String ADMIN_SERVICE_JAR = "hydra.war";
	private static final String JETTY_OPTION_PORT = "--port";
	private static final String JETTY_OPTION_PORT_DEFAULT = "9090";
	private static final String JETTY_OPTION_LOG = "--log";
	private static final String JETTY_OPTION_LOG_DEFAULT = "admin-service.log";
	private static final String JETTY_OPTION_PATH = "--path";
	private static final String JETTY_OPTION_PATH_DEFAULT = "/hydra";
	
	private String log_dir;
	private String lib_dir;

	public JettyStarter(Map<ConfigurationOptions, String> config) {
		log_dir = config.get(ConfigurationOptions.LOG_DIR);
		lib_dir = config.get(ConfigurationOptions.LIB_DIR);
	}
	
	public Thread startJetty() {

		String[] jettyArgs = { JETTY_OPTION_LOG, log_dir + JETTY_OPTION_LOG_DEFAULT,
				JETTY_OPTION_PORT, JETTY_OPTION_PORT_DEFAULT,
				JETTY_OPTION_PATH, JETTY_OPTION_PATH_DEFAULT,
				lib_dir + ADMIN_SERVICE_JAR };

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
