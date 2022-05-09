package test;

public class DummyThread implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int i = 0;
        while(!Thread.currentThread().isInterrupted()) {
            i++;
            System.out.printf("not interrupted [%d]\n",i);
        }
        System.out.println("interrupted");
    }
}
