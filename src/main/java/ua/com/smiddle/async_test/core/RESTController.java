package ua.com.smiddle.async_test.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RESTController {
    @Autowired
    private SomeUtil util;

    @RequestMapping(value = "/state",
            method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    public Object state(HttpServletRequest HTTPrequest, HttpServletResponse HTTPresponce) throws IOException {
        return util.getState();
    }

    @RequestMapping(value = "/start",
            method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    public void start(HttpServletRequest HTTPrequest, HttpServletResponse HTTPresponce) throws IOException {
        util.task1();
        util.task1();
        util.task2();
        util.task3();
    }

    @RequestMapping(value = "/stop",
            method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    public void start(@RequestParam String id, HttpServletRequest HTTPrequest, HttpServletResponse HTTPresponce) throws IOException {
        util.stop(id);
    }
}
