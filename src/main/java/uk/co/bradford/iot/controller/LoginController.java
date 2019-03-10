package uk.co.bradford.iot.controller;

import javax.validation.Valid;

import uk.co.bradford.iot.model.User;
import uk.co.bradford.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin() {
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        switch (myRole) {
            case "ADMIN":
                return "redirect:/admin/panel";
            case "DOP":
                return "redirect:/dop";
            case "DOCS":
                return "redirect:/docs";
            case "DOAS":
                return "redirect:/doas";
            case "CW":
                return "redirect:/cw";
        }
        return "redirect:/error";
    }

    private ModelAndView emailValue(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("emailValue", user.getEmail());
        modelAndView.setViewName("emailValue");
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/panel", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.addObject("currentPage", "Admin Panel");
        modelAndView.setViewName("admin/panel");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/panel", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        modelAndView.addObject("currentPage", "Admin Panel");
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/panel");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been added successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("admin/panel");

        }
        return modelAndView;
    }


    @RequestMapping(value = "/dop", method = RequestMethod.GET)
    public ModelAndView dop() {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        modelAndView.addObject("currentPage", "Department of Place");
        modelAndView.setViewName("dop");
        return modelAndView;
    }

    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public ModelAndView docs() {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        modelAndView.addObject("currentPage", "Department of Children Services");
        modelAndView.setViewName("docs");
        return modelAndView;
    }

    @RequestMapping(value = "/doas", method = RequestMethod.GET)
    public ModelAndView doas() {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        modelAndView.addObject("currentPage", "Department of Adult Services");
        modelAndView.setViewName("doas");
        return modelAndView;
    }

    @RequestMapping(value = "/cw", method = RequestMethod.GET)
    public ModelAndView cw() {
        ModelAndView modelAndView = emailValue(new ModelAndView());
        modelAndView.addObject("currentPage", "Council Wide");
        modelAndView.setViewName("cw");
        return modelAndView;
    }

}
