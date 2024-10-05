package com.example.utis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Schedule extends AppCompatActivity {
    Button back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        back2 = findViewById(R.id.button3);

        ArrayList<String> timingsList = getIntent().getStringArrayListExtra("timingsList");

        ListView timingsListView = findViewById(R.id.timingsListView);
        assert timingsList != null;
        ArrayAdapter<String> timingsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, timingsList);
        timingsListView.setAdapter(timingsAdapter);

        back2.setOnClickListener(v -> {
            startActivity(new Intent(Schedule.this, HomeActivity.class));
            finish();
        });
    }
}
