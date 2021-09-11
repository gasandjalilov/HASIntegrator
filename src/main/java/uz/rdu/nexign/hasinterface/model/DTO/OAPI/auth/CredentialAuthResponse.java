package uz.rdu.nexign.hasinterface.model.DTO.OAPI.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@XmlRootElement(name = "SELFCARE")
public class CredentialAuthResponse implements Serializable {

    @XmlElement(name = "SESSION_ID")
    private String hostname;

}
