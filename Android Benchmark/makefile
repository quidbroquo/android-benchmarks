CC = /home/gavin/CodeSourcery/Sourcery_G++_Lite/bin/arm-none-linux-gnueabi-gcc
LINKER = /home/gavin/CodeSourcery/Sourcery_G++_Lite/bin/arm-none-linux-gnueabi-ld
INCLUDES = -I/usr/lib/jvm/java-6-sun/include -I/usr/lib/jvm/java-6-sun/include/linux -I /native -I/home/gavin/Work/workspace/android/frameworks/base/include -I/home/gavin/Work/workspace/android/system/core/include

all : libNative.so

libNative.so : Native.o
	$(LINKER) -T native/armelf_linux_eabi.xsc -shared -o assets/native/libNative.so native/uk_ac_ic_doc_gea05_benchmark_NativeQuickSort.o	

Native.o : Native.h native/uk_ac_ic_doc_gea05_benchmark_NativeQuickSort.c
	$(CC) $(INCLUDES) -O2 -Wall -fpic -c native/uk_ac_ic_doc_gea05_benchmark_NativeQuickSort.c -o native/uk_ac_ic_doc_gea05_benchmark_NativeQuickSort.o

Native.h :
	javah -jni -d native -classpath bin/ uk.ac.ic.doc.gea05.benchmark.NativeQuickSort	
		
clean :
	-del /native/%.h
	-del /native/%.o
	