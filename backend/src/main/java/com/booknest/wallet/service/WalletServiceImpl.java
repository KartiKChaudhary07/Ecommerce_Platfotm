package com.booknest.wallet.service;

import com.booknest.user.entity.User;
import com.booknest.wallet.entity.Wallet;
import com.booknest.wallet.entity.WalletTransaction;
import com.booknest.wallet.repository.WalletRepository;
import com.booknest.wallet.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Override
    public Wallet getWallet(User user) {
        return walletRepository.findByUser(user)
                .orElseGet(() -> {
                    Wallet wallet = new Wallet();
                    wallet.setUser(user);
                    wallet.setBalance(BigDecimal.ZERO);
                    return walletRepository.save(wallet);
                });
    }

    @Override
    @Transactional
    public Wallet addMoney(User user, BigDecimal amount) {
        Wallet wallet = getWallet(user);
        wallet.setBalance(wallet.getBalance().add(amount));
        
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType("CREDIT");
        transaction.setDescription("Added money to wallet");
        transaction.setTransactionDate(LocalDateTime.now());
        
        transactionRepository.save(transaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoney(User user, BigDecimal amount, String description) {
        Wallet wallet = getWallet(user);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }
        
        wallet.setBalance(wallet.getBalance().subtract(amount));
        
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType("DEBIT");
        transaction.setDescription(description);
        transaction.setTransactionDate(LocalDateTime.now());
        
        transactionRepository.save(transaction);
        return walletRepository.save(wallet);
    }

    @Override
    public List<WalletTransaction> getTransactions(User user) {
        Wallet wallet = getWallet(user);
        return transactionRepository.findByWalletOrderByTransactionDateDesc(wallet);
    }
}
