package uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberData implements Serializable {

    private String balanceName;

    private String balanceId;

    private String totalBalance;

    private String availableBalance;

    private String maxLimit;

    private String balanceType;

    private String totalBalanceSrc;

    private String availableBalanceSrc;

    private String expDate;

}
