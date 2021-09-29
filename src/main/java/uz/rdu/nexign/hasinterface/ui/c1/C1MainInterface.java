package uz.rdu.nexign.hasinterface.ui.c1;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.AbstractTheme;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.HashMap;
import java.util.Map;

@Route
public class C1MainInterface extends AppLayout implements BeforeEnterObserver {

    private Tabs tabs = new Tabs();
    private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

    public C1MainInterface() {
        setPrimarySection(AppLayout.Section.DRAWER);
        addToNavbar(new DrawerToggle(), new H3("C1 Terminal"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        //Menu Tabs
        addMenuTab("Balances", BalancesMenu.class);
        addMenuTab("Report",TerminationReport.class);
        addToDrawer(tabs);
    }

    private void addMenuTab(String label, Class<? extends Component> target) {
        Tab tab = new Tab(new RouterLink(label, target));
        navigationTargetToTab.put(target, tab);
        tabs.add(tab);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        tabs.setSelectedTab(navigationTargetToTab.get(beforeEnterEvent.getNavigationTarget()));
    }
}
