package az.elixir.vin.controller;

import az.elixir.vin.dto.ErrorMessage;
import az.elixir.vin.dto.OrderDetails;
import az.elixir.vin.entity.OrderEntity;
import az.elixir.vin.entity.OrderHistory;
import az.elixir.vin.entity.UserEntity;
import az.elixir.vin.repository.OrderHistoryRepository;
import az.elixir.vin.repository.OrderRepository;
import az.elixir.vin.repository.UserRepository;
import az.elixir.vin.utils.SendRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class CheckController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;



    @PostMapping("/check")
    public String check(@Validated @ModelAttribute("vinCode") String vinCode,

                        RedirectAttributes redirectAttributes, HttpSession httpSession) {

        SendRequest sendRequest = new SendRequest();
        System.out.println(vinCode);

        OrderEntity orderEntity = orderRepository.findByVinCode(vinCode);
        OrderHistory orderHistory =null;
        int response =0;


        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(orderEntity==null){
            response = sendRequest.check("/carfax/check?vincode=" + vinCode + "&api_key=3aef82da-d3b6-4f75-844d-e86890b88787");

        }else {
            orderHistory = orderHistoryRepository.findByCustomerIdAndAndOrderId(userEntity.getId(), orderEntity.getId());

        }



        redirectAttributes.addFlashAttribute("vinCode", vinCode);

        if (orderHistory!=null){
            redirectAttributes.addFlashAttribute("error", "Siz Artiq Bu Vin Kodun Reportunu Əldə Etmisiniz!");

        }else if (response==200){
            redirectAttributes.addFlashAttribute("error1", "Nəticə Tapıldı");
        }else if (response==422){

            redirectAttributes.addFlashAttribute("error2", "Nəticə Tapılmadı");
        }else {
            redirectAttributes.addFlashAttribute("error3", "Texniki Xəta");

        }









        return "redirect:/home";
    }








}
