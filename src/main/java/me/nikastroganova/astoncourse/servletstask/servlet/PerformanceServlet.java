package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.PerformanceDtoWithActors;
import me.nikastroganova.astoncourse.servletstask.dto.SimplePerformanceDto;
import me.nikastroganova.astoncourse.servletstask.service.PerformanceService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;


@WebServlet("/performance")
public class PerformanceServlet extends HttpServlet{

    private final PerformanceService performanceService = PerformanceService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        Optional<PerformanceDtoWithActors> performance = performanceService.findById(id);
        if (performance.isPresent()) {
            String json = gson.toJson(performance);
            ServletUtil.printJson(resp, json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonPerformance = gson.fromJson(req.getReader(), JsonObject.class);

        SimplePerformanceDto performance = SimplePerformanceDto.builder()
                .name(jsonPerformance.get("name").getAsString())
                .description(jsonPerformance.get("description").getAsString())
                .hallId(jsonPerformance.get("hallId").getAsInt())
                .build();

        performance = PerformanceService.save(performance);

        ServletUtil.printJson(resp, gson.toJson(performance));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonPerformance = gson.fromJson(req.getReader(), JsonObject.class);
        Set<String> jsonKeys = jsonPerformance.keySet();

        SimplePerformanceDto.SimplePerformanceDtoBuilder builder = SimplePerformanceDto.builder();

        if (jsonKeys.contains("id")) {
            builder.id(jsonPerformance.get("id").getAsInt());
        }
        if (jsonKeys.contains("name")) {
            builder.name(jsonPerformance.get("name").getAsString());
        }
        if (jsonKeys.contains("description")) {
            builder.description(jsonPerformance.get("description").getAsString());
        }
        if (jsonKeys.contains("hallId")) {
            builder.hallId(jsonPerformance.get("hallId").getAsInt());
        }

        SimplePerformanceDto performance = builder.build();

        PerformanceService.update(performance);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        performanceService.deleteById(id);
    }
}
