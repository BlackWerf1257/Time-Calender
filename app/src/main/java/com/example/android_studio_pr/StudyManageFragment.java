package com.example.android_studio_pr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StudyManageFragment extends Fragment {
    EditText editTextTimer;
    Button buttonStart;
    Button buttonStop;
    Switch switchVibrate;
    Switch switchSound;
    CountDownTimer countDownTimer;
    boolean isTimerRunning = false;
    Ringtone ringtone;
    Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_manage, container, false);

        editTextTimer = view.findViewById(R.id.editTextTimer);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonStop = view.findViewById(R.id.buttonStop);
        switchVibrate = view.findViewById(R.id.switchVibrate);
        switchSound = view.findViewById(R.id.switchSound);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        if (!isTimerRunning) {
            String input = editTextTimer.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter a time", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] timeArray = input.split(":");
            if (timeArray.length != 2) {
                Toast.makeText(getActivity(), "Please enter time in the format of '분:초'", Toast.LENGTH_SHORT).show();
                return;
            }

            int minutes = Integer.parseInt(timeArray[0]);
            int seconds = Integer.parseInt(timeArray[1]);

            // 분과 초를 밀리초로 변환
            long millisInput = (minutes * 60 + seconds) * 1000;

            countDownTimer = new CountDownTimer(millisInput, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimerText(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    // Show alarm/notification when timer finishes
                    showAlarm();
                    isTimerRunning = false;
                }
            }.start();

            isTimerRunning = true;
            buttonStart.setVisibility(View.INVISIBLE);
            buttonStop.setVisibility(View.VISIBLE);
        }
    }

    private void stopTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel();
            if (ringtone != null && ringtone.isPlaying()) {
                ringtone.stop();
            }
            if (vibrator != null) {
                vibrator.cancel();
            }
            isTimerRunning = false;
            buttonStart.setVisibility(View.VISIBLE);
            buttonStop.setVisibility(View.INVISIBLE);
        }
    }

    private void updateTimerText(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / 1000) / 3600;
        int minutes = (int) ((millisUntilFinished / 1000) % 3600) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        }

        editTextTimer.setText(timeLeftFormatted);
    }

    private void showAlarm() {
        if (switchSound.isChecked()) {
            // 소리 재생
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(getContext(), notification);
            ringtone.play();
        }

        if (switchVibrate.isChecked()) {
            // 진동 재생
            vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(new long[]{1000, 1000, 1000}, 0); // 진동 1초 간격으로 3번 반복
            }
        }

        // 알림 창 표시
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("시간 경과")
                .setMessage("설정한 시간이 지났습니다")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 알림 창에서 "OK" 버튼을 눌렀을 때 실행되는 코드
                        if (ringtone != null && ringtone.isPlaying()) {
                            ringtone.stop();
                        }
                        if (vibrator != null) {
                            vibrator.cancel();
                        }
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                })
                .show();
    }
}