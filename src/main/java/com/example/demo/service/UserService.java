package com.example.demo.service;

import com.example.demo.domain.dto.AccountDTO;
import com.example.demo.domain.entity.Account;

import java.util.List;

public interface UserService {

    void createUser(Account account);

    void modifyUser(AccountDTO accountDto);

    List<Account> getUsers();

    AccountDTO getUser(Long id);

    void deleteUser(Long idx);

}
