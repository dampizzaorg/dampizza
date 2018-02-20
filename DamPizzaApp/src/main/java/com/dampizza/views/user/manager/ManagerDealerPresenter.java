/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.views.user.manager;

import com.dampizza.views.custom.userList;
import com.dampizza.App;
import static com.dampizza.App.MANAGER_ADD_DEALER_VIEW;
import static com.dampizza.App.MANAGER_VIEW;
import static com.dampizza.App.MANAGER_DEALER_VIEW;
import static com.dampizza.App.MANAGER_VIEW;
import static com.dampizza.App.ORDER_CREATE_VIEW;
import com.dampizza.DrawerManager;
import com.dampizza.cfg.AppConstants;
import com.dampizza.exception.order.OrderUpdateException;
import com.dampizza.exception.user.UserDeleteException;
import com.dampizza.exception.user.UserQueryException;
import com.dampizza.exception.user.UserUpdateException;
import com.dampizza.logic.dto.UserDTO;
import com.dampizza.logic.dto.ProductDTO;
import com.dampizza.logic.imp.OrderManagerImp;
import com.dampizza.logic.imp.UserManagerImp;
import com.dampizza.util.LogicFactory;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.Toast;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 *
 * @author Jon Xabier Gimenez
 */
public class ManagerDealerPresenter {

    private ObservableList<UserDTO> oblUsers;

    @FXML
    private View primary;

    @FXML
    private Button btnAddDealer;

    @FXML
    private Button btnDeleteDealer;

    @FXML
    private CharmListView<UserDTO, ? extends Comparable> lvDealers;

    public void initialize() {

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {

                AppBar appBar = MobileApplication.getInstance().getAppBar();

                appBar.setVisible(true);

                appBar.setTitleText("Repartidores");

                try {
                    lvDealers.setCellFactory(p -> new userList());
                    oblUsers = FXCollections.observableArrayList(LogicFactory.getUserManager().getUsersByType(AppConstants.USER_DEALER));
                    oblUsers.forEach(u -> System.out.println(u.getId()));
                    
                    lvDealers.setItems(oblUsers);
                    
                } catch (UserQueryException ex) {
                    Logger.getLogger(ManagerDealerPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    @FXML
    public void handlerAddDealer() {
//        NavigationDrawer.ViewItem Item = new NavigationDrawer.ViewItem("Select", MaterialDesignIcon.HOME.graphic(), MANAGER_ADD_DEALER_VIEW, ViewStackPolicy.SKIP);
//        DrawerManager drawer = new DrawerManager();
//        drawer.updateView(Item);
        MobileApplication.getInstance().switchView(MANAGER_ADD_DEALER_VIEW);
    }

    @FXML
    public void handlerDeleteDealer() throws UserDeleteException, UserUpdateException, UserQueryException {
        Alert alert;
        UserDTO user = new UserDTO();
        user = lvDealers.getSelectedItem();
        if (user != null) {
            //Esto quiere decir que ha elegido alguno mensaje de confirmacion y borrar
            alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, user.getUsername() + " sera eliminado, ¿Estas seguro?");
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.get().getButtonData().isCancelButton()) {
                Integer res = LogicFactory.getUserManager().setActive(user.getId(), Boolean.FALSE);
                if (res == 1) {
                    lvDealers.itemsProperty().remove(user);
                    new Toast("Usuario: <" + user.getUsername() + "> eliminado.").show();
                } else if (res == 2) {
                    new Toast("Usuario no registrado.").show();
                } else {
                    new Toast("Ha ocurrido un error.").show();
                }
            }
        } else {
            alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Debes seleccionar un repartidor primero.");
        }
    }
}
