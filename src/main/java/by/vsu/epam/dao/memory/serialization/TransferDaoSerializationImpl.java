package by.vsu.epam.dao.memory.serialization;

import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.memory.RepositoryException;
import by.vsu.epam.dao.memory.TransferDaoImpl;
import by.vsu.epam.domain.Transfer;

public class TransferDaoSerializationImpl extends TransferDaoImpl {
    @Override
    public Long create(Transfer transfer) throws DaoException {
        try {
            Long id = super.create(transfer);
            getRepository().saveTransfers();
            return id;
        } catch(RepositoryException e) {
            throw new DaoException(e);
        }
    }
}
