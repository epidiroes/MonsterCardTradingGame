package org.example.apps.task.controller;

public class TaskController extends Controller {

    @Override
    public boolean supports(String route) {
        return route.startsWith("/tasks");
    }

    @Override
    public Response handle() {
        return null;
    }
}
