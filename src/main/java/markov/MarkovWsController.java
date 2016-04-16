package markov;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkovWsController {

    @RequestMapping("/markov_chain")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
