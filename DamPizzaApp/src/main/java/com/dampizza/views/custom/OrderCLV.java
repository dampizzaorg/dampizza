/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.views.custom;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;
import com.dampizza.logic.dto.OrderDTO;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author Jon Xabier Gimenez
 */
public class OrderCLV extends CharmListCell<OrderDTO>{
    
    private final ListTile tile;
    private final ImageView imageView;

    public OrderCLV() {
        this.tile = new ListTile();
        imageView = new ImageView();
        tile.setPrimaryGraphic(imageView);
        setText(null);
    }

    @Override
    public void updateItem(OrderDTO item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            final Image image = new Image("/img/order.png", 50, 50, false, false);
            if (image != null) {
                imageView.setImage(image);
            }
            tile.textProperty().setAll(item.getId()+"  "+item.getDate(),item.getAddress());
            setGraphic(tile);
        } else {
            setGraphic(null);
        }
    }
    
}
