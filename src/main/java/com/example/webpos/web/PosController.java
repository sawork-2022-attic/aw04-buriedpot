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

    @Autowired
    private HttpSession session;

    private void saveCart(Cart cart) {
        session.setAttribute("cart", cart);
    }

    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        System.out.println("Controller: getCart: " + cart);
        if (cart == null) {
            System.out.println("cart == null");
            cart = new Cart();
            this.saveCart(cart);
        }
        return cart;
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        session.setAttribute("cart", getCart());
        System.out.println("setCart: "+ getCart());
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        posService.add(getCart(), pid, 1);
        session.setAttribute("cart", getCart());
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        System.out.println("delete");
        posService.delete(getCart(), id);
        session.setAttribute("cart", getCart());
        return "redirect:/index";
    }

    @GetMapping("/sub/{id}")
    public String sub(@PathVariable("id") String id, Model model) {
        System.out.println("sub");
        posService.add(getCart(), id, -1);
        session.setAttribute("cart", getCart());
        return "redirect:/index";
    }

    @GetMapping("/charge")
    public String charge(Model model) {
        System.out.println("charge");
        posService.empty(getCart());
        session.setAttribute("cart", getCart());
        return "redirect:/index";
    }
    @GetMapping("/cancel")
    public String cancel(Model model) {
        System.out.println("cancel");
        posService.empty(getCart());
        session.setAttribute("cart", getCart());
        return "redirect:/index";
    }
}
