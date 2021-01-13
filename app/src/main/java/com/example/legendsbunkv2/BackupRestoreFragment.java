package com.example.legendsbunkv2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.orm.SugarDb;
import com.snatik.storage.Storage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static java.io.File.createTempFile;

public class BackupRestoreFragment extends Fragment {
    View root;
    String sdPath;
    String TAG;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_backup_restore,container,false);
        setHasOptionsMenu(true);
        TAG="hijk";
        exportDatabase(getActivity().getApplicationContext(),"sugar_example.db","sugar_example.db");
//        exportDB();
        return root;
    }

    private void exportDB() {

        Storage storage = new Storage(getActivity().getApplicationContext());
        storage.copy("/sdcard/Movies/3DLUT mobile/3DLUTmobile_20200318_204201.mp4","/sdcard/Movies/export.mp4");
        File original=new File("/sdcard/Movies/3DLUT mobile/3DLUTmobile_20200318_204201.mp4");
        File copied=new File("/sdcard/Movies/export.mp4");


        try {
            FileUtils.copyFile(original,copied);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSDPresent(Context context) {

        File[] storage = ContextCompat.getExternalFilesDirs(context, null);
        if (storage.length > 1 && storage[0] != null && storage[1] != null) {
            sdPath = storage[1].toString();
            return true;
        }
        else
            return false;
    }


    private boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }

    public boolean exportDatabase(Context context, String localDbName, String backupDbName) {
        if (isContextValid(context))
            try {
                if (false) {
                    Log.e(TAG, "SD is absent!");
                    return false;
                }

                File sd = new File(sdPath);

                if (sd.canWrite()) {

                    File currentDB = new File("/data/data/" + context.getPackageName() +"/databases/", localDbName);
                    File backupDB = new File(sd,  backupDbName);

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }
                else {
                    Log.e(TAG, "SD can't write data!");
                    return false;
                }
            } catch (Exception e) {

            }
        else {
            Log.e(TAG, "Export DB: Context is not valid!");
            return false;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);}
    }
}
