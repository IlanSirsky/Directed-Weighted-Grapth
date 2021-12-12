package api;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraphImp graph;

    public Algorithms() {
    }

    public Algorithms(DirectedWeightedGraph g) {
        this.graph = (DirectedWeightedGraphImp) g;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = new DirectedWeightedGraphImp((DirectedWeightedGraphImp) g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphImp(this.graph);
    }

    @Override
    public boolean isConnected() {

        if (this.graph.edgeSize() < this.graph.nodeSize()) { // there are less than n edges;
            return false;
        }

        DirectedWeightedGraphImp reverse = new DirectedWeightedGraphImp(); //reversing the graph
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) { //adding the nodes
            reverse.addNode(nodeIter.next());
        }
        Iterator<EdgeData> edgeIter = this.graph.edgeIter();
        while (edgeIter.hasNext()) { //adding the edges but in a reversed way
            EdgeData edge = edgeIter.next();
            reverse.connect(edge.getDest(), edge.getSrc(), edge.getWeight()); // src = dest, dest = src
        }

        return runDFS(this.graph) && runDFS(reverse);
    }

    private boolean runDFS(DirectedWeightedGraphImp graph) {
        Iterator<NodeData> iterator = graph.nodeIter();
        while (iterator.hasNext()) { //setting the tag of all nodes to be 0
            iterator.next().setTag(0);
        }
        Iterator<NodeData> iterator2 = graph.nodeIter();
        while (iterator2.hasNext()) {
            NodeData x = iterator2.next();
            if (x.getTag() == 0) {
                DFS(x, graph);
            }
        }
        iterator = graph.nodeIter();
        while (iterator.hasNext()) { //if one of the nodes has a tag==0 the graph is not connected
            if (iterator.next().getTag() == 0)
                return false;
        }
        return true;
    }

    private void DFS(NodeData x, DirectedWeightedGraphImp graph) {
        x.setTag(1);
        Stack<NodeData> stack = new Stack<>();
        stack.push(x);
        while (!stack.empty()) {
            x = stack.peek();
            stack.pop();
            Iterator<EdgeData> edgeIter = graph.edgeIter(x.getKey());
            while (edgeIter.hasNext()) {
                NodeData node = graph.getNode(edgeIter.next().getDest());
                if (x.getTag() == 0) {
                    stack.push(node);
                }
            }
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest){
            return 0.0;
        }
        HashMap<NodeData[], Double> res = DijkstraAlgo(this.graph, src, dest);
        Map.Entry<NodeData[], Double> entry = res.entrySet().iterator().next();
        return entry.getValue();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (src == dest){
            return null;
        }
        HashMap<NodeData[], Double> res = DijkstraAlgo(this.graph, src, dest);
        Map.Entry<NodeData[], Double> entry = res.entrySet().iterator().next();
        NodeData[] prev = entry.getKey();
        NodeData nd = prev[dest];
        List<NodeData> path = new ArrayList<>();

        while (prev[nd.getKey()] != null) {
            path.add(0, nd);
            nd = prev[nd.getKey()];
        }
        if (nd != null) path.add(0, nd);
        path.add(this.graph.getNode(dest));
        return path;
    }

    public HashMap<NodeData[], Double> DijkstraAlgo(DirectedWeightedGraphImp graph, int src, int dest) {
        List<Integer> visit = new ArrayList<>();
        double[] dist = new double[graph.nodeSize()];
        NodeData[] prev = new NodeData[graph.nodeSize()];
        for (int i = 0; i < dist.length; i++) {
            visit.add(i);
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
        }
        dist[src] = 0;

        while (!visit.isEmpty()) {
            int lowerIndex = 0;
            double lowerValue = dist[visit.get(lowerIndex)];
            for (int i = 1; i < visit.size(); i++) {
                if (lowerValue > dist[visit.get(i)]) {
                    lowerIndex = i;
                    lowerValue = dist[visit.get(i)];
                }
            }

            Iterator<EdgeData> edgeIter = graph.edgeIter(visit.get(lowerIndex));
            while (edgeIter.hasNext()) {
                EdgeData ed = edgeIter.next();
                double alt = dist[visit.get(lowerIndex)] + ed.getWeight();
                if (alt < dist[ed.getDest()]) {
                    dist[ed.getDest()] = alt;
                    prev[ed.getDest()] = graph.getNode(visit.get(lowerIndex));
                }
            }
            visit.remove(lowerIndex);
        }
        HashMap<NodeData[], Double> ret = new HashMap<NodeData[], Double>();
        ret.put(prev, dist[dest]);
        return ret;
    }

    @Override
    public NodeData center() {
        if (!isConnected()) { //if the graph isn't connected then we return null (there is no center)
            return null;
        }
        double maxDis = Double.MAX_VALUE; //max value so we can find the shortest path
        int nodeKey = 0;
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) { //finding the max shortest path to all others nodes
            int src = nodeIter.next().getKey();
            double maxShortPath = 0;
            Iterator<NodeData> nodeIter2 = this.graph.nodeIter();
            while (nodeIter2.hasNext()) { //finding the shortest path for each node
                NodeData dst = nodeIter2.next();
                if (dst != graph.getNode(src)) {
                    double checkPath = shortestPathDist(src, dst.getKey());
                    if (checkPath > maxShortPath) {
                        maxShortPath = checkPath;
                    }
                }
            }
            if (maxShortPath < maxDis) {
                maxDis = maxShortPath;
                nodeKey = src; // setting the center node of the graph
            }
        }
        return this.graph.getNode(nodeKey);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities == null ||cities.isEmpty()) { //if cities list is empty return null
            return null;
        }
        if( !isConnected()){
            return null;
        }
        List<NodeData> salesman = new ArrayList<>();
        NodeData start = cities.get(0);
        salesman.add(start); //adding to the list the starting city
        for (int i = 1; i < cities.size(); i++) { //loop on all the cities and find for them the shortest path
            NodeData dst = cities.get(i);
            if (salesman.contains(dst)) {
                continue;
            }
            List<NodeData> path = shortestPath(start.getKey(), dst.getKey());
            for (int j = 0; j < path.size(); j++) { //loop on all nodes that are part of the path from one city to another
                if (path.get(j) != start) {
                    salesman.add(path.get(j)); //add them to the list
                }
            }
            start = dst; //update the starting city for the next iteration
        }
        return salesman;
    }

    public boolean checkCities(List<NodeData> cities){
        for (int i = 0; i < cities.size(); i++) {
            if (shortestPathDist(cities.get(0).getKey(),cities.get(i).getKey()) <= 0.0){
                return false;
            }
        }
        for (int i = 0; i < cities.size(); i++) {
            if (shortestPathDist(cities.get(i).getKey(),cities.get(0).getKey()) <= 0.0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(String file) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        JsonSerializer<DirectedWeightedGraphImp> serializer = new JsonSerializer<DirectedWeightedGraphImp>() {
            @Override
            public JsonElement serialize(DirectedWeightedGraphImp graph, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonGraph = new JsonObject();
                jsonGraph.add("Edges", new JsonArray());
                jsonGraph.add("Nodes", new JsonArray());
                Iterator<EdgeData> edgeIter = graph.edgeIter();
                while (edgeIter.hasNext()) {
                    JsonObject jsonEdgeObject = new JsonObject();
                    EdgeData edge = edgeIter.next();
                    jsonEdgeObject.addProperty("src", edge.getSrc());
                    jsonEdgeObject.addProperty("w", edge.getWeight());
                    jsonEdgeObject.addProperty("dest", edge.getDest());
                    jsonGraph.get("Edges").getAsJsonArray().add(jsonEdgeObject);
                }

                Iterator<NodeData> nodeIter = graph.nodeIter();
                while (nodeIter.hasNext()) {
                    JsonObject jsonNodeObject = new JsonObject();
                    NodeData node = nodeIter.next();
                    String pos = "" + node.getLocation().x() +
                            ',' +
                            node.getLocation().y() +
                            ',' +
                            node.getLocation().z();
                    jsonNodeObject.addProperty("pos", pos);
                    jsonNodeObject.addProperty("id", node.getKey());
                    jsonGraph.get("Nodes").getAsJsonArray().add(jsonNodeObject);

                }
                return jsonGraph;
            }
        };
        gsonBuilder.registerTypeAdapter(DirectedWeightedGraphImp.class, serializer);
        Gson graphGson = gsonBuilder.create();
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.write(graphGson.toJson(this.graph));
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            line = br.readLine();
            while (line != null) {
                jsonString.append(line);
                line = br.readLine();
            }
            br.close();

            GsonBuilder gsonBuilder = new GsonBuilder();
            // change serialization for specific types
            JsonDeserializer<DirectedWeightedGraphImp> deserializer = new JsonDeserializer<DirectedWeightedGraphImp>() {
                @Override
                public DirectedWeightedGraphImp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonObject jsonObject = json.getAsJsonObject();
                    DirectedWeightedGraphImp graph = new DirectedWeightedGraphImp();
                    JsonArray Edges = jsonObject.getAsJsonArray("Edges");
                    JsonArray Nodes = jsonObject.getAsJsonArray("Nodes");
                    Iterator<JsonElement> iterNodes = Nodes.iterator();
                    while (iterNodes.hasNext()) {
                        JsonElement node = iterNodes.next();
                        graph.addNode(new NodeDataImp(node.getAsJsonObject().get("id").getAsInt()));
                        String coordinates = node.getAsJsonObject().get("pos").getAsString();
                        GeoLocation pos = new GeoLocationImp(coordinates);
                        graph.getNode(node.getAsJsonObject().get("id").getAsInt()).setLocation(pos);
                    }

                    Iterator<JsonElement> iterEdges = Edges.iterator();
                    int src, dest;
                    double w;
                    while (iterEdges.hasNext()) {
                        JsonElement edge = iterEdges.next();
                        src = edge.getAsJsonObject().get("src").getAsInt();
                        dest = edge.getAsJsonObject().get("dest").getAsInt();
                        w = edge.getAsJsonObject().get("w").getAsDouble();
                        graph.connect(src, dest, w);
                    }
                    return graph;
                }
            };

            gsonBuilder.registerTypeAdapter(DirectedWeightedGraphImp.class, deserializer);
            Gson graphGson = gsonBuilder.create();
            this.graph = graphGson.fromJson(jsonString.toString(), DirectedWeightedGraphImp.class);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

