/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.views.user.customer;

import com.dampizza.App;
import com.dampizza.DrawerManager;
import com.dampizza.exception.order.OrderQueryException;
import com.dampizza.logic.dto.OrderDTO;
import com.dampizza.logic.imp.OrderManagerImp;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import static com.dampizza.App.MANAGER_ORDER_VIEW;
import static com.dampizza.App.HISTORY_DETAIL;
import com.dampizza.util.LogicFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class HistoryPresenter implements Initializable {

    private OrderManagerImp omi;
    private ObservableList<OrderDTO> oblOrders;
    private ObservableList<String> names;

    @FXML
    private CharmListView<OrderDTO, ? extends Comparable> lvOrders;
    @FXML
    private View primary;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();

                appBar.setVisible(true);

                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(App.MENU_LAYER)));
                appBar.setTitleText("Mis pedidos");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e
                        -> System.out.println("Buscar")));
                omi = LogicFactory.getOrderManager();
                initClassic();
            }
        });

    }

    public void initClassic() {
        try {
            oblOrders = FXCollections.observableArrayList(omi.getAllOrdersByUser());
        } catch (OrderQueryException ex) {
            Logger.getLogger(HistoryPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (oblOrders != null) {
            lvOrders.setItems(oblOrders);
            lvOrders.setCellFactory(p -> new historyList());
            lvOrders.selectedItemProperty().addListener((obs, ov, nv) -> {
                //Cargar la order en una constante
                App.setCurrentOrder(lvOrders.getSelectedItem());
                NavigationDrawer.ViewItem Item = new NavigationDrawer.ViewItem("Select", MaterialDesignIcon.HOME.graphic(), HISTORY_DETAIL, ViewStackPolicy.SKIP);
                DrawerManager drawer = new DrawerManager();
                drawer.updateView(Item);
            });
        }
    }
}
