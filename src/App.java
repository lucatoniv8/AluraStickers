import java.net.URI;
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
        System.out.println("1 - Filmes;\n2 - Series;\n0 - Sair");
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
                break;
        }
        // fazer um conexão HTTP e buscar os top 250 filmes
        //String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        
        // extrair os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        System.out.println("\n====================================================");
        // exibir e manipular os dados
         for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("\u001b[1mTITULO:\u001b[m" + filme.get("title"));
            System.out.println("\u001b[1mURL IMAGEM:\u001b[m" + filme.get("image"));
            double estrelas = Double.parseDouble(filme.get("imDbRating"));
            for (int i = 1; i <= estrelas; i++) {
                System.out.print(" ⭐");
            }
            System.out.println(" "+estrelas);
            String rotulo = "";
            if (estrelas > 9){
                rotulo = "OBRA PRIMA";
            }else if(estrelas > 7){
                rotulo = "MUITO BOM HEIN";
            }else if(estrelas > 5){
                rotulo = "DÁ PRO GASTO";
            }else{
                rotulo = "RUIM DEMAIS";
            }
            System.out.println(rotulo);
            System.out.println("\n====================================================");
            
        }
    }
}
