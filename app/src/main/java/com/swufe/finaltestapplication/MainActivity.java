package com.swufe.finaltestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPage(View btn){
        Intent intent;
        if(btn.getId()==R.id.main_btn_openTran){
            intent = new Intent(MainActivity.this, TransActivity.class);
        }else if(btn.getId()==R.id.main_btn_openImf){
            intent = new Intent(MainActivity.this, InforActivity.class);
        }else{
            intent = new Intent(MainActivity.this, ScActivity.class);
        }
        startActivity(intent);
    }
}
