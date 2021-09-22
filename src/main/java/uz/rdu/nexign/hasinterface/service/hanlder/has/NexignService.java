package uz.rdu.nexign.hasinterface.service.hanlder.has;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.argument.StructuredArgument;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.rdu.nexign.hasinterface.exception.NoHasDataFoundException;
import uz.rdu.nexign.hasinterface.exception.ServerException;
import uz.rdu.nexign.hasinterface.interfaces.NexignRestService;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.eir.EirData;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiConnectPrcl;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiGetPrclPrice;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.protei.ProteiSearchResponse;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.utolov.UtolovResponse;
import uz.rdu.nexign.hasinterface.model.rest.RestResponse;
import uz.rdu.nexign.hasinterface.model.rest.utolov.UtolovDTO;

import java.nio.charset.Charset;
import java.util.Map;

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

    @Value("${url.eir}")
    private String eir_url;

    @Value("${url.eir_username}")
    private String eir_username;

    @Value("${url.eir_password}")
    private String eir_password;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(NexignService.class);

    @Autowired
    @Qualifier("has")
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
            log.info("getUtolovData: {}, request msisdn: {}", getResponse(dto), getMsisdn(msisdn));
            return Mono.just(new RestResponse(dto));
        }
        catch (Exception e){
            log.error("getUtolovData: {}, request msisdn: {}", getError(e.getMessage()), getMsisdn(msisdn));
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
            log.info("getProteiSearchResponse: {}, request msisdn: {}", getResponse(proteiSearchResponse), getMsisdn(msisdn));
            return Mono.just(new RestResponse(proteiSearchResponse.getUcellBalancesExtra().getExtBalances()));
        }
        catch (Exception e){
            log.error("getProteiSearchResponse: {}, request msisdn: {}", getError(e.getMessage()), getMsisdn(msisdn));
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
            log.info("getProteiPrclPriceResponse: {}, request msisdn: {}", getResponse(proteiGetPrclPrice), getMsisdn(msisdn));
            return Mono.just(new RestResponse(proteiGetPrclPrice.getPrclPrice()));
        }
        catch (Exception e){
            log.error("getProteiPrclPriceResponse: {}, request msisdn: {}", getError(e.getMessage()), getMsisdn(msisdn));
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
            log.info("setProteiPrclResponse: {}, request msisdn: {}", getResponse(proteiConnectPrcl), getMsisdn(msisdn));
            return Mono.just(new RestResponse(proteiConnectPrcl.getPrclConnectResponse()));
        }
        catch (Exception e){
            log.error("setProteiPrclResponse: {}, request msisdn: {}", getError(e.getMessage()), getMsisdn(msisdn));
            throw new ServerException(e.getLocalizedMessage());
        }


    }

    @Override
    public Mono<RestResponse> getPassportData(String msisdn) {
        try {
            EirData passportData = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(eir_url)
                            .queryParam("LOGIN", eir_username)
                            .queryParam("PASSWORD", eir_password)
                            .queryParam("CHANNEL", "EIRATORCH")
                            .queryParam("SUBSCRIBER_MSISDN", msisdn)
                            .build())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .accept(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
                    .acceptCharset(Charset.forName("utf-8"))
                    .retrieve()
                    .bodyToMono(EirData.class)
                    .block();
            log.info("getPassportData: {}, request msisdn: {}", getResponse(passportData), getMsisdn(msisdn));
            return Mono.just(new RestResponse(passportData.getEirPassportData()));
        }
        catch (Exception e){
            log.error("getPassportData: {}, request msisdn: {}", getError(e.getMessage()), getMsisdn(msisdn));
            throw new ServerException(e.getLocalizedMessage());
        }

    }



    private StructuredArgument getMsisdn(String msisdn){
        return StructuredArguments.value("MSISDN", msisdn);
    }

    private StructuredArgument getError(String message){
        return StructuredArguments.value("ERROR",message);
    }

    public StructuredArgument getResponse(Object object){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.convertValue(object, Map.class);
            return StructuredArguments.e(map);
        }
        catch (IllegalArgumentException processingException){
            return StructuredArguments.value("SERVER_ERROR",processingException.getMessage());
        }
    }

}
