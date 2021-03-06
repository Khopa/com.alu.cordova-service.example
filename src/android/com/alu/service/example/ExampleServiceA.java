package com.alu.service.example;

import com.alu.service.ServicePlugin;
import com.alu.service.ServiceUtils;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Example service that runs a logging thread on foreground
 * @author Clement Perreau
 */
public class ExampleServiceA extends Service implements Runnable{
	
	/**
	 * Thread to log stuff
	 */
	private Thread thread;
	
	/**
	 * Thread running
	 */
	private boolean running = false;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		Log.d(ServicePlugin.LOG_KEY, "Service " + this.getClass().getSimpleName() + " is starting");
        
		// Start in foreground
        Notification n = ServiceUtils.getDefaultForegroundNotification(this.getApplicationContext(),
        		this.getClass(),
        		"Service" + this.getClass().getSimpleName(),
        		"This is the service " + this.getClass().getSimpleName() + " running in foreground");
        startForeground(startId, n);
        
        
        // Start example thread
        running = true;
        thread = new Thread(this);
        thread.start();
        
		return(START_STICKY);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(ServicePlugin.LOG_KEY, "Service " + this.getClass().getSimpleName() + " is being destroyed");
		running = false;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void run() {
		while(running){
			try {
				Log.d(ServicePlugin.LOG_KEY, "Hello from Service " + this.getClass().getSimpleName());
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		Log.d(ServicePlugin.LOG_KEY, "Service " + this.getClass().getSimpleName() + " thread is dead");
	}
	
}
