package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.PackageService;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

import java.util.List;

public class PackageController extends Controller {
    private final AuthorizationService authorizationService;
    private final PackageService packageService;
    public PackageController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.packageService = new PackageService(new PackageRepository(), new CardRepository());
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
        User user = authorizationService.authorizedUser(request.getAuthorization());
        if (!user.getUsername().equals("admin")) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Non admin trying to create package");
        }
        List<Card> cards = toObjects(request, Card.class);
        Package pack = packageService.save(user, cards);
        if (pack == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Could not create package because cards already exist");
        }
        return ok(json(pack));
    }
}
