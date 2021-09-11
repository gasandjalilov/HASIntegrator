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
public class ClntAttrForContext {

    @XmlElement(name = "ACCOUNT")
    private int account;
}
