
#import "AWSRNMobileAnalytics.h"
#import "RCTLog.h"


@implementation AWSRNMobileAnalytics {
    AWSMobileAnalytics *analytics;
}

const NSString *POOL_ID = @"identity_pool_id";
const NSString *APP_ID = @"app_id";

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(initWithOptions:(NSDictionary *)inputOptions)
{
    RCTLogInfo(@"Setting up the analytics library...");
    NSString *identityPoolId = [inputOptions objectForKey:POOL_ID];
    NSString *appId = [inputOptions objectForKey:APP_ID];

    analytics = [AWSMobileAnalytics
                        mobileAnalyticsForAppId: appId
                        identityPoolId: identityPoolId];
}

RCT_EXPORT_METHOD(createEvent:(NSString*)eventType attributes:(NSDictionary*)eventAttributes metrics:(NSDictionary*)eventMetrics)
{
    id<AWSMobileAnalyticsEventClient> eventClient = analytics.eventClient;
    id<AWSMobileAnalyticsEvent> theEvent = [eventClient createEventWithEventType:eventType];
    
    if(eventAttributes != nil){
        for(id key in eventAttributes) {
            id value = [eventAttributes objectForKey:key];
            [theEvent addAttribute:value forKey:key];
        }
    }
    
    if(eventMetrics != nil){
        for(id key in eventMetrics) {
            id value = [eventMetrics objectForKey:key];
            [theEvent addMetric:value forKey:key];
        }
    }
    
    [eventClient recordEvent:theEvent];
    
    
}



@end
  
