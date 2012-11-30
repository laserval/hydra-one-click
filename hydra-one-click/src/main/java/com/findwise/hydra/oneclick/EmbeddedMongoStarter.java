package com.findwise.hydra.oneclick;

import java.io.IOException;
import java.net.UnknownHostException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

public class EmbeddedMongoStarter {

	
	
	public static void startMongo() throws UnknownHostException, IOException {
		
		MongodProcess mongod = null;
		MongodConfig config = new MongodConfig(Version.Main.V2_2);
		
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		
		MongodExecutable mongodExecutable = runtime.prepare(config);
		mongod = mongodExecutable.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new MongoShutdownHook(mongod)));
	}
	
	private static class MongoShutdownHook implements Runnable {
		
		MongodProcess mongod;
		
		public MongoShutdownHook(MongodProcess m) {
			mongod = m;
		}
		
		@Override
		public void run() {
			mongod.stop();
		}
	}
}
