package by.vsu.epam.service;

public interface TransferService {
    void transfer(Long srcId, Long destId, Long summ) throws ServiceException;

    void putCash(Long accountId, Long summ) throws ServiceException;

    void getCash(Long accountId, Long summ) throws ServiceException;
}
