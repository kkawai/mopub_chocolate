package com.kk.mopub_chocolate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.Set;

class MopubAds extends BaseAds {

    private static final String TAG = "MopubDemo";

    MopubAds(Context context, TextView logView) {
        super(context, logView);
    }

    private MoPubRewardedVideoListener rewardedVideoListener = new MoPubRewardedVideoListener() {
        @Override
        public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
            log(TAG + " onRewardedVideoLoadSuccess: " + adUnitId);
            handler.post(() -> MoPubRewardedVideos.showRewardedVideo(Config.MOPUB_REWARDED_AD_UNIT_ID));
        }

        @Override
        public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            log(TAG + "onRewardedVideoLoadFailure: " + adUnitId + " errorCode: " + errorCode.toString());
        }

        @Override
        public void onRewardedVideoStarted(@NonNull String adUnitId) {
            log(TAG + " onRewardedVideoStarted: " + adUnitId);
        }

        @Override
        public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            log(TAG + "onRewardedVideoPlaybackError: " + adUnitId + " errorCode: " + errorCode.toString());
        }

        @Override
        public void onRewardedVideoClicked(@NonNull String adUnitId) {
            log(TAG + " onRewardedVideoClicked: " + adUnitId);
        }

        @Override
        public void onRewardedVideoClosed(@NonNull String adUnitId) {
            log(TAG + " onRewardedVideoClosed: " + adUnitId);
        }

        @Override
        public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
            log(TAG + " onRewardedVideoCompleted: " + adUnitIds + " reward: " + reward.getLabel() + " " + reward.getAmount());
        }
    };

    void loadRewardedAd() {
        MoPubRewardedVideos.setRewardedVideoListener(rewardedVideoListener);
        if (MoPubRewardedVideos.hasRewardedVideo(Config.MOPUB_REWARDED_AD_UNIT_ID)) {
            MoPubRewardedVideos.showRewardedVideo(Config.MOPUB_REWARDED_AD_UNIT_ID);
        } else {
            MoPubRewardedVideos.loadRewardedVideo(Config.MOPUB_REWARDED_AD_UNIT_ID);
        }
    }
}
