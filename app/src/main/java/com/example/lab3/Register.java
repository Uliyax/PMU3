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

public class Register extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //Вызывается при создании View
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_register, container, false); //Получаем view

        Button ExitButton = (Button) view.findViewById(R.id.BackBtn); //Получаем кнопку выхода
        final TextView AlertTitle = (TextView) view.findViewById(R.id.AlertTitle); //Получаем поле для вывода ошибок
        Button ConfirmButton = (Button) view.findViewById(R.id.ConfirmBtn); //Получаем кнопку регистрации

        ExitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                returnToPrevious();
            }
        });//Если нажата кнопка выхода

        ConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { //Если нажата кнопка регистрации
                EditText email = (EditText) getView().findViewById(R.id.LoginField); //Получаем поле ввода электронной почты
                EditText password = (EditText) getView().findViewById(R.id.PasswordField); //Получаем поле ввода пароля
                EditText passwordConfirm = (EditText) getView().findViewById(R.id.PaswordRepeat); //Получаем поле ввода повтора пароля
                EditText name = (EditText) getView().findViewById(R.id.NameField); //Получаем поле ввода имени

                //Валидация имени. Если оно не подходит под регулярное выражение (Английские и русские буквы, а так же символ -, в количестве от 3 до 16 символов)
                if(!name.getText().toString().matches("^[a-zA-Zа-яёА-ЯЁ-]{3,15}$"))
                {
                    AlertTitle.setText("Input correct name"); //Установка текста ошибки
                }
                //Валидация электронной почты. Если она не подходит по стандартные параметры, заданные регулярным выражением
                else if (!email.getText().toString().matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"))
                {
                    AlertTitle.setText("Input correct email"); //становка текста ошибки
                }
                //Валидация пародя.
                else
                {
                    //Если он не подходит под реугялрное выражение (Как минимум одна большая и маленькая английские буквы, цифра и спецсимвол (#?!@$ %^&*-), в количестве не менее 8)
                    if (!password.getText().toString().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$")) {
                        AlertTitle.setText("Input correct password"); //Установка текста ошибки
                    }
                    else
                    {
                        if (password.getText().toString().equals(passwordConfirm.getText().toString())) //Если пароли в поле ввода и поле подтверждения совпадают
                        {
                            LogIn.mLogin = email.getText().toString(); //Запомниить данные для входа
                            LogIn.mPassword = password.getText().toString();

                            returnToPrevious(); //Выйти на логин
                        }
                        else
                        {
                            AlertTitle.setText("Password mismatch!"); //Иначе ошибка несовпадения паролей
                        }
                    }
                }
            }

        });
        return view;
    }

    private void returnToPrevious(){ //Выход на логин
        Fragment fragment = null; //Создание фрагмента логина
        fragment = new LogIn();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainFragment, fragment); //Добавление в Activity
        fragmentTransaction.commit();
    }
}
