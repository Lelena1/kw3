package me.luppolem.socksapp.services;

import me.luppolem.socksapp.model.Color;
import me.luppolem.socksapp.model.Size;
import me.luppolem.socksapp.model.Socks;

import java.util.Collection;

public interface SocksService {

    Socks addSocks(Socks socks);

    Socks getSocks(Integer id);


    Collection<Socks> getAllSocks();


    Socks removeSocks(Socks socks);


    boolean updateSocks(Color color, Size size, int cottonPart, int quantity);

    Integer getQuantityOfSocks(Color color, Size size, Integer cottonMin, Integer cottonMax);
}
