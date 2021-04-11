import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

//import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//import org.apache.pdfbox.rendering.PDFRenderer;
//import org.apache.pdfbox.util.PDFMergerUtility;

class Main1 {

    public static final String PATHTOCROPPEDIMAGES = System.getProperty("user.dir") + "\\Cropped Images";

    public static void main(String[] args) throws IOException {
//            String[] bits = file.getName().split("\\.");
//            boolean endsInPDF = bits[bits.length - 1].equalsIgnoreCase("pdf");
            //            if(endsInPDF) convertPDFToCroppedPNG(System.getProperty("user.dir")+"\\Cropped Images", file);
        /*mergePDFs(*/
        getPDFFromFolderOfImages()/*)*/;
        }
        public static void getPDFFromFolderOfImages() throws IOException {
        /*File[] listOfPDFs;
            try (final PDDocument doc = new PDDocument()){
                for(File file: Objects.requireNonNull(folderWithImages.listFiles())) {
                    String[] bits = file.getName().split("\\.");
                    boolean endsInPDF = bits[bits.length - 1].equalsIgnoreCase("pdf");
                    if (endsInPDF) {
                        PDPage page = new PDPage();

                        doc.addPage(page);

                        String image = Image.class.getResource(file.getAbsolutePath()).getFile();
                        PDImageXObject pdImage = PDImageXObject.createFromFile(image, doc);

                        PDPageContentStream contents = new PDPageContentStream(doc, page);
                        PDRectangle mediaBox = page.getMediaBox();
*//*PDImageXObject pdfImage = PDImageXObject.createFromFileByContent(file, doc);
                    contents.drawImage(pdImage, 20f, 20f);*/
            /*
                        float startX = (mediaBox.getWidth() - pdImage.getWidth()) / 2;
                        float startY = (mediaBox.getHeight() - pdImage.getHeight()) / 2;
                        contents.drawImage(pdImage, startX, startY);

                        contents.close();
                        Files.createDirectory(Paths.get(System.getProperty("user.dir")+"\\temp"));
                        doc.save(new File(System.getProperty("user.dir")+"\\temp\\" + file.getName() + ".pdf"));
                    }
                }
            } catch (IOException e){
                System.err.println("Exception while trying to create pdf document - " + e);
            }
            return new File(System.getProperty("user.dir")+"\\temp").listFiles();*/
            PDPageContentStream contents;
            //Creating PDF document object
            PDDocument document = new PDDocument();
            //Override accept method
            FileFilter pngfilter = file -> {
                //if the file extension is .log return true, else false
                return file.getName().endsWith(".png");
            };
            File[] croppedImages = Objects.requireNonNull(new File(PATHTOCROPPEDIMAGES).listFiles(pngfilter));
            for(File ignored : croppedImages) document.addPage(new PDPage());
            //Saving the document
            File endFile = new File(System.getProperty("user.dir")+"\\merged.pdf");
            document.save(System.getProperty("user.dir")+"\\merged.pdf");

            System.out.println("PDF created");
            //Closing the document
            document.close();
            PDDocument doc = PDDocument.load(endFile);
            for(int i = 0;i<croppedImages.length;i++) {

    //Retrieving the page
    PDPage page = doc.getPage(i);

    //Creating PDImageXObject object
    PDImageXObject pdImage = PDImageXObject.createFromFile(croppedImages[i].getAbsolutePath(), doc);

    //creating the PDPageContentStream object
    contents = new PDPageContentStream(doc, page);

    //Drawing the image in the PDF document
    contents.drawImage(pdImage, 1, 0);

                contents.close();
                System.out.println("Image inserted");
            }
            //Closing the PDPageContentStream object

            //Saving the document
            doc.save(endFile.getAbsolutePath());

            //Closing the document
            doc.close();
        }
    public static void mergePDFs(File[] givenFiles) throws IOException {
        PDDocument doc = null;
        doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        try{
            BufferedImage awtImage = ImageIO.read( new File( "c://temp//line_chart.png" ) );
            PDImageXObject  pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
            contentStream.drawImage(pdImageXObject, 200, 300, awtImage.getWidth() / 2F, awtImage.getHeight() / 2F);
            contentStream.close();
            doc.save( "c://temp//pdf//PDF_image.pdf" );
            doc.close();
        } catch (Exception io){
            System.out.println(" -- fail --" + io);
        }

        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(System.getProperty("user.dir")+"\\Cropped Images"+"\\merged.pdf");
        for(File file : givenFiles) pdfMergerUtility.addSource(file);
        pdfMergerUtility.mergeDocuments(null);
        System.out.println("Documents merged.");
    }
/*
public static void convertPDFToCroppedPNG(String destinationDir, File sourceFile){
    try {
//        String destinationDir = System.getProperty("user.dir");

//        File sourceFile = new File("C:\\Users\\Shmuel\\OneDrive\\IdeaProjects\\PDFtoJPG\\p1 Yearbook.pdf");
        File destinationFile = new File(destinationDir);

        if (!destinationFile.exists()) {
            destinationFile.mkdir();
            System.out.println("Folder Created -> " + destinationFile.getAbsolutePath());
        }

        if (sourceFile.exists()) {
            PDDocument document = PDDocument.load(sourceFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            String fileName = sourceFile.getName().replace(".pdf", "");

            // int pageNumber = 0;

            // for (PDPage page : document.getPages()) {
            for (int pageNumber = 0; pageNumber < document.getNumberOfPages(); ++pageNumber) {
                BufferedImage bim = pdfRenderer.renderImage(pageNumber);
                BufferedImage croppedImage = bim.getSubimage(25, 25, 610, 790);
//                String destDir = destinationDir +"\\"+ fileName + "_" + pageNumber + ".png";
                String destDir1 = destinationDir +"\\"+ fileName + ".png";

//                ImageIO.write(bim, "png", new File(destDir));
                ImageIO.write(croppedImage, "png", new File(destDir1));
            }

            document.close();

            System.out.println("Image saved at -> " + destinationFile.getAbsolutePath());
        } else {
            System.err.println(sourceFile.getName() + " File does not exist");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

}
*/
}
