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

    private static String TERRAFORM = "terraform";
    private static String AUTO_APPROVE_OPTIONS = "-auto-approve";
    private static String APPLY = "apply";
    private static String DESTROY = "destroy";
    private static String INIT = "init";
    private static String SHOW = "show";
    private static String JSON_OPTIONS = "-json";

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
//        String[] cmdList = { "powershell", "-c", "echo yes | terraform show -json" };
        String[] cmdList = {TERRAFORM,SHOW,JSON_OPTIONS};
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

    public static void logFromProcessStream(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
//        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
        String line = null;

        while((line = inputReader.readLine())!=null ){
            log.info(line);
        }
//        while((line = errorReader.readLine())!=null){
//            log.info(line);
//        }

    }

    public static boolean terraformApply(File aimPath) throws IOException {
        // String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
//        String[] cmdList = { "powershell", "-c", "echo yes | terraform apply" };
        String[] cmdList = {TERRAFORM,APPLY,AUTO_APPROVE_OPTIONS};
        // List<String> resultList = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.directory(aimPath);
        processBuilder.command(cmdList);
        processBuilder.redirectErrorStream(true);

        // processBuilder.command();
        Process p = processBuilder.start();
        logFromProcessStream(p);
        int num = 0;
        try {
            num = p.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(num);
        if(num == 0){
            return true;
        }else{
            return false;
        }


    }

    public static boolean terraformInit(File aimPath) throws IOException {
        // String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
//        String[] cmdList = { "powershell", "-c", "echo yes | terraform init" };
        String[] cmdList = {TERRAFORM,INIT};
        // List<String> resultList = new ArrayList<>();

        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.directory(aimPath);
        processBuilder.command(cmdList);
        processBuilder.redirectErrorStream(true);
        // processBuilder.command();
        Process p = processBuilder.start();
        logFromProcessStream(p);
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
//        String[] cmdList = { "powershell", "-c", "echo yes | terraform destroy" };
        String[] cmdList = {TERRAFORM,DESTROY,AUTO_APPROVE_OPTIONS};
        ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.command(cmdList);
        processBuilder.directory(aimPath);
        Process p = processBuilder.start();
        logFromProcessStream(p);
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
