package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.Entity.OrdersEntity;
import mvc.Entity.ProductEntity;
import mvc.repository.OrderDetailsRepository;
import mvc.repository.OrderRepository;
import mvc.repository.ProductRepository;
import mvc.session.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public String addSession(Model model, @PathVariable int id, HttpSession session,HttpServletRequest request){
  ProductEntity product=productRepository.findById(id).get();
  List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
  if (itemList==null||itemList.isEmpty()){
    itemList=new ArrayList<>();
    CartItem item = new CartItem();
    item.setProduct(product);
    item.setQuantity(1);
    itemList.add(item);
    }else {
    CartItem item = new CartItem();
    item.setProduct(product);

    // count quantity
    CartItem cartTemp = itemList.stream()
        .filter(p -> id == (p.getProduct().getId()))
        .findAny()
        .orElse(null);

    if(cartTemp!=null){
      itemList.get(itemList.indexOf(cartTemp)).setQuantity(cartTemp.getQuantity() + 1);
    }
    else {
      item.setQuantity(1);
      itemList.add(item);
    }
  }

  session.setAttribute("itemList",itemList);
  model.addAttribute("msg","Remove");
  model.addAttribute("itemList",itemList);
  return "order/cart";
  }


  @RequestMapping(value = "/cart",method = RequestMethod.GET)
  public String getCart(Model model, HttpServletRequest request) {
    List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
    model.addAttribute("msg","Remove");
    model.addAttribute("itemList",itemList);
    return "order/cart";
  }
  @RequestMapping(value = "remove/{id}",method = RequestMethod.GET)
  public String removeCart(Model model,HttpServletRequest request,@PathVariable int id) {
    List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
    if (itemList==null){
      itemList=new ArrayList<>();
      request.getSession().setAttribute("itemList",itemList);
    }
    CartItem item=itemList.stream()
        .filter(p ->id==(p.getProduct().getId()))
        .findAny()
        .orElse(null);
    itemList.remove(item);
    request.getSession().setAttribute("itemList",itemList);
    model.addAttribute("itemList",itemList);
    return "redirect:/cart";
  }
  @RequestMapping(value = "/checkout",method = RequestMethod.GET)
  public String checkOu1t(Model model){
    model.addAttribute("OrdersEntity", new OrdersEntity());
    return "order/checkout";
  }
  @RequestMapping(value = "/continue",method = RequestMethod.POST)
  public String checkOut(Model model, OrdersEntity orders){
    orders.setCustomerName(orders.getCustomerName());
    orders.setCustomerAddress(orders.getCustomerAddress());
    orderRepository.save(orders);
    List<OrdersEntity>ordersList=(List<OrdersEntity>)orderRepository.findAll();
    model.addAttribute("ordersList",ordersList);
    return "order/order";
  }

//  @RequestMapping(value = "add/delete/{id}",method = RequestMethod.GET)
//  public String delete(@PathVariable int id){
//    orderRepository.deleteById(id);
//    return "redirect:/order";
//  }


}
