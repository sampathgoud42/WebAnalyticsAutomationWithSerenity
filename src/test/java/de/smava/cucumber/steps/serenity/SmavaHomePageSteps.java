package de.smava.cucumber.steps.serenity;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.JavascriptExecutor;

import static org.assertj.core.api.Assertions.assertThat;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import de.smava.cucumber.pages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmavaHomePageSteps extends ScenarioSteps {

    EnvironmentVariables variables;
    String urlSerenityProperty = "webdriver.base.url";

    //----------- PageObjects --------------

    private SmavaHomePage smavaHomePage;
    private SmavaLoanSearchKreditPage smavaLoanSearchKreditPage;
    private SmavaLoginErrorPage smavaLoginErrorPage;

    private KreditStepPage kreditStepPage;
    private PersonStep1Page personStep1Page;
    private PersonStep2Page personStep2Page;
    private EinkommenStep1Page einkommenStep1Page;
    private SmavaWebAnalyticsUtility smavaWebAnalyticsUtility;


    //------------ Test Data ---------------
    int loanAmount = 500;
    int loanDurationMonths = 24;
    String loanPurpose = "Wohnen";

    String invalidEmail = "smava@invalid.de";
    String invalidPassword = "smava";


    private static List<List<String>> HomePageGMTObjects = new ArrayList<List<String>>();
    static {
        {
            HomePageGMTObjects.add(Arrays.asList("affiliateChannel", "NA"));
            HomePageGMTObjects.add(Arrays.asList("channel", "smava intern"));
            HomePageGMTObjects.add(Arrays.asList("embedded", "0"));
            HomePageGMTObjects.add(Arrays.asList("event", "smavaWonderland"));
            HomePageGMTObjects.add(Arrays.asList("event", "CreditComparisonRoute"));
            HomePageGMTObjects.add(Arrays.asList("flow", "Comparison"));
            HomePageGMTObjects.add(Arrays.asList("site_title", "smava Kreditvergleich"));
        }
    }

    @Step
    public void openNewTabAndCloseExisting(){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(0));
        getDriver().close();
        getDriver().switchTo().window(tabs.get(1));
    }

    @Step
    public String getSerenityPropertyValue(String propertyName){
        variables = SystemEnvironmentVariables.createEnvironmentVariables();
        return variables.getProperty(propertyName);
    }

    @Step
    public void openProvidedURL(){
        getDriver().get( getSerenityPropertyValue(urlSerenityProperty) );
    }

    @Step
    public void open_smava_home_page(){
        smavaHomePage.open();
        getDriver().manage().window().maximize();
        assertThat( smavaHomePage.isSmavaHomePageLoaded() ).isTrue();
    }

    @Step
    public void user_enters_loanSearchInfo(){
        assertThat( smavaHomePage.inputNettokreditbetragValue(loanAmount) ).isTrue();
        assertThat( smavaHomePage.clickJetztVergleichenToStartWithKreditStep() ).isTrue();
    }

    @Step
    public void user_performs_vergleichen_action(){
        assertThat( smavaHomePage.clickJetztVergleichenToStartWithKreditStep() ).isTrue();
    }

    @Step
    public void verify_user_lands_on_kredit_step(){
        assertThat( smavaLoanSearchKreditPage.checkKreditStepPage() ).isTrue();
    }

    @Step
    public void verify_invalid_user_login(){
        assertThat( smavaHomePage.enterLoginDetailsAndSubmit(invalidEmail,invalidPassword) ).isTrue();
    }

    @Step
    public void verify_invalid_login_error_page(){
        assertThat( smavaLoginErrorPage.isErrorMessagePresent() ).isTrue();
    }

    @Step
    public void verifyGTMDataForSmavaHomePage(){
        assertThat( smavaWebAnalyticsUtility.fetchGTMObjectsForGivenPage("SmavaHomePage") ).isTrue();
        smavaWebAnalyticsUtility.verifyGTM_KeyValuePair(HomePageGMTObjects);
    }

}
