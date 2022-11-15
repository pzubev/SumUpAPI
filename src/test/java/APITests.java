import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class APITests {

    Gson gson = new Gson();
    Authorization auth = new Authorization();

    @BeforeTest
    void generateToken() throws IOException {

        ConfigProperties.initializePropertyFile();

        auth.setClient_id(ConfigProperties.properties.getProperty("CLIENT_ID"));
        auth.setClient_secret(ConfigProperties.properties.getProperty("CLIENT_SECRET"));
        auth.setUsername(ConfigProperties.properties.getProperty("USERNAME"));
        auth.setPassword(ConfigProperties.properties.getProperty("PASSWORD"));

        RequestSpecification httpRequest = given().
            contentType(ContentType.JSON).
            body(auth);

        Response response = httpRequest.post(ConfigProperties.properties.getProperty("baseURL") + "/oauth");

        String responseBody = response.getBody().asString();

        auth = gson.fromJson(responseBody, Authorization.class);
    }

    @Test
    void verifyBankCodeDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
            header("Authorization", "Bearer " + auth.getAccess_token()).
            contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get(ConfigProperties.properties.getProperty("baseURL") + ConfigProperties.properties.getProperty("bankAccountsPath"));

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("bank_code"), "BNBG9661");
    }

    @Test
    void verifySwiftBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get(ConfigProperties.properties.getProperty("baseURL") + ConfigProperties.properties.getProperty("bankAccountsPath"));

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("swift"), "ABCDEFZZ");
    }

    @Test
    void verifyAccountHolderNameBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get(ConfigProperties.properties.getProperty("baseURL") + ConfigProperties.properties.getProperty("bankAccountsPath"));

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("account_holder_name"), "Petar Zubev");
    }

    @Test
    void verifyBankAccountCreationDateBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get(ConfigProperties.properties.getProperty("baseURL") + ConfigProperties.properties.getProperty("bankAccountsPath"));

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("created_at"), "2022-11-10T20:49:28.876Z");
    }

    @Test
    void verifyNoTransactionsAreMade(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response transactions = httpsBankDetailsRequest.get(ConfigProperties.properties.getProperty("baseURL") + ConfigProperties.properties.getProperty("transactionsHistoryPath"));

        Assert.assertEquals(transactions.getStatusCode(), 200);


        ArrayList<String> transactionItems = transactions.jsonPath().get("items");
        Assert.assertTrue(transactionItems.isEmpty());
    }
}
