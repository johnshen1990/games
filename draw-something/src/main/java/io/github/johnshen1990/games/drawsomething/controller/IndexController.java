package io.github.johnshen1990.games.drawsomething.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: zhun.shen
 * Date: 2017-05-16 10:15
 * Description:
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index1", method = RequestMethod.GET)
    public String index() {
        return "index1";
    }
}
