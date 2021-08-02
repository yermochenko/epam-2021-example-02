package by.vsu.epam.domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
