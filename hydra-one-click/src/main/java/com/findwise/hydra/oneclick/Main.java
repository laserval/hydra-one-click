package com.findwise.hydra.oneclick;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	private static final String LIB_DIR = "lib" + File.separator;
	private static final String EXAMPLES_DIR = "examples" + File.separator;

	public static void main(String[] args) {
		try {
			System.out.println(": Starting MongoDB");
			MongoProcessManager.startMongo();
			//EmbeddedMongoStarter.startMongo();
		} catch (IOException e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}

		System.out.println(": Starting Admin Service");
		JettyStarter jettyStarter = new JettyStarter();
		Thread jt = jettyStarter.startJetty();
		
		System.out.println(": Starting Hydra Core");
		HydraCoreStarter hydraStarter = new HydraCoreStarter();
		Thread ht = hydraStarter.startHydraCore();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			
		}

		AdminServiceClient adminClient = new AdminServiceClient();

		Map<String, String> libraries = new HashMap<String, String>();
		libraries.put("basic",
				LIB_DIR + "hydra-basic-stages-jar-with-dependencies.jar");
		libraries.put("json-out",
				LIB_DIR + "hydra-json-out-stage-jar-with-dependencies.jar");
		libraries.put("tika",
				LIB_DIR + "hydra-tika-stages-jar-with-dependencies.jar");
		libraries.put("web",
				LIB_DIR + "hydra-web-stages-jar-with-dependencies.jar");
		
		adminClient.postLibraries(libraries);

		Map<String, String> stages = new HashMap<String, String>();
		stages.put("basic/stages/matchRegex",
				EXAMPLES_DIR + "stages/matchRegex.properties");
		stages.put("web/stages/htmlExtract",
				EXAMPLES_DIR + "stages/htmlExtract.properties");
		stages.put("json-out/stages/jsonOutput",
				EXAMPLES_DIR + "stages/jsonOutput.properties");
		
		adminClient.postStages(stages);
	}
}
