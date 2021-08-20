package az.elixir.vin.controller;

import az.elixir.vin.dto.OrderDetails;
import az.elixir.vin.entity.CommonEntity;
import az.elixir.vin.entity.OrderEntity;
import az.elixir.vin.entity.OrderHistory;
import az.elixir.vin.entity.UserEntity;
import az.elixir.vin.repository.CommonRepository;
import az.elixir.vin.repository.OrderHistoryRepository;
import az.elixir.vin.repository.OrderRepository;
import az.elixir.vin.repository.UserRepository;
import az.elixir.vin.utils.SendRequest;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Controller
public class OrderController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommonRepository commonRepository;


    @GetMapping("/order/{vinCode}")
    public String home(@PathVariable String vinCode, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(userEntity==null){
            redirectAttributes.addFlashAttribute("error6","Zəhmət olmasa daxil olun...");
            return "redirect:/";
        }
        SendRequest sendRequest = new SendRequest();

        CommonEntity commonEntity = commonRepository.findById(1).get();


        System.out.println(vinCode);



        int response1 = sendRequest.check("/carfax/check?vincode="+vinCode+"&api_key=3aef82da-d3b6-4f75-844d-e86890b88787");

        redirectAttributes.addFlashAttribute("vinCode",vinCode);
        if(userEntity.getBalance()-userEntity.getPerPrice()<0){
            redirectAttributes.addFlashAttribute("error4", "Balansda Kifayət Qədər Məbləğ Yoxdur");

        }
        else if (response1==200){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setChannel("1");
            orderEntity.setVinCode(vinCode);
            orderEntity.setCreationDate(LocalDateTime.now().format(formatter));
            orderEntity.setTranId("10002");
            orderEntity.setData(vinCode+".pdf");
            orderRepository.save(orderEntity);

            OrderHistory orderHistory =new OrderHistory();
            orderHistory.setCustomerId(userEntity.getId());
            orderHistory.setOrderId(orderEntity.getId());
            orderHistory.setPrice(userEntity.getPerPrice());
            orderHistory.setOrderDate(LocalDateTime.now().format(formatter));
            orderHistoryRepository.save(orderHistory);


            userEntity.setBalance(userEntity.getBalance()-userEntity.getPerPrice());
            userEntity.setExpense(userEntity.getExpense()+userEntity.getPerPrice());
            userRepository.save(userEntity);
            commonEntity.setNumberOfOrder(commonEntity.getNumberOfOrder()+1);
            commonRepository.save(commonEntity);

            String text = "";






        }else if (response1==422){

            redirectAttributes.addFlashAttribute("error2", "Nəticə Tapılmadı");
        }else {
            redirectAttributes.addFlashAttribute("error3", "Texniki Xəta");

        }





        return "redirect:/home";
    }
}
