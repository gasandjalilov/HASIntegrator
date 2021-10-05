package uz.rdu.nexign.hasinterface.model.DTO.coin;

import lombok.*;

import java.io.Serializable;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDbResponse implements Serializable {

    private int code;

    private String message;

    private String response;
}
