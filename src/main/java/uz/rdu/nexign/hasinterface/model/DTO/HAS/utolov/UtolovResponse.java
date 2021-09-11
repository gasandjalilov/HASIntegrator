package uz.rdu.nexign.hasinterface.model.DTO.HAS.utolov;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.rdu.nexign.hasinterface.adapter.UtolovAdapter;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.MainResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@NoArgsConstructor
@Getter
@ToString
@XmlJavaTypeAdapter(UtolovAdapter.class)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SELFCARE")
public class UtolovResponse extends MainResponse {

    @XmlElement(name = "SEARCH_FOR_UTULOV")
    private SearchResponse response;

    public UtolovResponse(MainResponse response) {
        super(response);
    }
}
