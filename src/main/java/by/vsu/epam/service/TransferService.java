package by.vsu.epam.service;

public interface TransferService {
    void transfer(Long srcId, Long destId, Long sum) throws ServiceException;
}
