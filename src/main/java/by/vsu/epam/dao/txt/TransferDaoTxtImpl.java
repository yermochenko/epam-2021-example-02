package by.vsu.epam.dao.txt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class TransferDaoTxtImpl extends BaseDaoTxtImpl<Transfer> implements TransferDao {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

    @Override
    public List<Transfer> readByAccount(Long accountId, Date begin, Date end) throws DaoException {
        return read(transfer -> ((transfer.getSrc() != null && transfer.getSrc().getId().equals(accountId))
                              || (transfer.getDest() != null && transfer.getDest().getId().equals(accountId)))
                              && transfer.getDate().after(begin)
                              && transfer.getDate().before(end));
    }

    @Override
    protected Transfer convert(String[] cells) throws DaoException {
        if(cells.length == 5) {
            Transfer transfer = new Transfer();
            try {
                transfer.setId(Long.valueOf(cells[0]));
            } catch(NumberFormatException e) {
                throw new DaoException(String.format("Incorrect transfers file format. Incorrect transfer identity \"%s\" in line \"%s\"", cells[0], String.join(";", cells)));
            }
            if(!cells[1].isBlank()) {
                try {
                    Account src = new Account();
                    src.setId(Long.valueOf(cells[1]));
                    transfer.setSrc(src);
                } catch(NumberFormatException e) {
                    throw new DaoException(String.format("Incorrect transfers file format. Incorrect transfer's source account identity \"%s\" in line \"%s\"", cells[1], String.join(";", cells)));
                }
            }
            if(!cells[2].isBlank()) {
                try {
                    Account dest = new Account();
                    dest.setId(Long.valueOf(cells[2]));
                    transfer.setDest(dest);
                } catch(NumberFormatException e) {
                    throw new DaoException(String.format("Incorrect transfers file format. Incorrect transfer's destination account identity \"%s\" in line \"%s\"", cells[2], String.join(";", cells)));
                }
            }
            try {
                transfer.setSumm(Long.valueOf(cells[3]));
            } catch(NumberFormatException e) {
                throw new DaoException(String.format("Incorrect transfers file format. Incorrect transfer summ \"%s\" in line \"%s\"", cells[3], String.join(";", cells)));
            }
            try {
                transfer.setDate(DATE_FORMAT.parse(cells[4]));
            } catch(ParseException e) {
                throw new DaoException(String.format("Incorrect transfers file format. Incorrect transfer date \"%s\" in line \"%s\"", cells[4], String.join(";", cells)));
            }
            return transfer;
        } else {
            throw new DaoException(String.format("Incorrect transfers file format. Line \"%s\" doesn't contains exactly 5 parts splitted by semicolon", String.join(";", cells)));
        }
    }

    @Override
    protected String[] convert(Transfer transfer) {
        return new String[] {
            transfer.getId().toString(),
            transfer.getSrc() != null ? transfer.getSrc().getId().toString() : new String(),
            transfer.getDest() != null ? transfer.getDest().getId().toString() : new String(),
            transfer.getSumm().toString(),
            DATE_FORMAT.format(transfer.getDate())
        };
    }
}
