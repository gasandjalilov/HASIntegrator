package uz.rdu.nexign.hasinterface.service.hanlder.authorization;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.rdu.nexign.hasinterface.exception.ServerException;
import uz.rdu.nexign.hasinterface.interfaces.OpenApiAuthorizationHelper;
import uz.rdu.nexign.hasinterface.model.DTO.OAPI.auth.CredentialAuthResponse;

import java.nio.charset.Charset;

@Service
public class OpenApiAuthorizationHelperImpl implements OpenApiAuthorizationHelper {

    private CredentialAuthResponse credentialAuthResponse;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OpenApiAuthorizationHelperImpl.class);

    @Autowired
    @Qualifier("openapi")
    WebClient webClient;

    private CredentialAuthResponse sessionId;

    @Value("${oapi.url.auth}")
    private String oapiUrl;

    @Value("${oapi.username}")
    private String login;

    @Value("${oapi.password}")
    private String password;

    @Override
    public CredentialAuthResponse getSessionId() {
        return sessionId;
    }

    @Scheduled(fixedDelay = 10*60*1000)
    private void generateKey(){
        try {
            sessionId = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(oapiUrl)
                            .queryParam("login", login)
                            .queryParam("password", password)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(CredentialAuthResponse.class)
                    .block();
            log.info("generateKey: {}", sessionId);
        }
        catch (Exception e){
            log.error("generateKey: {}", e.getMessage());
        }
    }


}
