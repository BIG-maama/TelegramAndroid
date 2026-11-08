package org.telegram.messenger.forkgram;

import android.content.Context;
import android.content.SharedPreferences;
import org.telegram.messenger.ApplicationLoader;
import java.util.HashSet;

public class PrivacyMode {
    private static HashSet<Long> ids;

    private static SharedPreferences prefs() {
        return ApplicationLoader.applicationContext.getSharedPreferences("privacy_mode", Context.MODE_PRIVATE);
    }

    private static synchronized HashSet<Long> load() {
        if (ids == null) {
            ids = new HashSet<>();
            for (String k : prefs().getAll().keySet()) {
                try { ids.add(Long.parseLong(k)); } catch (Exception ignore) {}
            }
        }
        return ids;
    }

    public static synchronized boolean isEnabled(long dialogId) {
        return dialogId != 0 && load().contains(dialogId);
    }

    public static synchronized boolean toggle(long dialogId) {
        boolean on = !isEnabled(dialogId);
        SharedPreferences.Editor e = prefs().edit();
        if (on) { load().add(dialogId); e.putBoolean(String.valueOf(dialogId), true); }
        else { load().remove(dialogId); e.remove(String.valueOf(dialogId)); }
        e.apply();
        return on;
    }
}