package uz.rdu.nexign.hasinterface.model.DTO.HAS.protei;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@ToString
public class MakeChargeByPrcl implements Serializable {

    @XmlElement(name = "SUCCESS")
    private int success;

}
