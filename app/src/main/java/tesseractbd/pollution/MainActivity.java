package tesseractbd.pollution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private TextView tvValTemp;
    private TextView tvComTemp;

    private TextView tvValSound;
    private TextView tvComSound;

    private TextView tvValWeather;
    private TextView tvComWeather;

    private TextView tvValGas;
    private TextView tvComGas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvValTemp = findViewById(R.id.tv_val_temp);
        tvComTemp = findViewById(R.id.tv_com_temp);

        tvValSound = findViewById(R.id.tv_val_sound);
        tvComSound = findViewById(R.id.tv_com_sound);

        tvValWeather = findViewById(R.id.tv_val_weather);
        tvComWeather = findViewById(R.id.tv_com_weather);

        tvValGas = findViewById(R.id.tv_val_gas);
        tvComGas = findViewById(R.id.tv_com_gas);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pojo post = dataSnapshot.getValue(Pojo.class);

                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                updateTemp(post.Temp);
                updateSound(post.Sound);
                updateWeather(post.weather);
                updateGas(post.Gas);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateTemp(long temp) {
        tvValTemp.setText(temp + " degree Celcius");
        if (temp < 20) {
            tvComTemp.setText("Temperature is Cold");
        } else if (temp >= 20 && temp <= 25) {
            tvComTemp.setText("Comfortable Temperature");
        } else if (temp >= 26 && temp <= 30) {
            tvComTemp.setText("Temperature is getting Warmer");
        } else if (temp >= 31 && temp <= 35) {
            tvComTemp.setText("Temperature is Hot");
        } else {
            tvComTemp.setText("Temperature is Unbearable");
        }
    }

    private void updateSound(long temp) {
        tvValSound.setText(temp + " bd");

        if (temp <= 30) {
            tvComSound.setText("Soft Music / Whisper");
        } else if (temp > 31 && temp <= 40) {
            tvComSound.setText("Average Home Noise");
        } else if (temp > 41 && temp <= 60) {
            tvComSound.setText("Normal Conversation , background music");
        } else if (temp > 61 && temp <= 70) {
            tvComSound.setText("Office Noise");
        } else if (temp > 71 && temp <= 75) {
            tvComSound.setText("Average Radio");
        } else {
            tvComSound.setText("Heavy Traffic");
        }
    }

    private void updateWeather(long temp) {
        tvValWeather.setText(temp + " ");

        if (temp > 500) {
            tvComWeather.setText("Very heavy Rain");
        } else if ((temp > 300) && (temp <= 500)) {
            tvComWeather.setText("AVERAGE Rain");
        } else {
            tvComWeather.setText("Dry Weather");
        }
    }

    private void updateGas(long temp) {
        tvValGas.setText(temp + " PPM");

        if (temp < 9) {
            tvComGas.setText("Normal fresh Air");
        } else if (temp == 9) {
            tvComGas.setText("Maximum recommended indoor CO level (ASHRAE).");
        } else if (temp >= 10 && temp <= 24) {
            tvComGas.setText("Possible health effects with long-term exposure.");
        } else if (temp == 25) {
            tvComGas.setText("Max TWA Exposure for 8 hour work-day (ACGIH).");
        } else if (temp >= 26 && temp <= 50) {
            tvComGas.setText("Maximum permissible exposure in workplace (OSHA).");
        } else if (temp >= 51 && temp <= 100) {
            tvComGas.setText("Slight headache after 1-2 hours.");
        }
    }
}
