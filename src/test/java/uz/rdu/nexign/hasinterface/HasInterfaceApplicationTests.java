package uz.rdu.nexign.hasinterface;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uz.rdu.nexign.hasinterface.service.hanlder.has.NexignService;

@AutoConfigureMockMvc
@SpringBootTest
class HasInterfaceApplicationTests {


	@Autowired
	NexignService nexignService;

	@Autowired
	private MockMvc mockMvc;

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(HasInterfaceApplicationTests.class);

	@Test
	void contextLoads() {
	}

	@Test
	void proceedTests(){


		log.info("ЭЛЬЕР МОЛОДЕЦ!!!!!");
		log.error("Ирина наркоман ");
		log.warn("Фу такой быть");
		log.debug("Кореец");
	}
}
