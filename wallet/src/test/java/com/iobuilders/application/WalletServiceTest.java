package com.iobuilders.wallet.application;

import com.iobuilders.application.WalletServiceImpl;
import com.iobuilders.domain.WalletRepository;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletID;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import com.iobuilders.wallet.domain.WalletObjectMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class WalletServiceTest {

    private static final Long WALLET_ID = 1L;
    private static final int NEW_WALLET_QUANTITY = 120;
    @Mock
    private WalletRepository walletRepositoryMock;
    @InjectMocks
    private WalletServiceImpl sut;

    @Test
    void should_returnWalletId_when_createSucceed() {
        //Given
        Wallet wallet = WalletObjectMother.basic();
        WalletID expectedId = new WalletID(WALLET_ID);
        doReturn(expectedId).when(walletRepositoryMock).create(wallet);

        //When
        WalletID newId = sut.create(wallet);

        //Then
        assertThat(newId).isEqualTo(expectedId);
    }

    @Test
    void should_throwException_when_WalletNotFound() {
        //Given
        doThrow(new UserNotFoundException(WALLET_ID)).when(walletRepositoryMock).findById(WALLET_ID);

        //When/Then
        assertThrows(UserNotFoundException.class, () -> sut.delete(WALLET_ID));
    }

    @Test
    void should_succeed_when_deleteIsPossible() {
        //Given
        doReturn(WalletObjectMother.basic()).when(walletRepositoryMock).findById(WALLET_ID);

        //When
        sut.delete(WALLET_ID);

        //Then
        verify(walletRepositoryMock, times(1)).delete(WALLET_ID);
    }

    @Test
    void should_returnUpdatedWallet_when_updateSucceed() {
        //Given
        Wallet wallet = WalletObjectMother.basic();
        Wallet expectedWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(expectedWallet).when(walletRepositoryMock).update(WALLET_ID, wallet);

        //When
        Wallet receivedWallet = sut.update(WALLET_ID, wallet);

        //Then
        assertThat(expectedWallet.getQuantity()).isEqualTo(NEW_WALLET_QUANTITY);
    }

}