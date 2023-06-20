package com.example.baewhaproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BarChart barChart;
    private List<BarEntry> entries;
    private ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모달창을 생성하는 코드 실행
                showSettingsModal();
            }
        });

        // 데이터 초기화
        entries = new ArrayList<>();
        entries.add(new BarEntry(0, 125212f));
        entries.add(new BarEntry(1, 24324f));
        entries.add(new BarEntry(2, 533000f));
        entries.add(new BarEntry(3, 30200f));
        entries.add(new BarEntry(4, 52300f));
        entries.add(new BarEntry(5, 133000f));
        entries.add(new BarEntry(6, 912000f));
        entries.add(new BarEntry(7, 15020f));
        entries.add(new BarEntry(8, 103300f));
        entries.add(new BarEntry(9, 105300f));
        entries.add(new BarEntry(10, 102300f));
        entries.add(new BarEntry(11, 310600f));


        barChart = findViewById(R.id.barChart);
        setupBarChart();
        populateBarChart();
    }

    private void showSettingsModal() {
        // 커스텀 다이얼로그를 생성
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼로그 타이틀 제거
        dialog.setContentView(R.layout.layout_dialog); // 사용자 정의된 레이아웃 파일을 설정

        // 모달창 내의 버튼들 설정
        Button closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모달창 닫기
                dialog.dismiss();
            }
        });

        // 모달창을 화면에 보여줌
        dialog.show();
    }


    private void setupBarChart() {
        // BarChart 설정
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
    }

    private void populateBarChart() {
        // 막대 색상 배열 생성
        int[] colors = new int[]{
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(R.color.yellow),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.teal_200),
                getResources().getColor(R.color.material_dynamic_primary70),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.purple_200),
                getResources().getColor(R.color.purple_500),
                getResources().getColor(R.color.purple_700),
                getResources().getColor(android.R.color.holo_purple)
        };

        // BarChart 데이터 생성
        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(colors); // 막대 색상 설정
        dataSet.setValueTextSize(12f);
        dataSet.setDrawValues(true);
        dataSet.setValueFormatter(new MyValueFormatter()); // 값 포맷터 설정

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.7f); // 막대 너비 조정
        dataSet.setDrawValues(false); // 막대 위의 숫자 표시 설정

        barChart.setData(data);
        barChart.animateY(1000);

        // Y축 설정
        barChart.getAxisLeft().setGranularity(200000f); // 단위 설정
        barChart.getAxisLeft().setAxisMinimum(0f); // 최소값 설정
        barChart.getAxisLeft().setAxisMaximum(1000000f); // 최대값 설정12
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);


        // X축 설정
        barChart.getXAxis().setDrawGridLines(false); // 가로축 선 제거
        barChart.getXAxis().setDrawAxisLine(false); // 가로축 맨 왼쪽 선 제거
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // X축 아래쪽에 배치
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisLabels())); // X축 라벨 설정
        barChart.getXAxis().setLabelCount(entries.size()); // X축 라벨 개수 설정

        // Legend 설정
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        // 모든 값의 총합 계산
        float total = 0f;
        for (BarEntry entry : entries) {
            total += entry.getY();
        }

        // BarChart 데이터를 가져와서 숫자의 평균값을 계산
        float sum = 0;
        for (BarEntry entry : entries) {
            sum += entry.getY();
        }
        float average = sum / entries.size();

        // TOTAL 텍스트 업데이트
        TextView totalTextView = findViewById(R.id.totalTextView);
        totalTextView.setText("TOTAL : " + (int) total);

        // averageTextView에 평균값 표시
        TextView averageTextView = findViewById(R.id.averageTextView);
        averageTextView.setText("AVERAGE : " + (int) average);

    }

    private class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.valueOf((int) value);
        }
    }

    private List<String> getXAxisLabels() {
        List<String> labels = new ArrayList<>();
        labels.add("JAN");
        labels.add("FEB");
        labels.add("MAR");
        labels.add("APR");
        labels.add("MAY");
        labels.add("JUN");
        labels.add("JUL");
        labels.add("AUG");
        labels.add("SEP");
        labels.add("OCT");
        labels.add("NOV");
        labels.add("DEC");
        return labels;
    }

}