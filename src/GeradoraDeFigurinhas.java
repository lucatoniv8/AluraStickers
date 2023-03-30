import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
    
    public void cria( InputStream inputStream, String nomeArquivo ) throws Exception{

        // leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        //InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies_1.jpg").openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // criar nova imagem em memoria com transparencia com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);
        //copiar a imagem original para nova imagem em memoria
        Graphics2D graphics = (Graphics2D) novaImagem.createGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);
        
        // configurar a fonte
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 200);
        graphics.setColor(Color.ORANGE);
        graphics.setFont(fonte);
        
        //escrever um texto na imagem
        graphics.drawString("O Loco Bixo!", 100, novaAltura - 25);


        //escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }
    // public static void main(String[] args) throws Exception {
    //     var geradora = new GeradoraDeFigurinhas();
    //     geradora.cria();
    // }
}
