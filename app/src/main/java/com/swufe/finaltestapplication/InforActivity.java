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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InforActivity extends AppCompatActivity implements Runnable{

    String keyword;
    String TAG="infor";
    int flag=0;
    Handler handler;
    private List<Result> resultsList = new ArrayList<Result>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==1){
                    Bundle bdn= (Bundle) msg.obj;
                    ArrayList<String> titlestr=bdn.getStringArrayList("titles");
                    ArrayList<String> hrefstr =bdn.getStringArrayList("hrefs");

                    resultsList.clear();
                    for(int i=0;i<titlestr.size();i++){
                        resultsList.add(new Result(titlestr.get(i),hrefstr.get(i)));
                    }

                    for(Result res:resultsList){
                        Log.i(TAG, res.toString());
                    }
                    ResultAdapter adapter = new ResultAdapter(InforActivity.this, R.layout.result_item, resultsList);
                    ListView listView = findViewById(R.id.resultlist);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        Log.i(TAG, "hello from run() ");

        Document doc=null;
        Bundle bdn=new Bundle();
        ArrayList<String> titlestr =new ArrayList<>();
        ArrayList<String> hrefstr =new ArrayList<>();
        int count=0;

        if(keyword=="CET"){
            for(int i=1;i<3;i++){
                try {
                    doc = Jsoup.connect("http://cet.neea.edu.cn/html1/category/16093/1124-"+i+".htm").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Log.i("doc", "文档标题："+doc.title());
                Elements lists = doc.getElementsByTag("ul");
                Elements reports=lists.select("#ReportIDname");

                for(Element report:reports){
                    //Log.i(TAG,"第"+count+"条："+"title:"+report.text()+" href:"+report.select("a").attr("abs:href"));
                    //count++;
                    titlestr.add(report.text());
                    hrefstr.add(report.select("a").attr("abs:href"));
                }
            }
            Log.i(TAG, "数据已添加入列表");

            Log.i(TAG, "title: "+titlestr.toString());
            Log.i(TAG, "href:"+hrefstr.toString());
        }else if(keyword=="IELTS"){
            for(int i=0;i<2;i++){
                try {
                    doc = Jsoup.connect("https://www.chinaielts.org/whats_new/ielts_news.shtml?page="+i).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("doc", "文档标题："+doc.title());
               // Log.i("doc", "文档内容 "+doc.toString());
                Elements reports = doc.getElementsByTag("li").select(".title");
                for(Element report:reports){
                    titlestr.add(report.text());
                    hrefstr.add(report.select("a").attr("abs:href"));
                }
            }
            Log.i(TAG, "数据已添加入列表");

            Log.i(TAG, "title: "+titlestr.toString());
            Log.i(TAG, "href:"+hrefstr.toString());
        }else{
            Log.i(TAG, "hello form TOEFL");
            try {
                String URL="https://toefl.neea.cn/allNewsList";
                doc = Jsoup.connect(URL).get();
                Log.i(TAG,URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("doc", "文档内容："+doc.text());
            Element body = doc.getElementsByTag("body").get(0);
            //Log.i(TAG, "lis:"+lis.size());
            Log.i(TAG, "lis:"+body.toString());
        }

        int size=titlestr.size();
        for(int i=0;i<size;i++){
            resultsList.add(new Result(titlestr.get(i),hrefstr.get(i)));
        }

        for(Result res:resultsList){
            Log.i(TAG, res.toString());
        }
        bdn.putStringArrayList("titles",titlestr);
        bdn.putStringArrayList("hrefs",hrefstr);
        Message msg = handler.obtainMessage(1);
        msg.obj= bdn;
        handler.sendMessage(msg);
        flag=1;

    }

    public void onSearch(View btn){
        if(btn.getId()==R.id.imf_btn_CET){
            keyword="CET";
        }else if(btn.getId()==R.id.imf_btn_IELTS){
            keyword="IELTS";
        }else{
            keyword="TOEFL";
        }
        Thread t=new Thread(this);
        t.start();
    }
}

class Result{
    String title;
    String href;
    Result(String title,String href){
        this.title=title;
        this.href=href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String toString(){
        return "title："+this.title+"href："+this.href;
    }
}

class ResultAdapter extends ArrayAdapter {
    private final int resourceId;
    public ResultAdapter(Context context, int textViewResourceId, List<Result> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result= (Result) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView title = (TextView) view.findViewById(R.id.itemtitle);//获取该布局内的文本视图
        TextView href= (TextView) view.findViewById(R.id.itemhref);
        title.setText(result.getTitle());
        href.setText(result.getHref());
        return view;
    }
}

