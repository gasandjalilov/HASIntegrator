package uz.rdu.nexign.hasinterface.repository.mainDS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.DefneDAO;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional()
public interface DefneDAORepository extends JpaRepository<DefneDAO, UUID> {
}
