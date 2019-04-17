package com.kk.mopub_chocolate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kk.mopub_chocolate.databinding.ActivityMainBinding;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.vdopia.ads.lw.Chocolate;
import com.vdopia.ads.lw.InitCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private MopubAds mopubAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mopubAds = new MopubAds(this, binding.log);
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
        switch (view.getId()) {
            case R.id.load_chocolate_rewarded:

                break;
            case R.id.load_chocolate_inview:
                break;
            case R.id.load_mopub_rewarded:
                mopubAds.loadRewardedAd();
                break;
            case R.id.load_mopub_inview:
                break;
            default:
                break;
        }
    }
}
