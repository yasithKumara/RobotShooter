import javax.swing.*;

public class ScoreThreadCls {

    Object mutex = new Object();
    private volatile int score;

    public void incrementScore(int bonus) {
        synchronized (mutex){
            this.score = this.score + bonus;
        }
    }

    public void run(JLabel label, SwingArena arena) throws InterruptedException {
        while (true){
            label.setText("Score : "+score);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedExceptionSTS) {
                System.out.println("Exception at Score Thread!");
                interruptedExceptionSTS.printStackTrace();
                Thread.currentThread().interrupt();
                throw interruptedExceptionSTS;
            }

            synchronized (mutex){
                score = score + 10;
            }

           for(RobotInfo robotInfo :  arena.getRobotarraylist()){
               if (robotInfo.getFx() == (arena.getGridWidth()-1)/2 && (robotInfo.getFy() == (arena.getGridHeight()-1)/2)){
                   RobotControl.WinCheckQueue.offer(1);
                   Thread.sleep(3000);
                   System.out.println("Game Over");
                   label.setText("Score : "+score);
                   JOptionPane.showMessageDialog(null,"Your score is " +score, "Game over", JOptionPane.PLAIN_MESSAGE);
                   Thread.currentThread().interrupt();
                   throw new InterruptedException();
               }
           }
        }
    }
}
