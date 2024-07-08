package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.service.PerformanceService;

import java.io.IOException;
import java.util.Set;

@WebServlet("/actorsOfPerformance")
public class ActorsPerformancesServlet extends HttpServlet {

    private final PerformanceService performanceService = PerformanceService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonActorPerformance = gson.fromJson(req.getReader(), JsonObject.class);
        performanceService.addActorToPerformance(
                    jsonActorPerformance.get("actorId").getAsInt(),
                    jsonActorPerformance.get("performanceId").getAsInt());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonActorPerformance = gson.fromJson(req.getReader(), JsonObject.class);
        performanceService.deleteActorFromPerformance(
                jsonActorPerformance.get("actorId").getAsInt(),
                jsonActorPerformance.get("performanceId").getAsInt());
    }
}
