package sample.cafekiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

/*    @Spy
    private MailSendClient mailSendClient;*/
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        //given
/*        MailSendClient mailSendClient = mock(MailSendClient.class);
        MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);*/

        /*MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);*/

        /*when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);*/

       /* doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());*/

        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        //when
        boolean result = mailService.sendMail("", "", "", "");

        //then
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
        assertThat(result).isTrue();
    }

}