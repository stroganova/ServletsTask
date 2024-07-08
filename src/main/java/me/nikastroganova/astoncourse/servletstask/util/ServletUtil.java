package me.nikastroganova.astoncourse.servletstask.util;

import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.exception.JsonPrintingException;

import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtil {
    public static void printJson(HttpServletResponse response, String json) {
        try(PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
        } catch (IOException e) {
            throw new JsonPrintingException(e);
        }
    }
}
