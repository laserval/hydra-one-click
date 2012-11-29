package com.findwise.hydra.oneclick;

import com.findwise.hydra.Main;

public class HydraCoreStarter {

	private static final String HYDRA_CORE_PROPERTIES = "../hydra-core.properties";
	
	public Thread startHydraCore() {
		
		String[] hydraArgs = {HYDRA_CORE_PROPERTIES};
		
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
