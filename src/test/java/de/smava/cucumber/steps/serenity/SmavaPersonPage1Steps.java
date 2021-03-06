package de.smava.cucumber.steps.serenity;

import static org.assertj.core.api.Assertions.assertThat;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import de.smava.cucumber.pages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmavaPersonPage1Steps extends ScenarioSteps {
    private PersonStep1Page personStep1Page;
    private SmavaWebAnalyticsUtility smavaWebAnalyticsUtility;

    private static List<List<String>> personStep1PageGMTObjects = new ArrayList<List<String>>();
    static {
        {
            personStep1PageGMTObjects.add(Arrays.asList("pageId", "ContactInfo"));
            personStep1PageGMTObjects.add(Arrays.asList("event", "registration_page"));
            personStep1PageGMTObjects.add(Arrays.asList("flow", "V1"));
            personStep1PageGMTObjects.add(Arrays.asList("pageNo", "2"));
            personStep1PageGMTObjects.add(Arrays.asList("pageTitle", "Person"));
            personStep1PageGMTObjects.add(Arrays.asList("requested_amount", "500"));
            personStep1PageGMTObjects.add(Arrays.asList("requested_duration", "84"));
            personStep1PageGMTObjects.add(Arrays.asList("requested_category", "888"));
        }
    }

    @Step
    public void isPersonPage1StepCompletelyLoaded(){
        assertThat( personStep1Page.isPersonStep1PageLoaded() ).isTrue();
    }

    @Step
    public void completePersonPage1Steps(){
        personStep1Page.fillPersonStep1PageInfoAndGotoNextPage();
    }

    @Step
    public void completePersonPage1StepsWithExistingEmail(boolean isCookiesSet){
        personStep1Page.fillPersonStep1PageInfoAndGotoNextPageWithSameEmailId(isCookiesSet);
    }

    @Step
    public void verifyGTMDataForSmavaPersonPage1Steps(){
        assertThat( personStep1Page.isPersonStep1PageLoaded() ).isTrue();
        assertThat( smavaWebAnalyticsUtility.fetchGTMObjectsForGivenPage("SmavaPersonStep1Page") ).isTrue();
        smavaWebAnalyticsUtility.verifyGTM_KeyValuePair(personStep1PageGMTObjects);
    }

    @Step
    public void verifyGTMDataForSmavaPersonStep1Page_AgainstNonEmptyValues(List<List<String>> data){
        //assertThat( smavaWebAnalyticsUtility.fetchGTMObjectsForGivenPage("SmavaPersonStep1Page") ).isTrue();
        smavaWebAnalyticsUtility.verifyGTM_ValueIsNotNull(data);
    }

}
