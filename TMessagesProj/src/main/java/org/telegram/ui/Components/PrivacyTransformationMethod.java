package org.telegram.ui.Components;

import android.graphics.Rect;
import android.text.GetChars;
import android.text.method.TransformationMethod;
import android.view.View;

public class PrivacyTransformationMethod implements TransformationMethod {
    public static final PrivacyTransformationMethod INSTANCE = new PrivacyTransformationMethod();
    private static final char[] SYMBOLS = "#$!@%^&*".toCharArray();

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new Masked(source);
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {}

    private static class Masked implements CharSequence, GetChars {
        private final CharSequence src;
        Masked(CharSequence src) { this.src = src; }
        public int length() { return src.length(); }
        public char charAt(int i) {
            char c = src.charAt(i);
            return (c == ' ' || c == '\n') ? c : SYMBOLS[c % SYMBOLS.length];
        }
        public CharSequence subSequence(int s, int e) { return new Masked(src.subSequence(s, e)); }
        public void getChars(int s, int e, char[] dest, int off) {
            for (int i = s; i < e; i++) dest[off + i - s] = charAt(i);
        }
        @Override public String toString() { return new StringBuilder(this).toString(); }
    }
}