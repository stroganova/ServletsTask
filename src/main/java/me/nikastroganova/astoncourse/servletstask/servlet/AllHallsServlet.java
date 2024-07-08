package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.HallDto;
import me.nikastroganova.astoncourse.servletstask.service.HallService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.List;


@WebServlet("/allHalls")
public class AllHallsServlet extends HttpServlet {

    private static final HallService hallService = HallService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<HallDto> halls = hallService.findAll();
        String json = gson.toJson(halls);
        ServletUtil.printJson(resp, json);
    }
}
