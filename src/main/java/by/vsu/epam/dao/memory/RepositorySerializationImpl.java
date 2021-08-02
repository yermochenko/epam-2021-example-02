package by.vsu.epam.dao.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class RepositorySerializationImpl implements Repository {
    private static RepositorySerializationImpl instance = new RepositorySerializationImpl();

    private String accountsFilename;
    private String transfersFilename;

    private Map<Long, Account> accounts;
    private Map<Long, Transfer> transfers;

    private RepositorySerializationImpl() {}

    @SuppressWarnings("unchecked")
    public void init(String accountsFilename, String transfersFilename) {
        this.accountsFilename = accountsFilename;
        this.transfersFilename = transfersFilename;
        try(ObjectInputStream accountsInputStream = new ObjectInputStream(new FileInputStream(accountsFilename))) {
            accounts = (Map<Long, Account>) accountsInputStream.readObject();
        } catch(IOException | ClassNotFoundException e) {
            accounts = new HashMap<>();
        }
        try(ObjectInputStream transfersInputStream = new ObjectInputStream(new FileInputStream(transfersFilename))) {
            transfers = (Map<Long, Transfer>) transfersInputStream.readObject();
        } catch(IOException | ClassNotFoundException e) {
            transfers = new HashMap<>();
        }
    }

    @Override
    public Map<Long, Account> getAccounts() {
        return accounts;
    }

    @Override
    public Map<Long, Transfer> getTransfers() {
        return transfers;
    }

    @Override
    public void saveAccounts() throws RepositoryException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(accountsFilename))) {
            outputStream.writeObject(accounts);
        } catch(IOException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void saveTransfers() throws RepositoryException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(transfersFilename))) {
            outputStream.writeObject(transfers);
        } catch(IOException e) {
            throw new RepositoryException(e);
        }
    }

    public static RepositorySerializationImpl getInstance() {
        return instance;
    }
}
