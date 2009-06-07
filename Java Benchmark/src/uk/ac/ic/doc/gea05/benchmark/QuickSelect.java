package uk.ac.ic.doc.gea05.benchmark;

public class QuickSelect{
/**
     * Quick selection algorithm.
     * Places the kth smallest item in a[k-1].
     * @param <C>
     * @param a an array of Comparable items.
     * @param k the desired rank (1 is minimum) in the entire array.
     */
    public static void quickSelect(int [ ] a, int k ) {
        quickSelect( a, 0, a.length - 1, k );
    }
    
    /**
     * Internal selection method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * Places the kth smallest item in a[k-1].
     * @param a an array of int items.
     * @param low the left-most index of the subarray.
     * @param high the right-most index of the subarray.
     * @param k the desired rank (1 is minimum) in the entire array.
     */
    private static void quickSelect( int [ ] a, int low, int high, int k ) {
        if( low + CUTOFF > high )
            insertionSort( a, low, high );
        else {
            // Sort low, middle, high
            int middle = ( low + high ) / 2;
            if( a[ middle ] < a[ low ])
                swapReferences( a, low, middle );
            if( a[ high ]<a[ low ] )
                swapReferences( a, low, high );
            if( a[ high ]<a[ middle ])
                swapReferences( a, middle, high );
            
            // Place pivot at position high - 1
            swapReferences( a, middle, high - 1 );
            int pivot = a[ high - 1 ];
            
            // Begin partitioning
            int i, j;
            for( i = low, j = high - 1; ; ) {
                while( a[ ++i ] < pivot)
                    ;
                while( pivot< a[ --j ] )
                    ;
                if( i >= j )
                    break;
                swapReferences( a, i, j );
            }
            
            // Restore pivot
            swapReferences( a, i, high - 1 );
            
            // Recurse; only this part changes
            if( k <= i )
                quickSelect( a, low, i - 1, k );
            else if( k > i + 1 )
                quickSelect( a, i + 1, high, k );
        }
    }
    
    
    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of int items.
     * @param low the left-most index of the subarray.
     * @param n the number of items to sort.
     */
    private static void insertionSort( int [ ] a, int low, int high ) {
        for( int p = low + 1; p <= high; p++ ) {
            int tmp = a[ p ];
            int j;
            
            for( j = p; j > low && tmp < a[ j - 1 ]; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }
    
    
    private static final int CUTOFF = 10;
    
    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static final void swapReferences( int[] a, int index1, int index2 ) {
        int tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }
    }