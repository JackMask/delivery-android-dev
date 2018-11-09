package com.yum.two_yum.view.my.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.base.BaseFragment;
import com.yum.two_yum.base.LoginBase;
import com.yum.two_yum.base.ReleaseMenuInfoBase;
import com.yum.two_yum.controller.Constant;
import com.yum.two_yum.controller.YumApplication;
import com.yum.two_yum.utile.AnimationUtil;
import com.yum.two_yum.utile.AppUtile;
import com.yum.two_yum.utile.CircleImageView;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshBase;
import com.yum.two_yum.utile.pulltorefresh.PullToRefreshScrollView;
import com.yum.two_yum.view.WelcomeActivity;
import com.yum.two_yum.view.guide.ReleaseActivity;
import com.yum.two_yum.view.login.LoginActivity;
import com.yum.two_yum.view.my.ModifyInformationActivity;
import com.yum.two_yum.view.my.SettingsActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class MyFragment extends BaseFragment {

    @Bind(R.id.hospital_head_img)
    CircleImageView hospitalHeadImg;
    @Bind(R.id.switching_tv)
    TextView switchingTv;
    @Bind(R.id.release_menu)
    LinearLayout releaseMenu;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.forgot_password)
    TextView forgotPassword;
    @Bind(R.id.no_log_in)
    LinearLayout noLogIn;
    @Bind(R.id.share)
    LinearLayout share;
    @Bind(R.id.switching)
    LinearLayout switching;
    @Bind(R.id.settings)
    LinearLayout settings;
    @Bind(R.id.feedback)
    LinearLayout feedback;
    @Bind(R.id.my_layout)
    PullToRefreshScrollView myLayout;


    private BaseActivity abActivity;
    private View view;
    private boolean menuType = false;
    private boolean issue = false;
    private boolean conType = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (BaseActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);

        initLayout();
        setBtndsInfo();
        return view;
    }

    public void setExamination() {
        if (TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getToken())) {
            noLogIn.setVisibility(View.VISIBLE);
            myLayout.setVisibility(View.GONE);
            loginBtn.setText(getString(R.string.LOGINCREATEACCOUNT));
            forgotPassword.setTextColor(getResources().getColor(R.color.color_484848));
            String protocol = getResources().getString(R.string.BYPROCEEDINGYOUAGREEWITHTHE);
            String termsofservice = getResources().getString(R.string.TERMSOFSERVICE_);
            String privacypolicy = getResources().getString(R.string.PRIVACYPOLICY);
            SpannableString spanTermsofservice = new SpannableString(termsofservice);
            SpannableString spanPrivacypolicy = new SpannableString(privacypolicy);
            ClickableSpan clickTermsofservice = new ShuoMClickableSpan(termsofservice, getActivity(), true);
            ClickableSpan clickPrivacypolicy = new ShuoMClickableSpan(privacypolicy, getActivity(), false);
            spanTermsofservice.setSpan(clickTermsofservice, 0, termsofservice.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spanPrivacypolicy.setSpan(clickPrivacypolicy, 0, privacypolicy.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (getLanguage().equals("zh-hk") || getLanguage().equals("zh-cn")) {
                forgotPassword.setText(protocol);
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(getResources().getString(R.string.AND));
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                forgotPassword.setText(protocol + " ");
                forgotPassword.append(spanTermsofservice);
                forgotPassword.append(" " + getResources().getString(R.string.AND) + " ");
                forgotPassword.append(spanPrivacypolicy);
                forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
            }
            forgotPassword.setHighlightColor(getResources().getColor(android.R.color.transparent));
        } else {
            noLogIn.setVisibility(View.GONE);
            myLayout.setVisibility(View.VISIBLE);
           //setHeadImg();
            //setBtnInfo();
        }
    }

    /**
     * 初始化数据
     */
    private void initLayout() {
        setHeadImg();
        myLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        myLayout.setGoneAll();
        myLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                setHeadImg();
            }
        });

    }

    public void setHeadImg() {
        getUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.hospital_head_img, R.id.share, R.id.switching, R.id.settings, R.id.feedback, R.id.release_menu, R.id.login_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.login_btn:
                intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra("type",true);
                startActivityForResult(intent, 0);
                getActivity().overridePendingTransition(R.anim.activity_open, R.anim.in);
                break;
            case R.id.hospital_head_img:
                intent = new Intent(getContext(), ModifyInformationActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            case R.id.share:

                Intent share_intent = new Intent();

                share_intent.setAction(Intent.ACTION_SEND);

                share_intent.setType("text/plain");

                share_intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.SHARENEW));

                share_intent.putExtra(Intent.EXTRA_TEXT, "2Yum Food Delivery \n http://www.2yum.app/yum/share");

                share_intent = Intent.createChooser(share_intent, getString(R.string.SHARENEW));

                startActivity(share_intent);
//                Intent textIntent = new Intent(Intent.ACTION_SEND);
//                textIntent.setType("text/plain");
//                textIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
//                startActivity(Intent.createChooser(textIntent, "分享"));

                break;
            case R.id.switching:
