package com.booknest.wallet.controller;

import com.booknest.security.UserDetailsImpl;
import com.booknest.user.entity.User;
import com.booknest.user.repository.UserRepository;
import com.booknest.wallet.entity.Wallet;
import com.booknest.wallet.entity.WalletTransaction;
import com.booknest.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Wallet> getWallet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(walletService.getWallet(user));
    }

    @PostMapping("/add")
    public ResponseEntity<Wallet> addMoney(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam BigDecimal amount) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(walletService.addMoney(user, amount));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<WalletTransaction>> getTransactions(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        return ResponseEntity.ok(walletService.getTransactions(user));
    }
}
