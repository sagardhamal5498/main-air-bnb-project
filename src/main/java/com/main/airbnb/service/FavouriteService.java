package com.main.airbnb.service;

import com.main.airbnb.entity.Favourite;
import com.main.airbnb.entity.User;

public interface FavouriteService {
    Favourite addFavourite(User user, Boolean status, String propertyId);

    Favourite removeFavourite(User user, Boolean status, String propertyId);
}