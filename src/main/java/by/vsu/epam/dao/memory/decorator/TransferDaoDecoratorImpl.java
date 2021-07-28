package by.vsu.epam.dao.memory.decorator;

import java.util.Date;
import java.util.List;

import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Transfer;

public class TransferDaoDecoratorImpl extends DaoDecoratorImpl<Transfer, TransferDao> implements TransferDao {
    @Override
    public List<Transfer> readByAccount(Long accountId, Date begin, Date end) throws DaoException {
        return getImpl().readByAccount(accountId, begin, end);
    }
}
