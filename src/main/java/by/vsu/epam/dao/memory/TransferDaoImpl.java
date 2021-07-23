package by.vsu.epam.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Transfer;

public class TransferDaoImpl extends BaseDaoImpl<Transfer> implements TransferDao {
    @Override
    public List<Transfer> readByAccount(Long accountId, Date begin, Date end) {
        List<Transfer> result = new ArrayList<>();
        for(Transfer transfer : getMap().values()) {
            if(((transfer.getSrc() != null && transfer.getSrc().getId().equals(accountId))
             || (transfer.getDest() != null && transfer.getDest().getId().equals(accountId)))
             && transfer.getDate().after(begin)
             && transfer.getDate().before(end)) {
                result.add(transfer);
            }
        }
        Collections.sort(result, (transfer1, transfer2) -> transfer1.getDate().compareTo(transfer2.getDate()));
        return result;
    }

    @Override
    protected Map<Long, Transfer> getMap() {
        return getRepository().getTransfers();
    }

    @Override
    public void update(Transfer entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }
}
