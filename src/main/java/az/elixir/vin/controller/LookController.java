package az.elixir.vin.controller;

import az.elixir.vin.dto.OrderDetails;
import az.elixir.vin.entity.OrderEntity;
import az.elixir.vin.entity.OrderHistory;
import az.elixir.vin.entity.UserEntity;
import az.elixir.vin.repository.OrderHistoryRepository;
import az.elixir.vin.repository.OrderRepository;
import az.elixir.vin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class LookController {
    @Autowired
    private UserRepository userRepository;



    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/look/{vinCode}")
    public String look(@PathVariable String vinCode, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        System.out.println(vinCode);
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(userEntity==null){
            redirectAttributes.addFlashAttribute("error6","Zəhmət olmasa daxil olun...");
            return "redirect:/";
        }

        OrderEntity orderEntity = orderRepository.findByVinCode(vinCode);
        if(orderEntity == null){
            redirectAttributes.addFlashAttribute("error5","Vin Koda Baxmaq Üçün Sifariş Etməlisiniz...");
            redirectAttributes.addFlashAttribute("vinCode",vinCode);
            return "redirect:/home";

        }

        OrderHistory orderHistory = orderHistoryRepository.findByCustomerIdAndAndOrderId(userEntity.getId(),orderEntity.getId());
        System.out.println(orderHistory);

        if(orderHistory == null){
           redirectAttributes.addFlashAttribute("error5","VinCode Baxmaq Üçün Sifariş Etməlisiniz.");
            redirectAttributes.addFlashAttribute("vinCode",vinCode);
            return "redirect:/home";

        }






        return "dashboard";
    }


}
