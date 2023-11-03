package com.core.util;

import java.util.Locale;

public final class OsDefiner {

    private static OsType detectedOS;

    public static OsType getOperatingSystemType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || (os.contains("darwin"))) {
                detectedOS = OsType.MacOS;
            } else if (os.contains("win")) {
                detectedOS = OsType.Windows;
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                detectedOS = OsType.Linux;
            } else {
                detectedOS = OsType.Other;
            }
        }
        return detectedOS;
    }

    public static String getDefaultPath(OsType osType){
        return switch (osType) {
            case Windows -> System.getProperty("java.io.tmpdir");
            case MacOS, Linux -> "/tmp/";
            default -> "/";
        };
    }

    public enum OsType {
        Windows,
        MacOS,
        Linux,
        Other
    }
}
