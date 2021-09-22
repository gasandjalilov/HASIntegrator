package uz.rdu.nexign.hasinterface.model.DTO.HAS.eir;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@ToString
public class EirPassportData implements Serializable {

    @XmlElement(name = "IMSI")
    private String imsi;

    @XmlElement(name = "NAME")
    private String name;


    @XmlElement(name = "DOC_TYPE")
    private int type;

    @XmlElement(name = "DOC_S")
    private String series;

    @XmlElement(name = "DOC_N")
    private long number;

    @XmlElement(name = "RESIDENT")
    private int resident;

    @XmlElement(name = "JRTP_ID")
    private int jrpt;

    @XmlElement(name = "PINFL")
    private BigDecimal pinfl;
}
