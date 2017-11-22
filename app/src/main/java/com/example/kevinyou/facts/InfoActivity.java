package com.example.kevinyou.facts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    private EditText name;
    private Button submit;

    private Intent intent;

    private Spinner factsSpinner;
    private String selectedFacts;
    
    private String nameStr;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();

        name = findViewById(R.id.name);
        submit = findViewById(R.id.submit);
        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        String nameString = sharedPreferences.getString("name", null);

        if (nameString != null) {
            intent = new Intent(InfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        String[] item = new String[] {
                "Daily",
                "Movie",
                "Music"
        };

        factsSpinner = findViewById(R.id.factsSpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);

        factsSpinner.setAdapter(arrayAdapter);

        factsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFacts = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = name.getText().toString();

                if (nameStr.length() < 3) {
                    Toast.makeText(InfoActivity.this, "Name too short!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedFacts.length() == 0) {
                    Toast.makeText(InfoActivity.this, "You need to select facts you want!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", nameStr);
                editor.putString("selected", selectedFacts);
                editor.apply();

                intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
