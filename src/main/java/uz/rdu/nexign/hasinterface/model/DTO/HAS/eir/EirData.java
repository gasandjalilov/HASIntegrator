package uz.rdu.nexign.hasinterface.model.DTO.HAS.eir;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.MainResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SELFCARE")
@ToString
public class EirData extends MainResponse implements Serializable  {

    @XmlElement(name = "UCELL_GET_PASSPORT_BY_MSISDN")
    private EirPassportData eirPassportData;

}
