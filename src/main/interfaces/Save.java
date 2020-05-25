package main.interfaces;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface Save {
    public void save() throws FileNotFoundException, UnsupportedEncodingException;
}