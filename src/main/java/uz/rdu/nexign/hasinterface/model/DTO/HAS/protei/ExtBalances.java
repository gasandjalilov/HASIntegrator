package uz.rdu.nexign.hasinterface.model.DTO.HAS.protei;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@ToString
public class ExtBalances implements Serializable {

    @XmlElement(name = "JRTP_ID")
    private int jrptId;

    @XmlElement(name = "BALANCE")
    private double balance;

    @XmlElement(name = "BRT_B")
    private double brtB;

    @XmlElement(name = "BRT_R")
    private double brtR;

    @XmlElement(name = "BRT_Z")
    private double brtZ;

    @XmlElement(name = "LC_STATUS_ID")
    private int statusId;

    @XmlElement(name = "LC_STATUS_NAME")
    private String statusName;

    @XmlElement(name = "RTPL_ID")
    private int rtplId;

    @XmlElement(name = "RTPL_NAME")
    private String rtplName;
}
