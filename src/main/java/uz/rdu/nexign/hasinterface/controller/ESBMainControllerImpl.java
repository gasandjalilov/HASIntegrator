package uz.rdu.nexign.hasinterface.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uz.rdu.nexign.hasinterface.interfaces.ESBMainController;
import uz.rdu.nexign.hasinterface.model.rest.RestResponse;
import uz.rdu.nexign.hasinterface.service.hanlder.has.NexignService;

@Slf4j
@RestController
@RequestMapping("/")
public class ESBMainControllerImpl implements ESBMainController {

    @Autowired
    NexignService nexignService;

    @ApiOperation(value = "Utolov", notes = "Returns U-tolov Data", tags = { "u-tolov" })
    @GetMapping(value = "utolov" ,params = { "msisdn"})
    @Override
    public Mono<RestResponse> getUtolovData(String msisdn) {
        return nexignService.getUtolovData(msisdn);
    }

    @ApiOperation(value = "Protei Search", notes = "Returns Protei Data", tags = { "protei" })
    @GetMapping(value = "protei_balances" ,params = { "msisdn"})
    @Override
    public Mono<RestResponse> getProteiSearchResponse(String msisdn) {
        return nexignService.getProteiSearchResponse(msisdn);
    }

    @ApiOperation(value = "Protei Get Prcl", notes = "Returns Protei Data", tags = { "protei" })
    @GetMapping(value = "protei_prcl_price" ,params = { "msisdn", "prcl"})
    @Override
    public Mono<RestResponse> getProteiPrclPriceResponse(String msisdn, int prcl) {
        return nexignService.getProteiPrclPriceResponse(msisdn, prcl);
    }

    @ApiOperation(value = "Protei Set Prcl", notes = "Returns Protei Data", tags = { "protei" })
    @GetMapping(value = "protei_prcl_set" ,params = { "msisdn", "prcl"})
    @Override
    public Mono<RestResponse> setProteiPrclResponse(String msisdn, int prcl){
        return nexignService.setProteiPrclResponse(msisdn, prcl);
    }

    @ApiOperation(value = "Get Passport Data by MSISDN", notes = "Returns Passport Data", tags = { "subscriber" })
    @GetMapping(value = "passport" ,params = { "msisdn"})
    @Override
    public Mono<RestResponse> getPassportData(String msisdn){
        return nexignService.getPassportData(msisdn);
    }
}
