package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.Entity.ProductEntity;
import mvc.repository.OrderDetailsRepository;
import mvc.repository.OrderRepository;
import mvc.repository.ProductRepository;
import mvc.session.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class CartController {
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  OrderDetailsRepository orderDetailsRepository;
  @Autowired
  ProductRepository productRepository;
  @RequestMapping(value = "/index")
  public String index(){return "index";}


  @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
  public String addSession(Model model, CartItem item, @PathVariable int id, HttpSession session,HttpServletRequest request){
    ProductEntity product=productRepository.findById(id).get();
    //Cart cart = new Cart();
    List<CartItem> cartList=(List<CartItem>)request.getSession().getAttribute("cartList");
    if (cartList==null || cartList.isEmpty()){
      cartList=new ArrayList<>();
      item.setProduct(product);
      item.setQuantity(item.getQuantity());
      cartList.add(item);

    }
    else {
        int total = 0;
        for ( int i = 0 ; i < cartList.size(); i ++){
          if (id==cartList.get(i).getProduct().getId()){
            for ( int k = 0 ; k < cartList.size(); k ++) {
              if(cartList.get(k).getProduct().getId() == id){
                total = cartList.get(k).getQuantity();
                break;
              }
            }
            item.setQuantity(total+1);
            item.setProduct(product);
            cartList.add(item);
            cartList.remove(i);
            break;
          }else{
          int ex = 0;
            for ( int j = 0 ; j < cartList.size(); j ++) {
              if(cartList.get(i).getProduct().getId() == cartList.get(j).getProduct().getId() && cartList.size() != 1){
                ex = 1;
              }
            }
            if(ex == 0){
              item.setProduct(product);
              item.setQuantity(item.getQuantity());
              cartList.add(item);
              break;
            }
          }
        }
    }
    session.setAttribute("cartList",cartList);
    model.addAttribute("cartList",cartList);
//    return "redirect:/";
    return "order/cart";
  }
  public void getCart(CartItem cart){

  }
  @RequestMapping(value = "/cart",method = RequestMethod.GET)
  public String getCart(Model model, HttpServletRequest request) {
    List<CartItem> cartList=(List<CartItem>)request.getSession().getAttribute("cartList");
    model.addAttribute("cartList",cartList);
    return "order/cart";
  }
//  @RequestMapping(value = "/add/remove/{id}")
//  public String removeCart(List<Cart> cartList,HttpServletRequest request,@PathVariable int id) {
//    Cart cart = new Cart();
//    for (int i = 0; i <= cartList.size(); i++) {
//      if (id == cartList.get(i).getProduct().getId()) {
//        cart = cartList.get(i);
//        request.getSession().getAttribute("cart");
//      }
//    }
//    request.getSession().removeAttribute("cart");
//    return "order/cart";
//  }
}
