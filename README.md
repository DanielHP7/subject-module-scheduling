# Subject Module Scheduling with Graph Algorithms

At Roskilde University, students combine two subject modules, for example Computer Science with Philosophy. If two modules share students, they cannot run in the same timeslot. This Java project models every module combination as a weighted graph and uses graph algorithms to find out how few timeslots are needed so that no student has a clash.

> Pair project for the Software Development course at Roskilde University (November 2025), by Mads Degn and Daniel Holst Pedersen.
> Original repository: [madsdegn/SDPortfolio03](https://github.com/madsdegn/SDPortfolio03)

**Tech:** Java 21 · graph algorithms (BFS, greedy graph colouring) · Maven

---

## The data

`combi.txt` lists how many students take each pair of subject modules:

```
PERF , KOMM , 286
VIRK , KOMM , 240
PSYK , KOMM , 220
```

That gives a graph with **27 subject modules (vertices)** and **215 combinations (edges)**. Each edge is weighted by the number of students who take both modules.

## What each part does

| Part | Question | Approach |
|---|---|---|
| **1** | What does the graph look like? | Reads `combi.txt` into an adjacency-list graph and prints every module with its neighbours and weights. |
| **2** | Is every module linked to every other through students? | Breadth-first search from one vertex, O(V + E). If not every module is reached, it lists the separate components. |
| **3** | Which modules can share a timeslot? | Greedy graph colouring. It repeatedly builds a group of modules with no students in common (an independent set) until every module is placed. Each group is one timeslot. |
| **4** | How strongly are the timeslots linked? | Builds a new graph where each group from Part 3 is a vertex. The edge weight is the number of students who take modules in both groups. |

## Results

- **The graph is connected.** Every module is linked to every other through chains of shared students, so the problem cannot be split into independent parts.
- **About 12 timeslots are needed.** The greedy algorithm visits the modules in random order, so the result varies between runs. Over 5,000 runs it needed 11–14 groups, most often 12.

Sample output from Part 3:

```
Group 1: [GEO, ENG, SUNDHFR, MAT]
Group 2: [SOC, MEDBIO, DATA]
Group 3: [KEMI, PERF, PA]
Group 4: [FYSIK, MOLBIO, TEKSAM, PÆD]
...
Group 12: [HIST]

Number of groups (timeslots needed): 12
```

---

## Running it

Requires Java 21. Run the commands from the project root, so the programs can find `combi.txt`.

```bash
javac -d out src/main/java/com/example/*.java
java -cp out com.example.Part1   # the graph
java -cp out com.example.Part2   # connectivity
java -cp out com.example.Part3   # timeslot groups
java -cp out com.example.Part4   # graph of groups
```

You can also open the project in IntelliJ IDEA as a Maven project and run each `PartX` class.

```
├── combi.txt                         # Module combinations and student counts
└── src/main/java/com/example/
    ├── Part1.java … Part4.java       # Our solutions
    ├── Graphs.java                   # Graph data structures (provided by the course)
    └── GraphAlgorithms.java          # BFS, DFS, MST, Dijkstra, file reading (provided by the course)
```

## Credits

`Graphs.java` and `GraphAlgorithms.java` were provided by the course staff. `Part1`–`Part4` are our own work, built on top of them.

**Possible improvements:** sorting modules by number of neighbours before colouring (Welsh–Powell) finds 11 groups on this data, one fewer than a typical random run. Unit tests could also check that no group contains two connected modules.

## License

MIT. See [LICENSE](LICENSE).
