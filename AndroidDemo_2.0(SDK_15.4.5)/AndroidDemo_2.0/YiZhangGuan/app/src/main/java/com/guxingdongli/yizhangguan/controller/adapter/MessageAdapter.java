package com.guxingdongli.yizhangguan.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.guxingdongli.yizhangguan.R;
import com.guxingdongli.yizhangguan.model.MessageBase;
import com.guxingdongli.yizhangguan.view.message.DelDialog;
import com.guxingdongli.yizhangguan.view.message.MessageDetailsActivity;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.BaseCommAdapter;
import com.yuxiaolong.yuxiandelibrary.universalAdapter.ViewHolder;

import java.util.List;

/**
 * Created by jackmask on 2018/3/2.
 */

public class MessageAdapter  extends BaseCommAdapter<MessageBase.MessageDataBase.dataBase> {
    public MessageAdapter(List<MessageBase.MessageDataBase.dataBase> datas) {
        super(datas);
    }

    @Override
    protected void setUI(ViewHolder holder, int position, final Context context) {
        final MessageBase.MessageDataBase.dataBase data = getItem(position);
        final TextView title_name = holder.getItemView(R.id.title_name);
        TextView title_time = holder.getItemView(R.id.title_time);
        TextView content_tv = holder.getItemView(R.id.content_tv);
        TextView del_btn = holder.getItemView(R.id.del_btn);
        ImageView type_img = holder.getItemView(R.id.type_img);
        LinearLayout item_layout = holder.getItemView(R.id.item_layout);
        item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SwipeMenuLayout和item的点击事件冲突，所以只能以这样才实现item的点击
                Intent intent = new Intent(context, MessageDetailsActivity.class);
                intent.putExtra("titleStr",data.getMessageType()+"/"+"的标题");
                intent.putExtra("jumpType",data.getJumpType()+"");
                intent.putExtra("content",data.getMessageContent());
                intent.putExtra("msgUrl",data.getMsgUrl());
                context.startActivity(intent);
            }
        });
        final SwipeMenuLayout chat_item = holder.getItemView(R.id.chat_item);
        if (data.getMessageType().equals("推送消息")){
            title_name.setText(data.getMessageType());
            type_img.setImageResource(R.mipmap.push);
        }else if (data.getMessageType().equals("邮件消息")||data.getMessageType().equals("普通消息")){
            title_name.setText(data.getMessageType());
            type_img.setImageResource(R.mipmap.msg);
        }else{
            title_name.setText(data.getMessageType());
            type_img.setImageResource(R.mipmap.notice);
        }
        title_time.setText(data.getSendTime().substring(0,data.getSendTime().length()-3));
        content_tv.setText(data.getMessageContent());
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DelDialog.class);
                context.startActivity(intent);
                chat_item.quickClose();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_message;
    }
}
