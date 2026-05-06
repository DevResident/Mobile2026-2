package com.example.clase12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DottedTitleTextView extends AppCompatTextView {

    private static final int ROWS = 7;
    private static final int COLS = 5;
    private static final float CHAR_GAP = 1.2f;
    private static final Map<Character, String[]> GLYPHS = createGlyphs();

    private final Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DottedTitleTextView(Context context) {
        super(context);
        init();
    }

    public DottedTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DottedTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dotPaint.setStyle(Paint.Style.FILL);
        setIncludeFontPadding(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence value = getText();
        if (TextUtils.isEmpty(value)) return;

        String text = normalize(value.toString());
        dotPaint.setColor(getCurrentTextColor());

        float availableWidth = Math.max(1, getWidth() - getPaddingLeft() - getPaddingRight());
        float targetCell = getTextSize() / ROWS;
        float neededWidth = measureDottedWidth(text, targetCell);
        float cell = neededWidth > availableWidth ? targetCell * (availableWidth / neededWidth) : targetCell;
        float radius = cell * 0.28f;
        float x = getPaddingLeft();
        float y = getPaddingTop() + radius;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String[] glyph = GLYPHS.getOrDefault(c, GLYPHS.get(' '));
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (glyph[row].charAt(col) == '1') {
                        canvas.drawCircle(x + col * cell + radius, y + row * cell, radius, dotPaint);
                    }
                }
            }
            x += (COLS + CHAR_GAP) * cell;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dottedHeight = Math.round(getTextSize() + getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(getMeasuredWidth(), Math.max(getMeasuredHeight(), dottedHeight));
    }

    private float measureDottedWidth(String text, float cell) {
        if (text.isEmpty()) return 0;
        return ((text.length() * COLS) + ((text.length() - 1) * CHAR_GAP)) * cell;
    }

    private String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized.toUpperCase(Locale.ROOT);
    }

    private static Map<Character, String[]> createGlyphs() {
        Map<Character, String[]> map = new HashMap<>();
        map.put(' ', glyph("00000", "00000", "00000", "00000", "00000", "00000", "00000"));
        map.put('0', glyph("01110", "10001", "10011", "10101", "11001", "10001", "01110"));
        map.put('1', glyph("00100", "01100", "00100", "00100", "00100", "00100", "01110"));
        map.put('2', glyph("01110", "10001", "00001", "00010", "00100", "01000", "11111"));
        map.put('3', glyph("11110", "00001", "00001", "01110", "00001", "00001", "11110"));
        map.put('4', glyph("00010", "00110", "01010", "10010", "11111", "00010", "00010"));
        map.put('5', glyph("11111", "10000", "11110", "00001", "00001", "10001", "01110"));
        map.put('6', glyph("00110", "01000", "10000", "11110", "10001", "10001", "01110"));
        map.put('7', glyph("11111", "00001", "00010", "00100", "01000", "01000", "01000"));
        map.put('8', glyph("01110", "10001", "10001", "01110", "10001", "10001", "01110"));
        map.put('9', glyph("01110", "10001", "10001", "01111", "00001", "00010", "01100"));
        map.put('A', glyph("01110", "10001", "10001", "11111", "10001", "10001", "10001"));
        map.put('B', glyph("11110", "10001", "10001", "11110", "10001", "10001", "11110"));
        map.put('C', glyph("01111", "10000", "10000", "10000", "10000", "10000", "01111"));
        map.put('D', glyph("11110", "10001", "10001", "10001", "10001", "10001", "11110"));
        map.put('E', glyph("11111", "10000", "10000", "11110", "10000", "10000", "11111"));
        map.put('F', glyph("11111", "10000", "10000", "11110", "10000", "10000", "10000"));
        map.put('G', glyph("01111", "10000", "10000", "10011", "10001", "10001", "01111"));
        map.put('H', glyph("10001", "10001", "10001", "11111", "10001", "10001", "10001"));
        map.put('I', glyph("11111", "00100", "00100", "00100", "00100", "00100", "11111"));
        map.put('J', glyph("00001", "00001", "00001", "00001", "10001", "10001", "01110"));
        map.put('K', glyph("10001", "10010", "10100", "11000", "10100", "10010", "10001"));
        map.put('L', glyph("10000", "10000", "10000", "10000", "10000", "10000", "11111"));
        map.put('M', glyph("10001", "11011", "10101", "10101", "10001", "10001", "10001"));
        map.put('N', glyph("10001", "11001", "10101", "10011", "10001", "10001", "10001"));
        map.put('O', glyph("01110", "10001", "10001", "10001", "10001", "10001", "01110"));
        map.put('P', glyph("11110", "10001", "10001", "11110", "10000", "10000", "10000"));
        map.put('Q', glyph("01110", "10001", "10001", "10001", "10101", "10010", "01101"));
        map.put('R', glyph("11110", "10001", "10001", "11110", "10100", "10010", "10001"));
        map.put('S', glyph("01111", "10000", "10000", "01110", "00001", "00001", "11110"));
        map.put('T', glyph("11111", "00100", "00100", "00100", "00100", "00100", "00100"));
        map.put('U', glyph("10001", "10001", "10001", "10001", "10001", "10001", "01110"));
        map.put('V', glyph("10001", "10001", "10001", "10001", "10001", "01010", "00100"));
        map.put('W', glyph("10001", "10001", "10001", "10101", "10101", "10101", "01010"));
        map.put('X', glyph("10001", "10001", "01010", "00100", "01010", "10001", "10001"));
        map.put('Y', glyph("10001", "10001", "01010", "00100", "00100", "00100", "00100"));
        map.put('Z', glyph("11111", "00001", "00010", "00100", "01000", "10000", "11111"));
        return map;
    }

    private static String[] glyph(String row1, String row2, String row3, String row4,
                                  String row5, String row6, String row7) {
        return new String[]{row1, row2, row3, row4, row5, row6, row7};
    }
}
