package com.example.android_studio_pr;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class ScheduleFragment extends Fragment {
    private CalendarView calendarView;
    private TextView memoTextView;
    private Button viewMemoButton;
    private Button editMemoButton;
    private Map<String, String> memoMap = new HashMap<>();
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        memoTextView = view.findViewById(R.id.memoTextView);
        viewMemoButton = view.findViewById(R.id.viewMemoButton);
        editMemoButton = view.findViewById(R.id.editMemoButton);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일 메모 내용 ★";
            }
        });

        viewMemoButton.setOnClickListener(v -> {
            if (selectedDate != null) {
                updateMemoTextView(selectedDate);
            } else {
                Toast.makeText(getActivity(), "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });

        editMemoButton.setOnClickListener(v -> {
            if (selectedDate != null) {
                String memo = memoMap.get(selectedDate);
                if (memo != null) {
                    showEditMemoDialog(selectedDate, memo);
                } else {
                    showAddMemoDialog(selectedDate);
                }
            } else {
                Toast.makeText(getActivity(), "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showAddMemoDialog(final String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("메모 추가");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("추가", (dialog, which) -> {
            String memoContent = input.getText().toString();
            memoMap.put(selectedDate, memoContent);
            updateMemoTextView(selectedDate);
            Toast.makeText(getActivity(), "일정 추가", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showEditMemoDialog(final String selectedDate, String memo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("메모 수정");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(memo);
        builder.setView(input);
        builder.setPositiveButton("저장", (dialog, which) -> {
            String memoContent = input.getText().toString();
            memoMap.put(selectedDate, memoContent);
            updateMemoTextView(selectedDate);
            Toast.makeText(getActivity(), "메모 변경", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void updateMemoTextView(String selectedDate) {
        String memo = memoMap.get(selectedDate);
        if (memo != null) {
            memoTextView.setText(selectedDate + "\n\n " + memo);
        } else {
            memoTextView.setText("메모가 여기에 표시됩니다.");
        }
    }
}