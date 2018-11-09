package com.yum.two_yum.utile;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;
import com.yum.two_yum.R;

/**
 * @author 余先德
 * @data 2018/3/29
 */

public class PicassoImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        System.out.println("width = "+width);
        System.out.println("height = "+height);
        Picasso.get()
                .load("file://" + path)
                .resize(width, height)
                .centerCrop()
                .placeholder(R.mipmap.gallery_pick_photo)
                .error(R.mipmap.ic_launcher)
                .into(galleryImageView);
        galleryImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void clearMemoryCache() {

    }
}