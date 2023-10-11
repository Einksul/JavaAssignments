package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        int vertices = StdIn.readInt();
        double[] cost = new double[vertices];
        int[][] wormhole = new int[vertices][vertices];
        createWeightedMatrix(cost, wormhole, vertices);
        StdOut.print(Dijkstra(0, vertices-1, wormhole));
        
       
    }
    public static void createWeightedMatrix(double[] cost, int[][] wormhole, int vertices)
    {
        for(int i = 0; i < vertices; i++)
        {
            int generator = StdIn.readInt();
            double functProb = StdIn.readDouble();
            cost[generator] = functProb;
        }
        for(int i = 0; i<vertices; i++)
        {
            for(int j = 0; j < vertices; j++)
            {
                wormhole[i][j] = StdIn.readInt();
            }
           
        }

        for(int i = 0; i < vertices; i++)
        {
            for(int j = 0; j < vertices; j++)
            {
                int path = wormhole[i][j];
                double totalCostD = path/(cost[i]*cost[j]);
                int totalcostI = (int) totalCostD;
                wormhole[i][j] = totalcostI;
            }
        }
    }
    
    
    public static int Dijkstra(int start, int end, int[][] wormhole)
    {   
        int[] min = new int[wormhole.length];
        boolean[] DSet = new boolean[wormhole.length];
        for(int i = 0; i < min.length; i++)
        {
           if(i == 0) min[i]=0;
           else min[i] = Integer.MAX_VALUE;
        } 
        for(int i = 0; i < wormhole.length-1; i++)
        {
            int source = minVal(min,DSet);
            DSet[source] = true;
            for(int j = 0; j < wormhole.length; j++)
            {
                if(wormhole[source][j]!=0)
                {
                    if(!DSet[j] && min[source]!=Integer.MAX_VALUE && min[j] > (min[source] + wormhole[source][j]))
                    {
                        min[j] = min[source] + wormhole[source][j];
                    }
                }
            }
        }
        return min[min.length-1];
    }
    
    
    public static int minVal(int[] arr, boolean[]Dset)
    {
        int min = Integer.MAX_VALUE;
        int index =0;
        for(int i =1; i<arr.length; i++)
        {
            if(!Dset[i])
            {
                if (arr[i] < min)
                {
                    min = arr[i];
                    index = i;
                }
            }
        }
        return index;
    }
}
