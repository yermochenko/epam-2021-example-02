package by.vsu.epam.dao.memory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class RepositoryImpl implements Repository {
    private static RepositoryImpl instance = new RepositoryImpl();

    private Map<Long, Account> accounts = new HashMap<>();
    private Map<Long, Transfer> transfers = new HashMap<>();

    private RepositoryImpl() {
        newAccount(1L, "Иванов" , 25_00L);
        newAccount(2L, "Петров" , 60_00L);
        newAccount(3L, "Сидоров",  0_00L);
        newTransfer(1L , null, 1L  , 100_00L, -3, 13, 15);
        newTransfer(2L , 1L  , 2L  ,  25_00L, -2, 9 , 35);
        newTransfer(3L , 1L  , 3L  ,  35_00L, -2, 9 , 45);
        newTransfer(4L , 1L  , null,  10_00L, -2, 15, 30);
        newTransfer(5L , 3L  , null,  35_00L, -2, 16, 55);
        newTransfer(6L , null, 2L  ,  45_00L, -2, 10, 15);
        newTransfer(7L , 2L  , 3L  ,   5_00L, -1, 10, 20);
        newTransfer(8L , 1L  , null,   5_00L, -1, 16, 15);
        newTransfer(9L , 2L  , null,   5_00L, -1, 17, 25);
        newTransfer(10L, 3L  , null,   5_00L, -1, 17, 50);
    }

    private void newAccount(Long id, String name, Long balance) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setBalance(balance);
        accounts.put(id, account);
    }

    private void newTransfer(Long id, Long srcId, Long destId, Long summ, int day, int hour, int minute) {
        Transfer transfer = new Transfer();
        transfer.setId(id);
        transfer.setSrc(srcId != null ? accounts.get(srcId) : null);
        transfer.setDest(destId != null ? accounts.get(destId) : null);
        transfer.setSumm(summ);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        transfer.setDate(c.getTime());
        transfers.put(id, transfer);
    }

    @Override
    public Map<Long, Account> getAccounts() {
        return accounts;
    }

    @Override
    public Map<Long, Transfer> getTransfers() {
        return transfers;
    }

    public static RepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public void saveAccounts() {}

    @Override
    public void saveTransfers() {}
}
