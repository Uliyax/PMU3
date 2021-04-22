package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) //Создание Activity
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.MainFragment, new LogIn()).commit(); //Устанавливаем в Activity фрагмент в входом
        }
    }
}
