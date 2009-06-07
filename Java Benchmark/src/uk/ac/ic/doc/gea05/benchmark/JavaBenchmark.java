package uk.ac.ic.doc.gea05.benchmark;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class JavaBenchmark {
	// Average the result of this many tests
	private static final int tests = 200;
	// To remove outliers we take the median of this many results in each 'test'
	private static final int internalTests = 5;

	private static final int maxPixels = 100000;
	private static final int step = 1000;
	private static SortedMap<Integer, Double> times;
	private static Random rand;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		times = new TreeMap<Integer, Double>();
		rand = new Random();
		int[] unsorted;
		
		long start, finish, duration;
		long[] internalResults = new long[internalTests];
		
		for (int i = step; i <= maxPixels; i += step) {
			unsorted = new int[i];

			long totalDuration = 0;
			
			for (int j = 0; j < tests; j++) {
				for(int k = 0; k < internalTests; k++){
					for (int l = 0; l < i; l++) {
						unsorted[l] = rand.nextInt();
					}

					start = System.currentTimeMillis();
					QuickSort.quicksort(unsorted);
					finish = System.currentTimeMillis();
					duration = finish - start;
					internalResults[k] = duration;					
				}
				QuickSelect.quickSelect(internalResults, internalTests/2);
				totalDuration += internalResults[(internalTests/2)-1];
			}
			double averageDuration = ((double)totalDuration) / tests;
			
			System.out.println(String.format(
					"Elements: %d Time: %f", i, averageDuration));
			times.put(i, averageDuration);
		}
		publishResults(times);
		System.out.println("Finished publishing results");
		closeResults();
	}

	private static void closeResults() {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				System.out.println("Couldn't close file");
				e.printStackTrace();
			}
			out = null;
		}
	}

	// Logging
	// For the logs
	private static BufferedWriter out;

	// csv
	private static void publishResults(SortedMap<Integer, Double> times) {
		StringBuilder builder = new StringBuilder();
		builder.append("elements, Java,\n");
		for (Integer inputSize : times.keySet()) {
			builder.append(String.format("%d,%f", inputSize, times
					.get(inputSize)));
		}
		writeToLog(builder.toString());
	}

	private static final String RESULTS_DIRECTORY = "/home/gavin/Work/benchmark/Results/";

	private static void writeToLog(String msg) {
		try {
			if (out == null) {
				System.out.println("Creating new file");
				File benchmarkLog = new File(RESULTS_DIRECTORY,
						"Java_Benchmark.csv");
				benchmarkLog.createNewFile();
				FileWriter writer = new FileWriter(benchmarkLog);
				out = new BufferedWriter(writer);
			}
			out.write(msg + "\n");
		} catch (IOException e) {
			System.out.println("Could not write file");
		}
	}
}
