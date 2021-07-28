package by.vsu.epam.ioc;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.memory.decorator.AccountDaoDecoratorImpl;
import by.vsu.epam.dao.memory.decorator.TransferDaoDecoratorImpl;

public class ContextExceptionsImpl extends ContextImpl {
    @Override
    public AccountDao getAccountDao() {
        AccountDao accountDao = super.getAccountDao();
        AccountDaoDecoratorImpl accountDaoDecorator = new AccountDaoDecoratorImpl();
        accountDaoDecorator.setImpl(accountDao);
        return accountDaoDecorator;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDao transferDao = super.getTransferDao();
        TransferDaoDecoratorImpl transferDaoDecorator = new TransferDaoDecoratorImpl();
        transferDaoDecorator.setImpl(transferDao);
        return transferDaoDecorator;
    }
}
