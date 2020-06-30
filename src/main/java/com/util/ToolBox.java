package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ToolBox {

    public static Date readDate(Scanner sc, String format){
        try {
            return new SimpleDateFormat(format).parse(sc.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

