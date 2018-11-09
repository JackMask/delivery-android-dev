package com.yum.two_yum.view.client.business.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.BaseActivity;
import com.yum.two_yum.view.client.business.DishesDetailsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 余先德
 * @data 2018/4/10
 */

public class DishesDetailsFragment extends Fragment {
    @Bind(R.id.pice_iv)
    ImageView piceIv;
    private DishesDetailsActivity activity;
    private View view;

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (DishesDetailsActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_dishes_details, container, false);
        ButterKnife.bind(this, view);
        setView();
        return view;
    }

    private void setView() {
        String pic = getArguments().getString("PIC");
        Picasso.get().load(pic).placeholder(R.mipmap.default_image).into(piceIv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
