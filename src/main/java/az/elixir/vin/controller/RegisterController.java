package az.elixir.vin.controller;

import az.elixir.vin.dto.OrderDetails;
import az.elixir.vin.dto.UserDTO;
import az.elixir.vin.entity.CommonEntity;
import az.elixir.vin.entity.OrderHistory;
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


@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/register")
    public String registerPage(Model model) {
        UserDTO userDTO = new UserDTO();

        model.addAttribute("user", userDTO);


        return "register";
    }



    @PostMapping("/do-register")
    public String register(@Validated @ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes) {
        SHAEncryption shaEncryption = new SHAEncryption();
        UserEntity userEntity = new UserEntity();
        userEntity.setExpense(0);
        userEntity.setBalance(0);
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPerPrice(8);
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setPassword(shaEncryption.get_SHA_512_SecurePassword(userDTO.getPassword()));
        userRepository.save(userEntity);

        redirectAttributes.addFlashAttribute("success","Uğurla Qeydiyyatdan Keçdiniz.");








        return "redirect:/";
    }
}
