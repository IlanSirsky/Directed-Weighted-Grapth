# Directed Weighted Graph

*Created by Eldad Tsemach, Ilan Sirisky and Nir Meir *

This project is a part of our Object Oriented Programming course in Java. The above project deals with the
implementation of a Directed Weighted graph, using Nodes and Edges. As well as implementing a number of algorithms on
the graph.

![](https://i.imgur.com/9orEkmf.png)

## List of algorithms

1. Checking if a DW graph is strongly connected by using the DFS algorithm.

2. Finding the shortest path between source and destination nodes by using Dijkstra algorithm.

3. Finding the center of a DW graph which minimizes the max distance to all the other nodes.
4. Computing a list of consecutive nodes which go over a list of nodes and finding the least costing path between all
   the nodes, similar to the Traveling Salesman Problem but without the limitaion of visiting each node only once.
5. Saving and loading a graph to and from a .json file which contains an array of Edges and Nodes.

The Dijkstra algorithm finds the shortest path between two nodes in a DW graph. For further reading
see: https://en.wikipedia.org/wiki/Dijkstras_algorithm.

Example of the algorithm:

![gif](https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Dijkstra_Animation.gif/220px-Dijkstra_Animation.gif)

## Code Description

- `GeoLocationImp.java` and `NodeDataImp.java` : Implements and represents the vertices of the graph.
- `EdgeDataImp.java`: Implements and represents the edges of the graph.
- `WeightedDirectedGraphImp.java`: Implements the graph itself, by using 2 hashmaps one for NodeData and the other for
  EdgeData.
- `Algorithms.java`: Implements all the algorithms that are listed above.
- `GUI.java`, `LoadScreen.java`, `AddScreen.java`, `RemoveScreen.java`, `ShortPathScreen.java` and `TspScreen.java`: are all part of the GUI.

## Dependencies

This project is using Java version 15. The tests included in this project are on JUnit version 5.7. Reading and writing
the json files are based on Gson version 2.8.6.

# How to Run

To run this project, download th EX2.jar and /data folder, and place them in the same folder.

- Run from CLI After you have downloaded the files neccessary (Ex2.jar, /data), open a terminal at the
  current folder, and type:
  `java -jar Ex2.jar <path\G1.json>`, or any json file.

## Input/Output Examples

### Example for G1.json input :

![Building](https://i.imgur.com/LohNcL8.png)| ![](https://i.imgur.com/MQzNuCr.png)

##### In Edges :

- *src* : the ID of the source node.
- *w* : the weight of the edge.
- *dest* : the ID of the destination node.

##### In Nodes :

- *pos* : containing the GeoLocation of the node based on x,y,z.
- *id* : the ID of the node.

### Example of the graph in G1.json :

![](https://i.imgur.com/yZtvaeh.png)

# Analyzing running time

|Number of nodes|Building the graph|isConnected|Center|
|---------|---------|---------|---------|
|G1.json : 17|59 ms|67 ms|71 ms|
|G2.json : 31|60 ms|77 ms|93 ms|
|G3.json : 48|69 ms|75 ms|177 ms|
|1000|157 ms|188 ms|4 min 43 sec|
|10000|473 ms|626 ms|11 min 24 sec|
|100000|7 sec 63 ms|10 sec 190 ms|timeout|
|1000000| No memory|

## UML

![](https://i.imgur.com/xlBFwP7.png)

Link to the main assignment: https://github.com/benmoshe/OOP_2021/tree/main/Assignments/Ex2.
