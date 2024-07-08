package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.PerformanceDtoWithActors;
import me.nikastroganova.astoncourse.servletstask.service.PerformanceService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.List;


@WebServlet("/allPerformances")
public class AllPerformancesServlet extends HttpServlet {

    private final PerformanceService performanceService = PerformanceService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PerformanceDtoWithActors> performances = performanceService.findAll();
        String json = gson.toJson(performances);
        ServletUtil.printJson(resp, json);
    }
}
