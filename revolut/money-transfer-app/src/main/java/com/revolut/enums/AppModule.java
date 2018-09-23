package com.revolut.enums;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.revolut.dao.AccountDAO;
import com.revolut.dao.AccountDAOImpl;
import com.revolut.dao.CustomerDAO;
import com.revolut.dao.CustomerDAOImpl;
import com.revolut.dao.RelatedTransactionsDAO;
import com.revolut.dao.RelatedTransactionsDAOImpl;
import com.revolut.dao.TransactionDAO;
import com.revolut.dao.TransactionDAOImpl;

/**
 * App module
 *
 * @author vsushko
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDAO.class).annotatedWith(Names.named("AccountDAO")).to(AccountDAOImpl.class);
        bind(CustomerDAO.class).annotatedWith(Names.named("CustomerDAO")).to(CustomerDAOImpl.class);
        bind(TransactionDAO.class).annotatedWith(Names.named("TransactionDAO")).to(TransactionDAOImpl.class);
        bind(RelatedTransactionsDAO.class).annotatedWith(Names.named("RelatedTransactionsDAO"))
                .to(RelatedTransactionsDAOImpl.class);
    }
}
