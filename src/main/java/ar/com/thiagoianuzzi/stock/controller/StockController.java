package ar.com.thiagoianuzzi.stock.controller;

import ar.com.thiagoianuzzi.stock.model.entity.Product;
import ar.com.thiagoianuzzi.stock.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/{page}")
    public String index(Model model, @PathVariable int page) {
        Pageable pageable = Pageable.ofSize(25);

        model.addAttribute("page", page);
        model.addAttribute("products", productRepository.findAll(pageable.withPage(page)));
        return "indice";
    }

    @RequestMapping("/search/{words}")
    public String search(Model model, @PathVariable String words) {
        List<Product> pageable =productRepository.findByNameContainingIgnoreCase(words);

        model.addAttribute("products", pageable);
        return "search";
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(Model model, @PathVariable Long id) {
        Product producto = productRepository.findById(id);
        productRepository.deleteById(producto);
        return new ModelAndView("redirect:/indice");
    }
}
