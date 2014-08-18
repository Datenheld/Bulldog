package org.bulldog.devices.motor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bulldog.devices.actuators.movement.Move;
import org.bulldog.devices.actuators.Actuator;

public abstract class AbstractStepper implements Actuator {
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<?> currentMove = null;
	
	
	public abstract void forward();
	public abstract void backward();
	public abstract void stop();
	
	public void move(Move move) {
		move.execute(this);
	}
	
	public void moveAsync(final Move move) {
		currentMove = executor.submit(new Runnable() {

			@Override
			public void run() {
				move(move);
			}
			
		});
	}
	
	public void awaitMoveCompleted() {
		if(currentMove != null) {
			try {
				currentMove.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
	}

}
