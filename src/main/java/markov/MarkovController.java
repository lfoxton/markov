package markov;

import markov.model.MarkovText;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class MarkovController {

    @RequestMapping("/")
    public String markov() {
        return "markov";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/doTheMarkov")
    public String doTheMarkov(
            @RequestParam("file") MultipartFile file,
            @RequestParam("depth") int depth,
            @RequestParam("firstWord") String firstWord,
            RedirectAttributes redirectAttributes,
            Model model) {

        MarkovText markovText = new MarkovText();

        if (!file.isEmpty()) {
            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String text;
                while((text = br.readLine()) != null) {
                    markovText.addText(text);
                }

            }  catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "You failed to upload " + e.getMessage());
                return "redirect:error";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "You failed to upload because the file was empty");
            return "redirect:error";
        }

        model.addAttribute("sentence", markovText.generateSentence(firstWord, depth));

        return "doTheMarkov";
    }
}
