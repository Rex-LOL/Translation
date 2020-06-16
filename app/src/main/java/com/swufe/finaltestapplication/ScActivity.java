package com.swufe.finaltestapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ScActivity extends AppCompatActivity implements Runnable {

    ArrayList<WordItem> wordList = new ArrayList<>();
    Handler handler;
    static String TAG="voca";
    VocaAdapter adapter;
    DBManager dbManager = new DBManager(ScActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc);
        
        Thread t=new Thread(this);
        t.start();
        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==1){
                    adapter = new VocaAdapter(ScActivity.this, R.layout.vocabulary_item, wordList);
                    ListView listView = findViewById(R.id.sc_vocabulary_list);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        Log.i(TAG, "run: .......");

        for(WordItem wordItem : dbManager.listAll()){
            Log.i(TAG, "Word:"+wordItem.getWord()+"Trans:"+wordItem.getTrans());
            wordList.add(wordItem);
        }
        Message msg = handler.obtainMessage(1);
        handler.sendMessage(msg);
    }

    public void onDelete(View btn){
        dbManager.deleteAll();
        wordList.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(ScActivity.this,"Connection has been cleared",Toast.LENGTH_SHORT).show();

    }
}

class VocaAdapter extends ArrayAdapter {
    private final int resourceId;

    public VocaAdapter(Context context, int textViewResourceId, ArrayList<WordItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordItem wordItem = (WordItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView word = (TextView) view.findViewById(R.id.itemword);//获取该布局内的文本视图
        TextView trans = (TextView) view.findViewById(R.id.itemtrans);
        word.setText(wordItem.getWord());
        trans.setText(wordItem.getTrans());
        return view;
    }

}

