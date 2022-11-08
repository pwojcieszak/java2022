package pl.edu.uj.restsender.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.uj.restsender.service.RestSenderService;

@RestController
class RestSenderEmailController {

    @Autowired
    RestSenderService restSenderService;


    @RequestMapping("/email")
    public @ResponseBody
    String getEmail(@RequestParam(value = "address") String address, @RequestParam(value = "title") String title,
                    @RequestParam(value = "message") String body) {
        return restSenderService.sendEmail(address, title, body);
    }
}