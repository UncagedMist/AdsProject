package kk.techbytecare.adsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnVideo,btnScreen,btnBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBanner = findViewById(R.id.btnBanner);
        btnScreen = findViewById(R.id.btnScreen);
        btnVideo = findViewById(R.id.btnVideo);

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RewardedVideoActivity.class));
            }
        });
    }
}
