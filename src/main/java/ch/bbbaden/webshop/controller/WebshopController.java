package ch.bbbaden.webshop.controller;

import ch.bbbaden.webshop.controller.records.AmountUpdate;
import ch.bbbaden.webshop.cart.ShoppingCart;
import ch.bbbaden.webshop.controller.records.Breadcrumb;
import ch.bbbaden.webshop.controller.records.Order;
import ch.bbbaden.webshop.model.OrderService;
import ch.bbbaden.webshop.model.entity.Category;
import ch.bbbaden.webshop.model.entity.CustomerOrder;
import ch.bbbaden.webshop.model.entity.Product;
import ch.bbbaden.webshop.model.repositories.CategoryRepository;
import ch.bbbaden.webshop.model.repositories.CustomerOrderRepository;
import ch.bbbaden.webshop.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

@Controller
public class WebshopController {

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return setContent(model, "categories", getHomeBreadcrumb());
    }

    @GetMapping({"/category/{id}"})
    public String category(@PathVariable Long id, Model model) {
        Category category = categoryRepository.getReferenceById(id);
        model.addAttribute("category", category);
        return setContent(model,
                "category",
                getHomeBreadcrumb(),
                getCategoryBreadcrumb(category));
    }

    @GetMapping({"/category/{catId}/product/{prodId}"})
    public String product(@PathVariable Long prodId, Model model) {
        Product product = productRepository.getReferenceById(prodId);
        model.addAttribute("product", product);
        return setContent(model,
                "product",
                getHomeBreadcrumb(),
                getCategoryBreadcrumb(product.getCategory()),
                getProductBreadcrumb(product));
    }


    @GetMapping({"/cart"})
    public String cartShow(Model model) {
        return setContent(model,
                "cart",
                getHomeBreadcrumb(),
                getCartBreadcrumb());
    }

    @GetMapping({"/cart/add/{id}"})
    public String cartAdd(@PathVariable Long id) {
        Product product = productRepository.getReferenceById(id);
        shoppingCart.add(product);
        return String.format("redirect:/category/%d", product.getCategory().getId());
    }

    @PostMapping(path = {"/cart/update/{id}"})
    public String cartAdd(@PathVariable Long id, AmountUpdate update) {
        Product product = productRepository.getReferenceById(id);
        shoppingCart.updateQuantity(product, update.amount());
        return "redirect:/cart";
    }

    @GetMapping({"/checkout"})
    public String checkout(Model model) {
        return setContent(model,
                "checkout",
                getHomeBreadcrumb(),
                getCheckoutBreadcrumb());
    }

    @PostMapping({"/checkout"})
    public String checkout(Order order, Model model) {
        CustomerOrder customerOrder = orderService.placeOrder(order);
        model.addAttribute("customerOrder", customerOrder);
        shoppingCart.clear();
        return setContent(model, "confirmation", getHomeBreadcrumb());
    }

    @GetMapping({"/lang/change/{locale}"})
    public String languageChange(@PathVariable String locale, Model model) {
        ((SessionLocaleResolver) localeResolver).setDefaultLocale(Locale.forLanguageTag(locale));
        return String.format("redirect:/");
    }

    private Breadcrumb getHomeBreadcrumb() {
        return new Breadcrumb("/", getText("home"));
    }

    private Breadcrumb getCartBreadcrumb() {
        return new Breadcrumb("/cart", getText("cart"));
    }

    private Breadcrumb getCheckoutBreadcrumb() {
        return new Breadcrumb("/checkout", getText("checkout"));
    }

    private Breadcrumb getCategoryBreadcrumb(Category category) {
        String categoryLabel = getText("category");
        String categoryName = getText(category.getName());
        String categoryLink = String.format("/category/%d", category.getId());
        return new Breadcrumb(categoryLink, String.format("%s: %s", categoryLabel, categoryName));
    }

    private Breadcrumb getProductBreadcrumb(Product product) {
        String productLabel = getText("product");
        String productName = getText(product.getName());
        String productLink = String.format("/category/%d/product/%d", product.getCategory().getId(), product.getId());
        return new Breadcrumb(productLink, String.format("%s: %s", productLabel, productName));
    }

    private String getText(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private String setContent(Model model, String content, Breadcrumb... breadcrumbs) {
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("content", content);
        model.addAttribute("breadcrumbs", breadcrumbs);
        return "shop/index";
    }
}
