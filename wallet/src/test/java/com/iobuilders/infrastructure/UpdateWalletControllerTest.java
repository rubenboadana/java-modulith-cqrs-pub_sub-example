package com.iobuilders.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.domain.WalletObjectMother;
import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import com.iobuilders.infrastructure.controller.UpdateWalletController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UpdateWalletController.class)
@AutoConfigureMockMvc
class UpdateWalletControllerTest {

    private static final Long WALLET_ID = 1L;
    private static final int NEW_WALLET_QUANTITY = 120;

    @MockBean
    private WalletService walletServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_responseWalletNotFound_when_invalidWalletIdProvided() throws Exception {
        //Given
        doThrow(new WalletNotFoundException(WALLET_ID)).when(walletServiceMock).update(any(), any(Wallet.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(put("/wallets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Could not find the requested resource: Wallet with id 1")));

    }


    @Test
    void should_responseInternalError_when_internalErrorIsProduced() throws Exception {
        //Given
        doThrow(new RuntimeException()).when(walletServiceMock).update(any(), any(Wallet.class));

        //When/Then
        Wallet wallet = WalletObjectMother.basic();
        mockMvc.perform(put("/wallets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void should_returnHTTP200_when_updateWalletSucceed() throws Exception {
        //Given
        Wallet expectedWallet = WalletObjectMother.withQuantity(NEW_WALLET_QUANTITY);
        doReturn(expectedWallet).when(walletServiceMock).update(any(), any(Wallet.class));

        //When/Then
        mockMvc.perform(put("/wallets/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(expectedWallet)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity", is(NEW_WALLET_QUANTITY)));
    }

}
