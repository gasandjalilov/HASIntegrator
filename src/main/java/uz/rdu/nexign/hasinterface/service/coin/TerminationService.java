package uz.rdu.nexign.hasinterface.service.coin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.DefneDAO;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.Subscriber;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.cOne.SubscriberTerminationData;
import uz.rdu.nexign.hasinterface.model.DTO.coin.CoinDbResponse;
import uz.rdu.nexign.hasinterface.repository.mainDS.DefneDAORepository;
import uz.rdu.nexign.hasinterface.repository.secondaryDS.SubscriberTerminationDataRepository;

import javax.persistence.*;
import java.util.List;

@Transactional("secondaryTransactionManager")
@Slf4j
@Service
public class TerminationService {

    private EntityManagerFactory emf;

    @Autowired
    SubscriberTerminationDataRepository subscriberTerminationDataRepository;

    @Autowired
    DefneDAORepository defneDAORepository;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SubscriberTerminationData terminateSubscriber(String msisdn) {

        return new SubscriberTerminationData();
    }

    private CoinDbResponse terminateSubscriberFromCoinDB(String msisdn) {
        EntityManager em = emf.createEntityManager();
        CoinDbResponse coinDbResponse = new CoinDbResponse();
        try {
            StoredProcedureQuery str = em.createStoredProcedureQuery("client_cmpgn.pkg_bn_termination.terminate")
                    .registerStoredProcedureParameter("p_msisdn", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_result_code", String.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_message", String.class, ParameterMode.INOUT)
                    .setParameter("p_msisdn", msisdn);

            str.execute();

            coinDbResponse.setCode(Integer.valueOf((String) str.getOutputParameterValue("p_result_code")));
            coinDbResponse.setMessage((String) str.getOutputParameterValue("p_message"));
            em.clear();
            em.close();
        } catch (Exception e) {
            em.clear();
            em.close();
            coinDbResponse.setCode(-100);
        }
        return coinDbResponse;
    }


}
