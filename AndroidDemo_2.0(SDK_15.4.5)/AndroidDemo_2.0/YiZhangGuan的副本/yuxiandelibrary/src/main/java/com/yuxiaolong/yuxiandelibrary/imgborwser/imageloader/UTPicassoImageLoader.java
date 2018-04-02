package com.yuxiaolong.yuxiandelibrary.imgborwser.imageloader;

/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

//public class UTPicassoImageLoader extends UTImageLoader {
//
//
//    @Override
//    public void disPlayImage(Context context, ImageView imageView, String url, final ILoadingCallBack loadingCallBack) {
//        url = getPath(url);
//        Picasso.with(context)
//                .load(url)
//                .error(R.drawable.pre_error)
//                .into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        loadingCallBack.loadingComplete();
//                    }
//
//                    @Override
//                    public void onError() {
//                        loadingCallBack.loadingError();
//                    }
//                });
//    }
//
//    @Override
//    public Bitmap getCacheImage(String url) {
//        return null;
//    }
//}
