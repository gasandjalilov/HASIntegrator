package uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscriber implements Serializable {

    private String msisdn;

    private int ucellSub;

    private int state;

    private String primaryOffer;

    private String availBalance;

    private List<SubscriberData> data;


}
