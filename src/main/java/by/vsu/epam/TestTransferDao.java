package by.vsu.epam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Transfer;
import by.vsu.epam.ioc.Context;
import by.vsu.epam.ioc.ContextFactory;

public class TestTransferDao {
    public static void output(List<Transfer> transfers) {
        System.out.println("******************** Список денежных переводов ********************");
        for(Transfer transfer : transfers) {
            if(transfer.getSrc() == null) {
                System.out.printf(
                    "[%02d]\n    начисление наличными на счёт #%02d (владелец %s)\n    %d руб. %02d коп.\n    %6$td.%6$tm.%6$tY, %6$tH:%6$tM.\n",
                    transfer.getId(),
                    transfer.getDest().getId(),
                    transfer.getDest().getName(),
                    transfer.getSumm() / 100,
                    transfer.getSumm() % 100,
                    transfer.getDate()
                );
            } else if(transfer.getDest() == null) {
                System.out.printf(
                    "[%02d]\n    снятие наличными со счёта #%02d (владелец %s)\n    %d руб. %02d коп.\n    %6$td.%6$tm.%6$tY, %6$tH:%6$tM.\n",
                    transfer.getId(),
                    transfer.getSrc().getId(),
                    transfer.getSrc().getName(),
                    transfer.getSumm() / 100,
                    transfer.getSumm() % 100,
                    transfer.getDate()
                );
            } else {
                System.out.printf(
                    "[%02d]\n    перевод со счёта #%02d (владелец %s)\n            на счёт  #%02d (владелец %s)\n    %d руб. %02d коп.\n    %8$td.%8$tm.%8$tY, %8$tH:%8$tM.\n",
                    transfer.getId(),
                    transfer.getSrc().getId(),
                    transfer.getSrc().getName(),
                    transfer.getDest().getId(),
                    transfer.getDest().getName(),
                    transfer.getSumm() / 100,
                    transfer.getSumm() % 100,
                    transfer.getDate()
                );
            }
        }
        System.out.println();
    }

    private static Date date(int day, int month, int year, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        return c.getTime();
    }

    public static void main(String[] args) throws DaoException {
        Date end = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.MONTH, -1);
        Date begin = calendar.getTime();
        Context context = ContextFactory.newInstance();
        TransferDao transferDao = context.getTransferDao();
        AccountDao accountDao = context.getAccountDao();
        Transfer transfer = null;
        System.out.println("###################################################################");
        System.out.println("###################################################################");
        output(transferDao.readByAccount(1L, begin, end));
        transfer = new Transfer();
        transfer.setDest(accountDao.read(1L));
        transfer.setSumm(123L);
        transfer.setDate(date(1, 11, 2020, 12, 0));
        transferDao.create(transfer);
        output(transferDao.readByAccount(1L, begin, end));
        System.out.println("###################################################################");
        System.out.println("###################################################################");
        output(transferDao.readByAccount(2L, begin, end));
        transfer = new Transfer();
        transfer.setDest(accountDao.read(2L));
        transfer.setSumm(123L);
        transfer.setDate(date(1, 11, 2020, 13, 0));
        transferDao.create(transfer);
        output(transferDao.readByAccount(2L, begin, end));
        System.out.println("###################################################################");
        System.out.println("###################################################################");
        output(transferDao.readByAccount(3L, begin, end));
        transfer = new Transfer();
        transfer.setDest(accountDao.read(3L));
        transfer.setSumm(123L);
        transfer.setDate(date(1, 11, 2020, 14, 0));
        transferDao.create(transfer);
        output(transferDao.readByAccount(3L, begin, end));
    }
}
