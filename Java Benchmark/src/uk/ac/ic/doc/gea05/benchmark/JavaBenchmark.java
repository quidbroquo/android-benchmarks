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
	private static final int tests = 10;
	private static final int maxPixels = 100000;
	private static final int step = 1000;
	private static SortedMap<Integer, Long> times;
	private static Random rand;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		times = new TreeMap<Integer, Long>();
		rand = new Random();
		int[] unsorted;
		int i, j;
		long start, finish, duration;
		long[] testResults = new long[tests];
		
		for (i = step; i <= maxPixels; i += step) {
			unsorted = new int[i];

			for (int t = 0; t < tests; t++) {
				for (j = 0; j < i; j++) {
					unsorted[j] = rand.nextInt();
				}

				start = System.currentTimeMillis();
				QuickSort.quicksort(unsorted);
				finish = System.currentTimeMillis();
				duration = finish - start;
				testResults[t] = duration;
			}
			QuickSelect.quickSelect(testResults, tests/2);
			duration = testResults[tests/2-1];
			System.out.println(String.format(
					"Elements: %d Time: %d", i, duration));
			times.put(i, duration);
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
	private static void publishResults(SortedMap<Integer, Long> times) {
		StringBuilder builder = new StringBuilder();
		builder.append("elements, Java,\n");
		for (Integer inputSize : times.keySet()) {
			builder.append(inputSize.toString() + ","
					+ times.get(inputSize).toString() + ",\n");
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
