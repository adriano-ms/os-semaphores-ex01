package view;

import java.util.concurrent.Semaphore;

import controller.DbController;

public class Main {

	public static void main(String[] args) {

		final int THREADS_AMOUNT = 21;

		Semaphore mutex = new Semaphore(1);
		DbController[] controllers = new DbController[THREADS_AMOUNT];
		
		for (int i = 0; i < THREADS_AMOUNT; i++)
			controllers[i] = new DbController(i+1, mutex);

		for (int i = 0; i < THREADS_AMOUNT; i++)
			controllers[i].start();

	}

}
