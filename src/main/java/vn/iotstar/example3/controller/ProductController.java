package vn.iotstar.example3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.example3.dto.ProductDTO;
import vn.iotstar.example3.service.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public String list(@RequestParam(defaultValue="") String keyword,
                       @RequestParam(defaultValue="0") int page,
                       Authentication auth, Model model) {
        Page<ProductDTO> data = productService.searchByOwner(auth.getName(), keyword, page, 5);
        model.addAttribute("data", data);
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "product/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.find(id));
        return "product/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") ProductDTO dto, BindingResult result,
                       @RequestParam(required=false) MultipartFile image,
                       Authentication auth) throws Exception {
        if (result.hasErrors()) return "product/form";
        String imageUrl = (image != null && !image.isEmpty()) ? cloudinaryService.upload(image) : null;
        productService.save(dto, auth.getName(), imageUrl);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
