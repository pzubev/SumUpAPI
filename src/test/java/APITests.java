import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class APITests {

    Gson gson = new Gson();
    Authorization auth = new Authorization();

    @BeforeTest
    void generateToken() {

        auth.setClient_id(Constants.CLIENT_ID);
        auth.setClient_secret(Constants.CLIENT_SECRET);
        auth.setUsername(Constants.USERNAME);
        auth.setPassword(Constants.PASSWORD);

        RequestSpecification httpRequest = given().
            contentType(ContentType.JSON).
            body(auth);

        Response response = httpRequest.post("https://api.sumup.com/oauth");

        String responseBody = response.getBody().asString();

        auth = gson.fromJson(responseBody, Authorization.class);
    }

    @Test
    void verifyBankCodeDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
            header("Authorization", "Bearer " + auth.getAccess_token()).
            contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get("https://api.sumup.com/v0.1/me/merchant-profile/bank-accounts");

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("bank_code"), "BNBG9661");
    }

    @Test
    void verifySwiftBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get("https://api.sumup.com/v0.1/me/merchant-profile/bank-accounts");

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("swift"), "ABCDEFZZ");
    }

    @Test
    void verifyAccountHolderNameBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get("https://api.sumup.com/v0.1/me/merchant-profile/bank-accounts");

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("account_holder_name"), "Petar Zubev");
    }

    @Test
    void verifyBankAccountCreationDateBankDetails(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response bankDetailsResponse = httpsBankDetailsRequest.get("https://api.sumup.com/v0.1/me/merchant-profile/bank-accounts");

        Assert.assertEquals(bankDetailsResponse.getStatusCode(), 200);

        JSONArray JSONResponseBody = new JSONArray(bankDetailsResponse.body().asString());
        Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("created_at"), "2022-11-10T20:49:28.876Z");
    }

    @Test
    void verifyNoTransactionsAreMade(){

        RequestSpecification httpsBankDetailsRequest = given().
                header("Authorization", "Bearer " + auth.getAccess_token()).
                contentType(ContentType.JSON);

        Response transactions = httpsBankDetailsRequest.get("https://api.sumup.com/v0.1/me/transactions/history");

        Assert.assertEquals(transactions.getStatusCode(), 200);


        ArrayList<String> transactionItems = transactions.jsonPath().get("items");
        Assert.assertTrue(transactionItems.isEmpty());
    }
}
