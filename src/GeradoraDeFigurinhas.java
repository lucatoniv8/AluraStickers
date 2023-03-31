import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
    
    public void cria( InputStream inputStream, InputStream joinha, String nomeArquivo, String rotulo ) throws Exception{

        // leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);
        BufferedImage joinhaSorrindo = ImageIO.read(joinha);

        // criar nova imagem em memoria com transparencia com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar a imagem original para nova imagem em memoria
        Graphics2D graphics = (Graphics2D) novaImagem.createGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        int alturaJoinha = altura - joinhaSorrindo.getHeight();
        graphics.drawImage(joinhaSorrindo, 0, alturaJoinha, null);
        
        // configurar a fonte
        var fonte = new Font("Impact", Font.BOLD, 90);
        graphics.setColor(Color.ORANGE);
        graphics.setFont(fonte);
        
        //escrever um texto na imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(rotulo, graphics);
        int larguraTexto = (int)retangulo.getWidth();       
        int posicaoTextoX = (largura - larguraTexto)/2;
        int posicaoTextoY = novaAltura - 50;
        graphics.drawString(rotulo, posicaoTextoX, posicaoTextoY);

        // fazer contorno na fonte
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(rotulo, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.black);
        graphics.draw(outline);
        graphics.setClip(outline);


        //escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }
}
