package me.luppolem.socksapp.services;

import me.luppolem.socksapp.model.Socks;

import java.util.Collection;

public interface SocksService {

    Socks addSocks(Socks socks);

    Socks getSocks(Integer id);


    Collection<Socks> getAllSocks();

    Socks removeSocks(int id);

    Socks updateSocks(int id, Socks socks);
}
