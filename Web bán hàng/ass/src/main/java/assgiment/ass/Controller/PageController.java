package assgiment.ass.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import assgiment.ass.Model.Product;
import assgiment.ass.Staff.CartItem;
import assgiment.ass.DAO.ProductDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    HttpSession session;
    
    @Autowired 
    ServletContext context;

    @Autowired
    ProductDAO ProductDAO;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = ProductDAO.findAll(); 
        Collections.shuffle(products);

        List<Product> limitedProducts = products.stream().limit(8).toList();
        List<Product> indexNewProducts = products.stream().limit(6).collect(Collectors.toList());
    
        model.addAttribute("indexstaff", limitedProducts); 
        model.addAttribute("indexnewproducts", indexNewProducts); 
        return "home/index"; 
    }

    @GetMapping("/products")
    public String link(Model model) {
        List<Product> products = ProductDAO.findAll();
        model.addAttribute("products", products); 
        model.addAttribute("pageTitle", "Trang chủ");
        return "home/link"; 
    }

    @GetMapping("/cart")
    public String Cart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
    
        double totalPrice = cart.stream()
                                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                                .sum();
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("pageTitle", "Giỏ hàng");
    
        return "home/cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId, 
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
    
        if (session.getAttribute("loggedInUser") == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
            return "redirect:/login";
        }
    
        Optional<Product> productOptional = ProductDAO.findById(productId);
        if (!productOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại");
            return "redirect:/products";
        }
    
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
    
        boolean productExists = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }
    
        if (!productExists) {
            CartItem newItem = new CartItem(productOptional.get(), 1);
            cart.add(newItem);
        }
        
        return "redirect:/products";
    }

    @GetMapping("/clear-cart")  
    
    public String clearCart() {
        session.setAttribute("cart", new ArrayList<>());
        return "redirect:/cart";
    }

    @PostMapping("/update-quantity")
    public String updateQuantity(@RequestParam("id") Long productId,
                                 @RequestParam("change") int change,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    int newQuantity = item.getQuantity() + change;
                    if (newQuantity > 0) {
                        item.setQuantity(newQuantity);
                    } else {
                        cart.remove(item);
                    }
                    break;
                }
            }
            session.setAttribute("cart", cart);
        }
    
        return "home/cart";
    }

    @GetMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam("id") Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getId() == productId);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }
        
        session.removeAttribute("cart");
        model.addAttribute("message", "Thanh toán thành công!");
        return "home/checkout";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        return "home/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "home/search";
    }


    @PostMapping("/submit-contact")
    public String submitContactForm(Model model) {
        return "redirect:/submit-contact";
    }
}
