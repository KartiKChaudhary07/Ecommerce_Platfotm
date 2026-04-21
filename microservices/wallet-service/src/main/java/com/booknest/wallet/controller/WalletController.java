package com.booknest.wallet.controller;

import com.booknest.wallet.entity.Wallet;
import com.booknest.wallet.entity.WalletTransaction;
import com.booknest.wallet.repository.WalletRepository;
import com.booknest.wallet.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<Wallet> getWallet(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(getOrCreateWallet(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Wallet> addMoney(@RequestHeader("X-User-Id") Long userId, @RequestParam BigDecimal amount) {
        Wallet wallet = getOrCreateWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        Wallet savedWallet = walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(savedWallet);
        transaction.setAmount(amount);
        transaction.setType("CREDIT");
        transaction.setDescription("Money added to wallet");
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        return ResponseEntity.ok(savedWallet);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<WalletTransaction>> getTransactions(@RequestHeader("X-User-Id") Long userId) {
        Wallet wallet = getOrCreateWallet(userId);
        return ResponseEntity.ok(transactionRepository.findByWallet(wallet));
    }

    private Wallet getOrCreateWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet();
                    newWallet.setUserId(userId);
                    newWallet.setBalance(BigDecimal.ZERO);
                    return walletRepository.save(newWallet);
                });
    }
}
