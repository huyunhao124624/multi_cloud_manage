package com.hyh.netdev.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TerraformUtil {

    public static JSONObject parseNormalTfOutputToJSON(InputStream inputStream) throws IOException {

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
        String jsonResStr = inputReader.readLine();
        JSONObject jsonObject = JSON.parseObject(jsonResStr);
        inputStream.close();
        return jsonObject;

    }

    public static JSONObject terraformShow(File aimPath) throws IOException {
        BufferedReader inputReader = null;
        BufferedReader errorReader = null;
        String[] cmdList = { "powershell", "-c", "echo yes | terraform show -json" };
        Map<String, List<String>> listMap = new HashMap<>();
        BufferedReader inputR = null;
//        BufferedReader errorReader = null;
        List<String> returnString = new ArrayList<>();
        List<String> errorString = new ArrayList<>();
        listMap.put("normal", returnString);
        listMap.put("error", errorString);
        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.command(cmdList);
        processBuilder.directory(aimPath);

        Process process = processBuilder.start();
        JSONObject jsonObject = parseNormalTfOutputToJSON(process.getInputStream());
        return jsonObject;
    }

    public static boolean terraformApply(File aimPath) throws IOException {
        // String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
        String[] cmdList = { "powershell", "-c", "echo yes | terraform apply" };
        // List<String> resultList = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.command(cmdList);
        processBuilder.directory(aimPath);
        // processBuilder.command();
        Process p = processBuilder.start();
        int num = 0;
        try {
            num = p.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(num);
        return false;

    }

    public static boolean terraformInit(File aimPath) throws IOException {
        // String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
        String[] cmdList = { "powershell", "-c", "echo yes | terraform init" };
        // List<String> resultList = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.directory(aimPath);
        processBuilder.command(cmdList);
        // processBuilder.command();
        Process p = processBuilder.start();
        int num = 0;
        try {
            num = p.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(num);
        if (num == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean terraformDestroy(File aimPath) throws IOException {
        String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.command(cmdList);
        processBuilder.directory(aimPath);
        Process p = processBuilder.start();
        int num = 0;
        try {
            num = p.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(num);
        if(num == 0){
            return true;
        }else{
            return false;
        }

    }

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }



}
