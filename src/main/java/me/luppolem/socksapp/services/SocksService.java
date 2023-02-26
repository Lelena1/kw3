package me.luppolem.socksapp.services;

import me.luppolem.socksapp.model.Color;
import me.luppolem.socksapp.model.Size;
import me.luppolem.socksapp.model.Socks;

import java.util.Collection;
import java.util.Map;

public interface SocksService {

    Socks addSocks(Socks socks);

    Socks getSocks(Integer id);


    Collection<Socks> getAllSocks();

    Map<Integer, Socks> getSocksMap();


    void removeDefectiveSocks(Color color, Size size, int cottonPart, int quantity);

    boolean updateSocks(Color color, Size size, int cottonPart, int quantity);

    Integer getQuantityOfSocks(Color color, Size size, Integer cottonMin, Integer cottonMax);
}
