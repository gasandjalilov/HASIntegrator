package uz.rdu.nexign.hasinterface.model.DTO.HAS.protei;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.MainResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Getter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SELFCARE")
public class ProteiGetPrclPrice extends MainResponse {

    @XmlElement(name = "UCELL_GET_PRCL_PRICE")
    private PrclPrice prclPrice;

    public ProteiGetPrclPrice(MainResponse response) {
        super(response);
    }

}
