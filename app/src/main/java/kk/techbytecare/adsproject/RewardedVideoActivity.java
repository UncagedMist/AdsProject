package kk.techbytecare.adsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RewardedVideoActivity extends AppCompatActivity implements RewardedVideoAdListener {

    Button btnShow;
    TextView txtCoin;
    int coin = 0;
    RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);


        btnShow = findViewById(R.id.btnShow);
        txtCoin = findViewById(R.id.txtCoin);

        btnShow.setVisibility(View.INVISIBLE);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!rewardedVideoAd.isLoaded())    {
                    loadAd();
                }
            }
        },3,5, TimeUnit.SECONDS);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rewardedVideoAd.isLoaded()) {
                    rewardedVideoAd.show();
                }
            }
        });
    }

    private void loadAd() {
        if (!rewardedVideoAd.isLoaded())    {
            rewardedVideoAd.loadAd("ca-app-pub-7920815986886474/6784841602",new AdRequest.Builder().build());
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        btnShow.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Click The Show Button to Earn Coins...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "Ad Opened...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "Watch Full Video to get the Reward...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "Ad Closed...", Toast.LENGTH_SHORT).show();
        loadAd();
        btnShow.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        coin = coin + rewardItem.getAmount();
        txtCoin.setText("Coin : "+coin);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Ad has been closed..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        rewardedVideoAd.pause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rewardedVideoAd.resume(this);
        loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rewardedVideoAd.destroy(this);
    }

    @Override
    protected void onStart() {
        loadAd();
        super.onStart();
    }
}

