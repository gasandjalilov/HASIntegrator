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
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class PrclPrice implements Serializable{

    @XmlElement(name = "PRICE")
    private double price;

    @XmlElement(name = "VAT")
    private double vat;
}
