public class RobotInfo {

    private double x;
    private double y;
    private int ID;
    private int px;
    private int py;
    private int fx;
    private int fy;
    private  boolean alive = true;
    private long delayTime = 2000;


    public RobotInfo(double x, double y, int ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getFx() {
        return fx;
    }

    public void setFx(int fx) {
        this.fx = fx;
    }

    public int getFy() {
        return fy;
    }

    public void setFy(int fy) {
        this.fy = fy;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long lastDelayTime) {
        this.delayTime = lastDelayTime;
    }
}
