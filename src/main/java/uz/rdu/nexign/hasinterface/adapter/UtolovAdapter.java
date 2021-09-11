package uz.rdu.nexign.hasinterface.adapter;

import uz.rdu.nexign.hasinterface.model.DTO.HAS.MainResponse;
import uz.rdu.nexign.hasinterface.model.DTO.HAS.utolov.UtolovResponse;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UtolovAdapter extends XmlAdapter<UtolovResponse, MainResponse> {
    @Override
    public MainResponse unmarshal(UtolovResponse utolovResponse) throws Exception {
        return utolovResponse;
    }

    @Override
    public UtolovResponse marshal(MainResponse response) throws Exception {
        return new UtolovResponse(response);
    }
}
