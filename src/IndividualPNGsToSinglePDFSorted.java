    import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
    import java.util.Arrays;
    import java.util.Objects;
import javax.imageio.ImageIO;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

    public class IndividualPNGsToSinglePDFSorted {
        public static final String PATHTOCROPPEDIMAGES = System.getProperty("user.dir") + "\\Cropped Images";

        public static void main(String[] args) throws IOException {
            getPDFFromFolderOfImages();
        }

        public static void getPDFFromFolderOfImages() throws IOException {
            PDDocument document = new PDDocument();
            FileFilter pngfilter = file -> file.getName().endsWith(".png");
            File[] croppedImages = new File(PATHTOCROPPEDIMAGES).listFiles(pngfilter);
            Arrays.sort(croppedImages);
            for (File ignored : croppedImages)
                document.addPage(new PDPage());
            File endFile = new File(System.getProperty("user.dir") + "\\merged.pdf");
            document.save(System.getProperty("user.dir") + "\\merged.pdf");
            System.out.println("PDF created");
            document.close();
            PDDocument doc = PDDocument.load(endFile);
            for (int i = 0; i < croppedImages.length; i++) {
                PDPage page = doc.getPage(i);
                PDImageXObject pdImage = PDImageXObject.createFromFile(croppedImages[i].getAbsolutePath(), doc);
                PDPageContentStream contents = new PDPageContentStream(doc, page);
                contents.drawImage(pdImage, 1.0F, 0.0F);
                contents.close();
                System.out.println("Image inserted");
            }
            doc.save(endFile.getAbsolutePath());
            doc.close();
        }

        public static void mergePDFs(File[] givenFiles) throws IOException {
            PDDocument doc = null;
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            try {
                BufferedImage awtImage = ImageIO.read(new File("c://temp//line_chart.png"));
                PDImageXObject pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
                contentStream.drawImage(pdImageXObject, 200.0F, 300.0F, awtImage.getWidth() / 2.0F, awtImage.getHeight() / 2.0F);
                contentStream.close();
                doc.save("c://temp//pdf//PDF_image.pdf");
                doc.close();
            } catch (Exception io) {
                System.out.println(" -- fail --" + io);
            }
            PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
            pdfMergerUtility.setDestinationFileName(System.getProperty("user.dir") + "\\Cropped Images\\merged.pdf");
            for (File file : givenFiles)
                pdfMergerUtility.addSource(file);
            pdfMergerUtility.mergeDocuments(null);
            System.out.println("Documents merged.");
        }
    }
