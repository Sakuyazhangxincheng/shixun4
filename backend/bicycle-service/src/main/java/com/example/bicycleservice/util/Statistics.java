package com.example.bicycleservice.util;

import java.io.*;
import java.util.ArrayList;

public class Statistics {
    //读csv文件
    public static ArrayList<String> readCsvByBufferedReader(String filePath) {
        File csv = new File(filePath);
        csv.setReadable(true);
        csv.setWritable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        ArrayList<String> records = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                records.add(line);
            }
            System.out.println("csv表格读取行数：" + records.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static double[][] recordsToArray(){
        ArrayList<String> records;
        records = readCsvByBufferedReader("JDT.csv");
        //System.out.println(records);
        System.out.println();

        //二维数组
        double[][] info = new double[101][7];
        for(int x=1; x<records.size() - 1; x++)
        {
            String[] lineRecords = records.get(x).split(",");
            //System.out.println(lineRecords[8]);

            info[x][0] = Double.parseDouble(lineRecords[0]);
            info[x][1] = Double.parseDouble(lineRecords[1]);
            info[x][2] = Double.parseDouble(lineRecords[2]) * 10;
            info[x][3] = Double.parseDouble(lineRecords[3]);
            info[x][4] = Double.parseDouble(lineRecords[4]);

            if(lineRecords[5].equals("y")) info[x][5] = 1;
            else if (lineRecords[5].equals("s")) info[x][5] = 2;
            else if (lineRecords[5].equals("x")) info[x][5] = 3;
            else if (lineRecords[5].equals("n")) info[x][5] = 4;

            info[x][6] = Double.parseDouble(lineRecords[6]);
        }

        return info;
    }

    static double[][] data = recordsToArray();

    public static double[][] getX(){
        double[][] x = new double[101][6];
        for(int j=0; j< x.length; j++)
            System.arraycopy(data[j], 0, x[j], 0, 6);
        return x;
    }

    public static double[] getY(){
        double[] y = new double[101];
        for(int j=0; j< y.length; j++)
            y[j] = data[j][6];
        return y;
    }
}
