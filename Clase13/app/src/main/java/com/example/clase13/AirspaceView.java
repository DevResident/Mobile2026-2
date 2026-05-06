package com.example.clase13;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class AirspaceView extends View {

    private final Paint bgPaint = new Paint();
    private final Paint gridPaint = new Paint();
    private final Paint gridDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint planePaint = new Paint();
    private final Paint planeRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint collisionPaint = new Paint();
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint planeIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private SimulationState state;
    private int gridSize = 10;

    public AirspaceView(Context context) {
        super(context);
        init();
    }

    public AirspaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AirspaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bgPaint.setColor(Color.parseColor("#101010"));
        bgPaint.setStyle(Paint.Style.FILL);

        gridPaint.setColor(Color.parseColor("#2A2A2A"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1f);

        gridDotPaint.setColor(Color.parseColor("#4A4A4A"));
        gridDotPaint.setStyle(Paint.Style.FILL);

        planePaint.setColor(Color.parseColor("#F4F4F4"));
        planePaint.setStyle(Paint.Style.FILL);

        planeRingPaint.setColor(Color.parseColor("#D71920"));
        planeRingPaint.setStyle(Paint.Style.STROKE);
        planeRingPaint.setStrokeWidth(3f);

        collisionPaint.setColor(Color.parseColor("#D71920"));
        collisionPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.parseColor("#050505"));
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        planeIconPaint.setColor(Color.parseColor("#050505"));
        planeIconPaint.setFakeBoldText(true);
        planeIconPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setState(SimulationState state, int gridSize) {
        this.state = state;
        this.gridSize = gridSize;
        invalidate();
    }

    private float cellSize() {
        return Math.min(getWidth(), getHeight()) / (float) gridSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state == null) return;

        float cell = cellSize();
        float totalSize = cell * gridSize;
        float startX = (getWidth() - totalSize) / 2f;
        float startY = (getHeight() - totalSize) / 2f;
        textPaint.setTextSize(cell * 0.45f);
        planeIconPaint.setTextSize(cell * 0.48f);

        // Fondo visual del grid
        canvas.drawRect(startX, startY, startX + totalSize, startY + totalSize, bgPaint);

        // Agrupar aviones por celda
        HashMap<String, ArrayList<Airplane>> cellMap = new HashMap<>();
        for (Airplane a : state.airplanes) {
            if (a.x < 0 || a.x >= gridSize || a.y < 0 || a.y >= gridSize) continue;
            String key = a.x + "," + a.y;
            if (!cellMap.containsKey(key)) cellMap.put(key, new ArrayList<>());
            cellMap.get(key).add(a);
        }

        // Dibujar aviones (F-02, F-05)
        for (HashMap.Entry<String, ArrayList<Airplane>> entry : cellMap.entrySet()) {
            ArrayList<Airplane> planes = entry.getValue();
            Airplane first = planes.get(0);
            float cx = startX + first.x * cell + cell / 2f;
            float cy = startY + first.y * cell + cell / 2f;
            float radius = cell * 0.38f;

            canvas.drawCircle(cx, cy, radius, planePaint);
            canvas.drawCircle(cx, cy, radius, planeRingPaint);
            drawPlaneIcon(canvas, first, cx, cy);
        }

        // Líneas del grid
        for (int i = 0; i <= gridSize; i++) {
            float posX = startX + i * cell;
            float posY = startY + i * cell;
            canvas.drawLine(posX, startY, posX, startY + totalSize, gridPaint);
            canvas.drawLine(startX, posY, startX + totalSize, posY, gridPaint);
            canvas.drawCircle(posX, posY, 2.5f, gridDotPaint);
        }

        // Marcar solo las colisiones reales del estado actual
        for (CollisionPoint point : state.collisionPoints) {
            drawLandingCollision(canvas, startX, startY, cell, point.x, point.y);
        }
    }

    private void drawPlaneIcon(Canvas canvas, Airplane plane, float cx, float cy) {
        canvas.save();
        canvas.rotate(plane.direction.rotationDeg(), cx, cy);
        canvas.drawText("✈", cx, cy + planeIconPaint.getTextSize() * 0.34f, planeIconPaint);
        canvas.restore();
    }

    private void drawLandingCollision(Canvas canvas, float startX, float startY, float cell, int x, int y) {
        float cx = startX + x * cell + cell / 2f;
        float cy = startY + y * cell + cell / 2f;
        canvas.drawCircle(cx, cy, cell * 0.30f, collisionPaint);
        textPaint.setColor(Color.WHITE);
        canvas.drawText("X", cx, cy + textPaint.getTextSize() * 0.38f, textPaint);
    }
}
