package com.example.lab3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LogIn extends Fragment
{
    public static String mLogin = "123"; //Данные для фхода текущего пользователя после регистрацииgit
    public static String mPassword = "123";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) //Вызывается при отрисовке фрагмента
    {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false); //Получаем View

        TextView SignUpButton = (TextView) view.findViewById(R.id.SignUpButton); //Переход на регистрацию
        final TextView AlertTitle = (TextView) view.findViewById(R.id.AlertTitle); //Поле информирования об ошибке
        Button EnterButton = (Button) view.findViewById(R.id.EnterButton); //Кнопка входа

        SignUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //Обработка нажатия на текст регистрации
                 //Создание и установка фрагмента м регистрацией
                Fragment fragment = new Register();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.MainFragment, fragment);
                fragmentTransaction.commit();
            }
        });

        EnterButton.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //бработка нажатия на кнопку входа
                EditText loginET = (EditText) getView().findViewById(R.id.LoginField); //Получаем поле ввода электронной почты
                EditText passwordET = (EditText) getView().findViewById(R.id.PasswordField); //Получаем поле ввода пароля

                if (loginET.getText().toString().equals("")) //Если электронная почта пустая
                    {
                    AlertTitle.setText("Input Email"); //Установка текста ошибки
                    passwordET.setText(""); //Очистка поля пароля
                }
                else
                {
                    if (passwordET.getText().toString().equals("")) //Если пустой пароль
                    {
                        AlertTitle.setText("Input Password"); //Установка текста ошибки
                        passwordET.setText(""); //Очистка поля пароля
                    }
                    else
                    {
                        if (loginET.getText().toString().equals(mLogin)) //Если совпадает email
                        {
                            if (passwordET.getText().toString().equals(mPassword)) //Если совпадает пароль
                            {
                                Fragment fragment = null; //Производим вход. Создаем фрагмент галереи и добавляем в Activity
                                fragment = new Gallery();
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.MainFragment, fragment);
                                ft.commit();
                            }
                            else //Если же пароли не совпадают
                            {
                                AlertTitle.setText("Wrong password"); //Установка текста ошибки
                                passwordET.setText("");
                            }
                        }
                        else //Если не совпадает email
                        {
                            AlertTitle.setText("No current account"); //Аккаунт не существует
                            passwordET.setText("");
                        }
                    }
                }
            }
        }));
        return view;
    }
}
