package uz.rdu.nexign.hasinterface.model.DTO.HAS.protei;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


@NoArgsConstructor
@Getter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class UcellBalancesExtra implements Serializable {

    @XmlElement(name = "EXT_BALANCE")
    private ExtBalances extBalances;
}
