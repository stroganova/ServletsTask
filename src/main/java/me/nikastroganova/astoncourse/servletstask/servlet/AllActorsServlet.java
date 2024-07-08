package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.ActorDtoWithPerformances;
import me.nikastroganova.astoncourse.servletstask.service.ActorService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/allActors")
public class AllActorsServlet extends HttpServlet {

    private static final ActorService actorService = ActorService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ActorDtoWithPerformances> actors = actorService.findAll();
        String json = gson.toJson(actors);
        ServletUtil.printJson(resp, json);
    }
}
