package uz.rdu.nexign.hasinterface.ui.c1;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.Opt;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.DefneDAO;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.Subscriber;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.SubscriberData;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.PostPaidSubscriberDAO;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.PrePaidSubscriberDAO;
import uz.rdu.nexign.hasinterface.model.DAO.secondaryDS.SubscriberTerminationData;
import uz.rdu.nexign.hasinterface.model.DTO.coin.CoinDbResponse;
import uz.rdu.nexign.hasinterface.service.c1.ProcedureRequestsImpl;
import uz.rdu.nexign.hasinterface.service.coin.CoinService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Route(value = "balances", layout = C1MainInterface.class)
@CssImport(
        themeFor = "vaadin-grid",
        value = "./styles/main.css"
)
public class BalancesMenu extends Div {


    @Value("${bn.po}'.split(',')")
    List<String> poIDs;

    ProcedureRequestsImpl procedureRequests;
    CoinService coinService;

    Grid<SubscriberData> mainData = new Grid<SubscriberData>(SubscriberData.class);
    ListDataProvider<SubscriberData> dataProvider = (ListDataProvider<SubscriberData>) mainData.getDataProvider();

    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    //Extended Data
    TextField primaryOffer = new TextField("Primary Offer");
    TextField availBalance = new TextField("Available Balance");
    TextField state = new TextField("State");
    TextField defneData = new TextField("Defne Data");
    //Termination
    Button terminationButton = new Button("Terminate");
    //
    TextField msisdnField = new TextField();
    TextField subscriberPnfl = new TextField("PINFL");
    TextField susbcriberDocNumber = new TextField("Document number");
    TextField susbcriberPasSeries = new TextField("Passport Series");
    TextField susbcriberPasNumber = new TextField("Passport Series");
    TextField susbcriberINN = new TextField("INN");
    //
    String username = "";
    boolean balance = true;
    boolean supBalances = true;
    boolean offer = true;
    boolean defne = true;

    public BalancesMenu(@Autowired ProcedureRequestsImpl procedureRequests, @Autowired CoinService coinService) {
        this.procedureRequests = procedureRequests;
        this.coinService = coinService;
        mainData.setHeightByRows(true);
        VerticalLayout layout = new VerticalLayout(formLayout(),setExternalData(),mainData);
        this.add(layout);
        try {
            KeycloakPrincipal principal =
                    (KeycloakPrincipal) SecurityContextHolder.getContext()
                            .getAuthentication().getPrincipal();

            KeycloakSecurityContext keycloakSecurityContext =
                    principal.getKeycloakSecurityContext();
            this.username = keycloakSecurityContext.getIdToken().getPreferredUsername();
            log.info("PreferredUsername: {}", username);
        }catch (Exception e)
        {
            log.error("Getting Security Context: {}", e.getLocalizedMessage());
        }
    }


    /**
     * Main method to get data from serviceDb
     *
     * @param msisdn
     */
    private void balancesGet(String msisdn){
        try {
        Subscriber subscriber = procedureRequests.GetData(msisdn);
        log.info("Subscriber: {}", subscriber);
        primaryOffer.setValue(subscriber.getPrimaryOffer());
        availBalance.setValue(subscriber.getAvailBalance());
        state.setValue(String.valueOf(subscriber.getState()));
        //check PO
        if(poIDs.contains(subscriber.getPrimaryOffer())) {
            primaryOffer.setInvalid(true);
            primaryOffer.setErrorMessage("У данного пользователя используется ТП BN");
            offer = false;
        }
        else {
            primaryOffer.setInvalid(false);
            offer = true;
        }
        //check available balance
        if(Double.valueOf(availBalance.getValue())<0){
            availBalance.setInvalid(true);
            balance = false;
            primaryOffer.setErrorMessage("У данного пользователя отрицательный баланс");
        }
        else {
            balance = true;
            availBalance.setInvalid(false);
        }
        //check Defne Data
        Optional<DefneDAO> optionalDefneDAO = procedureRequests.getDefneData(msisdn);
        optionalDefneDAO.ifPresentOrElse(present ->{
            defneData.setValue("Remaining Service Fee"+present.getRemaingServiceFee());
            defneData.setInvalid(true);
            defne = false;
        },()->{
            defneData.setValue("");
            defneData.setInvalid(false);
            defne=true;
        });
        //
        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(subscriber.getData());
        mainData.getDataProvider().refreshAll();
        sortGrid();
            testOnClickOfElement();
        mainData.getColumns().forEach(personColumn -> personColumn.setAutoWidth(true));
        }
        catch (NullPointerException nullPointerException){
            log.error("Null Pointer Exception: {}, MSISDN: {}",nullPointerException.getLocalizedMessage(),msisdn);
            errorDialog("No data for selected MSISDN");
        }
    }

