package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private ArrayList<Note> notes;  // 한 번만 선언
    private static final int SCREEN_WIDTH = 1080;
    private static final int NOTE_SPEED = 10;
    private long lastUpdateTime;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.game_view);
        notes = new ArrayList<>();  // notes 초기화
        score = 0;

        // 터치 이벤트 처리
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkNoteHit(event.getX());
                }
                return true;
            }
        });

        // 게임 루프 시작
        startGameLoop();
    }

    // notes getter 메소드 추가
    public ArrayList<Note> getNotes() {
        return notes;
    }

    private void startGameLoop() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateGame();
                gameView.invalidate();
                handler.postDelayed(this, 16); // 약 60FPS
            }
        });
    }

    private void updateGame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 2000) { // 2초마다 노트 생성
            createNote();
            lastUpdateTime = currentTime;
        }

        // 노트 이동
        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            note.y += NOTE_SPEED;

            // 화면 벗어난 노트 제거
            if (note.y > SCREEN_WIDTH) {
                iterator.remove();
                decreaseScore();
            }
        }
    }

    private void createNote() {
        float x = (float) (Math.random() * (SCREEN_WIDTH - 100));
        notes.add(new Note(x, 0));
    }

    private void checkNoteHit(float touchX) {
        for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = iterator.next();
            if (Math.abs(touchX - note.x) < 100 && note.y > SCREEN_WIDTH - 200) {
                iterator.remove();
                increaseScore();
                break;
            }
        }
    }

    private void increaseScore() {
        score += 100;
        updateScoreDisplay();
    }

    private void decreaseScore() {
        score -= 50;
        if (score < 0) score = 0;
        updateScoreDisplay();
    }

    private void updateScoreDisplay() {
        TextView scoreView = findViewById(R.id.score_text);
        scoreView.setText("Score: " + score);
    }
}