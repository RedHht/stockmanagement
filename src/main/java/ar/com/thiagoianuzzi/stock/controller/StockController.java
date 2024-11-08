package ar.com.thiagoianuzzi.stock.controller;

import ar.com.thiagoianuzzi.stock.model.entity.Product;
import ar.com.thiagoianuzzi.stock.model.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StockController {

    private final ProductRepository productRepository;

    StockController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/view/"+product.getId();
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute(new Product());
        return "add";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/view/"+product.getId();
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
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/1";
    }
}
