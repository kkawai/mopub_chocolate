package com.kk.mopub_chocolate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vdopia.ads.lw.Chocolate;
import com.vdopia.ads.lw.InitCallback;
import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOAdSize;
import com.vdopia.ads.lw.LVDOBannerAd;
import com.vdopia.ads.lw.LVDOBannerAdListener;
import com.vdopia.ads.lw.LVDOBannerPartnerHelper;
import com.vdopia.ads.lw.LVDOConstants;
import com.vdopia.ads.lw.LVDORewardedAd;
import com.vdopia.ads.lw.RewardedAdListener;

class ChocolateAds extends BaseAds implements RewardedAdListener, LVDOBannerAdListener {

    private static final String TAG = "ChocolateDemo: ";

    private LVDORewardedAd rewardedAd;
    private LVDOAdRequest adRequest;
    private LVDOBannerAd bannerAd;

    ChocolateAds(Context context, TextView logView, ViewGroup inviewParent) {
        super(context, logView, inviewParent);
        adRequest = new LVDOAdRequest(context);
        Chocolate.enableChocolateTestAds(true);
        Chocolate.init((Activity)context, Config.CHOCOLATE_API_KEY, new InitCallback() {
            @Override
            public void onSuccess() {
                log("Chocolate initialized");
            }

            @Override
            public void onError(String msg) {
                log("Chocolate not initialized");
            }
        });
    }

    void loadBannerAd() {
        ChocolatePartners.choosePartners(ChocolatePartners.INVIEW, context, (dialog, which) -> {
            ChocolatePartners.setInviewPartners(adRequest);
            if (bannerAd == null) {
                bannerAd = new LVDOBannerAd(context, LVDOAdSize.IAB_MRECT, Config.CHOCOLATE_API_KEY,ChocolateAds.this);
            }
            bannerAd.loadAd(adRequest);
        });
    }

    void loadChocolateRewarded() {
        ChocolatePartners.choosePartners(ChocolatePartners.REWARDED, context, (dialog, which) -> {
            ChocolatePartners.setRewardedPartners(adRequest);
            if (rewardedAd == null) {
                rewardedAd = new LVDORewardedAd(context, Config.CHOCOLATE_API_KEY, this);
            }
            if (rewardedAd.isReady()) {
                rewardedAd.showRewardAd("mysecret", "myuserid", "coins", "30");
            } else {
                rewardedAd.loadAd(adRequest);
            }
        });
    }

    @Override
    public void onRewardedVideoLoaded(LVDORewardedAd lvdoRewardedAd) {
        handler.post(()-> rewardedAd.showRewardAd("mysecret", "myuserid", "coins", "30"));
    }

    @Override
    public void onRewardedVideoFailed(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {
        log(TAG + "onRewardedVideoFailed. errorCode: " + lvdoErrorCode);
        toast("Rewarded Ad: " + lvdoErrorCode);
    }

    @Override
    public void onRewardedVideoShown(LVDORewardedAd lvdoRewardedAd) {
        log(TAG + "onRewardedVideoShown.  winner: " + lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onRewardedVideoShownError(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {
        log(TAG + "onRewardedVideoShownError. " + lvdoRewardedAd.getWinningPartnerName() + " error: " + lvdoErrorCode);
    }

    @Override
    public void onRewardedVideoDismissed(LVDORewardedAd lvdoRewardedAd) {
        log(TAG + "onRewardedVideoDismissed. winner: " + lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onRewardedVideoCompleted(LVDORewardedAd lvdoRewardedAd) {
        log(TAG + "onRewardedVideoCompleted. winner: " + lvdoRewardedAd.getWinningPartnerName());
    }

    @Override
    public void onBannerAdLoaded(View view) {
        log(TAG + "onBannerAdLoaded. winner: " + bannerAd.getWinningPartnerName());
        inviewParent.removeAllViews();
        inviewParent.addView(view);
    }

    @Override
    public void onBannerAdFailed(View view, LVDOConstants.LVDOErrorCode lvdoErrorCode) {
        log(TAG + "onBannerAdLoaded. error: "+lvdoErrorCode);
    }

    @Override
    public void onBannerAdClicked(View view) {
        log(TAG + "onBannerAdClicked. winner: " + bannerAd.getWinningPartnerName());
    }

    @Override
    public void onBannerAdClosed(View view) {
        log(TAG + "onBannerAdClosed. winner: " + bannerAd.getWinningPartnerName());
    }
}
