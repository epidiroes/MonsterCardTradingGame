package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.PackageService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class PackageController extends Controller {
    private final PackageService packageService;
    public PackageController() {
        this.packageService = new PackageService(new PackageRepository(), new CardRepository(), new UserRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/packages");
    }

    @Override
    public Response handle(Request request) {

        if (request.getRoute().equals("/packages")) {
            if (request.getMethod().equals("POST")) {
                return create(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response create(Request request) {
        List<Card> cards = toObjects(request, Card.class);
        Package p = packageService.save(request, cards);
        if (p == null) {
            return statusMessage(HttpStatus.BAD_REQUEST, "Could not create package (no authorization or cards already exist");
        }
        return ok(json(p));
    }
}
