package com.guxingdongli.yizhangguan.view.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.RemindBase;
import com.guxingdongli.yizhangguan.util.AppUtile;
import com.guxingdongli.yizhangguan.util.Constant;
import com.guxingdongli.yizhangguan.util.HttpUtile;
import com.guxingdongli.yizhangguan.util.HttpUtileCallBack;
import com.guxingdongli.yizhangguan.view.home.HomeActivity;
import com.guxingdongli.yizhangguan.view.remind.RemindListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by jackmask on 2018/3/1.
 */

public class RemindFragment extends Fragment {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.chart)
    PieChart chart;
    @Bind(R.id.chart_layout)
    RelativeLayout chartLayout;

    private HomeActivity abActivity;
    private View view;

    private String[] colorSet = new String[]{"#FFD700", "#FF0000", "#ADFF2F", "#A0522D", "#778899", "#6495ED",
            "#00FF7F", "#000000", "#800000", "#808080"};
    private List<String> dataList = new ArrayList<>();
    private List<Integer> dataPercentage = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (HomeActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_remind, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {
        returnBtn.setVisibility(View.INVISIBLE);
        titleText.setText("工作提醒");


        setPicChart(chart);
    }

    /**
     * 求总数的百分比
     *
     * @param allNum  总数
     * @param digital 求数
     * @return
     */
    private float getPercentage(float allNum, float digital) {
        if (allNum > digital) {
            System.out.println("Percentage = " + (digital / allNum) * 100);
            return (digital / allNum) * 100;
        } else {
            return 0;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setPicChart(final PieChart chart) {
        chart.setUsePercentValues(true);            //使用百分比显示
        chart.getDescription().setEnabled(false);    //设置pieChart图表的描述
        chart.setExtraOffsets(10, 10, 50, 10);
        chart.setBackgroundColor(Color.parseColor("#F5F5F5"));      //设置pieChart图表背景色
        chart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]
        chart.setRotationAngle(0);                   //设置pieChart图表起始角度
        chart.setRotationEnabled(false);              //设置pieChart图表是否可以手动旋转
        chart.setHighlightPerTapEnabled(true);       //设置piecahrt图表点击Item高亮是否可用
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

        // 设置 pieChart 图表Item文本属性
        chart.setDrawEntryLabels(false);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）

        // 设置 pieChart 内部圆环属性
        chart.setDrawHoleEnabled(false);              //是否显示PieChart内部圆环(true:下面属性才有意义)

        chart.setDrawCenterText(false);               //是否绘制PieChart内部中心文本（true：下面属性才有意义）

        // 获取pieCahrt图列
        Legend l = chart.getLegend();
        l.setEnabled(true);                    //是否启用图列（true：下面属性才有意义）
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT); //设置图例的形状
        l.setFormSize(10);                    //设置图例的大小
        l.setFormToTextSpace(10f);            //设置每个图例实体中标签和形状之间的间距
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);           //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setXEntrySpace(10f);                //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        l.setYEntrySpace(2f);                 //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        l.setYOffset(0f);                     //设置比例块Y轴偏移量
        l.setTextSize(10f);                   //设置图例标签文本的大小
        l.setTextColor(Color.parseColor("#ff9933"));//设置图例标签文本的颜色'
        RequestBody body = new  FormBody.Builder().build();
        HttpUtile httpUtile =new HttpUtile(getActivity(), Constant.DOMAIN_NAME + Constant.GETWORKTIP, body, new HttpUtileCallBack() {
            @Override
            public void getReturnStr(String returnStr) {
                RemindBase data = JSON.parseObject(returnStr,RemindBase.class);
                setData(chart,data.getData());
            }
            @Override
            public void getReturnStrFailure(String returnStr) {
            }
            @Override
            public void getErrorStr(String errorStr) {

            }
        },false);

    }

    /**
     * 模拟饼图数据
     *
     * @param chart
     */
    private void setData(PieChart chart,List<RemindBase.DataBase> data) {
        ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        int allNum = 0;
        for (RemindBase.DataBase dataBase:data){
            allNum+=dataBase.getQuantity();
        }
        for (RemindBase.DataBase dataBase:data){
            dataList.add(dataBase.getName()+"（"+dataBase.getQuantity()+"）");
            dataPercentage.add(Math.round(getPercentage(allNum, dataBase.getQuantity())));
        }




        //饼图实体 PieEntry
        for (int i = 0; i < data.size(); i++) {
            pieEntryList.add(new PieEntry(dataPercentage.get(i), dataList.get(i)));
            colors.add(Color.parseColor(AppUtile.getRandColorCode()));
        }
        /*PieEntry CashBalance = new PieEntry(70, "现金余额 1500");
        PieEntry ConsumptionBalance = new PieEntry(30, "消费余额 768");
        PieEntry ConsumptionBalance1 = new PieEntry(1, "消费余额 58");
        PieEntry ConsumptionBalance2 = new PieEntry(1, "消费余额 548");
        PieEntry ConsumptionBalance3 = new PieEntry(1, "消费余额 5348");
        pieEntryList.add(CashBalance);
        pieEntryList.add(ConsumptionBalance);
        pieEntryList.add(ConsumptionBalance1);
        pieEntryList.add(ConsumptionBalance3);
        pieEntryList.add(ConsumptionBalance2);*/
        //饼状图数据集 PieDataSet
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "资产总览");
        pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
        pieDataSet.setColor(Color.WHITE);
        pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
        pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
        //最终数据 PieData
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        pieData.setValueTextColor(Color.BLUE);  //设置所有DataSet内数据实体（百分比）的文本颜色
        pieData.setValueTextSize(22f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
        pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
        chart.setData(pieData);
        chart.highlightValues(null);
        chart.invalidate();                    //将图表重绘以显示设置的属性和数据
    }



    @OnClick({R.id.see_all,R.id.return_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.see_all:
            //查看所有
                Intent intent = new Intent(abActivity, RemindListActivity.class);
                startActivity(intent);
            /*chartLayout.setVisibility(View.GONE);
            listLayout.setVisibility(View.VISIBLE);
                returnBtn.setVisibility(View.VISIBLE);*/
                break;
            case R.id.return_btn:

                break;
        }
    }
}
