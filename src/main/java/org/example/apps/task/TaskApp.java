package org.example.apps.task;

import org.example.apps.task.controller.Controller;
import org.example.apps.task.controller.TaskController;
import org.example.server.ServerApplication;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class TaskApp implements ServerApplication {

    private final List<Controller> controllers;

    public TaskApp() {
        this.controllers = new ArrayList<>();
        this.controllers.add(new TaskController());
    }

    @Override
    public Response handle(Request request) {

        for (Controller controller: controllers) {
            if (!controller.supports(request.getRoute())) {
                continue;
            }

            return controller.handle(request);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody("Route not found!");

        return response;
    }
}
