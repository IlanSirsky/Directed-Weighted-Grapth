package api;

public class NodeDataImp implements api.NodeData {
    GeoLocation loc;
    int key;
    double weight;
    String info;
    int tag;

    public NodeDataImp(GeoLocation loc, int key){
        this.loc = loc;
        this.key = key;
        this.weight = 0;
        this.info = "";
        this.tag = 0;
    }

    //getters and setters
    public NodeDataImp(int key){
        this.key = key;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return loc;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.loc = new GeoLocationImp(p.x(), p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }


    @Override
    public String toString() {
        return "MyNodeData{" +
                "loc=" + loc +
                ", key=" + key +
                '}';
    }
}
