package uz.rdu.nexign.hasinterface.model.rest.utolov;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtolovDTO implements Serializable {

    private int lang;

    private int subsriberId;

    private long msisdn;

    private String activationDate;

    private long clientId;

    private int ratingState;

}
