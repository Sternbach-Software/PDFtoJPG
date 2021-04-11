import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.io.File
import java.io.FileFilter
import javax.imageio.ImageIO


fun main(args: Array<String>) {
    val destinationDir = System.getProperty("user.dir")
    try {
        //Override accept method
        val pdfFilter = FileFilter { file: File -> file.extension=="pdf"}

        for (sourceFile in File(destinationDir).listFiles(pdfFilter)!!) {
            val destinationFile = File(destinationDir)
            if (!destinationFile.exists()) {
                destinationFile.mkdir()
                println("Folder Created -> " + destinationFile.absolutePath)
            }
            if (sourceFile.exists()) {
                val document = PDDocument.load(sourceFile)
                val pdfRenderer = PDFRenderer(document)
                val fileName = sourceFile.nameWithoutExtension

                // int pageNumber = 0;

                // for (PDPage page : document.getPages()) {
                for (pageNumber in 0 until document.numberOfPages) {
                    val bim = pdfRenderer.renderImage(pageNumber)
//                val croppedImage = bim.getSubimage(25, 25, 610, 790)
                    val destDir = "$destinationDir\\$fileName.png"
                    val g2d: Graphics2D = bim.createGraphics()
                    g2d.color = Color.white
                    g2d.fill(Rectangle2D.Float(0F, 0F, 25F, 840F))//left margin
                    g2d.fill(Rectangle2D.Float(24F, 0F, 635F, 24F))//top margin
                    g2d.fill(Rectangle2D.Float(24F, 815F, 635F, 24F))//bottom margin
                    g2d.fill(Rectangle2D.Float(635F, 0F, 25F, 840F))//right margin
//                g2d.fill(Rectangle2D.Float(0F, 0F, 24F, 839F))
                    g2d.dispose()
//                val destDir1 = destinationDir + "\\" + fileName + "_" + pageNumber + 1 + ".png"
                    ImageIO.write(bim, "png", File(destDir))
//                ImageIO.write(croppedImage, "png", File(destDir1))
                }
                document.close()
                println("Image saved at -> " + destinationFile.absolutePath)
            } else {
                System.err.println(sourceFile.name + " File does not exist")
            }
        }
    }
    catch (e: Exception) {
        e.printStackTrace()
    }
}
