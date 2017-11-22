package com.example.kevinyou.facts;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kevinyou.facts.data.DatabaseAccess;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    private TextView facts;
    private TextView hello;
    private TextView duk;

    private DatabaseAccess databaseAccess;

    private ArrayList<String> tipsList;

    private int tipsCounter;

    private long tableRowCount;

    private ImageView share;

    private SharedPreferences sharedPreferences;

    private String selectedFacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tipsCounter = 0;

        hello = findViewById(R.id.hello);
        facts = findViewById(R.id.facts);
        duk = findViewById(R.id.duk);
        relativeLayout = findViewById(R.id.relativeLayout);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/KlinicSlabMedium.otf");
        hello.setTypeface(typeface);
        facts.setTypeface(typeface);
        duk.setTypeface(typeface);

        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        selectedFacts = sharedPreferences.getString("selected", "");

        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        tipsList = getTips();
        tableRowCount = getTableRowCount();

        if (!name.equals("")) {
            hello.append(name);
        }

        share = findViewById(R.id.share);
        share.bringToFront();

        facts.setText(tipsList.get(tipsCounter));

        relativeLayout.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    private ArrayList<String> getTips() {
        databaseAccess.openReadableDatabase();
        ArrayList<String> list = databaseAccess.getTips(selectedFacts);
        databaseAccess.close();

        return list;
    }

    private long getTableRowCount() {
        databaseAccess.openReadableDatabase();

        long count = databaseAccess.getTableRowCount(selectedFacts);
        databaseAccess.close();

        return count;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout:
                tipsCounter++;
                if (tipsCounter == tableRowCount) {
                    tipsCounter = 0;
                }

                int red = (int) (Math.random() * 255 + 1);
                int green = (int) (Math.random() * 255 + 1);
                int blue = (int) (Math.random() * 255 + 1);

                facts.setText(tipsList.get(tipsCounter));

                ObjectAnimator colorFade = ObjectAnimator.ofObject(relativeLayout, "backgroundColor", new ArgbEvaluator(), Color.rgb(0, 0, 0), Color.argb(255, red, green, blue));
                colorFade.setDuration(500);
                colorFade.start();
                break;

            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareDataSub = "Did you know?";
                String shareDataBody = tipsList.get(tipsCounter);

                intent.putExtra(Intent.EXTRA_SUBJECT, shareDataSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareDataBody);

                startActivity(Intent.createChooser(intent, "Share with... "));
                break;

            default:
                break;
        }
    }


}
