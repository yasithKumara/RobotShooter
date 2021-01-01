import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class RobotControl {

    RobotInfo robotInfo;
    public static ArrayList<RobotInfo> robotInfoArrayList;
    SwingArena arena;
    private JTextArea textArea;
    private JLabel label;

    public static Object rcMutex = new Object();
    public static ArrayBlockingQueue<Integer> ShootCheckQueue = new ArrayBlockingQueue<>(1);
    public static ArrayBlockingQueue<Integer> WinCheckQueue = new ArrayBlockingQueue<>(1);

    public RobotControl(RobotInfo robot, ArrayList<RobotInfo> robotInfoArrayList, SwingArena arena, JTextArea textArea, JLabel label){
        this.robotInfo = robot;
        this.robotInfoArrayList = robotInfoArrayList;
        this.arena = arena;
        this.textArea = textArea;
        this.label = label;
    }

    public RobotInfo getRobot(){
        return robotInfo;
    }

    public ArrayList<RobotInfo> getAllRobots(){
        return robotInfoArrayList;
    }

    public SwingArena getArena() {
        return arena;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getLabel() {
        return label;
    }

    public boolean moveNorth() {
        synchronized (rcMutex){
            for(int i = 0; i < robotInfoArrayList.size(); i++) {
                if ((((robotInfo.getY() - 1) < 0))
                        ||(((robotInfoArrayList.get(i).getPy()) == robotInfo.getY() - 1) && (robotInfoArrayList.get(i).getPx() == robotInfo.getX()))
                        ||(((robotInfoArrayList.get(i).getFy()) == robotInfo.getY() - 1) && (robotInfoArrayList.get(i).getFx() == robotInfo.getX()))){
                    return false;}
            }
            return true;
        }

    }

    public boolean moveEast() {
        synchronized (rcMutex){
            for(int i = 0; i < robotInfoArrayList.size(); i++) {
                if (((robotInfo.getX() + 1) >= arena.getGridWidth())
                        ||(((robotInfoArrayList.get(i).getPy()) == robotInfo.getY()) && (robotInfoArrayList.get(i).getPx() == robotInfo.getX()+1))
                        ||(((robotInfoArrayList.get(i).getFy()) == robotInfo.getY()) && (robotInfoArrayList.get(i).getFx() == robotInfo.getX()+1))){
                    return false;
                }
            }
            return true;
        }
    }

    public boolean moveSouth () {
        synchronized (rcMutex){
            for(int i = 0; i < robotInfoArrayList.size(); i++) {
                if (((robotInfo.getY() + 1) >= arena.getGridHeight())
                        ||(((robotInfoArrayList.get(i).getPy()) == robotInfo.getY()+1) && (robotInfoArrayList.get(i).getPx() == robotInfo.getX()))
                        ||(((robotInfoArrayList.get(i).getFy()) == robotInfo.getY()+1) && (robotInfoArrayList.get(i).getFx() == robotInfo.getX()))){
                    return false;
                }
            }
            return true;
        }
    }

    public boolean moveWest(){
        synchronized (rcMutex){
            for(int i = 0; i < robotInfoArrayList.size(); i++) {
                if (((robotInfo.getX() - 1) < 0)
                        ||(((robotInfoArrayList.get(i).getPy()) == robotInfo.getY()) && (robotInfoArrayList.get(i).getPx() == robotInfo.getX()-1))
                        ||(((robotInfoArrayList.get(i).getFy()) == robotInfo.getY()) && (robotInfoArrayList.get(i).getFx() == robotInfo.getX()-1))){
                    return false;
                }
            }
            return true;
        }
    }

    public boolean didRobotsWin(){

        if(RobotControl.WinCheckQueue.isEmpty())
            return false;

        return true;
    }

}
