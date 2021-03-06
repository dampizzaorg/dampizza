/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.views.custom;

import com.dampizza.cfg.AppConstants;
import com.dampizza.logic.dto.ProductDTO;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

/**
 *
 * @author Jon Xabier Gimenez
 */
public class ProductCLV extends CharmListCell<ProductDTO> {

    private final ListTile tile;
    private final ImageView imageView;

    public ProductCLV() {
        this.tile = new ListTile();
        imageView = new ImageView();
        tile.setPrimaryGraphic(imageView);
        setText(null);
    }

    @Override
    public void updateItem(ProductDTO item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
//            final Image image = new Image(item.getUrl(),50,50,false,false);
            if (item.getCategory() == AppConstants.PRODUCT_DRINK) {
                final Image image = new Image("/img/cola.png", 50, 50, false, false);
                if (image != null) {
                    imageView.setImage(image);
                }
            }else{
                final Image image = new Image("/img/pizza_margarita.png", 50, 50, false, false);
                if (image != null) {
                    imageView.setImage(image);
                }
            }
                

            tile.textProperty().setAll(item.getName() + "    " + item.getPrice(), item.getDescription());
            setGraphic(tile);
        } else {
            setGraphic(null);
        }
    }

}
