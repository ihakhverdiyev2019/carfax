package az.elixir.vin.controller;


import az.elixir.vin.dto.OrderDetails;
import az.elixir.vin.entity.CommonEntity;
import az.elixir.vin.entity.OrderHistory;
import az.elixir.vin.entity.UserEntity;
import az.elixir.vin.repository.CommonRepository;
import az.elixir.vin.repository.OrderHistoryRepository;
import az.elixir.vin.repository.OrderRepository;
import az.elixir.vin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    @Autowired
private UserRepository userRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommonRepository commonRepository;


    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) throws SQLException, IOException {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("user");
        if(userEntity==null){
            redirectAttributes.addFlashAttribute("error6","Zəhmət olmasa daxil olun...");
            return "redirect:/";
        }
        CommonEntity commonEntity = commonRepository.findById(1).get();
        Clob photo = commonEntity.getPhotoBase64();
        Reader r = photo.getCharacterStream();
        StringBuffer buffer = new StringBuffer();
        int ch;
        while ((ch = r.read())!=-1) {
            buffer.append(""+(char)ch);
        }

        List<OrderHistory> orderHistories = orderHistoryRepository.findAllByCustomerId(userEntity.getId());

        List<OrderDetails> orderDetails = new ArrayList<>();
        double expense = 0;
        for(int i = 0;i<orderHistories.size();i++){
            OrderDetails orderDetails1 = new OrderDetails();
            orderDetails1.setDate(String.valueOf(orderHistories.get(i).getOrderDate()));
            orderDetails1.setPrice(orderHistories.get(i).getPrice());
            orderDetails1.setVinCode(orderRepository.findById(orderHistories.get(i).getOrderId()).getVinCode());
            expense =expense+orderHistories.get(i).getPrice();
            orderDetails.add(orderDetails1);
        }


        model.addAttribute("numberOrder",commonEntity.getNumberOfOrder());
        model.addAttribute("coverPhoto",buffer.toString());
        model.addAttribute("balance",   df2.format(userEntity.getBalance()));
        model.addAttribute("history",   orderDetails);
        model.addAttribute("expense",   df2.format(userEntity.getExpense()));
        model.addAttribute("user",      userEntity);
        return "dashboard";
    }






}
