import javax.swing.*;

import static java.lang.Thread.*;

class ShootThreadCls {

    public Object monitorS = new Object();
    private boolean shotSucces;

    public void run(SwingArena arena, ScoreThreadCls scoreThreadCls, JTextArea logger) throws InterruptedException{

        for (int i = 0; i < 1000; ) {
            shotSucces = false;

            if(arena.getShotArrayX(i) ==-1){
                synchronized (monitorS){
                    try {
                        monitorS.wait();
                    } catch (InterruptedException interruptedExceptionSTCM) {
                        System.out.println("Exception at ShootThread Monitor!");
                        throw interruptedExceptionSTCM;
                    }
                }
            }

            if(arena.getShotArrayX(i) !=-1){

                for(RobotInfo robotinfo : arena.getRobotarraylist()){
                    if(robotinfo.getPx() == arena.getShotArrayX(i) && robotinfo.getPy() == arena.getShotArrayY(i) || robotinfo.getFx() == arena.getShotArrayX(i) && robotinfo.getFy() == arena.getShotArrayY(i)){
                        RobotControl.ShootCheckQueue.clear();
                        RobotControl.ShootCheckQueue.offer(robotinfo.getID());
                        logger.append("\nRobot "+robotinfo.getID()+" got shot");
                        long bonus = 10 + 100 * (System.currentTimeMillis() - arena.getShotTime(i)) / robotinfo.getDelayTime();
                        scoreThreadCls.incrementScore((int) bonus);
                        shotSucces = true;
                    }
                }

                if(shotSucces == false){
                    logger.append("\nShot missed!");
                }
                try {
                    sleep(1000);
                } catch (InterruptedException interruptedExceptionSTCS) {
                    System.out.println("Exception at ShootThread sleep!");
                    Thread.currentThread().interrupt();
                    throw interruptedExceptionSTCS;
                }
                i++;
                continue;
            }

            if(!RobotControl.WinCheckQueue.isEmpty()){
                Thread.currentThread().interrupt();
            }
        }
    }
}
