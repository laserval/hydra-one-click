package com.findwise.hydra.oneclick;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	private static final String LIB_DIR_DEFAULT = "lib" + File.separator;
	private static final String EXAMPLES_DIR_DEFAULT = "examples" + File.separator;
	private static final String CONFIG_DIR_DEFAULT = "conf" + File.separator;
	private static final String LOG_DIR_DEFAULT = "logs" + File.separator;
	private static final String MONGO_DIR_DEFAULT = "mongodb" + File.separator;

	private static final Map<ConfigurationOptions, String> config = new HashMap<ConfigurationOptions, String>();
	
	public static void main(String[] args) {
		
		config.put(ConfigurationOptions.CONFIG_DIR, CONFIG_DIR_DEFAULT);
		config.put(ConfigurationOptions.EXAMPLES_DIR, EXAMPLES_DIR_DEFAULT);
		config.put(ConfigurationOptions.LIB_DIR, LIB_DIR_DEFAULT);
		config.put(ConfigurationOptions.LOG_DIR, LOG_DIR_DEFAULT);
		config.put(ConfigurationOptions.MONGO_DIR, MONGO_DIR_DEFAULT);

		MongoProcessManager mongoProcessManager = new MongoProcessManager(config);
		try {
			System.out.println(": Starting MongoDB");
			mongoProcessManager.startMongo();
			//EmbeddedMongoStarter.startMongo();
		} catch (IOException e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}

		System.out.println(": Starting Admin Service");
		JettyStarter jettyStarter = new JettyStarter(config);
		jettyStarter.startJetty();
		
		System.out.println(": Starting Hydra Core");
		HydraCoreStarter hydraStarter = new HydraCoreStarter(config);
		hydraStarter.startHydraCore();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			
		}

		AdminServiceClient adminClient = new AdminServiceClient();

		String lib_dir = config.get(ConfigurationOptions.LIB_DIR);
		
		Map<String, String> libraries = new HashMap<String, String>();
		libraries.put("basic",
				lib_dir + "hydra-basic-stages-jar-with-dependencies.jar");
		libraries.put("json-out",
				lib_dir + "hydra-json-out-stage-jar-with-dependencies.jar");
		libraries.put("tika",
				lib_dir + "hydra-tika-stages-jar-with-dependencies.jar");
		libraries.put("web",
				lib_dir + "hydra-web-stages-jar-with-dependencies.jar");
		
		adminClient.postLibraries(libraries);
		
		String examples_dir = config.get(ConfigurationOptions.EXAMPLES_DIR);

		Map<String, String> stages = new HashMap<String, String>();
		stages.put("basic/stages/matchRegex",
				examples_dir + "stages/matchRegex.properties");
		stages.put("web/stages/htmlExtract",
				examples_dir + "stages/htmlExtract.properties");
		stages.put("json-out/stages/jsonOutput",
				examples_dir + "stages/jsonOutput.properties");
		
		adminClient.postStages(stages);
	}
}
