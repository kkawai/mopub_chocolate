package com.kk.mopub_chocolate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kk.mopub_chocolate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private MopubAds mopubAds;
    private ChocolateAds chocolateAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mopubAds = new MopubAds(this, binding.log, binding.inview);
        chocolateAds = new ChocolateAds(this, binding.log, binding.inview);
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.load_chocolate_rewarded:
                chocolateAds.loadChocolateRewarded();
                break;
            case R.id.load_chocolate_inview:
                break;
            case R.id.load_mopub_rewarded:
                mopubAds.loadMoPubRewardedAd();
                break;
            case R.id.load_mopub_inview:
                break;
            default:
                break;
        }
    }
}
