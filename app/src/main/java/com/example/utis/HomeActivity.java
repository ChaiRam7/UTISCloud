package com.example.utis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import android.graphics.Color;

public class HomeActivity extends AppCompatActivity {

    private ImageView notificationImageView, aboutUsImageView, backImg,cimage,dimage;
    private Spinner departureSpinner, arrivalSpinner;
    private Button timingButton,scheduleButton;
    private TextView timelyTextView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ImageView menuButton;
    private   ImageView tempImageView;



    private static final String DATABASE_NAME = "my_transport_schedules.db";
    private static final String TABLE_NAME = "routes";
    private static final String COLUMN_ROUTE = "route";
    private static final String COLUMN_SOURCE = "source";
    private static final String COLUMN_SOURCE_TIME = "source_time";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DESTINATION_TIME = "destination_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notificationImageView = findViewById(R.id.notification);
        aboutUsImageView = findViewById(R.id.AboutUs);
        departureSpinner = findViewById(R.id.departure);
        arrivalSpinner = findViewById(R.id.arrival);
        timingButton = findViewById(R.id.timing);
        scheduleButton=findViewById(R.id.schedule);
        cimage = findViewById(R.id.chatImageView);
        dimage=findViewById(R.id.imageView3);
        drawerLayout = findViewById(R.id.optimise);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.menu);
        ImageView chatbotImageView = findViewById(R.id.chatbot);
        ImageView mapImageView = findViewById(R.id.map);
        ImageView tempImageView = findViewById(R.id.temp);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        ImageView viewTravelLogButton = findViewById(R.id.viewTravelLogButton);
        MenuItem appInfoItem = menu.findItem(R.id.nav_app_info);
        View actionView = appInfoItem.getActionView();


        if (actionView != null) {
            actionView.setBackgroundColor(Color.parseColor("#518AFF"));
        }

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        tempImageView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TempStopActivity.class);
            startActivity(intent);
        });

        viewTravelLogButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TravelLogActivity.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_dark_mode) {
                    // Handle Dark Mode action
                } else if (item.getItemId() == R.id.nav_languages) {
                    // Handle Languages action
                } else if (item.getItemId() == R.id.nav_clear_searches) {
                    // Handle Clear Searches action
                } else if (item.getItemId() == R.id.nav_share_app) {
                    // Handle Share App action
                } else if (item.getItemId() == R.id.nav_rate_us) {
                    // Handle Rate Us action
                }


                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        chatbotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });



        mapImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View
                    v) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });


        cimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomContactDialog();
            }

            private void showCustomContactDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Button btnWhatsApp = dialogView.findViewById(R.id.btn_whatsapp);
                Button btnGmail = dialogView.findViewById(R.id.btn_gmail);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

                btnWhatsApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWhatsAppChat("8610122637");
                        alertDialog.dismiss();
                    }
                });

                btnGmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGmail("bounishselfuse@gmail.com");
                        alertDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }

            private void openWhatsAppChat(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }

            private void openGmail(String email) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                startActivity(emailIntent);
            }
        });



        dimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomContactDialog();
            }

            private void showCustomContactDialog() {


                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog_report, null);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Button btnWhatsApp = dialogView.findViewById(R.id.btn_whatsapp);
                Button btnGmail = dialogView.findViewById(R.id.btn_gmail);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

                btnWhatsApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWhatsAppChat("9445014448");
                        alertDialog.dismiss();
                    }
                });

                btnGmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGmail("commercial@tnstc.org");
                        alertDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }

            private void openWhatsAppChat(String phoneNumber) {
                // Open WhatsApp chat using Intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }

            private void openGmail(String email) {
                // Open Gmail using Intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                startActivity(emailIntent);
            }
        });




        // Inflate the layout containing the timelyTextView
        View timeLayout = getLayoutInflater().inflate(R.layout.activity_time, null);
        // Find the timelyTextView within the inflated layout
        timelyTextView = timeLayout.findViewById(R.id.timely);

        // Set click listeners
        notificationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Admin.class);
                startActivity(intent);
            }
        });

        aboutUsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        timingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNearestTime();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule();
            }
        });

        // Initialize database operations
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase(); // Open database for writing
            insertData(db, "T11 G", "VELLORE", "07:00:00", "A.K.PADAVEDU", "08:20:00");
            insertData(db, "T11 G", "VELLORE", "07:00:00", "A.K.PADAVEDU", "08:20:00");
            insertData(db, "T11 G", "VELLORE", "14:20:00", "A.K.PADAVEDU", "15:40:00");
            insertData(db, "T11 G", "VELLORE", "18:45:00", "A.K.PADAVEDU", "20:05:00");
            insertData(db, "T 3AA", "VELLORE", "05:20:00", "ADUKAMPARAI G.H.", "05:50:00");
            insertData(db, "T11 G", "VELLORE", "12:45:00", "ADUKAMPARAI G.H.", "13:15:00");



        } catch (SQLiteException e) {
            Log.e("DatabaseError", e.getMessage());
            Toast.makeText(this, "Error accessing database", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        // Set up the spinners
        ArrayAdapter<String> departureAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"KATPADI", "VELLORE"});
        departureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureSpinner.setAdapter(departureAdapter);

        ArrayAdapter<String> arrivalAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);
        arrivalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Set<String> uniqueDestinations = new HashSet<>();

        try {
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DESTINATION}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String destination = cursor.getString(0);
                    uniqueDestinations.add(destination);
                } while (cursor.moveToNext());
            }
            cursor.close();

            List<String> sortedDestinations = new ArrayList<>(uniqueDestinations);
            Collections.sort(sortedDestinations);

            arrivalAdapter.addAll(sortedDestinations);
            arrivalSpinner.setAdapter(arrivalAdapter);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    private void insertData(SQLiteDatabase db, String route, String source, String sourceTime, String destination, String destinationTime) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE, route);
        values.put(COLUMN_SOURCE, source);
        values.put(COLUMN_SOURCE_TIME, sourceTime);
        values.put(COLUMN_DESTINATION, destination);
        values.put(COLUMN_DESTINATION_TIME, destinationTime);
        db.insert(TABLE_NAME, null, values);
    }

    private void showNearestTime() {
        try {
            String departure = departureSpinner.getSelectedItem().toString();
            String source = arrivalSpinner.getSelectedItem().toString();
            String currentTime = getCurrentTime();
            String nearestSourceTime = queryNearestSourceTime(source,departure, currentTime);
            timelyTextView.setText("nearestSourceTime");

            if (!nearestSourceTime.isEmpty()) {
                Intent intent = new Intent(HomeActivity.this,TimeActivity.class);
                intent.putExtra("message",nearestSourceTime);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No direct route buses found for the selected source and destination.", Toast.LENGTH_SHORT).show();
            }
//            System.out.println(nearestSourceTime);

        }catch(Exception e){
            System.out.println(e);
        }
    }
    private void schedule() {
        try {
            String departure = departureSpinner.getSelectedItem().toString();
            String source = arrivalSpinner.getSelectedItem().toString();

            q1schedule(source,departure);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void q1schedule(String source, String departure) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> timingsList = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + TABLE_NAME +
                            " WHERE " + COLUMN_SOURCE + " = ? AND " + COLUMN_DESTINATION + " = ?"+" ORDER BY " + COLUMN_SOURCE_TIME + " ASC",
                    new String[]{departure, source});
            if (cursor.moveToFirst()) {
                int routeIndex = cursor.getColumnIndex(COLUMN_ROUTE);
                int sourceTimeIndex = cursor.getColumnIndex(COLUMN_SOURCE_TIME);
                int destinationTimeIndex = cursor.getColumnIndex(COLUMN_DESTINATION_TIME);
                do {
                    String timing = "BUS NAME: " + cursor.getString(routeIndex) + "\n" +
                            "SOURCE TIME: " + cursor.getString(sourceTimeIndex) + "\n" +
                            "DESTINATION TIME: " + cursor.getString(destinationTimeIndex);
                    timingsList.add(timing);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        if (!timingsList.isEmpty()) {
            Intent intent = new Intent(HomeActivity.this, Schedule.class);
            intent.putStringArrayListExtra("timingsList", (ArrayList<String>) timingsList);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No schedule found for the selected source and destination.", Toast.LENGTH_SHORT).show();
        }
    }




    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @SuppressLint("Range")
    private String queryNearestSourceTime(String arrival,String departure, String currentTime) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String nearestSourceTime = "";

        try {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME +                  // Selecting all columns from a table
                            " WHERE " + COLUMN_SOURCE + " = ?" +            // Filtering rows where COLUMN_SOURCE equals 'departure'
                            " AND " + COLUMN_DESTINATION + " = ?" +         // AND COLUMN_DESTINATION equals 'arrival'
                            " AND " + COLUMN_SOURCE_TIME + " > ?" +         // AND COLUMN_SOURCE_TIME is greater than 'currentTime'
                            " ORDER BY " + COLUMN_SOURCE_TIME + " ASC" +    // Ordering the results by COLUMN_SOURCE_TIME in ascending order
                            " LIMIT 1",                                    // Limiting the result to 1 row
                    new String[]{departure, arrival, currentTime}); // Binding variables 'departure', 'arrival', and 'currentTime'

            if (cursor.moveToFirst()) {
                nearestSourceTime = "\n" +
                        "ROUTE: " + cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE)) + "\n" +
                        "SOURCE: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE)) + "\n" +
                        "SOURCE TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_TIME)) + "\n" +
                        "DESTINATION: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)) + "\n" +
                        "DESTINATION TIME: " + cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION_TIME));
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return nearestSourceTime;
    }

    private static class MyDatabaseHelper extends SQLiteOpenHelper {

        public MyDatabaseHelper(HomeActivity context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ROUTE + " TEXT, " +
                    COLUMN_SOURCE + " TEXT, " +
                    COLUMN_SOURCE_TIME + " TEXT, " +
                    COLUMN_DESTINATION + " TEXT, " +
                    COLUMN_DESTINATION_TIME + " TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database upgrades here
        }
    }

}
