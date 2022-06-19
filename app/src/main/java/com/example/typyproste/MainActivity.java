package com.example.typyproste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.health.ServiceHealthStats;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                EditText editText = findViewById(R.id.editText);
                CheckBox checkBox = findViewById(R.id.checkBox);

                //zapis satanu
                prefsEditor.putString("editText", editText.getText().toString());
                prefsEditor.putBoolean("checkBox", checkBox.isChecked());
                prefsEditor.commit();
            }
        });

        SharedPreferences appPrefs = getPreferences(MODE_PRIVATE);
        String tekst = appPrefs.getString("editText", "");
        boolean w = appPrefs.getBoolean("checkBox", false);
        EditText editText = findViewById(R.id.editText);
        CheckBox checkBox = findViewById(R.id.checkBox);
        editText.setText(tekst);
        checkBox.setChecked(w);

        Button buttonZapisz = findViewById(R.id.buttonZapisz);
        EditText editText2 = findViewById(R.id.editText2);
        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tekst = editText2.getText().toString();
                try {
                    FileOutputStream fOut = openFileOutput("textfile.txt", MODE_PRIVATE);
                    OutputStreamWriter osw  = new OutputStreamWriter(fOut);
                    osw.write(tekst);
                    osw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button buttonWczytaj = findViewById(R.id.buttonWczytaj);
        buttonWczytaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fIn = openFileInput("textfile.txt");
                    InputStreamReader isr = new InputStreamReader(fIn);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuilder sb = new StringBuilder();
                    String linia;
                    while((linia = reader.readLine()) !=null){
                        sb.append(linia).append("\n");
                    }
                    reader.close();
                    editText2.setText(sb.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}