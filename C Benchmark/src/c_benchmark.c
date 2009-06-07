/*
 ============================================================================
 Name        : C.c
 Author      : Gavin Aiken
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

#include <sys/time.h>

typedef int elem_type;

#define ELEM_SWAP(a,b) { register elem_type t=(a);(a)=(b);(b)=t; }

/*
 *  This Quickselect routine is based on the algorithm described in
 *  "Numerical recipes in C", Second Edition,
 *  Cambridge University Press, 1992, Section 8.5, ISBN 0-521-43108-5
 *  This code by Nicolas Devillard - 1998. Public domain.
 */

elem_type quick_select(elem_type arr[], int n)
{
    int low, high ;
    int median;
    int middle, ll, hh;

    low = 0 ; high = n-1 ; median = (low + high) / 2;
    for (;;) {
        if (high <= low) /* One element only */
            return arr[median] ;

        if (high == low + 1) {  /* Two elements only */
            if (arr[low] > arr[high])
                ELEM_SWAP(arr[low], arr[high]) ;
            return arr[median] ;
        }

    /* Find median of low, middle and high items; swap into position low */
    middle = (low + high) / 2;
    if (arr[middle] > arr[high])    ELEM_SWAP(arr[middle], arr[high]) ;
    if (arr[low] > arr[high])       ELEM_SWAP(arr[low], arr[high]) ;
    if (arr[middle] > arr[low])     ELEM_SWAP(arr[middle], arr[low]) ;

    /* Swap low item (now in position middle) into position (low+1) */
    ELEM_SWAP(arr[middle], arr[low+1]) ;

    /* Nibble from each end towards middle, swapping items when stuck */
    ll = low + 1;
    hh = high;
    for (;;) {
        do ll++; while (arr[low] > arr[ll]) ;
        do hh--; while (arr[hh]  > arr[low]) ;

        if (hh < ll)
        break;

        ELEM_SWAP(arr[ll], arr[hh]) ;
    }

    /* Swap middle item (in position low) back into correct position */
    ELEM_SWAP(arr[low], arr[hh]) ;

    /* Re-set active partition */
    if (hh <= median)
        low = ll;
        if (hh >= median)
        high = hh - 1;
    }
}

#undef ELEM_SWAP

const int CUTOFF = 10;
//median of 3 quick sort.

/**
 * Method to swap to elements in an array.
 * @param a an array of objects.
 * @param index1 the index of the first object.
 * @param index2 the index of the second object.
 */
int swap;
void swapReferences( int  a[ ], int index1, int index2 ) {
	swap = a[ index1 ];
    a[ index1 ] = a[ index2 ];
    a[ index2 ] = swap;
}


/**
 * Internal insertion sort routine for subarrays
 * that is used by quicksort.
 * @param a an array of int items.
 * @param low the left-most index of the subarray.
 * @param n the number of items to sort.
 */
int k,p,tmp;
void insertionSort(int a[], int low, int high ) {
    for(p = low + 1; p <= high; p++ ) {
        tmp = a[ p ];

        for( k = p; k > low && tmp < a[ k - 1 ] ; k-- )
            a[ k ] = a[ k - 1 ];
        a[ k ] = tmp;
    }
}

 /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * @param a an array of int items.
     * @param low the left-most index of the subarray.
     * @param high the right-most index of the subarray.
     */
	int middle,pivot,i,j;
    void quicksort( int a[], int low, int high ) {
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
            pivot = a[ high - 1 ];

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



const int maxPixels = 100000;
const int step = 1000;

int main() {
	FILE *file;
	file = fopen("/home/gavin/Work/benchmark/Results/C_Benchmark.csv","w+");

	struct timeval tv;

	time_t start;
	time_t stop;
	unsigned duration;

	int i,j,median;
	for (i = step; i <= maxPixels; i+= step){

		int unsorted[i];

		for(j = 0; j < i; j++){
			unsorted[j] = rand();
		}

		gettimeofday(&tv, 0);
		start = tv.tv_usec;
		quicksort(unsorted,0,i-1);
		gettimeofday(&tv, 0);
		median = unsorted[(i-1)/2];
		stop = tv.tv_usec;
		duration = (unsigned)((float)(stop-start)/1000)+0.5;
		printf("Elements: %d Time: %d Median: %d \n",i,duration,median);
		fprintf(file,"%d, %d,\n",i,duration);
	}

	return fclose(file);
}


