package uk.ac.ic.doc.gea05.benchmark;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Benchmark extends Activity implements OnClickListener {
	private static final String TAG = "Benchmark";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupViews();
	}
	
	private TextView tv;
	private Button javaButton;
	private Button jniButton;

	private void setupViews(){
		tv = (TextView) findViewById(R.id.textview);
		javaButton = (Button) findViewById(R.id.button_java);
		jniButton = (Button) findViewById(R.id.button_jni);
		javaButton.setOnClickListener(this);
		jniButton.setOnClickListener(this);
	}
	
	private static final int maxPixels = 100000;
	private static final int step = 100;

	private int executed = 0;
	
	public class BenchmarkTask extends AsyncTask<Integer, Void, String> {

		long start;
		long finish;
		@Override
		protected String doInBackground(Integer... ids) {
			Random rand = new Random();
			int[] unsorted;
			int i, j, median;
			long start = 0;
			long finish = 0;
			for (i = step; i <= maxPixels; i += step) {
				unsorted = new int[i];

				for (j = 0; j < i; j++) {
					unsorted[j] = rand.nextInt();
				}

				switch (ids[0]) {
				case R.id.button_java:
					start = System.currentTimeMillis();
					QuickSort.quicksort(unsorted);
					finish = System.currentTimeMillis();
					break;
				case R.id.button_jni:
					start = System.currentTimeMillis();
					QuickSort.nQuicksort(unsorted);
					finish = System.currentTimeMillis();
					break;
				}
				median = unsorted[(unsorted.length - 1) / 2];
				Log.i(TAG, String.format("Elements: %d Time: %d Median: %d", i,
						(finish - start), median));
			}
			return String.format("Finished : %d", ++executed);
		}
		@Override
		public void onPostExecute(String msg){
			changeText(msg);
		}
	}

	private void changeText(String msg){
		if(tv!=null){
			tv.setText(msg);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_java:
			new BenchmarkTask().execute(R.id.button_java);
			break;
		case R.id.button_jni:
			new BenchmarkTask().execute(R.id.button_jni);
			break;
		default:
			Toast.makeText(this,
					String.format("Unknown view clicked %d", v.getId()),
					Toast.LENGTH_SHORT).show();
		}

	}
}