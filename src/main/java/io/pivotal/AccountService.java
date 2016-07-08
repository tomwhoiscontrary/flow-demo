package io.pivotal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final Random random;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.random = new Random();
    }

    public void createAccount(Account account) {
        account.setNumber(random.nextInt(100000000));
        account.setSortCode(account.getType().getSortCode());
        LOGGER.info("creating account: {}", account);
        accountRepository.save(account);
    }

}
