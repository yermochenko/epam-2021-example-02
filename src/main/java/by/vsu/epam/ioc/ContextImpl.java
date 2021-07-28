package by.vsu.epam.ioc;

import by.vsu.epam.console.ExitMenuItem;
import by.vsu.epam.console.GetCashMenuItem;
import by.vsu.epam.console.Menu;
import by.vsu.epam.console.MenuItem;
import by.vsu.epam.console.OpenAccountMenuItem;
import by.vsu.epam.console.PutCashMenuItem;
import by.vsu.epam.console.ShowAccountHistoryMenuItem;
import by.vsu.epam.console.ShowAccountsMenuItem;
import by.vsu.epam.console.TransferMenuItem;
import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.dao.memory.AccountDaoImpl;
import by.vsu.epam.dao.memory.Repository;
import by.vsu.epam.dao.memory.RepositoryImpl;
import by.vsu.epam.dao.memory.TransferDaoImpl;
import by.vsu.epam.service.AccountService;
import by.vsu.epam.service.AccountServiceImpl;
import by.vsu.epam.service.TransferService;
import by.vsu.epam.service.TransferServiceImpl;

public class ContextImpl implements Context {
    @Override
    public Menu getMenu() {
        Menu menu = new Menu();
        menu.add(1, getShowAccountsMenuItem());
        menu.add(2, getShowAccountHistoryMenuItem());
        menu.add(3, getPutCashMenuItem());
        menu.add(4, getGetCashMenuItem());
        menu.add(5, getTransferMenuItem());
        menu.add(6, getOpenAccountMenuItem());
        menu.add(0, getExitMenuItem());
        return menu;
    }

    @Override
    public MenuItem getShowAccountsMenuItem() {
        ShowAccountsMenuItem menuItem = new ShowAccountsMenuItem();
        menuItem.setAccountService(getAccountService());
        return menuItem;
    }

    @Override
    public MenuItem getShowAccountHistoryMenuItem() {
        ShowAccountHistoryMenuItem menuItem = new ShowAccountHistoryMenuItem();
        menuItem.setAccountService(getAccountService());
        return menuItem;
    }

    @Override
    public MenuItem getPutCashMenuItem() {
        PutCashMenuItem menuItem = new PutCashMenuItem();
        menuItem.setTransferService(getTransferService());
        return menuItem;
    }

    @Override
    public MenuItem getGetCashMenuItem() {
        GetCashMenuItem menuItem = new GetCashMenuItem();
        menuItem.setTransferService(getTransferService());
        return menuItem;
    }

    @Override
    public MenuItem getTransferMenuItem() {
        TransferMenuItem menuItem = new TransferMenuItem();
        menuItem.setTransferService(getTransferService());
        return menuItem;
    }

    @Override
    public MenuItem getOpenAccountMenuItem() {
        OpenAccountMenuItem menuItem = new OpenAccountMenuItem();
        menuItem.setAccountService(getAccountService());
        return menuItem;
    }

    @Override
    public MenuItem getExitMenuItem() {
        return new ExitMenuItem();
    }

    @Override
    public AccountService getAccountService() {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.setAccountDao(getAccountDao());
        accountService.setTransferDao(getTransferDao());
        return accountService;
    }

    @Override
    public TransferService getTransferService() {
        TransferServiceImpl transferService = new TransferServiceImpl();
        transferService.setAccountDao(getAccountDao());
        transferService.setTransferDao(getTransferDao());
        return transferService;
    }

    @Override
    public AccountDao getAccountDao() {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        accountDao.setRepository(getRepository());
        return accountDao;
    }

    @Override
    public TransferDao getTransferDao() {
        TransferDaoImpl transferDao = new TransferDaoImpl();
        transferDao.setRepository(getRepository());
        return transferDao;
    }

    public Repository getRepository() {
        return RepositoryImpl.getInstance();
    }
}
