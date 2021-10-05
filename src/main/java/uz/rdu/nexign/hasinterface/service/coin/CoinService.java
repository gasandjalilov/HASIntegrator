package uz.rdu.nexign.hasinterface.service.coin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.PostPaidSubscriberDAO;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.PrePaidSubscriberDAO;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.SubscriberTerminationData;
import uz.rdu.nexign.hasinterface.model.DTO.coin.CoinDbResponse;
import uz.rdu.nexign.hasinterface.repository.mainDS.DefneDAORepository;
import uz.rdu.nexign.hasinterface.repository.secondaryDS.SubscriberTerminationDataRepository;

import javax.persistence.*;
import java.util.Optional;

@Transactional("secondaryTransactionManager")
@Slf4j
@Service
public class CoinService {

    @Autowired
    @Qualifier("secondaryEntityManagerFactory")
    private EntityManagerFactory emf;

    @Autowired
    SubscriberTerminationDataRepository subscriberTerminationDataRepository;

    @Autowired
    DefneDAORepository defneDAORepository;

    /*

      procedure SubscriberChangeState(p_msisdn      in varchar2,
                                  p_DistState   in varchar2,
                                  p_SourceState in out varchar2,
                                  p_result      in out varchar2,
                                  p_response    in out varchar2,
                                  p_user_id     in number)

                            998939781400   15637
     */

    public CoinDbResponse terminateSubscriberFromCoinDB(String msisdn,String state) {
        EntityManager em = emf.createEntityManager();
        CoinDbResponse coinDbResponse = new CoinDbResponse();
        try {
            StoredProcedureQuery str = em.createStoredProcedureQuery("client.pkg_c1_subs_managment_api.SubscriberChangeState")
                    .registerStoredProcedureParameter("p_msisdn", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_DistState", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_user_id", Integer.class, ParameterMode.IN)

                    .registerStoredProcedureParameter("p_SourceState", String.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_response", String.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_result", String.class, ParameterMode.INOUT)
                    .setParameter("p_msisdn", msisdn)
                    .setParameter("p_user_id",15637)
                    .setParameter("p_DistState", state);

            str.execute();

            coinDbResponse.setResponse((String) str.getOutputParameterValue("p_response"));
            coinDbResponse.setMessage((String) str.getOutputParameterValue("p_result"));
            log.info("TERMINATION: {}", coinDbResponse);
            em.clear();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
            em.clear();
            em.close();
            coinDbResponse.setCode(-100);
        }
        return coinDbResponse;
    }

    public Optional<PostPaidSubscriberDAO> getPostPaidData(String msisdn){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("postpaidQuery",PostPaidSubscriberDAO.class);
            query.setParameter(1,msisdn);
            PostPaidSubscriberDAO postPaidSubscriberDAO = (PostPaidSubscriberDAO) query.getSingleResult();
            em.clear();
            em.close();
            return Optional.ofNullable(postPaidSubscriberDAO);
        }
        catch (Exception e){
            e.printStackTrace();
            em.clear();
            em.close();
            return Optional.ofNullable(null);
        }
    }


    public Optional<PrePaidSubscriberDAO> getPrePaidData(String msisdn){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNamedQuery("prepaidQuery",PrePaidSubscriberDAO.class);
            query.setParameter(1,msisdn);
            PrePaidSubscriberDAO prePaidSubscriberDAO = (PrePaidSubscriberDAO) query.getSingleResult();
            em.clear();
            em.close();
            return Optional.ofNullable(prePaidSubscriberDAO);
        }
        catch (Exception e){
            e.printStackTrace();
            em.clear();
            em.close();
            return Optional.ofNullable(null);
        }
    }


    public void saveTermination(SubscriberTerminationData data){
        subscriberTerminationDataRepository.save(data);
    }
}
