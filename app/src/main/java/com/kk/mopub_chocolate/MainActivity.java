package com.kk.mopub_chocolate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kk.mopub_chocolate.databinding.ActivityMainBinding;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.vdopia.ads.lw.Chocolate;
import com.vdopia.ads.lw.InitCallback;
import com.vdopia.ads.lw.LVDORewardedAd;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Chocolate.enableChocolateTestAds(true);
        Chocolate.init(this, Config.CHOCOLATE_API_KEY, new InitCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String msg) {

            }
        });

        MoPub.initializeSdk(this, new SdkConfiguration.Builder(Config.MOPUB_REWARDED_AD_UNIT_ID).build(),
                new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {

                    }
                });
        MoPub.initializeSdk(this, new SdkConfiguration.Builder(Config.MOPUB_INVIEW_AD_UNIT_ID).build(),
                new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {

                    }
                });
    }

    public void onButtonClicked(View view) {
        Log.d(TAG,"onClick " + (view.getId() == R.id.request1 ));
    }
}
