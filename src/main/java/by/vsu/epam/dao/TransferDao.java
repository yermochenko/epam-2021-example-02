package by.vsu.epam.dao;

import java.util.Date;
import java.util.List;

import by.vsu.epam.domain.Transfer;

public interface TransferDao extends Dao<Transfer> {
    List<Transfer> readByAccount(Long accountId, Date begin, Date end);
}
