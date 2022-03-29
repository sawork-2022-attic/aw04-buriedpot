package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PosController {

    private PosService posService;

    private Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

/*    public Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }*/

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        System.out.println("setCart: "+ cart);
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        posService.add(cart, pid, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "index";
    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "index";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        System.out.println("delete");
        posService.delete(cart, id);
        return "redirect:/index";
    }

    @GetMapping("/sub/{id}")
    public String sub(@PathVariable("id") String id, Model model) {
        System.out.println("sub");
        posService.add(cart, id, -1);
        return "redirect:/index";
    }

    @GetMapping("/charge")
    public String charge(Model model) {
        System.out.println("charge");
        posService.empty(cart);
        return "redirect:/index";
    }
    @GetMapping("/cancel")
    public String cancel(Model model) {
        System.out.println("cancel");
        posService.empty(cart);
        return "redirect:/index";
    }
}
