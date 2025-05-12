package com.red.stevo.chemsales.controllers.stockController;


import com.red.stevo.chemsales.Helpers.classes.SaveImage;
import com.red.stevo.chemsales.models.AddStockModel;
import com.red.stevo.chemsales.service.AddStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController()
@RequiredArgsConstructor()
@RequestMapping("/api/v1/stock")
public class addStockController {

    private final AddStockService addStockService;

    private final SaveImage saveImage;

    @PostMapping("/add/stock")
    public ResponseEntity<HttpStatus> addStock(
            @RequestParam("image") MultipartFile imageFile,
            @RequestPart AddStockModel addStockModel) {

        /*Save image in a directory*/
        addStockModel.setImage(saveImage.saveImage(imageFile));

        System.out.println(addStockModel);
        /*Save or update product data in the repository*/
        return addStockService.saveProduct(addStockModel);
    }
}

