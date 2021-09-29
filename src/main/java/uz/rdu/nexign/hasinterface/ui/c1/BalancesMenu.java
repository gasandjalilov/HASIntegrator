package uz.rdu.nexign.hasinterface.ui.c1;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.DefneDAO;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.Subscriber;
import uz.rdu.nexign.hasinterface.model.DAO.mainDS.cOne.SubscriberData;
import uz.rdu.nexign.hasinterface.service.c1.ProcedureRequestsImpl;

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
    Grid<SubscriberData> mainData = new Grid<SubscriberData>(SubscriberData.class);
    ListDataProvider<SubscriberData> dataProvider = (ListDataProvider<SubscriberData>) mainData.getDataProvider();

    //Extended Data
    TextField primaryOffer = new TextField("Primary Offer");
    TextField availBalance = new TextField("Available Balance");
    TextField state = new TextField("State");
    TextField defneData = new TextField("Defne Data");
    //Termination
    Button terminationButton = new Button("Terminate");

    /*
    KeycloakPrincipal principal =
            (KeycloakPrincipal) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

    KeycloakSecurityContext keycloakSecurityContext =
            principal.getKeycloakSecurityContext();

    String preferredUsername = keycloakSecurityContext.getIdToken().getPreferredUsername();

     */
    public BalancesMenu(@Autowired ProcedureRequestsImpl procedureRequests) {
        this.procedureRequests = procedureRequests;
        mainData.setHeightByRows(true);
        VerticalLayout layout = new VerticalLayout(formLayout(),setExternalData(),mainData);
        this.add(layout);
        //log.info("PreferredUsername: {}", preferredUsername);
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
        }
        else primaryOffer.setInvalid(false);
        //check available balance
        if(Double.valueOf(availBalance.getValue())<0){
            availBalance.setInvalid(true);
            primaryOffer.setErrorMessage("У данного пользователя отрицательный баланс");
        }
        else availBalance.setInvalid(false);
        //check Defne Data
        Optional<DefneDAO> optionalDefneDAO = procedureRequests.getDefneData(msisdn);
        optionalDefneDAO.ifPresentOrElse(present ->{
            defneData.setValue("Remaining Service Fee"+present.getRemaingServiceFee());
            defneData.setInvalid(true);
        },()->{
            defneData.setValue("");
            defneData.setInvalid(false);
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
            Dialog alarm = new Dialog();
            alarm.add(new Text("No Data For Selected MSISDN"));
            alarm.open();
        }
    }

    /**
     * Data Search FormLayout
     *
     * @return {@link FormLayout}
     */
    private FormLayout formLayout(){
        FormLayout msisdnLayout = new FormLayout();
        TextField msisdnField = new TextField();
        msisdnField.setLabel("MSISDN");
        msisdnField.setPlaceholder("9989XXXXXXXX");
        Button save = new Button("Search");
        save.addClickListener(event -> {
            balancesGet(msisdnField.getValue());
        });
        msisdnLayout.addFormItem(msisdnField, "MSISDN");
        msisdnLayout.addFormItem(save,"Search");
        return msisdnLayout;
    }

    /**
     * sort grid and highlight balances that ar less than 0
     *
     *
     */
    private void sortGrid(){

        mainData.setClassNameGenerator(balance -> {
                String tmpBalance = balance.getAvailableBalanceSrc().replace(",",".");
                if(Float.valueOf(tmpBalance)<0 | balance.getBalanceName().equals("MMS BALANCE 7")){
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

        extendedDataForm.addFormItem(primaryOffer,"Primary Offer");
        extendedDataForm.addFormItem(state, "Subscriber State");
        extendedDataForm.addFormItem(availBalance, "Available Balance");
        extendedDataForm.addFormItem(defneData, "Defne Data");
        extendedDataForm.addFormItem(terminationButton,"Terminate Subscriber");

        defneData.setReadOnly(true);
        primaryOffer.setReadOnly(true);
        state.setReadOnly(true);
        availBalance.setReadOnly(true);

        return extendedDataForm;
    }


    private void testOnClickOfElement(){
        if(primaryOffer.isInvalid()){
            primaryOffer.setErrorMessage("У данного пользователя используется ТП BN");
        }
    }


}
