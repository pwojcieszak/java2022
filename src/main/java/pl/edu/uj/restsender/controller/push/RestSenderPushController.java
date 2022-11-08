package pl.edu.uj.restsender.controller.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.uj.restsender.service.RestSenderService;

@RestController
class RestSenderPushController {

    @Autowired
    RestSenderService restSenderService;


    @RequestMapping("/push")
    public @ResponseBody
    String getPush(@RequestParam(value = "address") String address, @RequestParam(value = "title") String title,
                    @RequestParam(value = "message") String body) {
        return restSenderService.sendPush(address, title, body);
    }
}