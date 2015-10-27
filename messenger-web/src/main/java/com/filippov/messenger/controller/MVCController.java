package com.filippov.messenger.controller;

import com.filippov.messenger.api.user.IUserService;
import com.filippov.messenger.dao.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Controller
public class MVCController {

    @Inject
    IUserService userService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        ResponseEntity<List<User>> responseEntity = userService.getUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        User currentUser =  (responseEntity.getBody() == null || responseEntity.getBody().size() == 0) ? null : responseEntity.getBody().get(0);
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

        return model;

    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {

        ModelAndView model = new ModelAndView();
        model.setViewName("profile");

        return model;

    }

    @Transactional
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ModelAndView contacts() {

        ModelAndView model = new ModelAndView();
        model.setViewName("contacts");

        ResponseEntity<List<User>> responseEntity = userService.getUsers(SecurityContextHolder.getContext().getAuthentication().getName());
        User currentUser =  (responseEntity.getBody() == null || responseEntity.getBody().size() == 0) ? null : responseEntity.getBody().get(0);

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

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;

    }

}
