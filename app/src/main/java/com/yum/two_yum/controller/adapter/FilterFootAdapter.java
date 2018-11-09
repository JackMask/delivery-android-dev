package com.yum.two_yum.controller.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yum.two_yum.R;
import com.yum.two_yum.base.FilterBase;
import com.yum.two_yum.controller.adapter.callback.FilterFootCallBack;
import com.yum.two_yum.utile.arrogantlistview.util.StringMatcher;
import com.yum.two_yum.view.client.clientorder.FilterActivity;

import java.util.List;

/**
 * @author 余先德
 * @data 2018/4/9
 */

public class FilterFootAdapter extends ArrayAdapter<FilterBase> implements SectionIndexer {
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int resoureId;
    private List<FilterBase> objects;
    private Context context;
    private FilterFootCallBack callBack;
    private boolean showType = false;


    private boolean cotType;

    public boolean isCotType() {
        return cotType;
    }

    public void setCotType(boolean cotType) {
        this.cotType = cotType;
    }

    public boolean isShowType() {
        return showType;
    }

    public void setShowType(boolean showType) {
        this.showType = showType;
    }

    public void setCallBack(FilterFootCallBack callBack) {
        this.callBack = callBack;
    }

    public FilterFootAdapter(Context context, int resourceId, List<FilterBase> objects) {
        super(context, resourceId, objects);
        // TODO Auto-generated constructor stub
        this.objects=objects;
        this.context=context;

    }

    private  class ViewHolder
    {
        ImageView imageView;
        TextView title;
        ImageView flagIv;
        LinearLayout select_item;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public FilterBase getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater mInflater= LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_filter_foot, null);
            viewHolder.flagIv = (ImageView) convertView.findViewById(R.id.flag_iv);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_view);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.hook_red_iv);
            viewHolder.select_item = (LinearLayout) convertView.findViewById(R.id.select_item);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FilterBase person = objects.get(position);
        if (!TextUtils.isEmpty(person.getFlag())&&cotType){
            viewHolder.flagIv.setVisibility(View.VISIBLE);
            Picasso.get().load(person.getFlag()).into(viewHolder.flagIv);
        }else{
            viewHolder.flagIv.setVisibility(View.GONE);
        }
        if (showType){
            viewHolder.title.setText(person.getTitle()+"("+person.getCurrencySymbol()+")");
        }else{
            viewHolder.title.setText(person.getTitle());
        }
        if(person.getTitle().equals(context.getString(R.string.ALLCUISINES))){
            viewHolder.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else{
            viewHolder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        viewHolder.select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack!=null){
                    callBack.selectFoot(position,person.isType());
                }
            }
        });
        if (person.isType()){
            viewHolder.imageView.setImageResource(R.mipmap.hook_red);
        }else {
            viewHolder.imageView.setImageResource(0);
        }


        return convertView;
    }
    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(getItem(j).getTitle().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(getItem(j).getTitle().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }
}
