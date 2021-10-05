package uz.rdu.nexign.hasinterface.model.DAO.secondaryDS;


import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Immutable
@NamedNativeQuery(
        name="postpaidQuery",
        query="select m.msisdn, s.account_no, s.subscr_no, c.full_name, c.ssn, p.pin_fl from z_msisdn m left join z_subscriber s on m.id=s.msisdn_id left join z_client_j c on s.client_id=c.id left join z_client_pinfl p on s.client_id=p.client_id where m.msisdn=? and s.state=2",
        resultClass=PostPaidSubscriberDAO.class
)
public class PostPaidSubscriberDAO implements Serializable {

    @Id
    @Column(name = "MSISDN")
    private String msisdn;

    @Column(name = "ACCOUNT_NO")
    private Long accountNo;

    @Column(name = "SUBSCR_NO")
    private Long subscriberNo;

    @Column(name = "FULL_NAME")
    private String name;

    @Column(name = "SSN")
    private Long ssn;

    @Column(name = "PIN_FL")
    private Long pinfl;
}
