package com.booknest.wallet.service;

import com.booknest.user.entity.User;
import com.booknest.wallet.entity.Wallet;
import com.booknest.wallet.entity.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    Wallet getWallet(User user);
    Wallet addMoney(User user, BigDecimal amount);
    Wallet deductMoney(User user, BigDecimal amount, String description);
    List<WalletTransaction> getTransactions(User user);
}
