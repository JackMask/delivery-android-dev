package com.yuxiaolong.yuxiandelibrary.imgborwser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuxiaolong.yuxiandelibrary.R;
import com.yuxiaolong.yuxiandelibrary.imgborwser.interfaces.ILoadingCallBack;

import java.io.FileOutputStream;


/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTGlideImageLoader extends UTImageLoader {


    @Override
    public void disPlayImage(Context context, final ImageView imageView, String url, final ILoadingCallBack loadingCallBack) {
        url = getPath(url);
        Picasso.get().load(url).placeholder(R.drawable.ease_default_avatar).into(new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                loadingCallBack.loadingComplete();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                loadingCallBack.loadingError();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        /*Glide.with(context)
                .load(url)
                .thumbnail(Glide.with(context).load(R.drawable.ease_default_avatar))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loadingCallBack.loadingError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loadingCallBack.loadingComplete();
                        return false;
                    }
                })
                .into(imageView)
        ;*/
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
