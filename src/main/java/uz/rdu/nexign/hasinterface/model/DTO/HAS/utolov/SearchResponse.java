package uz.rdu.nexign.hasinterface.model.DTO.HAS.utolov;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.Error;

import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@Getter
@ToString
public class SearchResponse {


    @XmlElement(name = "ERROR")
    private Error error;

    @XmlElement(name = "SUBS_ID")
    private int subscriberId;

    @XmlElement(name = "CLNT_ID")
    private int clientId;

    @XmlElement(name = "RATE_PLAN_ID")
    private int ratingPlanId;

    @XmlElement(name = "SUBS_RT_STATE")
    private int subscriberRatingState;

    @XmlElement(name = "ACTIVATION_DATE")
    private String activationDate;

    @XmlElement(name = "SUBS_LANG")
    private int subscriberLang;

}
