package com.iobuilders.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserJPARepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByWalletId(String walletId);

}