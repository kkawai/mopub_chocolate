package com.kk.mopub_chocolate;

import android.content.Context;
import android.widget.TextView;

import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOConstants;
import com.vdopia.ads.lw.LVDORewardedAd;
import com.vdopia.ads.lw.RewardedAdListener;

class ChocolateAds extends BaseAds implements RewardedAdListener {

    LVDORewardedAd rewardedAd;
    LVDOAdRequest adRequest;

    ChocolateAds(Context context, TextView logView) {
        super(context, logView);
        adRequest = new LVDOAdRequest(context);
    }

    void loadChocolateRewarded() {
        if (rewardedAd == null) {
            rewardedAd = new LVDORewardedAd(context, Config.CHOCOLATE_API_KEY, this);
        }
        if (rewardedAd.isReady()) {
            rewardedAd.showRewardAd("mysecret", "myuserid", "coins", "30");
        } else {
            rewardedAd.loadAd(adRequest);
        }
    }

    @Override
    public void onRewardedVideoLoaded(LVDORewardedAd lvdoRewardedAd) {
        rewardedAd.showRewardAd("mysecret", "myuserid", "coins", "30");
    }

    @Override
    public void onRewardedVideoFailed(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {

    }

    @Override
    public void onRewardedVideoShown(LVDORewardedAd lvdoRewardedAd) {

    }

    @Override
    public void onRewardedVideoShownError(LVDORewardedAd lvdoRewardedAd, LVDOConstants.LVDOErrorCode lvdoErrorCode) {

    }

    @Override
    public void onRewardedVideoDismissed(LVDORewardedAd lvdoRewardedAd) {

    }

    @Override
    public void onRewardedVideoCompleted(LVDORewardedAd lvdoRewardedAd) {

    }
}
