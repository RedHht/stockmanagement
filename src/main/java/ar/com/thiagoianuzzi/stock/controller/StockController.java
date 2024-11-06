package ar.com.thiagoianuzzi.stock.controller;

import ar.com.thiagoianuzzi.stock.model.entity.Product;
import ar.com.thiagoianuzzi.stock.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StockController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("paginaActual", "inicio");

        return "indice";
    }

    @RequestMapping("/{page}")
    public String stock(Model model, @PathVariable int page) {
        model.addAttribute("paginaActual", "stock");
        Pageable pageable = Pageable.ofSize(16);

        model.addAttribute("page", page);
        model.addAttribute("products", productRepository.findAll(pageable.withPage(page)));
        return "stock";
    }

    @RequestMapping("/search")
    public String search(Model model, @RequestParam(name = "query", required = false) String query) {
        model.addAttribute("paginaActual", "stock");
        List<Product> pageable =productRepository.findByNameContainingIgnoreCase(query);

        model.addAttribute("products", pageable);
        return "search";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Product producto = productRepository.findById(id);
        model.addAttribute(producto);
        return "edit";
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id) {
        Product producto = productRepository.findById(id);
        model.addAttribute(producto);
        return "view";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/1";
    }
}
