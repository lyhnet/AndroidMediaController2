package com.example.volcont2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;
import android.view.MotionEvent;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Handler handler = new Handler();
    private boolean isLongPressActive = false;
    private int countLongPress;
    private int countLongPressTrigger;
    private boolean isPressedVolumeRocker = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLongPress=0;
        countLongPressTrigger=2;




        startService(new Intent(this, VolumeService.class));
        // find buttons
        Button btn = findViewById(R.id.btnVolUp);
        Button btn2 = findViewById(R.id.btnVolDown);

        Button btnDelay5 = findViewById(R.id.btnDelay5);
        Button btnDelay60 = findViewById(R.id.btnDelay60);
        Button btnDelay120 = findViewById(R.id.btnDelay120);
        Button btnDelay180 = findViewById(R.id.btnDelay180);
        Button btnDelay240 = findViewById(R.id.btnDelay240);
        Button btnDelay720 = findViewById(R.id.btnDelay720);

        Button btnUSB = findViewById(R.id.btnUSB);
        Button btnOptical = findViewById(R.id.btnOptical);
        Button btnMute = findViewById(R.id.btnMute);

        Button btnOff = findViewById(R.id.btnOff);
        Button btnOn = findViewById(R.id.btnOn);
        // set button behaviour

        btnDelay5.setOnClickListener(this);
        btnDelay60.setOnClickListener(this);
        btnDelay120.setOnClickListener(this);
        btnDelay180.setOnClickListener(this);
        btnDelay240.setOnClickListener(this);
        btnDelay720.setOnClickListener(this);
        btnUSB.setOnClickListener(this);
        btnOptical.setOnClickListener(this);
        btnMute.setOnClickListener(this);
        btnOff.setOnClickListener(this);
        btnOn.setOnClickListener(this);


        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLongPressActive = true;
                        handler.postDelayed(longPressRunnableUp, 150);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isLongPressActive = false;
                        handler.removeCallbacks(longPressRunnableUp);
                        return true;
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle quick tap (single execution)
                if (!isLongPressActive) {
                    new FetchDataTask("VolUp").execute();
                }
            }
        });

        // set button behaviour
        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLongPressActive = true;
                        handler.postDelayed(longPressRunnableDown, 150);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isLongPressActive = false;
                        handler.removeCallbacks(longPressRunnableDown);
                        return true;
                }
                return false;
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle quick tap (single execution)
                if (!isLongPressActive) {
                    new FetchDataTask("VolDown").execute();
                }
            }
        });




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if ( isPressedVolumeRocker == false) {

        //isPressedVolumeRocker=true;
        //isLongPressActive=true;
        if (( countLongPress == countLongPressTrigger)) {
            countLongPress = 0;
            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
                Log.d("VolRocker", "Down, pressed");
                new FetchDataTask("VolDown").execute();
                return true;

            } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
                Log.d("VolRocker", "Up, pressed");
                new FetchDataTask("VolUp").execute();
                return true;
            }
        } else {
            countLongPress = countLongPress + 1;
        }
            // }
        //return super.onKeyDown(keyCode, event);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        isPressedVolumeRocker=false;
        isLongPressActive=false;
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            //Do something
            Log.d("VolRocker", "Down, released" );}
        else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            Log.d("VolRocker", "Up, released" );}
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDelay5) {
            new FetchDataTask("Delay/5").execute();
            showToast("PowerOff delay: 5 min");
        } else if (v.getId() == R.id.btnDelay60) {
            new FetchDataTask("Delay/60").execute();
            showToast("PowerOff delay: 1 h");
        } else if (v.getId() == R.id.btnDelay120) {
            new FetchDataTask("Delay/120").execute();
            showToast("PowerOff delay: 2 h");
        } else if (v.getId() == R.id.btnDelay180) {
            new FetchDataTask("Delay/180").execute();
            showToast("PowerOff delay: 3 h");
        } else if (v.getId() == R.id.btnDelay240) {
            new FetchDataTask("Delay/240").execute();
            showToast("PowerOff delay: 4 h");
        } else if (v.getId() == R.id.btnDelay720) {
            new FetchDataTask("Delay/720").execute();
            showToast("PowerOff delay: 12 h");
        } else if (v.getId() == R.id.btnUSB) {
            new FetchDataTask("USB").execute();
            showToast("Input: USB selected");
        } else if (v.getId() == R.id.btnOptical) {
            new FetchDataTask("Optical2").execute();
            showToast("Input: Optical 1 selected");
        } else if (v.getId() == R.id.btnMute) {
            new FetchDataTask("Mute").execute();
            showToast("Mute toggle");
        } else if (v.getId() == R.id.btnOff) {
            new FetchDataTask("PowerOff").execute();
            showToast("Powering off");
        } else if (v.getId() == R.id.btnOn) {
            new FetchDataTask("PowerOn").execute();
            new FetchDataTask("Delay/120").execute();
            showToast("Powering up, setting PowerOff delay to 2 hours");
        }
    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {
        private String irCommand;

        public FetchDataTask(String irCommand) {
            this.irCommand = irCommand;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                // Replace this URL with your actual API endpoint
                //String apiUrl = "http://192.168.1.178:3000/api/v1/commands/?cmd=volume&volume=plus";
                String apiUrl = "http://"+getString(R.string.WebIP)+":"+ getString(R.string.WebPort) +"/" + irCommand;
                // Log the URL
                Log.d("HttpClientRequest", "Making request to: " + apiUrl);

                // Create URL object
                URL url = new URL(apiUrl);

                // Open connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    // Check the HTTP response code
                    int responseCode = urlConnection.getResponseCode();
                    Log.d("HttpClientRequest", "Response Code: " + responseCode);

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read the response
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line).append("\n");
                        }

                        // Log the response content
                        Log.d("HttpClientRequest", "Response Content:\n" + result.toString());

                        return result.toString();
                    } else {
                        // Handle non-OK response codes
                        Log.e("HttpClientRequest", "Non-OK response code: " + responseCode);
                    }

                } finally {
                    urlConnection.disconnect(); // Disconnect the connection
                }

            } catch (IOException e) {
                // Log any exceptions
                Log.e("HttpClientRequest", "Error making HTTP request: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // No need to update UI here, as we are not using responseTextBox
        }
    }

    private final Runnable longPressRunnableUp= new Runnable() {
        @Override
        public void run() {
            if (isLongPressActive) {
                // Execute your command here
                new FetchDataTask("VolUp").execute();
                Log.d("LongPRun", "longPress: Up");
                // Schedule the next execution after 100 milliseconds
                handler.postDelayed(this, 200);
            }
        }
    };

    private final Runnable longPressRunnableDown= new Runnable() {
        @Override
        public void run() {
            if (isLongPressActive) {
                // Execute your command here
                new FetchDataTask("VolDown").execute();
                Log.d("LongPRun", "longPress: Down");
                // Schedule the next execution after 100 milliseconds
                handler.postDelayed(this, 200);
            }
        }
    };

    // Function to display a Toast message
    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }




}

