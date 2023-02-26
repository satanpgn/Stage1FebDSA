import java.util.*;

 class Answer1b {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res=new ArrayList<>();

        List<int[]> heights=new ArrayList<>();

        transformBuilding(buildings,heights);

        //if heights of 2 points are same then place the point with smaller height first else place point with smaller starting point

        Collections.sort(heights,(a, b)->(a[0]==b[0]) ? a[1]-b[1] : a[0]-b[0]);// TC->O(nlog n)

        PriorityQueue<Integer> pq=new PriorityQueue<Integer>((a, b)->(b-a));

        //seeding the Priority Queue
        pq.add(0);
        int prevMax=0;

        for(int[] height:heights){ //O(n)

            if(height[1]<0){
                pq.add(-height[1]);
            }
            else{
                pq.remove(height[1]); //O(log n)
            }

            int CurrentMax=pq.peek();
            if(CurrentMax!=prevMax)
            {
                List<Integer> subResult=new ArrayList<>();
                subResult.add(height[0]);
                subResult.add(CurrentMax);

                res.add(subResult);
                prevMax=CurrentMax;
            }
        }

        return res;
    }
    //this will seperate the values of start point and end point with height
    //example-->[1,2,3]-->[1,-3] & [2,3]-->here -(minus) is just for convention for starting point
    private void transformBuilding(int[][] buildings,List<int[]> heights)
    {
        for(int[] building:buildings)
        {
            heights.add(new int[]{building[0],-building[2]});
            heights.add(new int[]{building[1],building[2]});
        }
    }
}
//Qn1 b>  Assume you were hired to create an application for an ISP,
// and there is n number of network devices, such as routers,
// that are linked together to provides internet access to home user users.
// You are given a 2D array that represents network connections between these network devices
// such that a[i]=[xi,yi] where xi is connected to yi device.
// Suppose there is a power outage on a certain device provided as int n represents id of
// the device on which power failure occurred)), Write an algorithm to return impacted network devices
// due to breakage of the link between network devices. These impacted device list assists you notify linked
// consumers that there is a power outage, and it will take some time to rectify an issue.
//  Note that: node 0 will always represent a source of internet or gateway to international network.



class Answers1b {
    public static List<Integer> findImpactedDevices(int n, int[][] connections, int failedDevice) {
        // Create adjacency list for network connections
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int[] conn : connections) {
            int x = conn[0], y = conn[1];
            adjList.putIfAbsent(x, new ArrayList<>());
            adjList.putIfAbsent(y, new ArrayList<>());
            adjList.get(x).add(y);
            adjList.get(y).add(x);
        }

        // Perform DFS traversal to find impacted devices
        Set<Integer> visited = new HashSet<>();
        dfs(0, adjList, visited, failedDevice);

        // Return all devices except those in visited set
        List<Integer> impactedDevices = new ArrayList<>();
        for (int device : adjList.keySet()) {
            if (!visited.contains(device)) {
                impactedDevices.add(device);
            }
        }
        return impactedDevices;
    }

    private static void dfs(int device, Map<Integer, List<Integer>> adjList, Set<Integer> visited, int failedDevice) {
        visited.add(device);
        for (int neighbor : adjList.get(device)) {
            if (!visited.contains(neighbor) && !(device == failedDevice && neighbor == 0) && neighbor != failedDevice) {
                dfs(neighbor, adjList, visited, failedDevice);
            }
        }
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] connections = {{0,1}, {0,2}, {1,3}, {1,6}, {2,4}, {4,6}, {4,5}, {5,7}};
        int failedDevice = 4;
        List<Integer> impactedDevices = findImpactedDevices(n, connections, failedDevice);
        System.out.println("Impacted devices: " + impactedDevices);
    }
}

