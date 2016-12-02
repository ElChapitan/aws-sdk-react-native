
package com.amazonaws.reactnative.mobileanalytics;

import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.amazonaws.mobileconnectors.amazonmobileanalytics.*;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;


public class AWSRNMobileAnalyticsModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static final String APP_ID = "app_id";
    private static final String IDENTITY_POOL_ID = "identity_pool_id";
    private static final String LOG_STRING = "MobileAnalyticsModule";

    private final ReactApplicationContext reactContext;

    private static MobileAnalyticsManager analytics;

    public AWSRNMobileAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "AWSRNMobileAnalytics";
    }

    @ReactMethod
    public void initWithOptions(final ReadableMap options) throws IllegalArgumentException {
        if (!options.hasKey(IDENTITY_POOL_ID)) {
            throw new IllegalArgumentException("identity_pool_id not supplied");
        } else if (!options.hasKey(APP_ID)) {
            throw new IllegalArgumentException("app_id not supplied");
        }

        try {
            String mobileAnalyticsAppId = options.getString(APP_ID);
            String cognitoIdentityPoolId = options.getString(IDENTITY_POOL_ID);

            analytics = MobileAnalyticsManager.getOrCreateInstance(
                    reactContext,
                    mobileAnalyticsAppId, //Amazon Mobile Analytics App ID
                    cognitoIdentityPoolId //Amazon Cognito Identity Pool ID
            );
        } catch (InitializationException ex) {
            Log.e(LOG_STRING, "Failed to initialize Amazon Mobile Analytics", ex);
        }
    }

    @ReactMethod
    public void createEvent(String eventName, ReadableMap attributes, ReadableMap metrics){
        final AnalyticsEvent eventToRecord = analytics.getEventClient().createEvent(eventName);

        if (attributes != null) {
            ReadableMapKeySetIterator keyiter = attributes.keySetIterator();
            while(keyiter.hasNextKey()){
                String key = keyiter.nextKey();
                String value = attributes.getString(key);
                eventToRecord.withAttribute(key, value);
            }
        }

        if (attributes != null) {
            ReadableMapKeySetIterator iterator = metrics.keySetIterator();
            while(iterator.hasNextKey()){
                String key = iterator.nextKey();
                double value = metrics.getDouble(key);
                eventToRecord.withMetric(key, value);
            }
        }

        analytics.getEventClient().recordEvent(eventToRecord);
    }

    @ReactMethod
    public void submitEvents(){
        if(analytics != null){
            analytics.getEventClient().submitEvents();
        }
    }

    @ReactMethod
    public void pauseSession() {
        if(analytics != null){
            analytics.getSessionClient().pauseSession();
        }
    }

    @ReactMethod
    public void resumeSession() {
        if(analytics != null){
            analytics.getSessionClient().resumeSession();
        }
    }

    @Override
    public void onHostResume() {
        resumeSession();
    }

    @Override
    public void onHostPause() {
        submitEvents();
        pauseSession();
    }

    @Override
    public void onHostDestroy() {

    }
}
