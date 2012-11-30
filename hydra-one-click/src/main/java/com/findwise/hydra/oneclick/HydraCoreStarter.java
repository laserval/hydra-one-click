package com.findwise.hydra.oneclick;

import java.util.Map;

import com.findwise.hydra.Main;

public class HydraCoreStarter {

	private static final String HYDRA_CORE_PROPERTIES = "hydra-core.properties";
	
	private String conf_dir;
	
	public HydraCoreStarter(Map<ConfigurationOptions, String> config) {
		conf_dir = config.get(ConfigurationOptions.CONFIG_DIR);
	}

	public Thread startHydraCore() {
		
		String[] hydraArgs = {conf_dir + HYDRA_CORE_PROPERTIES};
		
		Runnable hydraRunnable = new HydraCoreRunnable(hydraArgs);
		Thread hydraThread = new Thread(hydraRunnable);
		hydraThread.start();
		
		return hydraThread;
	}
	
	private class HydraCoreRunnable implements Runnable {
		
		private String[] args;
		
		public HydraCoreRunnable(String[] args) {
			this.args = args;
		}
		
		@Override
		public void run() {
			Main.main(args);
		}
	}
}
