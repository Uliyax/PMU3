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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Gallery extends Fragment
{

    private boolean inEditMode = false; //Флаг, указывающий на нахождение в режиме редактирования
    private ArrayList<Bitmap> mImageBitmaps = new ArrayList <Bitmap>(); //ArrayList с битмапами картинок

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //Вызывается при рендеринге фрагмента
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.scroll_fragment, container, false); //Получаем view
        final Button AddButton = (Button) view.findViewById(R.id.Add); //Кнопка открытия галереи и добавления
        final ListView listView = view.findViewById(R.id.listView); //Получаем листвью для отображения фото
        listView.setAdapter(new ImageAdapter(mImageBitmaps, getContext())); //Устанавливаем адаптер с фото

        AddButton.setOnClickListener(new View.OnClickListener() //Вешаем обработчик нажатия на кнопку добавления и открытия галереи
        {
            @Override
            public void onClick(View v)
            {
                if(!inEditMode) { //Если приложение находится в режиме просмотра, то нажатие данной кнопки откроет галерею и после переведет приложение в режим редактирования
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    String[] mimeTypes = {"image/jpeg", "image/png"};
                    intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                    startActivityForResult(intent, 0); //Выхываем системную галерею

                    inEditMode = true; //Устанавливаем режим редактирования

                }else{ //Если приложение находится в режиме добавления фотографии, то нажатие данной кнопки добавит фото и удалит фрагмент редактирования
                    FragmentManager fm = getActivity().getSupportFragmentManager(); //Получаем фрагмент редактирования
                    Fragment fragment = fm.findFragmentById(R.id.TopScreenFragment);

                    Bitmap image = ((topFragment) fragment).getImage(); //Получаем выбранное фото

                    if(image != null){ //Если битмап валидный, т.е. не была нажата кнопка закрытия режима
                        mImageBitmaps.add(0, image); //Добавляем новую картинку в список
                    }

                    fm.beginTransaction().remove(fragment).commit(); //Удаляем фрагмент редактирования
                    listView.setAdapter(new ImageAdapter(mImageBitmaps, getContext())); //устанавливаем в listView новый адаптер

                    AddButton.setText("Select Image"); //Устанавливаем новый текст на кнопку
                    inEditMode = false; //Выходим из режима редактирования
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) //Вызывается после закрытыия галереи
    {
        if (resultCode == Activity.RESULT_OK){ //Если фото выбрано
            try {
                final Button AddButton = (Button) getActivity().findViewById(R.id.Add); //Получаем кнопку добавления

                AddButton.setText("Add"); //Меняем на ней текст

                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream); //Получаем битмап выбранной картинки

                FragmentManager fm = getActivity().getSupportFragmentManager(); //Создаем фрагмент
                Fragment fragment = new topFragment(0,0, bitmap);
                FragmentTransaction ft = fm.beginTransaction();

                ft.add(R.id.TopScreenFragment, fragment); //Добавляем фрагмент в Activity
                ft.commit();

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }

        }else{ //Если галерея закрыта без выбора фото
            inEditMode = false; //Выходим из режима редактирования
        }
    }
}
