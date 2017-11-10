package game.treasurehunter.service.impl;

import game.treasurehunter.model.Account;
import game.treasurehunter.repository.AccountRepository;
import game.treasurehunter.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }
}
