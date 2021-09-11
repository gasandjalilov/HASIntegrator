package uz.rdu.nexign.hasinterface.interfaces;

import reactor.core.publisher.Mono;
import uz.rdu.nexign.hasinterface.model.rest.RestResponse;


public interface ESBMainController {

    Mono<RestResponse> getUtolovData(String msisdn);

    Mono<RestResponse> getProteiSearchResponse(String msisdn);

    Mono<RestResponse> getProteiPrclPriceResponse(String msisdn,int prcl);

    Mono<RestResponse> setProteiPrclResponse(String msisdn, int prcl);
}
