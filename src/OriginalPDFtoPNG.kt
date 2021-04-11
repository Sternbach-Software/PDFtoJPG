import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.io.File
import javax.imageio.ImageIO

fun main() {
    try {
        val destinationDir = "C:\\Users\\Shmuel\\OneDrive\\Documents\\"
        val sourceFile = File("C:\\Users\\Shmuel\\OneDrive\\Documents\\Yichus Brief Translation.pdf")
        val destinationFile = File(destinationDir)
        if (!destinationFile.exists()) {
            destinationFile.mkdir()
            println("Folder Created -> " + destinationFile.absolutePath)
        }
        if (sourceFile.exists()) {
            val document = PDDocument.load(sourceFile)
            val pdfRenderer = PDFRenderer(document)
            val fileName = sourceFile.name.replace(".pdf", "")

            // int pageNumber = 0;

            // for (PDPage page : document.getPages()) {
            for (pageNumber in 0 until document.numberOfPages) {
                val bim = pdfRenderer.renderImage(pageNumber)
                val destDir = destinationDir + fileName + "_" + pageNumber + ".png"
                ImageIO.write(bim, "png", File(destDir))
            }
            document.close()
            println("Image saved at -> " + destinationFile.absolutePath)
        } else {
            System.err.println(sourceFile.name + " File does not exist")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}