package by.vsu.epam.service;

public class AccountNotExistsException extends ServiceException {
    private Long accountId;

    public AccountNotExistsException(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
