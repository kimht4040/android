package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Paint notePaint;
    private MainActivity activity;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (MainActivity) context;

        notePaint = new Paint();
        notePaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 노트 그리기
        for (Note note : activity.getNotes()) {
            canvas.drawCircle(note.x, note.y, 50, notePaint);
        }

        // 판정선 그리기
        Paint linePaint = new Paint();
        linePaint.setColor(Color.RED);
        canvas.drawLine(0, getHeight() - 100, getWidth(), getHeight() - 100, linePaint);
    }
}