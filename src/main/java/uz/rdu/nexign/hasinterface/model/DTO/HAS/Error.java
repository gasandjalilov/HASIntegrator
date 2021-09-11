package uz.rdu.nexign.hasinterface.model.DTO.HAS;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Getter
@ToString
public class Error {

    @XmlAttribute(name = "SQLCODE")
    private int errorCode;

    @XmlAttribute(name = "SQLERRM")
    private String message;
}
