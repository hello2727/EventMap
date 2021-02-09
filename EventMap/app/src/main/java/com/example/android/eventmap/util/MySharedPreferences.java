package com.example.android.eventmap.util;

/*
* 설정 값 유지하기
* */

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    final String KIND = "map_setting";
    SharedPreferences spf;
    SharedPreferences.Editor editor;

    public MySharedPreferences(Context context){
        spf = context.getSharedPreferences(KIND, Context.MODE_PRIVATE);
        editor = spf.edit();
    }

    public void setBasic(Boolean b){
        editor.putBoolean("basic", b);
        editor.commit();
    }
    public void setSatellite(Boolean b){
        editor.putBoolean("satellite", b);
        editor.commit();
    }
    public void setTerrain(Boolean b){
        editor.putBoolean("terrain", b);
        editor.commit();
    }
    public void setNavi(Boolean b){
        editor.putBoolean("navi", b);
        editor.commit();
    }

    public void setIndoor(Boolean b){
        editor.putBoolean("indoor", b);
        editor.commit();
    }

    public void setTraffic(Boolean b){
        editor.putBoolean("traffic", b);
        editor.commit();
    }
    public void setTransit(Boolean b){
        editor.putBoolean("transit", b);
        editor.commit();
    }
    public void setBicycle(Boolean b){
        editor.putBoolean("bicycle", b);
        editor.commit();
    }
    public void setMountain(Boolean b){
        editor.putBoolean("mountain", b);
        editor.commit();
    }
    public void setCadastral(Boolean b){
        editor.putBoolean("cadastral", b);
        editor.commit();
    }

    public boolean getBasic(){
        return spf.getBoolean("basic", false);
    }
    public boolean getSatellite(){
        return spf.getBoolean("satellite", false);
    }
    public boolean getTerrain(){
        return spf.getBoolean("terrain", false);
    }
    public boolean getNavi(){
        return spf.getBoolean("navi", false);
    }

    public boolean getIndoor(){
        return spf.getBoolean("indoor", false);
    }

    public boolean getTraffic(){
        return spf.getBoolean("traffic", false);
    }
    public boolean getTransit(){
        return spf.getBoolean("transit", false);
    }
    public boolean getBicycle(){
        return spf.getBoolean("bicycle", false);
    }
    public boolean getMountain(){
        return spf.getBoolean("mountain", false);
    }
    public boolean getCadastral(){
        return spf.getBoolean("cadastral", false);
    }
}