    /**
     * Data Search FormLayout
     *
     * @return {@link FormLayout}
     */
    private FormLayout formLayout(){
        FormLayout msisdnLayout = new FormLayout();
        msisdnField.setLabel("MSISDN");
        msisdnField.setPlaceholder("9989XXXXXXXX");
        Button save = new Button("Search");
        save.addClickListener(event -> {
            if(subscriberPnfl.getOptionalValue().isEmpty()
                    & susbcriberDocNumber.getOptionalValue().isEmpty()
                    & (susbcriberPasSeries.getOptionalValue().isEmpty() | susbcriberPasNumber.getOptionalValue().isEmpty())
                    & susbcriberINN.getOptionalValue().isEmpty()
            ) errorDialog("No Data Presented");
            else {
                if(radioGroup.getValue().equals("PrePaid")){
                    Optional<PrePaidSubscriberDAO> subscriber = prePaidDataGet(msisdnField.getValue());
                    subscriber.ifPresentOrElse(present->{
                        subscriberPnfl.getOptionalValue().ifPresent(exists -> {
                            if(exists.equals(present.getPinfl())) balancesGet(msisdnField.getValue());
                            else subscriberPnfl.setInvalid(true);
                        });
                        susbcriberDocNumber.getOptionalValue().ifPresent(exists -> {
                            if(exists.equals(present.getDocumentNumber())) balancesGet(msisdnField.getValue());
                            else susbcriberDocNumber.setInvalid(true);
                        });
                        susbcriberPasSeries.getOptionalValue().ifPresent(exists -> {
                            susbcriberPasNumber.getOptionalValue().ifPresent(pas -> {
                                if(exists.equals(present.getPassportSeries()) & pas.equals(present.getDocumentNumber())){
                                    balancesGet(msisdnField.getValue());
                                }
                                else {
                                    susbcriberPasNumber.setInvalid(true);
                                    susbcriberPasSeries.setInvalid(true);
                                }
                            });
                        });

                        },()->{
                        errorDialog("Data not found in DB");

                    });
                }
                else {
                    Optional<PostPaidSubscriberDAO> subscriber = postPaidDataGet(msisdnField.getValue());
                    subscriber.ifPresentOrElse(present->{
                        susbcriberINN.getOptionalValue().ifPresentOrElse(exists -> {
                                    balancesGet(msisdnField.getValue());
                                },
                                ()-> errorDialog("No INN Data"));
                    },()->{
                        errorDialog("Data not found in DB");
                    });
                }

            }
        });

        msisdnLayout.add(msisdnField, subscriberType());
        //Data
        subscriberPnfl.setVisible(false);
        susbcriberDocNumber.setVisible(false);
        susbcriberPasSeries.setVisible(false);
        susbcriberPasNumber.setVisible(false);
        susbcriberINN.setVisible(false);


        msisdnLayout.add(subscriberPnfl,susbcriberDocNumber,susbcriberPasSeries,susbcriberPasNumber,susbcriberINN);
        //
        msisdnLayout.add(save);
        return msisdnLayout;
    }

    /**
     * sort grid and highlight balances that ar less than 0
     *
     *
     */
    private void sortGrid(){
        supBalances = true;
        mainData.setClassNameGenerator(balance -> {
                String tmpBalance = balance.getAvailableBalanceSrc().replace(",",".");
                if(Float.valueOf(tmpBalance)<0 | balance.getBalanceName().equals("MMS BALANCE 7")){
                    supBalances = false;
                    return "warn";
                }
                else return null;
        });
    }

    /**
     * set external data, will add PO and state info
     *
     * @return {@link FormLayout}
     */
    private FormLayout setExternalData(){
        FormLayout extendedDataForm = new FormLayout();
        terminationButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        terminationButton.addClickListener(present -> terminateUserButton(msisdnField.getValue()));
        extendedDataForm.add(primaryOffer,state,availBalance,defneData,terminationButton);
        defneData.setReadOnly(true);
        primaryOffer.setReadOnly(true);
        state.setReadOnly(true);
        availBalance.setReadOnly(true);

        return extendedDataForm;
    }

    private void terminateUserButton(String msisdn){
        if(!supBalances) errorDialog("Fix supplementary balance");
        else if(!balance) errorDialog("Balance not valid");
        else if(!offer) errorDialog("Primary Offer is BN");
        else if(!defne) errorDialog("This subscriber has a debt(Defne)");
        else if(supBalances & balance & offer & defne) {
            CoinDbResponse response = terminateUser(msisdn);
            errorDialog(response.getResponse());
            SubscriberTerminationData data = new SubscriberTerminationData();
            data.setMessage(response.getMessage());
            data.setUsername(username);
            data.setCreateDate(new Date());
            data.setMsisdn(msisdn);
            data.setCode(1);
            coinService.saveTermination(data);
        };
    }

    private void testOnClickOfElement(){
        if(primaryOffer.isInvalid()){
            primaryOffer.setErrorMessage("У данного пользователя используется ТП BN");
        }
    }

    private RadioButtonGroup<String> subscriberType(){
        radioGroup.setLabel("Subscriber Type");
        radioGroup.setItems("PrePaid", "PostPaid");
        radioGroup.addValueChangeListener(listener -> {
            if(listener.getValue().equals("PrePaid")){
                subscriberPnfl.setVisible(true);
                susbcriberDocNumber.setVisible(true);
                susbcriberPasSeries.setVisible(true);
                susbcriberPasNumber.setVisible(true);
                susbcriberINN.setVisible(false);
            }
            else {
                subscriberPnfl.setVisible(true);
                susbcriberDocNumber.setVisible(false);
                susbcriberPasSeries.setVisible(false);
                susbcriberPasNumber.setVisible(false);
                susbcriberINN.setVisible(true);
            }
        });
        return radioGroup;
    }

    private Optional<PostPaidSubscriberDAO> postPaidDataGet(String msisdn){
        return coinService.getPostPaidData(msisdn);
    }

    private Optional<PrePaidSubscriberDAO> prePaidDataGet(String msisdn){
        return coinService.getPrePaidData(msisdn);
    }

    private CoinDbResponse terminateUser(String msisdn){
        return coinService.terminateSubscriberFromCoinDB(msisdn,"7");
    }


    public void errorDialog(String message){
        Dialog alarm = new Dialog();
        alarm.add(new Text(message));
        alarm.open();
    }

}
