package com.swufe.finaltestapplication;

public class WordItem {
    private String word;
    private String trans;

    public WordItem(String word,String trans){
        super();
        this.word=word;
        this.trans=trans;
    }

    public WordItem(){
        super();
        this.word="";
        this.trans="";
    }

    public String getWord(){
        return this.word;
    }

    public String getTrans(){
        return this.trans;
    }

    public void setWord(String newword){
        this.word=newword;
    }
    public void setTrans(String newtrans){
        this.trans=newtrans;
    }
}
