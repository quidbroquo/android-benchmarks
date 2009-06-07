package uk.ac.ic.doc.gea05.benchmark;


public class QuickSort {
	static{
		NativeMediator.load(QuickSort.class);
	}
	/**
     * Quicksort algorithm.
     * @param a an array of Comparable items.
     */
    public static void quicksort( int [ ] a ) {
        quicksort( a, 0, a.length - 1 );
    }
    
    private static final int CUTOFF = 10;
    
    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * @param a an array of int items.
     * @param low the left-most index of the subarray.
     * @param high the right-most index of the subarray.
     */
    private static int i, middle;
    private static void quicksort( int [ ] a, int low, int high ) {
        if( low + CUTOFF > high )
            insertionSort( a, low, high );
        else {
            // Sort low, middle, high
            middle = ( low + high ) / 2;
            if( a[ middle ] <  a[ low ]  )
                swapReferences( a, low, middle );
            if( a[ high ] <  a[ low ]  )
                swapReferences( a, low, high );
            if( a[ high ] <  a[ middle ]  )
                swapReferences( a, middle, high );
            
            // Place pivot at position high - 1
            swapReferences( a, middle, high - 1 );
            int pivot = a[ high - 1 ];
            
            // Begin partitioning
            for( i = low, j = high - 1; ; ) {
                while( a[ ++i ] <  pivot  )
                    ;
                while( pivot <  a[ --j ]  )
                    ;
                if( i >= j )
                    break;
                swapReferences( a, i, j );
            }
            
            // Restore pivot
            swapReferences( a, i, high - 1 );
            
            quicksort( a, low, i - 1 );    // Sort small elements
            quicksort( a, i + 1, high );   // Sort large elements
        }
    }
    
    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static final void swapReferences( int [ ] a, int index1, int index2 ) {
        tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }
    
    
    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of int items.
     * @param low the left-most index of the subarray.
     * @param n the number of items to sort.
     */
    private static int p, tmp, j;
    private static void insertionSort( int [ ] a, int low, int high ) {
        for(p = low + 1; p <= high; p++ ) {
            tmp = a[ p ];
            
            for( j = p; j > low && tmp < a[ j - 1 ] ; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }
    

    public static native void nQuicksort( int [ ] a);
   
}
