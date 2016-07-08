package io.pivotal;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Repository
public class AccountRepository {

    private Map<Integer, Account> accounts = new TreeMap<>();

    public void save(Account account) {
        accounts.put(account.getNumber(), account);
    }

    public Account findOne(int number) {
        return accounts.get(number);
    }

    public Iterable<Account> findAll() {
        return accounts.values();
    }

}
