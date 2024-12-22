package com.activity3.user_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.activity3.user_management.model.User;
import com.activity3.user_management.repository.UserRepository;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "edit-user";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id);  // Make sure the ID remains the same to update the user
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
}
