package controller;

import dao.user.IUserDao;
import dao.user.User;
import dao.user.UserAlreadyExistException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class MVCController {

    @Inject
    IUserDao userDao;

    @Transactional
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        User currentUser = userDao.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestHeader(value="email") String email, @RequestHeader(value="password") String password) {

        ModelAndView model = new ModelAndView();

        try {
            userDao.createUser(email, password);
            model.addObject("msg", "You have been successfully registered! Please, login!");
            model.setViewName("login");
        } catch (UserAlreadyExistException e){
            model.addObject("error", "User with this email already exists!");
            model.setViewName("register");
        }

        return model;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {

        ModelAndView model = new ModelAndView();
        model.setViewName("profile");

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
            System.out.println(userDetail);
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;

    }

}
