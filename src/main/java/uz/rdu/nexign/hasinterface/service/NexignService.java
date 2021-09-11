package uz.rdu.nexign.hasinterface.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.rdu.nexign.hasinterface.exception.NoHasDataFoundException;
import uz.rdu.nexign.hasinterface.exception.ServerException;
import uz.rdu.nexign.hasinterface.interfaces.NexignRestService;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiConnectPrcl;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiGetPrclPrice;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiSearchResponse;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.utolov.UtolovResponse;
import uz.rdu.nexign.hasinterface.model.rest.RestResponse;
import uz.rdu.nexign.hasinterface.model.rest.utolov.UtolovDTO;

import java.nio.charset.Charset;

@Service
public class NexignService implements NexignRestService {

    @Value("${url.utolov}")
    private String utolovPath;

    @Value("${url.username}")
    private String login;

    @Value("${url.password}")
    private String password;

    @Value("${url.protei_url_search}")
    private String protei_url_search;

    @Value("${url.protei_url_prcl_get}")
    private String protei_url_prcl_get;

    @Value("${url.protei_url_prcl_set}")
    private String protei_url_prcl_set;

    @Value("${url.username_protei}")
    private String username_protei;

    @Value("${url.password_protei}")
    private String password_protei;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NexignService.class);

    @Autowired
    WebClient webClient;

    @Override
    public Mono<RestResponse> getUtolovData(String msisdn) {
        try {
            UtolovResponse utolovResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(utolovPath)
                            .queryParam("LOGIN", login)
                            .queryParam("PASSWORD", password)
                            .queryParam("CHANNEL", "UTULOV")
                            .queryParam("P_MSISDN", msisdn)
                            .build()
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(UtolovResponse.class)
                    .block();
            if (utolovResponse.getResponse().getError().getErrorCode() == 100)
                throw new NoHasDataFoundException("Subscriber not found");
            UtolovDTO dto = new UtolovDTO();
            dto.setActivationDate(utolovResponse.getResponse().getActivationDate());
            dto.setClientId(utolovResponse.getResponse().getClientId());
            dto.setLang(utolovResponse.getResponse().getSubscriberLang());
            dto.setRatingState(utolovResponse.getResponse().getSubscriberRatingState());
            dto.setMsisdn(Long.parseLong(msisdn));
            dto.setSubsriberId(utolovResponse.getResponse().getSubscriberId());
            log.info("getUtolovData: {}, request msisdn: {}", dto, msisdn);
            return Mono.just(new RestResponse(dto));
        }
        catch (Exception e){
            log.error("getUtolovData: {}, request msisdn: {}", e.getMessage(), msisdn);
            throw new ServerException(e.getLocalizedMessage());
        }
    }

    @Override
    public Mono<RestResponse> getProteiSearchResponse(String msisdn) {
        try {
            ProteiSearchResponse proteiSearchResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(protei_url_search)
                            .queryParam("LOGIN", username_protei)
                            .queryParam("PASSWORD", password_protei)
                            .queryParam("CHANNEL", "PROTEI")
                            .queryParam("SUBSCRIBER_MSISDN", msisdn)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(ProteiSearchResponse.class)
                    .block();
            log.info("getProteiSearchResponse: {}, request msisdn: {}", proteiSearchResponse, msisdn);
            return Mono.just(new RestResponse(proteiSearchResponse.getUcellBalancesExtra().getExtBalances()));
        }
        catch (Exception e){
            log.error("getProteiSearchResponse: {}, request msisdn: {}", e.getMessage(), msisdn);
            throw new ServerException(e.getLocalizedMessage());
        }
    }

    @Override
    public Mono<RestResponse> getProteiPrclPriceResponse(String msisdn, int prcl) {
        try {
            ProteiGetPrclPrice proteiGetPrclPrice = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(protei_url_prcl_get)
                            .queryParam("LOGIN", username_protei)
                            .queryParam("PASSWORD", password_protei)
                            .queryParam("CHANNEL", "PROTEI")
                            .queryParam("SUBSCRIBER_MSISDN", msisdn)
                            .queryParam("P_PRCL_ID", String.valueOf(prcl))
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(ProteiGetPrclPrice.class)
                    .block();
            log.info("getProteiPrclPriceResponse: {}, request msisdn: {}", proteiGetPrclPrice, msisdn);
            return Mono.just(new RestResponse(proteiGetPrclPrice.getPrclPrice()));
        }
        catch (Exception e){
            log.error("getProteiPrclPriceResponse: {}, request msisdn: {}", e.getMessage(), msisdn);
            throw new ServerException(e.getLocalizedMessage());
        }
    }

    @Override
    public Mono<RestResponse> setProteiPrclResponse(String msisdn, int prcl) {
        try {
            ProteiConnectPrcl proteiConnectPrcl = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(protei_url_prcl_set)
                            .queryParam("LOGIN", username_protei)
                            .queryParam("PASSWORD", password_protei)
                            .queryParam("CHANNEL", "PROTEI")
                            .queryParam("SUBSCRIBER_MSISDN", msisdn)
                            .queryParam("PRCL_ID", String.valueOf(prcl))
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(ProteiConnectPrcl.class)
                    .block();
            log.info("setProteiPrclResponse: {}, request msisdn: {}", proteiConnectPrcl, msisdn);
            return Mono.just(new RestResponse(proteiConnectPrcl.getPrclConnectResponse()));
        }
        catch (Exception e){
            log.error("setProteiPrclResponse: {}, request msisdn: {}", e.getMessage(), msisdn);
            throw new ServerException(e.getLocalizedMessage());
        }


    }



}
