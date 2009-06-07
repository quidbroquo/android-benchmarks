package uk.ac.ic.doc.gea05.benchmark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.util.Log;

/**
 * 
 * @author gavin
 */
public class NativeMediator {
	private static String TAG = "Native.java";
	
	// Native code related
    static Runtime mRuntime = Runtime.getRuntime();
    
    final static int BUFSIZE = 10000;
    final static String ZIP_FILTER = "assets/native";
    
    /*
     * Pass the main activity class
     * 
     * @param c Main Activity for of the application
     */
    public static void load(Class<?> c) {
    	String packageName = c.getPackage().getName();
        String mAppRoot =  "/data/data/" + packageName;
        String APK_PATH = "/data/app/"+ packageName +".apk";
        
        try
        {
                File zipFile = new File(APK_PATH);
                long zipLastModified = zipFile.lastModified();
                ZipFile zip = new ZipFile(APK_PATH);
                Vector<ZipEntry> files = librariesFilesFromZip(zip);
                int zipFilterLength = ZIP_FILTER.length();

                Enumeration<ZipEntry> entries = files.elements();
                while (entries.hasMoreElements())
                {
                        ZipEntry entry = entries.nextElement();
                        String path = entry.getName().substring(zipFilterLength);
                        File outputFile = new File(mAppRoot, path);
                        outputFile.getParentFile().mkdirs();

                        if (outputFile.exists() && entry.getSize() == outputFile.length() && zipLastModified < outputFile.lastModified())
                        {
                                Log.i(TAG,outputFile.getName() + " already extracted.");
                        }
                        else
                        {
                                FileOutputStream fos = new FileOutputStream(outputFile);
                                copyStreams(zip.getInputStream(entry), fos);
                                Log.i(TAG,"Copied " + entry + " to " + mAppRoot + "/" + path);
                                String curPath = outputFile.getAbsolutePath();
                                do
                                {
                                        mRuntime.exec("/system/bin/chmod 755 " + curPath);
                                        curPath = new File(curPath).getParent();
                                }
                                while (!curPath.equals(mAppRoot));
                        }
                        try {
                            Log.i("JNI", "Trying to load "+ path);
                            System.load(mAppRoot + path);
                        }
                        catch (UnsatisfiedLinkError ule) {
                            Log.e("JNI", "WARNING: Could not load "+ path);
                            Log.e("JNI", Log.getStackTraceString(ule));
                        }
                }
        }
        catch (IOException e)
        {
                Log.e(TAG,"Native library error: "+Log.getStackTraceString(e));
        }
    }
    
    
    public static void copyStreams(InputStream is, FileOutputStream fos)
    {
            BufferedOutputStream os = null;
            try
            {
                    byte data[] = new byte[BUFSIZE];
                    int count;
                    os = new BufferedOutputStream(fos, BUFSIZE);
                    while ((count = is.read(data, 0, BUFSIZE)) != -1)
                    {
                            os.write(data, 0, count);
                    }
                    os.flush();
            }
            catch (IOException e)
            {
                    Log.e(TAG,"Exception while copying: " + e);
            }
            finally
            {
                    try
                    {
                            if (os != null)
                            {
                                    os.close();
                            }
                    }
                    catch (IOException e2)
                    {
                            Log.e(TAG,"Exception while closing the stream: " + e2);
                    }
            }
    }

    
    public static Vector<ZipEntry> librariesFilesFromZip(ZipFile zip)
    {
            Vector<ZipEntry> list = new Vector<ZipEntry>();
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements())
            {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.getName().startsWith(ZIP_FILTER))
                    {
                            list.add(entry);
                    }
            }
            return list;
    }    
}

