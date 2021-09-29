package uz.rdu.nexign.hasinterface.repository.secondaryDS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.cOne.SubscriberTerminationData;

import java.util.UUID;

@Repository
public interface SubscriberTerminationDataRepository extends JpaRepository<SubscriberTerminationData, UUID> {
}
