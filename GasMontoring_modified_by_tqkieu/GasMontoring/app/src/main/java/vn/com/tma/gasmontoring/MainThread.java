package vn.com.tma.gasmontoring;

/**
 * Created by nhokm on 08/14/2017.
 */

public class MainThread extends Thread {
    private boolean running;

    public void setRunning(boolean r)
    {
        running = r;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();

        while(running)
        {
            ////// cap nhat lai trang thai game
            /////  render du lieu ra man hinh
        }
    }
}
