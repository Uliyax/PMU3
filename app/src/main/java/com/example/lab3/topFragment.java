package com.example.lab3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class topFragment extends Fragment
{

    private GridView gridView; //GridView в фрагменте
    private Bitmap currentImage; //Текущая картинка
    private View view; //view фрагмента

    public topFragment(int width, int height, Bitmap imageBitmap){
        //this.fragmentWidth = width;
        //this.fragmentHeight = height;
        this.currentImage = imageBitmap; //Устанавливаем первоначальную картинку
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) //Вызывается при создании view
    {
        view = inflater.inflate(R.layout.top_fragment, container, false); //получаем view
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.height = 400;
        params.setMargins(0,0,0,40);
        view.setLayoutParams(params); //устанавливаем размеры и отступы

        gridView = (GridView) view.findViewById(R.id.PreView); //получаем GridView
        Button CloseButton = (Button) view.findViewById(R.id.Close); //Получаем кнопку выхода из режима редактирования

        params = (FrameLayout.LayoutParams) CloseButton.getLayoutParams();
        params.setMargins(0,40,40,0);
        CloseButton.setLayoutParams(params); //Устанавливаем отступы для кнопки

        setImage(currentImage); //Устанавливаем картинку

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //Установка обработчика нажатия на картинку
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent, 0); //ызов галереи для смены картинки
            }
        });

        CloseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //Установка обработчика нажатия на кнопку выхода из режима редактирования
                currentImage = null; //Удаляем картинку
                getActivity().findViewById(R.id.Add).callOnClick(); //Эмулируем нажатие клавиши Add/Select на фрагменте Gallery (читай комментарии к обработчику той кнопки)
            }

        });

        return view;
    }

    public void setImage(Bitmap imageBitmap){ //Установка картинки
        currentImage = imageBitmap;

        ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>(); //Создание адаптера
        bitmap.add(0, currentImage);
        gridView.setAdapter(new ImageAdapter(bitmap, view.getContext())); //Установка адаптера
    }

    public Bitmap getImage(){
        return  currentImage;
    } //Получить картинку

    public void onActivityResult(int requestCode, int resultCode, Intent data) //После закрытия галереи
    {
        if (resultCode == Activity.RESULT_OK){ //Если выбрана картинка
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                setImage(bitmap); //Установка новой

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }

        }
    }
}
