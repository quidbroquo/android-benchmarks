package uk.ac.ic.doc.gea05.benchmark;

import java.util.Random;

public class Runner {
	private static final int maxPixels = 100000;
	private static final int step = 100;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random rand = new Random();
		int[] unsorted;
		int i,j,median;
		long start, finish;
		for (i = step; i <= maxPixels; i+= step){
			unsorted = new int[i];

			for(j = 0; j < i; j++){
				unsorted[j] = rand.nextInt();
			}

			start = System.currentTimeMillis();
			QuickSort.quicksort(unsorted);
			finish = System.currentTimeMillis();
			median = unsorted[(unsorted.length-1)/2];
			System.out.println(String.format("Elements: %d Time: %d Median: %d",i,(finish-start),median));
		}

	}

}
