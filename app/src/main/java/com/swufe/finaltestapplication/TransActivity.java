package com.swufe.finaltestapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class TransActivity extends AppCompatActivity implements Runnable{

    EditText input;
    TextView show;
    String origin,result;
    String TAG="Trans";
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
        Thread t=new Thread(this);
        t.start();
    }
}


