package com.abhishek.ShoppingCart.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abhishek.ShoppingCart.Model.Category;
import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Model.User;
import com.abhishek.ShoppingCart.Repository.ProductRepository;
import com.abhishek.ShoppingCart.Services.CategoryService;
import com.abhishek.ShoppingCart.Services.ProductService;
import com.abhishek.ShoppingCart.dto.Product.ProductDto;

@Controller
@RequestMapping("kurtisKulture")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	

	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping("/products/{id}")
	public String getProduct(@PathVariable Integer id, Model model) throws Exception {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "product";
	}
	@GetMapping("/productList")
	public ResponseEntity<List<ProductDto>> getProduct() {
		List<ProductDto> productList = productService.listProducts();
		return new ResponseEntity<List<ProductDto>>(productList, HttpStatus.OK);
//		model.addAttribute("products", productList);
//		return "index";
	}

	@GetMapping("/addProducts")
    public String createNewPost(Model model) {

        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "product_new";
    }
	
	@PostMapping("/addProducts")
	public ResponseEntity<?> addProduct(@ModelAttribute Product product, @RequestParam(value = "id") Integer id, @RequestParam("file") MultipartFile file) throws IOException{
		productService.addProduct(product, id, file);
		return new ResponseEntity<>("Product has been added successfully.", HttpStatus.CREATED);
	}

	@PostMapping("/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable("productId") Integer productId,
			@RequestBody @Valid ProductDto productDto) {
		Optional<Category> optionalcategory = categoryService.findById(productDto.getCategoryId());
		if (!optionalcategory.isPresent()) {
			return new ResponseEntity<>("Category is invalid.", HttpStatus.CONFLICT);
		}
		Category category = optionalcategory.get();
		productService.updateProduct(productId, productDto);
		return new ResponseEntity<>("Product has been updated", HttpStatus.OK);
	}
}
