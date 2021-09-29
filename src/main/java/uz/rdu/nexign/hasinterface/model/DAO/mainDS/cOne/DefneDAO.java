package uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Immutable
@Entity
@Table(name = "V_DEFNE_COINVIEW", schema = "CLIENT", catalog="CLIENT")
public class DefneDAO {

    @Id
    @Column(name = "UUID")
    private UUID id;

    @Column(name = "MSISDN")
    private String msisdn;

    @Column(name = "REMAINING_DEBT_AMOUNT")
    private String remainingDebtAmount;

    @Column(name = "REMAINING_SERVICE_FEE")
    private String remaingServiceFee;

    @Column(name = "DEBT_DATE")
    private Date date;

    @Column(name = "DEBT_TYPE")
    private int type;
}
