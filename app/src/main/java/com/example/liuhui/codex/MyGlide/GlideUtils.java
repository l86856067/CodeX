package com.example.liuhui.codex.MyGlide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liuhui.codex.R;

/**
 * Created by liuhui on 2018/9/12.
 */

public class GlideUtils {

    public static void loadImage(Context context,String url,ImageView imageView){

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.error_small)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

    }

    public static void loadImageNoCache(Context context,String url,ImageView imageView){

        Glide.with(context)
                .load(url)
                .thumbnail(Glide.with(context).load(R.drawable.img_loading))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

    }

}
