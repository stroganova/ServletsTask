package me.nikastroganova.astoncourse.servletstask.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.service.PerformanceService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/performances")
public class PerformanceServlet extends HttpServlet{

    private final PerformanceService performanceService = PerformanceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try(var printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список спектаклей:</h1>");
            printWriter.write("<ul>");
            performanceService.findAll().forEach(performanceDto ->
                printWriter.write("""
                        <li>
                        <a href="/actors?performanceId=%d">%s</a>
                        </li>
                        """.formatted(performanceDto.getId(), performanceDto.getName())));
            printWriter.write("</ul>");
        }
    }
}
