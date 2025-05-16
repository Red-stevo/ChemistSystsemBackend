package com.red.stevo.chemsales.controllers.homePageController;


import com.red.stevo.chemsales.models.HomePageDetailsModel;
import com.red.stevo.chemsales.models.PageData;
import com.red.stevo.chemsales.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor()
@RequestMapping("api/v1/products")
public class ProductDetailsController {

    private final ProductDetailsService detailsService;

    @GetMapping("/details")
    public ResponseEntity<HomePageDetailsModel> getDetail(
             @RequestBody PageData pageData
    ) {

        return detailsService.getProductDetails(pageData);
    }
}
