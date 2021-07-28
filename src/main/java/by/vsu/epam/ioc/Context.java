package by.vsu.epam.ioc;

import by.vsu.epam.console.Menu;
import by.vsu.epam.console.MenuItem;
import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.service.AccountService;
import by.vsu.epam.service.TransferService;

public interface Context {
    AccountDao getAccountDao();
    TransferDao getTransferDao();

    AccountService getAccountService();
    TransferService getTransferService();

    MenuItem getShowAccountsMenuItem();
    MenuItem getShowAccountHistoryMenuItem();
    MenuItem getPutCashMenuItem();
    MenuItem getGetCashMenuItem();
    MenuItem getTransferMenuItem();
    MenuItem getOpenAccountMenuItem();
    MenuItem getExitMenuItem();
    Menu getMenu();
}
