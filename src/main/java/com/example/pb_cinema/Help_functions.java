package com.example.pb_cinema;

public class Help_functions {

    public static String StringBoxToTimeFormatConvert(int H, int M)
    {
        String SelectTime = "";
        if(H<10)
        {
            SelectTime+="0";
        }
        SelectTime += H + ":";
        if(M<10)
        {
            SelectTime+="0";
        }
        SelectTime += M + ":00";
        return SelectTime;
    }
    public static String TimeFormatRemover(String s) {
        return s.substring(0, 5);
    }
}
