package by.vsu.epam.dao.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import by.vsu.epam.dao.DaoException;
import by.vsu.epam.dao.TransferDao;
import by.vsu.epam.domain.Account;
import by.vsu.epam.domain.Transfer;

public class TransferDaoXmlImpl extends BaseDaoXmlImpl<Transfer> implements TransferDao {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<Transfer> readByAccount(Long accountId, Date begin, Date end) throws DaoException {
        return read(transfer -> ((transfer.getSrc() != null && transfer.getSrc().getId().equals(accountId))
                              || (transfer.getDest() != null && transfer.getDest().getId().equals(accountId)))
                              && transfer.getDate().after(begin)
                              && transfer.getDate().before(end));
    }

    @Override
    protected void readStartTag(String tagName, EntityHolder<Transfer> entityHolder, XMLStreamReader reader) throws DaoException {
        try {
            switch(tagName) {
                case "transfer":
                    entityHolder.setValue(new Transfer());
                    break;
                case "id":
                    entityHolder.getValue().setId(Long.valueOf(reader.getElementText()));
                    break;
                case "source-account-id":
                    entityHolder.getValue().setSrc(new Account());
                    entityHolder.getValue().getSrc().setId(Long.valueOf(reader.getElementText()));
                    break;
                case "destination-account-id":
                    entityHolder.getValue().setDest(new Account());
                    entityHolder.getValue().getDest().setId(Long.valueOf(reader.getElementText()));
                    break;
                case "summ":
                    entityHolder.getValue().setSumm(Long.valueOf(reader.getElementText()));
                    break;
                case "date":
                    entityHolder.getValue().setDate(format.parse(reader.getElementText()));
            }
        } catch(XMLStreamException | ParseException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected boolean readEndTag(String tagName, EntityHolder<Transfer> entityHolder, XMLStreamReader reader) throws DaoException {
        return "transfer".equals(tagName);
    }

    @Override
    protected void writeEntity(Transfer transfer, XMLStreamWriter writer) throws DaoException {
        try {
            writer.writeStartElement("transfer");
            writer.writeStartElement("id");
            writer.writeCharacters(transfer.getId().toString());
            writer.writeEndElement();
            Account src = transfer.getSrc();
            if(src != null) {
                writer.writeStartElement("source-account-id");
                writer.writeCharacters(src.getId().toString());
                writer.writeEndElement();
            }
            Account dest = transfer.getDest();
            if(dest != null) {
                writer.writeStartElement("destination-account-id");
                writer.writeCharacters(dest.getId().toString());
                writer.writeEndElement();
            }
            writer.writeStartElement("summ");
            writer.writeCharacters(transfer.getSumm().toString());
            writer.writeEndElement();
            writer.writeStartElement("date");
            writer.writeCharacters(format.format(transfer.getDate()));
            writer.writeEndElement();
            writer.writeEndElement();
        } catch(XMLStreamException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected String rootTagName() {
        return "transfers";
    }

    @Override
    protected String namespace() {
        return "http://epam.vsu.by/final-project-example/transfers";
    }

    @Override
    protected String xsdFilename() {
        return "transfers.xsd";
    }
}
