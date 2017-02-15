package com.star.sse;

import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.jetty.servlets.EventSource;

public class MovementEventSource implements EventSource{
	private int width = 800;
	private int height = 600;
	private int stepMax = 5;
	private int x = 0;
	private int y = 0;
	private Random random = new Random();
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public MovementEventSource(int width, int height, int stepMax) {
		this.width = width;
		this.height = height;
		this.stepMax = stepMax;
		this.x = random.nextInt(width);
		this.y = random.nextInt(height);
	}
	
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(Emitter emit) throws IOException {
		// TODO Auto-generated method stub
		emit.comment("");
		emit.data("111");
		
		
		
	}

}
