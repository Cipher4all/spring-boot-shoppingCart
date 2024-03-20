package com.abhishek.ShoppingCart.Services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abhishek.ShoppingCart.Model.Category;
import com.abhishek.ShoppingCart.Model.Product;
import com.abhishek.ShoppingCart.Repository.CategoryRepository;
import com.abhishek.ShoppingCart.Repository.ProductRepository;
import com.abhishek.ShoppingCart.dto.Product.ProductDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }
	
	public static ProductDto getDtoFromProduct(Product product) {
		ProductDto productDto = new ProductDto(product);
		return productDto;
	}
	
	public static Product getProductFromDto(ProductDto productDto) {
        Product product = new Product(productDto);
        return product;
    }
	
	@SuppressWarnings("deprecation")
	public void addProduct(Product product, Integer catid, MultipartFile file) throws IOException{
		Category categorytemp = categoryRepository.getById(catid);
		List<Product> products = new ArrayList<>();
		product.setImageData(file.getBytes());
		product.setCategory(categorytemp);
		products.add(product);
		categorytemp.setProducts(products);
		categoryRepository.save(categorytemp);
	}
	
	public void updateProduct(Integer productId, ProductDto productDto) {
		Product product = getProductFromDto(productDto);
		product.setId(productId);
		productRepository.save(product);
	}
	
	public Product getProductById(Integer productId) throws Exception{
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(!optionalProduct.isPresent()) {
			throw new Exception("ProductId is invalid" + productId);
		}
		return optionalProduct.get();
	}
}
