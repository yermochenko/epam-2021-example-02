package by.vsu.epam.dao.xml;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import by.vsu.epam.dao.AccountDao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Account;

public class AccountDaoXmlImpl extends BaseDaoXmlImpl<Account> implements AccountDao {
    @Override
    public List<Account> readAll() throws DaoException {
        return read(account -> true);
    }

    @Override
    protected void readStartTag(String tagName, EntityHolder<Account> entityHolder, XMLStreamReader reader) throws DaoException {
        try {
            switch(tagName) {
                case "account":
                    entityHolder.setValue(new Account());
                    break;
                case "id":
                    entityHolder.getValue().setId(Long.valueOf(reader.getElementText()));
                    break;
                case "name":
                    entityHolder.getValue().setName(reader.getElementText());
                    break;
                case "balance":
                    entityHolder.getValue().setBalance(Long.valueOf(reader.getElementText()));
                    break;
            }
        } catch(XMLStreamException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected boolean readEndTag(String tagName, EntityHolder<Account> entityHolder, XMLStreamReader reader) throws DaoException {
        return "account".equals(tagName);
    }

    @Override
    protected void writeEntity(Account account, XMLStreamWriter writer) throws DaoException {
        try {
            writer.writeStartElement("account");
            writer.writeStartElement("id");
            writer.writeCharacters(account.getId().toString());
            writer.writeEndElement();
            writer.writeStartElement("name");
            writer.writeCharacters(account.getName());
            writer.writeEndElement();
            writer.writeStartElement("balance");
            writer.writeCharacters(account.getBalance().toString());
            writer.writeEndElement();
            writer.writeEndElement();
        } catch(XMLStreamException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected String rootTagName() {
        return "accounts";
    }

    @Override
    protected String namespace() {
        return "http://epam.vsu.by/final-project-example/accounts";
    }

    @Override
    protected String xsdFilename() {
        return "accounts.xsd";
    }
}
