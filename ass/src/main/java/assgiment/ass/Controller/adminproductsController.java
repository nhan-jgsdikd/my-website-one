package assgiment.ass.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import assgiment.ass.Model.Product;
import assgiment.ass.DAO.ProductDAO;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class adminproductsController {

    @Autowired
    private ProductDAO productDAO;

    private static final String PHOTO_UPLOAD_DIR = "C:\\Java5\\ass\\src\\main\\resources\\static\\product-photos";

    @GetMapping("/adminproducts")
    public String listProducts(Model model) {
        List<Product> products = productDAO.findAll();
        products.forEach(p -> {
            if (p.getPhoto() == null || p.getPhoto().isEmpty()) {
                p.setPhoto("/images/default-product.jpg");
            }
        });
        model.addAttribute("products", products);
        return "Account/another";
    }

    @GetMapping("/admin/add-product")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "Account/productedit";
    }

    @GetMapping("/admin/edit-product/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "Account/productedit";
    }

    @PostMapping("/admin/save-product")
    public String saveProduct(@ModelAttribute("product") Product product,
                             @RequestParam("photoFile") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            if (file != null && !file.isEmpty()) {
                File uploadDir = new File(PHOTO_UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadDir, fileName));
                product.setPhoto("/product-photos/" + fileName);
            } else if (product.getPhoto() == null) {
                product.setPhoto("/images/default-product.jpg");
            }
            productDAO.save(product);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
            return "redirect:/adminproducts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "redirect:/adminproducts";
        }
        return "redirect:/adminproducts";
    }

    @PostMapping("/admin/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productDAO.deleteById(id);
        return "redirect:/adminproducts";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Lỗi hệ thống: " + ex.getMessage());
        return "redirect:/adminproducts";
    }
}