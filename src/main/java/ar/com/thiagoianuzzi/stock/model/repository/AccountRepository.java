package ar.com.thiagoianuzzi.stock.model.repository;

import ar.com.thiagoianuzzi.stock.model.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getAccountByUsername(String username);
}
