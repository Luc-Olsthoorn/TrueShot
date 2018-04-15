package threeD.trueshot.Scenarios;

public class locationCoords {
    public  double x;
    public  double y;
    public  double ele;
    public  double azimuth;

    public locationCoords(double x, double y, double ele){
        this.x = x;
        this.y = y;
        this.ele = ele;
        this.azimuth = Math.atan2(y, x) * (180 / Math.PI);
    }


}
