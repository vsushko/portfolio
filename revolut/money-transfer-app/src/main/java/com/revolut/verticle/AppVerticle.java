package com.revolut.verticle;

import com.google.inject.Guice;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import com.revolut.entity.Transaction;
import com.revolut.enums.AppModule;
import com.revolut.service.AccountServiceImpl;
import com.revolut.service.CustomerServiceImpl;
import com.revolut.service.RelatedTransactionServiceImpl;
import com.revolut.service.TransactionServiceImpl;
import com.revolut.service.dto.AccountOperation;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Collection;

/**
 * AppVerticle
 *
 * @author vsushko
 */
public class AppVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        router.route("/").handler(context -> context.response().sendFile("webroot/index.html").end());

        // create the HTTP server and pass the "accept" method to the request handler.
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // retrieve the port from the configuration, default to 8080.
                        config().getInteger("http.port", 8080),
                        result -> {
                            if (result.succeeded()) {
                                startFuture.complete();
                            } else {
                                startFuture.fail(result.cause());
                            }
                        }
                );

        Handler<RoutingContext> accountHandler = this::getAllAccounts;
        router.get("/api/accounts").handler(accountHandler);
        router.get("/api/customers").handler(this::getAllCustomers);
        router.get("/api/transactions").handler(this::getAllTransactions);
        router.get("/api/related").handler(this::getAllRelatedTransactions);

        router.route("/api/customers*").handler(BodyHandler.create());
        router.post("/api/customers").handler(this::addCustomer);
        router.route("/api/accounts*").handler(BodyHandler.create());
        router.post("/api/accounts").handler(this::addAccount);
        router.route("/api/transactions*").handler(BodyHandler.create());
        router.post("/api/transactions").handler(this::executeOperation);
    }

    /**
     * Returns customers
     *
     * @param routingContext the routing context
     */
    private void getAllCustomers(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(Guice.createInjector(new AppModule())
                        .getInstance(CustomerServiceImpl.class).getCustomers()));
    }

    /**
     * Returns accounts
     *
     * @param routingContext the routing context
     */
    private void getAllAccounts(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(Guice.createInjector(new AppModule())
                        .getInstance(AccountServiceImpl.class).getAccounts()));
    }

    /**
     * Returns transactions
     *
     * @param routingContext the routing context
     */
    private void getAllTransactions(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(Guice.createInjector(new AppModule())
                        .getInstance(TransactionServiceImpl.class).getTransactions()));
    }

    /**
     * Returns related transactions
     *
     * @param routingContext the routing context
     */
    private void getAllRelatedTransactions(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(Guice.createInjector(new AppModule())
                        .getInstance(RelatedTransactionServiceImpl.class).getRelatedTransactions()));
    }

    /**
     * Adds customer
     *
     * @param routingContext the routing context
     */
    private void addCustomer(RoutingContext routingContext) {
        Customer customer =
                Guice.createInjector(new AppModule()).getInstance(CustomerServiceImpl.class)
                        .createCustomer(Json.decodeValue(routingContext.getBodyAsString(), Customer.class));

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(customer));
    }

    /**
     * Adds account
     *
     * @param routingContext the routing context
     */
    private void addAccount(RoutingContext routingContext) {
        AccountOperation accountOperation = Json.decodeValue(routingContext.getBodyAsString(), AccountOperation.class);
        Account account =
                Guice.createInjector(new AppModule()).getInstance(AccountServiceImpl.class)
                        .createAccount(accountOperation.getCustomerId());

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(account));
    }

    /**
     * Executes operation
     *
     * @param routingContext the routing context
     */
    private void executeOperation(RoutingContext routingContext) {
        AccountOperation accountOperation =
                Json.decodeValue(routingContext.getBodyAsString(), AccountOperation.class);

        Collection<Transaction> transactions =
                Guice.createInjector(new AppModule()).getInstance(TransactionServiceImpl.class)
                        .executeOperation(accountOperation);

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(transactions));
    }
}
