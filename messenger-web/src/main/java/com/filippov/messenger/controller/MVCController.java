package com.filippov.messenger.controller;

import com.filippov.messenger.service.user.IUserService;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Controller
public class MVCController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("appPath", getApplicationPath());
        List<User> userList =
                userService.getUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        User currentUser = null;

        if (userList != null && !userList.isEmpty())
            currentUser = userList.get(0);

        if (currentUser == null) {
            model.addObject("userId", "");
        } else {
            model.addObject("userId", currentUser.getId());
        }

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        model.addObject("appPath", getApplicationPath());
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {

        ModelAndView model = new ModelAndView();
        model.setViewName("register");
        model.addObject("appPath", getApplicationPath());

        return model;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {

        ModelAndView model = new ModelAndView();
        model.setViewName("profile");
        model.addObject("appPath", getApplicationPath());

        List<User> userList =
                userService.getUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        User currentUser = null;

        if (userList != null && !userList.isEmpty())
            currentUser = userList.get(0);

        if (currentUser == null) {
            model.addObject("userId", "");
        } else {
            model.addObject("userId", currentUser.getId());
        }

        return model;
    }

    @Transactional
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ModelAndView contacts() {

        ModelAndView model = new ModelAndView();
        model.setViewName("contacts");
        model.addObject("appPath", getApplicationPath());

        List<User> userList =
                userService.getUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        User currentUser = null;

        if (userList != null && !userList.isEmpty())
            currentUser = userList.get(0);

        if (currentUser == null) {
            model.addObject("userId", "");
        } else {
            model.addObject("userId", currentUser.getId());
        }

        return model;
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView model = new ModelAndView();
        model.addObject("appPath", getApplicationPath());

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }

    private String getApplicationPath() {
        String result;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("conf.properties")) {
            Properties props = new Properties();
            props.load(inputStream);
            result = props.getProperty("application.path");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}
