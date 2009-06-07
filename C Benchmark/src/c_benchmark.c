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


//median of 3 quick sort.

void exchange(int list[], int p, int q)
{
	int temp = list[p];
	list[p] = list[q];
	list[q] = temp;
}

int partition(int list[], int p, int r)
{
	int x = list[r];
	int i = p-1;
	int j;
	for(j=p; j<r; j++)
	{
		if(list[j] <= x)
		{
			i++;
			exchange(list, i, j);
		}
	}
	exchange(list, i+1, r);
	return i+1;
}

int median_of_3(int list[], int p, int r)
{
	int median = (p + r) / 2;

	if(list[p] > list[r])
		exchange(list, p, r);
	if(list[p] > list[median])
		exchange(list, p, median);
	if(list[r] > list[median])
		exchange(list, r, median);

	return list[r];
}

void quicksort(int list[], int p, int r)
{
	if(p < r)
	{
		median_of_3(list, p, r);

		int q = partition(list, p, r);
		quicksort(list, p, q-1);
		quicksort(list, q+1, r);
	}
}


const int maxPixels = 100000;
const int step = 100;

int main() {

	struct timeval tv;

	time_t start;
	time_t stop;

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
		printf("Elements: %d Time: %d Median: %d \n",i,(unsigned)((stop-start)/1000),median);
	}

	return 0;
}


