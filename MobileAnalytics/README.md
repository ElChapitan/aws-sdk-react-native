
# react-native-mobile-analytics

## Getting started

`$ npm install react-native-mobile-analytics --save`

### Mostly automatic installation

`$ react-native link react-native-mobile-analytics`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mobile-analytics` and add `AWSRNMobileAnalytics.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libAWSRNMobileAnalytics.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.amazonaws.reactnative.mobileanalytics.AWSRNMobileAnalyticsPackage;` to the imports at the top of the file
  - Add `new AWSRNMobileAnalyticsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-mobile-analytics'
  	project(':react-native-mobile-analytics').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mobile-analytics/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-mobile-analytics')
  	```


## Usage
```javascript
import AWSRNMobileAnalytics from 'react-native-mobile-analytics';

// TODO: What do with the module?
AWSRNMobileAnalytics;
```
  