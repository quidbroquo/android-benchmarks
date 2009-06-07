package uk.ac.ic.doc.gea05.benchmark;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import android.os.AsyncTask;
import android.util.Log;
// TODO Make a seperate benchmask task that uses the benchmarkable interface
/*
public class BenchmarkTask extends AsyncTask<Benchmarkable, Void, String> {

	private SortedMap<Integer,Long> times;
			
	@Override
	protected String doInBackground(Integer... ids) {
		times = new TreeMap<Integer,Long>();
		
		Random rand = new Random();
		int[] unsorted;
		int i, j, median;
		long start = 0;
		long finish = 0;
		long duration;
		String mode = "Unknown";
		for (i = step; i <= maxPixels; i += step) {
			unsorted = new int[i];

			for (j = 0; j < i; j++) {
				unsorted[j] = rand.nextInt();
			}

			switch (ids[0]) {
			case R.id.button_java:
				mode = "Dalvik";
				start = System.currentTimeMillis();
				QuickSort.quicksort(unsorted);
				finish = System.currentTimeMillis();
				break;
			case R.id.button_jni:
				mode = "JNI";
				start = System.currentTimeMillis();
				NativeQuickSort.nQuicksort(unsorted);
				finish = System.currentTimeMillis();
				break;
			}
			median = unsorted[(unsorted.length - 1) / 2];
			duration = (finish - start);
			Log.i(TAG, String.format("Elements: %d Time: %d Median: %d", i,
					(finish - start), median));
			times.put(i, duration);
		}
		
		publishResults(times,mode);
		return String.format("Finished : %d", ++executed);
	}
}
*/