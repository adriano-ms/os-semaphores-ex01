package controller;

import java.util.concurrent.Semaphore;

public class DbController extends Thread {

	private int id;
	private Semaphore mutex;
	private long calcTimeMin;
	private long calcTimeMax;
	private long transactionTime;
	private int iterationsAmount;

	public DbController() {
		super();
	}

	public DbController(int id, Semaphore mutex) {
		this.id = id;
		this.mutex = mutex;
		switch (id % 3) {
		case 1:
			this.calcTimeMin = 200;
			this.calcTimeMax = 1000;
			this.transactionTime = 1000;
			this.iterationsAmount = 2;
			break;
		case 2:
			this.calcTimeMin = 500;
			this.calcTimeMax = 1500;
			this.transactionTime = 1500;
			this.iterationsAmount = 3;
			break;
		case 3:
			this.calcTimeMin = 1000;
			this.calcTimeMax = 2000;
			this.transactionTime = 1500;
			this.iterationsAmount = 3;
		}
	}

	@Override
	public void run() {
		try {
			transactions();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			mutex.release();
		}
	}

	private void transactions() throws InterruptedException {
		for (; iterationsAmount > 0; iterationsAmount--) {
			doCalculations();
			doTransactions();
		}
	}

	private void doCalculations() throws InterruptedException {

		long rolledTime = rollTime();
		System.out.printf("%s: Calculation (%.1fs)\n", this, ((double) rolledTime / 1000));
		sleep(rolledTime);
	}

	private void doTransactions() throws InterruptedException {
		mutex.acquire();
		System.out.printf("%s: Database transaction (%.1fs)\n", this, ((double) transactionTime / 1000));
		sleep(transactionTime);
		mutex.release();
	}

	private long rollTime() {
		return (long) ((Math.random() * (calcTimeMax - calcTimeMin) + calcTimeMin));
	}

	@Override
	public String toString() {
		return "Thread " + id;
	}

}
