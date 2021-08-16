package by.vsu.epam.dao.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import by.vsu.epam.dao.Dao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Entity;

public abstract class BaseDaoXmlImpl<T extends Entity> implements Dao<T> {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public final Long create(T entity) throws DaoException {
        return write(entity);
    }

    @Override
    public final T read(Long id) throws DaoException {
        List<T> entities = read(entity -> entity.getId().equals(id));
        return entities.isEmpty() ? null : entities.get(0);
    }

    @Override
    public final void update(T entity) throws DaoException {
        write(curr -> curr.getId().equals(entity.getId()) ? entity : curr);
    }

    @Override
    public final void delete(Long id) throws DaoException {
        write(curr -> curr.getId().equals(id) ? null : curr);
    }

    protected final List<T> read(Predicate<T> filter) throws DaoException {
        validate();
        XMLInputFactory factory = XMLInputFactory.newFactory();
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        List<T> result = new ArrayList<>();
        try {
            inputStream = new FileInputStream(fileName);
            reader = factory.createXMLStreamReader(inputStream);
            EntityHolder<T> holder = new EntityHolder<>();
            while(reader.hasNext()) {
                int type = reader.next();
                switch(type) {
                    case XMLStreamConstants.START_ELEMENT: {
                        String tagName = reader.getLocalName();
                        readStartTag(tagName, holder, reader);
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        String tagName = reader.getLocalName();
                        if(readEndTag(tagName, holder, reader)) {
                            T entity = holder.getValue();
                            if(filter.test(entity)) {
                                result.add(entity);
                            }
                        }
                        break;
                    }
                }
            }
            return result;
        } catch(FileNotFoundException e) {
            return result;
        } catch(XMLStreamException e) {
            throw new DaoException(e);
        } finally {
            try { reader.close(); } catch(Exception e) {}
            try { inputStream.close(); } catch(Exception e) {}
        }
    }

    protected final void write(Function<T, T> function) throws DaoException {
        validate();
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        XMLStreamReader reader = null;
        XMLStreamWriter writer = null;
        File srcFile = new File(fileName);
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(fileName, ".~xml", new File("."));
            inputStream = new FileInputStream(srcFile);
            reader = inputFactory.createXMLStreamReader(inputStream);
            outputStream = new FileOutputStream(tmpFile);
            writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement(rootTagName());
            writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("xmlns", namespace());
            writer.writeAttribute("xsi:schemaLocation", namespace() + ' ' + xsdFilename());
            EntityHolder<T> holder = new EntityHolder<>();
            while(reader.hasNext()) {
                int type = reader.next();
                switch(type) {
                    case XMLStreamConstants.START_ELEMENT: {
                        String tagName = reader.getLocalName();
                        readStartTag(tagName, holder, reader);
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        String tagName = reader.getLocalName();
                        if(readEndTag(tagName, holder, reader)) {
                            T entity = holder.getValue();
                            entity = function.apply(entity);
                            if(entity != null) {
                                writeEntity(entity, writer);
                            }
                        }
                        break;
                    }
                }
            }
            writer.writeEndElement();
            writer.writeEndDocument();
        } catch(XMLStreamException | IOException e) {
            if(tmpFile != null) {
                tmpFile.delete();
                tmpFile = null;
            }
            throw new DaoException(e);
        } finally {
            try { reader.close(); } catch(Exception e) {}
            try { inputStream.close(); } catch(Exception e) {}
            try { writer.close(); } catch(Exception e) {}
            try { outputStream.close(); } catch(Exception e) {}
            if(tmpFile != null) {
                srcFile.delete();
                tmpFile.renameTo(srcFile);
            }
        }
    }

    protected final Long write(T newEntity) throws DaoException {
        validate();
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        XMLStreamReader reader = null;
        XMLStreamWriter writer = null;
        File srcFile = new File(fileName);
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(fileName, ".~xml", new File("."));
            outputStream = new FileOutputStream(tmpFile);
            writer = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement(rootTagName());
            writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("xmlns", namespace());
            writer.writeAttribute("xsi:schemaLocation", namespace() + ' ' + xsdFilename());
            Long id = 0L;
            try {
                inputStream = new FileInputStream(srcFile);
                reader = inputFactory.createXMLStreamReader(inputStream);
                EntityHolder<T> holder = new EntityHolder<>();
                while(reader.hasNext()) {
                    int type = reader.next();
                    switch(type) {
                        case XMLStreamConstants.START_ELEMENT: {
                            String tagName = reader.getLocalName();
                            readStartTag(tagName, holder, reader);
                            break;
                        }
                        case XMLStreamConstants.END_ELEMENT: {
                            String tagName = reader.getLocalName();
                            if(readEndTag(tagName, holder, reader)) {
                                T entity = holder.getValue();
                                if(entity.getId() > id) {
                                    id = entity.getId();
                                }
                                writeEntity(entity, writer);
                            }
                            break;
                        }
                    }
                }
            } catch(FileNotFoundException e) {}
            id++;
            newEntity.setId(id);
            writeEntity(newEntity, writer);
            writer.writeEndElement();
            writer.writeEndDocument();
            return id;
        } catch(XMLStreamException | IOException e) {
            if(tmpFile != null) {
                tmpFile.delete();
                tmpFile = null;
            }
            throw new DaoException(e);
        } finally {
            try { reader.close(); } catch(Exception e) {}
            try { inputStream.close(); } catch(Exception e) {}
            try { writer.close(); } catch(Exception e) {}
            try { outputStream.close(); } catch(Exception e) {}
            if(tmpFile != null) {
                srcFile.delete();
                tmpFile.renameTo(srcFile);
            }
        }
    }

    protected abstract void readStartTag(String tagName, EntityHolder<T> entityHolder, XMLStreamReader reader) throws DaoException;

    protected abstract boolean readEndTag(String tagName, EntityHolder<T> entityHolder, XMLStreamReader reader) throws DaoException;

    protected abstract void writeEntity(T entity, XMLStreamWriter writer) throws DaoException;

    protected abstract String rootTagName();

    protected abstract String namespace();

    protected abstract String xsdFilename();

    protected static class EntityHolder<T extends Entity> {
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    private void validate() throws DaoException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
        try {
            Schema schema = factory.newSchema(new File(xsdFilename()));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(xmlErrorHandler);
            validator.validate(new StreamSource(fileName));
        } catch(FileNotFoundException e) {
        } catch(SAXException | IOException e) {
            String errorMessage = xmlErrorHandler.getErrorMessage();
            if(errorMessage != null) {
                throw new DaoException(errorMessage, e);
            } else {
                throw new DaoException(e);
            }
        }
    }

    private static class XmlErrorHandler implements ErrorHandler {
        private StringBuilder errorMessage = new StringBuilder();

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            buildErrorMessage(exception, "warning");
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            buildErrorMessage(exception, "error");
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            buildErrorMessage(exception, "fatal error");
            throw exception;
        }

        public String getErrorMessage() {
            if(!errorMessage.isEmpty()) {
                return errorMessage.toString();
            } else {
                return null;
            }
        }

        private void buildErrorMessage(SAXParseException e, String level) {
            errorMessage.append("XML validation ").append(level).append(": line ").append(e.getLineNumber()).append(", column ").append(e.getColumnNumber());
        }
    }
}
