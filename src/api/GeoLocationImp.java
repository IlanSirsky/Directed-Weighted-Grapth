package api;

public class GeoLocationImp implements api.GeoLocation {

    private double x;
    private double y;
    private double z;

    public GeoLocationImp(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GeoLocationImp(String location) {
        String[] loc = location.split(",");
        this.x = Double.parseDouble(loc[0]);
        this.y = Double.parseDouble(loc[1]);
        this.z = Double.parseDouble(loc[2]);
    }

    //getters and setters
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double distance(api.GeoLocation g) {
        return Math.sqrt(Math.pow(this.x - g.x(),2) + Math.pow(this.y - g.y(),2) + Math.pow(this.z - g.z(),2));
    }

    @Override
    public String toString() {
        return "MyGeoLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
