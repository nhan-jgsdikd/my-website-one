package assgiment.ass.Controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import assgiment.ass.DAO.ProductDAO;
import assgiment.ass.DAO.UserDAO;
import assgiment.ass.Model.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ServletContext context;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/action")
    public String account(Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<User> users = userDAO.findAll();
        model.addAttribute("user", user);
        return "Account/action";
    }

    @GetMapping("/edit-account")
    public String editAccount(Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "Account/edit-account";
    }

    @PostMapping("/update-account")
    public String updateAccount(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword,
            @RequestParam(required = false) MultipartFile file,
            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        if (password != null && !password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
                return "redirect:/edit-account";
            }
            user.setPassword(password);
        }

        user.setUsername(username);
        user.setEmail(email);

        String dir = "C:\\Java5\\ass\\src\\main\\resources\\static\\avt";
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(dir);
            String fileName = file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadDir, fileName));
                user.setImg("/avt/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Không thể tải lên file: " + e.getMessage());
                return "redirect:/edit-account";
            }
        }

        userDAO.save(user);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/action";
    }
}