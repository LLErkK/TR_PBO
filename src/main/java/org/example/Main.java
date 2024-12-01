package org.example;

import controller.Database;
import controller.Koneksi;
import view.FormLogin;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        FormLogin view = new FormLogin();
        view.setVisible(true);
    }
}