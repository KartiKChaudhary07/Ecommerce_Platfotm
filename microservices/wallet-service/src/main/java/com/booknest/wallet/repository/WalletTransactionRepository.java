package com.booknest.wallet.repository;

import com.booknest.wallet.entity.Wallet;
import com.booknest.wallet.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWallet(Wallet wallet);
}
