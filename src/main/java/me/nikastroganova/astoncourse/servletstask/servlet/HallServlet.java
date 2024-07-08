package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.HallDto;
import me.nikastroganova.astoncourse.servletstask.service.HallService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;


@WebServlet("/hall")
public class HallServlet extends HttpServlet {

    private static final HallService hallService = HallService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        Optional<HallDto> hall = hallService.findById(id);
        if (hall.isPresent()) {
            String json = gson.toJson(hall);
            ServletUtil.printJson(resp, json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonHall = gson.fromJson(req.getReader(), JsonObject.class);
        Set<String> jsonKeys = jsonHall.keySet();

        HallDto hall = HallDto.builder()
                .name(jsonHall.get("name").getAsString())
                .address(jsonHall.get("address").getAsString())
                .phoneNumber(jsonHall.get("phoneNumber").getAsString())
                .build();

        hall = hallService.save(hall);

        ServletUtil.printJson(resp, gson.toJson(hall));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonHall = gson.fromJson(req.getReader(), JsonObject.class);
        Set<String> jsonKeys = jsonHall.keySet();
        HallDto.HallDtoBuilder builder = HallDto.builder();

        if (jsonKeys.contains("id")) {
            builder.id(jsonHall.get("id").getAsInt());
        }
        if (jsonKeys.contains("name")) {
            builder.name(jsonHall.get("name").getAsString());
        }
        if (jsonKeys.contains("address")) {
            builder.address(jsonHall.get("address").getAsString());
        }
        if (jsonKeys.contains("phoneNumber")) {
            builder.phoneNumber(jsonHall.get("phoneNumber").getAsString());
        }

        HallDto hall = builder.build();

        HallService.update(hall);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        hallService.deleteById(id);
    }
}
