package com.findwise.hydra.oneclick;

import java.io.IOException;


public class Starter {
	
    public static void main( String[] args )
    {
    	MongoStarter mongoStarter = new MongoStarter();
    	Process p = null;
    	try {
    		System.out.println(": Starting MongoDB");
			p = mongoStarter.startMongo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	JettyStarter jettyStarter = new JettyStarter();
    	Thread jt = jettyStarter.startJetty();
    	
    	HydraCoreStarter hydraStarter = new HydraCoreStarter();
    	Thread ht = hydraStarter.startHydraCore();
    	
    }
}
