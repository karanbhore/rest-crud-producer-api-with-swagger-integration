package com.prowings.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prowings.entity.Product;
import com.prowings.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.prowings.util.Constants.*;

import lombok.extern.log4j.Log4j2;

@Tag(name = "Product", description = "Product management/CRUD APIs")
@Log4j2
@RestController
@RequestMapping("/api/v1")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Operation(
			summary = "Retrive all Products",
			method = "getProducts",
			description = "Gets the list of all Products!!"
			)
	  @ApiResponses({
		    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
		    @ApiResponse(responseCode = "404", description = "Error while fetching the Products.", content = { @Content(schema = @Schema()) })
		  })
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts()
	{
		log.info("Request received to fetch all Products");
		List<Product> prodList = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);
	}


	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id)
	{
		log.info("Request received to fetch all Products");
		Product retrivedProduct = productService.getProductById(id);
		return new ResponseEntity<Product>(retrivedProduct, HttpStatus.OK);
	}

	@GetMapping("/products/name")
	public ResponseEntity<List<Product>> getProductByName(@RequestParam String name)
	{
		log.info("Request received to fetch Products by name");
		List<Product> prodList = productService.getProductByName(name);
		return new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);

	}

	@GetMapping("/products/name_price")
	public ResponseEntity<List<Product>> getProductByNameAndPrice(@RequestParam String name, float price)
	{
		log.info("Request received to fetch Products by name and price");
		List<Product> prodList = productService.getProductByNameAndPrice(name, price);
		return new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);
		
	}
	
	@PostMapping("/products")
	public ResponseEntity<String> createProduct(@RequestBody Product product)
	{
		log.info("Request received to store the Product : {}", product);
		String res = (productService.createProduct(product)) ?  SUCCESSFULLY_STORED: ERROR_WHILE_STORING;
		return new ResponseEntity<String>(res, HttpStatus.CREATED);
	}

	
	@GetMapping("/products/sort") 
	public ResponseEntity<List<Product>> getAllProductsSorted(@RequestParam String sortBy, String sortDir)
	{
		log.info("Request received to fetch all Products with sorting techniq");
		List<Product> prodList = productService.getAllProductsSorted(sortBy,sortDir);
		return new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);
	}

//	@GetMapping("/products/sort") 
//	public ResponseEntity<List<Product>> getAllProductsSortedWithPagination(@RequestParam String sortBy, 
//			@RequestParam String sortDir,
//			@RequestParam int pageSize,
//			@RequestParam int pageNo )
//	{
//		log.info("Request received to fetch all Products with sorting and Pagination techniq");
//		List<Product> prodList = productService.getAllProductsSortedAndPaginated(pageSize, pageNo, sortBy,sortDir);
//		return new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);
//	}

}
