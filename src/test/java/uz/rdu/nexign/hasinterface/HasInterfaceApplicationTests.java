package uz.rdu.nexign.hasinterface;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uz.rdu.nexign.hasinterface.repository.mainDS.DefneDAORepository;
import uz.rdu.nexign.hasinterface.service.c1.ProcedureRequestsImpl;
import uz.rdu.nexign.hasinterface.service.coin.TerminationService;
import uz.rdu.nexign.hasinterface.service.hanlder.has.NexignService;

@AutoConfigureMockMvc
@SpringBootTest
class HasInterfaceApplicationTests {

    @Autowired
    ProcedureRequestsImpl procedureRequests;

    @Autowired
    NexignService nexignService;

    @Autowired
    DefneDAORepository defneDAORepository;


    @Autowired
    private MockMvc mockMvc;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HasInterfaceApplicationTests.class);

    @Test
    void contextLoads() {
        log.info("PROCESSING TESTS");
        log.info("PROCESSING TESTS");
        log.info("PROCESSING TESTS: {}", procedureRequests.getDefneData("998931717736"));

        //log.info("Data: {}", procedureRequests.getAllDefneData());
    }
}
