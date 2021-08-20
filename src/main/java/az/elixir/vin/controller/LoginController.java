package az.elixir.vin.controller;

import az.elixir.vin.dto.LoginDTO;
import az.elixir.vin.dto.UserDTO;
import az.elixir.vin.entity.UserEntity;
import az.elixir.vin.repository.UserRepository;
import az.elixir.vin.utils.SHAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/")
    public String loginPage(Model model) {
        LoginDTO loginDTO = new LoginDTO();

        model.addAttribute("login", loginDTO);


        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
    httpSession.invalidate();


        return "redirect:/";
    }



    @PostMapping("/do-login")
    public String register(@Validated @ModelAttribute("login") LoginDTO loginDTO, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        SHAEncryption shaEncryption =new SHAEncryption();
        UserEntity userEntity = userRepository.findByEmailAndPassword(loginDTO.getEmail(),shaEncryption.get_SHA_512_SecurePassword(loginDTO.getPassword()));
        if(userEntity==null){
        redirectAttributes.addFlashAttribute("errorLogin", "İstifadəçi Tapılmadı.");

            return "redirect:/login";
        }
        httpSession.setAttribute("user",userEntity);







        return "redirect:/home";
    }
}
