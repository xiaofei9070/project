package com.star.test;

public class SynchronizedTest {
	public static void main(String[] args) {
		for(int i = 0;i<10;i++){
			String a = "";
			if(i<5)a = "1231";
			else if(i>6)a = "23131";
			else a = String.valueOf(i);
			new MethodTest(String.valueOf(i),a).start();
		}
	}
}
class MethodTest extends Thread{
	private final String i;
	private String a;
	public MethodTest(String i,String a){
		this.i = i;
		this.a = a;
	}
	@Override
	public void run() {
		synchronized (a) {
			try {
				System.out.println(i);
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
