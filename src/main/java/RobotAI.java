import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Thread.sleep;

public class RobotAI {

    public Object monitorI = new Object();
    final Object AImutex = RobotControl.rcMutex;

    public void runAI(RobotControl rc) throws InterruptedException{

        RobotInfo myRobot = rc.getRobot();

        int randomDelayTime = ThreadLocalRandom.current().nextInt(500,2001);
        myRobot.setDelayTime(randomDelayTime);

        boolean northBusy = false, eastBusy = false, westBusy = false, southBusy = false;

        //start spawn
        boolean spawned = false;

        boolean busy00 = false, busy0y = false, busyx0 = false, busyxy = false;

        while(!spawned){

            if( busy00 && busy0y && busyx0 && busyxy){
                busy00 = false; busy0y = false; busyx0 = false; busyxy = false;
                sleep(randomDelayTime);
            }

            switch (ThreadLocalRandom.current().nextInt(1, 5)) {

                case 1 :synchronized (AImutex){
                    for(int i = 0;i<rc.getAllRobots().size();i++){
                        if((((rc.getAllRobots().get(i).getPy()) == 0) && (rc.getAllRobots().get(i).getPx() == 0))
                                ||(((rc.getAllRobots().get(i).getFy()) == 0) && (rc.getAllRobots().get(i).getFx() == 0))){
                            System.out.println("0 0 busy");
                            busy00 = true;
                            break;

                        }else{
                            rc.arena.robotX.add((double) 0);
                            rc.arena.robotY.add((double) 0);
                            myRobot.setX(0);
                            myRobot.setY(0);

                            rc.arena.AddRobotToList(myRobot);

                            myRobot.setPx(0);
                            myRobot.setPy(0);
                            myRobot.setFx(0);
                            myRobot.setFy(0);

                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            spawned = true;
                            break;
                        }
                    }
                    break;
                }
                case 2 : synchronized (AImutex){
                    for(int i = 0;i<rc.getAllRobots().size();i++){
                        if((((rc.getAllRobots().get(i).getPy()) == (rc.arena.getGridHeight()-1)) && (rc.getAllRobots().get(i).getPx() == 0))
                                ||(((rc.getAllRobots().get(i).getFy()) == (rc.arena.getGridHeight()-1)) && (rc.getAllRobots().get(i).getFx() == 0))){
                            System.out.println("0 "+(rc.arena.getGridWidth()-1)+" busy");
                            busy0y = true;
                            break;
                        }else {
                            rc.arena.robotX.add((double) 0);
                            rc.arena.robotY.add((double) (rc.arena.getGridHeight()-1));
                            myRobot.setX(0);
                            myRobot.setY((rc.arena.getGridHeight()-1));

                            rc.arena.AddRobotToList(myRobot);

                            myRobot.setPx(0);
                            myRobot.setPy((rc.arena.getGridHeight()-1));
                            myRobot.setFx(0);
                            myRobot.setFy((rc.arena.getGridHeight()-1));

                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            spawned = true;
                            break;
                        }
                    }
                    break;
                }
                case 3 : synchronized (AImutex){
                    for(int i = 0;i<rc.getAllRobots().size();i++){
                        if((((rc.getAllRobots().get(i).getPy()) == 0) && (rc.getAllRobots().get(i).getPx() == (rc.arena.getGridWidth()-1)))
                                ||(((rc.getAllRobots().get(i).getFy()) == 0) && (rc.getAllRobots().get(i).getFx() == (rc.arena.getGridWidth()-1)))){
                            System.out.println((rc.arena.getGridWidth()-1)+" 0 busy");
                            busyx0 = true;
                            break;
                        }else{
                            rc.arena.robotX.add((double) (rc.arena.getGridWidth()-1));
                            rc.arena.robotY.add((double) 0);
                            myRobot.setX((rc.arena.getGridWidth()-1));
                            myRobot.setY(0);

                            rc.arena.AddRobotToList(myRobot);

                            myRobot.setPx((rc.arena.getGridWidth()-1));
                            myRobot.setPy(0);
                            myRobot.setFx((rc.arena.getGridWidth()-1));
                            myRobot.setFy(0);

                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            spawned = true;
                            break;
                        }
                    }
                    break;

                }
                case 4 : synchronized (AImutex){
                    for(int i = 0;i<rc.getAllRobots().size();i++){
                        if((((rc.getAllRobots().get(i).getPy()) == (rc.arena.getGridWidth()-1)) && (rc.getAllRobots().get(i).getPx() == (rc.arena.getGridWidth()-1)))
                                ||(((rc.getAllRobots().get(i).getFy()) == (rc.arena.getGridWidth()-1)) && (rc.getAllRobots().get(i).getFx() == (rc.arena.getGridWidth()-1)))){
                            System.out.println((rc.arena.getGridWidth()-1)+" "+(rc.arena.getGridWidth()-1)+" busy");
                            busyxy = true;
                            break;
                        }else{
                            rc.arena.robotX.add((double) (rc.arena.getGridWidth()-1));
                            rc.arena.robotY.add((double) (rc.arena.getGridHeight()-1));
                            myRobot.setX((rc.arena.getGridWidth()-1));
                            myRobot.setY((rc.arena.getGridWidth()-1));

                            rc.arena.AddRobotToList(myRobot);

                            myRobot.setPx((rc.arena.getGridWidth()-1));
                            myRobot.setPy((rc.arena.getGridWidth()-1));
                            myRobot.setFx((rc.arena.getGridWidth()-1));
                            myRobot.setFy((rc.arena.getGridWidth()-1));

                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            spawned = true;
                            break;
                        }
                    }
                    break;

                }
                default : System.out.println("error at randomizing the spawn");
            }
        }

        //end spawn
        synchronized (monitorI){
            monitorI.notify();
        }

//        if(checkFiredStatus(rc,myRobot)){}
//        sleep(randomDelayTime/4);
//
//        if(checkFiredStatus(rc,myRobot)){}
//        sleep(randomDelayTime/4);
//
//        if(checkFiredStatus(rc,myRobot)){}
//        sleep(randomDelayTime/4);
//
//        if(checkFiredStatus(rc,myRobot)){}
//        sleep(randomDelayTime/4);
//
//        if(checkFiredStatus(rc,myRobot)){}

        rc.getArena().repaint();

        //start robot routine
        while(!Thread.interrupted()){

            if(rc.didRobotsWin()){
                break;
            }

            //movement
            boolean moved;
            moved = false;

            while(!moved){

                if( northBusy && eastBusy && southBusy && westBusy){
                    northBusy = false; eastBusy = false; southBusy = false; westBusy = false;
                    sleep(randomDelayTime);
                }

                switch(ThreadLocalRandom.current().nextInt(1, 5)) {
                    case 1 :    if(rc.moveNorth()){
                        synchronized (AImutex){
                            myRobot.setPx((int) myRobot.getX());
                            myRobot.setPy((int) myRobot.getY());

                            myRobot.setFx((int) myRobot.getX());
                            myRobot.setFy((int) ((myRobot.getY()-1)));
                        }

                        for(int i = 0;i<10;i++){
                            myRobot.setY(myRobot.getY() - 0.1);
                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            sleep(50);
                        }
                        moved = isMoved(myRobot,rc);
                        break;
                    }else {continue;}

                    case 2 :    if(rc.moveEast()){
                        synchronized (AImutex){
                            myRobot.setPx((int) myRobot.getX());
                            myRobot.setPy((int) myRobot.getY());

                            myRobot.setFx((int) ((myRobot.getX()+1)));
                            myRobot.setFy((int) myRobot.getY());
                        }

                        for(int i = 0;i<10;i++) {
                            myRobot.setX(myRobot.getX() + 0.1);
                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            sleep(50);
                        }
                        moved = isMoved(myRobot,rc);
                        break;
                    }else {continue;}

                    case 3 :    if(rc.moveSouth()){
                        synchronized (AImutex){
                            myRobot.setPx((int) myRobot.getX());
                            myRobot.setPy((int) myRobot.getY());

                            myRobot.setFx((int) myRobot.getX());
                            myRobot.setFy((int) ((myRobot.getY()+1)));
                        }

                        for(int i = 0;i<10;i++) {
                            myRobot.setY(myRobot.getY() + 0.1);
                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            sleep(50);
                        }
                        moved = isMoved(myRobot,rc);
                        break;
                    }else {continue;}

                    case 4 :

                        if(rc.moveWest()){
                        synchronized (AImutex){
                            myRobot.setPx((int) myRobot.getX());
                            myRobot.setPy((int) myRobot.getY());

                            myRobot.setFx((int) (myRobot.getX()-1));
                            myRobot.setFy((int) myRobot.getY());
                        }


                        for(int i = 0;i<10;i++) {
                            myRobot.setX(myRobot.getX() - 0.1);
                            rc.getArena().setRobotPosition(myRobot.getX(), myRobot.getY(), myRobot.getID());
                            sleep(50);
                        }
                        moved = isMoved(myRobot,rc);
                            //moved = true;
                        break;
                    }else {continue;}

                    default:    {
                        System.out.println("robot "+myRobot.getID()+" did not move");
                    }
                }
            }
            //end movement

            if(checkFiredStatus(rc,myRobot)){break;}
            sleep(randomDelayTime/4);

            if(checkFiredStatus(rc,myRobot)){break;}
            sleep(randomDelayTime/4);

            if(checkFiredStatus(rc,myRobot)){break;}
            sleep(randomDelayTime/4);

            if(checkFiredStatus(rc,myRobot)){break;}
            sleep(randomDelayTime/4);

            if(checkFiredStatus(rc,myRobot)){break;}
        }
        Thread.currentThread().interrupt();
    }

    private boolean isMoved(RobotInfo myRobot, RobotControl rc) {

        if(checkFiredStatus(rc,myRobot)){}
        synchronized (AImutex){
            myRobot.setX(myRobot.getFx());
            myRobot.setY(myRobot.getFy());

            myRobot.setPx((int) myRobot.getX());
            myRobot.setPy((int) myRobot.getY());

            myRobot.setFx((int) myRobot.getX());
            myRobot.setFy((int) myRobot.getY());
        }
        return true;
    }

    public boolean checkFiredStatus(RobotControl rc, RobotInfo myRobot){
            if(!RobotControl.ShootCheckQueue.isEmpty()){
                if(RobotControl.ShootCheckQueue.peek() == myRobot.getID()){
                    RobotControl.ShootCheckQueue.poll();
                    rc.getArena().setRobotPosition(-2,-2,myRobot.getID());

                    synchronized (AImutex){
                        myRobot.setFx(-2);
                        myRobot.setFy(-2);
                        myRobot.setPx(-2);
                        myRobot.setPy(-2);
                    }
                    rc.arena.repaint();
                    Thread.currentThread().interrupt();
                    return true;
                }
            }
        return false;
    }
}

