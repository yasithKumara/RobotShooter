import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RobotInitializerThreadCls {
    public void run(SwingArena arena, JTextArea logger, JLabel label) throws InterruptedException{

        int robotLimit = 100;

        ArrayList<RobotInfo> robotInfols = new ArrayList<>();
        ArrayList<RobotControl> robotControls = new ArrayList<>();
        ArrayList<RobotAI> aiImplementationls = new ArrayList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0;!Thread.interrupted();i++){

            if(!RobotControl.WinCheckQueue.isEmpty()){
                Thread.currentThread().interrupt();
                throw new InterruptedException();
            }


            if(i == robotLimit){
                System.out.println("Robot init thread interrupted");
                Thread.currentThread().interrupt();
                throw new InterruptedException();
            }

            robotInfols.add(new RobotInfo(-2, -2, i));

            robotControls.add(new RobotControl(robotInfols.get(i), robotInfols ,arena, logger,label));
            aiImplementationls.add(new RobotAI());
            int finalI = i;
            Runnable myTask1 = () ->
            {
                try {
                    aiImplementationls.get(finalI).runAI(robotControls.get(finalI));
                } catch (InterruptedException ex) {
                    System.out.println("exception at creating Robo Thread "+finalI);
                }
            };
            executorService.execute(myTask1);

            synchronized (aiImplementationls.get(finalI).monitorI){
                //System.out.println("monitor waiting for spawn");
                try {
                    aiImplementationls.get(finalI).monitorI.wait();
                } catch (InterruptedException interruptedException) {
                    System.out.println("Exception at Robot Initializer Thread monitor!");
                }
            }

            logger.append("\nRobot "+finalI+" spawned");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                System.out.println("Exception at Robot Initializer Thread sleep!");
            }
        }
    }
}
