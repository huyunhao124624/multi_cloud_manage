package com.hyh.netdev.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyh.netdev.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 向客户端下发状态
 *
 * @author Albumen
 */
public class PageCodeUtil {

    public static void printCode(HttpServletResponse response, Result result) throws IOException {
        printCode(response, result, 401);
    }

    public static void printCode(HttpServletResponse response, Result result, Integer statusCode) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        writer.print(json);
        writer.close();
        response.flushBuffer();
    }
}
