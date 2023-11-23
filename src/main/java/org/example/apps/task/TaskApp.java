package org.example.apps.task;

import org.example.apps.task.controller.Controller;
import org.example.apps.task.controller.TaskController;
import org.example.server.http.Response;

import java.util.ArrayList;
import java.util.List;

public class TaskApp  {

    private final List<Controller> controllers;

    public TaskApp() {
        this.controllers = new ArrayList<>();
        this.controllers.add(new TaskController());
    }
    @Override
    public Response handele() {
        return null;
    }
}
