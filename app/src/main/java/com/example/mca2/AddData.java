package com.example.mca2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddData extends AppCompatActivity {
    int date = (int)(Instant.now().getEpochSecond())/(86400000);
    String[] data = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        final Button button = findViewById(R.id.submitData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareData();
                try {
                    printFileToConsole(new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + getString(R.string.folderName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**Some code stolen from the tutorial
     * "Checks if external storage is available for read and write"
     * I would be fine with it crashing but muh good practice
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    /** Fills the data List with all the right Strings.
     * Entirely devoid of future-proofing, and not open to expansion :( */
    public void prepareData(){
        RadioGroup happinessGroup = findViewById(R.id.radioHappiness);
        RadioGroup energyGroup = findViewById(R.id.radioEnergy);
        RadioGroup successGroup = findViewById(R.id.radioSuccess);
        date = (int)(Instant.now().getEpochSecond())/(86400000);
        data[0] = (Integer.toString(date));
        data[1] = (Integer.toString(happinessGroup.getCheckedRadioButtonId()));
        data[2] = (Integer.toString(energyGroup.getCheckedRadioButtonId()));
        data[3] = (Integer.toString(successGroup.getCheckedRadioButtonId()));
        try {
            checkIfCanWriteToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            checkIfCanWriteToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    public File getPrivateFileStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }
    */

    /** Writes the data List to a csv file called Data.txt */
    public void checkIfCanWriteToFile() throws IOException {
        Context context = getApplicationContext();

        File fileDir =  context.getExternalFilesDir(null);
        //Create folder if it doesn't exist yet
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        //Set up path to csv file
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + getString(R.string.folderName) + File.separator + getString(R.string.fileName));
        //Create csv file if it doesn't exist yet
        if (!file.exists()) {
            new File(context.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS), getString(R.string.fileName));
        }

        List<String[]> fileContents = new ArrayList<String[]>();
        try (CSVReader reader = new CSVReader(new FileReader(getString(R.string.fileName)))) {
            fileContents = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        int temp = 0;
        if(fileContents.size()!=0){
            temp = fileContents.size()-1;
        }
        if(!(fileContents.get(temp)[0].equals(Integer.toString(date)))){
            writeToFile(file);
        } else {
            fileContents.remove(fileContents.size()-1);
            CSVWriter reWriter = new CSVWriter(new FileWriter(file));
            reWriter.writeAll(fileContents);
            reWriter.close();
            writeToFile(file);
        }
        */
        writeToFile(file);

    }
    public void writeToFile(File file) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(file));

        writer.writeNext(data);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test:
        printFileToConsole(file);


    }
    public void printFileToConsole(File file) throws IOException {
        List<String[]> fileContents = null;
        CSVReader reader = new CSVReader(new FileReader(getString(R.string.fileName)));
            fileContents = reader.readAll();

        for (String[] line : fileContents) {
            System.out.println("Epoch Days : " + line[0]);
            System.out.println("Happiness : " + line[1]);
            System.out.println("Energy : " + line[2]);
            System.out.println("Success : " + line[3]);
            System.out.println("---------------------------");
        }
    }



}
