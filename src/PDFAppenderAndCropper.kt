import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
//import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
//import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg
//import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage
//import org.apache.pdfbox.util.PDFMergerUtility
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
//import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg
//import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
//import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage

fun main() {
//    for (file in File(System.getProperty("user.dir")).listFiles() ?: listFilesReturnedNull()) {
        val file = File("C:\\Users\\Shmuel\\OneDrive\\IdeaProjects\\PDFtoJPG\\p1 Yearbook.pdf")
        val document = PDDocument()
//        val bufferedImage = pdfPageToBufferedImage(file)
//        val width = bufferedImage.width.toFloat()-100
//        val height = bufferedImage.height.toFloat()-100
//        val page = PDPage(PDRectangle(width, height))
//        document.addPage(page)
//        val img: PDXObjectImage = PDJpeg(document, FileInputStream(file))
//        val contentStream = PDPageContentStream(document, page)
//        contentStream.drawImage(img, 0F, 0F)
//        contentStream.close()
        if(File("test.pdf").exists())File("test.pdf").delete()
        document.save("test.pdf")
        document.close()
//        mergePDFs(File(System.getProperty("user.dir")).listFiles() ?: listFilesReturnedNull())
    }
//}

/*private fun mergePDFs(givenFiles: Array<File>) {
    val pdfMerger = PDFMergerUtility()
    pdfMerger.destinationFileName = System.getProperty("user.dir") + "/merged.pdf"
    for (file in givenFiles) pdfMerger.addSource(file)
    pdfMerger.mergeDocuments()
    println("Documents merged.")
}*/

/*
fun pdfPageToBufferedImage(sourceFile: File): BufferedImage {
    val document = PDDocument.load(sourceFile)
    val pdfRenderer = PDFRenderer(document)
    return if(pdfRenderer.renderImage(0)!=null) pdfRenderer.renderImage(0) else {
        println("An error occured when converting $sourceFile file to image (preliminary step to cropping).")
        BufferedImage(0,0,0)
    }
}
*/

fun listFilesReturnedNull(): Array<File> {
    println("An error occured when accessing the files. Perhaps the program does not have a sufficient level of permission? Try running the program using administrator priveleges.")
    return arrayOf<File>()
}