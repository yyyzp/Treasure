package com.sohu.auto.treasure.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CommonUtils {
    public final static String SDF_YYYY_MM_DD_HH_MM = "yyyy.MM.dd HH:mm";
    public final static String SDF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 将时间戳转换成 f 格式
     *
     * @param time
     * @param f
     * @return
     */
    public static String formatDate(long time, String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 压缩图片到小于某个设定值
     *
     * @param image 源文件
     * @param size  要压缩到多少byte
     * @return
     */
    public static byte[] compressImage(Bitmap image, int size) {
        if (image == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.size() > size) { // 循环判断如果压缩后图片是否大于目标值, 大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();   // 关闭流, 防止内存泄漏
                }
            } catch (Exception e) {

            }
        }

        return null;
    }

    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }

    /**
     * 将byte[]转换成文件
     *
     * @param bytes 源文件bytes
     * @param file 目标文件
     */
    public static void bytes2File(byte[] bytes, File file) {
        if (bytes == null || file == null) {
            return;
        }

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 最普通的加载图片
     * */
    public static void loadImage(Context context, String url, ImageView imageView) {

        if (TextUtils.isEmpty(url))
            return;

        Glide
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(imageView);
    }
}
