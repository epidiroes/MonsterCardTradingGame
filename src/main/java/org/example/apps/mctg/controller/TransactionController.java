package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.AuthorizationService;
import org.example.apps.mctg.service.TransactionService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class TransactionController extends Controller {
    private final AuthorizationService authorizationService;
    private final TransactionService transactionService;
    public TransactionController() {
        this.authorizationService = new AuthorizationService(new UserRepository());
        this.transactionService = new TransactionService(new PackageRepository(), new UserRepository(), new CardRepository());
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/transactions/packages");
    }

    @Override
    public Response handle(Request request) {
        if (request.getRoute().equals("/transactions/packages")) {
            if (request.getMethod().equals("POST")) {
                return acquire(request);
            }
            return status(HttpStatus.METHOD_NOT_ALLOWED);
        }
        return status(HttpStatus.NOT_FOUND);
    }

    private Response acquire(Request request) {
        User user = authorizationService.authorizedUser(request.getAuthorization());
        Package pack = transactionService.buy(user);
        return ok(json(pack));
    }
}
