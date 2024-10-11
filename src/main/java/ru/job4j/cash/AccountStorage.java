package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return Objects.isNull(accounts.putIfAbsent(account.id(), account));
    }

    public synchronized boolean update(Account account) {
        return Objects.nonNull(accounts.replace(account.id(), account));
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rev = false;
        var optionalFromAccount = getById(fromId);
        var optionalToAccount = getById(toId);
        if (optionalFromAccount.isPresent() && optionalToAccount.isPresent()) {
            var fromAccount = optionalFromAccount.get();
            var toAccount = optionalToAccount.get();
            var fromAmount = fromAccount.amount();
            if (fromAmount >= amount) {
                rev = update(new Account(fromAccount.id(), fromAmount - amount))
                        && update(new Account(toAccount.id(), toAccount.amount() + amount));
            }
        }
        return rev;
    }
}