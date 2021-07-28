package by.vsu.epam.domain;

import java.util.List;

public class Account extends Entity {
    private String name;
    private Long balance;
    private List<Transfer> history;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public List<Transfer> getHistory() {
        return history;
    }

    public void setHistory(List<Transfer> history) {
        this.history = history;
    }
}
