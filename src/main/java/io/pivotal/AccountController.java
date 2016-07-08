package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/accounts");
    }

    @RequestMapping("/accounts")
    public ModelAndView list() {
        return new ModelAndView("listAccounts").addObject("accounts", accountRepository.findAll());
    }

}
