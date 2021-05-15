//package com.company1.accessibilityservice_test;
//
//import android.content.Context;
//import android.content.Intent;
//
//import androidx.test.runner.AndroidJUnit4;
//import androidx.test.uiautomator.By;
//import androidx.test.uiautomator.UiDevice;
//import androidx.test.uiautomator.Until;
//
//import org.junit.Before;
//
//@RunWith(AndroidJUnit4.class)
//@SdkSuppress(minSdkVersion = 18)
//public class ChangeTextBehaviorTest {
//
//    private static final String BASIC_SAMPLE_PACKAGE
//            = "com.example.android.testing.uiautomator.BasicSample";
//    private static final int LAUNCH_TIMEOUT = 5000;
//    private static final String STRING_TO_BE_TYPED = "UiAutomator";
//    private UiDevice device;
//
//    @Before
//    public void startMainActivityFromHomeScreen() {
//        // Initialize UiDevice instance
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//
//        // Start from the home screen
//        device.pressHome();
//
//        // Wait for launcher
//        final String launcherPackage = device.getLauncherPackageName();
//        assertThat(launcherPackage, notNullValue());
//        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
//                LAUNCH_TIMEOUT);
//
//        // Launch the app
//        Context context = ApplicationProvider.getApplicationContext();
//        final Intent intent = context.getPackageManager()
//                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
//        // Clear out any previous instances
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//
//        // Wait for the app to appear
//        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
//                LAUNCH_TIMEOUT);
//    }
//}
