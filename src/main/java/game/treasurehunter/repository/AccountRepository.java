package game.treasurehunter.repository;

import game.treasurehunter.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long>{
}
