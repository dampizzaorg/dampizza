package com.dampizza.views.user.manager;

import com.dampizza.views.custom.OrderCLV;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.dampizza.App;
import static com.dampizza.App.MANAGER_ORDER_VIEW;
import com.dampizza.cfg.AppConstants;
import com.dampizza.exception.order.OrderQueryException;
import com.dampizza.exception.order.OrderUpdateException;
import com.dampizza.logic.dto.OrderDTO;
import com.dampizza.logic.imp.OrderManagerImp;
import com.dampizza.util.LogicFactory;
import com.dampizza.views.user.customer.HistoryPresenter;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Presenter for manager_main.fxml
 * TODO: Check button visibility when changing tabs. Sometimes it doesn't work properly.
 * @author Carlos Santos
 */
public class ManagerPresenter {

    private OrderManagerImp orderManager;
    private HashMap SESSION;
    private ObservableList<OrderDTO> oblPrep = FXCollections.observableArrayList(new ArrayList<>());
    private ObservableList<OrderDTO> oblOnDelivery;
    private ObservableList<OrderDTO> oblDelivered;

    @FXML
    private CharmListView<OrderDTO, ? extends Comparable> lvPrep;
    @FXML
    private CharmListView<OrderDTO, ? extends Comparable> lvOnDelivery;
    @FXML
    private CharmListView<OrderDTO, ? extends Comparable> lvDelivered;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelivered;

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabPrep;
    @FXML
    private Tab tabOnDelivery;
    @FXML
    private Tab tabDelivered;

    @FXML
    private View primary;

    public void initialize() {

        SESSION = LogicFactory.getUserManager().getSession();
        orderManager = LogicFactory.getOrderManager();

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();

                appBar.setVisible(true);

                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(App.MENU_LAYER)));
                appBar.setTitleText("Pedidos");
                
                addListeners();
                loadPrep();
                
                tabPane.getSelectionModel().select(0);

            }
        });
    }

    
    public void loadPrep() {
        btnDelivered.setVisible(false);
        btnAdd.setVisible(true);
        btnCancel.setVisible(true);
        try {
            oblPrep = FXCollections.observableArrayList(orderManager.getOrdersByStatus(AppConstants.STATUS_PREPARING));

        } catch (OrderQueryException ex) {
            Logger.getLogger(HistoryPresenter.class.getName()).log(Level.SEVERE, "ERROR", ex);
        }
        lvPrep.setCellFactory(p -> new OrderCLV());
        lvPrep.setItems(oblPrep);
    }

    /**
     * Populate list with orders status = on delivery
     */
    @FXML
    public void loadOnDelivery() {
        btnDelivered.setVisible(true);
        btnAdd.setVisible(false);
        btnCancel.setVisible(false);
        try {
            oblOnDelivery = FXCollections.observableArrayList(orderManager.getOrdersByStatus(AppConstants.STATUS_ONDELIVER));

        } catch (OrderQueryException ex) {
            Logger.getLogger(HistoryPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        lvOnDelivery.setCellFactory(p -> new OrderCLV());
        lvOnDelivery.setItems(oblOnDelivery);
    }

    /**
     * Populate list with orders status = delivered
     */
    @FXML
    public void loadDelivered() {
        btnDelivered.setVisible(false);
        btnAdd.setVisible(false);
        btnCancel.setVisible(false);

        try {
            oblDelivered = FXCollections.observableArrayList(orderManager.getOrdersByStatus(AppConstants.STATUS_DELIVERED));

        } catch (OrderQueryException ex) {
            Logger.getLogger(HistoryPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        lvDelivered.setCellFactory(p -> new OrderCLV());
        lvDelivered.setItems(oblDelivered);
    }

    public void addListeners() {
        
        /**
         * Populate lvPrep when tabPrep is selected
         */
        tabPrep.onSelectionChangedProperty().addListener((ov, oldTab, newTab) -> {
            if(newTab==tabPrep){
                loadPrep();
            }
        });

        /**
         * Enable/disable buttons on listview selected item change.
         */
        lvPrep.selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {

                btnAdd.setDisable(false);
                btnCancel.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                btnCancel.setDisable(true);
            }
        });

        /**
         * Enable/disable buttons on listview selected item change.
         */
        lvOnDelivery.selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (newSelection != null) {
                btnDelivered.setDisable(false);
            } else {
                btnDelivered.setDisable(true);
            }
        });

    }

    /**
     * Add product to order(cart)
     */
    @FXML
    public void btnAddAction() {
        OrderDTO selectedOrder = null;
        if (tabPrep.isSelected()) {
            selectedOrder = lvPrep.getSelectedItem();
        } else if (tabOnDelivery.isSelected()) {
            selectedOrder = lvOnDelivery.getSelectedItem();
        } else if (tabDelivered.isSelected()) {
            selectedOrder = lvDelivered.getSelectedItem();
        }

        if (selectedOrder != null) {
            SESSION.remove("transferOrder");
            SESSION.put("transferOrder", selectedOrder);
            MobileApplication.getInstance().switchView(MANAGER_ORDER_VIEW);
        } else {
            new Toast("Debes seleccionar un pedido primero.").show();
        }
    }

    /**
     * Cancel order(cart)
     */
    @FXML
    public void btnCancelAction() throws OrderUpdateException, OrderQueryException {
        OrderDTO selectedOrder = null;
        if (tabPrep.isSelected()) {
            selectedOrder = lvPrep.getSelectedItem();
        } else if (tabOnDelivery.isSelected()) {
            selectedOrder = lvOnDelivery.getSelectedItem();
        } else if (tabDelivered.isSelected()) {
            selectedOrder = lvDelivered.getSelectedItem();
        }

        if (selectedOrder != null) {
            Integer res = orderManager.updateStatus(selectedOrder.getId(), AppConstants.STATUS_CANCELLED);

            if (res == 1) {
                loadPrep();
                new Toast("Pedido: " + selectedOrder.getId() + " cancelado.").show();
            } else if (res == 2) {
                new Toast("Pedido: " + selectedOrder.getId() + " no encontrado.").show();
            } else {
                new Toast("Ha ocurrido un error.").show();
            }

        } else {
            new Toast("Debes seleccionar un pedido primero.").show();
        }
    }

    /**
     * Cancel order(cart)
     */
    @FXML
    public void btnDeliveredAction() throws OrderUpdateException, OrderQueryException {
        OrderDTO selectedOrder = null;
        if (tabOnDelivery.isSelected()) {
            selectedOrder = lvOnDelivery.getSelectedItem();
        }

        if (selectedOrder != null) {
            Integer res = orderManager.updateStatus(selectedOrder.getId(), AppConstants.STATUS_DELIVERED);

            if (res == 1) {
                loadOnDelivery();
                new Toast("Pedido: " + selectedOrder.getId() + " entregado.").show();
            } else if (res == 2) {
                new Toast("Pedido: " + selectedOrder.getId() + " no encontrado.").show();
            } else {
                new Toast("Ha ocurrido un error.").show();
            }

        } else {
            new Toast("Debes seleccionar un pedido primero.").show();
        }
    }
}
