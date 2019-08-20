package com.example.mca2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Called when Add Data button is clicked
     *  TODO: Open Add Data activity
     * @param view
     */
    public void openScreenAdd(View view){

    }

    /** Called when View Data button is clicked
     *  Opens View Calendar activity
     * @param view
     */
    public void openScreenView(View view){
        Intent intent = new Intent(this, ViewCalendar.class);
        startActivity(intent);
    }

    /** Called when View Statistics button is clicked
     *  TODO: Open View Stats activity
     * @param view
     */
    public void openScreenStats(View view){

    }
}
