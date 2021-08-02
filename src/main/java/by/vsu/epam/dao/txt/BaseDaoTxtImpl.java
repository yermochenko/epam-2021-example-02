package by.vsu.epam.dao.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import by.vsu.epam.dao.Dao;
import by.vsu.epam.dao.DaoException;
import by.vsu.epam.domain.Entity;

public abstract class BaseDaoTxtImpl<T extends Entity> implements Dao<T> {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Long create(T entity) throws DaoException {
        return write(entity);
    }

    @Override
    public T read(Long id) throws DaoException {
        List<T> entities = read(entity -> entity.getId().equals(id));
        return entities.isEmpty() ? null : entities.get(0);
    }

    @Override
    public void update(T entity) throws DaoException {
        write(curr -> curr.getId().equals(entity.getId()) ? entity : curr);
    }

    @Override
    public void delete(Long id) throws DaoException {
        write(curr -> curr.getId().equals(id) ? null : curr);
    }

    protected final List<T> read(Predicate<T> filter) throws DaoException {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        List<T> result = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(fileName);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                T entity = convert(line.split(";"));
                if(filter.test(entity)) {
                    result.add(entity);
                }
            }
            return result;
        } catch(FileNotFoundException e) {
            return result;
        } catch(IOException e) {
            throw new DaoException(e);
        } finally {
            try { bufferedReader.close(); } catch(Exception e) {}
            try { inputStreamReader.close(); } catch(Exception e) {}
            try { fileInputStream.close(); } catch(Exception e) {}
        }
    }

    protected final void write(Function<T, T> function) throws DaoException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        File srcFile = new File(fileName);
        File tmpFile = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            tmpFile = File.createTempFile(fileName, ".~txt", new File("."));
            fileOutputStream = new FileOutputStream(tmpFile);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                T entity = convert(line.split(";"));
                entity = function.apply(entity);
                if(entity != null) {
                    String[] cells = convert(entity);
                    bufferedWriter.write(String.join(";", cells));
                    bufferedWriter.newLine();
                }
            }
            
        } catch(IOException e) {
            if(tmpFile != null) {
                tmpFile.delete();
                tmpFile = null;
            }
            throw new DaoException(e);
        } finally {
            try { bufferedReader.close(); } catch(Exception e) {}
            try { bufferedWriter.close(); } catch(Exception e) {}
            try { inputStreamReader.close(); } catch(Exception e) {}
            try { outputStreamWriter.close(); } catch(Exception e) {}
            try { fileInputStream.close(); } catch(Exception e) {}
            try { fileOutputStream.close(); } catch(Exception e) {}
            if(tmpFile != null) {
                srcFile.delete();
                tmpFile.renameTo(srcFile);
            }
        }
    }

    protected final Long write(T newEntity) throws DaoException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Long id = 0L;
        try {
            fileInputStream = new FileInputStream(fileName);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                T entity = convert(line.split(";"));
                if(entity.getId() > id) {
                    id = entity.getId();
                }
            }
        } catch(FileNotFoundException e) {
        } catch(IOException e) {
            throw new DaoException(e);
        } finally {
            try { bufferedReader.close(); } catch(Exception e) {}
            try { inputStreamReader.close(); } catch(Exception e) {}
            try { fileInputStream.close(); } catch(Exception e) {}
        }
        newEntity.setId(id + 1);
        try {
            fileOutputStream = new FileOutputStream(fileName, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String[] cells = convert(newEntity);
            bufferedWriter.write(String.join(";", cells));
            bufferedWriter.newLine();
            return newEntity.getId();
        } catch(IOException e) {
            throw new DaoException(e);
        } finally {
            try { bufferedWriter.close(); } catch(Exception e) {}
            try { outputStreamWriter.close(); } catch(Exception e) {}
            try { fileOutputStream.close(); } catch(Exception e) {}
        }
    }

    protected abstract T convert(String[] cells) throws DaoException;

    protected abstract String[] convert(T entity);
}
