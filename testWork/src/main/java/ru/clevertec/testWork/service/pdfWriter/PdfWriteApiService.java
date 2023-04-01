package ru.clevertec.testWork.service.pdfWriter;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfWriteApiService implements PdfWriteService {

    /**
     * this method creates a copy based on an existing PDF file "updated_Clevertec_Template.pdf"
     * and fills it with data from the method {@link ru.clevertec.testWork.service.product.ProductApiService} getCheck
     * @param check get from {@link ru.clevertec.testWork.controllers.shop.ShopController} check
     */
    @Override
    public void writeToPdfFile(List<Object> check) {
        try {
            PdfReader reader = new PdfReader("Clevertec_Template.pdf");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("updated_Clevertec_Template.pdf"));
            PdfContentByte canvas = stamper.getUnderContent(1);

            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            canvas.setFontAndSize(font, 8);
            int yOffset = 500;
            for (Object obj : check) {
                System.out.println("obj = " + obj);
                canvas.beginText();
                canvas.setTextMatrix(20, yOffset);
                canvas.showText(obj.toString());
                canvas.endText();
                yOffset -= 20;
            }
            stamper.close();
            reader.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
