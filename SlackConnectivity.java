import java.util.*;

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int[] size;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    // Path Compression
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union by Rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY)
            return;

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
        }
        else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        }
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
            size[rootX] += size[rootY];
        }
    }

    public int getComponentSize(int node) {
        return size[find(node)];
    }
}

public class SlackConnectivity {

    public static void main(String[] args) {

        int channels = 12000;

        UnionFind uf = new UnionFind(channels);

        // Sample channel-sharing edges
        int[][] edges = {
                {0,1},
                {1,2},
                {2,3},
                {4,5},
                {5,6},
                {7,8},
                {8,9},
                {9,10},
                {10,11}
        };

        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        Set<Integer> components = new HashSet<>();

        for (int i = 0; i < channels; i++) {
            components.add(uf.find(i));
        }

        int largestComponent = 0;

        for (int i = 0; i < channels; i++) {
            largestComponent =
                    Math.max(largestComponent,
                             uf.getComponentSize(i));
        }

        System.out.println("Slack Workspace Connectivity Analysis");
        System.out.println("-------------------------------------");
        System.out.println("Total Channels : " + channels);
        System.out.println("Connected Components : "
                + components.size());
        System.out.println("Largest Component Size : "
                + largestComponent);
    }
}
