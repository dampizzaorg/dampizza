/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.views.user.manager;

import com.dampizza.views.custom.ProductCLV;
import com.dampizza.App;
import static com.dampizza.App.MANAGER_VIEW;
import static com.dampizza.App.MANAGER_DEALER_VIEW;
import static com.dampizza.App.MANAGER_VIEW;
import static com.dampizza.App.ORDER_CREATE_VIEW;
import com.dampizza.DrawerManager;
import com.dampizza.cfg.AppConstants;
import com.dampizza.exception.order.OrderQueryException;
import com.dampizza.exception.order.OrderUpdateException;
import com.dampizza.exception.user.UserQueryException;
import com.dampizza.logic.dto.OrderDTO;
import com.dampizza.logic.dto.UserDTO;
import com.dampizza.logic.dto.ProductDTO;
import com.dampizza.logic.imp.OrderManagerImp;
import com.dampizza.logic.imp.UserManagerImp;
import com.dampizza.util.LogicFactory;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 *
 * @author 2dam
 */
public class ManagerOrderPresenter {

    private HashMap SESSION;
    private ObservableList<ProductDTO> orderProducts;
    private ObservableList<UserDTO> dealers;
    private OrderDTO order;

    @FXML
    private View primary;

    @FXML
    private TextArea taOrder;

    @FXML
    private Button btnSelect, btnConfirm;

    @FXML
    private ComboBox<UserDTO> cbDealer;

    @FXML
    private CharmListView<ProductDTO, ? extends Comparable> lvProducts;

    public void initialize() {

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {

                AppBar appBar = MobileApplication.getInstance().getAppBar();

                appBar.setVisible(true);

                appBar.getActionItems().add(MaterialDesignIcon.ARROW_BACK.button(e
                        -> back()));
                appBar.setTitleText("Manager order");

                SESSION = LogicFactory.getUserManager().getSession();
                order = (OrderDTO) SESSION.get("transferOrder");

                if (order != null) {
                    try {
                        lvProducts.setCellFactory(p -> new ProductCLV());
                        taOrder.setEditable(false);
                        taOrder.setText(order.getId() + "\n" + order.getDate() + "\n" + order.getAddress());
                        orderProducts = FXCollections.observableArrayList(order.getProducts());
                        lvProducts.setItems(orderProducts);
                        
                        dealers = FXCollections.observableArrayList(LogicFactory.getUserManager().getUsersByType(AppConstants.USER_DEALER));
                        cbDealer.setItems(dealers);
                    } catch (UserQueryException ex) {
                        Logger.getLogger(ManagerOrderPresenter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    back();
                }

            }
        });

    }

    @FXML
    public void confirm() throws OrderQueryException {
        try {
            Integer res = LogicFactory.getOrderManager().updateDealer(order.getId(), cbDealer.getValue().getId());
            back();
        } catch (OrderUpdateException ex) {
            Logger.getLogger(ManagerOrderPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void back() {
        //Metodo que te lleva a la ventana anterior
//        NavigationDrawer.ViewItem Item = new NavigationDrawer.ViewItem("Select", MaterialDesignIcon.HOME.graphic(), MANAGER_VIEW, ViewStackPolicy.SKIP);
//        DrawerManager drawer = new DrawerManager();
//        drawer.updateView(Item);
        MobileApplication.getInstance().switchView(MANAGER_VIEW);
    }

    /**
     * @return the order
     */
    public OrderDTO getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    private ArrayList<String> getNames(ObservableList<UserDTO> dealerList) {
        ArrayList<String> names = new ArrayList<>();
        //for each UserDTO object take the name and surnames on a string and add to the name list
        dealerList.forEach((dealer) -> {
            names.add(dealer.getName() + " " + dealer.getSurnames());
        });
        return names;
    }

}
