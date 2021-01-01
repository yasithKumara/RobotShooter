import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class App
{
    public static void main(String[] args) 
    {
        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            JFrame window = new JFrame("Sad Reacts Only");
            SwingArena arena = new SwingArena();

            JToolBar toolbar = new JToolBar();
            JButton btn1 = new JButton("Start");
            JButton btn2 = new JButton("Stop");
            JLabel label = new JLabel("Score: 00");
            toolbar.add(btn1);
            toolbar.add(btn2);
            toolbar.add(label);

            JTextArea logger = new JTextArea();
            JScrollPane loggerArea = new JScrollPane(logger);
            loggerArea.setBorder(BorderFactory.createEtchedBorder());

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, arena, logger);

            Container contentPane = window.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(toolbar, BorderLayout.NORTH);
            contentPane.add(splitPane, BorderLayout.CENTER);

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(800, 800));
            window.pack();
            window.setVisible(true);
            splitPane.setDividerLocation(0.75);

            AtomicBoolean first = new AtomicBoolean(true);

            btn1.addActionListener((event) ->
            {
                //ensure that the button click only works once
                if(first.get()){
                    first.set(false);

                    //Robot initializer thread initializer
                    RobotInitializerThreadCls robotInitializerThreadCls = new RobotInitializerThreadCls();
                    Runnable myTask3 = () ->
                    {
                        try {
                            robotInitializerThreadCls.run(arena,logger, label);
                        } catch (InterruptedException interruptedException) {
                            System.out.println("Handled exception at Robot Initializer Thread");
                        }
                    };
                    Thread ritc = new Thread(myTask3);
                    ritc.start();


                    //score thread initializer
                    ScoreThreadCls scoreThreadCls = new ScoreThreadCls();
                    Runnable myTask1 = () ->
                    {
                        try {
                            scoreThreadCls.run(label,arena);
                        } catch (InterruptedException ex) {
                            System.out.println("Exception at Score Thread!");
                        }
                    };
                    Thread sct = new Thread(myTask1);
                    sct.start();

                    //Shoot Thread Initializer
                    ShootThreadCls shootThreadCls = new ShootThreadCls();
                    Runnable myTask2 = () ->
                    {
                        try {
                            shootThreadCls.run(arena,scoreThreadCls, logger);
                        } catch (InterruptedException ex ) {
                            System.out.println("Exception at Shoot Thread!");
                        }
                    };
                    Thread stt = new Thread(myTask2);
                    stt.start();

                    arena.addListener((x, y) ->
                    {
                        long time = System.currentTimeMillis();
                        arena.setShotArray(x,y,arena.getShotCounter(), time);
                        arena.setShotCounter(arena.getShotCounter()+1);
                        synchronized (shootThreadCls.monitorS){
                            shootThreadCls.monitorS.notify();
                        }
                    });
                }
            });
        });
    }
}
