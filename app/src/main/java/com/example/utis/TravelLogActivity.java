package com.example.utis;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TravelLogActivity extends AppCompatActivity {

    private TextView travelLogTextView;
    private Button selectDateButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log);

        travelLogTextView = findViewById(R.id.travelLogTextView);
        selectDateButton = findViewById(R.id.selectDateButton);
        prefs = getSharedPreferences("TravelPatterns", MODE_PRIVATE);

        // Set up the DatePickerDialog for date selection
        selectDateButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    // Format selected date to yyyy-MM-dd
                    calendar.set(year, month, dayOfMonth);
                    String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

                    displayTravelLogForDate(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void displayTravelLogForDate(String date) {
        // Get today's date
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Show travel log if it exists, otherwise display a relevant message
        if (date.compareTo(todayDate) > 0) {
            travelLogTextView.setText("YET TO TRAVEL");
        } else {
            String travelLog = prefs.getString(date, "No travel data available for this date.");
            travelLogTextView.setText(travelLog);
        }
    }
}

