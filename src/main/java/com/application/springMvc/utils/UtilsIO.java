package com.application.springMvc.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * For usability
 * @author Ihor Savchenko
 * @version 1.0
 */
public class UtilsIO {

    public static byte[] getByteArray(InputStream inputStream) throws IOException {

        int number = 0;
        byte[] arr = new byte[15000000];

        while(number != -1){
            number = inputStream.read(arr);
        }
        inputStream.close();
        return arr;
    }
}
