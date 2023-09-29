package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.Entity.ProductEntity;
import mvc.repository.OrderDetailsRepository;
import mvc.repository.OrderRepository;
import mvc.repository.ProductRepository;
import mvc.session.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class OrderController {
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  OrderDetailsRepository orderDetailsRepository;
  @Autowired
  ProductRepository productRepository;
  @RequestMapping(value = "/index")
  public String index(){return "index";}


  @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
  public String addSession(@PathVariable int id, HttpSession session){
    ProductEntity product=productRepository.findById(id).get();
    Cart cart=new Cart();
    cart.setProduct(product);
    session.setAttribute("cart",cart);
    return "redirect:/";
  }
  @RequestMapping(value = "/cart",method = RequestMethod.GET)
  public String getCart(Model model, HttpServletRequest request) {
    Cart cart=(Cart) request.getSession().getAttribute("cart");
    model.addAttribute("msg","Remove");
    model.addAttribute("cart",cart);
    return "order/cart";
  }
  @RequestMapping(value = "/remove")
  public String removeCart(Model model,HttpServletRequest request) {
    request.getSession().removeAttribute("product");
    return "order/cart";
  }
}
