package cn.ggdo.system.function.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class IndexController {

    private final Logger log = LogManager.getLogger(IndexController.class);

    @RequestMapping("/index")
    public ModelAndView toIndex(
            @RequestParam(value = "name", defaultValue = "")
            String name) {
        log.info("User have into toIndex() modth.");
        Map<String, Object> map = new HashMap<String, Object>();
        return new ModelAndView("/index", map);
    }
    
    @RequestMapping("/login")
    public ModelAndView toLogin() {
        log.info("User have into toLogin() modth.");
        Map<String, Object> map = new HashMap<String, Object>();
        return new ModelAndView("/login", map);
    }
    
    @RequestMapping("/main")
    public ModelAndView toMain() {
        log.info("User have into toMain() modth.");
        Map<String, Object> map = new HashMap<String, Object>();
        return new ModelAndView("/main", map);
    }
}
