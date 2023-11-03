package com.example.screenshareddemo;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.HiddenApiBypass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_screen_test";
    private Context mContext;

    private Button btn_screen_shared_detection;
    private Button btn_screen_recording_detection;
    private final HashSet<Integer> allDisplayId = new HashSet<>();
    private final HashSet<Integer> displayIdCache = new HashSet<>();
    private ConcurrentHashMap<String, String> displayMap = new ConcurrentHashMap<>();
    private Boolean isCaptured = false;
    private Boolean isScreenShared = false;
    private Boolean isScreenShared_td = false;
    private Boolean isCapturedHistory = false;
    private Button btn_screen_shared_detection_td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 环境初始化
        mContext = this;
        btn_screen_shared_detection = (Button) findViewById(R.id.btn_screen_shared_detection);
        btn_screen_recording_detection = (Button) findViewById(R.id.btn_screen_recording_detection);
        btn_screen_shared_detection_td = (Button) findViewById(R.id.btn_screen_shared_detection_td);

        btn_screen_shared_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    try {
                        Method getActiveProjectionInfoMethod = HiddenApiBypass.getDeclaredMethod(MediaProjectionManager.class, "getActiveProjectionInfo", new Class[0]);
                        //Object invoke = getActiveProjectionInfoMethod.invoke(mediaProjectionManager);
                        Object getActiveProjectionInfo = HiddenApiBypass.invoke(MediaProjectionManager.class, mediaProjectionManager, "getActiveProjectionInfo", new Object[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                checksSharingScreen(mContext);
                showToast(mContext, "芯盾是否屏幕共享:" + isScreenShared);
            }
        });

        btn_screen_recording_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checksSharingScreen(mContext);
                showToast(mContext, "芯盾是否屏幕录制:" + isCaptured);
            }
        });

        btn_screen_shared_detection_td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenSharingStatus(mContext);
                showToast(mContext, "同盾是否屏幕录制:" + isScreenShared_td);
            }
        });

    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void checksSharingScreen(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            final DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.xindun.sdk.core.DeviceInfoUtils.DynamicInfoManager.8
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                    checkDisplay(i, displayManager.getDisplay(i), false);
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                    checkDisplay(i, displayManager.getDisplay(i), true);
                }
            }, new Handler(Looper.getMainLooper()));
            Display[] displays = displayManager.getDisplays();
            if (displays == null) {
                return;
            }
            int length = displays.length;
            if (length > 1) {
                isScreenShared = true;
            } else {
                isScreenShared = false;
            }
            for (int i = 0; i < length; i++) {
                Display display = displays[i];
                if (display != null) {
                    checkDisplay(display.getDisplayId(), display, false);
                }
            }
        }
    }

    public void checkDisplay(int i, Display display, boolean z) {
        if (i == 0) {
            return;
        }
        String str = "";
        if (display != null) {
            try {
                Field instanceField = Build.VERSION.SDK_INT >= 28 ? HiddenApiBypass.getInstanceField(Display.class, "mType") : display.getClass().getDeclaredField("mType");
                instanceField.setAccessible(true);
                if (5 != ((Integer) instanceField.get(display)).intValue()) {
                    return;
                }
                Field instanceField2 = Build.VERSION.SDK_INT >= 28 ? HiddenApiBypass.getInstanceField(Display.class, "mOwnerPackageName") : display.getClass().getDeclaredField("mOwnerPackageName");
                instanceField2.setAccessible(true);
                str = (String) instanceField2.get(display);
            } catch (Throwable th) {
                Log.d(TAG, "display get: " + th);
                return;
            }
        }
        PowerManager powerManager = (PowerManager) mContext.getSystemService("power");
        if (z) {
            displayIdCache.remove(Integer.valueOf(i));
            if (displayIdCache.size() == 0) {
                isCaptured = false;
            }
        } else {
            displayIdCache.add(Integer.valueOf(i));
            if (powerManager != null && powerManager.isScreenOn()) {
                isCaptured = true;
            }
        }
        if (allDisplayId.contains(Integer.valueOf(i))) {
            return;
        }
        allDisplayId.add(Integer.valueOf(i));
        if (powerManager != null && powerManager.isScreenOn()) {
            isCapturedHistory = true;
        }
        if (TextUtils.isEmpty(str)) {
            displayMap.put(String.valueOf(i), String.valueOf(i));
        } else if (!displayMap.containsValue(str)) {
            displayMap.put(String.valueOf(i), str);
        }
        Log.d(TAG, "display getAll:" + displayMap.toString());
    }

    public String getScreenSharingStatus(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
                Display[] displays = displayManager.getDisplays();
                if (displays != null) {
                    Display display = null;
                    if (displays.length > 1) {
                        display = displays[1];
                        isScreenShared_td = true;

                    } else if (displays.length == 1) {
                        display = displays[0];
                        isScreenShared_td = false;
                    }
                    if (display != null) {
                        String ownPackageName = getOwnPackageName(display);
                        return String.format(Locale.US, "%s|%d|%d|%s", display.getName(), display.getFlags(), displays.length, ownPackageName);
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static String getOwnPackageName(Display display) {
        try {
            Method getOwnerPackageName = Display.class.getDeclaredMethod("getOwnerPackageName");
            Object ownerPackageName = getOwnerPackageName.invoke(display);
            if (ownerPackageName != null) {
                return (String) ownerPackageName;
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}