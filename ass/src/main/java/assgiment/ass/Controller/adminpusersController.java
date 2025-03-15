package assgiment.ass.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import assgiment.ass.Model.User;
import assgiment.ass.DAO.UserDAO;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class adminpusersController {

    @Autowired
    private UserDAO userDAO;

    private static final String AVATAR_UPLOAD_DIR = "C:\\Java5\\ass\\src\\main\\resources\\static\\avt";

    @RequestMapping("/adminusers")
    public String listUsers(Model model) {
        List<User> users = userDAO.findByRole("USER");
        model.addAttribute("users", users);
        return "Account/disabled";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "Account/userformnew";
    }

    @PostMapping("/newsave")
    public String newSaveUser(@ModelAttribute("user") User userForm,
                             @RequestParam(value = "avatarFile", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            User newUser = new User();
            if (userForm.getPassword() == null || userForm.getPassword().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu là bắt buộc khi tạo mới");
                return "redirect:/newuser";
            }
            newUser.setUsername(userForm.getUsername());
            newUser.setEmail(userForm.getEmail());
            newUser.setPassword(userForm.getPassword());
            newUser.setRole("USER");
            if (file != null && !file.isEmpty()) {
                File uploadDir = new File(AVATAR_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadDir, fileName));
                    newUser.setImg("/avt/" + fileName);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
                    return "redirect:/newuser";
                }
            } else {
                newUser.setImg("/images/default-avatar.jpg");
            }
            userDAO.save(newUser);
            redirectAttributes.addFlashAttribute("success", "Tạo người dùng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
            return "redirect:/newuser";
        }
        return "redirect:/adminusers";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User userForm,
                           @RequestParam(value = "avatarFile", required = false) MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        try {
            User userToSave;
            if (userForm.getId() == 0 || userForm.getId() == 0) {
                userToSave = new User();
                if (userForm.getPassword() == null || userForm.getPassword().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Mật khẩu là bắt buộc khi tạo mới");
                    return "redirect:/edit-account";
                }
                if (file == null || file.isEmpty()) {
                    userToSave.setImg("/images/default-avatar.jpg");
                }
            } else {
                userToSave = userDAO.findById(userForm.getId())
                                    .orElseThrow(() -> new RuntimeException("User không tồn tại"));
                if (userForm.getPassword() != null && !userForm.getPassword().isEmpty()) {
                    userToSave.setPassword(userForm.getPassword());
                }
            }
            userToSave.setUsername(userForm.getUsername());
            userToSave.setEmail(userForm.getEmail());
            if (file != null && !file.isEmpty()) {
                File uploadDir = new File(AVATAR_UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadDir, fileName));
                    userToSave.setImg("/avt/" + fileName);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh: " + e.getMessage());
                    return "redirect:/edit-account";
                }
            }
            userDAO.save(userToSave);
            redirectAttributes.addFlashAttribute("success", "Lưu user thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
            return "redirect:/edit-account";
        }
        return "redirect:/adminusers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userDAO.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "Account/userform";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userDAO.deleteById(id);
        return "redirect:/adminusers";
    }
}