import React, { Component } from 'react';
import {
  Platform,
  NativeModules,
  NativeAppEventEmitter,
  DeviceEventEmitter
} from 'react-native';

var mobileAnalyticsClient = NativeModules.AWSRNMobileAnalytics;
var listener;

if (Platform.OS === 'ios'){
  listener = NativeAppEventEmitter;
}else{
  listener = DeviceEventEmitter;
}

var initialized;
export default class AWSMobileAnalytics {

  constructor(){
    console.log("Constructing...");
  }

  initWithOptions(options){
    if(!options.identity_pool_id){
     return "Error: No identity_pool_id";
    }
    if(!options.app_id){
     return "Error: No app_id";
    }

    console.log("creating the instance of AWSRNMobileAnalyticsModule");
    mobileAnalyticsClient.initWithOptions(options);
    initialized = true;
  }

  createEvent(event_type, attributes, options){
    mobileAnalyticsClient.createEvent(event_type, attributes, options);
  }

  isInitialized() {
    return initialized;
  }
}

