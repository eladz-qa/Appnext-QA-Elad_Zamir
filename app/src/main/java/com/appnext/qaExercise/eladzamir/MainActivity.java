package com.appnext.qaExercise.eladzamir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// AppNext SDK packages:
import com.appnext.ads.AdsError;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
import com.appnext.core.ECPM;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnAdOpened;
import com.appnext.core.callbacks.OnAdClicked;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnECPMLoaded;

import java.util.logging.LogManager;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate SDK when the app is loaded
        Appnext.init(getApplicationContext());

        // Ad definition \ construction
        final Interstitial interstitial_Ad = new Interstitial(this, "24233eba-345a-4128-a6af-48e14d9dd858");
        Button showAd;

        // Button-Ad opening integration
        showAd = (Button)findViewById(R.id.button);
        showAd.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v){
                                          if (interstitial_Ad.isAdLoaded()){
                                              interstitial_Ad.showAd();

                                          } else {
                                              interstitial_Ad.loadAd();
                                              interstitial_Ad.showAd();
                                          }

                                          // AppNext SDK Callbacks:
                                          // Get callback for ad loaded
                                          interstitial_Ad.setOnAdLoadedCallback(new OnAdLoaded() {
                                              @Override
                                              public void adLoaded(String bannerID) {
                                                  Log.i("setOnAdLoadedCallback", "AD is loaded");
                                              }
                                          });

                                          // Get callback for ad opened
                                          interstitial_Ad.setOnAdOpenedCallback(new OnAdOpened() {
                                              @Override
                                              public void adOpened() {
                                                  Log.i("setOnAdOpenedCallback", "AD is open");
                                              }
                                          });

                                          // Get callback for ad clicked
                                          interstitial_Ad.setOnAdClickedCallback(new OnAdClicked() {
                                              @Override
                                              public void adClicked() {
                                                  Log.i("setOnAdClickedCallback", "AD was clicked");

                                              }
                                          });

                                          // Get callback for ad closed
                                          interstitial_Ad.setOnAdClosedCallback(new OnAdClosed() {
                                              @Override
                                              public void onAdClosed() {
                                                  Log.i("setOnAdClosedCallback", "AD closed");
                                              }
                                          });

                                          // Get callback for ad error
                                          interstitial_Ad.setOnAdErrorCallback(new OnAdError() {
                                              @Override
                                              public void adError(String error) {
                                              }
                                          });

                                      }          });


        // Callback exception messages in log
        interstitial_Ad.setOnAdErrorCallback(new OnAdError() {
                                                 @Override
                                                 public void adError(String error) {
                                                     switch (error){
                                                         case AdsError.AD_NOT_READY:
                                                             Log.v("appnext", "Callback adError - ad not ready");
                                                             break;

                                                         case AdsError.NO_ADS:
                                                             Log.v("appnext", "Callback adError - no ads");
                                                             break;

                                                         case AdsError.CONNECTION_ERROR:
                                                             Log.v("appnext", "Callback adError - connection problem");
                                                             break;

                                                         case AdsError.SLOW_CONNECTION:
                                                             Log.v("appnext", "Callback adError - slow connection");
                                                             break;

                                                         case AdsError.INTERNAL_ERROR:
                                                             Log.v("appnext", "Callback adError - internal error");
                                                             break;

                                                         case AdsError.NO_MARKET:
                                                             Log.v("appnext", "Callback adError - no market");
                                                             break;

                                                         case AdsError.TIMEOUT:
                                                             Log.v("appnext", "Callback adError - timeout");
                                                             break;

                                                         default:
                                                             Log.v("appnext", "Callback adError - other error");
                                                     }
                                                 }
                                             }
                                         );


        // AppNext In-App Header Bidding - ECPM
        interstitial_Ad.getECPM(new OnECPMLoaded() {
                                    @Override
                                    public void ecpm(ECPM ecpm) {
                                        Log.v("ecpm", "ecpm: " + ecpm.getEcpm() + ", ppr: " + ecpm.getPpr() + ", banner ID: " + ecpm.getBanner());
                                        interstitial_Ad.loadAd();
                                    }


                                    // ECPM exception message in log
                                    @Override
                                    public void error(String error) {
                                        Log.v("ecpm", "error: " + error);

                                    }
         }
    );
  }
}
