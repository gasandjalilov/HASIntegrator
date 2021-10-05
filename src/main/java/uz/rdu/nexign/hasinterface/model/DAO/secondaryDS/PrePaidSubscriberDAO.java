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
        name="prepaidQuery",
        query="select m.msisdn, s.account_no, s.subscr_no, c.last_name, c.first_name, " +
                "c.middle_name, c.address, c.passport_series, c.passport_number, c.document_number, p.pin_fl " +
                " from z_msisdn m " +
                "  left join z_subscriber s on m.id=s.msisdn_id " +
                "  left join z_client_p c on s.client_id=c.id " +
                "  left join z_client_pinfl p on s.client_id=p.client_id " +
                "where m.msisdn=? " +
                "and s.state=2",
        resultClass=PrePaidSubscriberDAO.class
)
public class PrePaidSubscriberDAO implements Serializable {

    @Id
    @Column(name = "MSISDN")
    private String msisdn;

    @Column(name = "ACCOUNT_NO")
    private Long accountNo;

    @Column(name = "SUBSCR_NO")
    private Long subscriberNo;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "MIDDLE_NAME")
    private String middlename;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PASSPORT_SERIES")
    private String passportSeries;

    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "PIN_FL")
    private String pinfl;
}
