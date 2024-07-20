package com.main.airbnb.service;

import com.main.airbnb.entity.Favourite;
import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.User;
import com.main.airbnb.exception.PropertyNotFoundException;
import com.main.airbnb.repository.FavouriteRepository;
import com.main.airbnb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FavouriteServiceImpl implements FavouriteService{
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    public FavouriteServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Favourite addFavourite(User user, Boolean status, String propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property not found")
        );
        Optional<Favourite> opFavourite = favouriteRepository.findByUserAndProperty(user, property);
        if(opFavourite.isPresent()){
            Favourite favourite = opFavourite.get();
            favourite.setStatus(status);
            return favouriteRepository.save(favourite);
        }
        Favourite favourite = new Favourite();
        String favouriteId = UUID.randomUUID().toString();
        favourite.setId(favouriteId);
        favourite.setStatus(status);
        favourite.setUser(user);
        favourite.setProperty(property);
        return favouriteRepository.save(favourite);

    }

    @Override
    public Favourite removeFavourite(User user, Boolean status, String propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property not found")
        );
        Optional<Favourite> opFavourite = favouriteRepository.findByUserAndProperty(user, property);
        Favourite favourite = opFavourite.get();
        favourite.setStatus(status);
        return favouriteRepository.save(favourite);
    }
}