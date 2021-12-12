package Graphics;

import api.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {

    private int curveScale = 45;

    HashMap<String, Boolean> checkEdge = new HashMap<String, Boolean>(); // check if src -> dst or dst -> src edge already created

    private double xMin, xMax, yMin, yMax;
    private DirectedWeightedGraphAlgorithms algo;
    double width, height;
    private int kRADIUS = 15; // the size of the circles of the nodes
    private Image mBuffer_image;
    private Graphics mBuffer_graphics;

    // custom colors to save
    private Color normalColor = Color.BLUE;
    private Color shortestPathColor = new Color(86, 255, 0);
    private Color centerColor = new Color(0, 0, 0);

    private int extraSpecialSize = 10; // curve amount

    public GUI(DirectedWeightedGraphAlgorithms algo) {
        this.algo = algo;
        init();
    }

    public void update(DirectedWeightedGraphAlgorithms algo) {
        this.algo = algo;
    }

    // updating the size by the nodes we got, probably gonna be called when we add node or delete, or load a new json
    public void updateSize(){
        Iterator<NodeData> t = this.algo.getGraph().nodeIter();
        if (t != null) {
            NodeData nd = t.next();
            this.xMin = nd.getLocation().x();
            this.xMax = nd.getLocation().x();
            this.yMax = nd.getLocation().y();
            this.yMin = nd.getLocation().y();
        }
        while (t.hasNext()) {
            NodeData nd = t.next();

            if (this.xMin > nd.getLocation().x()) {
                this.xMin = nd.getLocation().x();
            } else if (this.xMax < nd.getLocation().x()) {
                this.xMax = nd.getLocation().x();
            }

            if (this.yMin > nd.getLocation().y()) {
                this.yMin = nd.getLocation().y();
            } else if (this.yMax < nd.getLocation().y()) {
                this.yMax = nd.getLocation().y();
            }

        }

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    // initiate the GUI of the graph
    public void init() {

        updateSize();

        this.setSize((int) this.width, (int) this.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.add(menu);
        Menu algorithmsBar = new Menu("Algorithm");
        menuBar.add(algorithmsBar);
        Menu graphBar = new Menu("Graph");
        menuBar.add(graphBar);
        this.setMenuBar(menuBar);

        MenuItem item1 = new MenuItem("Load");
        item1.addActionListener(this);

        MenuItem item2 = new MenuItem("Save");
        item2.addActionListener(this);

        MenuItem shortestpath = new MenuItem("Shortest path");
        shortestpath.addActionListener(this);

        MenuItem center = new MenuItem("Center");
        center.addActionListener(this);

        MenuItem tsp = new MenuItem("TSP");
        tsp.addActionListener(this);

        MenuItem clear = new MenuItem("Clear");
        clear.addActionListener(this);

        MenuItem addNode = new MenuItem("Add Node");
        addNode.addActionListener(this);

        MenuItem addEdge = new MenuItem("Add Edge");
        addEdge.addActionListener(this);

        MenuItem delNode = new MenuItem("Delete Node");
        delNode.addActionListener(this);

        MenuItem delEdge = new MenuItem("Delete Edge");
        delEdge.addActionListener(this);

        menu.add(item1);
        menu.add(item2);

        algorithmsBar.add(shortestpath);
        algorithmsBar.add(center);
        algorithmsBar.add(tsp);
        algorithmsBar.add(clear);

        graphBar.add(addNode);
        graphBar.add(addEdge);
        graphBar.add(delNode);
        graphBar.add(delEdge);


    }

    // painting the graph, nodes and edges, labels of the weight.
    @Override
    public void paintComponents(Graphics g) {

        Iterator<NodeData> t = this.algo.getGraph().nodeIter();
        while (t.hasNext()) {
            boolean special = false;

            NodeData nd = t.next();
            Dimension pos = getPointPosition(nd.getLocation().x(), nd.getLocation().y());
            g.setColor(normalColor);
            if(nd.getInfo() != null ){
                if(nd.getInfo().equals("In Path")) { // if this edge is a part of tsp or shortest path
                    g.setColor(shortestPathColor);
                    special=true;
                }
                else if(nd.getInfo().equals("Center")){ // if the node is the center
                    g.setColor(centerColor);
                    special=true;
                }
            }
            int plus = 0;
            if (special) plus += extraSpecialSize;
            g.fillOval((int) pos.getWidth() - kRADIUS, (int) pos.getHeight() - kRADIUS, 2 * kRADIUS + plus, 2 * kRADIUS + plus);
            g.setColor(new Color(0, 0, 0));
            g.setFont(new Font("Serif", Font.BOLD, 14));
            g.drawString("" + nd.getKey(), (int) pos.getWidth() - kRADIUS, (int) pos.getHeight() - kRADIUS);
        }

        Iterator<EdgeData> et = this.algo.getGraph().edgeIter();

        checkEdge.clear();

        while (et.hasNext()) {
            EdgeData ed = et.next();
            g.setColor(Color.RED);

            if(ed.getInfo() != null ) {
                if (ed.getInfo().equals("In Path")) { // if the edge is a part of the shortest path or tsp
                    g.setColor(shortestPathColor);
                }
            }

            NodeData src = this.algo.getGraph().getNode(ed.getSrc());
            NodeData dst = this.algo.getGraph().getNode(ed.getDest());

            Dimension posSrc = getPointPosition(src.getLocation().x(), src.getLocation().y());
            Dimension posDst = getPointPosition(dst.getLocation().x(), dst.getLocation().y());

            int width = ((int) posSrc.getWidth() + (int) posDst.getWidth()) / 2;
            int height = ((int) posSrc.getHeight() + (int) posDst.getHeight()) / 2;

            String check = src.getKey() + "_" + dst.getKey();
            String revCheck = dst.getKey() + "_" + src.getKey();
            QuadCurve2D.Double curve;

            // check if src -> dst edge or dst -> src edge exist already
            if (checkEdge.get(check) == null && checkEdge.get(revCheck) == null) {

                checkEdge.put(check, true);

                width += curveScale;
                height += curveScale;

                // curve the edge
                curve = new QuadCurve2D.Double((int) posSrc.getWidth(), (int) posSrc.getHeight(), width, height, (int) posDst.getWidth(), (int) posDst.getHeight());
                ((Graphics2D) g).draw(curve);

            } else {
                width -= curveScale;
                height -= curveScale;

                // curve the edge
                curve = new QuadCurve2D.Double((int) posSrc.getWidth(), (int) posSrc.getHeight(), width, height, (int) posDst.getWidth(), (int) posDst.getHeight());
                ((Graphics2D) g).draw(curve);
            }

            g.setColor(new Color(0, 0, 0));
            Font font = new Font("Ariel", Font.BOLD, 14);

            // make the label of the weight and rotate it as the edge way.
            AffineTransform tx = new AffineTransform();

            double angle = Math.atan2((int) posDst.getHeight() - height, (int) posDst.getWidth() - width);

            double m = (posDst.getHeight() - height) / ((int) posDst.getWidth() - width);
            int plus = 0;
            plus = (int) posDst.getWidth() < width ? 3 : -3;
            double n = posDst.getHeight() - (m * posDst.getWidth());
            double y = m * (width) + n;

            tx.rotate(angle);
            Font rotatedFont = font.deriveFont(tx);
            g.setFont(rotatedFont);

            String sValue = String.format("%.3f", ed.getWeight());
            g.drawString(sValue + " , " + ed.getSrc() + "->" + ed.getDest(), width , (int)y);

        }
    }

    public void paint(Graphics g) {
        // Create a new "canvas"
        mBuffer_image = createImage((int) this.width, (int) this.height);
        mBuffer_graphics = mBuffer_image.getGraphics();

        // Draw on the new "canvas"
        paintComponents(mBuffer_graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(mBuffer_image, 0, 0, this);
    }

    // create an injective function from the minimum nodes positions to the size of the screen
    public Dimension getPointPosition(double x, double y) {
        Dimension size = new Dimension();

        double widthSpace = xMax - xMin; // [xMin,xMax] - > [100, this.width-100]
        widthSpace = ((this.width - 150) / widthSpace);
        double width = widthSpace * x - (xMin * widthSpace - 75);

        double heightSpace = yMax - yMin; // [yMin,yMax] - > [100, this.height-100]
        heightSpace = ((this.height - 150) / heightSpace);
        double height = heightSpace * y - (yMin * heightSpace - 75);

        size.setSize(width, height);

        return size;
    }

    // actionPerformed when clicked at any button. then we use str.equals to determine each button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();

        if (str.equals("Load")) {
            LoadScreen sc = new LoadScreen(this);
            sc.init();
        }
        if (str.equals("Save")) {
            this.algo.save("./data/save.json");
        }
        if (str.equals("Shortest path")){

            ShortPathScreen sc = new ShortPathScreen(this);
            sc.init();
        }
        if(str.equals("Center")){
            this.clear();

            NodeData nd = algo.center();
            nd.setInfo("Center");
            repaint();
        }
        if(str.equals("TSP")){
            TspScreen sc = new TspScreen(this);
            sc.init();
        }
        if(str.equals("Clear")){
            this.clear();
        }
        if(str.equals("Add Node")){
            AddScreen sc = new AddScreen(this, AddScreen.Mode.NODE);
            sc.init();
        }
        if(str.equals("Add Edge")){
            AddScreen sc = new AddScreen(this, AddScreen.Mode.EDGE);
            sc.init();
        }
        if(str.equals("Delete Node")){
            RemoveScreen sc = new RemoveScreen(this, RemoveScreen.Mode.NODE);
            sc.init();
        }
        if(str.equals("Delete Edge")){
            RemoveScreen sc = new RemoveScreen(this, RemoveScreen.Mode.EDGE);
            sc.init();
        }

    }

    // make tsp nodes changed in the graph
    public void tspGUI(int[] nodes){
        this.clear();

        List<NodeData> city = new ArrayList<>();

        for (int i : nodes){
            city.add(this.algo.getGraph().getNode(i));
        }

        List<NodeData> tspList = this.algo.tsp(city);

        markPath(tspList);

        repaint();
    }

    // clear any algorithm that currently play in the graph
    public void clear(){
        Iterator<NodeData> t = algo.getGraph().nodeIter();
        while(t.hasNext()){
            NodeData nd = t.next();
            nd.setInfo("");
        }

        Iterator<EdgeData> t2 = algo.getGraph().edgeIter();
        while(t2.hasNext()){
            EdgeData ed = t2.next();
            ed.setInfo("");
        }

        repaint();
    }

    // make shortest path nodes changed in the graph
    public void shortestPath(int src, int dst){
        this.clear();
        List<NodeData> shortest = algo.shortestPath(src,dst);

        markPath(shortest);

        repaint();
    }

    // mark the info of the path we gave the function to "In Path" to change the color later
    public void markPath(List<NodeData> shortest){
        for(int i = 0; i<shortest.size()-1; i++){
            NodeData nd = shortest.get(i);
            NodeData ndNext = shortest.get(i+1);

            nd.setInfo("In Path");
            EdgeData ed = algo.getGraph().getEdge(nd.getKey(), ndNext.getKey());
            ed.setInfo("In Path");
        }

        NodeData nd = shortest.get(shortest.size()-1);

        nd.setInfo("In Path");
    }

    // adding node to the graph
    public void addNode(double x, double y){

        GeoLocation geo = new GeoLocationImp(x,y,0);
        int key = this.algo.getGraph().nodeSize();

        NodeData nd = new NodeDataImp(geo, key);

        this.algo.getGraph().addNode(nd);

        updateSize();

        repaint();

    }

    // adding edge to the graph
    public void addEdge(int src, int dst, double weight){
        this.algo.getGraph().connect(src, dst, weight);
        repaint();
    }

    // removing node from graph
    public void removeNode(int key){
        this.algo.getGraph().removeNode(key);
        repaint();
    }

    // removing edge from graph
    public void removeEdge(int src, int dst){
        this.algo.getGraph().removeEdge(src, dst);
        repaint();
    }

}