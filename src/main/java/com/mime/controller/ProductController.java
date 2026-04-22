import com.mime.service.RecommendationService;

private final RecommendationService recService;

public ProductController(ProductService service, RecommendationService recService) {
    this.service = service;
    this.recService = recService;
}

@GetMapping("/product/{id}")
public String product(@PathVariable Long id, Model model) {
    model.addAttribute("product", service.getProductById(id));
    model.addAttribute("similarProducts", recService.getSimilarProducts(id));
    return "product-details";
}
