package uz.rdu.nexign.hasinterface.ui.c1;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = "Report", layout = C1MainInterface.class)
@CssImport(
        themeFor = "vaadin-grid",
        value = "./styles/main.css"
)
public class TerminationReport extends VerticalLayout {




}
