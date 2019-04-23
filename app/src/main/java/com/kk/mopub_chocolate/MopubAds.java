package com.kk.mopub_chocolate;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.nativeads.MediaViewBinder;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.MoPubVideoNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;
import com.vdopia.ads.lw.VdopiaLogger;

import java.util.EnumSet;
import java.util.Set;

class MopubAds extends BaseAds implements MoPubNative.MoPubNativeNetworkListener {

    private static final String TAG = "MopubDemo: ";
    private MoPubNative moPubNative;
    private View view;
    private RelativeLayout nativeAdView;

    MopubAds(Context context, TextView logView, ViewGroup inviewParent) {
        super(context, logView, inviewParent);
        nativeAdView = new RelativeLayout(context);
        MoPub.initializeSdk(context, new SdkConfiguration.Builder(Config.MOPUB_REWARDED_AD_UNIT_ID).build(),
                new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {
                        log("MoPub initialized");
                    }
                });
        MoPub.initializeSdk(context, new SdkConfiguration.Builder(Config.MOPUB_INVIEW_AD_UNIT_ID).build(),
                new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {
                        log("MoPub initialized");
                    }
                });
    }

    private MoPubRewardedVideoListener rewardedVideoListener = new MoPubRewardedVideoListener() {
        @Override
        public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
            log(TAG + "onRewardedVideoLoadSuccess: " + adUnitId);
            handler.post(() -> MoPubRewardedVideos.showRewardedVideo(Config.MOPUB_REWARDED_AD_UNIT_ID));
        }

        @Override
        public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            log(TAG + "onRewardedVideoLoadFailure: " + adUnitId + " errorCode: " + errorCode.toString());
        }

        @Override
        public void onRewardedVideoStarted(@NonNull String adUnitId) {
            log(TAG + "onRewardedVideoStarted: " + adUnitId);
        }

        @Override
        public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            log(TAG + "onRewardedVideoPlaybackError: " + adUnitId + " errorCode: " + errorCode.toString());
        }

        @Override
        public void onRewardedVideoClicked(@NonNull String adUnitId) {
            log(TAG + "onRewardedVideoClicked: " + adUnitId);
        }

        @Override
        public void onRewardedVideoClosed(@NonNull String adUnitId) {
            log(TAG + "onRewardedVideoClosed: " + adUnitId);
        }

        @Override
        public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
            log(TAG + "onRewardedVideoCompleted: " + adUnitIds + " reward: " + reward.getLabel() + " " + reward.getAmount());
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

    void loadInviewAd() {
        String adID = Config.MOPUB_INVIEW_AD_UNIT_ID;
        VdopiaLogger.d(TAG, "showNativeAd. ad id: " + adID);
        moPubNative = new MoPubNative(context, adID, this);

        //Register static renderer
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(new ViewBinder.Builder(com.vdopia.ads.lw.mopub.R.layout.static_ad_list_item).mainImageId(com.vdopia.ads.lw.mopub.R.id.native_image_layout).build());
        moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

        //Register video renderer
        MoPubVideoNativeAdRenderer moPubVideoNativeAdRenderer = new MoPubVideoNativeAdRenderer(new MediaViewBinder.Builder(com.vdopia.ads.lw.mopub.R.layout.video_ad_list_item).mediaLayoutId(com.vdopia.ads.lw.mopub.R.id.native_media_layout).build());
        moPubNative.registerAdRenderer(moPubVideoNativeAdRenderer);

        //Specify which native assets you want to use in your ad.
        EnumSet<RequestParameters.NativeAdAsset> assetsSet = EnumSet.of(RequestParameters.NativeAdAsset.MAIN_IMAGE);
        RequestParameters requestParameters;
        requestParameters = new RequestParameters.Builder().desiredAssets(assetsSet).build();
        moPubNative.makeRequest(requestParameters);

    }

    @Override
    public void onNativeLoad(NativeAd nativeAd) {
        view = nativeAd.createAdView(context, nativeAdView);
        nativeAd.prepare(view);
        nativeAd.renderAdView(view);
        nativeAd.setMoPubNativeEventListener(new NativeAd.MoPubNativeEventListener() {
            @Override
            public void onImpression(View view) {
                log("Native Mopub Impression 1...");
            }

            @Override
            public void onClick(View view) {
                log("Native Mopub has been clicked 3...");
            }
        });

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Math.round(300 * dm.density), Math.round(250 * dm.density));
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);

        RelativeLayout container = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(Gravity.CENTER);
        container.setLayoutParams(params);
        container.setBackgroundColor(Color.WHITE);
        container.addView(view);
        inviewParent.removeAllViews();
        inviewParent.addView(container);
    }

    @Override
    public void onNativeFail(NativeErrorCode errorCode) {
        log("Mopub inview ad failed: " + errorCode);
        toast("Mopub inview ad failed: " + errorCode);
    }
}
