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
    List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
    if (itemList==null || itemList.isEmpty()){
      itemList=new ArrayList<>();
      item.setProduct(product);
      item.setQuantity(item.getQuantity());
      itemList.add(item);

    }
    else {
        int total = 0;
        for ( int i = 0 ; i < itemList.size(); i ++){
          if (id==itemList.get(i).getProduct().getId()){
            for ( int k = 0 ; k < itemList.size(); k ++) {
              if(itemList.get(k).getProduct().getId() == id){
                total = itemList.get(k).getQuantity();
                break;
              }
            }
            item.setQuantity(total+1);
            item.setProduct(product);
            itemList.add(item);
            itemList.remove(i);
            break;
          }else{
          int ex = 0;
            for ( int j = 0 ; j < itemList.size(); j ++) {
              if(itemList.get(i).getProduct().getId() == itemList.get(j).getProduct().getId() && itemList.size() != 1){
                ex = 1;
              }
            }
            if(ex == 0){
              item.setProduct(product);
              item.setQuantity(item.getQuantity());
              itemList.add(item);
              break;
            }
          }
        }
    }
    session.setAttribute("itemList",itemList);
    model.addAttribute("itemList",itemList);
//    return "redirect:/";
    return "order/cart";
  }
//@RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
//  public String addSession(Model model,CartItem item, @PathVariable int id, HttpSession session,HttpServletRequest request){
//  ProductEntity product=productRepository.findById(id).get();
//  List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
//  if (itemList==null||itemList.isEmpty()){
//    itemList=new ArrayList<>();
//      for (int i=0;i<=itemList.size();i++){
//        if (itemList.get(i).getProduct().getId()==id){
//          item.setQuantity(item.getQuantity()+1);
//        }else {
//          item.setProduct(product);
//          item.setQuantity(item.getQuantity());
//          itemList.add(item);
//        }
//      }
//    }
//  session.setAttribute("itemList",itemList);
//  model.addAttribute("itemList",itemList);
//  return "order/cart";
//  }


  @RequestMapping(value = "/cart",method = RequestMethod.GET)
  public String getCart(Model model, HttpServletRequest request) {
    List<CartItem> itemList=(List<CartItem>)request.getSession().getAttribute("itemList");
    model.addAttribute("itemList",itemList);
    return "order/cart";
  }
//  @RequestMapping(value = "/add/remove/{id}")
//  public String removeCart(Model model,List<CartItem> itemList,HttpServletRequest request,@PathVariable int id) {
//    for (int i=0;i<=itemList.size();i++){
//      if (itemList.get(i).getProduct().getId()==id){
//        itemList.remove(i);
//      }
//    }
//    model.addAttribute("itemList",itemList);
//    return "order/cart";
//  }
}
