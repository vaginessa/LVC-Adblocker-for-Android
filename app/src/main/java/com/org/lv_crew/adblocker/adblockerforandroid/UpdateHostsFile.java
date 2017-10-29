package com.org.lv_crew.adblocker.adblockerforandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import static android.R.attr.category;
import static android.R.attr.data;

/**
 * Created by denni on 24.10.2017.
 */

public class UpdateHostsFile {
    private String _text;
    UpdateHostsFile(String text) throws IOException {
        _text=text;
    }

    public void startUpdate(Context context)
    {
        try {


        }catch(Exception ex) {

        }
        try {
            setPermissions();
            setText(context);


            resetPermissions();
        }catch(IOException e){
        }
    }
    private void setText(Context context) throws IOException {
        //FileOutputStream fs=new FileOutputStream(new File("/system/etc/hosts"));
        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("/system/etc/hosts", Context.MODE_APPEND));
        File outputDir = context.getCacheDir();
        FileOutputStream fs=new FileOutputStream (new File( outputDir+"/hosts.tmp")); // true will be same as Context.MODE_APPEND
        ;
        fs.write(_text.getBytes());
        fs.close();
        File f1=new File("/system/etc/hosts");
        File f=new File(outputDir+"/hosts.tmp");
        int count=0;
        while(f.getTotalSpace()!=f1.getTotalSpace()&&count<20) {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "cp " + outputDir + "/hosts.tmp /system/etc/hosts"});
            /*
            Reader r = new InputStreamReader(process.getInputStream());
            BufferedReader in = new BufferedReader(r);
            String line="";
            String ges="";
            while((line = in.readLine()) != null)
                ges+=line;
               // Toast.makeText(context,line,Toast.LENGTH_LONG).show();
            in.close();
        //Toast.makeText(context,ges,Toast.LENGTH_LONG).show();
            Log.d("D",ges);*/
            try{
            process.waitFor();}catch(Exception e){}
            count++;
        }
    }


    private void setPermissions() throws IOException {
        Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,rw /system"});

    }

    private void resetPermissions() throws IOException {
        Runtime.getRuntime().exec(new String[] { "su", "-c", "mount -o remount,ro /system"});
    }

}