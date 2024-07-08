package me.nikastroganova.astoncourse.servletstask.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.nikastroganova.astoncourse.servletstask.dto.ActorDtoWithPerformances;
import me.nikastroganova.astoncourse.servletstask.dto.SimpleActorDto;
import me.nikastroganova.astoncourse.servletstask.service.ActorService;
import me.nikastroganova.astoncourse.servletstask.util.ServletUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;


@WebServlet("/actor")
public class ActorServlet extends HttpServlet {

    private static final ActorService actorService = ActorService.getInstance();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        Optional<ActorDtoWithPerformances> actor = actorService.findById(id);
        if (actor.isPresent()) {
            String json = gson.toJson(actor);
            ServletUtil.printJson(resp, json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonActor = gson.fromJson(req.getReader(), JsonObject.class);
        Set<String> jsonKeys = jsonActor.keySet();

        SimpleActorDto actor = SimpleActorDto.builder()
                .firstName(jsonActor.get("firstname").getAsString())
                .lastName(jsonActor.get("lastname").getAsString())
                .phoneNumber(jsonActor.get("phoneNumber").getAsString())
                .build();

        actor = ActorService.save(actor);

        ServletUtil.printJson(resp, gson.toJson(actor));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonActor = gson.fromJson(req.getReader(), JsonObject.class);
        Set<String> jsonKeys = jsonActor.keySet();

        SimpleActorDto.SimpleActorDtoBuilder builder = SimpleActorDto.builder();

        if (jsonKeys.contains("id")) {
            builder.id(jsonActor.get("id").getAsInt());
        }
        if (jsonKeys.contains("firstname")) {
            builder.firstName(jsonActor.get("firstname").getAsString());
        }
        if (jsonKeys.contains("lastname")) {
            builder.lastName(jsonActor.get("lastname").getAsString());
        }
        if (jsonKeys.contains("phoneNumber")) {
            builder.phoneNumber(jsonActor.get("phoneNumber").getAsString());
        }

        SimpleActorDto actor = builder.build();

        ActorService.update(actor);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = Integer.parseInt(req.getParameter("id"));
        actorService.deleteById(id);
    }
}
