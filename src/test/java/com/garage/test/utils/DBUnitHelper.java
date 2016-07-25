package com.garage.test.utils;

import com.garage.test.utils.yaml.YamlDataSetLoader;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Ruslan Yaniuk
 * @date September 2015
 */
@Component
public class DBUnitHelper {

    public static final String DBUNIT_DATASETS = "/dbunit-datasets/";

    @Autowired
    private DataSource dataSource;

    private IDatabaseConnection dbUnitCon;

    private IDataSet garages;
    private IDataSet levels;
    private IDataSet parkingLots;
    private IDataSet vehicles;

    @PostConstruct
    private void init() throws DatabaseUnitException, IOException {
        Connection con = DataSourceUtils.getConnection(dataSource);

        dbUnitCon = new DatabaseConnection(con);
        dbUnitCon.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

        garages = YamlDataSetLoader.load(DBUNIT_DATASETS + "garages.yml");
        levels = YamlDataSetLoader.load(DBUNIT_DATASETS + "levels.yml");
        parkingLots = YamlDataSetLoader.load(DBUNIT_DATASETS + "parking-lots.yml");
        vehicles = YamlDataSetLoader.load(DBUNIT_DATASETS + "vehicles.yml");
    }

    public void insertGarages() throws FileNotFoundException, SQLException, DatabaseUnitException {
        DatabaseOperation.INSERT.execute(dbUnitCon, garages);
    }

    public void insertLevels() throws DatabaseUnitException, SQLException {
        DatabaseOperation.INSERT.execute(dbUnitCon, levels);
    }

    public void insertParkingLots() throws DatabaseUnitException, SQLException {
        DatabaseOperation.INSERT.execute(dbUnitCon, parkingLots);
    }

    public void insertVehicles() throws DatabaseUnitException, SQLException {
        DatabaseOperation.INSERT.execute(dbUnitCon, vehicles);
    }

    public void deleteAllFixtures() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(dbUnitCon, parkingLots);
        DatabaseOperation.DELETE_ALL.execute(dbUnitCon, vehicles);
        DatabaseOperation.DELETE_ALL.execute(dbUnitCon, levels);
        DatabaseOperation.DELETE_ALL.execute(dbUnitCon, garages);
    }
}
