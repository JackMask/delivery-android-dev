package com.guxingdongli.yizhangguan.util;

import android.app.Activity;
import android.content.Context;

import com.guxingdongli.yizhangguan.R;
import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * @author 余先德
 * @data 2018/3/29
 */

public class PicassoImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        Picasso.get()
                .load("file://" + path)
                .resize(width, height)
                .placeholder(R.mipmap.gallery_pick_photo)
                .error(R.mipmap.ic_launcher)
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}