package pl.piomin.services.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.piomin.services.account.model.Account;
import pl.piomin.services.account.repository.AccountRepository;

@Configuration
public class AccountConfig {

    @Bean
    AccountRepository repository() {
        AccountRepository repository = new AccountRepository();
        repository.add(new Account("1234567890", 50000, 1L));
        repository.add(new Account("1234567891", 50000, 1L));
        repository.add(new Account("1234567892", 0, 1L));
        repository.add(new Account("1234567893", 50000, 2L));
        repository.add(new Account("1234567894", 0, 2L));
        repository.add(new Account("1234567895", 50000, 2L));
        repository.add(new Account("1234567896", 0, 3L));
        repository.add(new Account("1234567897", 50000, 3L));
        repository.add(new Account("1234567898", 50000, 3L));
        return repository;
    }

}
