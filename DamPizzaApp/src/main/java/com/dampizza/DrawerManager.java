package com.dampizza;

import static com.dampizza.App.CART_VIEW;
import static com.dampizza.App.CUSTOMER_VIEW;
import static com.dampizza.App.DEALER_VIEW;
import static com.dampizza.App.HISTORY_VIEW;
import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.dampizza.App.MENU_LAYER;
import static com.dampizza.App.LOGIN_VIEW;
import static com.dampizza.App.MANAGER_VIEW;
import static com.dampizza.App.MANAGER_ORDER_VIEW;
import static com.dampizza.App.ORDER_CREATE_VIEW;
import static com.dampizza.App.PROFILE_VIEW;
import static com.dampizza.App.RECOVER_VIEW;
import static com.dampizza.App.SIGNUP_VIEW;
import static com.dampizza.App.MANAGER_DEALER_VIEW;
import static com.dampizza.App.PIZZA_CREATE_VIEW;
import static com.dampizza.App.PIZZA_DELETE_VIEW;
import com.dampizza.cfg.AppConstants;
import com.dampizza.util.LogicFactory;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * Drawer Manager. Manage drawer items and behavior.
 *
 * @author Carlos Santos
 */
public class DrawerManager {

    private final NavigationDrawer drawer;
    private static Integer userType = 1;

    public DrawerManager(Integer status) {
        this.drawer = new NavigationDrawer();
        userType = status;
    }

    public DrawerManager() {
        this.drawer = new NavigationDrawer();
        
        userType = (Integer)LogicFactory.getUserManager().getSession().get("type");
        System.out.println(userType);

        NavigationDrawer.Header header = new NavigationDrawer.Header("DamPizza",
                "Mobile App",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/img/pizza_avatar_128.png"))));
        drawer.setHeader(header);

        /* CREATE NEW DRAWER ITEMS HERE*/
        /* CAMBIAR MENU DEPENDIENDO DEL USUARIO*/
        if (userType == AppConstants.USER_CUSTOMER) {
            System.out.println("customer type");
            final Item customerItem = new ViewItem("Inicio", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), CUSTOMER_VIEW);
            final Item orderItem = new ViewItem("Hacer pedido", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), ORDER_CREATE_VIEW);
            final Item historyItem = new ViewItem("Mis pedidos", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), HISTORY_VIEW);
            final Item profileItem = new ViewItem("Perfil", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PROFILE_VIEW);
            final Item logoutItem = new ViewItem("Salir", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), LOGIN_VIEW);
            final Item cartItem = new ViewItem("Carrito", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), CART_VIEW);
            final Item ownPizzaCreate= new ViewItem("Crear pizza", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PIZZA_CREATE_VIEW);

            /* REMEMBER TO ADD ITEMS TO THE DRAWER */
            drawer.getItems().clear();
            drawer.getItems().addAll(customerItem, orderItem, ownPizzaCreate, historyItem, profileItem, cartItem, logoutItem);
        } else if (userType == AppConstants.USER_MANAGER) {
            System.out.println("manager type");
            final Item managerItem = new ViewItem("Pedidos", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), MANAGER_VIEW);
//            final Item managerOrderItem = new ViewItem("Manager Order", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), MANAGER_ORDER_VIEW);
            //final Item registerItem = new ViewItem("Register a dealer", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), SIGNUP_VIEW);
            final Item managerDealerItem = new ViewItem("Repartidores", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), MANAGER_DEALER_VIEW);
            final Item profileItem = new ViewItem("Perfil", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PROFILE_VIEW);
            final Item logoutItem = new ViewItem("Salir", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), LOGIN_VIEW);
            final Item pizzaCreateItem= new ViewItem("Crear Pizza", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PIZZA_CREATE_VIEW);
            final Item pizzaDeleteItem = new ViewItem("Eliminar Pizza", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PIZZA_DELETE_VIEW);
            
            drawer.getItems().addAll(managerItem, pizzaCreateItem ,pizzaDeleteItem,profileItem,managerDealerItem,logoutItem);

        } else if (userType == AppConstants.USER_DEALER){
            System.out.println("dellivery type");
            final Item profileItem = new ViewItem("Perfil", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), PROFILE_VIEW);
            final Item dealerItem = new ViewItem("Pedidos", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), DEALER_VIEW);
            final Item logoutItem = new ViewItem("Salir", MaterialDesignIcon.ACCOUNT_CIRCLE.graphic(), LOGIN_VIEW);
            
            drawer.getItems().addAll(dealerItem, profileItem,logoutItem);
        }

        if (Platform.isDesktop()) {
//            final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
//            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
//                if (nv) {
//                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
//                }
//            });
            //drawer.getItems().add(quitItem);
        }

        drawer.addEventHandler(NavigationDrawer.ITEM_SELECTED,
                e -> MobileApplication.getInstance().hideLayer(MENU_LAYER));

        MobileApplication.getInstance().viewProperty().addListener((obs, oldView, newView) -> updateItem(newView.getName()));
        //updateItem(LOGIN_VIEW);
    }

    /**
     * Update item
     *
     * @param viewName
     */
    private void updateItem(String viewName) {
        for (Node item : drawer.getItems()) {
            if (item instanceof ViewItem && ((ViewItem) item).getViewName().equals(viewName)) {
                drawer.setSelectedItem(item);
                break;
            }
        }
    }

    /**
     * Update view
     *
     * @param Item
     */
    public void updateView(Item Item) {
        drawer.setSelectedItem(Item);

    }

    /**
     * Returns Drawer
     *
     * @return NavigationDrawer object
     */
    public NavigationDrawer getDrawer() {
        return drawer;
    }
}
