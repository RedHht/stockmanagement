package ar.com.thiagoianuzzi.stock.controller;

import ar.com.thiagoianuzzi.stock.model.entity.Product;
import ar.com.thiagoianuzzi.stock.model.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    public String search(Model model, @RequestParam(name = "query") String query) {
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
        Optional<Product> producto = productRepository.findById(id);

        if (producto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto inexistente");
        }

        model.addAttribute(producto.get());
        return "edit";
    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id) {
        Optional<Product> producto = productRepository.findById(id);

        if (producto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto inexistente");
        }

        model.addAttribute(producto.get());
        return "view";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto inexistente");
        }

        productRepository.deleteById(id);

        return "redirect:/1";
    }
}
