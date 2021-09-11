package uz.rdu.nexign.hasinterface.model.DTO.HAS;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Getter
@ToString
@XmlRootElement(name = "CHECK_CUSTOM_ACCESS")
public class CheckCustomAccess {

    @XmlElement(name = "SLRN_ID")
    private String slrnId;
}
