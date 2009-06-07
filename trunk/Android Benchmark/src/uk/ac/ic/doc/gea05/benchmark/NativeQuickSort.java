package uk.ac.ic.doc.gea05.benchmark;

public class NativeQuickSort implements Benchmarkable{
	static{
		NativeMediator.load(QuickSort.class);
	}
	
    public static native void nQuicksort( int [ ] a);


	@Override
	public void benchmark(Object... args) {
		// TODO Auto-generated method stub
		
	}
}
