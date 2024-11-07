package ar.com.thiagoianuzzi.stock.config;

import ar.com.thiagoianuzzi.stock.model.entity.Account;
import ar.com.thiagoianuzzi.stock.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsername(username);
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .build();
    }
}
