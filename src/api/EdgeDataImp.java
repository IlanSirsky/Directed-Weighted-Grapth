package api;

public class EdgeDataImp implements api.EdgeData {

    int src;
    int dst;
    double weight;
    String info;
    int tag;

    public EdgeDataImp(int src, double weight, int dst){
        this.src = src;
        this.dst = dst;
        this.weight = weight;
        this.info = "";
        this.tag = 0;
    }


    //getters and setters
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dst;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
        return "MyEdgeData{" +
                "src=" + src +
                ", dst=" + dst +
                ", weight=" + weight +
                '}';
    }
}
