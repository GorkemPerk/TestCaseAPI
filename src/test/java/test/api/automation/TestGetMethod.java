package test.api.automation;



import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;



public class TestGetMethod {
    //final static String ROOT_URI = "http://www.omdbapi.com";

    @Test
    public void GetMEthod() {

       RestAssured.baseURI = "http://www.omdbapi.com/";

        RequestSpecification request = RestAssured

                .given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));

        Response response = request

                .when()

                .get("/?apikey=5148dbc1&s=Harry Potter")
                .then()
                .statusCode(200)


                .extract().response();

        int responseCode = response.getStatusCode();
        ResponseBody responseBody = response.getBody();
        ArrayList<String> list=new ArrayList<String>();

        //System.out.println(response.asString());
        Gson gson=new Gson();
        String json=response.asString();
        Search obj=gson.fromJson(json,Search.class);
        String imdb="";
        for (Element e:obj.Search
             ) {
            String title=e.getTitle();
         if (title.equals("Harry Potter and the Sorcerer's Stone")){
          imdb=e.getimdbID();
         }
        }
        System.out.println("imdbID:"+imdb);
        //http://www.omdbapi.com/?apikey=5148dbc1&i=tt0241527&type=movie
        RequestSpecification request1 = RestAssured

                .given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));

        Response response1 = request1

                .when()

                .get("/?apikey=5148dbc1&i="+imdb+"&type=movie")
                .then()
                .statusCode(200)


                .extract().response();

        String json1=response1.asString();
        Movie obj1=gson.fromJson(json1,Movie.class);

        System.out.println("Title:" +obj1.getTitle() + " Year: " + obj1.getYear()+ " Released: "+obj1.getReleased());

        System.out.println("---------------------");





    }
}

