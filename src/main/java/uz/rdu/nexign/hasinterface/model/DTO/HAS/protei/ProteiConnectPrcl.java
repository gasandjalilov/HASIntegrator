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
public class ProteiConnectPrcl extends MainResponse {

    @XmlElement(name = "UCELL_MAKE_CHARGE_BY_PRCL")
    private MakeChargeByPrcl prclConnectResponse;

    public ProteiConnectPrcl(MainResponse response) {
        super(response);
    }
}
