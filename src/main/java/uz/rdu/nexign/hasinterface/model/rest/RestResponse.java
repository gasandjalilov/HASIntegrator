package uz.rdu.nexign.hasinterface.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> implements Serializable {

    private T data;

    private Error error;

    public RestResponse(T data) {
        this.data = data;
    }
}
