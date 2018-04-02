package com.yuxiaolong.yuxiande;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbRequestParams;
import com.ab.util.AbToastUtil;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.yuxiaolong.yuxiandelibrary.ActionSheetDialog;
import com.yuxiaolong.yuxiandelibrary.SwitchView;
import com.yuxiaolong.yuxiandelibrary.TakePhotoVideoView;
import com.yuxiaolong.yuxiandelibrary.YuXianDeUtile;
import com.yuxiaolong.yuxiandelibrary.pickerview.OptionsPickerView;
import com.yuxiaolong.yuxiandelibrary.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.album)
    Button album;
    @Bind(R.id.camera)
    Button camera;
    @Bind(R.id.select_btn)
    Button selectBtn;
    @Bind(R.id.v_switch_1)
    SwitchView vSwitch1;
    @Bind(R.id.time)
    Button time;
    @Bind(R.id.condition)
    Button condition;
    private ListView dataList;
    private List<AboutUsVo.ResultBean> dataLists;
    private AdapterTest as;

    ArrayList<Media> select;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataLists = new ArrayList<AboutUsVo.ResultBean>();
        dataList = (ListView) findViewById(R.id.mListView);
        as = new AdapterTest(dataLists);
        dataList.setAdapter(as);
        initData();
        as.notifyDataSetChanged();
        //改变滑动块的颜色
        vSwitch1.setColor(0xFF000000, 0x4FC3F7);
        vSwitch1.setOnStateChangedListener(new switchView());
        initOptionData();
        initOptionsPicker();

    }

    private class switchView implements SwitchView.OnStateChangedListener {


        @Override
        public void toggleToOn(SwitchView view) {
            //开
            vSwitch1.setOpened(true);
            AbToastUtil.showToast(MainActivity.this, "开");
        }

        @Override
        public void toggleToOff(SwitchView view) {
            //关
            vSwitch1.setOpened(false);
            AbToastUtil.showToast(MainActivity.this, "关");
        }
    }

    private void initData() {

        for (int i = 0; i < 5; i++) {
            AboutUsVo.ResultBean a = new AboutUsVo.ResultBean();
            a.setContent("剑圣");
            a.setId("11");
            a.setTitle("刀锋战士");
            dataLists.add(a);
        }
    }

    /**
     * 条件选择器初始化
     */
    private void initOptionsPicker() {
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).getPickerViewText()
                    + options2Items.get(options1).get(options2)
                    + options3Items.get(options1).get(options2).get(options3);
            AbToastUtil.showToast(MainActivity.this,tx);


            /*AbRequestParams params = new AbRequestParams();
            params.put("id",securityId);
            webApplicationService(Constant.PUBLIC_STATIONDEVICE,params);*/

            //btn_Options.setText(tx);
        }
    })
            .setSubmitText("确认")
            .setCancelText("取消")
            .setTitleBgColor(Color.WHITE)
            .setTitleColor(Color.parseColor("#2177d5"))
            .setTitleText("维修地点")
            .setSubmitColor(Color.parseColor("#999999"))
            .setCancelColor(Color.parseColor("#999999"))

            .setContentTextSize(18)//设置滚轮文字大小
            .setDividerColor(Color.parseColor("#e7e7e7"))
            .setSelectOptions(0, 0)//默认选中项
            .setOutSideCancelable(true)//点击外部dismiss default true
            .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setLabels("", "", "")
            //.setBackgroundId(0x66000000) //设置外部遮罩颜色
            .build();

        //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }
    private void initOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */



        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);
        options3Items.add(options2Items);
        options3Items.add(options2Items);
        options3Items.add(options2Items);

        /*--------数据源添加完毕---------*/
    }
    /**
     * 时间选择器初始化
     * @param title
     */
    private void initTimePicker( final String title) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year,month-1,day,selectedDate.get(Calendar.HOUR),selectedDate.get(Calendar.MINUTE));
        Calendar endDate = Calendar.getInstance();
        endDate.set(year+1,month+1,day,0,0);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                // Button btn = (Button) v;
                // btn.setText(getTime(date));
                AbToastUtil.showToast(MainActivity.this,YuXianDeUtile.getTime(date,"yyyy-MM-dd HH:mm"));

            }
        },null)
                .setSubmitText("确认")
                .setCancelText("取消")
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#2177d5"))
                .setTitleText(title)
                .setSubmitColor(Color.parseColor("#999999"))
                .setCancelColor(Color.parseColor("#999999"))
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setLabel("年", "月", "日", "时", "分", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#e7e7e7"))
                .setContentSize(18)
                .setDate(Calendar.getInstance())

                .setRangDate(selectedDate,endDate)
                .build();
    }
    @OnClick({R.id.album, R.id.camera, R.id.select_btn,R.id.condition,R.id.time})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.time://时间选择器
                initTimePicker("选择生日");
                pvTime.show();
                break;
            case R.id.condition://条件选择器
                pvOptions.show();
                break;
            case R.id.select_btn:
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("按钮1", ActionSheetDialog.SheetItemColor.Black,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //AbToastUtil.showToast(InteractiveMessage.this,"清空所有消息");
                                        //startVideoCall();
                                        AbToastUtil.showToast(MainActivity.this, "点击按钮1");

                                    }
                                })
                        .addSheetItem("按钮2", ActionSheetDialog.SheetItemColor.Black,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //AbToastUtil.showToast(InteractiveMessage.this,"清空所有消息");
                                        //startVoiceCall();
                                        AbToastUtil.showToast(MainActivity.this, "点击按钮2");
                                    }
                                })
                        .show();
                break;
            case R.id.album://
                //仿微信选相册功能PickerConfig.PICKER_IMAGE_VIDEO：选视频和图片
                //PickerConfig.PICKER_IMAGE:选照片
                //PickerConfig.PICKER_VIDEO:选视频
                select = new ArrayList<>();
                intent = new Intent(this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);//默认图像和视频(Optional)
                long maxSize = 188743680L;//long long long
                intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //默认大小 180MB (Optional)
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 15);  //默认40张 (Optional)
                intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
                startActivityForResult(intent, 321);
                break;
            case R.id.camera://相机
                intent = new Intent(this, TakePhotoVideoView.class);
                startActivityForResult(intent, 111);
                break;
        }
    }
}
