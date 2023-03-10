
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UserController {

    private final RoleService roleService;

    private final UserService userService;
    @Autowired
    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping("user")
    public String pageForUser (Model model, Principal principal) {
        model.addAttribute("user",userService.getUserByLogin(principal.getName()));
        return "user";
    }
    @GetMapping("admin")
    public String index(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "admin";
    }
    @GetMapping("addUser")
    public String addUser(Model model/*, @Valid User user*/) {
        model.addAttribute("roles", roleService.getRoleList());
    model.addAttribute("user", new User());
    return "new";
    }
   @PostMapping("create")
    public  String create(@ModelAttribute("user") User user) {
    userService.add(user);
    return "redirect:/admin";
    }
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.getRoleList());
    model.addAttribute("user", userService.show(id));
    return "edit";
    }
    @PatchMapping("edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(id, user);
        return "redirect:/admin";
    }
    @PostMapping("del")
    public String delete(@RequestParam("id") Long id) {
       userService.delete(id);
        return "redirect:/admin";
    }
}

