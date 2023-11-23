package org.example.apps.task.controller;

public abstract class Controller {
    public abstract boolean supports(String route);


    public abstract Response handle(Request request);
}
