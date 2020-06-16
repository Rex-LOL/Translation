package com.swufe.finaltestapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class TransActivity extends AppCompatActivity implements Runnable{

    EditText input;
    TextView show;
    String origin,result;
    String TAG="Trans";
    int flag =1;
    private static final String APP_ID = "20200603000485026";
    private static final String SECURITY_KEY = "kW2rSH7fShaq3mleGptm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
    }

    @Override
    public void run() {
        input=findViewById(R.id.trans_input);
        show=findViewById(R.id.trans_output);
        origin=input.getText().toString();

        Log.i(TAG, "run:。。。 ");

        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String str=api.getTransResult(origin, "auto", "zh");
        Log.i(TAG, "str: "+str);

        try {
            JSONObject jso=new JSONObject(new JSONTokener(str));
            Log.i(TAG, "json对象创建成功");
            String re=jso.getString("trans_result");
            re=re.substring(1,re.length()-1);
            Log.i(TAG, "json"+re);
            JSONObject jso2=new JSONObject(new JSONTokener(re));
            String result=jso2.getString("dst");
            Log.i(TAG, "json"+result);
            show.setText(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onFanyi(View btn){
        flag=0;
        Thread t=new Thread(this);
        t.start();
    }

    public void onSave(View btn){
        input=findViewById(R.id.trans_input);
        show=findViewById(R.id.trans_output);
        origin=input.getText().toString();
        result=show.getText().toString();
        if(origin.length()>0&flag==0){
            DBManager dbManager = new DBManager(TransActivity.this);
            WordItem wordItem=new WordItem(origin,result);
            dbManager.add(wordItem);
            flag=1;
            Log.i(TAG, "已存入数据库，origin:"+origin+"result:"+result);
        }else if(origin.length()==0){
            Toast.makeText(TransActivity.this,"Please translate word firstly",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(TransActivity.this,"This word has been saved yet",Toast.LENGTH_SHORT).show();
        }

    }
}


