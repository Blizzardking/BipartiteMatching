import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TestBipartiteMatching {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] edges = null;
		int numberOfX = 0, numberOfY = 0, numberOfEdges =0;
		try {
			//make a 'file' object
			File file = new File("matching-1k.txt");  
			//Get data from this file using a file reader.
			//To store the contents read via File Reader
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line0 = input.readLine();
			String[] items= line0.split("\\s+");
			numberOfX = Integer.parseInt(items[0]);
			numberOfY = Integer.parseInt(items[1]);
			numberOfEdges = Integer.parseInt(items[2]);
			edges = new int[numberOfEdges][3];
			readEdges( input, edges, numberOfX);
		} catch(Exception e) {
			e.printStackTrace();
		}
		BipartiteMatching graph = new BipartiteMatching(edges, numberOfX, numberOfX + numberOfY);
	//	graph.printWeightedEdges();
		long time1 = System.currentTimeMillis();
		int output = graph.findMaximumMatching();
		long time2 = System.currentTimeMillis();
		System.out.printf("Hopcraft-Karp algorithm\n" +
				"Sample output:%d\n" +
				"Elapsed time(ms):%d\n\n", output, time2-time1);
		
	}
	public static void readEdges( BufferedReader in, int[][] edges, int numberOfX) throws IOException
    {
            String line;
            int i = 0;
            while( ( line = in.readLine( ) ) != null ) {
            		String[] items= line.trim().split("\\s+");
            		edges[i][0] = Integer.parseInt(items[0])-1;
            		edges[i][1] = Integer.parseInt(items[1]) + numberOfX -1 ;
            		edges[i][2] = Integer.parseInt(items[2]);
            		i++;
            }

    } 

}
