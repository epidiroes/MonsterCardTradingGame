package org.example.apps.mctg.controller;

import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.repository.PackageRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.example.apps.mctg.service.TransactionService;
import org.example.server.http.HttpStatus;
import org.example.server.http.Request;
import org.example.server.http.Response;

public class TransactionController extends Controller {
    private final TransactionService transactionService;
    public TransactionController() {
        this.transactionService = new TransactionService(new PackageRepository(), new UserRepository());
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
        return null;
    }

    private Response acquire(Request request) {
        Package pack = transactionService.buy(request);
        if (pack == null) {
            return statusMessage(HttpStatus.BAD_REQUEST, "Transaction failed (no money or no available packages)");
        }
        return ok(json(pack));
    }
}
