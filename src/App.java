import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // menu aplicação
        System.out.println("Escolha qual Rank você deseja:");
        System.out.println("|1 - Filmes|-|2 - Series|-|0 - Sair|");
        Scanner ler = new Scanner(System.in);
        int opcao = ler.nextInt();
        String url = "";
        switch (opcao) {
            case 1:
            url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
                break;
            case 2:
            url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
                break;
            case 0:
                System.out.println("Saindo ..........");
                break;
            default:
            System.out.println("Não entendi fechando aplicação");
                break;
        }
        // fazer um conexão HTTP e buscar os top 250 filmes
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        // extrair os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        var geradora = new GeradoraDeFigurinhas();
        System.out.println("\n====================================================");
        
        // exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[1mTITULO:\u001b[m" + filme.get("title"));
            double estrelas = Double.parseDouble(filme.get("imDbRating"));
            for (int i = 1; i <= estrelas; i++) {
                System.out.print(" ⭐");
            }
            System.out.println(" "+estrelas);
            String rotulo = "";
            if (estrelas > 9){
                rotulo = "OBRA PRIMA";
            }else if(estrelas > 7){
                rotulo = "MUITO BOM ";
            }else if(estrelas > 5){
                rotulo = "TÁ FRACO";
            }else{
                rotulo = "RUIM DEMAIS";
            }
            System.out.println(rotulo);
            System.out.println("\n====================================================");
            
            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            InputStream joinha = new FileInputStream(new File("figurinhas/joinha.png"));
            String nomeArquivo = "figurinhas/" + titulo + ".png";
            
            geradora.cria(inputStream, joinha, nomeArquivo, rotulo);

        }
    }
}
