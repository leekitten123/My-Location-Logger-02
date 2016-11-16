package com.example.hyojin.mylocationlogger02;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/** PieChart 클래스를 도와주는 클래스 **/
public class MyPieChart {
    PieChart pieChart ;

    /** 그래프에 표시할 항목에 대한 변수 **/
    int[] yData = new int[4] ;

    /** 그래프에 표현할 카테고리 이름 **/
    String[] categoryName = {"공부", "산책", "식사", "축구"} ;

    /** 클래스 선언 시 생성자 **/
    MyPieChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true) ;
        pieChart.setRotationAngle(0) ;
        pieChart.setRotationEnabled(true) ;


        this.pieChart = pieChart ;
    }

    /** 그래프 표시 함수 **/
    void addData() {
        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        for (int i = 0; i < yData.length; i++) {
            yVals1.add(new PieEntry(yData[i], categoryName[i])) ;
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Category") ;
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5) ;

        /** 색 추가 **/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c) ;
        dataSet.setColors(colors) ;

        PieData data = new PieData(dataSet) ;
        data.setValueFormatter(new PercentFormatter()) ;
        data.setValueTextSize(11f) ;
        data.setValueTextColor(Color.GRAY) ;

        pieChart.setData(data) ;
        pieChart.highlightValues(null) ;
        pieChart.setEntryLabelColor(Color.GRAY);
        pieChart.invalidate();
    }

    /** yData 가져오는 함수 **/
    void setyData(int[] yData) {
        for (int i = 0 ; i < this.yData.length ; i++) {
            this.yData[i] = yData[i] ;
        }
    }
}
