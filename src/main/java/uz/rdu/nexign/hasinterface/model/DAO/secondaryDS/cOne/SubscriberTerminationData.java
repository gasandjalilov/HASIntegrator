package uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.cOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "SUBSCRIBER_MNP_TRMNTN")
@Entity
public class SubscriberTerminationData implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    @GeneratedValue
    private UUID id;

    private String msisdn;

    private int code;

    private String message;

    @CreatedDate
    private Date createDate;

}
