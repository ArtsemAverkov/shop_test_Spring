package ru.clevertec.testWork.controllers.shop;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.service.pdfWriter.PdfWriteService;
import ru.clevertec.testWork.service.product.ProductService;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@Slf4j
@RestController
@RequestMapping("/product")
public record ShopController(ProductService productService,
                             PdfWriteService pdfWriteService) {
    /**
     * this method creates a new product
     *
     * @param productDto get from server
     * @return the long id of the created product
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.create(productDto);
    }

    /**
     * this method searches the product database
     * @param id get from server
     * @param amount get from server
     * @param discount get from server
     * @param idDiscount get from server
     * @return check according to the condition:
     * - the cost is calculated based on the quantity,
     * - if there are more than 5 promotional goods, then a 10 percent discount is calculated for them,
     * - the total amount and the amount after the discount are returned.
     */

    @GetMapping(value = "/check")
    @ResponseStatus(HttpStatus.OK)
    public List<Object> getCheck(@RequestParam(value = "id") List<Long> id,
                             @RequestParam(value = "amount") List<Long> amount,
                             @RequestParam(value = "discount") String discount,
                             @RequestParam(value = "idDiscount") Long idDiscount) {
        return productService.getCheck(id, amount, idDiscount,discount);
    }

    /**
     * this method updates product by id
     *
     * @param productDto get from server
     * @param id         get from server
     * @returт successful and unsuccessful update
     */

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        return productService.update(productDto, id);
    }

    /**
     * this method removes the product from the database
     *
     * @param id get from server
     * @returт successful and unsuccessful delete
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    /**
     * this method returns a collection of all product in the database
     *
     * @return collection of all product
     */

    @GetMapping(produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public List<Product> readProduct(@PageableDefault(page = 0) Pageable pageable) {
        return productService.readAll(pageable);
    }

    /**
     * this method returns receipt in PDF format
     * @param id get from server
     * @param amount get from server
     * @param discount get from server
     * @param idDiscount get from server
     * @return PDF file in server
     */
    @GetMapping("/getCheckInPDF")
    public ResponseEntity<byte[]> getCheckInPDF (@RequestParam(value = "id") List<Long> id,
                            @RequestParam(value = "amount") List<Long> amount,
                            @RequestParam(value = "discount") String discount,
                            @RequestParam(value = "idDiscount") Long idDiscount) throws IOException {

        List<Object> check = productService.getCheck(id, amount, idDiscount, discount);
        pdfWriteService.writeToPdfFile(check);
        InputStream inputStream = new FileInputStream("updated_Clevertec_Template.pdf");
        byte[] contents = IOUtils.toByteArray(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "updated_Clevertec_Template.pdf");

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }
}