//                intent = new Intent(getContext(), ReleaseActivity.class);
//                startActivity(intent);
                if (issue){
                    intent = new Intent(getContext(), ReleaseActivity.class);
                    getActivity().startActivityForResult(intent,999);
                    getActivity().overridePendingTransition(R.anim.activity_open, R.anim.in);
                }else{
                    intent = new Intent(getContext(), WelcomeActivity.class);
                    if (YumApplication.getInstance().isInputType()) {
                        YumApplication.getInstance().setInputType(false);
                    } else {
                        YumApplication.getInstance().setInputType(true);
                    }
                    ComponentName cn = intent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);//ComponentInfo{包名+类名}
                    startActivity(mainIntent);
                }
//                if ((YumApplication.getInstance().getMyInformation().getMerchantType() == 2 || menuType) && !issue) {
//
//                } else {
//
//                }

                break;
            case R.id.settings:
                intent = new Intent(getContext(), SettingsActivity.class);
                getActivity().startActivityForResult(intent,999);
                break;
            case R.id.feedback:
                ((BaseActivity)getActivity()).showEmail();
//                if (noDataLayout.getVisibility() ==View.GONE&&selectLayout.getVisibility() == View.GONE) {
//                    noDataLayout.setVisibility(View.VISIBLE);
//                    selectLayout.setVisibility(View.VISIBLE);
//                    selectLayout.setAnimation(AnimationUtil.fromBottomToNow());
//                }else{
//                    noDataLayout.setVisibility(View.GONE);
//                    selectLayout.setVisibility(View.GONE);
//                    selectLayout.setAnimation(AnimationUtil.fromNowToBottom());
//                }
                break;
            case R.id.release_menu:
                intent = new Intent(getContext(), ReleaseActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open, R.anim.in);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){

        }
    }
    public void getUserInfo(){
        OkHttpUtils
                .get()
                .url(Constant.GET_USER_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        //System.out.println("e = "+e.getMessage());;
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                        if (myLayout!=null){
                            myLayout.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (myLayout!=null){
                            myLayout.onRefreshComplete();
                        }
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,(BaseActivity)getActivity())) {
                                LoginBase loginBase = JSON.parseObject(s, LoginBase.class);
                                if (!TextUtils.isEmpty(YumApplication.getInstance().getMyInformation().getAvatar())) {
                                    if (!YumApplication.getInstance().getMyInformation().getAvatar().equals(Constant.SHOW_IMG+loginBase.getData().getAvatar())) {
                                        YumApplication.getInstance().getMyInformation().setAvatar(Constant.SHOW_IMG+loginBase.getData().getAvatar());
                                    }
                                    Picasso.get().load(YumApplication.getInstance().getMyInformation().getAvatar()).placeholder(R.mipmap.head_img_being).into(hospitalHeadImg);
                                }else{
                                    YumApplication.getInstance().getMyInformation().setAvatar(Constant.SHOW_IMG+loginBase.getData().getAvatar());
                                    Picasso.get().load(YumApplication.getInstance().getMyInformation().getAvatar()).placeholder(R.mipmap.head_img_being).into(hospitalHeadImg);
                                }
                            }
                        }
                    }
                });
    }

    public void setBtndsInfo() {
        OkHttpUtils
                .get()
                .url(Constant.MENU_INFO)
                .addHeader("Accept-Language", AppUtile.getLanguage())
                .addHeader("YUM-AUTH-TOKEN", YumApplication.getInstance().getMyInformation().getToken())
                .addParams("userId", YumApplication.getInstance().getMyInformation().getUid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        //System.out.println("e = "+e.getMessage());;
                        if (AppUtile.getNetWork(e.getMessage())){
                            showMsg(getString(R.string.NETERROR));
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            if (AppUtile.setCode(s,(BaseActivity)getActivity())) {
                                ReleaseMenuInfoBase data = JSON.parseObject(s, ReleaseMenuInfoBase.class);
                                if (data.getData().getIssue() != 0||data.getData().getAuditState() == 2) {
                                    issue = true;
                                } else {
                                    issue = false;
                                }
                                setType();
//                                if (issue){
//                                    setType();
//                                }else{
//
//                                }
//                                conType = false;
//                                if (data.getData().getMerchantType() == 2) {//证件
//                                    conType = true;
//                                }
//
//                                if (conType) {
//                                    menuType = true;
//                                    setSwitchingTvText();
//                                } else {
//                                    menuType = false;
//                                    switchingTv.setText(getString(R.string.LISTYOURMENU));
//                                }

                            }
                        }
                    }
                });
    }
    public void setIssue(boolean issue){
        this.issue = issue;
    }
    public void setType(){
        if (!issue) {
            if (YumApplication.getInstance().isInputType()) {
                switchingTv.setText(getString(R.string.SWITCHTOBUSINESS));
            } else {
                switchingTv.setText(getString(R.string.SWITCHTOORDER));
            }
        }else{
            switchingTv.setText(getString(R.string.LISTYOURMENU));
        }
    }

    public void setSwitchingTvTexst() {
        if (conType) {
            if (YumApplication.getInstance().isInputType()) {
                switchingTv.setText(getString(R.string.SWITCHTOBUSINESS));
            } else {
                switchingTv.setText(getString(R.string.SWITCHTOORDER));
            }
        }else{
            YumApplication.getInstance().setInputType(false);
            switchingTv.setText(getString(R.string.LISTYOURMENU));
        }
    }


}
