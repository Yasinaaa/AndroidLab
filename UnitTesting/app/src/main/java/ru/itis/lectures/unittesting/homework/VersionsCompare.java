package ru.itis.lectures.unittesting.homework;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author Artur Vasilov
 */
public final class VersionsCompare {

    /**
     * Compare two versions of application
     * (e.g. current version of application, and actual version in Google Play)
     *
     * @param version1 - first version in format like x.x - where x - single number
     * @param version2 - second version in the same format
     * @return 1 if version1 is greater than version2,
     * -1 if version2 is greater than version0,
     * 0 if they're equal
     * @throws IllegalArgumentException if any version is in irregular format
     */
    public static int compare(@NonNull String version1, @NonNull String version2) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^\\d\\.\\d$");
        if (!pattern.matcher(version1).matches() || !pattern.matcher(version2).matches()) {
            throw new IllegalArgumentException("Version should be in format x.x where x is a single number");
        }

        String[] numbers1 = TextUtils.split(version1, "\\.");
        String[] numbers2 = TextUtils.split(version2, "\\.");

        int[] firstNumbers = {Integer.valueOf(numbers1[0]), Integer.valueOf(numbers1[1])};
        int[] secondNumbers = {Integer.valueOf(numbers2[0]), Integer.valueOf(numbers2[1])};

        if (firstNumbers[0] > secondNumbers[0]) {
            return 1;
        } else if (firstNumbers[0] < secondNumbers[0]) {
            return -1;
        }

        if (firstNumbers[1] > secondNumbers[1]) {
            return 1;
        } else if (firstNumbers[1] < secondNumbers[1]) {
            return -1;
        } else {
            return 0;
        }
    }

}



