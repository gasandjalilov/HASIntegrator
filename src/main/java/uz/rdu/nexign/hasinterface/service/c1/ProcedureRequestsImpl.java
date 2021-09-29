package uz.rdu.nexign.hasinterface.service.c1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.DefneDAO;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.Subscriber;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.SubscriberData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional("primaryTransactionManager")
@Service
@Slf4j
public class ProcedureRequestsImpl {


    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public Subscriber GetData(String msisdn) {
        EntityManager em = emf.createEntityManager();
        Subscriber subscriber = new Subscriber();
        try {
            StoredProcedureQuery str = em.createStoredProcedureQuery("client_cmpgn.pkg_cmpgn_utolov_app.get_subs_info")
                    .registerStoredProcedureParameter("p_msisdn", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_ucell_subs", Integer.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_state_id", Integer.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_primaryOffer_value", String.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_available_balance", String.class, ParameterMode.INOUT)
                    .registerStoredProcedureParameter("p_cur", void.class, ParameterMode.REF_CURSOR)
                    .setParameter("p_msisdn", msisdn);

            str.execute();

            subscriber.setMsisdn(msisdn);
            subscriber.setUcellSub((int) str.getOutputParameterValue("p_ucell_subs"));
            subscriber.setAvailBalance((String) str.getOutputParameterValue("p_available_balance"));
            subscriber.setPrimaryOffer((String) str.getOutputParameterValue("p_primaryOffer_value"));
            subscriber.setState((int) str.getOutputParameterValue("p_state_id"));


            List<Object[]> rows = str.getResultList();
            List<SubscriberData> result = new ArrayList<>(rows.size());
            for (Object[] row : rows) {
                result.add(new SubscriberData(
                        CheckNull(row[0]),
                        CheckNull(row[1]),
                        CheckNull(row[2]),
                        CheckNull(row[3]),
                        CheckNull(row[4]),
                        CheckNull(row[5]),
                        CheckNull(row[6]),
                        CheckNull(row[7]),
                        CheckNull(row[8])
                ));

            }
            subscriber.setData(result);
            em.clear();
            em.close();
        } catch (Exception e) {
            em.clear();
            em.close();
            e.printStackTrace();
            log.error("Exception: {}", e.getLocalizedMessage());
        }
        return subscriber;
    }


    private String CheckNull(Object str) {
        if (str != null) {
            return str.toString();
        } else {
            return "";
        }
    }


    public Optional<DefneDAO> getDefneData(String msisdn){
        EntityManager em = emf.createEntityManager();
        try {
            DefneDAO defneDAOS = (DefneDAO) em.createNativeQuery(String.format("select v.MSISDN, v.REMAINING_DEBT_AMOUNT, v.REMAINING_SERVICE_FEE, v.DEBT_DATE, v.DEBT_TYPE, v.UUID from client.v_defne_coinview v where v.msisdn='%s'", msisdn), DefneDAO.class).getSingleResult();
            em.clear();
            em.close();
            return Optional.ofNullable(defneDAOS);
        }
        catch (Exception e){
            em.clear();
            em.close();
            return Optional.ofNullable(null);
        }
    }

}
