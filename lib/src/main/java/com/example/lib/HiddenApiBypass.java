package com.example.lib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import sun.misc.Unsafe;

@RequiresApi(28)
/* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
public final class HiddenApiBypass {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long artFieldBias;
    private static final long artFieldSize;
    private static final long artMethodBias;
    private static final long artMethodSize;
    private static final long artOffset;
    private static final long classOffset;
    private static final long iFieldOffset;
    private static final long infoOffset;
    private static final long memberOffset;
    private static final long methodOffset;
    private static final long methodsOffset;
    private static final long sFieldOffset;
    private static final Unsafe unsafe;

    /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
    static class Helper {

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static class AccessibleObject {
            private boolean override;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static final class Class {
            private transient int accessFlags;
            private transient int classFlags;
            private transient ClassLoader classLoader;
            private transient int classSize;
            private transient int clinitThreadId;
            private transient java.lang.Class<?> componentType;
            private transient short copiedMethodsOffset;
            private transient Object dexCache;
            private transient int dexClassDefIndex;
            private volatile transient int dexTypeIndex;
            private transient Object extData;
            private transient long iFields;
            private transient Object[] ifTable;
            private transient long methods;
            private transient String name;
            private transient int numReferenceInstanceFields;
            private transient int numReferenceStaticFields;
            private transient int objectSize;
            private transient int objectSizeAllocFastPath;
            private transient int primitiveType;
            private transient int referenceInstanceOffsets;
            private transient long sFields;
            private transient int status;
            private transient java.lang.Class<?> superClass;
            private transient short virtualMethodsOffset;
            private transient Object vtable;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static final class Executable extends AccessibleObject {
            private int accessFlags;
            private long artMethod;
            private Class declaringClass;
            private Class declaringClassOfOverriddenMethod;
            private Object[] parameters;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static final class HandleInfo {
            private final Member member = null;
            private final MethodHandle handle = null;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static class InvokeStub {
            private InvokeStub(Object... objArr) {
                throw new IllegalStateException("Failed to new a instance");
            }

            private static Object invoke(Object... objArr) {
                throw new IllegalStateException("Failed to invoke the method");
            }
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static class MethodHandle {
            private MethodHandle cachedSpreadInvoker;
            private MethodType nominalType;
            private final MethodType type = null;
            protected final int handleKind = 0;
            protected final long artFieldOrMethod = 0;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static final class MethodHandleImpl extends MethodHandle {
            private final MethodHandleInfo info = null;
        }

        /* loaded from: /Volumes/Transcend/reverse/xdkj_classes.dex */
        public static class NeverCall {

            /* renamed from: s */
            private static int s;

            /* renamed from: t */
            private static int t;

            /* renamed from: i */
            private int i;

            /* renamed from: j */
            private int j;

            /* renamed from: a */
            private static void a() {
            }

            /* renamed from: b */
            private static void b() {
            }
        }

        Helper() {
        }
    }

    static {
        try {
            unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", new Class[0]).invoke(null, new Object[0]);
            methodOffset = unsafe.objectFieldOffset(Helper.Executable.class.getDeclaredField("artMethod"));
            classOffset = unsafe.objectFieldOffset(Helper.Executable.class.getDeclaredField("declaringClass"));
            artOffset = unsafe.objectFieldOffset(Helper.MethodHandle.class.getDeclaredField("artFieldOrMethod"));
            infoOffset = unsafe.objectFieldOffset(Helper.MethodHandleImpl.class.getDeclaredField("info"));
            methodsOffset = unsafe.objectFieldOffset(Helper.Class.class.getDeclaredField("methods"));
            iFieldOffset = unsafe.objectFieldOffset(Helper.Class.class.getDeclaredField("iFields"));
            sFieldOffset = unsafe.objectFieldOffset(Helper.Class.class.getDeclaredField("sFields"));
            memberOffset = unsafe.objectFieldOffset(Helper.HandleInfo.class.getDeclaredField("member"));
            Method declaredMethod = Helper.NeverCall.class.getDeclaredMethod("a", new Class[0]);
            Method declaredMethod2 = Helper.NeverCall.class.getDeclaredMethod("b", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            MethodHandle unreflect = MethodHandles.lookup().unreflect(declaredMethod);
            MethodHandle unreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
            long j = unsafe.getLong(unreflect, artOffset);
            long j2 = unsafe.getLong(unreflect2, artOffset);
            long j3 = unsafe.getLong(Helper.NeverCall.class, methodsOffset);
            artMethodSize = j2 - j;
            artMethodBias = (j - j3) - artMethodSize;
            Field declaredField = Helper.NeverCall.class.getDeclaredField("i");
            Field declaredField2 = Helper.NeverCall.class.getDeclaredField("j");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            MethodHandle unreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
            long j4 = unsafe.getLong(unreflectGetter, artOffset);
            long j5 = unsafe.getLong(unreflectGetter2, artOffset);
            long j6 = unsafe.getLong(Helper.NeverCall.class, iFieldOffset);
            artFieldSize = j5 - j4;
            artFieldBias = j4 - j6;
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @VisibleForTesting
    static boolean checkArgsForInvokeMethod(Class<?>[] clsArr, Object[] objArr) {
        if (clsArr.length != objArr.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (clsArr[i].isPrimitive()) {
                if (clsArr[i] == Integer.TYPE && !(objArr[i] instanceof Integer)) {
                    return false;
                }
                if (clsArr[i] == Byte.TYPE && !(objArr[i] instanceof Byte)) {
                    return false;
                }
                if (clsArr[i] == Character.TYPE && !(objArr[i] instanceof Character)) {
                    return false;
                }
                if (clsArr[i] == Boolean.TYPE && !(objArr[i] instanceof Boolean)) {
                    return false;
                }
                if (clsArr[i] == Double.TYPE && !(objArr[i] instanceof Double)) {
                    return false;
                }
                if (clsArr[i] == Float.TYPE && !(objArr[i] instanceof Float)) {
                    return false;
                }
                if (clsArr[i] == Long.TYPE && !(objArr[i] instanceof Long)) {
                    return false;
                }
                if (clsArr[i] == Short.TYPE && !(objArr[i] instanceof Short)) {
                    return false;
                }
            } else if (objArr[i] != null && !clsArr[i].isInstance(objArr[i])) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public static Constructor<?> getDeclaredConstructor(@NonNull Class<?> cls, @NonNull Class<?>... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable instanceof Constructor) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] != parameterTypes[i]) {
                            break;
                        }
                    }
                    return (Constructor) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }

    @NonNull
    public static Method getDeclaredMethod(@NonNull Class<?> cls, @NonNull String str, @NonNull Class<?>... clsArr) throws NoSuchMethodException {
        for (Executable executable : getDeclaredMethods(cls)) {
            if (executable.getName().equals(str) && (executable instanceof Method)) {
                Class<?>[] parameterTypes = executable.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < clsArr.length; i++) {
                        if (clsArr[i] != parameterTypes[i]) {
                            break;
                        }
                    }
                    return (Method) executable;
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }

    @NonNull
    public static List<Executable> getDeclaredMethods(@NonNull Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        if (cls.isPrimitive() || cls.isArray()) {
            return arrayList;
        }
        try {
            Method declaredMethod = Helper.NeverCall.class.getDeclaredMethod("a", new Class[0]);
            declaredMethod.setAccessible(true);
            MethodHandle unreflect = MethodHandles.lookup().unreflect(declaredMethod);
            long j = unsafe.getLong(cls, methodsOffset);
            if (j == 0) {
                return arrayList;
            }
            int i = unsafe.getInt(j);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(unreflect, artOffset, j + (i2 * artMethodSize) + artMethodBias);
                unsafe.putObject(unreflect, infoOffset, (Object) null);
                try {
                    MethodHandles.lookup().revealDirect(unreflect);
                } catch (Throwable th) {
                }
                arrayList.add((Executable) unsafe.getObject((MethodHandleInfo) unsafe.getObject(unreflect, infoOffset), memberOffset));
            }
            return arrayList;
        } catch (IllegalAccessException | NoSuchMethodException e) {
            return arrayList;
        }
    }

    @NonNull
    public static Field getInstanceField(@NonNull Class<?> cls, @NonNull String str) throws NoSuchFieldException, IllegalAccessException {
        if (cls.isPrimitive() || cls.isArray()) {
            throw new NoSuchFieldException("this class is Primitive or Array");
        }
        Field declaredField = Helper.NeverCall.class.getDeclaredField("i");
        declaredField.setAccessible(true);
        MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
        long j = unsafe.getLong(cls, iFieldOffset);
        if (j == 0) {
            throw new NoSuchFieldException(str);
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            unsafe.putLong(unreflectGetter, artOffset, j + (i2 * artFieldSize) + artFieldBias);
            unsafe.putObject(unreflectGetter, infoOffset, (Object) null);
            try {
                MethodHandles.lookup().revealDirect(unreflectGetter);
            } catch (Throwable th) {
            }
            Field field = (Field) unsafe.getObject((MethodHandleInfo) unsafe.getObject(unreflectGetter, infoOffset), memberOffset);
            if (field != null && TextUtils.equals(str, field.getName())) {
                return field;
            }
        }
        throw new NoSuchFieldException(str);
    }

    @NonNull
    public static List<Field> getInstanceFields(@NonNull Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        if (cls.isPrimitive() || cls.isArray()) {
            return arrayList;
        }
        try {
            Field declaredField = Helper.NeverCall.class.getDeclaredField("i");
            declaredField.setAccessible(true);
            MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            long j = unsafe.getLong(cls, iFieldOffset);
            if (j == 0) {
                return arrayList;
            }
            int i = unsafe.getInt(j);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(unreflectGetter, artOffset, j + (i2 * artFieldSize) + artFieldBias);
                unsafe.putObject(unreflectGetter, infoOffset, (Object) null);
                try {
                    MethodHandles.lookup().revealDirect(unreflectGetter);
                } catch (Throwable th) {
                }
                arrayList.add((Field) unsafe.getObject((MethodHandleInfo) unsafe.getObject(unreflectGetter, infoOffset), memberOffset));
            }
            return arrayList;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return arrayList;
        }
    }

    @NonNull
    public static Field getStaticField(@NonNull Class<?> cls, @NonNull String str) throws NoSuchFieldException, IllegalAccessException {
        if (cls.isPrimitive() || cls.isArray()) {
            throw new NoSuchFieldException("this class is Primitive or Array");
        }
        Field declaredField = Helper.NeverCall.class.getDeclaredField("s");
        declaredField.setAccessible(true);
        MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
        long j = unsafe.getLong(cls, sFieldOffset);
        if (j == 0) {
            throw new NoSuchFieldException(str);
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            unsafe.putLong(unreflectGetter, artOffset, j + (i2 * artFieldSize) + artFieldBias);
            unsafe.putObject(unreflectGetter, infoOffset, (Object) null);
            try {
                MethodHandles.lookup().revealDirect(unreflectGetter);
            } catch (Throwable th) {
            }
            Field field = (Field) unsafe.getObject((MethodHandleInfo) unsafe.getObject(unreflectGetter, infoOffset), memberOffset);
            if (field != null && TextUtils.equals(str, field.getName())) {
                return field;
            }
        }
        throw new NoSuchFieldException(str);
    }

    @NonNull
    public static List<Field> getStaticFields(@NonNull Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        if (cls.isPrimitive() || cls.isArray()) {
            return arrayList;
        }
        try {
            Field declaredField = Helper.NeverCall.class.getDeclaredField("s");
            declaredField.setAccessible(true);
            MethodHandle unreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            long j = unsafe.getLong(cls, sFieldOffset);
            if (j == 0) {
                return arrayList;
            }
            int i = unsafe.getInt(j);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(unreflectGetter, artOffset, j + (i2 * artFieldSize) + artFieldBias);
                unsafe.putObject(unreflectGetter, infoOffset, (Object) null);
                try {
                    MethodHandles.lookup().revealDirect(unreflectGetter);
                } catch (Throwable th) {
                }
                arrayList.add((Field) unsafe.getObject((MethodHandleInfo) unsafe.getObject(unreflectGetter, infoOffset), memberOffset));
            }
            return arrayList;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return arrayList;
        }
    }

    public static Object invoke(@NonNull Class<?> cls, @Nullable Object obj, @NonNull String str, Object... objArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (obj == null || cls.isInstance(obj)) {
            Method declaredMethod = Helper.InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
            declaredMethod.setAccessible(true);
            long j = unsafe.getLong(cls, methodsOffset);
            if (j == 0) {
                throw new NoSuchMethodException("Cannot find matching method");
            }
            int i = unsafe.getInt(j);
            for (int i2 = 0; i2 < i; i2++) {
                unsafe.putLong(declaredMethod, methodOffset, j + (i2 * artMethodSize) + artMethodBias);
                if (str.equals(declaredMethod.getName()) && checkArgsForInvokeMethod(declaredMethod.getParameterTypes(), objArr)) {
                    return declaredMethod.invoke(obj, objArr);
                }
            }
            throw new NoSuchMethodException("Cannot find matching method");
        }
        throw new IllegalArgumentException("this object is not an instance of the given class");
    }

    public static Object newInstance(@NonNull Class<?> cls, Object... objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method declaredMethod = Helper.InvokeStub.class.getDeclaredMethod("invoke", Object[].class);
        Constructor declaredConstructor = Helper.InvokeStub.class.getDeclaredConstructor(Object[].class);
        declaredConstructor.setAccessible(true);
        long j = unsafe.getLong(cls, methodsOffset);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching constructor");
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            long j2 = j + (i2 * artMethodSize) + artMethodBias;
            unsafe.putLong(declaredMethod, methodOffset, j2);
            if ("<init>".equals(declaredMethod.getName())) {
                unsafe.putLong(declaredConstructor, methodOffset, j2);
                unsafe.putObject(declaredConstructor, classOffset, cls);
                if (checkArgsForInvokeMethod(declaredConstructor.getParameterTypes(), objArr)) {
                    return declaredConstructor.newInstance(objArr);
                }
            }
        }
        throw new NoSuchMethodException("Cannot find matching constructor");
    }
}
