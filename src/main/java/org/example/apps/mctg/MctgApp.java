package org.example.apps.mctg;

import org.example.apps.mctg.controller.*;
import org.example.server.ServerApplication;
import org.example.server.http.HttpContentType;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class MctgApp implements ServerApplication {

    private final List<Controller> controllers;

    public MctgApp() {
        this.controllers = new ArrayList<>();
        this.controllers.add(new UserController());
        this.controllers.add(new SessionController());
        this.controllers.add(new PackageController());
        this.controllers.add(new TransactionController());
    }

    @Override
    public Response handle(Request request) {

        for(Controller controller: controllers) {
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
