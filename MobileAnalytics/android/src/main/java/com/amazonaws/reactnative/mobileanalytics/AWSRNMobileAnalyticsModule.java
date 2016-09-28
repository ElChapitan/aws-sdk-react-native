
package com.amazonaws.reactnative.mobileanalytics;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.amazonaws.mobileconnectors.amazonmobileanalytics.*;
import com.facebook.react.bridge.ReadableMap;

public class AWSRNMobileAnalyticsModule extends ReactContextBaseJavaModule {

    private static final String APP_ID = "app_id";
    private static final String IDENTITY_POOL_ID = "identity_pool_id";
    private static final String LOG_STRING = "MobileAnalyticsModule";

    private final ReactApplicationContext reactContext;

    private static MobileAnalyticsManager analytics;

    public AWSRNMobileAnalyticsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
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
}
