package com.example.lab3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private List<Bitmap> mThumbIds; //Битмапы картинок
    private Context mContext; //Контекст
    private int imageWidth, imageHeight; //Размеры картинки

    public ImageAdapter(List<Bitmap> mThumbIds, Context mContext) {
        this.mThumbIds = mThumbIds;
        this.mContext = mContext;
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size); //Получаем размеры экрана в пикселях
        this.imageWidth = size.x;
        this.imageHeight = 400;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
    } //Получаем количество обьектов

    @Override
    public Object getItem(int position) { //Получаем элемент на позиции
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    } //Получаем id

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //При рендеринге вызывается для каждого элемента
        ImageView imageView = (ImageView) convertView; //Получаем ImageView


        if(imageView == null){
            imageView = new ImageView(mContext);
            //new GridView.LayoutParams(, 450)

            imageView.setLayoutParams(new GridView.LayoutParams(this.imageWidth, this.imageHeight)); //Задаем ширину и высоту блока в ListView
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); //Центрока картинки в блоке
        }

        imageView.setImageBitmap(mThumbIds.get(position)); //Установка битмапа картинки

        return imageView;
    }
}
