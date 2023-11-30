package com.example.screenshareddemo;


import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.Log;
import android.view.Display;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes9.dex */
public class IndriverScreenSharing {
    public static String TAG = IndriverScreenSharing.class.getSimpleName();

    /* renamed from: a  reason: collision with root package name */
    public static boolean isAweray = false;

    /* renamed from: b  reason: collision with root package name */
    public static boolean isAirdroid = false;

    /* renamed from: c  reason: collision with root package name */
    public static boolean isVysor = false;

    /* renamed from: d  reason: collision with root package name */
    public static boolean f18876d = false;

    public static /* synthetic */ void b() {
        boolean d14 = isAweray();
        isAweray = d14;
        boolean c14 = isAirdroid();
        isAirdroid = c14;

        boolean f14 = isVysor();
        isVysor = f14;
        boolean e14 = e();
        f18876d = e14;
    }

    public static boolean isAirdroid() {
        int i14 = 8888;
        int i15 = 0;
        while (i14 <= 8892) {
            if (i14 == 8892) {
                i14 = 8765;
            }
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), i14));
                i15++;
                socket.close();
            } catch (ConnectException e14) {
                e14.printStackTrace();
            } catch (Exception e15) {
                e15.printStackTrace();
            }
            if (i14 == 8765) {
                break;
            }
            i14++;
        }
        if (i15 != 5) {
            return false;
        }
        return true;
    }

    public static boolean isAweray() {
        try {
            new DatagramSocket(5647).close();
            return false;
        } catch (Exception e14) {
            String obj = e14.toString();
            if (!obj.contains("EADDRINUSE")) {
                return false;
            }
            Log.e(TAG, "aweray port detected ");
            return true;
        }
    }

    public static boolean e() {
        int i14 = 0;
        for (int i15 = 20200; i15 <= 20202; i15++) {
            try {
                new DatagramSocket(i15).close();
            } catch (Exception e14) {
                String obj = e14.toString();
                e14.printStackTrace();
                if (obj.contains("EADDRINUSE")) {
                    i14++;
                }
            }
        }
        if (i14 < 2) {
            return false;
        }
        return true;
    }

    public static boolean isVysor() {
        int i14 = 0;
        for (int i15 = 53516; i15 <= 53519; i15++) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), i15));
                i14++;
                socket.close();
            } catch (ConnectException e14) {
                e14.printStackTrace();
            } catch (Exception e15) {
                e15.printStackTrace();
            }
        }
        if (i14 < 3) {
            return false;
        }
        return true;
    }

    public static void startScreenSharingChecker() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        newSingleThreadExecutor.execute(new Runnable() { // from class: ci.g
            @Override // java.lang.Runnable
            public final void run() {
                IndriverScreenSharing.b();
            }
        });
        newSingleThreadExecutor.shutdown();
    }


    public static String getScreenSharing(Context context) {
        Object obj;
        Object obj2;
        Display[] displays = ((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays();
        StringBuilder sb4 = new StringBuilder();
        for (Display display : displays) {
            StringBuilder sb5 = new StringBuilder();
            try {
                Field declaredField = display.getClass().getDeclaredField("mDisplayInfo");
                declaredField.setAccessible(true);
                Object obj3 = declaredField.get(display);
                if (Build.VERSION.SDK_INT >= 28) {
                    String str = "";
                    List<Field> fieldList = HiddenApiBypass.getInstanceFields(obj3.getClass());
                    for (Field field : fieldList) {
                        if (field.getName().equals("uniqueId") && (obj2 = field.get(obj3)) != null) {
                            String replaceAll = String.valueOf(obj2).replaceAll(",", ":");
                            StringBuilder sb6 = new StringBuilder(replaceAll);
                            if (str.length() > 0 && !replaceAll.contains(str)) {
                                sb6.append(":");
                                sb6.append(str);
                            }
                            sb5 = sb6;
                        }
                        if (field.getName().equals("ownerPackageName") && (obj = field.get(obj3)) != null) {
                            str = String.valueOf(obj).replaceAll(",", ":");
                            if (sb5.length() > 0) {
                                sb5.append(":");
                                sb5.append(str);
                            } else {
                                sb5.append(str);
                            }
                        }
                    }
                }
                if (sb4.length() == 0) {
                    sb4.append(display.getName());
                    sb4.append("*");
                    sb4.append((CharSequence) sb5);
                } else {
                    sb4.append(", ");
                    sb4.append(display.getName());
                    sb4.append("*");
                    sb4.append((CharSequence) sb5);
                }
            } catch (IllegalAccessException e14) {
                throw new RuntimeException(e14);
            } catch (NoSuchFieldException e15) {
                throw new RuntimeException(e15);
            }
        }
        if (IndriverScreenSharing.isAirdroid) {
            sb4.append(", ");
            sb4.append("airDroid");
        } else if (IndriverScreenSharing.isAweray) {
            sb4.append(", ");
            sb4.append("aweray");
        } else if (IndriverScreenSharing.isVysor) {
            sb4.append(", ");
            sb4.append("vysor");
        }
        return sb4.toString();
    }


}
