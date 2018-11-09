package com.yum.two_yum.view.merchants.merchantsfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yum.two_yum.R;
import com.yum.two_yum.view.merchants.MerchantsActivity;
import com.yum.two_yum.view.merchants.statistics.OrderHistoryActivity;
import com.yum.two_yum.view.merchants.statistics.TransactionHistoryActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 余先德
 * @data 2018/4/7
 */

public class StatisticsFragment extends Fragment {

    private MerchantsActivity abActivity;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        abActivity = (MerchantsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.order_history_btn, R.id.transaction_history_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.order_history_btn://订单历史
                intent = new Intent(getContext(), OrderHistoryActivity.class);
                abActivity.startActivity(intent);
                break;
            case R.id.transaction_history_btn://交易记录
                intent = new Intent(getContext(), TransactionHistoryActivity.class);
                abActivity.startActivity(intent);
                break;
        }
    }
}
